/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.EdgeLabelPlacement;
import org.eclipse.elk.graph.ElkConnectableShape;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.elk.graph.ElkGraphFactory;
import org.eclipse.elk.graph.ElkLabel;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.ElkPort;
import org.eclipse.elk.graph.properties.IProperty;
import org.eclipse.elk.graph.properties.Property;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.TextBounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ImageSizeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to convert the diagram into a graph of ELK objects. During the transformation, it will also pre-compute some
 * default sizes to help ELK perform the layout afterward.
 *
 * @author hmarchadour
 * @author sbegaudeau
 */
@Service
public class ELKDiagramConverter implements IELKDiagramConverter {

    public static final IProperty<String> PROPERTY_TYPE = new Property<>("org.eclipse.sirius.components.layout.type"); //$NON-NLS-1$

    public static final IProperty<Class<? extends ILayoutStrategy>> PROPERTY_CHILDREN_LAYOUT_STRATEGY = new Property<>("org.eclipse.sirius.components.layout.children.layout.strategy"); //$NON-NLS-1$

    public static final String DEFAULT_DIAGRAM_TYPE = "graph"; //$NON-NLS-1$

    public static final String DEFAULT_IMAGE_TYPE = "image:inside-center"; //$NON-NLS-1$

    private final TextBoundsService textBoundsService;

    private final ImageSizeProvider imageSizeProvider;

    private final ImageNodeStyleSizeProvider imageNodeStyleSizeProvider;

    private final Logger logger = LoggerFactory.getLogger(ELKDiagramConverter.class);

    public ELKDiagramConverter(TextBoundsService textBoundsService, ImageSizeProvider imageSizeProvider) {
        this.textBoundsService = Objects.requireNonNull(textBoundsService);
        this.imageSizeProvider = Objects.requireNonNull(imageSizeProvider);
        this.imageNodeStyleSizeProvider = new ImageNodeStyleSizeProvider(this.imageSizeProvider);
    }

    @Override
    public ELKConvertedDiagram convert(Diagram diagram) {
        Diagram initializedDiagram = this.initializeDiagram(diagram);

        ElkNode elkDiagram = this.convertDiagram(initializedDiagram);

        Map<String, ElkGraphElement> id2ElkGraphElements = new HashMap<>();
        Map<String, ElkConnectableShape> connectableShapeIndex = new LinkedHashMap<>();
        initializedDiagram.getNodes().stream().forEach(node -> this.convertNode(node, elkDiagram, connectableShapeIndex, id2ElkGraphElements));
        initializedDiagram.getEdges().stream().forEach(edge -> this.convertEdge(edge, elkDiagram, connectableShapeIndex, id2ElkGraphElements));

        return new ELKConvertedDiagram(elkDiagram, id2ElkGraphElements);
    }

    /**
     * Looks for {@link Node} with {@link ListLayoutStrategy} in the <em>diagram</em> in order to initialize them.
     *
     * @param diagram
     *            The {@link Diagram} in which {@link Node} with {@link ListLayoutStrategy} will be initialized
     * @return A new {@link Diagram} containing initialized {@link Node} with {@link ListLayoutStrategy}
     */
    private Diagram initializeDiagram(Diagram diagram) {
        List<Node> childNodes = this.initializeNodes(diagram.getNodes());
        // @formatter:off
        return Diagram.newDiagram(diagram)
                .nodes(childNodes)
                .build();
        // @formatter:on
    }

    /**
     * Looks for {@link Node} with {@link ListLayoutStrategy} in the <em>nodes</em> list in order to initialize them.
     *
     * <p>
     * If a node has children, the node is initialized.
     * </p>
     *
     * @param nodes
     *            the list of {@link Node} in which {@link Node}s with {@link ListLayoutStrategy} will be initialized
     * @return A new list of {@link Node} containing initialized {@link Node} with {@link ListLayoutStrategy}
     */
    private List<Node> initializeNodes(List<Node> nodes) {
        List<Node> childNodes = new ArrayList<>();
        for (Node node : nodes) {
            Node childNode = node;
            if (node.getChildrenLayoutStrategy() instanceof ListLayoutStrategy) {
                childNode = this.initializeNodeList(node);
            } else if (!node.getChildNodes().isEmpty()) {
                childNode = this.initializeNode(node);
            }
            childNodes.add(childNode);
        }
        return childNodes;
    }

    /**
     * Looks for {@link Node} with {@link ListLayoutStrategy} in the <em>node</em> children in order to initialize them.
     *
     * @param node
     *            the {@link Node} in which {@link Node} with {@link ListLayoutStrategy} children will be initialized
     * @return
     */
    private Node initializeNode(Node node) {
        List<Node> childNodes = this.initializeNodes(node.getChildNodes());
        // @formatter:off
        return Node.newNode(node)
                .childNodes(childNodes)
                .build();
        // @formatter:on
    }

