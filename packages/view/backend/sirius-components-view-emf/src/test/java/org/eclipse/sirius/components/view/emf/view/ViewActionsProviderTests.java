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
package org.eclipse.sirius.components.view.emf.view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.ArrangeLayoutDirection;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.Action;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionPredicate;
import org.eclipse.sirius.components.view.emf.ViewAQLInterpreterFactory;
import org.eclipse.sirius.components.view.emf.diagram.ViewActionsProvider;
import org.eclipse.sirius.components.view.emf.diagram.api.IViewDiagramDescriptionSearchService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

/**
 * {@link ViewActionsProvider}-related tests.
 *
 * @author arichard
 */
public class ViewActionsProviderTests {

    private static final String EDITING_CONTEXT_ID = "editingContextId";
    private static final String NODE_DESCRIPTION_ID = "nodeDescriptionId";
    private static final String NODE_1_ID = "node1";
    private static final String OBJECT_1_ID = "object1";
    private static final String REPRESENTATION_ID = "representationId";
    private static final String ACTION_ICON_URL = "iconURL";
    private static final String ACTION_LABEL = "label";
    private static final String ACTION_NAME = "name";
    private static final String TRANSPARENT_COLOR_NAME = "transparent";
    private static final String BLACK_COLOR_NAME = "black";

    @Test
    @DisplayName("Given a valid context, when the ViewActionsProvider is executed, then an Action is returned.")
    public void testViewActionsProvider() {
        var object1 = new Object();
        NodeDescription viewNodeDescription = this.createViewNodeDescription();
        DiagramDescription viewDiagramDescription = this.createViewDiagramDescription(List.of(viewNodeDescription));
        var id = UUID.nameUUIDFromBytes(ACTION_NAME.getBytes()).toString();
        var action = this.createAction(id);
        var nodeDescription = this.createNodeDescription(NODE_DESCRIPTION_ID, List.of(action));
        var diagramDescription = this.createDiagramDescription(REPRESENTATION_ID, List.of(nodeDescription));
        var node1 = this.createNode(NODE_1_ID, NODE_DESCRIPTION_ID, OBJECT_1_ID);

        var viewRepresentationDescriptionPredicate = new IViewRepresentationDescriptionPredicate() {

            @Override
            public boolean test(IRepresentationDescription t) {
                return true;
            }
        };
        var viewDiagramDescriptionSearchService = new IViewDiagramDescriptionSearchService() {

            @Override
            public Optional<NodeDescription> findViewNodeDescriptionById(IEditingContext editingContext, String edgeDescriptionId) {
                return Optional.of(viewNodeDescription);
            }

            @Override
            public Optional<EdgeDescription> findViewEdgeDescriptionById(IEditingContext editingContext, String edgeDescriptionId) {
                return Optional.empty();
            }

            @Override
            public Optional<DiagramDescription> findById(IEditingContext editingContext, String diagramDescriptionId) {
                return Optional.of(viewDiagramDescription);
            }
        };
        var aqlInterpreterFactory = new ViewAQLInterpreterFactory(List.of(), new StaticApplicationContext());

        var actionsProvider = new ViewActionsProvider(viewRepresentationDescriptionPredicate, viewDiagramDescriptionSearchService, aqlInterpreterFactory);

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(actionsProvider.canHandle(diagramDescription)).isTrue();

        var actions = actionsProvider.handle(object1, node1, nodeDescription, diagramDescription, editingContext);

        assertThat(actions).hasSize(1);

        var actionDTO = new org.eclipse.sirius.components.collaborative.diagrams.dto.Action(id, ACTION_LABEL, List.of(ACTION_ICON_URL));
        assertThat(actions).contains(actionDTO);
    }

    private Action createViewAction() {
        var action = DiagramFactory.eINSTANCE.createAction();
        action.setName(ACTION_NAME);
        action.setLabelExpression(ACTION_LABEL);
        action.setIconURLsExpression(ACTION_ICON_URL);
        return action;
    }

    private NodeDescription createViewNodeDescription() {
        var nodeDescription = DiagramFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName(NODE_DESCRIPTION_ID);
        nodeDescription.getActions().add(this.createViewAction());
        return nodeDescription;
    }

    private DiagramDescription createViewDiagramDescription(List<NodeDescription> nodeDescriptions) {
        var diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName(REPRESENTATION_ID);
        diagramDescription.getNodeDescriptions().addAll(nodeDescriptions);
        return diagramDescription;
    }

