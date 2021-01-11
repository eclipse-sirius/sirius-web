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
package org.eclipse.sirius.web.diagrams.components;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.INodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.LabelStyle;
import org.eclipse.sirius.web.diagrams.LineStyle;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.web.diagrams.Size;
import org.junit.Test;

/**
 * Test cases for {@link NodePositionProvider}.
 *
 * @author fbarbin
 * @author wpiers
 */
public class NodePositionProviderTestCases {

    private static final Size DEFAULT_NODE_SIZE = Size.newSize().height(70).width(150).build();

    private static final double STARTX = 1000;

    private static final double STARTY = 1000;

    @Test
    public void testNewNodesInClosedDiagram() {
        List<Node> nodes = new ArrayList<>();
        Diagram parent;
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        INodeStyle style = this.getRectangularNodeStyle();

        parent = this.getDiagram(nodes);
        NodePositionProvider nodePositionProvider = new NodePositionProvider(Optional.empty(), Map.of());

        Position nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        parent = this.getDiagram(nodes);
        nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));
    }

    @Test
    public void testNewNodesInOpenedDiagram() {
        List<Node> nodes = new ArrayList<>();
        Diagram parent;
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        INodeStyle style = this.getRectangularNodeStyle();

        parent = this.getDiagram(nodes);
        NodePositionProvider nodePositionProvider = new NodePositionProvider(Optional.empty(), Map.of());
        Position nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        parent = this.getDiagram(nodes);
        nodePositionProvider = new NodePositionProvider(Optional.empty(), Map.of());
        nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));

        // Test creation of a new node at a given position (creation tool)
        Position startingPosition = Position.newPosition().x(STARTX).y(STARTY).build();
        parent = this.getDiagram(nodes);
        nodePositionProvider = new NodePositionProvider(Optional.of(startingPosition), Map.of());
        nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        Size size = nodeSizeProvider.getSize(style, List.of());
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(STARTX - size.getWidth() / 2);
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(STARTY - size.getHeight() / 2);
    }

    @Test
    public void testNewNodesInNodeInClosedDiagram() {
        Position parentPosition = Position.newPosition().x(0).y(0).build();
        List<Node> nodes = new ArrayList<>();
        Node parent;
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        INodeStyle style = this.getRectangularNodeStyle();

        parent = this.getNode(parentPosition, nodes);
        NodePositionProvider nodePositionProvider = new NodePositionProvider(Optional.empty(), Map.of());

        Position nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        parent = this.getNode(parentPosition, nodes);
        nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));
    }

    @Test
    public void testNewNodesInNodeInOpenedDiagram() {
        Position parentPosition = Position.newPosition().x(0).y(0).build();
        List<Node> nodes = new ArrayList<>();
        Node parent;
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider();
        INodeStyle style = this.getRectangularNodeStyle();

        parent = this.getNode(parentPosition, nodes);
        NodePositionProvider nodePositionProvider = new NodePositionProvider(Optional.empty(), Map.of());
        Position nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(0));

        parent = this.getNode(parentPosition, nodes);
        nodePositionProvider = new NodePositionProvider(Optional.empty(), Map.of());
        nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(0));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(DEFAULT_NODE_SIZE.getHeight() + 30));

        // Test creation of a new node at a given position (creation tool)
        Position startingPosition = Position.newPosition().x(STARTX).y(STARTY).build();
        parent = this.getNode(parentPosition, nodes);
        nodePositionProvider = new NodePositionProvider(Optional.of(startingPosition), Map.of());
        nextPosition = nodePositionProvider.getPosition(UUID.randomUUID(), Optional.empty(), Optional.of(parent), nodeSizeProvider, style);
        Size size = nodeSizeProvider.getSize(style, List.of());
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(STARTX - size.getWidth() / 2);
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(STARTY - size.getHeight() / 2);
    }

    public Diagram getDiagram(List<Node> nodes) {
        // @formatter:off
        return Diagram.newDiagram(UUID.randomUUID())
                .label("diagramLabel") //$NON-NLS-1$
                .descriptionId(UUID.randomUUID())
                .targetObjectId("diagramTargetObjectId") //$NON-NLS-1$
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .nodes(nodes)
                .edges(List.of())
                .build();
        // @formatter:on
    }

    public Node getNode(Position position, List<Node> childNodes) {
        // @formatter:off
        return Node.newNode(UUID.randomUUID())
                .position(position)
                .size(DEFAULT_NODE_SIZE)
                .type("nodeType") //$NON-NLS-1$
                .targetObjectId("nodeTargetObjectId") //$NON-NLS-1$
                .targetObjectKind("") //$NON-NLS-1$
                .targetObjectLabel("") //$NON-NLS-1$
                .descriptionId(UUID.randomUUID())
                .label(this.getLabel())
                .style(this.getRectangularNodeStyle())
                .borderNodes(List.of())
                .childNodes(childNodes)
                .build();
        // @formatter:on
    }

    private Label getLabel() {
        // @formatter:off
        LabelStyle labelStyle = LabelStyle.newLabelStyle()
                .color("#000000") //$NON-NLS-1$
                .fontSize(16)
                .iconURL("") //$NON-NLS-1$
                .build();
        return Label.newLabel("labelId") //$NON-NLS-1$
                .type("labelType") //$NON-NLS-1$
                .text("text") //$NON-NLS-1$
                .position(Position.UNDEFINED)
                .size(Size.UNDEFINED)
                .alignment(Position.UNDEFINED)
                .style(labelStyle)
                .build();
        // @formatter:on
    }

    private RectangularNodeStyle getRectangularNodeStyle() {
        // @formatter:off
        return RectangularNodeStyle.newRectangularNodeStyle()
                .borderColor("#000000") //$NON-NLS-1$
                .borderSize(1)
                .borderStyle(LineStyle.Solid)
                .color("#FFFFFF") //$NON-NLS-1$
                .build();
        // @formatter:on
    }
}