    private double getLargestIconLabelNodeWidth(List<Node> iconLabelNodes) {
        // @formatter:off
        return iconLabelNodes.stream()
                .map(Node::getLabel)
                .map(this.textBoundsService::getBounds)
                .map(TextBounds::getSize)
                .mapToDouble(Size::getWidth)
                .map(width -> width + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_RIGHT + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT)
                .max()
                .orElse(-1);
        // @formatter:on

    }

    /**
     * Returns the size of the given {@link Node} with {@link ListLayoutStrategy}, which is the max value between the
     * highest children item width, the node label width and {@link} LayoutOptionValues#MIN_WIDTH_CONSTRAINT}.
     *
     * @param nodeList
     *            The {@link Node} with {@link ListLayoutStrategy} we want the width
     * @return the size of the given {@link Node} with {@link ListLayoutStrategy}
     */
    private double getNodeListWidth(Node nodeList) {
        TextBounds nodeListLabelTextBounds = this.textBoundsService.getBounds(nodeList.getLabel());
        double largestNodeListItemWidth = this.getLargestIconLabelNodeWidth(nodeList.getChildNodes());

        // @formatter:off
         return Arrays.asList(
                largestNodeListItemWidth,
                nodeListLabelTextBounds.getSize().getWidth() + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING * 2,
                LayoutOptionValues.MIN_WIDTH_CONSTRAINT)
              .stream()
              .mapToDouble(Number::doubleValue)
              .max()
              .orElse(LayoutOptionValues.MIN_WIDTH_CONSTRAINT);
         // @formatter:on
    }

    /**
     * Initializes the given {@link Node} with {@link ListLayoutStrategy} with size and its children with size and
     * position in order let ELK use the size and position for these elements.
     *
     * <p>
     * It is possible to let ELK use the size and position for these elements thanks to ELK options that have been used
     * in the ELK configuration.
     * </p>
     *
     * @see LayoutConfiguratorRegistry#getDefaultLayoutConfigurator()
     * @param nodeList
     *            The {@link Node} with {@link ListLayoutStrategy} to initialize
     * @return a new initialized {@link Node}
     */
    private Node initializeNodeList(Node nodeList) {
        List<Node> childNodes = nodeList.getChildNodes();

        double nodeListWidth = this.getNodeListWidth(nodeList);
        List<Node> nodeListItems = this.initializeIconLabelNodes(childNodes, nodeListWidth, nodeList.getLabel().getSize().getHeight());
        double nodeListHeight = this.getNodeListHeight(nodeListItems);

        Size nodelistSize = Size.of(nodeListWidth, nodeListHeight);

        // @formatter:off
        return Node.newNode(nodeList)
                .size(nodelistSize)
                .childNodes(nodeListItems)
                .build();
        // @formatter:on
    }

    /**
     * Returns the {@link Node} with {@link ListLayoutStrategy} height.
     *
     * <p>
     * The height of a {@link Node} with {@link ListLayoutStrategy} is the sum of the vertical position of the last
     * children, the height of the last children and {@link LayoutOptionValues#DEFAULT_ELK_PADDING}. The height cannot
     * be smaller than {@link LayoutOptionValues#MIN_HEIGHT_CONSTRAINT}.
     * </p>
     *
     * @param nodeListChildren
     *            The list of {@link Node} with {@link ListLayoutStrategy} children
     * @return the node {@link Node} with {@link ListLayoutStrategy} height
     */
    private double getNodeListHeight(List<Node> nodeListChildren) {
        double nodeListHeight = 0;
        if (!nodeListChildren.isEmpty()) {
            Node lastNodeListItem = nodeListChildren.get(nodeListChildren.size() - 1);
            nodeListHeight = lastNodeListItem.getPosition().getY() + lastNodeListItem.getSize().getHeight() + LayoutOptionValues.DEFAULT_ELK_PADDING;
        }

        if (nodeListHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            nodeListHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }
        return nodeListHeight;
    }

