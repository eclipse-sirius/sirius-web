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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.web.diagrams.Diagram;
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

    private static final double STARTX = 20;

    private static final double STARTY = 70;

    @Test
    public void testNewNodesInDiagram() {
        List<Node> nodes = new ArrayList<>();
        Diagram parent;

        // Diagram created but closed
        parent = this.getDiagram(nodes);
        NodePositionProvider nodePositionProvider = new NodePositionProvider(STARTX, STARTY);
        Position nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY));

        parent = this.getDiagram(nodes);
        nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY + DEFAULT_NODE_SIZE.getHeight() + 30));

        // Diagram opened (we recreate a provider each time)
        parent = this.getDiagram(nodes);
        nodePositionProvider = new NodePositionProvider(STARTX, STARTY);
        nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY + (DEFAULT_NODE_SIZE.getHeight() + 30) * 2));

        parent = this.getDiagram(nodes);
        nodePositionProvider = new NodePositionProvider(STARTX, STARTY);
        nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY + (DEFAULT_NODE_SIZE.getHeight() + 30) * 3));
    }

    @Test
    public void testNewNodesInNode() {
        Position initialPosition = Position.newPosition().x(STARTX).y(STARTY).build();
        List<Node> nodes = new ArrayList<>();
        Node parent;

        // Diagram created but closed
        parent = this.getNode(initialPosition, nodes);
        NodePositionProvider nodePositionProvider = new NodePositionProvider(STARTX, STARTY);
        Position nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY));

        parent = this.getNode(initialPosition, nodes);
        nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY + DEFAULT_NODE_SIZE.getHeight() + 30));

        // Diagram opened (we recreate a provider each time)
        parent = this.getNode(initialPosition, nodes);
        nodePositionProvider = new NodePositionProvider(STARTX, STARTY);
        nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        nodes.add(this.getNode(nextPosition, List.of()));
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY + (DEFAULT_NODE_SIZE.getHeight() + 30) * 2));

        parent = this.getNode(initialPosition, nodes);
        nodePositionProvider = new NodePositionProvider(STARTX, STARTY);
        nextPosition = nodePositionProvider.getNextPosition(Optional.of(parent), DEFAULT_NODE_SIZE);
        assertThat(nextPosition).extracting(Position::getX).isEqualTo(Double.valueOf(STARTX));
        assertThat(nextPosition).extracting(Position::getY).isEqualTo(Double.valueOf(STARTY + (DEFAULT_NODE_SIZE.getHeight() + 30) * 3));
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