    private org.eclipse.sirius.components.diagrams.actions.Action createAction(String actionId) {
        return this.createAction(actionId, variableManager -> new Success(ChangeKind.SEMANTIC_CHANGE, Map.of()));
    }

    private org.eclipse.sirius.components.diagrams.actions.Action createAction(String actionId, Function<VariableManager, IStatus> handler) {
        return org.eclipse.sirius.components.diagrams.actions.Action.newAction(actionId)
                .handler(handler)
                .iconURL(List.of(ACTION_ICON_URL))
                .label(ACTION_LABEL)
                .build();
    }

    private Node createNode(String nodeId, String nodeDescriptionId, String targetObjectId) {
        var labelStyle = LabelStyle.newLabelStyle()
                .color("#000001")
                .fontSize(16)
                .iconURL(List.of())
                .background(TRANSPARENT_COLOR_NAME)
                .borderColor(BLACK_COLOR_NAME)
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();
        var label = InsideLabel.newLabel(UUID.randomUUID().toString())
                .text("text")
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .style(labelStyle)
                .isHeader(false)
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.NEVER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        return Node.newNode(nodeId)
                .type(NodeType.NODE_RECTANGLE)
                .targetObjectId(targetObjectId)
                .targetObjectKind("")
                .targetObjectLabel("")
                .descriptionId(nodeDescriptionId)
                .insideLabel(label)
                .style(RectangularNodeStyle.newRectangularNodeStyle()
                        .borderColor("#000000")
                        .borderSize(1)
                        .borderStyle(LineStyle.Solid)
                        .background("#FFFFFF")
                        .build())
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .borderNodes(List.of())
                .childNodes(List.of())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .build();
    }

    private org.eclipse.sirius.components.diagrams.description.NodeDescription createNodeDescription(String nodeDescriptionId, List<org.eclipse.sirius.components.diagrams.actions.Action> actions) {
        var styleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "")
                .fontSizeProvider(variableManager -> 0)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .backgroundProvider(variableManager -> TRANSPARENT_COLOR_NAME)
                .borderColorProvider(variableManager -> BLACK_COLOR_NAME)
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .maxWidthProvider(variableManager -> null)
                .build();

        var insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("insideLabelDescription")
                .idProvider(vm -> "")
                .styleDescriptionProvider(vm -> styleDescription)
                .textProvider(vm -> "")
                .isHeaderProvider(vm -> false)
                .headerSeparatorDisplayModeProvider(vm -> HeaderSeparatorDisplayMode.NEVER)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        return org.eclipse.sirius.components.diagrams.description.NodeDescription.newNodeDescription(nodeDescriptionId)
                .borderNodeDescriptions(List.of())
                .childNodeDescriptions(List.of())
                .childrenLayoutStrategyProvider(vm -> new FreeFormLayoutStrategy())
                .deleteHandler(vm -> new Success())
                .insideLabelDescription(insideLabelDescription)
                .labelEditHandler((vm, newLabel) -> new Success())
                .reusedBorderNodeDescriptionIds(List.of())
                .reusedChildNodeDescriptionIds(List.of())
                .semanticElementsProvider(vm -> List.of())
                .styleProvider(vm -> null)
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .targetObjectLabelProvider(vm -> "")
                .typeProvider(vm -> "")
                .actionsProvider(vm -> actions)
                .build();
    }

    private org.eclipse.sirius.components.diagrams.description.DiagramDescription createDiagramDescription(String representationId, List<org.eclipse.sirius.components.diagrams.description.NodeDescription> nodeDescriptions) {
        return org.eclipse.sirius.components.diagrams.description.DiagramDescription.newDiagramDescription(representationId)
                .arrangeLayoutDirection(ArrangeLayoutDirection.DOWN)
                .autoLayout(false)
                .canCreatePredicate(vm -> true)
                .dropHandler(vm -> new Success())
                .dropNodeHandler(vm -> new Success())
                .edgeDescriptions(List.of())
                .iconURLsProvider(vm -> List.of())
                .label("")
                .labelProvider(vm -> "")
                .nodeDescriptions(nodeDescriptions)
                .palettes(List.of())
                .targetObjectIdProvider(vm -> "")
                .build();
    }
}
