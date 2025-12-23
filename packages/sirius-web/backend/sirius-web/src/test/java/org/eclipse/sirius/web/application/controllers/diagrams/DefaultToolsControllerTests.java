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

import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;
import static org.eclipse.sirius.components.diagrams.tests.DiagramEventPayloadConsumer.assertRefreshedDiagramThat;

import com.jayway.jsonpath.JsonPath;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.EdgeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LabelLayoutDataInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.NodeLayoutDataInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.layoutdata.HandleLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.HandleType;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.diagrams.layoutdata.Size;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolExecutor;
import org.eclipse.sirius.components.diagrams.tests.graphql.LayoutDiagramMutationRunner;
import org.eclipse.sirius.components.diagrams.tests.graphql.PaletteQueryRunner;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.view.emf.diagram.tools.FadeElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.PinElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.UnFadeElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.UnPinElementToolHandler;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.diagrams.EdgeDiagramDescriptionProvider;
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
 * Integration tests for the declaration and execution of default tools.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class DefaultToolsControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private EdgeDiagramDescriptionProvider edgeDiagramDescriptionProvider;

    @Autowired
    private PaletteQueryRunner paletteQueryRunner;

    @Autowired
    private LayoutDiagramMutationRunner layoutDiagramMutationRunner;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolExecutor invokeSingleClickOnDiagramElementToolExecutor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenSubscriptionToLabelEditableDiagramDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.edgeDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "DefaultToolDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node, when the palette is queried, then the default tools are available")
    public void givenDiagramWithANodeWhenThePaletteIsRequestedThenTheDefaultToolsAreReturned() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebDomainNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            siriusWebDomainNodeId.set(siriusWebDomainNode.getId());
        });

        Runnable requestPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(siriusWebDomainNodeId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> quickToolsLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].label");
            assertThat(quickToolsLabels).hasSize(4);
            assertThat(quickToolsLabels).containsSequence("Pin", "Adjust size", "Fade", "Hide");

            List<String> paletteEntriesLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(paletteEntriesLabels).hasSize(1);
            assertThat(paletteEntriesLabels).containsSequence("Edit");

            List<String> paletteEditLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].tools[*].label");
            assertThat(paletteEditLabels).hasSize(2);
            assertThat(paletteEditLabels).containsSequence("Edit", "Delete from model");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a node, when a default tool is executed, then its execution update the diagram")
    public void givenDiagramWithANodeWhenTheADefaultToolIsExecutedThenTheDiagramIsUpdated() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebDomainNodeId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            siriusWebDomainNodeId.set(siriusWebDomainNode.getId());
        });

        Consumer<Object> fadedNodeDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).hasModifiers(Set.of(ViewModifier.Faded));
        });

        Runnable fadeDiagramElement = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(siriusWebDomainNodeId.get()) , FadeElementToolHandler.FADE_ELEMENT_TOOL_ID, 0, 0, List.of())
                .isSuccess();

        Consumer<Object> pinnedNodeDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).isPinned();
        });

        Runnable pinDiagramElement = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(siriusWebDomainNodeId.get()) , PinElementToolHandler.PIN_ELEMENT_TOOL_ID, 0, 0, List.of())
                .isSuccess();

        Runnable requestPalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(siriusWebDomainNodeId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> quickToolsLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].label");
            assertThat(quickToolsLabels).hasSize(4);
            assertThat(quickToolsLabels).containsSequence("Unpin", "Adjust size", "Unfade", "Hide");

            List<String> paletteEntriesLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(paletteEntriesLabels).hasSize(1);
            assertThat(paletteEntriesLabels).containsSequence("Edit");

            List<String> paletteEditLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].tools[*].label");
            assertThat(paletteEditLabels).hasSize(2);
            assertThat(paletteEditLabels).containsSequence("Edit", "Delete from model");
        };

        Consumer<Object> unFadedNodeDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).doesNotContainAnyModifiersOf(Set.of(ViewModifier.Faded));
        });

        Runnable unFadeDiagramElement = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(siriusWebDomainNodeId.get()) , UnFadeElementToolHandler.UNFADE_ELEMENT_TOOL_ID, 0, 0, List.of())
                .isSuccess();

        Consumer<Object> unPinnedNodeDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            assertThat(siriusWebDomainNode).isNotPinned();
        });

        Runnable unPinDiagramElement = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), List.of(siriusWebDomainNodeId.get()) , UnPinElementToolHandler.UNPIN_ELEMENT_TOOL_ID, 0, 0, List.of())
                .isSuccess();

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(fadeDiagramElement)
                .consumeNextWith(fadedNodeDiagramContentConsumer)
                .then(pinDiagramElement)
                .consumeNextWith(pinnedNodeDiagramContentConsumer)
                .then(requestPalette)
                .then(unFadeDiagramElement)
                .consumeNextWith(unFadedNodeDiagramContentConsumer)
                .then(unPinDiagramElement)
                .consumeNextWith(unPinnedNodeDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with a layouted element, when the palette is queried, then the default tools are available")
    public void givenDiagramWithALayoutedElementWhenThePaletteIsRequestedThenTheDefaultToolsAreReturned() {
        var flux = this.givenSubscriptionToLabelEditableDiagramDiagram();

        var diagramId = new AtomicReference<String>();
        var siriusWebDomainNodeId = new AtomicReference<String>();
        var webApplicationToDomainEdgeId = new AtomicReference<String>();
        var webApplicationToDomainEdgeOutsideLabelId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            var siriusWebDomainNode = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            siriusWebDomainNodeId.set(siriusWebDomainNode.getId());
            var webApplicationToDomainEdge = new DiagramNavigator(diagram).edgeWithLabel("sirius-web-application -> sirius-web-domain").getEdge();
            webApplicationToDomainEdgeId.set(webApplicationToDomainEdge.getId());
            webApplicationToDomainEdgeOutsideLabelId.set(webApplicationToDomainEdge.getCenterLabel().id());
        });

        Runnable initialDiagramLayout = () -> {
            var siriusWebDomainNodeHandleLayoutLayout = new HandleLayoutData(webApplicationToDomainEdgeId.get(), new Position(50, 0), "top", HandleType.target);
            var siriusWebDomainNodeLayout = new NodeLayoutDataInput(siriusWebDomainNodeId.get(), new Position(0, 0), new Size(50, 50), true, true, List.of(siriusWebDomainNodeHandleLayoutLayout), new Size(40, 40));

            var bendingPoints = List.of(new Position(10, 10), new Position(20, 20));
            var webApplicationToDomainEdgeLayoutData = new EdgeLayoutDataInput(webApplicationToDomainEdgeId.get(), bendingPoints, List.of());

            var labelLayoutData = new LabelLayoutDataInput(webApplicationToDomainEdgeOutsideLabelId.get(), new Position(5, 5), new Size(10, 10), true);
            var layoutData = new DiagramLayoutDataInput(List.of(siriusWebDomainNodeLayout), List.of(webApplicationToDomainEdgeLayoutData), List.of(labelLayoutData));
            var layoutInput = new LayoutDiagramInput(UUID.randomUUID(), PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), "layout", layoutData);
            this.layoutDiagramMutationRunner.run(layoutInput);
        };

        Consumer<Object> afterLayoutDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(diagram.getLayoutData().nodeLayoutData()).isNotEmpty();
            assertThat(diagram.getLayoutData().edgeLayoutData()).isNotEmpty();
            assertThat(diagram.getLayoutData().labelLayoutData()).isNotEmpty();
        });

        Runnable requestEdgePalette = () -> {
            Map<String, Object> variables = Map.of(
                    "editingContextId", PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    "representationId", diagramId.get(),
                    "diagramElementIds", List.of(webApplicationToDomainEdgeId.get())
            );
            var result = this.paletteQueryRunner.run(variables);

            List<String> quickToolsLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.quickAccessTools[*].label");
            assertThat(quickToolsLabels).hasSize(6);
            assertThat(quickToolsLabels).containsSequence("Reset outside labels positions", "Reset labels sizes", "Reset bending points", "Reset handles positions", "Fade", "Hide");

            List<String> paletteEntriesLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].label");
            assertThat(paletteEntriesLabels).hasSize(1);
            assertThat(paletteEntriesLabels).containsSequence("Edit");

            List<String> paletteEditLabels = JsonPath.read(result.data(), "$.data.viewer.editingContext.representation.description.palette.paletteEntries[*].tools[*].label");
            assertThat(paletteEditLabels).hasSize(2);
            assertThat(paletteEditLabels).containsSequence("Edit", "Delete from model");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(initialDiagramLayout)
                .consumeNextWith(afterLayoutDiagramContentConsumer)
                .then(requestEdgePalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
