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
package org.eclipse.sirius.web.diagrams.layout;

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
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Edge;
import org.eclipse.sirius.web.diagrams.ImageNodeStyle;
import org.eclipse.sirius.web.diagrams.Label;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.NodeType;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.TextBounds;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageNodeStyleSizeProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.ImageSizeProvider;
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
public class ELKDiagramConverter {

    public static final IProperty<String> PROPERTY_TYPE = new Property<>("org.eclipse.sirius.web.layout.type"); //$NON-NLS-1$

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
     * Looks for {@link NodeType#NODE_LIST} {@link Node} in the <em>diagram</em> in order to initialize them.
     *
     * @param diagram
     *            The {@link Diagram} in which {@link NodeType#NODE_LIST} {@link Node} will be initialized
     * @return A new {@link Diagram} containing initialized {@link NodeType#NODE_LIST} {@link Node}
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
     * Looks for {@link NodeType#NODE_LIST} {@link Node} in the <em>nodes</em> list in order to initialize them.
     *
     * <p>
     * If a node has children, the node is initialized.
     * </p>
     *
     * @param nodes
     *            the list of {@link Node} in which {@link NodeType#NODE_LIST} {@link Node} will be initialized
     * @return A new list of {@link Node} containing initialized {@link NodeType#NODE_LIST} {@link Node}
     */
    private List<Node> initializeNodes(List<Node> nodes) {
        List<Node> childNodes = new ArrayList<>();
        for (Node node : nodes) {
            Node childNode = node;
            if (NodeType.NODE_LIST.equals(node.getType())) {
                childNode = this.initializeNodeList(node);
            } else if (!node.getChildNodes().isEmpty()) {
                childNode = this.initializeNode(node);
            }
            childNodes.add(childNode);
        }
        return childNodes;
    }

    /**
     * Looks for {@link NodeType#NODE_LIST} Node in the <em>node</em> children in order to initialize them.
     *
     * @param node
     *            the {@link Node} in which {@link NodeType#NODE_LIST} children {@link Node} will be initialized
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

    private double getLargestNodeListItemWidth(List<Node> nodeListItems) {
        // @formatter:off
        return nodeListItems.stream()
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
     * Returns the size of the given {@link NodeType#NODE_LIST}, which is the max value between the highest children
     * item width, the node label width and {@link} LayoutOptionValues#MIN_WIDTH_CONSTRAINT}.
     *
     * @param nodeList
     *            The node list we want the width
     * @return the size of the given {@link NodeType#NODE_LIST}
     */
    private double getNodeListWidth(Node nodeList) {
        TextBounds nodeListLabelTextBounds = this.textBoundsService.getBounds(nodeList.getLabel());
        double largestNodeListItemWidth = this.getLargestNodeListItemWidth(nodeList.getChildNodes());

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
     * Initializes the given {@link NodeType#NODE_LIST} with size and its {@link NodeType#NODE_LIST_ITEM} with size and
     * position in order let ELK use the size and position for these elements.
     *
     * <p>
     * It is possible to let ELK use the size and position for these elements thanks to ELK options that have been used
     * in the ELK configuration.
     * </p>
     *
     * @see LayoutConfiguratorRegistry#getDefaultLayoutConfigurator()
     * @param nodeList
     *            The {@link NodeType#NODE_LIST} node to initialize
     * @return a new initialized {@link Node}
     */
    private Node initializeNodeList(Node nodeList) {
        List<Node> childNodes = nodeList.getChildNodes();

        double nodeListWidth = this.getNodeListWidth(nodeList);
        List<Node> nodeListItems = this.initializeNodeListItems(childNodes, nodeListWidth, nodeList.getLabel().getSize().getHeight());
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
     * Returns the node list height.
     *
     * <p>
     * The height of a node list is the sum of the vertical position of the last children, the height of the last
     * children and {@link LayoutOptionValues#DEFAULT_ELK_PADDING}. The height cannot be smaller than
     * {@link LayoutOptionValues#MIN_HEIGHT_CONSTRAINT}.
     * </p>
     *
     * @param nodeListItems
     *            The list of children
     * @return the node list height
     */
    private double getNodeListHeight(List<Node> nodeListItems) {
        double nodeListHeight = 0;
        if (!nodeListItems.isEmpty()) {
            Node lastNodeListItem = nodeListItems.get(nodeListItems.size() - 1);
            nodeListHeight = lastNodeListItem.getPosition().getY() + lastNodeListItem.getSize().getHeight() + LayoutOptionValues.DEFAULT_ELK_PADDING;
        }

        if (nodeListHeight < LayoutOptionValues.MIN_HEIGHT_CONSTRAINT) {
            nodeListHeight = LayoutOptionValues.MIN_HEIGHT_CONSTRAINT;
        }
        return nodeListHeight;
    }

    private List<Node> initializeNodeListItems(List<Node> childNodes, double nodeListWidth, double nodeListLabelHeight) {
        List<Node> nodeListItems = new ArrayList<>();
        for (int i = 0; i < childNodes.size(); ++i) {
            Node nodeListItem = childNodes.get(i);

            double nodeListItemHeight = this.getNodeListItemHeight(nodeListItem);
            Size nodelistItemSize = Size.of(nodeListWidth - LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT, nodeListItemHeight);

            double nodeListItemPosY = this.getCurrentListItemYPosition(nodeListItems, nodeListLabelHeight);
            Position nodeListItemPosition = Position.at(LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_LEFT, nodeListItemPosY);

            // @formatter:off
            Node newNodeListItem = Node.newNode(nodeListItem)
                    .size(nodelistItemSize)
                    .position(nodeListItemPosition)
                    .build();
            // @formatter:on
            nodeListItems.add(newNodeListItem);
        }
        return nodeListItems;
    }

    /**
     * Return the current list item Y position.
     *
     * <p>
     * If the list of eldest siblings is empty the position of the current list item in the node list is the sum of
     * {@link LayoutOptionValues#NODE_LIST_ELK_PADDING_TOP} and
     * {@link LayoutOptionValues#DEFAULT_ELK_NODE_LABELS_PADDING}. Otherwise, the list of the current list item in the
     * node list is the sum of the Y position of the previous sibling, the height of the previous sibling and the
     * {@link LayoutOptionValues#NODE_LIST_ELK_NODE_NODE_GAP}.
     * </p>
     *
     * @param handledListItems
     *            The list of the eldest siblings
     * @param nodeListLabelHeight
     * @return the current list item Y position
     */
    private double getCurrentListItemYPosition(List<Node> handledListItems, double nodeListLabelHeight) {
        double nodeListItemYPosition = 0;
        if (handledListItems.isEmpty()) {
            nodeListItemYPosition = nodeListLabelHeight + LayoutOptionValues.NODE_LIST_ELK_PADDING_TOP + LayoutOptionValues.DEFAULT_ELK_NODE_LABELS_PADDING;
        } else {
            Node previousNodeListItemSibling = handledListItems.get(handledListItems.size() - 1);
            nodeListItemYPosition = previousNodeListItemSibling.getPosition().getY() + previousNodeListItemSibling.getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_NODE_GAP;
        }
        return nodeListItemYPosition;
    }

    private double getNodeListItemHeight(Node nodeListItem) {
        TextBounds nodeListItemTextBounds = this.textBoundsService.getBounds(nodeListItem.getLabel());
        double nodeListItemHeight = nodeListItemTextBounds.getSize().getHeight() + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_TOP
                + LayoutOptionValues.NODE_LIST_ELK_NODE_LABELS_PADDING_BOTTOM;
        return nodeListItemHeight;
    }

    private ElkNode convertDiagram(Diagram diagram) {
        ElkNode elkDiagram = ElkGraphFactory.eINSTANCE.createElkNode();
        elkDiagram.setIdentifier(diagram.getId().toString());
        elkDiagram.setProperty(PROPERTY_TYPE, DEFAULT_DIAGRAM_TYPE);
        return elkDiagram;
    }

    private void convertNode(Node node, ElkNode parent, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkNode elkNode = ElkGraphFactory.eINSTANCE.createElkNode();
        elkNode.setIdentifier(node.getId().toString());
        elkNode.setProperty(PROPERTY_TYPE, node.getType());

        TextBounds textBounds = this.textBoundsService.getBounds(node.getLabel());

        if (NodeType.NODE_LIST.equals(node.getType())) {
            elkNode.setDimensions(node.getSize().getWidth(), node.getSize().getHeight());
        } else if (NodeType.NODE_LIST_ITEM.equals(node.getType())) {
            elkNode.setDimensions(node.getSize().getWidth(), node.getSize().getHeight());
            elkNode.setLocation(node.getPosition().getX(), node.getPosition().getY());
        } else {
            double width = Math.max(textBounds.getSize().getWidth(), node.getSize().getWidth());
            double height = Math.max(textBounds.getSize().getHeight(), node.getSize().getHeight());
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

        id2ElkGraphElements.put(node.getId().toString(), elkNode);
    }

    private void convertBorderNode(Node borderNode, ElkNode elkNode, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkPort elkPort = ElkGraphFactory.eINSTANCE.createElkPort();
        elkPort.setIdentifier(borderNode.getId().toString());
        elkPort.setProperty(PROPERTY_TYPE, borderNode.getType());

        TextBounds textBounds = this.textBoundsService.getBounds(borderNode.getLabel());
        double width = Math.max(textBounds.getSize().getWidth(), borderNode.getSize().getWidth());
        double height = Math.max(textBounds.getSize().getHeight(), borderNode.getSize().getHeight());

        elkPort.setDimensions(width, height);
        elkPort.setParent(elkNode);

        connectableShapeIndex.put(elkPort.getIdentifier(), elkPort);

        if (borderNode.getStyle() instanceof ImageNodeStyle) {
            Size imageSize = this.imageNodeStyleSizeProvider.getSize((ImageNodeStyle) borderNode.getStyle());
            elkPort.setDimensions(imageSize.getWidth(), imageSize.getHeight());
        }

        this.convertLabel(borderNode.getLabel(), textBounds, elkPort, id2ElkGraphElements, null);

        id2ElkGraphElements.put(borderNode.getId().toString(), elkPort);
    }

    private void convertLabel(Label label, TextBounds textBounds, ElkGraphElement elkGraphElement, Map<String, ElkGraphElement> id2ElkGraphElements, EdgeLabelPlacement placement) {
        ElkLabel elkLabel = ElkGraphFactory.eINSTANCE.createElkLabel();
        elkLabel.setIdentifier(label.getId().toString());
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

        id2ElkGraphElements.put(label.getId().toString(), elkLabel);
    }

    private void convertEdge(Edge edge, ElkNode elkDiagram, Map<String, ElkConnectableShape> connectableShapeIndex, Map<String, ElkGraphElement> id2ElkGraphElements) {
        ElkEdge elkEdge = ElkGraphFactory.eINSTANCE.createElkEdge();
        elkEdge.setIdentifier(edge.getId().toString());

        ElkConnectableShape source = connectableShapeIndex.get(edge.getSourceId().toString());
        ElkConnectableShape target = connectableShapeIndex.get(edge.getTargetId().toString());

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

        Optional.ofNullable(edge.getBeginLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.HEAD));
        Optional.ofNullable(edge.getCenterLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.CENTER));
        Optional.ofNullable(edge.getEndLabel()).ifPresent(label -> this.convertLabel(label, this.textBoundsService.getBounds(label), elkEdge, id2ElkGraphElements, EdgeLabelPlacement.TAIL));

        id2ElkGraphElements.put(edge.getId().toString(), elkEdge);
    }
}
