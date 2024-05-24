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
package org.eclipse.sirius.components.diagrams.renderer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.sirius.components.diagrams.ArrowStyle;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.EdgeStyle;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.InsideLabelLocation;
import org.eclipse.sirius.components.diagrams.LabelOverflowStrategy;
import org.eclipse.sirius.components.diagrams.LabelTextAlign;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.ViewModifier;
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
 * Tests for node visibility.
 *
 * @author tgiraudet
 */
public class DiagramElementChangeVisibilityTests {

    private static final String LABEL_TEXT = "Node";

    private static final String INSIDE_LABEL_ID = "insideLabelId";

    private static final int LABEL_FONT_SIZE = 40;

    private static final String COLOR = "#AFAFAF";

    private static final String NODE_IMAGE = "node:image";

    private static final String DIAGRAM_LABEL = "Diagram";

    private static final Function<VariableManager, String> TYPE_PROVIDER = variableManager -> NODE_IMAGE;

    private static final Function<VariableManager, Size> SIZE_PROVIDER = VariableManager -> Size.UNDEFINED;

    private static final List<String> OBJECT_IDS = List.of("First", "Second", "Third", "Fourth");

    private static final Function<VariableManager, INodeStyle> STYLE_PROVIDER = variableManager -> {
        return ImageNodeStyle.newImageNodeStyle()
                .imageURL("test")
                .scalingFactor(1)
                .build();
    };

    private static final Function<VariableManager, EdgeStyle> STYLE_EDGE_PROVIDER = variableManager -> {
        return EdgeStyle.newEdgeStyle()
                .color(COLOR)
                .lineStyle(LineStyle.Dash)
                .sourceArrow(ArrowStyle.Diamond)
                .targetArrow(ArrowStyle.Diamond)
                .build();

    };

    private NodeDescription createNodeDescription(String elementId, List<NodeDescription> borderNodes, List<NodeDescription> children) {
        String id = UUID.randomUUID().toString();

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            if (object instanceof String) {
                return id + "__" + object;
            }
            return null;
        };

        LabelStyleDescription labelStyleDescription = LabelStyleDescription.newLabelStyleDescription()
                .italicProvider(VariableManager -> true)
                .boldProvider(VariableManager -> true)
                .underlineProvider(VariableManager -> true)
                .strikeThroughProvider(VariableManager -> true)
                .colorProvider(VariableManager -> COLOR)
                .fontSizeProvider(variableManager -> LABEL_FONT_SIZE)
                .iconURLProvider(VariableManager -> List.of())
                .build();

        InsideLabelDescription insideLabelDescription = InsideLabelDescription.newInsideLabelDescription(UUID.randomUUID().toString())
                .idProvider(variableManager -> INSIDE_LABEL_ID)
                .textProvider(variableManager -> LABEL_TEXT)
                .styleDescriptionProvider(variableManager -> labelStyleDescription)
                .isHeaderProvider(vm -> false)
                .displayHeaderSeparatorProvider(vm -> false)
                .insideLabelLocation(InsideLabelLocation.TOP_CENTER)
                .overflowStrategy(LabelOverflowStrategy.NONE)
                .textAlign(LabelTextAlign.CENTER)
                .build();

