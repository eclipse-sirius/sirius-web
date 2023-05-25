/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.data.Offset;
import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.PortLabelPlacement;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.ElkShape;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.InsideLabel;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.layout.ELKConvertedDiagram;
import org.eclipse.sirius.components.diagrams.layout.ELKDiagramConverter;
import org.eclipse.sirius.components.diagrams.layout.ELKPropertiesService;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.TextBoundsService;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Unit tests of the diagram converter.
 *
 * @author sbegaudeau
 */
public class DiagramConverterTests {
    private static final double TEXT_WIDTH = 100;

    private static final double TEXT_HEIGHT = 10;

    private static final double IMAGE_WIDTH = 100;

    private static final double IMAGE_HEIGHT = 100;

    private static final String DIAGRAM_ID = UUID.randomUUID().toString();

    private static final String FIRST_NODE_ID = UUID.randomUUID().toString();

    private static final String SECOND_NODE_ID = UUID.randomUUID().toString();

    private static final String THIRD_NODE_ID = UUID.randomUUID().toString();

    private static final String FIRST_EDGE_ID = UUID.randomUUID().toString();

    private ELKPropertiesService elkPropertiesService = new ELKPropertiesService();

    private TextBoundsService textBoundsService = new TextBoundsService() {
        @Override
        public TextBounds getBounds(InsideLabel label) {
            Size size = Size.of(TEXT_WIDTH, TEXT_HEIGHT);
            Position alignment = Position.UNDEFINED;
            return new TextBounds(size, alignment);
        }

        @Override
        public TextBounds getAutoWrapBounds(InsideLabel label, double maxWidth) {
            Size size = Size.of(TEXT_WIDTH, TEXT_HEIGHT);
            Position alignment = Position.UNDEFINED;
            return new TextBounds(size, alignment);
        }
    };