    private List<Node> initializeIconLabelNodes(List<Node> childNodes, double nodeListWidth, double nodeListLabelHeight) {
        List<Node> iconLabelNodes = new ArrayList<>();
        for (int i = 0; i < childNodes.size(); ++i) {
            Node iconLabelNode = childNodes.get(i);

            double iconLabelNodeHeight = this.getIconLabelNodeHeight(iconLabelNode);
            Size iconLabelNodeSize = Size.of(nodeListWidth - LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT, iconLabelNodeHeight);

            double iconLabelNodePosY = this.getCurrentListItemYPosition(iconLabelNodes, nodeListLabelHeight);
            Position iconLabelNodePosition = Position.at(LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT, iconLabelNodePosY);

            // @formatter:off
            Node newIconLabelNode = Node.newNode(iconLabelNode)
                    .size(iconLabelNodeSize)
                    .position(iconLabelNodePosition)
                    .build();
            // @formatter:on
            iconLabelNodes.add(newIconLabelNode);
        }
        return iconLabelNodes;
    }

    /**
     * Return the current list item Y position.
     *
     * <p>
     * If the list of eldest siblings is empty, the position of the current node child in the {@link Node} with
     * {@link ListLayoutStrategy} is the sum of {@link LayoutOptionValues#NODE_LIST_ELK_PADDING_TOP} and
     * {@link LayoutOptionValues#DEFAULT_ELK_NODE_LABELS_PADDING}. Otherwise, the list of the current node child in the
     * {@link Node} with {@link ListLayoutStrategy} is the sum of the Y position of the previous sibling, the height of
     * the previous sibling and the {@link LayoutOptionValues#NODE_LIST_ELK_NODE_NODE_GAP}.
     * </p>
     *
     * @param handledIconLabelNodes
     *            The list of the eldest siblings
     * @param nodeListLabelHeight
     *            The label height of the {@link Node} with {@link ListLayoutStrategy}
     * @return the current list item Y position
     */
    private double getCurrentListItemYPosition(List<Node> handledIconLabelNodes, double nodeListLabelHeight) {
        double iconLabelNodeYPosition = 0;
        if (handledIconLabelNodes.isEmpty()) {
            iconLabelNodeYPosition = nodeListLabelHeight + LayoutOptionValues.NODE_LIST_ELK_PADDING_TOP + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        } else {
            Node previousIconLabelNodeSibling = handledIconLabelNodes.get(handledIconLabelNodes.size() - 1);
            iconLabelNodeYPosition = previousIconLabelNodeSibling.getPosition().getY() + previousIconLabelNodeSibling.getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP;
        }
        return iconLabelNodeYPosition;
    }

    private double getIconLabelNodeHeight(Node nodeListItem) {
        TextBounds iconLabelNodeTextBounds = this.textBoundsService.getBounds(nodeListItem.getLabel());
        double iconLabelNodeHeight = iconLabelNodeTextBounds.getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_TOP
                + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM;
        return iconLabelNodeHeight;
    }

    private ElkNode convertDiagram(Diagram diagram) {
        ElkNode elkDiagram = ElkGraphFactory.eINSTANCE.createElkNode();
        elkDiagram.setIdentifier(diagram.getId());
        elkDiagram.setProperty(PROPERTY_TYPE, DEFAULT_DIAGRAM_TYPE);
        return elkDiagram;
    }

    private void convertNode(Node node, ElkNode parent, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkNode elkNode = ElkGraphFactory.eINSTANCE.createElkNode();
        elkNode.setIdentifier(node.getId());
        elkNode.setProperty(PROPERTY_TYPE, node.getType());
        if (node.getChildrenLayoutStrategy() != null) {
            elkNode.setProperty(PROPERTY_CHILDREN_LAYOUT_STRATEGY, node.getChildrenLayoutStrategy().getClass());
        }

        TextBounds textBounds = this.textBoundsService.getBounds(node.getLabel());

        if (node.getChildrenLayoutStrategy() instanceof ListLayoutStrategy) {
            elkNode.setDimensions(node.getSize().getWidth(), node.getSize().getHeight());
        } else if (NodeType.NODE_ICON_LABEL.equals(node.getType())) {
            elkNode.setDimensions(node.getSize().getWidth(), node.getSize().getHeight());
            elkNode.setLocation(node.getPosition().getX(), node.getPosition().getY());
        } else {
            double width = textBounds.getSize().getWidth();
            double height = textBounds.getSize().getHeight();
            elkNode.setDimensions(width, height);
        }

        elkNode.setParent(parent);
        connectableShapeIndex.put(elkNode.getIdentifier(), elkNode);

        if (node.getStyle() instanceof ImageNodeStyle) {
            ImageNodeStyle imageNodeStyle = (ImageNodeStyle) node.getStyle();

            ElkNode elkImage = ElkGraphFactory.eINSTANCE.createElkNode();
            elkImage.setIdentifier(node.getId() + "_image"); //$NON-NLS-1$
            elkImage.setProperty(PROPERTY_TYPE, DEFAULT_IMAGE_TYPE);

            Size imageSize = this.imageNodeStyleSizeProvider.getSize(imageNodeStyle);
            elkImage.setDimensions(imageSize.getWidth(), imageSize.getHeight());

            elkImage.setParent(elkNode);
        }

        node.getBorderNodes().stream().forEach(borderNode -> this.convertBorderNode(borderNode, elkNode, connectableShapeIndex, id2ElkGraphElements));
        node.getChildNodes().stream().forEach(childNode -> this.convertNode(childNode, elkNode, connectableShapeIndex, id2ElkGraphElements));

        this.convertLabel(node.getLabel(), textBounds, elkNode, id2ElkGraphElements, null);

        id2ElkGraphElements.put(node.getId(), elkNode);
    }