        NodeDescription nodeDescription = NodeDescription.newNodeDescription(id)
                .typeProvider(TYPE_PROVIDER)
                .semanticElementsProvider(variableManager -> List.of(elementId))
                .targetObjectIdProvider(targetObjectIdProvider)
                .targetObjectKindProvider(variableManager -> "")
                .targetObjectLabelProvider(variableManager -> "")
                .insideLabelDescription(insideLabelDescription).styleProvider(STYLE_PROVIDER)
                .childrenLayoutStrategyProvider(variableManager -> new FreeFormLayoutStrategy())
                .sizeProvider(SIZE_PROVIDER)
                .borderNodeDescriptions(borderNodes)
                .childNodeDescriptions(children)
                .labelEditHandler((variableManager, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();
        return nodeDescription;
    }

    private EdgeDescription createEdgeDescription(NodeDescription sourceDescription, String sourceObjectId, NodeDescription targetDescription, String targetObjectId) {
        Function<VariableManager, List<Element>> sourceNodesProvider = variableManager -> {
            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            Map<Object, List<Element>> objectToNodes = optionalCache.map(DiagramRenderingCache::getObjectToNodes).orElse(new HashMap<>());

            List<Element> sourceNodes = objectToNodes.get(sourceObjectId).stream()
                    .filter(node -> ((NodeElementProps) node.getProps()).getDescriptionId().equals(sourceDescription.getId()))
                    .filter(Objects::nonNull)
                    .toList();

            return sourceNodes;
        };

        Function<VariableManager, List<Element>> targetNodesProvider = variableManager -> {
            var optionalCache = variableManager.get(DiagramDescription.CACHE, DiagramRenderingCache.class);
            Map<Object, List<Element>> objectToNodes = optionalCache.map(DiagramRenderingCache::getObjectToNodes).orElse(new HashMap<>());

            List<Element> targetNodes = objectToNodes.get(targetObjectId).stream()
                    .filter(node -> ((NodeElementProps) node.getProps()).getDescriptionId().equals(targetDescription.getId()))
                    .filter(Objects::nonNull)
                    .toList();

            return targetNodes;
        };

        EdgeDescription edgeDescription = EdgeDescription.newEdgeDescription(UUID.randomUUID().toString())
                .styleProvider(STYLE_EDGE_PROVIDER)
                .sourceNodeDescriptions(List.of(sourceDescription))
                .targetNodeDescriptions(List.of(targetDescription))
                .targetObjectIdProvider(TYPE_PROVIDER)
                .targetObjectKindProvider(TYPE_PROVIDER)
                .sourceNodesProvider(sourceNodesProvider)
                .targetNodesProvider(targetNodesProvider)
                .targetObjectLabelProvider(variableManager -> "")
                .semanticElementsProvider(variableManager -> List.of(new Object()))
                .labelEditHandler((variableManager, edgeLabelKind, newLabel) -> new Success())
                .deleteHandler(variableManager -> new Success())
                .build();

        return edgeDescription;
    }

    private Diagram createDiagram(Optional<Diagram> previousDiagram, List<NodeDescription> nodeDescriptions, List<EdgeDescription> edgeDescriptions) {
        DiagramDescription diagramDescription = DiagramDescription.newDiagramDescription(UUID.randomUUID().toString())
                .label("")
                .canCreatePredicate(variableManager -> true)
                .targetObjectIdProvider(variableManager -> "diagramTargetObjectId")
                .labelProvider(variableManager -> DIAGRAM_LABEL)
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
                .previousDiagram(previousDiagram)
                .operationValidator(new IOperationValidator.NoOp())
                .diagramEvents(List.of())
                .build();

        Element element = new Element(DiagramComponent.class, props);
        return new DiagramRenderer().render(element);
    }

    @Test
    public void testSimpleFadedElementRendering() {
        List<NodeDescription> nodeDescriptions = IntStream.range(0, 3)
                .mapToObj(i -> this.createNodeDescription(OBJECT_IDS.get(i), List.of(), List.of()))
                .toList();

        var edgeDescription = this.createEdgeDescription(nodeDescriptions.get(1), OBJECT_IDS.get(1), nodeDescriptions.get(2), OBJECT_IDS.get(2));
        var diagram = this.createDiagram(Optional.empty(), nodeDescriptions, List.of(edgeDescription));

        // Test: (1) (2) -a-> (3), fade 1 and a
        // Expected result: 1: Faded - 2: Normal - 3: Normal - a: Faded

        var node = Node.newNode(diagram.getNodes().get(0))
                .modifiers(Set.of(ViewModifier.Faded))
                .build();

        var edge = Edge.newEdge(diagram.getEdges().get(0))
                .modifiers(Set.of(ViewModifier.Faded))
                .build();

        String modifiedNodeId = node.getId();
        String modifiedEdgeId = edge.getId();

        List<Node> nodes = diagram.getNodes().stream()
                .filter(n -> !n.getId().equals(modifiedNodeId))
                .toList();
        nodes = new ArrayList<>(nodes);
        nodes.add(0, node);

        Diagram diagramTemp = Diagram.newDiagram(diagram)
                .nodes(nodes)
                .edges(List.of(edge))
                .build();

        Diagram newDiagram = this.createDiagram(Optional.of(diagramTemp), nodeDescriptions, List.of(edgeDescription));

        assertThat(newDiagram.getNodes()).filteredOn(n -> n.getId().equals(modifiedNodeId)).extracting(Node::getState).allMatch(s -> s == ViewModifier.Faded);
        assertThat(newDiagram.getNodes()).filteredOn(n -> !n.getId().equals(modifiedNodeId)).extracting(Node::getState).allMatch(s -> s == ViewModifier.Normal);
        assertThat(newDiagram.getEdges()).filteredOn(e -> e.getId().equals(modifiedEdgeId)).extracting(Edge::getState).allMatch(s -> s == ViewModifier.Faded);
    }

    @Test
    public DiagramTestData testHideSpreadToEdgesRendering() {
        // @formatter:off
        List<NodeDescription> nodeDescriptions = IntStream.range(0, 3)
                .mapToObj(i -> this.createNodeDescription(OBJECT_IDS.get(i), List.of(), List.of()))
                .collect(Collectors.toUnmodifiableList());

        var edgeDescription = this.createEdgeDescription(nodeDescriptions.get(1), OBJECT_IDS.get(1), nodeDescriptions.get(0), OBJECT_IDS.get(0));
        var edge2Description = this.createEdgeDescription(nodeDescriptions.get(1), OBJECT_IDS.get(1), nodeDescriptions.get(2), OBJECT_IDS.get(2));

        var diagram = this.createDiagram(Optional.empty(), nodeDescriptions, List.of(edgeDescription, edge2Description));

        // Test: (1) <-a- (2) -b-> (3), hide 3 then 2
        // Expected result: 1: Normal - 2: Normal - 3: Hidden - a: Normal - b: Hidden
        // Then expected result: 1: Normal - 2: Hidden - 3: Hidden - a: Hidden - b: Hidden

        var node = Node.newNode(diagram.getNodes().get(2))
                .modifiers(Set.of(ViewModifier.Hidden))
                .build();

        String modifiedNodeId = node.getId();

        List<Node> nodes = diagram.getNodes().stream().filter(n -> !n.getId().equals(modifiedNodeId)).toList();
        nodes = new ArrayList<>(nodes);
        nodes.add(2, node);

        Diagram diagramTemp = Diagram.newDiagram(diagram)
                .nodes(nodes)
                .build();

        Diagram newDiagram = this.createDiagram(Optional.of(diagramTemp), nodeDescriptions, List.of(edgeDescription, edge2Description));

        assertThat(newDiagram.getNodes()).filteredOn(n -> n.getId().equals(modifiedNodeId)).extracting(Node::getState).allMatch(s -> s == ViewModifier.Hidden);
        assertThat(newDiagram.getNodes()).filteredOn(n -> !n.getId().equals(modifiedNodeId)).extracting(Node::getState).allMatch(s -> s == ViewModifier.Normal);
        assertThat(newDiagram.getEdges()).filteredOn(e -> e.getSourceId().equals(modifiedNodeId) || e.getTargetId().equals(modifiedNodeId)).extracting(Edge::getState)
        .allMatch(s -> s == ViewModifier.Hidden);

        var node2 = Node.newNode(diagram.getNodes().get(1))
                .modifiers(Set.of(ViewModifier.Hidden))
                .build();

        var modifiedNodeIds = List.of(modifiedNodeId, node2.getId());

        nodes = newDiagram.getNodes().stream().filter(n -> !modifiedNodeIds.contains(n.getId())).toList();
        nodes = new ArrayList<>(nodes);
        nodes.add(0, node);
        nodes.add(1, node2);

        Diagram diagramTemp2 = Diagram.newDiagram(newDiagram)
                .nodes(nodes)
                .build();

        Diagram newDiagram2 = this.createDiagram(Optional.of(diagramTemp2), nodeDescriptions, List.of(edgeDescription, edge2Description));

        assertThat(newDiagram2.getNodes()).filteredOn(n -> modifiedNodeId.contains(n.getId())).extracting(Node::getState).allMatch(s -> s == ViewModifier.Hidden);
        assertThat(newDiagram2.getNodes()).filteredOn(n -> !modifiedNodeIds.contains(n.getId())).extracting(Node::getState).allMatch(s -> s == ViewModifier.Normal);
        assertThat(newDiagram2.getEdges()).filteredOn(e -> modifiedNodeIds.contains(e.getSourceId()) || modifiedNodeIds.contains(e.getTargetId())).extracting(Edge::getState)
                .allMatch(s -> s == ViewModifier.Hidden);

        return new DiagramTestData(diagram, nodeDescriptions, List.of(edgeDescription, edge2Description));
    }

    @Test
    public DiagramTestData testHideSpreadToChildrenRendering() {
        NodeDescription nodeDesc4 = this.createNodeDescription(OBJECT_IDS.get(3), List.of(), List.of());
        NodeDescription nodeDesc3 = this.createNodeDescription(OBJECT_IDS.get(2), List.of(), List.of());
        NodeDescription nodeDesc2 = this.createNodeDescription(OBJECT_IDS.get(1), List.of(nodeDesc4), List.of(nodeDesc3));
        NodeDescription nodeDesc = this.createNodeDescription(OBJECT_IDS.get(0), List.of(), List.of(nodeDesc2));
        List<NodeDescription> nodeDescriptions = List.of(nodeDesc);

        var diagram = this.createDiagram(Optional.empty(), nodeDescriptions, List.of());

        // Test: (1 (4)(2 (3))), hide 1
        // Expected result: all nodes are hidden

        var node = Node.newNode(diagram.getNodes().get(0))
                .modifiers(Set.of(ViewModifier.Hidden))
                .build();

        Diagram diagramTemp = Diagram.newDiagram(diagram)
                .nodes(List.of(node))
                .build();

        Diagram newDiagram = this.createDiagram(Optional.of(diagramTemp), nodeDescriptions, List.of());

        assertThat(this.getAllNodes(newDiagram.getNodes())).extracting(Node::getState).allMatch(state -> state == ViewModifier.Hidden);

        return new DiagramTestData(newDiagram, nodeDescriptions, List.of());
    }

    public List<Node> getAllNodes(List<Node> nodes) {
        var allNodes = new LinkedList<Node>();
        for (Node node : nodes) {
            var children = new LinkedList<Node>();
            allNodes.add(node);
            children.addAll(node.getBorderNodes());
            children.addAll(node.getChildNodes());
            allNodes.addAll(this.getAllNodes(children));
        }
        return allNodes;
    }

    @Test
    public void testRevealHiddenNodesSpreadingRendering() {
        // Test: (1 (4)(2 (3))), reveal 1
        // Result: all nodes become visible

        var returnedValue = this.testHideSpreadToEdgesRendering();
        Diagram diagram = returnedValue.getDiagram();
        List<NodeDescription> nodeDescriptions = returnedValue.getNodeDescriptions();

        var node = Node.newNode(diagram.getNodes().get(0))
                .modifiers(Set.of())
                .build();

        var diagramTemp = Diagram.newDiagram(diagram)
                .nodes(List.of(node))
                .build();

        var newDiagram = this.createDiagram(Optional.of(diagramTemp), nodeDescriptions, List.of());

        assertThat(this.getAllNodes(newDiagram.getNodes())).extracting(Node::getState).allMatch(state -> state == ViewModifier.Normal);
    }

    @Test
    public void testRevealHiddenEdgesSpreadingRendering() {
        // Test: (0) <-a- (1) -b-> (2), reveal 1 and 2
        // Result: all nodes and edges become visible

        var returnedValue = this.testHideSpreadToEdgesRendering();
        Diagram diagram = returnedValue.getDiagram();
        List<NodeDescription> nodeDescriptions = returnedValue.getNodeDescriptions();
        List<EdgeDescription> edgeDescriptions = returnedValue.getEdgeDescriptions();

        var node0 = Node.newNode(diagram.getNodes().get(0))
                .modifiers(Set.of())
                .build();

        var node1 = Node.newNode(diagram.getNodes().get(1))
                .modifiers(Set.of())
                .build();

        var diagramTemp = Diagram.newDiagram(diagram)
                .nodes(List.of(node0, node1, diagram.getNodes().get(2)))
                .build();


        var newDiagram = this.createDiagram(Optional.of(diagramTemp), nodeDescriptions, edgeDescriptions);

        assertThat(newDiagram.getNodes()).extracting(Node::getState).allMatch(state -> state == ViewModifier.Normal);
        assertThat(newDiagram.getEdges()).extracting(Edge::getState).allMatch(state -> state == ViewModifier.Normal);
    }

    @Test
    public void testModifierPriority() {
        NodeDescription nodeDesc = this.createNodeDescription(OBJECT_IDS.get(0), List.of(), List.of());
        EdgeDescription edgeDesc = this.createEdgeDescription(nodeDesc, OBJECT_IDS.get(0), nodeDesc, OBJECT_IDS.get(0));

        var diagram = this.createDiagram(Optional.empty(), List.of(nodeDesc), List.of(edgeDesc));

        Node node = Node.newNode(diagram.getNodes().get(0)).modifiers(Set.of(ViewModifier.Normal)).build();

        Edge edge = Edge.newEdge(diagram.getEdges().get(0)).modifiers(Set.of(ViewModifier.Normal)).build();

        Diagram diagramTemp = Diagram.newDiagram(diagram)
                .nodes(List.of(node))
                .edges(List.of(edge))
                .build();

        Diagram newDiagram = this.createDiagram(Optional.of(diagramTemp), List.of(nodeDesc), List.of(edgeDesc));

        assertThat(newDiagram.getNodes()).extracting(Node::getState).allMatch(state -> state == ViewModifier.Normal);
        assertThat(newDiagram.getEdges()).extracting(Edge::getState).allMatch(state -> state == ViewModifier.Normal);

        node = Node.newNode(diagram.getNodes().get(0)).modifiers(Set.of(ViewModifier.Normal, ViewModifier.Faded)).build();

        edge = Edge.newEdge(diagram.getEdges().get(0)).modifiers(Set.of(ViewModifier.Normal, ViewModifier.Faded)).build();

        diagramTemp = Diagram.newDiagram(diagram)
                .nodes(List.of(node))
                .edges(List.of(edge))
                .build();

        newDiagram = this.createDiagram(Optional.of(diagramTemp), List.of(nodeDesc), List.of(edgeDesc));

        assertThat(newDiagram.getNodes()).extracting(Node::getState).allMatch(state -> state == ViewModifier.Faded);
        assertThat(newDiagram.getEdges()).extracting(Edge::getState).allMatch(state -> state == ViewModifier.Faded);

        edge = Edge.newEdge(diagram.getEdges().get(0)).modifiers(Set.of(ViewModifier.Normal, ViewModifier.Faded, ViewModifier.Hidden)).build();

        diagramTemp = Diagram.newDiagram(newDiagram)
                .edges(List.of(edge))
                .build();

        newDiagram = this.createDiagram(Optional.of(diagramTemp), List.of(nodeDesc), List.of(edgeDesc));

        assertThat(newDiagram.getNodes()).extracting(Node::getState).allMatch(state -> state == ViewModifier.Faded);
        assertThat(newDiagram.getEdges()).extracting(Edge::getState).allMatch(state -> state == ViewModifier.Hidden);

        node = Node.newNode(diagram.getNodes().get(0)).modifiers(Set.of(ViewModifier.Normal, ViewModifier.Faded, ViewModifier.Hidden)).build();

        diagramTemp = Diagram.newDiagram(newDiagram)
                .nodes(List.of(node))
                .build();

        newDiagram = this.createDiagram(Optional.of(diagramTemp), List.of(nodeDesc), List.of(edgeDesc));

        assertThat(newDiagram.getNodes()).extracting(Node::getState).allMatch(state -> state == ViewModifier.Hidden);
        assertThat(newDiagram.getEdges()).extracting(Edge::getState).allMatch(state -> state == ViewModifier.Hidden);
    }
}
