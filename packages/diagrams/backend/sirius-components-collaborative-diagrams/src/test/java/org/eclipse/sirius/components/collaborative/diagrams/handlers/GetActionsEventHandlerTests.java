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
package org.eclipse.sirius.components.collaborative.diagrams.handlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.collection;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IActionsProvider;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramDescriptionService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.GetActionsSuccessPayload;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.ArrangeLayoutDirection;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
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
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.actions.Action;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * {@link GetActionsEventHandler}-related tests.
 *
 * @author arichard
 */
public class GetActionsEventHandlerTests {

    private static final String DIAGRAM_ID = "diagramId";
    private static final String EDITING_CONTEXT_ID = "editingContextId";
    private static final String NODE_DESCRIPTION_ID = "nodeDescriptionId";
    private static final String NODE_1_ID = "node1";
    private static final String OBJECT_1_ID = "object1";
    private static final String REPRESENTATION_ID = "representationId";
    private static final String ACTION_ID = "actionId";
    private static final String ACTION_ICON_URL = "iconURL";
    private static final String ACTION_LABEL = "label";
    private static final String TRANSPARENT_COLOR_NAME = "transparent";
    private static final String BLACK_COLOR_NAME = "black";


    @Test
    @DisplayName("Given a valid GetActionsInput, when the GetActionsEventHandler is executed, then an GetActionsSuccessPayload is returned.")
    public void testGetActions() {
        var object1 = new Object();
        var action = this.createAction(ACTION_ID);
        var nodeDescription = this.createNodeDescription(NODE_DESCRIPTION_ID, List.of(action));
        var diagramDescription = this.createDiagramDescription(REPRESENTATION_ID, List.of(nodeDescription));
        var node1 = this.createNode(NODE_1_ID, NODE_DESCRIPTION_ID, OBJECT_1_ID);

        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                return Optional.of(diagramDescription);
            }
        };
        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
                return Optional.of(node1);
            }
        };
        var diagramDescriptionService = new IDiagramDescriptionService.NoOp() {
            @Override
            public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
                return Optional.of(nodeDescription);
            }
        };
        var objectSearchService = new IObjectSearchService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(object1);
            }
        };
        var actionsProvider = new IActionsProvider() {
            @Override
            public List<org.eclipse.sirius.components.collaborative.diagrams.dto.Action> handle(Object targetElement, Object diagramElement, Object diagramElementDescription,
                    DiagramDescription diagramDescription, IEditingContext editingContext) {
                var actionDTO = org.eclipse.sirius.components.collaborative.diagrams.dto.Action.newAction(ACTION_ID)
                        .iconURL(List.of(ACTION_ICON_URL))
                        .label(ACTION_LABEL)
                        .build();
                return List.of(actionDTO);
            }

            @Override
            public boolean canHandle(DiagramDescription diagramDescription) {
                return true;
            }
        };

        var handler = new GetActionsEventHandler(representationDescriptionSearchService, diagramQueryService, diagramDescriptionService, objectSearchService, actionsProvider, new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new GetActionsInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, NODE_1_ID);

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(DIAGRAM_ID));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(GetActionsSuccessPayload.class);
        var actionAssert = assertThat(payload).extracting(p -> ((GetActionsSuccessPayload) p).actions(), collection(org.eclipse.sirius.components.collaborative.diagrams.dto.Action.class))
            .hasSize(1)
            .element(0);
        actionAssert.extracting(a -> a.id()).matches(id -> id.equals(ACTION_ID));
        actionAssert.extracting(a -> a.label()).matches(label -> label.equals(ACTION_LABEL));
        actionAssert.extracting(a -> a.iconURL()).matches(iconURL -> iconURL.contains(ACTION_ICON_URL));
    }

    @Test
    @DisplayName("Given a GetActionsInput with a wrong Node id, when the GetActionsEventHandler is executed, then an ErrorPayload is returned.")
    public void testGetActionsOnWrongNode() {
        var object1 = new Object();
        var action = this.createAction(ACTION_ID);
        var nodeDescription = this.createNodeDescription(NODE_DESCRIPTION_ID, List.of(action));
        var diagramDescription = this.createDiagramDescription(REPRESENTATION_ID, List.of(nodeDescription));
        var node1 = this.createNode(NODE_1_ID, NODE_DESCRIPTION_ID, OBJECT_1_ID);

        var representationDescriptionSearchService = new IRepresentationDescriptionSearchService.NoOp() {
            @Override
            public Optional<IRepresentationDescription> findById(IEditingContext editingContext, String representationDescriptionId) {
                return Optional.of(diagramDescription);
            }
        };
        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
                if (nodeId.equals(node1.getId())) {
                    return Optional.of(node1);
                }
                return Optional.empty();
            }
        };
        var diagramDescriptionService = new IDiagramDescriptionService.NoOp() {
            @Override
            public Optional<NodeDescription> findNodeDescriptionById(DiagramDescription diagramDescription, String nodeDescriptionId) {
                return Optional.of(nodeDescription);
            }
        };
        var objectSearchService = new IObjectSearchService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(object1);
            }
        };
        var actionsProvider = new IActionsProvider() {
            @Override
            public List<org.eclipse.sirius.components.collaborative.diagrams.dto.Action> handle(Object targetElement, Object diagramElement, Object diagramElementDescription,
                    DiagramDescription diagramDescription, IEditingContext editingContext) {
                var actionDTO = org.eclipse.sirius.components.collaborative.diagrams.dto.Action.newAction(ACTION_ID)
                        .iconURL(List.of(ACTION_ICON_URL))
                        .label(ACTION_LABEL)
                        .build();
                return List.of(actionDTO);
            }

            @Override
            public boolean canHandle(DiagramDescription diagramDescription) {
                return true;
            }
        };

        var handler = new GetActionsEventHandler(representationDescriptionSearchService, diagramQueryService, diagramDescriptionService, objectSearchService, actionsProvider, new ICollaborativeMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new GetActionsInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, "anotherNodeId");

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(DIAGRAM_ID));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.NOTHING);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(ErrorPayload.class);
    }

    private Action createAction(String actionId) {
        return this.createAction(actionId, variableManager -> new Success(ChangeKind.SEMANTIC_CHANGE, Map.of()));
    }

    private Action createAction(String actionId, Function<VariableManager, IStatus> handler) {
        return Action.newAction(actionId)
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
                .style(new TestDiagramBuilder().getRectangularNodeStyle())
                .childrenLayoutStrategy(new FreeFormLayoutStrategy())
                .borderNodes(List.of())
                .childNodes(List.of())
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .collapsingState(CollapsingState.EXPANDED)
                .build();
    }

    private NodeDescription createNodeDescription(String nodeDescriptionId, List<Action> actions) {
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

        return NodeDescription.newNodeDescription(nodeDescriptionId)
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

    private DiagramDescription createDiagramDescription(String representationId, List<NodeDescription> nodeDescriptions) {
        return DiagramDescription.newDiagramDescription(representationId)
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
