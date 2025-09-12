/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditLabelMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.InitialDirectEditElementLabelQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EditableLabelDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
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
 * Integration tests for the edition of diagram elements label.
 *
 * @author pcdavid
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class EditLabelDiagramControllerTests extends AbstractIntegrationTests {
    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EditLabelMutationRunner editLabelMutationRunner;

    @Autowired
    private InitialDirectEditElementLabelQueryRunner initialDirectEditElementLabelQueryRunner;

    @Autowired
    private EditableLabelDiagramDescriptionProvider editableLabelDiagramDescriptionProvider;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToLabelEditableDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.editableLabelDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "EditableLabelDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node using a computed label, when its initial label is requested, then the raw label is returned")
    public void givenDiagramWithNodeUsingComputedLabelWhenItsInitialLabelIsRequestedThenTheRawLabelIsReturned() {
        var flux = this.givenSubscriptionToLabelEditableDiagram();

        var diagramId = new AtomicReference<String>();
        var labelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain-suffix").getNode();
            labelId.set(node.getInsideLabel().getId());
        });

        Runnable requestInitialLabel = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "diagramId", diagramId.get(),
                    "labelId", labelId.get()
            );
            var initialDirectEditElementLabelResult = this.initialDirectEditElementLabelQueryRunner.run(variables);

            String initialDirectEditElementLabel = JsonPath.read(initialDirectEditElementLabelResult, "$.data.viewer.editingContext.representation.description.initialDirectEditElementLabel");
            assertThat(initialDirectEditElementLabel).isEqualTo("sirius-web-domain");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestInitialLabel)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node, when its label is edited, then the label is updated")
    public void givenDiagramWithNodeWhenItsLabelIsEditedThenTheLabelIsUpdated() {
        var flux = this.givenSubscriptionToLabelEditableDiagram();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();
        var labelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain-suffix").getNode();
            nodeId.set(node.getId());
            labelId.set(node.getInsideLabel().getId());
        });

        Runnable editLabel = () -> {
            var input = new EditLabelInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), labelId.get(), "new label");
            var result = this.editLabelMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editLabel.__typename");
            assertThat(typename).isEqualTo(EditLabelSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var node = new DiagramNavigator(diagram).nodeWithId(nodeId.get()).getNode();
            assertThat(node.getInsideLabel()).hasText("new label-suffix");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(editLabel)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with an edge, when its begin label is edited, then the label is updated")
    public void givenDiagramWithEdgeWhenItsBeeginLabelIsEditedThenTheLabelIsUpdated() {
        var flux = this.givenSubscriptionToLabelEditableDiagram();

        var diagramId = new AtomicReference<String>();
        var edgeId = new AtomicReference<String>();
        var labelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var edge = new DiagramNavigator(diagram).edgeWithLabel("sirius-web-application - sirius-web-domain").getEdge();
            edgeId.set(edge.getId());
            labelId.set(edge.getCenterLabel().id());
        });

        Runnable editLabel = () -> {
            var input = new EditLabelInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), labelId.get(), "sirius-web-application-renamed");
            var result = this.editLabelMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.editLabel.__typename");
            assertThat(typename).isEqualTo(EditLabelSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentMatcher = assertRefreshedDiagramThat(diagram -> {
            var edge = new DiagramNavigator(diagram).edgeWithId(edgeId.get()).getEdge();
            assertThat(edge.getCenterLabel()).hasText("sirius-web-application-renamed - sirius-web-domain");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(editLabel)
                .consumeNextWith(updatedDiagramContentMatcher)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }


}
