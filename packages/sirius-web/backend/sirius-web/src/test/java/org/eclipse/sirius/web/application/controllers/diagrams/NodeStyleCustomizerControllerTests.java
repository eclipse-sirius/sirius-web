/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import static org.eclipse.sirius.components.diagrams.tests.assertions.DiagramInstanceOfAssertFactories.RECTANGULAR_NODE_STYLE;

import fr.obeo.dsl.designer.sample.flow.CompositeProcessor;
import fr.obeo.dsl.designer.sample.flow.FlowElementStatus;
import fr.obeo.dsl.designer.sample.flow.PoweredStatus;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.tests.graphql.EditRectangularNodeAppearanceExecutor;
import org.eclipse.sirius.components.diagrams.tests.navigation.DiagramNavigator;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionInput;
import org.eclipse.sirius.components.graphql.tests.ExecuteEditingContextFunctionSuccessPayload;
import org.eclipse.sirius.components.graphql.tests.api.IExecuteEditingContextFunctionRunner;
import org.eclipse.sirius.web.AbstractIntegrationTests;
import org.eclipse.sirius.web.data.FlowIdentifier;
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
 * Integration tests for node style customization.
 *
 * @author gcoutable
 */
@Transactional
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NodeStyleCustomizerControllerTests  extends AbstractIntegrationTests {

    @Autowired
    private IGivenInitialServerState givenInitialServerState;

    @Autowired
    private IGivenCreatedDiagramSubscription givenCreatedDiagramSubscription;

    @Autowired
    private IObjectSearchService objectSearchService;

    @Autowired
    private IExecuteEditingContextFunctionRunner executeEditingContextFunctionRunner;

    @Autowired
    private EditRectangularNodeAppearanceExecutor editRectangularNodeAppearanceExecutor;

    @BeforeEach
    public void beforeEach() {
        this.givenInitialServerState.initialize();
    }

    private Flux<Object> givenDiagramSubscription() {
        var input = new CreateRepresentationInput(
                UUID.randomUUID(),
                FlowIdentifier.FLOW_EDITING_CONTEXT_ID,
                FlowIdentifier.TOPOGRAPHY_VIEW_DESCRIPTION_ID,
                FlowIdentifier.FLOW_ROOT_SYSTEM_OBJECT,
                "TopographyDiagram"
        );
        return this.givenCreatedDiagramSubscription.createAndSubscribe(input).flux();
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a flow node in a topography diagram, when conditions activating node style customization are met, then node style customizations are applied")
    public void givenFlowNodeInTopographyDiagramWhenConditionsActivatingNodeStyleCustomizationAreMetThenNodeStyleCustomizationsAreApplied() {
        var flux = this.givenDiagramSubscription();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();
        var labelId = new AtomicReference<String>();

        Consumer<Object> initialDiagram = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("CompositeProcessor1").getNode();
            assertThat(node.getStyle()).isInstanceOf(RectangularNodeStyle.class)
                    .asInstanceOf(RECTANGULAR_NODE_STYLE)
                    .hasBackground("#F0F0F0")
                    .hasBorderSize(1)
                    .hasBorderColor("#EF5350")
                    .hasBorderStyle(LineStyle.Solid);
            nodeId.set(node.getId());
            labelId.set(node.getInsideLabel().getId());
        });

        Runnable enableANodeStyleCustomization = () -> {
            BiFunction<IEditingContext, IInput, IPayload> updatePowerStatus = (editingContext, input) -> {
                this.objectSearchService.getObject(editingContext, FlowIdentifier.FLOW_COMPOSITE_PROCESSOR_1)
                        .filter(CompositeProcessor.class::isInstance)
                        .map(CompositeProcessor.class::cast)
                        .ifPresent(compositeProcessor -> {
                            compositeProcessor.setPowerStatus(PoweredStatus.LOW);
                        });
                return new ExecuteEditingContextFunctionSuccessPayload(input.id(), null);
            };

            var inputId = UUID.randomUUID();
            var input = new ExecuteEditingContextFunctionInput(inputId, FlowIdentifier.FLOW_EDITING_CONTEXT_ID, updatePowerStatus, new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, FlowIdentifier.FLOW_EDITING_CONTEXT_ID, () -> inputId));
            this.executeEditingContextFunctionRunner.execute(input);
        };

        Consumer<Object> verifyTheNodeCustomizationApplied = assertRefreshedDiagramThat(newDiagram -> {
            var node = new DiagramNavigator(newDiagram).nodeWithId(nodeId.get()).getNode();
            assertThat(node.getStyle()).isInstanceOf(RectangularNodeStyle.class)
                    .asInstanceOf(RECTANGULAR_NODE_STYLE)
                    .hasBackground("#F0F0F0")
                    .hasBorderColor("#FF9800");
        });

        Runnable enableAnotherNodeStyleCustomization = () -> {
            BiFunction<IEditingContext, IInput, IPayload> updatePowerStatus = (editingContext, input) -> {
                this.objectSearchService.getObject(editingContext, FlowIdentifier.FLOW_COMPOSITE_PROCESSOR_1)
                        .filter(CompositeProcessor.class::isInstance)
                        .map(CompositeProcessor.class::cast)
                        .ifPresent(compositeProcessor -> {
                            compositeProcessor.setStatus(FlowElementStatus.INACTIVE);
                        });
                return new ExecuteEditingContextFunctionSuccessPayload(input.id(), null);
            };

            var inputId = UUID.randomUUID();
            var input = new ExecuteEditingContextFunctionInput(inputId, FlowIdentifier.FLOW_EDITING_CONTEXT_ID, updatePowerStatus, new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, FlowIdentifier.FLOW_EDITING_CONTEXT_ID, () -> inputId));
            this.executeEditingContextFunctionRunner.execute(input);
        };

        Consumer<Object> verifyBothStyleNodeCustomizationApplied = assertRefreshedDiagramThat(newDiagram -> {
            var node = new DiagramNavigator(newDiagram).nodeWithId(nodeId.get()).getNode();
            assertThat(node.getStyle()).isInstanceOf(RectangularNodeStyle.class)
                    .asInstanceOf(RECTANGULAR_NODE_STYLE)
                    .hasBackground("#FFFFFF")
                    .hasBorderColor("#FF9800");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagram)
                .then(enableANodeStyleCustomization)
                .consumeNextWith(verifyTheNodeCustomizationApplied)
                .then(enableAnotherNodeStyleCustomization)
                .consumeNextWith(verifyBothStyleNodeCustomizationApplied)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }

    @Test
    @GivenSiriusWebServer
    @DisplayName("Given a node with a customized style, when its appearance is edited manually, then the manual changes take precedence")
    public void givenNodeWithCustomizedStyleWhenItsAppearanceIsEditedManuallyThenManualChangeTakePrecedence() {
        var flux = this.givenDiagramSubscription();

        var diagramId = new AtomicReference<String>();
        var nodeId = new AtomicReference<String>();
        var labelId = new AtomicReference<String>();

        Consumer<Object> initialDiagram = assertRefreshedDiagramThat(diagram -> {
            diagramId.set(diagram.getId());

            var node = new DiagramNavigator(diagram).nodeWithLabel("CompositeProcessor1").getNode();
            assertThat(node.getStyle()).isInstanceOf(RectangularNodeStyle.class)
                    .asInstanceOf(RECTANGULAR_NODE_STYLE)
                    .hasBackground("#F0F0F0");
            nodeId.set(node.getId());
            labelId.set(node.getInsideLabel().getId());
        });

        Runnable enableANodeStyleCustomization = () -> {
            BiFunction<IEditingContext, IInput, IPayload> updatePowerStatus = (editingContext, input) -> {
                this.objectSearchService.getObject(editingContext, FlowIdentifier.FLOW_COMPOSITE_PROCESSOR_1)
                        .filter(CompositeProcessor.class::isInstance)
                        .map(CompositeProcessor.class::cast)
                        .ifPresent(compositeProcessor -> {
                            compositeProcessor.setStatus(FlowElementStatus.INACTIVE);
                        });
                return new ExecuteEditingContextFunctionSuccessPayload(input.id(), null);
            };

            var inputId = UUID.randomUUID();
            var input = new ExecuteEditingContextFunctionInput(inputId, FlowIdentifier.FLOW_EDITING_CONTEXT_ID, updatePowerStatus, new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, FlowIdentifier.FLOW_EDITING_CONTEXT_ID, () -> inputId));
            this.executeEditingContextFunctionRunner.execute(input);
        };

        Consumer<Object> verifyTheNodeCustomizationApplied = assertRefreshedDiagramThat(newDiagram -> {
            var node = new DiagramNavigator(newDiagram).nodeWithId(nodeId.get()).getNode();
            assertThat(node.getStyle()).isInstanceOf(RectangularNodeStyle.class)
                    .asInstanceOf(RECTANGULAR_NODE_STYLE)
                    .hasBackground("#FFFFFF");
        });

        Runnable editBackgroundNodeAppearance = () -> {
            var appearanceInput = new RectangularNodeAppearanceInput("red", null, 5, null, null);
            this.editRectangularNodeAppearanceExecutor.execute(FlowIdentifier.FLOW_EDITING_CONTEXT_ID, diagramId.get(), List.of(nodeId.get()), appearanceInput).isSuccess();
        };

        Consumer<Object> verifyAppearanceOverridesNodeCustomization = assertRefreshedDiagramThat(newDiagram -> {
            var node = new DiagramNavigator(newDiagram).nodeWithId(nodeId.get()).getNode();
            assertThat(node.getStyle()).isInstanceOf(RectangularNodeStyle.class)
                    .asInstanceOf(RECTANGULAR_NODE_STYLE)
                    .hasBackground("red");
        });

        StepVerifier.create(flux)
                .consumeNextWith(initialDiagram)
                .then(enableANodeStyleCustomization)
                .consumeNextWith(verifyTheNodeCustomizationApplied)
                .then(editBackgroundNodeAppearance)
                .consumeNextWith(verifyAppearanceOverridesNodeCustomization)
                .thenCancel()
                .verify(Duration.ofSeconds(10));
    }
}
