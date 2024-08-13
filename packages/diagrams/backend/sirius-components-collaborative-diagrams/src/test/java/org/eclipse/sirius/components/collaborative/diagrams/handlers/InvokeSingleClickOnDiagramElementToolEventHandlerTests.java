/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramQueryService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolSuccessPayload;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariableType;
import org.eclipse.sirius.components.collaborative.diagrams.messages.ICollaborativeDiagramMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.CollapsingState;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.ViewModifier;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.SingleClickOnDiagramElementTool;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.junit.jupiter.api.Test;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Used to test the execution of a single click on diagram element tool.
 *
 * @author sbegaudeau
 */
public class InvokeSingleClickOnDiagramElementToolEventHandlerTests {

    private static final String NAME_VARIABLE_VALUE = "nameVariableValue";

    private static final String NAME_VARIABLE = "name";

    private static final String SELECTED_OBJECTS = "selectedObjects";

    private static final String SELECTED_OBJECT = "selectedObject";

    private static final String DIALOG_DESCRIPTION_ID = "dialogDescriptionId";

    private static final String DIAGRAM_ID = "diagramId";
    private static final String EDGE_1_ID = "edge1";
    private static final String EDGE_DESCRIPTION_ID = "edgeDescriptionId";
    private static final String EDITING_CONTEXT_ID = "editingContextId";
    private static final String NODE_DESCRIPTION_ID = "nodeDescriptionId";
    private static final String NODE_1_ID = "node1";
    private static final String OBJECT_1_ID = "object1";

    private static final String OBJECT_2_ID = "object2";

    private static final String OBJECT_3_ID = "object3";

    private static final String LINK_1_ID = "link1";
    private static final String REPRESENTATION_ID = "representationId";

    private static final List<ToolVariable> VARIABLES = List.of();

    private static final String TOOL_ID = "toolId";
    private static final String TOOL_IMAGE_URL = "imageURL";
    private static final String TOOL_LABEL = "label";
    private static final String TRANSPARENT_COLOR_NAME = "transparent";
    private static final String BLACK_COLOR_NAME = "black";

