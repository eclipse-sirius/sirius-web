/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.controllers.diagrams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateChildInput;
import org.eclipse.sirius.components.collaborative.dto.CreateChildSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
import org.eclipse.sirius.components.graphql.tests.EditingContextEventSubscriptionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;
import org.eclipse.sirius.web.application.document.dto.CreateDocumentSuccessPayload;
import org.eclipse.sirius.web.data.TestIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EmptyDocumentStereotypeHandler;
import org.eclipse.sirius.web.services.diagrams.RelationBasedEdgeDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.graphql.CreateChildMutationRunner;
import org.eclipse.sirius.web.tests.graphql.CreateDocumentMutationRunner;
import org.eclipse.sirius.web.tests.graphql.CreateRootObjectMutationRunner;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests of relation based edges.
 *
 * @author arichard
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class RelationBasedEdgeControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCommittedTransaction givenCommittedTransaction;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditingContextEventSubscriptionRunner editingContextEventSubscriptionRunner;

    @Autowired
    private CreateDocumentMutationRunner createDocumentMutationRunner;

    @Autowired
    private CreateRootObjectMutationRunner createRootObjectMutationRunner;

    @Autowired
    private CreateChildMutationRunner createChildMutationRunner;

    @Autowired
    private RelationBasedEdgeDiagramDescriptionProvider relationBasedEdgeDescriptionProvider;

    private AtomicReference<UUID> documentId;

    private AtomicReference<String> rootObjectId;

    private AtomicReference<String> entityObjectId;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
        this.documentId = new AtomicReference<>();
        this.rootObjectId = new AtomicReference<>();
        this.entityObjectId = new AtomicReference<>();
    }

    private Flux<Object> givenSubscriptionToRelationBasedEdgeDiagram(String objectId) {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                this.relationBasedEdgeDescriptionProvider.getRepresentationDescriptionId(),
                objectId,
                "RelationBasedEdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with composition edges, when , then there is only one edge between an Entity and its subEntity")
    public void givenDiagramWithCompoistionEdgesWhenThenItWorksAsExpected() {

        this.createData();

        var flux = this.givenSubscriptionToRelationBasedEdgeDiagram(this.rootObjectId.get());

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            assertThat(diagram.getNodes()).hasSize(3);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private void createData() {
        var editingContextEventInput = new EditingContextEventInput(UUID.randomUUID(), TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString());
        var flux = this.editingContextEventSubscriptionRunner.run(editingContextEventInput);

        Consumer<CreateDocumentInput> createEmptyDocumentTask = this.createEmptyDocumentTask2();
        var createEmptyDocumentInput = new CreateDocumentInput(
                UUID.randomUUID(),
                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                EmptyDocumentStereotypeHandler.STEREOTYPE_ID_TEST,
                "EmptyDoc");

        Consumer<CreateRootObjectInput> createRootObjectTask = this.createRootObjectTask();
        Consumer<CreateChildInput> createEntityChildTask = this.createEntityChildTask("NewEntity1");
        Consumer<CreateChildInput> createSubEntityChildTask = this.createEntityChildTask("NewSubEntity1");
        Consumer<CreateChildInput> createSubSubEntityChildTask = this.createEntityChildTask("NewSubEntity1");

        StepVerifier.create(flux)
                .then(() -> createEmptyDocumentTask.accept(createEmptyDocumentInput))
                .then(() -> createRootObjectTask.accept(
                        new CreateRootObjectInput(
                                UUID.randomUUID(),
                                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                                this.documentId.get(),
                                "domain://rbeDomain",
                                "Root")))
                .then(() -> createEntityChildTask.accept(
                        new CreateChildInput(
                                UUID.randomUUID(),
                                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                                this.rootObjectId.get(),
                                "entities-Entity")))
                .then(() -> createSubEntityChildTask.accept(
                        new CreateChildInput(
                                UUID.randomUUID(),
                                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                                this.entityObjectId.get(),
                                "entities-Entity")))
                .then(() -> createSubSubEntityChildTask.accept(
                        new CreateChildInput(
                                UUID.randomUUID(),
                                TestIdentifiers.SYSML_SAMPLE_EDITING_CONTEXT_ID.toString(),
                                this.entityObjectId.get(),
                                "entities-Entity")))
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    private Consumer<CreateDocumentInput> createEmptyDocumentTask2() {
        this.givenCommittedTransaction.commit();

        Consumer<CreateDocumentInput> createEmptyDocumentTask = (input) -> {
            var result = this.createDocumentMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.createDocument.__typename");
            assertThat(typename).isEqualTo(CreateDocumentSuccessPayload.class.getSimpleName());

            String createdDocumentId = JsonPath.read(result, "$.data.createDocument.document.id");
            assertThat(typename).isNotEmpty();
            this.documentId.set(UUID.fromString(createdDocumentId));
        };
        return createEmptyDocumentTask;
    }

    private Consumer<CreateRootObjectInput> createRootObjectTask() {
        this.givenCommittedTransaction.commit();

        Consumer<CreateRootObjectInput> createRootObjectTask = (input) -> {
            var result = this.createRootObjectMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.createRootObject.__typename");
            assertThat(typename).isEqualTo(CreateRootObjectSuccessPayload.class.getSimpleName());

            String objectId = JsonPath.read(result, "$.data.createRootObject.object.id");
            assertThat(objectId).isNotBlank();
            this.rootObjectId.set(objectId);

            String objectKind = JsonPath.read(result, "$.data.createRootObject.object.kind");
            assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=rbeDomain&entity=Root");
        };
        return createRootObjectTask;
    }

    private Consumer<CreateChildInput> createEntityChildTask(String name) {
        this.givenCommittedTransaction.commit();

        Consumer<CreateChildInput> createEntityChildTask = (input) -> {
            var result = this.createChildMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.createChild.__typename");
            assertThat(typename).isEqualTo(CreateChildSuccessPayload.class.getSimpleName());

            String objectId = JsonPath.read(result, "$.data.createChild.object.id");
            assertThat(objectId).isNotBlank();
            this.entityObjectId.set(objectId);

            String objectLabel = JsonPath.read(result, "$.data.createChild.object.label");
            assertThat(objectLabel).isEqualTo(name);

            String objectKind = JsonPath.read(result, "$.data.createChild.object.kind");
            assertThat(objectKind).isEqualTo("siriusComponents://semantic?domain=rbeDomain&entity=Entity");

        };
        return createEntityChildTask;
    }
}
