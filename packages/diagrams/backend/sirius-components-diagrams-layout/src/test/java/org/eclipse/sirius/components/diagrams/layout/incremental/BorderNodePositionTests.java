/*******************************************************************************
 * Copyright (c) 2022, 2023 THALES GLOBAL SERVICES.
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.data.LayoutMetaDataService;
import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.IconLabelNodeStyle;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.SinglePositionEvent;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.LayoutConfiguratorRegistry;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.tests.TestDiagramBuilder;
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
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);

        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        incrementalLayoutEngine.layout(Optional.empty(), incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    private void checkBorderNodeLabel(LabelLayoutData labelLayoutData, Position borderNodeLabelTextPosition, TextBounds borderNodeLabelTextBounds) {
        assertThat(labelLayoutData.getPosition()).isEqualTo(borderNodeLabelTextPosition);
        assertThat(labelLayoutData.getTextBounds()).isEqualTo(borderNodeLabelTextBounds);
    }

    @Test
    public void testBorderNodeCreationEvent() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        // add a border node with an non positioned label
        LabelLayoutData labelLayoutData = this.createLabelLayoutData(Position.at(-1, -1), "any", BORDER_NODE_LABEL_TEXT_BOUNDS);
        borderNodes.add(this.createBorderNodeLayoutData(BORDER_NODE_LABEL_TEXT_POSITION, DEFAULT_BORDER_NODE_SIZE, initializeDiagram, NodeType.NODE_RECTANGLE, labelLayoutData));

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        Optional<IDiagramEvent> creationEvent = Optional.of(new SinglePositionEvent(Position.at(10, 10)));
        incrementalLayoutEngine.layout(creationEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testMoveParentNodeEvent() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();
        String nodeId = initializeDiagram.getChildrenNodes().get(0).getId();

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        Optional<IDiagramEvent> resizeEvent = Optional.of(new MoveEvent(nodeId, Position.at(50, -100)));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testMoveBorderNodeEvent() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        NodeLayoutData northBorderNode = initializeDiagram.getChildrenNodes().get(0).getBorderNodes().get(0);
        NodeLayoutData eastBorderNode = initializeDiagram.getChildrenNodes().get(0).getBorderNodes().get(3);

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        // move slightly the north border node so that the incremental layout updates the label position
        Optional<IDiagramEvent> moveEvent = Optional.of(new MoveEvent(northBorderNode.getId(), Position.at(northBorderNode.getPosition().getX() + 1, northBorderNode.getPosition().getY())));
        incrementalLayoutEngine.layout(moveEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(northBorderNode.getLabel(), Position.at(-100, -BORDER_NODE_LABEL_TEXT_BOUNDS.getSize().getHeight()), BORDER_NODE_LABEL_TEXT_BOUNDS);

        // move slightly the east border node so that the incremental layout updates the label position
        moveEvent = Optional.of(new MoveEvent(eastBorderNode.getId(), Position.at(eastBorderNode.getPosition().getX() + 1, eastBorderNode.getPosition().getY())));
        incrementalLayoutEngine.layout(moveEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());
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
    public void testUpdateBorderNodePositionOnParentResizeDueToChildCreation() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        NodeLayoutData parentNode = initializeDiagram.getChildrenNodes().get(0);
        parentNode.setResizedByUser(true);
        parentNode.setChildrenLayoutStrategy(new FreeFormLayoutStrategy());
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        NodeLayoutData newChildLayoutData = this.createNodeLayoutData(Position.UNDEFINED, Size.UNDEFINED, parentNode, NodeType.NODE_RECTANGLE);
        // The height 118 is the right height to prevent the parent node decrease its height
        SinglePositionEvent singlePositionEvent = new SinglePositionEvent(Position.at(200, 118));
        incrementalLayoutEngine.layout(Optional.of(singlePositionEvent), incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        assertThat(newChildLayoutData.getPosition()).isEqualTo(Position.at(100, 18));
        assertThat(newChildLayoutData.getSize()).isEqualTo(Size.of(150, 70));
        this.checkBorderNodesAfterParentResizeDueToChildCreation(borderNodes);
    }

    private void checkBorderNodesAfterParentResizeDueToChildCreation(List<NodeLayoutData> borderNodes) {
        assertThat(this.getRoundedPosition(borderNodes.get(0).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(40.4, -12)));
        assertThat(this.getRoundedPosition(borderNodes.get(1).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(53.5, -12)));
        assertThat(this.getRoundedPosition(borderNodes.get(2).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(254, 40)));
        assertThat(this.getRoundedPosition(borderNodes.get(3).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(254, 90)));
        assertThat(this.getRoundedPosition(borderNodes.get(4).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(250, 92)));
        assertThat(this.getRoundedPosition(borderNodes.get(5).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(119, 92)));
        assertThat(this.getRoundedPosition(borderNodes.get(6).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(-16, 40)));
        assertThat(this.getRoundedPosition(borderNodes.get(7).getPosition())).isEqualTo(this.getRoundedPosition(Position.at(-16, -10)));
    }

    @Test
    public void testResizeParentNodeSouthEastEvent() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        String nodeId = initializeDiagram.getChildrenNodes().get(0).getId();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        // Decrease the parent node size
        Optional<IDiagramEvent> resizeEvent = Optional.of(new ResizeEvent(nodeId, ZERO_POSITION, Size.of(150, 70)));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAfterResize(borderNodes);

        // Increase the parent node size to get the initial size
        resizeEvent = Optional.of(new ResizeEvent(nodeId, ZERO_POSITION, DEFAULT_NODE_SIZE));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testResizeBorderNodeEvent() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        // decrease the height of the west border node
        NodeLayoutData westBorderNode = borderNodes.get(6);
        Optional<IDiagramEvent> resizeEvent = Optional
                .of(new ResizeEvent(westBorderNode.getId(), ZERO_POSITION, Size.of(DEFAULT_BORDER_NODE_SIZE.getWidth(), DEFAULT_BORDER_NODE_SIZE.getHeight() / 2)));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(westBorderNode.getLabel(), Position.at(BORDER_NODE_LABEL_TEXT_POSITION.getX(), BORDER_NODE_LABEL_TEXT_POSITION.getY() / 2), BORDER_NODE_LABEL_TEXT_BOUNDS);

        // increase the width and the height of the east border node
        NodeLayoutData eastBorderNode = borderNodes.get(3);
        resizeEvent = Optional.of(new ResizeEvent(eastBorderNode.getId(), ZERO_POSITION, Size.of(DEFAULT_BORDER_NODE_SIZE.getWidth() + 10, DEFAULT_BORDER_NODE_SIZE.getHeight() + 10)));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodeLabel(eastBorderNode.getLabel(), Position.at(eastBorderNode.getSize().getWidth(), eastBorderNode.getSize().getHeight()), BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    @Test
    public void testResizeParentNodeNorthWestEvent() {
        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = this.initializeDiagram();
        DiagramLayoutData initializeDiagram = incrementalLayoutConvertedDiagram.getDiagramLayoutData();
        String nodeId = initializeDiagram.getChildrenNodes().get(0).getId();
        List<NodeLayoutData> borderNodes = initializeDiagram.getChildrenNodes().get(0).getBorderNodes();

        ImageSizeProvider imageSizeProvider = new ImageSizeProvider();
        NodeSizeProvider nodeSizeProvider = new NodeSizeProvider(imageSizeProvider);
        BorderNodeLayoutEngine borderNodeLayoutEngine = new BorderNodeLayoutEngine(nodeSizeProvider);
        ImageNodeStyleSizeProvider imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(imageSizeProvider);
        ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider = () -> new LayoutEngineHandlerSwitch(borderNodeLayoutEngine, List.of(), imageNodeStyleSizeProvider);
        IncrementalLayoutEngine incrementalLayoutEngine = new IncrementalLayoutEngine(layoutEngineHandlerSwitchProvider);

        // Decrease the parent node size
        Optional<IDiagramEvent> resizeEvent = Optional.of(new ResizeEvent(nodeId, Position.at(100, 50), Size.of(150, 70)));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAfterResize(borderNodes);

        // Increase the parent node size to get the initial size
        resizeEvent = Optional.of(new ResizeEvent(nodeId, ZERO_POSITION, DEFAULT_NODE_SIZE));
        incrementalLayoutEngine.layout(resizeEvent, incrementalLayoutConvertedDiagram, new LayoutConfiguratorRegistry(List.of()).getDefaultLayoutConfigurator());

        this.checkBorderNodesAtInitialPosition(borderNodes);

        this.checkBorderNodeLabel(borderNodes.get(0).getLabel(), BORDER_NODE_LABEL_TEXT_POSITION, BORDER_NODE_LABEL_TEXT_BOUNDS);
    }

    private void checkBorderNodesAfterResize(List<NodeLayoutData> borderNodes) {
        assertThat(borderNodes.get(0).getPosition()).isEqualTo(Position.at(18, -12));
        assertThat(borderNodes.get(1).getPosition()).isEqualTo(Position.at(25.5, -12));
        assertThat(borderNodes.get(2).getPosition()).isEqualTo(Position.at(142, 25));
        assertThat(borderNodes.get(3).getPosition()).isEqualTo(Position.at(142, 60));
        assertThat(borderNodes.get(4).getPosition()).isEqualTo(Position.at(138, 62));
        assertThat(borderNodes.get(5).getPosition()).isEqualTo(Position.at(63, 62));
        assertThat(borderNodes.get(6).getPosition()).isEqualTo(Position.at(-16, 25));
        assertThat(borderNodes.get(7).getPosition()).isEqualTo(Position.at(-16, -10));
    }

    /**
     * Initialize the diagram with 1 node containing many border nodes.<br/>
     * The border nodes are voluntary at an invalid position to make sure they properly positioned after nay layout
     * event.
     */
    private IncrementalLayoutConvertedDiagram initializeDiagram() {
        DiagramLayoutData diagramLayoutData = this.createDiagramLayoutData();
        diagramLayoutData.setEdges(new ArrayList<>());

        NodeLayoutData nodeLayoutData = this.createNodeLayoutData(Position.at(100, 100), DEFAULT_NODE_SIZE, diagramLayoutData, NodeType.NODE_RECTANGLE);

        List<NodeLayoutData> borderNodes = nodeLayoutData.getBorderNodes();

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

        IncrementalLayoutConvertedDiagram incrementalLayoutConvertedDiagram = new IncrementalLayoutConvertedDiagram(diagramLayoutData, Map.of());
        return incrementalLayoutConvertedDiagram;
    }

    private DiagramLayoutData createDiagramLayoutData() {
        DiagramLayoutData diagramLayoutData = new DiagramLayoutData();
        diagramLayoutData.setId(UUID.randomUUID().toString());
        diagramLayoutData.setPosition(Position.at(0, 0));
        diagramLayoutData.setSize(Size.of(1000, 1000));
        diagramLayoutData.setChildrenNodes(new ArrayList<>());
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
                this.createLabelLayoutData(BORDER_NODE_LABEL_TEXT_POSITION, "any", BORDER_NODE_LABEL_TEXT_BOUNDS));
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
        nodeLayoutData.setBorderNodes(new ArrayList<>());
        nodeLayoutData.setLabel(this.createLabelLayoutData(Position.at(0, 0), "inside", new TextBounds(Size.of(0, 0), Position.at(0, 0))));
        nodeLayoutData.setResizedByUser(true); // Used to take account of the current size of the node.
        nodeLayoutData.setStyle(this.getStyle(nodeType));
        parent.getChildrenNodes().add(nodeLayoutData);
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

    private Position getRoundedPosition(Position position) {
        BigDecimal roundedX = BigDecimal.valueOf(position.getX()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedY = BigDecimal.valueOf(position.getY()).setScale(4, RoundingMode.HALF_UP);
        return Position.at(roundedX.doubleValue(), roundedY.doubleValue());
    }

    public INodeStyle getStyle(String nodeType) {
        INodeStyle nodeStyle = null;
        switch (nodeType) {
            case NodeType.NODE_RECTANGLE:
                nodeStyle = new TestDiagramBuilder().getRectangularNodeStyle();
                break;
            case NodeType.NODE_IMAGE:
                nodeStyle = new TestDiagramBuilder().getImageNodeStyle();
                break;
            case NodeType.NODE_ICON_LABEL:
                nodeStyle = IconLabelNodeStyle.newIconLabelNodeStyle().backgroundColor("transparent").build();
                break;
            default:
                nodeStyle = new TestDiagramBuilder().getRectangularNodeStyle();
                break;
        }
        return nodeStyle;
    }

}
