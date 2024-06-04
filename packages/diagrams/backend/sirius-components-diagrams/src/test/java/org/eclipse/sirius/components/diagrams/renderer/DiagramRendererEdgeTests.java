/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo and others.
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
package org.eclipse.sirius.components.diagrams.renderer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.components.DiagramComponent;
import org.eclipse.sirius.components.diagrams.components.DiagramComponentProps;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.InsideLabelDescription;
import org.eclipse.sirius.components.diagrams.description.LabelStyleDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IOperationValidator;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the rendering of the nodes in diagrams.
 *
 * @author sbegaudeau
 */
public class DiagramRendererEdgeTests {

    private static final String EDGE_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final String DIAGRAM_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final String NODE_DESCRIPTION_ID1 = UUID.randomUUID().toString();

    private static final String NODE_DESCRIPTION_ID2 = UUID.randomUUID().toString();

    private static final String FIRST_OBJECT_ID = "First";

    private static final String SECOND_OBJECT_ID = "Second";

    /**
     * Creates a diagram with two nodes "First" and "Second" and with an edge between the two nodes.
     */
    @Test
    public void testSimpleEdgeRendering() {
        NodeDescription nodeDescription = this.getNodeDescription(NODE_DESCRIPTION_ID1);
        EdgeDescription edgeDescription = this.getEdgeDescription(nodeDescription, EDGE_DESCRIPTION_ID);

        Diagram diagram = this.renderDiagram(List.of(nodeDescription), List.of(edgeDescription));

        assertThat(diagram).isNotNull();
        assertThat(diagram.getId()).asString().isNotBlank();
        assertThat(diagram.getLabel()).isNotBlank();
        assertThat(diagram.getTargetObjectId()).isNotBlank();

        assertThat(diagram.getNodes()).hasSize(2);

        Node firstNode = diagram.getNodes().get(0);
        Node secondNode = diagram.getNodes().get(1);

        assertThat(diagram.getEdges()).hasSize(1);

        Edge edge = diagram.getEdges().get(0);
        assertThat(edge).extracting(Edge::getSourceId).isEqualTo(firstNode.getId());
        assertThat(edge).extracting(Edge::getTargetId).isEqualTo(secondNode.getId());
    }

    /**
     * Check edge between nodes (of a first mapping) that represents objects that are represented by another mapping. To
     * do so, this test creates a diagram with
     * <li>two nodes "First" and "Second" from a nodeDescriptionId1</li>
     * <li>two other nodes "First" and "Second" from a nodeDescriptionId2</li>
     * <li>an edge between nodes of a type nodeDescriptionId1</li>
     */
    @Test
    public void testEdgeRendering() {
        NodeDescription nodeDescription1 = this.getNodeDescription(NODE_DESCRIPTION_ID1);
        NodeDescription nodeDescription2 = this.getNodeDescription(NODE_DESCRIPTION_ID2);
        EdgeDescription edgeDescription = this.getEdgeDescription(nodeDescription1, EDGE_DESCRIPTION_ID);

        Diagram diagram = this.renderDiagram(List.of(nodeDescription1, nodeDescription2), List.of(edgeDescription));
        assertThat(diagram.getNodes()).hasSize(4);

        Node node1 = diagram.getNodes().get(0);
        Node node2 = diagram.getNodes().get(1);
        assertThat(diagram.getEdges()).hasSize(1);

        Edge edge = diagram.getEdges().get(0);
        assertThat(edge).extracting(Edge::getSourceId).isEqualTo(node1.getId());
        assertThat(edge).extracting(Edge::getTargetId).isEqualTo(node2.getId());
    }

    /**
     * Check that two edges with the same source and target nodes but from different edge description have distinct ids.
     */
    @Test
    public void testNoDuplicateEdgeId() {
        NodeDescription nodeDescription1 = this.getNodeDescription(NODE_DESCRIPTION_ID1);
        NodeDescription nodeDescription2 = this.getNodeDescription(NODE_DESCRIPTION_ID2);
        EdgeDescription edgeDescription1 = this.getEdgeDescription(nodeDescription1, UUID.randomUUID().toString());
        EdgeDescription edgeDescription2 = this.getEdgeDescription(nodeDescription1, UUID.randomUUID().toString());

        Diagram diagram = this.renderDiagram(List.of(nodeDescription1, nodeDescription2), List.of(edgeDescription1, edgeDescription2));

        assertThat(diagram.getEdges()).hasSize(2);
        assertThat(diagram.getEdges().get(0).getId()).isNotEqualTo(diagram.getEdges().get(1).getId());
    }