    @Test
    public void testInvokeToolOnDiagram() {
        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(new Object());
            }
        };

        var diagramQueryService = new IDiagramQueryService.NoOp();

        var tool = this.createTool(TOOL_ID, true, List.of());

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, DIAGRAM_ID, TOOL_ID, 5.0, 8.0, VARIABLES);

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(DIAGRAM_ID));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InvokeSingleClickOnDiagramElementToolSuccessPayload.class);
    }

    @Test
    public void testInvokeToolOnNode() {
        var object1 = new Object();

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(object1);
            }
        };

        var nodeDescription = this.createNodeDescription(NODE_DESCRIPTION_ID);
        var node1 = this.createNode(NODE_1_ID, NODE_DESCRIPTION_ID, OBJECT_1_ID);

        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
                return Optional.of(node1);
            }
        };

        var tool = this.createTool(TOOL_ID, false, List.of(nodeDescription));

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, NODE_1_ID, TOOL_ID, 5.0, 8.0, VARIABLES);

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(DIAGRAM_ID));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InvokeSingleClickOnDiagramElementToolSuccessPayload.class);
    }

    @Test
    public void testInvokeToolOnNodeWithDialogDescription() {
        var object1 = new Object();
        var object2 = new Object();
        var object3 = new Object();

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                Object object = switch (objectId) {
                    case OBJECT_1_ID -> object1;
                    case OBJECT_2_ID -> object2;
                    case OBJECT_3_ID -> object3;
                    default -> null;
                };
                return Optional.of(object);
            }
        };

        var nodeDescription = this.createNodeDescription(NODE_DESCRIPTION_ID);
        var node1 = this.createNode(NODE_1_ID, NODE_DESCRIPTION_ID, OBJECT_1_ID);

        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
                return Optional.of(node1);
            }
        };

        final Map<String, Object> computedVariables = new HashMap<>();

        Function<VariableManager, IStatus> toolHandler = variableManager -> {
            computedVariables.putAll(variableManager.getVariables());
            return new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
        };

        var tool = this.createTool(TOOL_ID, false, List.of(nodeDescription), DIALOG_DESCRIPTION_ID, toolHandler);

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var variables = List.of(new ToolVariable(SELECTED_OBJECT, OBJECT_2_ID, ToolVariableType.OBJECT_ID), new ToolVariable(NAME_VARIABLE, NAME_VARIABLE_VALUE, ToolVariableType.STRING),
                new ToolVariable(SELECTED_OBJECTS, OBJECT_1_ID + "," + OBJECT_2_ID + "," + OBJECT_3_ID, ToolVariableType.OBJECT_ID_ARRAY));

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, NODE_1_ID, TOOL_ID, 5.0, 8.0, variables);

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(DIAGRAM_ID));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InvokeSingleClickOnDiagramElementToolSuccessPayload.class);
        assertThat(computedVariables).containsEntry(SELECTED_OBJECT, object2).containsEntry(NAME_VARIABLE, NAME_VARIABLE_VALUE).containsEntry(SELECTED_OBJECTS, List.of(object1, object2, object3));
    }

    @Test
    public void testInvokeToolOnWrongNode() {
        var object1 = new Object();

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(object1);
            }
        };

        var nodeDescription = this.createNodeDescription(NODE_DESCRIPTION_ID);
        var node1 = this.createNode(NODE_1_ID, NODE_DESCRIPTION_ID, OBJECT_1_ID);

        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Node> findNodeById(Diagram diagram, String nodeId) {
                if (nodeId.equals(node1.getId())) {
                    return Optional.of(node1);
                }
                return Optional.empty();
            }
        };

        var tool = this.createTool(TOOL_ID, false, List.of(nodeDescription));

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, "anotherNodeId", TOOL_ID, 5.0, 8.0, VARIABLES);

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

    @Test
    public void testInvokeToolOnEdge() {
        var link1 = new Object();

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(link1);
            }
        };

        var edgeDescription = this.createEdgeDescription(EDGE_DESCRIPTION_ID);
        var edge1 = this.createEdge(EDGE_1_ID, EDGE_DESCRIPTION_ID, LINK_1_ID);

        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
                if (edgeId.equals(edge1.getId())) {
                    return Optional.of(edge1);
                }
                return Optional.empty();
            }
        };

        String selectionEntryId = "entryId";
        String selectionEntryLabel = "entry label";
        String selectionEntryKind = "entryKind";
        var expectedSelectionEntry = new WorkbenchSelectionEntry(selectionEntryId, selectionEntryLabel, selectionEntryKind);
        var expectedWorkbenchSelection = new WorkbenchSelection(List.of(expectedSelectionEntry));

        var tool = this.createTool(TOOL_ID, false, List.of(), null, variableManager -> {
            var newSelectionEntry = new WorkbenchSelectionEntry(selectionEntryId, selectionEntryLabel, selectionEntryKind);
            var newWorkbenchSelection = new WorkbenchSelection(List.of(newSelectionEntry));
            var success = new Success();
            success.getParameters().put(Success.NEW_SELECTION, newWorkbenchSelection);
            return success;
        });

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, EDGE_1_ID, TOOL_ID, 5.0, 8.0, VARIABLES);

        IEditingContext editingContext = () -> EDITING_CONTEXT_ID;

        assertThat(handler.canHandle(input)).isTrue();

        Many<ChangeDescription> changeDescriptionSink = Sinks.many().unicast().onBackpressureBuffer();
        One<IPayload> payloadSink = Sinks.one();

        IDiagramContext diagramContext = new DiagramContext(new TestDiagramBuilder().getDiagram(DIAGRAM_ID));
        handler.handle(payloadSink, changeDescriptionSink, editingContext, diagramContext, input);

        ChangeDescription changeDescription = changeDescriptionSink.asFlux().blockFirst();
        assertThat(changeDescription.getKind()).isEqualTo(ChangeKind.SEMANTIC_CHANGE);

        IPayload payload = payloadSink.asMono().block();
        assertThat(payload).isInstanceOf(InvokeSingleClickOnDiagramElementToolSuccessPayload.class);

        var workbenchSelection = ((InvokeSingleClickOnDiagramElementToolSuccessPayload) payload).newSelection();
        assertThat(workbenchSelection).isNotNull();
        assertThat(workbenchSelection).isEqualTo(expectedWorkbenchSelection);
    }

    @Test
    public void testInvokeToolOnWrongEdge() {
        var link1 = new Object();

        var objectService = new IObjectService.NoOp() {
            @Override
            public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
                return Optional.of(link1);
            }
        };

        var edgeDescription = this.createEdgeDescription(EDGE_DESCRIPTION_ID);
        var edge1 = this.createEdge(EDGE_1_ID, EDGE_DESCRIPTION_ID, LINK_1_ID);

        var diagramQueryService = new IDiagramQueryService.NoOp() {
            @Override
            public Optional<Edge> findEdgeById(Diagram diagram, String edgeId) {
                if (edgeId.equals(edge1.getId())) {
                    return Optional.of(edge1);
                }
                return Optional.empty();
            }
        };

        var tool = this.createTool(TOOL_ID, false, List.of(edgeDescription));

        var toolService = new IToolService.NoOp() {
            @Override
            public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
                return Optional.of(tool);
            }
        };

        var handler = new InvokeSingleClickOnDiagramElementToolEventHandler(objectService, diagramQueryService, toolService, new ICollaborativeDiagramMessageService.NoOp(), new SimpleMeterRegistry());

        var input = new InvokeSingleClickOnDiagramElementToolInput(UUID.randomUUID(), EDITING_CONTEXT_ID, REPRESENTATION_ID, "anotherEdgeId", TOOL_ID, 5.0, 8.0, VARIABLES);

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

    private SingleClickOnDiagramElementTool createTool(String toolId, boolean appliesToDiagramRoot, List<IDiagramElementDescription> diagramElementsDescriptions) {
        return this.createTool(toolId, appliesToDiagramRoot, diagramElementsDescriptions, null, variableManager -> new Success(ChangeKind.SEMANTIC_CHANGE, Map.of()));
    }

    private SingleClickOnDiagramElementTool createTool(String toolId, boolean appliesToDiagramRoot, List<IDiagramElementDescription> diagramElementsDescriptions, String dialogDescriptionId, Function<VariableManager, IStatus> handler) {
        return SingleClickOnDiagramElementTool.newSingleClickOnDiagramElementTool(toolId)
                .label(TOOL_LABEL)
                .iconURL(List.of(TOOL_IMAGE_URL))
                .targetDescriptions(diagramElementsDescriptions)
                .dialogDescriptionId(dialogDescriptionId)
                .handler(handler)
                .appliesToDiagramRoot(appliesToDiagramRoot)
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

    private NodeDescription createNodeDescription(String nodeDescriptionId) {
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
                .build();
    }

    private Edge createEdge(String edgeId, String edgeDescriptionId, String targetObjectId) {
        var labelStyle = LabelStyle.newLabelStyle()
                .color("#000002")
                .fontSize(14)
                .iconURL(List.of())
                .background(TRANSPARENT_COLOR_NAME)
                .borderColor(BLACK_COLOR_NAME)
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        var label = Label.newLabel(UUID.randomUUID().toString())
                .style(labelStyle)
                .text("text")
                .type("labelType")
                .build();

        var edgeStyle = EdgeStyle.newEdgeStyle()
                .color("#000000")
                .lineStyle(LineStyle.Solid)
                .size(1)
                .sourceArrow(ArrowStyle.None)
                .targetArrow(ArrowStyle.InputArrow)
                .build();

        return Edge.newEdge(edgeId)
                .type("edge:straight")
                .sourceId("")
                .targetId("")
                .beginLabel(label)
                .centerLabel(label)
                .endLabel(label)
                .descriptionId(edgeDescriptionId)
                .style(edgeStyle)
                .targetObjectId(targetObjectId)
                .targetObjectKind("")
                .targetObjectLabel("")
                .modifiers(Set.of())
                .state(ViewModifier.Normal)
                .build();
    }

    private EdgeDescription createEdgeDescription(String edgeDescriptionId) {
        var edgeStyle = EdgeStyle.newEdgeStyle()
                .color("#000003")
                .lineStyle(LineStyle.Dash)
                .sourceArrow(ArrowStyle.Diamond)
                .targetArrow(ArrowStyle.Diamond)
                .build();

        return EdgeDescription.newEdgeDescription(edgeDescriptionId)
                .deleteHandler(vm -> new Success())
                .labelEditHandler((vm, edgeKind, newLabel) -> new Success())
                .semanticElementsProvider(vm -> List.of(new Object()))
                .sourceNodeDescriptions(List.of())
                .sourceNodesProvider(vm -> List.of())
                .styleProvider(vm -> edgeStyle)
                .targetNodeDescriptions(List.of())
                .targetNodesProvider(vm -> List.of())
                .targetObjectIdProvider(vm -> "")
                .targetObjectKindProvider(vm -> "")
                .targetObjectLabelProvider(vm -> "")
                .build();
    }
}
