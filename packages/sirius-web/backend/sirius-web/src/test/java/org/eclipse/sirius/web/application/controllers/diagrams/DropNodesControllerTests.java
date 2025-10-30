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

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodesInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.SuccessPayload;
import org.eclipse.sirius.components.diagrams.tests.graphql.DropNodesMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.GetDropNodeCompatibilityQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.DropNodeDiagramDescriptionProvider;
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
 * Integration tests of the drag and drop of nodes.
 *
 * @author sbegaudeau
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DropNodesControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private DropNodeDiagramDescriptionProvider dropNodeDiagramDescriptionProvider;

    @Autowired
    private GetDropNodeCompatibilityQueryRunner dropNodeCompatibilityQueryRunner;

    @Autowired
    private DropNodesMutationRunner dropNodesMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToDropNodeDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.dropNodeDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "DropNodeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when a node is dropped in another one, then the diagram is updated")
    public void givenDiagramWithSomeNodesWhenNodeIsDroppedInAnotherOneThenTheDiagramIsUpdated() {
        var flux = this.givenSubscriptionToDropNodeDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebApplicationNodeId = new AtomicReference<String>();
        var siriusWebInfrastructureNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebApplicationNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            siriusWebApplicationNodeId.set(siriusWebApplicationNode.getId());

            var siriusWebInfrastructureNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();
            siriusWebInfrastructureNodeId.set(siriusWebInfrastructureNode.getId());

            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel().text().equals("sirius-web-infrastructure -> sirius-web-application"))
                    .isEmpty();
        });

        Runnable dropNode = () -> {
            var input = new DropNodesInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(siriusWebApplicationNodeId.get()),
                    siriusWebInfrastructureNodeId.get(),
                    0,
                    0
            );
            var result = this.dropNodesMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.dropNodes.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getEdges())
                    .filteredOn(edge -> edge.getCenterLabel().text().equals("sirius-web-infrastructure -> sirius-web-application"))
                    .hasSize(1);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(dropNode)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when a node is dropped on the diagram, then the diagram is updated")
    public void givenDiagramWithSomeNodesWhenNodeIsDroppedOnTheDiagramThenTheDiagramIsUpdated() {
        var flux = this.givenSubscriptionToDropNodeDiagram();

        var diagramId = new AtomicReference<String>();
        var servicesNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var servicesNode = new DiagramNavigator(diagram)
                    .nodeWithLabel("sirius-web-domain")
                    .childNodeWithLabel("services")
                    .getNode();
            servicesNodeId.set(servicesNode.getId());

            assertThat(diagram.getNodes()).noneMatch(node -> node.getInsideLabel().getText().equals("services"));
        });

        Runnable dropNode = () -> {
            var input = new DropNodesInput(
                    UUID.randomUUID(),
                    PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    List.of(servicesNodeId.get()),
                    null,
                    0,
                    0
            );
            var result = this.dropNodesMutationRunner.run(input);
            String typename = JsonPath.read(result, "$.data.dropNodes.__typename");
            assertThat(typename).isEqualTo(SuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getNodes()).anyMatch(node -> node.getInsideLabel().getText().equals("services"));
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(dropNode)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when the compatibility of the drop node tool is requested, then the data is retrieved")
    public void givenDiagramWithSomeNodesWhenTheCompatibilityOfTheDropNodeToolIsRequestedThenTheDataIsReceived() {
        var flux = this.givenSubscriptionToDropNodeDiagram();

        var diagramId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> diagramId.set(diagram.getId()));

        Runnable getDropNodeCompatibility = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get()
            );
            var result = this.dropNodeCompatibilityQueryRunner.run(variables);
            List<String> droppedNodeDescriptionIds = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.dropNodeCompatibility[*].droppedNodeDescriptionId");
            assertThat(droppedNodeDescriptionIds).isNotNull();
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(getDropNodeCompatibility)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
