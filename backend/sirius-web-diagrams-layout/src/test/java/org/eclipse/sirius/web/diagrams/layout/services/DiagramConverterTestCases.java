/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.data.Offset;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.ElkShape;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.ConvertedDiagram;
import org.eclipse.sirius.web.diagrams.layout.DiagramConverter;
import org.eclipse.sirius.web.diagrams.layout.ImageNodeStyleSizeService;
import org.eclipse.sirius.web.diagrams.layout.ImageSizeService;
import org.eclipse.sirius.web.diagrams.layout.TextBounds;
import org.eclipse.sirius.web.diagrams.layout.TextBoundsService;
import org.eclipse.sirius.web.diagrams.tests.TestDiagramBuilder;
import org.junit.Test;

/**
 * Unit tests of the diagram converter.
 *
 * @author sbegaudeau
 */
public class DiagramConverterTestCases {
    private static final double TEXT_WIDTH = 100;

    private static final double TEXT_HEIGHT = 10;

    private static final double IMAGE_WIDTH = 150;

    private static final double IMAGE_HEIGHT = 150;

    private static final UUID DIAGRAM_ID = UUID.randomUUID();

    private static final String FIRST_NODE_ID = "firstNodeId"; //$NON-NLS-1$

    private static final String SECOND_NODE_ID = "secondNodeId"; //$NON-NLS-1$

    private static final String THIRD_NODE_ID = "thirdNodeId"; //$NON-NLS-1$

    private static final String FIRST_EDGE_ID = "firstEdgeId"; //$NON-NLS-1$

    private TextBoundsService textSizeService = new TextBoundsService() {
        @Override
        public TextBounds getBounds(Label label) {
            Size size = Size.newSize().width(TEXT_WIDTH).height(TEXT_HEIGHT).build();
            Position alignment = Position.UNDEFINED;
            return new TextBounds(size, alignment);
        }
    };

    private ImageSizeService imageSizeService = new ImageSizeService() {
        @Override
        public Optional<Size> getSize(String imagePath) {
            return Optional.of(Size.newSize().width(IMAGE_WIDTH).height(IMAGE_HEIGHT).build());
        }
    };

    private ImageNodeStyleSizeService imageNodeStyleSizeService = new ImageNodeStyleSizeService(this.imageSizeService) {
        @Override
        public Size getSize(ImageNodeStyle imageNodeStyle) {
            return Size.newSize().width(IMAGE_WIDTH).height(IMAGE_HEIGHT).build();
        }
    };

    private void assertSize(ElkShape elkShape, double width, double height) {
        assertThat(elkShape.getWidth()).isCloseTo(width, Offset.offset(0.0001));
        assertThat(elkShape.getHeight()).isCloseTo(height, Offset.offset(0.0001));
    }

    @Test
    public void testDiagramOneRectangularNode() {
        DiagramConverter diagramConverter = new DiagramConverter(this.textSizeService, this.imageNodeStyleSizeService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();
        Node node = diagramBuilder.getNode(FIRST_NODE_ID);

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .build();
        // @formatter:on

        ConvertedDiagram convertedDiagram = diagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        assertThat(id2ElkGraphElements.get(node.getId())).isInstanceOf(ElkNode.class);
        assertThat(id2ElkGraphElements.get(node.getLabel().getId())).isInstanceOf(ElkLabel.class);

        ElkNode elkNode = (ElkNode) id2ElkGraphElements.get(node.getId());
        this.assertSize(elkNode, TEXT_WIDTH, TEXT_HEIGHT);

        assertThat(elkNode.getLabels().size()).isEqualTo(1);
        ElkLabel elkLabel = elkNode.getLabels().get(0);
        this.assertSize(elkLabel, TEXT_WIDTH, TEXT_HEIGHT);
    }

    @Test
    public void testDiagramOneImageNode() {
        DiagramConverter diagramConverter = new DiagramConverter(this.textSizeService, this.imageNodeStyleSizeService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();
        Node node = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID))
                .style(diagramBuilder.getImageNodeStyle())
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .build();
        // @formatter:on

        ConvertedDiagram convertedDiagram = diagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        assertThat(id2ElkGraphElements.get(node.getId())).isInstanceOf(ElkNode.class);
        assertThat(id2ElkGraphElements.get(node.getLabel().getId())).isInstanceOf(ElkLabel.class);

        ElkNode elkNode = (ElkNode) id2ElkGraphElements.get(node.getId());
        this.assertSize(elkNode, TEXT_WIDTH, TEXT_HEIGHT);

        assertThat(elkNode.getChildren().size()).isEqualTo(1);
        ElkNode elkImage = elkNode.getChildren().get(0);
        this.assertSize(elkImage, IMAGE_WIDTH, IMAGE_HEIGHT);

        assertThat(elkNode.getLabels().size()).isEqualTo(1);
        ElkLabel elkLabel = elkNode.getLabels().get(0);
        this.assertSize(elkLabel, TEXT_WIDTH, TEXT_HEIGHT);
    }

