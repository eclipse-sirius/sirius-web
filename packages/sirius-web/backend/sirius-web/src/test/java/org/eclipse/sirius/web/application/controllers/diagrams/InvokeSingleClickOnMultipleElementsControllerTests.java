/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramAssertions.assertThat;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.diagrams.tests.graphql.InvokeSingleClickOnDiagramElementToolExecutor;
import org.eclipse.sirius.components.diagrams.tests.graphql.PaletteExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.events.DecoratedCause;
import org.eclipse.sirius.components.view.emf.diagram.tools.PinElementToolHandler;
import org.eclipse.sirius.components.view.emf.diagram.tools.UnPinElementToolHandler;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.PapayaIdentifiers;
import org.eclipse.sirius.web.services.TestChangeDescriptionConsumer;
import org.eclipse.sirius.web.services.diagrams.GroupPaletteDiagramDescriptionProvider;
import org.eclipse.sirius.web.tests.data.GivenSiriusWebServer;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDiagramSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenInitialServerState;
import org.eclipse.sirius.web.tests.undoredo.UndoExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Integration tests for the group palette and group tools.
 *
 * @author mcharfadi
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = { "sirius.web.test.enabled=studio" })
public class InvokeSingleClickOnMultipleElementsControllerTests extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private PaletteExecutor paletteExecutor;

    @Autowired
    private InvokeSingleClickOnDiagramElementToolExecutor invokeSingleClickOnDiagramElementToolExecutor;

    @Autowired
    private GroupPaletteDiagramDescriptionProvider groupPaletteDiagramDescriptionProvider;

    @Autowired
    private TestChangeDescriptionConsumer testChangeDescriptionConsumer;

    @Autowired
    private UndoExecutor undoExecutor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
        this.testChangeDescriptionConsumer.reset();
    }

    private Flux<Object> givenSubscriptionToLifeCycleDiagram() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                this.groupPaletteDiagramDescriptionProvider.getRepresentationDescriptionId(),
                PapayaIdentifiers.PROJECT_OBJECT.toString(),
                "GroupPaletteDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when we request a group palette on several nodes, then it is returned properly")
    public void givenDiagramWithSomeNodesWhenWeRequestGroupPaletteOnSeveralNodesThenItIsReturnedProperly() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode().getId();
            var siriusWebApplicationId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode().getId();
            var siriusWebInfrastructureId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId();

            componentIds.add(siriusWebDomainId);
            componentIds.add(siriusWebApplicationId);
            componentIds.add(siriusWebInfrastructureId);
        });

        Runnable requestGroupPalette = () -> this.paletteExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                diagramId.get(),
                componentIds
        ).hasPaletteEntries(paletteEntryLabels -> assertThat(paletteEntryLabels).isNotEmpty());

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestGroupPalette)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when we execute a tool on a group of nodes, then it is executed")
    public void givenDiagramWithSomeNodesWhenWeExecuteToolOnGroupOfNodesThenItIsExecuted() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode().getId();
            var siriusWebApplicationId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode().getId();
            var siriusWebInfrastructureId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId();

            componentIds.add(siriusWebDomainId);
            componentIds.add(siriusWebApplicationId);
            componentIds.add(siriusWebInfrastructureId);
        });

        Runnable executeTool = () -> {
            String toolId = this.groupPaletteDiagramDescriptionProvider.getEditToolId();
            this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                    diagramId.get(),
                    componentIds,
                    toolId,
                    0,
                    0,
                    List.of()).isSuccess();
        };

        Consumer<Object> updatedDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain Suffix");
            new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application Suffix");
            new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure Suffix");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(executeTool)
                .consumeNextWith(updatedDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when the bulk pin and unpin tools are executed, then all nodes are updated")
    public void givenDiagramWithSomeNodesWhenBulkPinAndUnpinToolsAreExecutedThenAllNodesAreUpdated() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomain = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode();
            var siriusWebApplication = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode();
            var siriusWebInfrastructure = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode();

            assertThat(siriusWebDomain).isNotPinned();
            assertThat(siriusWebApplication).isNotPinned();
            assertThat(siriusWebInfrastructure).isNotPinned();

            componentIds.add(siriusWebDomain.getId());
            componentIds.add(siriusWebApplication.getId());
            componentIds.add(siriusWebInfrastructure.getId());
        });

        Runnable requestPinTool = () -> this.paletteExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                diagramId.get(),
                componentIds
        ).hasQuickAccessTools(quickAccessToolLabels -> assertThat(quickAccessToolLabels).contains("Pin").doesNotContain("Unpin"));

        Runnable pinDiagramElements = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                diagramId.get(),
                componentIds,
                PinElementToolHandler.PIN_ELEMENT_TOOL_ID,
                0,
                0,
                List.of()
        ).isSuccess();

        Consumer<Object> pinnedNodesDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode()).isPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode()).isPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode()).isPinned();
        });

        Runnable requestUnPinTool = () -> this.paletteExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                diagramId.get(),
                componentIds
        ).hasQuickAccessTools(quickAccessToolLabels -> assertThat(quickAccessToolLabels).contains("Unpin").doesNotContain("Pin"));

        Runnable unPinDiagramElements = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                diagramId.get(),
                componentIds,
                UnPinElementToolHandler.UNPIN_ELEMENT_TOOL_ID,
                0,
                0,
                List.of()
        ).isSuccess();

        Consumer<Object> unPinnedNodesDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode()).isNotPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode()).isNotPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode()).isNotPinned();
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(requestPinTool)
                .then(pinDiagramElements)
                .consumeNextWith(pinnedNodesDiagramContentConsumer)
                .then(requestUnPinTool)
                .then(unPinDiagramElements)
                .consumeNextWith(unPinnedNodesDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with mixed pin states, when bulk pin is undone, then the previous pin states are restored")
    public void givenDiagramWithMixedPinStatesWhenBulkPinIsUndoneThenThePreviousPinStatesAreRestored() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();
        var bulkPinInputId = new AtomicReference<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());
            componentIds.add(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode().getId());
            componentIds.add(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode().getId());
            componentIds.add(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId());
        });

        Runnable pinFirstDiagramElement = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                diagramId.get(),
                List.of(componentIds.getFirst()),
                PinElementToolHandler.PIN_ELEMENT_TOOL_ID,
                0,
                0,
                List.of()
        ).isSuccess();

        Consumer<Object> mixedPinStatesDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode()).isPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode()).isNotPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode()).isNotPinned();
        });

        Runnable pinAllDiagramElements = () -> {
            bulkPinInputId.set(this.invokeSingleClickOnDiagramElementToolExecutor.execute(
                            PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                            diagramId.get(),
                            componentIds,
                            PinElementToolHandler.PIN_ELEMENT_TOOL_ID,
                            0,
                            0,
                            List.of()
                    ).isSuccess()
                    .getId());
        };

        Consumer<Object> pinnedNodesDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode()).isPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode()).isPinned();
            assertThat(new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode()).isPinned();
        });

        Runnable undoBulkPin = () -> this.undoExecutor.execute(
                PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(),
                UUID.fromString(bulkPinInputId.get())
        ).isSuccess();

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(pinFirstDiagramElement)
                .consumeNextWith(mixedPinStatesDiagramContentConsumer)
                .then(pinAllDiagramElements)
                .consumeNextWith(pinnedNodesDiagramContentConsumer)
                .then(undoBulkPin)
                .consumeNextWith(mixedPinStatesDiagramContentConsumer)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a diagram with some nodes, when we execute a tool on a group of nodes, then an event decorator is produced")
    public void givenDiagramWithSomeNodesWhenWeExecuteToolOnGroupOfNodesThenAnEventDecoratorIsProduced() {
        var flux = this.givenSubscriptionToLifeCycleDiagram();
        var diagramId = new AtomicReference<String>();
        var componentIds = new ArrayList<String>();

        Consumer<Object> initialDiagramContentConsumer = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var siriusWebDomainId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-domain").getNode().getId();
            var siriusWebApplicationId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-application").getNode().getId();
            var siriusWebInfrastructureId = new DiagramNavigator(diagram).nodeWithLabel("sirius-web-infrastructure").getNode().getId();

            componentIds.add(siriusWebDomainId);
            componentIds.add(siriusWebApplicationId);
            componentIds.add(siriusWebInfrastructureId);
        });

        Runnable executeTool = () -> this.invokeSingleClickOnDiagramElementToolExecutor.execute(PapayaIdentifiers.PAPAYA_EDITING_CONTEXT_ID.toString(), diagramId.get(), componentIds, this.groupPaletteDiagramDescriptionProvider.getEditToolId(), 0, 0, List.of())
                .isSuccess();

        Runnable checkDecoratedCause = () -> {
            var changeDescription = this.testChangeDescriptionConsumer.getAcceptChangeDescription();

            assertThat(changeDescription).isNotNull();
            assertThat(changeDescription.getCause()).isInstanceOf(DecoratedCause.class);
            var decoratedCause = (DecoratedCause) changeDescription.getCause();
            assertThat(decoratedCause.label()).isEqualTo("Used \"Edit labels\" on the sirius-web-domain, sirius-web-application, sirius-web-infrastructure");
        };

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagramContentConsumer)
                .then(executeTool)
                .consumeNextWith(assertRefreshedDiagramThat(diagram -> assertThat(diagram).isNotNull()))
                .then(checkDecoratedCause)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
