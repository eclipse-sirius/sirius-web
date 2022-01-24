/*******************************************************************************
 * Copyright (c) 2021 THALES GLOBAL SERVICES.
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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.events.NodeCreationEvent;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.web.diagrams.events.MoveEvent;
import org.eclipse.sirius.web.diagrams.events.ResizeEvent;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodePositionProvider;
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

    private static final double STARTX = 1000;

    private static final double STARTY = 1000;

    @Test
    public void testDiagramSeveralNewNodesAtOnce() {
        List<NodeLayoutData> nodes = new ArrayList<>();
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);

        Optional<IDiagramEvent> optionalDiagramElementEvent = Optional.of(new NodeCreationEvent(ZERO_POSITION));
        Position nextPosition = nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);

        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData2);
        nextPosition = nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData2);

        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));
    }

    @Test
    public void testDiagramNewNodesOneByOne() {
        List<NodeLayoutData> nodes = new ArrayList<>();
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);

        Position nextPosition = nodePositionProvider.getPosition(Optional.empty(), nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);

        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        nodePositionProvider = new NodePositionProvider();
        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nextPosition = nodePositionProvider.getPosition(Optional.empty(), nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));

        // Test creation of a new node at a given position (creation tool)
        Position startingPosition = Position.at(STARTX, STARTY);
        nodePositionProvider = new NodePositionProvider();
        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);

        Optional<IDiagramEvent> optionalEvent = Optional.of(new NodeCreationEvent(startingPosition));
        nextPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(STARTX);
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(STARTY);
    }

    @Test
    public void testNodeSeveralNewNodesAtOnce() {
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData parentNodeLayoutData = this.createNodeLayoutData(ZERO_POSITION, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(List.of(parentNodeLayoutData));
        List<NodeLayoutData> nodes = new ArrayList<>();
        parentNodeLayoutData.setChildrenNodes(nodes);

        Optional<IDiagramEvent> optionalEvent = Optional.of(new NodeCreationEvent(ZERO_POSITION));

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        Position nextPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nextPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));
    }

    @Test
    public void testNodeNewNodesOneByOne() {
        NodePositionProvider nodePositionProvider = new NodePositionProvider();
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData parentNodeLayoutData = this.createNodeLayoutData(ZERO_POSITION, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(List.of(parentNodeLayoutData));
        List<NodeLayoutData> nodes = new ArrayList<>();
        parentNodeLayoutData.setChildrenNodes(nodes);

        Optional<IDiagramEvent> optionalEvent = Optional.of(new NodeCreationEvent(ZERO_POSITION));

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        Position nextPosition = nodePositionProvider.getPosition(optionalEvent, nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nodePositionProvider = new NodePositionProvider();
        nextPosition = nodePositionProvider.getPosition(Optional.empty(), nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));

        // Test creation of a new node at a given position (creation tool)
        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        Position startingPosition = Position.at(STARTX, STARTY);
        nodes.add(nodeLayoutData);

        nodePositionProvider = new NodePositionProvider();
        nextPosition = nodePositionProvider.getPosition(Optional.of(new NodeCreationEvent(startingPosition)), nodeLayoutData);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(STARTX);
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(STARTY);
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
