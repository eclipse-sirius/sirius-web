/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.components.diagrams.layout.incremental;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.components.LabelType;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.LayoutOptionValues;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodePositionProvider;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link NodePositionProvider}.
 *
 * @author fbarbin
 * @author wpiers
 */
public class NodePositionProviderTests {

    private static final Size DEFAULT_NODE_SIZE = Size.of(150, 70);

    private static final Position ZERO_POSITION = Position.at(0, 0);

    private static final double START_X_WITHIN_PARENT = 75;

    private static final double START_Y_WITHIN_PARENT = 35;

    private static final double START_X_OUTSIDE_PARENT = 1001;

    private static final double START_Y_OUTSIDE_PARENT = 1001;

    @Test
    public void testDiagramSeveralNewNodesAtOnce() {
        List<NodeLayoutData> nodes = new ArrayList<>();
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);

        Optional<IDiagramEvent> optionalDiagramElementEvent = Optional.of(new SinglePositionEvent(ZERO_POSITION));
        Position nextPosition = nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        // we pin the node so the next one gets positioned after
        // this is what happens in the incremental layout
        nodeLayoutData.setPinned(true);
        assertThat(nextPosition).isEqualTo(ZERO_POSITION);

        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData2);
        nextPosition = nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData2);
        assertThat(nextPosition).isEqualTo(Position.at(Double.valueOf(0), Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30)));
    }

    @Test
    public void testDiagramNewNodesOneByOne() {
        List<NodeLayoutData> nodes = new ArrayList<>();
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);

        Optional<IDiagramEvent> optionalDiagramElementEvent = Optional.of(new SinglePositionEvent(ZERO_POSITION));
        Position nextPosition = nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).isEqualTo(Position.at(0, 0));

        List<NodeLayoutData> childNodes = new ArrayList<>();
        nodeLayoutData.setChildrenNodes(childNodes);
        // Test creation of a new node at a given position within parent (creation tool)
        Position startingPosition = Position.at(START_X_WITHIN_PARENT, START_Y_WITHIN_PARENT);
        nodePositionProvider = new NodePositionProvider();
        NodeLayoutData childLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE);
        childNodes.add(childLayoutData);

        Optional<IDiagramEvent> optionalEventInside = Optional.of(new SinglePositionEvent(startingPosition));
        nextPosition = nodePositionProvider.getPosition(optionalEventInside, childLayoutData);
        assertThat(nextPosition).isEqualTo(startingPosition);

        // Test creation of a new node at a given position outside parent (creation tool)
        Position startingPositionOutside = Position.at(START_X_OUTSIDE_PARENT, START_Y_OUTSIDE_PARENT);
        nodePositionProvider = new NodePositionProvider();
        childLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE);
        childNodes.add(nodeLayoutData);

        Optional<IDiagramEvent> optionalEventOutside = Optional.of(new SinglePositionEvent(startingPositionOutside));
        nextPosition = nodePositionProvider.getPosition(optionalEventOutside, childLayoutData);
        assertThat(nextPosition).isEqualTo(nodePositionProvider.getDefaultPosition(childLayoutData));
    }

    @Test
    public void testNodeSeveralNewNodesAtOnce() {
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData parentNodeLayoutData = this.createNodeLayoutData(ZERO_POSITION, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(List.of(parentNodeLayoutData));
        List<NodeLayoutData> nodes = new ArrayList<>();
        parentNodeLayoutData.setChildrenNodes(nodes);

        Optional<IDiagramEvent> optionalEvent = Optional.of(new SinglePositionEvent(ZERO_POSITION));

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        Position nextPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        // we pin the node so the next one gets positioned after
        // this is what happens in the incremental layout
        nodeLayoutData.setPinned(true);
        assertThat(nextPosition).isEqualTo(ZERO_POSITION);

        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nextPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        assertThat(nextPosition).isEqualTo(Position.at(0, Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30)));
    }

    @Test
    public void testNodeNewNodesOneByOne() {
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData parentNodeLayoutData = this.createNodeLayoutData(ZERO_POSITION, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(List.of(parentNodeLayoutData));
        List<NodeLayoutData> nodes = new ArrayList<>();
        parentNodeLayoutData.setChildrenNodes(nodes);

        Optional<IDiagramEvent> optionalEvent = Optional.of(new SinglePositionEvent(ZERO_POSITION));

        NodeLayoutData firstChild = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(firstChild);
        Position nextPosition = nodePositionProvider.getPosition(optionalEvent, firstChild);
        firstChild.setPosition(nextPosition);
        assertThat(nextPosition).isEqualTo(ZERO_POSITION);

        NodeLayoutData secondChild = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(secondChild);
        nextPosition = nodePositionProvider.getPosition(optionalEvent, secondChild);
        secondChild.setPosition(nextPosition);
        assertThat(nextPosition).isEqualTo(ZERO_POSITION);

        // Test creation of a new node at a given position within parent (creation tool)
        nodePositionProvider = new NodePositionProvider();
        NodeLayoutData thirdChild = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        Position startingPosition = Position.at(START_X_WITHIN_PARENT, START_Y_WITHIN_PARENT);
        nodes.add(thirdChild);

        nextPosition = nodePositionProvider.getPosition(Optional.of(new SinglePositionEvent(startingPosition)), thirdChild);
        assertThat(nextPosition).isEqualTo(Position.at(START_X_WITHIN_PARENT, START_Y_WITHIN_PARENT));

        // Test creation of a new node at a given position outside parent (creation tool)
        NodeLayoutData fourthChild = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        Position startingPositionOutside = Position.at(START_X_OUTSIDE_PARENT, START_Y_OUTSIDE_PARENT);
        nodes.add(fourthChild);

        nodePositionProvider = new NodePositionProvider();
        nextPosition = nodePositionProvider.getPosition(Optional.of(new SinglePositionEvent(startingPositionOutside)), fourthChild);
        assertThat(nextPosition).isEqualTo(nodePositionProvider.getDefaultPosition(fourthChild));
    }

    @Test
    public void testMoveNodeEvent() {
        Position nodePosition1 = Position.at(100, 100);
        Position nodePosition2 = Position.at(200, 300);
        List<NodeLayoutData> nodes = new ArrayList<>();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(nodePosition1, Size.of(200, 100), diagramLayoutData, NodeType.NODE_RECTANGLE);
        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(nodePosition2, Size.of(200, 100), diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);
        nodes.add(nodeLayoutData2);
        Position newNodePosition = Position.at(300, 300);
        NodePositionProvider nodePositionProvider = new NodePositionProvider();

        Optional<IDiagramEvent> optionalEvent = Optional.of(new MoveEvent(nodeLayoutData.getId(), newNodePosition));
        Position newPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        Position newPosition2 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData2);
        assertThat(newPosition).isEqualTo(newNodePosition);
        assertThat(newPosition2).isEqualTo(nodePosition2);
    }

    @Test
    public void testNWResizeNodeEvent() {
        Position nodePosition1 = Position.at(100, 100);
        Position nodePosition2 = Position.at(200, 300);
        Position nodePosition21 = Position.at(40, 40);
        Size nodeSize = Size.of(200, 100);
        Size nodeSize21 = Size.of(50, 50);
        List<NodeLayoutData> nodes = new ArrayList<>();

        // Create diagram with two children
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(nodePosition1, nodeSize, diagramLayoutData, NodeType.NODE_RECTANGLE);
        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(nodePosition2, nodeSize, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);
        nodes.add(nodeLayoutData2);

        // Add one child within the node 2
        NodeLayoutData nodeLayoutData21 = this.createNodeLayoutData(nodePosition21, nodeSize21, nodeLayoutData2, NodeType.NODE_RECTANGLE);
        nodeLayoutData2.setChildrenNodes(List.of(nodeLayoutData21));

        // a positive position delta means the node has been resized from NW to the outside.
        Position positionDelta = Position.at(10, 20);
        Size newNode2Size = Size.of(190, 80);
        Position expectedNewNodePosition = Position.at(190, 280);

        // The new sub node relative position has been increase to keep the same abolution position.
        Position expectedSubNodePosition = Position.at(50, 60);
        NodePositionProvider nodePositionProvider = new NodePositionProvider();

        Optional<IDiagramEvent> optionalEvent = Optional.of(new ResizeEvent(nodeLayoutData2.getId(), positionDelta, newNode2Size));
        Position newPosition1 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        Position newPosition2 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData2);
        Position newPosition21 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData21);
        assertThat(newPosition2).isEqualTo(expectedNewNodePosition);
        assertThat(newPosition1).isEqualTo(nodePosition1);
        assertThat(newPosition21).isEqualTo(expectedSubNodePosition);
    }

    @Test
    public void testSEResizeNodeEvent() {
        Position nodePosition1 = Position.at(100, 100);
        Position nodePosition2 = Position.at(200, 300);
        Position nodePosition21 = Position.at(40, 40);
        Size nodeSize = Size.of(200, 100);
        Size nodeSize21 = Size.of(50, 50);
        List<NodeLayoutData> nodes = new ArrayList<>();
        // Create diagram with two children
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(nodePosition1, nodeSize, diagramLayoutData, NodeType.NODE_RECTANGLE);
        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(nodePosition2, nodeSize, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);
        nodes.add(nodeLayoutData2);

        // Add one child within the node 2
        NodeLayoutData nodeLayoutData21 = this.createNodeLayoutData(nodePosition21, nodeSize21, nodeLayoutData2, NodeType.NODE_RECTANGLE);
        nodeLayoutData2.setChildrenNodes(List.of(nodeLayoutData21));

        Size newNode2Size = Size.of(190, 80);

        // Since the resize is performed from the SE, the position should not change.
        NodePositionProvider nodePositionProvider = new NodePositionProvider();

        Optional<IDiagramEvent> optionalEvent = Optional.of(new ResizeEvent(nodeLayoutData2.getId(), Position.at(0, 0), newNode2Size));
        Position newPosition1 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        Position newPosition2 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData2);
        Position newPosition21 = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData21);

        assertThat(newPosition2).isEqualTo(nodePosition2);
        assertThat(newPosition1).isEqualTo(nodePosition1);
        assertThat(newPosition21).isEqualTo(nodePosition21);
    }

    @Test
    public void testProvidePositionOfAListItem() {
        Position nodeListPosition = Position.at(10, 10);
        Position nodeListItemPosition = Position.at(0, 38);
        Size nodeListSize = Size.of(100, 64);
        Size nodeListItemSize = Size.of(100, 20);

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeLayoutData(nodeListPosition, nodeListSize, diagramLayoutData, NodeType.NODE_LIST);
        NodeLayoutData nodeListItemLayoutData = this.createNodeLayoutData(nodeListItemPosition, nodeListItemSize, nodeListLayoutData, NodeType.NODE_LIST_ITEM);
        nodeListLayoutData.setChildrenNodes(List.of(nodeListItemLayoutData));
        nodeListLayoutData.setLabel(this.createNodeListLabelLayoutData());
        diagramLayoutData.setChildrenNodes(List.of(nodeListLayoutData));

        NodePositionProvider nodePositionProvider = new NodePositionProvider();

        Position newListItemPosition = nodePositionProvider.getPosition(Optional.empty(), nodeListItemLayoutData);
        assertThat(newListItemPosition).isEqualTo(nodeListItemPosition);
    }

    @Test
    public void testProvidePositionOfTwoListItems() {
        Size nodeListSize = Size.of(100, 64);
        Size nodeListItemSize = Size.of(100, 20);
        Position nodeListPosition = Position.at(10, 10);
        Position firstNodeListItemPosition = Position.at(0, 38);
        Position secondNodeListItemPosition = Position.at(0, firstNodeListItemPosition.getY() + nodeListItemSize.getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP);

        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeListLayoutData = this.createNodeLayoutData(nodeListPosition, nodeListSize, diagramLayoutData, NodeType.NODE_LIST);
        NodeLayoutData firstNodeListItemLayoutData = this.createNodeLayoutData(firstNodeListItemPosition, nodeListItemSize, nodeListLayoutData, NodeType.NODE_LIST_ITEM);
        NodeLayoutData secondNodeListItemLayoutData = this.createNodeLayoutData(secondNodeListItemPosition, nodeListItemSize, nodeListLayoutData, NodeType.NODE_LIST_ITEM);
        nodeListLayoutData.setChildrenNodes(List.of(firstNodeListItemLayoutData, secondNodeListItemLayoutData));
        nodeListLayoutData.setLabel(this.createNodeListLabelLayoutData());
        diagramLayoutData.setChildrenNodes(List.of(nodeListLayoutData));

        NodePositionProvider nodePositionProvider = new NodePositionProvider();

        Position newFisrtNodeListItemPosition = nodePositionProvider.getPosition(Optional.empty(), firstNodeListItemLayoutData);
        assertThat(newFisrtNodeListItemPosition).isEqualTo(firstNodeListItemPosition);

        Position newSecondNodeListItemPosition = nodePositionProvider.getPosition(Optional.empty(), secondNodeListItemLayoutData);
        assertThat(newSecondNodeListItemPosition).isEqualTo(secondNodeListItemPosition);
    }

    private LabelLayoutData createNodeListLabelLayoutData() {
        LabelLayoutData labelLayoutData = new LabelLayoutData();
        labelLayoutData.setId(UUID.randomUUID().toString());
        labelLayoutData.setLabelType(LabelType.INSIDE_CENTER.getValue());
        labelLayoutData.setPosition(Position.at(5, 5));
        TextBounds textBounds = new TextBounds(Size.of(90, 20), Position.UNDEFINED);
        labelLayoutData.setTextBounds(textBounds);
        return labelLayoutData;
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID().toString());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private NodeLayoutData createNodeLayoutData(Position position, Size size, IContainerLayoutData parent, String nodeType) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID().toString());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(position);
        nodeLayoutData.setSize(size);
        nodeLayoutData.setNodeType(nodeType);
        return nodeLayoutData;
    }
}