    @BeforeAll
    public static void initTest() {
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());
    }

    private void assertSize(ElkShape elkShape, double width, double height) {
        assertThat(elkShape.getWidth()).isCloseTo(width, Offset.offset(0.0001));
        assertThat(elkShape.getHeight()).isCloseTo(height, Offset.offset(0.0001));
    }

    @Test
    public void testDiagramOneRectangularNode() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        ELKDiagramConverter diagramConverter = new ELKDiagramConverter(this.textBoundsService, imageSizeProvider, this.elkPropertiesService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();
        Node node = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID, true))
                .type(NodeType.NODE_RECTANGLE)
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .build();
        // @formatter:on

        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        ELKConvertedDiagram convertedDiagram = diagramConverter.convert(diagram, layoutConfigurator);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        assertThat(id2ElkGraphElements.get(node.getId().toString())).isInstanceOf(ElkNode.class);
        assertThat(id2ElkGraphElements.get(node.getInsideLabel().getId().toString())).isInstanceOf(ElkLabel.class);

        ElkNode elkNode = (ElkNode) id2ElkGraphElements.get(node.getId().toString());
        this.assertSize(elkNode, Size.UNDEFINED.getWidth(), Size.UNDEFINED.getHeight());

        assertThat(elkNode.getLabels().size()).isEqualTo(1);
        ElkLabel elkLabel = elkNode.getLabels().get(0);
        this.assertSize(elkLabel, TEXT_WIDTH, TEXT_HEIGHT);

        imageSizeProvider.dispose();
    }

    @Test
    @Disabled
    public void testDiagramOneImageNode() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        ELKDiagramConverter diagramConverter = new ELKDiagramConverter(this.textBoundsService, imageSizeProvider, this.elkPropertiesService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();
        Node node = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID, true))
                .type(NodeType.NODE_IMAGE)
                .style(diagramBuilder.getImageNodeStyle())
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .build();
        // @formatter:on

        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        ELKConvertedDiagram convertedDiagram = diagramConverter.convert(diagram, layoutConfigurator);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);

        Map<String, ElkGraphElement> id2ElkGraphElements = convertedDiagram.getId2ElkGraphElements();
        assertThat(id2ElkGraphElements.get(node.getId().toString())).isInstanceOf(ElkNode.class);
        assertThat(id2ElkGraphElements.get(node.getInsideLabel().getId().toString())).isInstanceOf(ElkLabel.class);

        ElkNode elkNode = (ElkNode) id2ElkGraphElements.get(node.getId().toString());
        this.assertSize(elkNode, TEXT_WIDTH, TEXT_HEIGHT);

        assertThat(elkNode.getChildren().size()).isEqualTo(1);
        ElkNode elkImage = elkNode.getChildren().get(0);
        this.assertSize(elkImage, IMAGE_WIDTH, IMAGE_HEIGHT);

        assertThat(elkNode.getLabels().size()).isEqualTo(1);
        ElkLabel elkLabel = elkNode.getLabels().get(0);
        this.assertSize(elkLabel, TEXT_WIDTH, TEXT_HEIGHT);

        imageSizeProvider.dispose();
    }

    @Test
    public void testDiagramOneNodeAndOneEdge() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        ELKDiagramConverter diagramConverter = new ELKDiagramConverter(this.textBoundsService, imageSizeProvider, this.elkPropertiesService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();
        Node node = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID, true))
                .style(diagramBuilder.getImageNodeStyle())
                .build();

        Edge edge = Edge.newEdge(diagramBuilder.getEdge(FIRST_EDGE_ID, node.getId(), node.getId()))
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .edges(List.of(edge))
                .build();
        // @formatter:on

        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        ELKConvertedDiagram convertedDiagram = diagramConverter.convert(diagram, layoutConfigurator);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getContainedEdges().size()).isEqualTo(1);

        ElkEdge elkEdge = elkDiagram.getContainedEdges().get(0);
        assertThat(elkEdge.getIdentifier()).isEqualTo(edge.getId().toString());

        imageSizeProvider.dispose();
    }

    @Test
    public void testDiagramOneNodeAndOneBorderNode() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        ELKDiagramConverter diagramConverter = new ELKDiagramConverter(this.textBoundsService, imageSizeProvider, this.elkPropertiesService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();

        Node borderNode = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID, true))
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();

        Node node = Node.newNode(diagramBuilder.getNode(SECOND_NODE_ID, true))
                .style(diagramBuilder.getImageNodeStyle())
                .borderNodes(List.of(borderNode))
                .build();

        Diagram diagram = Diagram.newDiagram(diagramBuilder.getDiagram(DIAGRAM_ID))
                .nodes(List.of(node))
                .build();
        // @formatter:on

        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        ELKConvertedDiagram convertedDiagram = diagramConverter.convert(diagram, layoutConfigurator);

        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);

        ElkNode elkNode = elkDiagram.getChildren().get(0);
        assertThat(elkNode.getPorts().size()).isEqualTo(1);

        ElkPort elkPort = elkNode.getPorts().get(0);
        assertThat(elkPort.getIdentifier()).isEqualTo(borderNode.getId().toString());
        assertThat(elkPort.getLabels().get(0).getProperty(CoreOptions.PORT_LABELS_PLACEMENT)).isEqualTo(PortLabelPlacement.outside());

        imageSizeProvider.dispose();
    }

    @Test
    public void testDiagramOneEdgeBetweenTwoBorderNodes() {
        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        ELKDiagramConverter diagramConverter = new ELKDiagramConverter(this.textBoundsService, imageSizeProvider, this.elkPropertiesService);

        // @formatter:off
        TestDiagramBuilder diagramBuilder = new TestDiagramBuilder();

        Node firstBorderNode = Node.newNode(diagramBuilder.getNode(FIRST_NODE_ID, true))
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();
        Node secondBorderNode = Node.newNode(diagramBuilder.getNode(SECOND_NODE_ID, true))
                .style(diagramBuilder.getRectangularNodeStyle())
                .build();

        Node node = Node.newNode(diagramBuilder.getNode(THIRD_NODE_ID, true))
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

        ISiriusWebLayoutConfigurator layoutConfigurator = new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator();
        ELKConvertedDiagram convertedDiagram = diagramConverter.convert(diagram, layoutConfigurator);
        ElkNode elkDiagram = convertedDiagram.getElkDiagram();
        assertThat(elkDiagram.getChildren().size()).isEqualTo(1);
        assertThat(elkDiagram.getChildren().get(0)).isInstanceOf(ElkNode.class);
        assertThat(elkDiagram.getContainedEdges().size()).isEqualTo(1);
        assertThat(elkDiagram.getContainedEdges().get(0)).isInstanceOf(ElkEdge.class);

        ElkEdge elkEdge = elkDiagram.getContainedEdges().get(0);
        assertThat(elkEdge.getSources().size()).isEqualTo(1);
        assertThat(elkEdge.getSources().get(0).getIdentifier()).isEqualTo(firstBorderNode.getId().toString());
        assertThat(elkEdge.getTargets().size()).isEqualTo(1);
        assertThat(elkEdge.getTargets().get(0).getIdentifier()).isEqualTo(secondBorderNode.getId().toString());

        imageSizeProvider.dispose();
    }
}