    private void convertBorderNode(Node borderNode, ElkNode elkNode, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkPort elkPort = ElkGraphFactory.eINSTANCE.createElkPort();
        elkPort.setIdentifier(borderNode.getId());
        elkPort.setProperty(PROPERTY_TYPE, borderNode.getType());
        if (borderNode.getChildrenLayoutStrategy() != null) {
            elkPort.setProperty(PROPERTY_CHILDREN_LAYOUT_STRATEGY, borderNode.getChildrenLayoutStrategy().getClass());
        }

        TextBounds textBounds = this.textBoundsService.getBounds(borderNode.getLabel());
        double width = borderNode.getSize().getWidth();
        double height = borderNode.getSize().getHeight();

        elkPort.setDimensions(width, height);
        elkPort.setParent(elkNode);

        connectableShapeIndex.put(elkPort.getIdentifier(), elkPort);

        if (borderNode.getStyle() instanceof ImageNodeStyle) {
            Size imageSize = this.imageNodeStyleSizeProvider.getSize((ImageNodeStyle) borderNode.getStyle());
            elkPort.setDimensions(imageSize.getWidth(), imageSize.getHeight());
        }

        this.convertLabel(borderNode.getLabel(), textBounds, elkPort, id2ElkGraphElements, null);

        id2ElkGraphElements.put(borderNode.getId(), elkPort);
    }

    private void convertLabel(Label label, TextBounds textBounds, ElkGraphElement elkGraphElement, Map<String, ElkGraphElement> id2ElkGraphElements, EdgeLabelPlacement placement) {
        ElkLabel elkLabel = ElkGraphFactory.eINSTANCE.createElkLabel();
        elkLabel.setIdentifier(label.getId());
        elkLabel.setProperty(PROPERTY_TYPE, label.getType());
        elkLabel.setDimensions(textBounds.getSize().getWidth(), textBounds.getSize().getHeight());

        if (label.getText().isEmpty() && !label.getStyle().getIconURL().isEmpty()) {
            elkLabel.setText(" "); //$NON-NLS-1$
        } else {
            elkLabel.setText(label.getText());
        }

        elkLabel.setParent(elkGraphElement);

        if (placement != null) {
            elkLabel.setProperty(CoreOptions.EDGE_LABELS_PLACEMENT, placement);
        }

        elkLabel.eAdapters().add(new AlignmentHolder(textBounds.getAlignment()));

        id2ElkGraphElements.put(label.getId(), elkLabel);
    }

    private void convertEdge(Edge edge, ElkNode elkDiagram, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkEdge elkEdge = ElkGraphFactory.eINSTANCE.createElkEdge();
        elkEdge.setIdentifier(edge.getId());

        ElkConnectableShape source = connectableShapeIndex.get(edge.getSourceId());
        ElkConnectableShape target = connectableShapeIndex.get(edge.getTargetId());

        if (source != null) {
            elkEdge.getSources().add(source);
        } else {
            String pattern = "The source with the id {} has not been found"; //$NON-NLS-1$
            this.logger.warn(pattern, edge.getSourceId());
        }

        if (target != null) {
            elkEdge.getTargets().add(target);
        } else {
            String pattern = "The target with the id {} has not been found"; //$NON-NLS-1$
            this.logger.warn(pattern, edge.getTargetId());
        }

        if (source != null && target != null) {
            ElkNode container = ElkGraphUtil.findBestEdgeContainment(elkEdge);
            if (container != null) {
                elkEdge.setContainingNode(container);
            } else {
                elkEdge.setContainingNode(elkDiagram);
            }
        }

        Optional.ofNullable(edge.getBeginLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.TAIL));
        Optional.ofNullable(edge.getCenterLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.CENTER));
        Optional.ofNullable(edge.getEndLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.HEAD));

        id2ElkGraphElements.put(edge.getId(), elkEdge);
    }
}
