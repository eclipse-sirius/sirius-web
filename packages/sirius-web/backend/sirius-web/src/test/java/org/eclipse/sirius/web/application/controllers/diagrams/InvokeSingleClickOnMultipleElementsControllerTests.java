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

import com.jayway.jsonpath.JsonPath;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.PaletteQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.tools.LifecycleGroupPaletteProvider;
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

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

/**
 * Integration tests for the group palette and group tools.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class InvokeSingleClickOnMultipleElementsControllerTests extends AbstractIntegrationTests {

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=diagramDescription&sourceKind=view&sourceId=83f1194e-9833-398b-80ad-be5d77e02585&sourceElementId=37dc91b6-6550-3337-85a5-6f1dfd02ca4c";

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private PaletteQueryRunner paletteQueryRunner;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolMutationRunner invokeSingleClickOnDiagramElementToolMutationRunner;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToLifeCycleDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                REPRESENTATION_DESCRIPTION_ID,
                PapayaIdentifiers.PAPAYA_SIRIUS_WEB_LIFECYCLE_ROOT_OBJECT.toString(),
                "EdgeOnEdgeDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input);
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, then we can request a group palette on several nodes")
    public void givenDiagramWithSomeNodesThenWeCanRequestAGroupPalette() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var channelId = new AtomicReference<String>();
        var controllerId = new AtomicReference<String>();
        var eventId = new AtomicReference<String>();
        var commandId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var nodeCount = new DiagramNavigator(diagram).findDiagramNodeCount();
            assertThat(nodeCount).isEqualTo(11);

            var channelNodeId = new DiagramNavigator(diagram).nodeWithLabel("HTTP").getNode().getId();
            var controllerNodeId = new DiagramNavigator(diagram).nodeWithLabel("Controller1").getNode().getId();
            var commandNodeId = new DiagramNavigator(diagram).nodeWithLabel("Command1").getNode().getId();
            var eventNodeId = new DiagramNavigator(diagram).nodeWithLabel("ProjectSemanticDataCreatedEvent").getNode().getId();
            diagramId.set(diagram.getId());
            channelId.set(channelNodeId);
            controllerId.set(controllerNodeId);
            eventId.set(eventNodeId);
            commandId.set(commandNodeId);
            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(3);
        });

        Runnable requestEmptyGroupPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(channelId.get(), controllerId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> topLevelToolsLabel = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(topLevelToolsLabel)
                    .isEmpty();
        };

        Runnable requestGroupPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(channelId.get(), controllerId.get(), eventId.get(), commandId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> topLevelToolsLabel = JsonPath.read(result, "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(topLevelToolsLabel)
                    .hasSize(1);
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestEmptyGroupPalette)
                .then(requestGroupPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, then we can execute a group tool")
    public void givenDiagramWithSomeNodesThenWeCanExecuteAGroupTool() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var channelId = new AtomicReference<String>();
        var controllerId = new AtomicReference<String>();
        var eventId = new AtomicReference<String>();
        var commandId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var nodeCount = new DiagramNavigator(diagram).findDiagramNodeCount();
            assertThat(nodeCount).isEqualTo(11);

            var channelNodeId = new DiagramNavigator(diagram).nodeWithLabel("HTTP").getNode().getId();
            var controllerNodeId = new DiagramNavigator(diagram).nodeWithLabel("Controller1").getNode().getId();
            var commandNodeId = new DiagramNavigator(diagram).nodeWithLabel("Command1").getNode().getId();
            var eventNodeId = new DiagramNavigator(diagram).nodeWithLabel("ProjectSemanticDataCreatedEvent").getNode().getId();
            diagramId.set(diagram.getId());
            channelId.set(channelNodeId);
            controllerId.set(controllerNodeId);
            eventId.set(eventNodeId);
            commandId.set(commandNodeId);
            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(3);
        });

        Runnable executeTool = () -> {
            String toolId = LifecycleGroupPaletteProvider.getCausedByGroupToolId();
            var candidateNodeIds = List.of(channelId.get(), controllerId.get(), eventId.get(), commandId.get());
            var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), candidateNodeIds, toolId, 0, 0, List.of());
            var result = this.invokeSingleClickOnDiagramElementToolMutationRunner.run(input);

            String typename = JsonPath.read(result, "$.data.invokeSingleClickOnDiagramElementTool.__typename");
            assertThat(typename).isEqualTo(InvokeSingleClickOnDiagramElementToolSuccessPayload.class.getSimpleName());
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var edgeCount = new DiagramNavigator(diagram).findDiagramEdgeCount();
            assertThat(edgeCount).isEqualTo(6);
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(executeTool)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }


}