    private Diagram renderDiagram(List<NodeDescription> nodeDescriptions, List<EdgeDescription> edgeDescriptions) {
        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(DIAGRAM_DESCRIPTION_ID)
                .label("")
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .labelProvider(variableManager -> "Diagram")
                .nodeDescriptions(nodeDescriptions)
                .edgeDescriptions(edgeDescriptions)
                .palettes(List.of())
                .dropHandler(variableManager -> new Failure(""))
                .build();

        VariableManager variableManager = new VariableManager();
        DiagramComponentProps props = DiagramComponentProps.newDiagramComponentProps()
                .variableManager(variableManager)
                .diagramDescription(diagramDescription)
                .allDiagramDescriptions(List.of(diagramDescription))
                .viewCreationRequests(List.of())
                .viewDeletionRequests(List.of())
                .previousDiagram(Optional.empty())
                .operationValidator(new IOperationValidator.NoOp())
                .diagramEvents(List.of())
                .build();
        Element element = new Element(DiagramComponent.class, props);
        Diagram diagram = new DiagramRenderer().render(element);
        return diagram;
    }

    private NodeDescription getNodeDescription(String nodeDescriptionId) {
        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .colorProvider(variableManager -> "#000000")
                .fontSizeProvider(variableManager -> 16)
                .boldProvider(variableManager -> false)
                .italicProvider(variableManager -> false)
                .underlineProvider(variableManager -> false)
                .strikeThroughProvider(variableManager -> false)
                .iconURLProvider(variableManager -> List.of())
                .backgroundProvider(variableManager -> "transparent")
                .borderColorProvider(variableManager -> "black")
                .borderRadiusProvider(variableManager -> 0)
                .borderSizeProvider(variableManager -> 0)
                .borderStyleProvider(variableManager -> LineStyle.Solid)
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription("insideLabelDescriptionId")
                .idProvider(variableManager -> "insideLabelId")
                .textProvider(variableManager -> "Node")
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        Function<VariableManager, INodeStyle> nodeStyleProvider = variableManager -> RectangularNodeStyle.newRectangularNodeStyle()
                .background("")
                .borderColor("")
                .borderSize(0)
                .borderStyle(LineStyle.Solid)
                .build();

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object instanceof String) {
                return nodeDescriptionId + "__" + object;
            }
            return null;
        };

        return NodeDescription.newNodeDescription(nodeDescriptionId)
                .typeProvider(variableManager -> "")
                .semanticElementsProvider(variableManager -> List.of(FIRST_OBJECT_ID, SECOND_OBJECT_ID))
                .targetObjectIdProvider(targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription)
                .styleProvider(nodeStyleProvider)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(variableManager -> Size.UNDEFINED)
                .borderNodeDescriptions(new ArrayList<>())
                .childNodeDescriptions(new ArrayList<>())
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
    }

    private EdgeDescription getEdgeDescription(NodeDescription nodeDescription, String id) {
        Function<VariableManager, List<Element>> sourceNodesProvider = variableManager -> {
            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            Map<Object, List<Element>> objectToNodes = optionalCache.map(DiagramRenderingCache::getObjectToNodes).orElse(new HashMap<>());

            List<Element> sourceNodes = objectToNodes.get(FIRST_OBJECT_ID).stream()
                    .filter(node -> ((NodeElementProps) node.getProps()).getDescriptionId().equals(nodeDescription.getId()))
                    .filter(Objects::nonNull)
                    .toList();

            return sourceNodes;
        };

        Function<VariableManager, List<Element>> targetNodesProvider = variableManager -> {
            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            Map<Object, List<Element>> objectToNodes = optionalCache.map(DiagramRenderingCache::getObjectToNodes).orElse(new HashMap<>());

            List<Element> targetNodes = objectToNodes.get(SECOND_OBJECT_ID).stream()
                    .filter(node -> ((NodeElementProps) node.getProps()).getDescriptionId().equals(nodeDescription.getId()))
                    .filter(Objects::nonNull)
                    .toList();

            return targetNodes;
        };

        Function<VariableManager, EdgeStyle> edgeStyleProvider = variableManager -> {
            return EdgeStyle.newEdgeStyle()
                    .size(2)
                    .lineStyle(LineStyle.Dash_Dot)
                    .sourceArrow(ArrowStyle.InputArrowWithDiamond)
                    .targetArrow(ArrowStyle.None)
                    .color("rgb(1, 2, 3)")
                    .build();
        };

        Function<VariableManager, String> idProvider = variableManager -> {
            return variableManager.get(VariableManager.SELF, String.class).orElse(null);
        };

        return EdgeDescription.newEdgeDescription(id)
                .semanticElementsProvider(variableManager -> List.of(FIRST_OBJECT_ID))
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .sourceNodeDescriptions(List.of(nodeDescription))
                .targetNodeDescriptions(List.of(nodeDescription))
                .targetObjectIdProvider(idProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .styleProvider(edgeStyleProvider)
                .deleteHandler(variableManager -> new Failure(""))
                .labelEditHandler((variableManager, edgeLabelKind, newLabel) -> new Failure(""))
                .build();
    }
}