    @Test
    public void testDiagramOneNodeAndOneEdge() {
        DiagramConverter diagramConverter = new DiagramConverter(this.textSizeService, this.imageNodeStyleSizeService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();
        Node node = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID))
                .style(diagramBuilder.getImageNodeStyle())
                .build();

        Edge edge = Edge.newEdge(diagramBuilder.getEdge(FIRST_EDGE_ID, node.getId(), node.getId()))
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .edges(List.of(edge))
                .build();
        // @formatter:on

        ConvertedDiagram convertedDiagram = diagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getContainedEdges().size()).isEqualTo(1);

        ElkEdge elkEdge = elkDiagram.getContainedEdges().get(0);
        assertThat(elkEdge.getIdentifier()).isEqualTo(edge.getId());
    }

    @Test
    public void testDiagramOneNodeAndOneBorderNode() {
        DiagramConverter diagramConverter = new DiagramConverter(this.textSizeService, this.imageNodeStyleSizeService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();

        Node borderNode = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID))
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();

        Node node = Node.newNode(diagramBuilder.getNode(SECOND_NODE_ID))
                .style(diagramBuilder.getImageNodeStyle())
                .borderNodes(List.of(borderNode))
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .build();
        // @formatter:on

        ConvertedDiagram convertedDiagram = diagramConverter.convert(diagram);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);

        ElkNode elkNode = elkDiagram.getChildren().get(0);
        assertThat(elkNode.getPorts().size()).isEqualTo(1);

        ElkPort elkPort = elkNode.getPorts().get(0);
        assertThat(elkPort.getIdentifier()).isEqualTo(borderNode.getId());
    }

    @Test
    public void testDiagramOneEdgeBetweenTwoBorderNodes() {
        DiagramConverter diagramConverter = new DiagramConverter(this.textSizeService, this.imageNodeStyleSizeService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();

        Node firstBorderNode = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID))
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();
        Node secondBorderNode = Node.newNode(diagramBuilder.getNode(SECOND_NODE_ID))
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();

        Node node = Node.newNode(diagramBuilder.getNode(THIRD_NODE_ID))
                .style(diagramBuilder.getImageNodeStyle())
                .borderNodes(List.of(firstBorderNode, secondBorderNode))
                .build();

        Edge edge = Edge.newEdge(diagramBuilder.getEdge(FIRST_EDGE_ID, firstBorderNode.getId(), secondBorderNode.getId()))
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .edges(List.of(edge))
                .build();
        // @formatter:on

        ConvertedDiagram convertedDiagram = diagramConverter.convert(diagram);
        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);
        assertThat(elkDiagram.getChildren().get(0)).isInstanceOf(ElkNode.class);
        assertThat(elkDiagram.getContainedEdges().size()).isEqualTo(1);
        assertThat(elkDiagram.getContainedEdges().get(0)).isInstanceOf(ElkEdge.class);

        ElkEdge elkEdge = elkDiagram.getContainedEdges().get(0);
        assertThat(elkEdge.getSources().size()).isEqualTo(1);
        assertThat(elkEdge.getSources().get(0).getIdentifier()).isEqualTo(firstBorderNode.getId());
        assertThat(elkEdge.getTargets().size()).isEqualTo(1);
        assertThat(elkEdge.getTargets().get(0).getIdentifier()).isEqualTo(secondBorderNode.getId());
    }
}
