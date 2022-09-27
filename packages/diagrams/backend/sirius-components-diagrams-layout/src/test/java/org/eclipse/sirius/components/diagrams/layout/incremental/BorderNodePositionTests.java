/*******************************************************************************
 * Copyright (c) 2022 THALES GLOBAL SERVICES.
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

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test cases for border nodes.
 *
 * @author lfasani
 */
public class BorderNodePositionTests {

    private static final Size DEFAULT_NODE_SIZE = Size.of(200, 100);

    private static final Size DEFAULT_BORDER_NODE_SIZE = Size.of(24, 20);

    private static final TextBounds BORDER_NODE_LABEL_TEXT_BOUNDS = new TextBounds(Size.of(100, 26), Position.at(0, 20));

    private static final Position BORDER_NODE_LABEL_TEXT_POSITION = Position.at(-100, DEFAULT_BORDER_NODE_SIZE.getHeight());

    private static final Position ZERO_POSITION = Position.at(0, 0);

    @BeforeAll
    private static void initTest() {
        LayoutMetaDataService.getInstance().registerLayoutMetaDataProviders(new LayeredOptions());
    }

    /**
     * Test the snap of the border nodes on their parent container.<br/>
     * The border nodes are voluntary at an invalid position to make sure they properly snap done at the first layout
     */
    @Test
    public void testSnap() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        incrementalLayoutEngine.layout(Optional.empty(), initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    private void checkBorderNodeLabel(LabelLayoutData labelLayoutData, Position borderNodeLabelTextPosition, TextBounds borderNodeLabelTextBounds) {
        assertThat(labelLayoutData.getPosition()).isEqualTo(borderNodeLabelTextPosition);
        assertThat(labelLayoutData.getTextBounds()).isEqualTo(borderNodeLabelTextBounds);
    }

    @Test
    public void testBorderNodeCreationEvent() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        // add a border node with an non positioned label
        LabelLayoutData labelLayoutData = this.createLabelLayoutData(Position.at(-1, -1), "any", BORDER_NODE_LABEL_TEXT_BOUNDS); //$NON-NLS-1$
        borderNodes.add(this.createBorderNodeLayoutData(BORDER_NODE_LABEL_TEXT_POSITION, DEFAULT_BORDER_NODE_SIZE, initializeDiagram, NodeType.NODE_RECTANGLE, labelLayoutData));

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        Optional<IDiagramEvent> creationEvent = Optional.of(new SinglePositionEvent(Position.at(10, 10)));
        incrementalLayoutEngine.layout(creationEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testMoveParentNodeEvent() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();
        String nodeId = initializeDiagram.getChildrenNodes().get(0).getId();

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        Optional<IDiagramEvent> resizeEvent = Optional.of(new MoveEvent(nodeId, Position.at(50, -100)));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testMoveBorderNodeEvent() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        NodeLayoutData northBorderNode = initializeDiagram.getChildrenNodes().get(0).getBorderNodes().get(0);
        NodeLayoutData eastBorderNode = initializeDiagram.getChildrenNodes().get(0).getBorderNodes().get(3);

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        // move slightly the north border node so that the incremental layout updates the label position
        Optional<IDiagramEvent> resizeEvent = Optional.of(new MoveEvent(northBorderNode.getId(), Position.at(northBorderNode.getPosition().getX() + 1, northBorderNode.getPosition().getY())));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(northBorderNode.getLabel(), Position.at(-100, -BORDER_NODE_LABEL_TEXT_BOUNDS.getSize().getHeight()), BORDER_NODE_LABEL_TEXT_BOUNDS);

        // move slightly the east border node so that the incremental layout updates the label position
        resizeEvent = Optional.of(new MoveEvent(eastBorderNode.getId(), Position.at(eastBorderNode.getPosition().getX() + 1, eastBorderNode.getPosition().getY())));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());
        this.checkBorderNodeLabel(eastBorderNode.getLabel(), Position.at(DEFAULT_BORDER_NODE_SIZE.getWidth(), DEFAULT_BORDER_NODE_SIZE.getHeight()), BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    private void checkBorderNodesAtInitialPosition(List<NodeLayoutData> borderNodes) {
        assertThat(borderNodes.get(0).getPosition()).isEqualTo(Position.at(28, -12));
        assertThat(borderNodes.get(1).getPosition()).isEqualTo(Position.at(38, -12));
        assertThat(borderNodes.get(2).getPosition()).isEqualTo(Position.at(192, 40));
        assertThat(borderNodes.get(3).getPosition()).isEqualTo(Position.at(192, 90));
        assertThat(borderNodes.get(4).getPosition()).isEqualTo(Position.at(188, 92));
        assertThat(borderNodes.get(5).getPosition()).isEqualTo(Position.at(88, 92));
        assertThat(borderNodes.get(6).getPosition()).isEqualTo(Position.at(-16, 40));
        assertThat(borderNodes.get(7).getPosition()).isEqualTo(Position.at(-16, -10));
    }

    @Test
    public void testResizeParentNodeSouthEastEvent() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        String nodeId = initializeDiagram.getChildrenNodes().get(0).getId();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        // Decrease the parent node size
        Optional<IDiagramEvent> resizeEvent = Optional.of(new ResizeEvent(nodeId, ZERO_POSITION, Size.of(100, 50)));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAfterResize(borderNodes);

        // Increase the parent node size to get the initial size
        resizeEvent = Optional.of(new ResizeEvent(nodeId, ZERO_POSITION, DEFAULT_NODE_SIZE));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testResizeBorderNodeEvent() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        // decrease the height of the west border node
        NodeLayoutData westBorderNode = borderNodes.get(6);
        Optional<IDiagramEvent> resizeEvent = Optional
                .of(new ResizeEvent(westBorderNode.getId(), ZERO_POSITION, Size.of(DEFAULT_BORDER_NODE_SIZE.getWidth(), DEFAULT_BORDER_NODE_SIZE.getHeight() / 2)));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(westBorderNode.getLabel(), Position.at(BORDER_NODE_LABEL_TEXT_POSITION.getX(), BORDER_NODE_LABEL_TEXT_POSITION.getY() / 2), BORDER_NODE_LABEL_TEXT_BOUNDS);

        // increase the width and the height of the east border node
        NodeLayoutData eastBorderNode = borderNodes.get(3);
        resizeEvent = Optional.of(new ResizeEvent(eastBorderNode.getId(), ZERO_POSITION, Size.of(DEFAULT_BORDER_NODE_SIZE.getWidth() + 10, DEFAULT_BORDER_NODE_SIZE.getHeight() + 10)));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(eastBorderNode.getLabel(), Position.at(eastBorderNode.getSize().getWidth(), eastBorderNode.getSize().getHeight()), BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testResizeParentNodeNorthWestEvent() {
        DiagramLayoutData initializeDiagram = this.initializeDiagram();
        String nodeId = initializeDiagram.getChildrenNodes().get(0).getId();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(new ImageSizeProvider());
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(nodeSizeProvider, List.of());

        // Decrease the parent node size
        Optional<IDiagramEvent> resizeEvent = Optional.of(new ResizeEvent(nodeId, Position.at(100, 50), Size.of(100, 50)));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAfterResize(borderNodes);

        // Increase the parent node size to get the initial size
        resizeEvent = Optional.of(new ResizeEvent(nodeId, ZERO_POSITION, DEFAULT_NODE_SIZE));
        incrementalLayoutEngine.layout(resizeEvent, initializeDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    private void checkBorderNodesAfterResize(List<NodeLayoutData> borderNodes) {
        assertThat(borderNodes.get(0).getPosition()).isEqualTo(Position.at(8, -12));
        assertThat(borderNodes.get(1).getPosition()).isEqualTo(Position.at(13, -12));
        assertThat(borderNodes.get(2).getPosition()).isEqualTo(Position.at(92, 15));
        assertThat(borderNodes.get(3).getPosition()).isEqualTo(Position.at(92, 40));
        assertThat(borderNodes.get(4).getPosition()).isEqualTo(Position.at(88, 42));
        assertThat(borderNodes.get(5).getPosition()).isEqualTo(Position.at(38, 42));
        assertThat(borderNodes.get(6).getPosition()).isEqualTo(Position.at(-16, 15));
        assertThat(borderNodes.get(7).getPosition()).isEqualTo(Position.at(-16, -10));
    }

    /**
     * Initialize the diagram with 1 node containing many border nodes.<br/>
     * The border nodes are voluntary at an invalid position to make sure they properly positioned after nay layout
     * event.
     */
    private DiagramLayoutData initializeDiagram() {
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        diagramLayoutData.setEdges(new ArrayList<>());

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.at(100, 100), DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);
        List<NodeLayoutData> nodes = new ArrayList<>();
        nodes.add(nodeLayoutData);
        diagramLayoutData.setChildrenNodes(nodes);

        List<NodeLayoutData> borderNodes = new ArrayList<>();

        // Position of the border if it was located centered at the origin of its parent
        Position borderNodeCenterAtOrigin = Position.at(-DEFAULT_BORDER_NODE_SIZE.getWidth() / 2, -DEFAULT_BORDER_NODE_SIZE.getHeight() / 2);
        // border nodes on north
        borderNodes.add(this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() + 40, borderNodeCenterAtOrigin.getY() - 20), DEFAULT_BORDER_NODE_SIZE, nodeLayoutData,
                NodeType.NODE_RECTANGLE));
        borderNodes.add(
                this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() + 50, borderNodeCenterAtOrigin.getY()), DEFAULT_BORDER_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE));
        // border nodes on east
        borderNodes.add(
                this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() + DEFAULT_NODE_SIZE.getWidth() + 10, borderNodeCenterAtOrigin.getY() + DEFAULT_NODE_SIZE.getHeight() / 2),
                        DEFAULT_BORDER_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE));
        borderNodes.add(
                this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() + DEFAULT_NODE_SIZE.getWidth() + 10, borderNodeCenterAtOrigin.getY() + DEFAULT_NODE_SIZE.getHeight() + 5),
                        DEFAULT_BORDER_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE));
        // border nodes on south
        borderNodes.add(
                this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() + DEFAULT_NODE_SIZE.getWidth() + 5, borderNodeCenterAtOrigin.getY() + DEFAULT_NODE_SIZE.getHeight() + 10),
                        DEFAULT_BORDER_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE));
        borderNodes.add(
                this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() + DEFAULT_NODE_SIZE.getWidth() / 2, borderNodeCenterAtOrigin.getY() + DEFAULT_NODE_SIZE.getHeight() - 20),
                        DEFAULT_BORDER_NODE_SIZE, nodeLayoutData, NodeType.NODE_RECTANGLE));
        // border nodes on west
        borderNodes.add(this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX(), borderNodeCenterAtOrigin.getY() + DEFAULT_NODE_SIZE.getHeight() / 2), DEFAULT_BORDER_NODE_SIZE,
                nodeLayoutData, NodeType.NODE_RECTANGLE));
        borderNodes.add(this.createBorderNodeLayoutData(Position.at(borderNodeCenterAtOrigin.getX() - 2, borderNodeCenterAtOrigin.getY() - 1), DEFAULT_BORDER_NODE_SIZE, nodeLayoutData,
                NodeType.NODE_RECTANGLE));

        nodeLayoutData.setBorderNodes(borderNodes);

        return diagramLayoutData;
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID().toString());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));

        return diagramLayoutData;
    }

    private NodeLayoutData createBorderNodeLayoutData(Position position, Size size, IContainerLayoutData parent, String nodeType, LabelLayoutData labelLayoutData) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID().toString());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(position);
        nodeLayoutData.setSize(size);
        nodeLayoutData.setNodeType(nodeType);
        nodeLayoutData.setBorderNode(true);
        nodeLayoutData.setLabel(labelLayoutData);
        return nodeLayoutData;
    }

    private NodeLayoutData createBorderNodeLayoutData(Position position, Size size, IContainerLayoutData parent, String nodeType) {
        NodeLayoutData nodeLayoutData = this.createBorderNodeLayoutData(position, size, parent, nodeType,
                this.createLabelLayoutData(BORDER_NODE_LABEL_TEXT_POSITION, "any", BORDER_NODE_LABEL_TEXT_BOUNDS)); //$NON-NLS-1$
        return nodeLayoutData;
    }

    private NodeLayoutData createNodeLayoutData(Position position, Size size, IContainerLayoutData parent, String nodeType) {
        NodeLayoutData nodeLayoutData = new NodeLayoutData();
        nodeLayoutData.setId(UUID.randomUUID().toString());
        nodeLayoutData.setParent(parent);
        nodeLayoutData.setPosition(position);
        nodeLayoutData.setSize(size);
        nodeLayoutData.setNodeType(nodeType);
        nodeLayoutData.setChildrenNodes(new ArrayList<>());
        nodeLayoutData.setLabel(this.createLabelLayoutData(Position.at(0, 0), "inside", new TextBounds(Size.of(0, 0), Position.at(0, 0)))); //$NON-NLS-1$
        return nodeLayoutData;
    }

    private LabelLayoutData createLabelLayoutData(Position position, String labelType, TextBounds textBounds) {
        LabelLayoutData labelLayoutData = new LabelLayoutData();
        labelLayoutData.setId(UUID.randomUUID().toString());
        labelLayoutData.setPosition(position);
        labelLayoutData.setLabelType(labelType);
        labelLayoutData.setTextBounds(textBounds);
        return labelLayoutData;
    }

}
