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
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodePositionProvider;
import org.junit.Test;

/**
 * Test cases for {@link NodePositionProvider}.
 *
 * @author fbarbin
 * @author wpiers
 */
public class NodePositionProviderTestCases {

    private static final Size DEFAULT_NODE_SIZE = Size.of(150, 70);

    private static final Position ZERO_POSITION = Position.at(0, 0);

    private static final double STARTX = 1000;

    private static final double STARTY = 1000;

    @Test
    public void testDiagramSeveralNewNodesAtOnce() {
        List<NodeLayoutData> nodes = new ArrayList<>();
        NodePositionProvider nodePositionProvider = new NodePositionProvider(ZERO_POSITION);
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(nodes);
        nodes.add(nodeLayoutData);
        Position nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);

        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        NodeLayoutData nodeLayoutData2 = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData2);
        nextPosition = nodePositionProvider.getPosition(nodeLayoutData2);

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
        Position nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        nodePositionProvider = new NodePositionProvider();
        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));

        // Test creation of a new node at a given position (creation tool)
        Position startingPosition = Position.at(STARTX, STARTY);
        nodePositionProvider = new NodePositionProvider(startingPosition);
        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(STARTX);
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(STARTY);
    }

    @Test
    public void testNodeSeveralNewNodesAtOnce() {
        NodePositionProvider nodePositionProvider = new NodePositionProvider(ZERO_POSITION);
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData parentNodeLayoutData = this.createNodeLayoutData(ZERO_POSITION, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(List.of(parentNodeLayoutData));
        List<NodeLayoutData> nodes = new ArrayList<>();
        parentNodeLayoutData.setChildrenNodes(nodes);

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        Position nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));
    }

    @Test
    public void testNodeNewNodesOneByOne() {
        NodePositionProvider nodePositionProvider = new NodePositionProvider(ZERO_POSITION);
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        NodeLayoutData parentNodeLayoutData = this.createNodeLayoutData(ZERO_POSITION, DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        diagramLayoutData.setChildrenNodes(List.of(parentNodeLayoutData));
        List<NodeLayoutData> nodes = new ArrayList<>();
        parentNodeLayoutData.setChildrenNodes(nodes);

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        Position nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        nodes.add(nodeLayoutData);
        nodePositionProvider = new NodePositionProvider();
        nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        nodeLayoutData.setPosition(nextPosition);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));

        // Test creation of a new node at a given position (creation tool)
        nodeLayoutData = this.createNodeLayoutData(Position.UNDEFINED, DEFAULT_NODE_SIZE, parentNodeLayoutData, NodeType.NODE_RECTANGLE);
        Position startingPosition = Position.at(STARTX, STARTY);
        nodes.add(nodeLayoutData);
        nodePositionProvider = new NodePositionProvider(startingPosition);
        nextPosition = nodePositionProvider.getPosition(nodeLayoutData);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(STARTX);
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(STARTY);
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private NodeLayoutData createNodeLayoutData(Position position, Size size, IContainerLayoutData parent, String nodeType) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(position);
        nodeLayoutData.setSize(size);
        nodeLayoutData.setNodeType(nodeType);
        return nodeLayoutData;
    }
}
