/*******************************************************************************
 * Copyright (c) 2021, 2022 THALES GLOBAL SERVICES.
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.DoublePositionEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ResizeEvent;
import org.eclipse.sirius.components.diagrams.events.UpdateEdgeRoutingPointsEvent;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.api.Geometry;
import org.eclipse.sirius.components.diagrams.layout.api.PointOnRectangleInfo;
import org.eclipse.sirius.components.diagrams.layout.api.RectangleSide;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ILayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.BorderNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.EdgeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.EdgeRoutingPointsProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.ICustomNodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodePositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.updater.ContainmentUpdater;
import org.eclipse.sirius.components.diagrams.layout.incremental.updater.OverlapsUpdater;
import org.springframework.stereotype.Service;

/**
 * The engine that computes the incremental layout, using informations from:
 * <ul>
 * <li>the UI: created nodes, moved nodes...</li>
 * <li>the existing layout data</li>
 * <ul>
 *
 * @author wpiers
 */
@Service
public class IncrementalLayoutEngine {

    /**
     * The minimal distance between nodes.
     */
    public static final double NODES_GAP = 30;

    private NodeLabelPositionProvider nodeLabelPositionProvider;

    private BorderNodeLabelPositionProvider borderNodeLabelPositionProvider;

    private final EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

    private EdgeLabelPositionProvider edgeLabelPositionProvider;

    private final NodePositionProvider nodePositionProvider = new NodePositionProvider();

    private final NodeSizeProvider nodeSizeProvider;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    public IncrementalLayoutEngine(NodeSizeProvider nodeSizeProvider, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders) {
        this.nodeSizeProvider = Objects.requireNonNull(nodeSizeProvider);
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
    }

    public void layout(Optional<IDiagramEvent> optionalDiagramElementEvent, IncrementalLayoutConvertedDiagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
        this.borderNodeLabelPositionProvider = new BorderNodeLabelPositionProvider();
        this.nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);
        this.edgeLabelPositionProvider = new EdgeLabelPositionProvider(layoutConfigurator);
        DiagramLayoutData diagramLayoutData = diagram.getDiagramLayoutData();
        Optional<Position> optionalDelta = this.getDeltaFromMoveEvent(optionalDiagramElementEvent, diagram);

        // first we layout all the nodes
        for (NodeLayoutData node : diagramLayoutData.getChildrenNodes()) {
            this.layoutNode(optionalDiagramElementEvent, node, layoutConfigurator);
        }

        // resolve overlaps due to previous changes
        new OverlapsUpdater().update(diagramLayoutData);

        // resize according to the content
        new ContainmentUpdater().update(diagramLayoutData);

        // finally we recompute the edges that needs to
        for (EdgeLayoutData edge : diagramLayoutData.getEdges()) {
            if (this.shouldLayoutEdge(optionalDiagramElementEvent, edge)) {
                this.layoutEdge(optionalDiagramElementEvent, optionalDelta, edge);
            }
        }
    }

    private Optional<Position> getDeltaFromMoveEvent(Optional<IDiagramEvent> optionalDiagramElementEvent, IncrementalLayoutConvertedDiagram diagram) {
        Map<String, ILayoutData> id2LayoutData = diagram.getId2LayoutData();
        // @formatter:off
        return optionalDiagramElementEvent.filter(MoveEvent.class::isInstance)
                .map(MoveEvent.class::cast)
                .map(moveEvent -> {
            ILayoutData iLayoutData = id2LayoutData.get(moveEvent.getNodeId());
            if (iLayoutData instanceof NodeLayoutData) {
                NodeLayoutData nodeLayoutData = (NodeLayoutData) iLayoutData;
                Position fromPosition = nodeLayoutData.getPosition();
                Position toPosition = moveEvent.getNewPosition();
                return Position.at(toPosition.getX() - fromPosition.getX(), toPosition.getY() - fromPosition.getY());
            }
            return null;
        });
        // @formatter:on
    }

    private boolean shouldLayoutEdge(Optional<IDiagramEvent> optionalDiagramElementEvent, EdgeLayoutData edge) {
        boolean shouldLayoutEdge = false;

        shouldLayoutEdge = shouldLayoutEdge || this.hasChanged(edge.getSource());
        shouldLayoutEdge = shouldLayoutEdge || this.hasChanged(edge.getTarget());
        shouldLayoutEdge = shouldLayoutEdge || !this.isLabelPositioned(edge);
        shouldLayoutEdge = shouldLayoutEdge || this.hasRoutingPointsToUpdate(optionalDiagramElementEvent, edge);
        shouldLayoutEdge = shouldLayoutEdge || !this.isEdgePositioned(edge);

        return shouldLayoutEdge;
    }

    /**
     * Used to support not positioned edges, namely edges from old diagram.
     *
     * This can be removed when we will consider all diagram should have been migrated
     */
    private boolean isEdgePositioned(EdgeLayoutData edge) {
        return edge.getSourceAnchorRelativePosition() != null && edge.getTargetAnchorRelativePosition() != null;
    }

    private boolean isLabelPositioned(EdgeLayoutData edge) {
        if (edge.getCenterLabel() != null) {
            Position position = edge.getCenterLabel().getPosition();
            return position.getX() != -1 || position.getY() != -1;
        }
        return false;
    }

    private boolean hasRoutingPointsToUpdate(Optional<IDiagramEvent> diagramEvent, EdgeLayoutData edge) {
        // @formatter:off
        return diagramEvent.filter(UpdateEdgeRoutingPointsEvent.class::isInstance)
                .map(UpdateEdgeRoutingPointsEvent.class::cast)
                .map(UpdateEdgeRoutingPointsEvent::getEdgeId)
                .filter(edge.getId()::equals)
                .isPresent();
        // @formatter:on
    }

    private void layoutNode(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        Bounds initialNodeBounds = Bounds.newBounds().position(node.getPosition()).size(node.getSize()).build();
        // first layout child nodes
        for (NodeLayoutData childNode : node.getChildrenNodes()) {
            if (NodeType.NODE_ICON_LABEL.equals(childNode.getNodeType())) {
                this.layoutIconLabelNode(optionalDiagramElementEvent, childNode, layoutConfigurator);
            } else {
                this.layoutNode(optionalDiagramElementEvent, childNode, layoutConfigurator);
            }
        }

        // compute the node size according to what has been done in the previous steps
        Size size = this.nodeSizeProvider.getSize(optionalDiagramElementEvent, node, layoutConfigurator);
        if (!this.getRoundedSize(size).equals(this.getRoundedSize(node.getSize()))) {
            node.setSize(size);
            node.setChanged(true);
        }
        // recompute the node position
        Position position = this.nodePositionProvider.getPosition(optionalDiagramElementEvent, node);
        if (!position.equals(node.getPosition())) {
            node.setPosition(position);
            node.setChanged(true);
            node.setPinned(true);
        }

        // resolve overlaps due to previous changes
        new OverlapsUpdater().update(node);

        // resize / change position according to the content
        new ContainmentUpdater().update(node);

        // update the border node once the current node bounds are updated
        Bounds newBounds = Bounds.newBounds().position(node.getPosition()).size(node.getSize()).build();
        List<BorderNodesOnSide> borderNodesOnSide = this.layoutBorderNodes(optionalDiagramElementEvent, node.getBorderNodes(), initialNodeBounds, newBounds, layoutConfigurator);

        // recompute the label
        if (node.getLabel() != null) {
            // @formatter:off
            Position nodeLabelPosition = this.customLabelPositionProviders.stream()
                    .map(customLabelPositionProvider -> customLabelPositionProvider.getLabelPosition(layoutConfigurator, node.getLabel().getTextBounds().getSize(), node.getSize(),
                            node.getNodeType(), node.getStyle()))
                    .flatMap(Optional::stream)
                    .findFirst()
                    .orElseGet(() -> this.nodeLabelPositionProvider.getPosition(node, node.getLabel(), borderNodesOnSide));
            // @formatter:on
            node.getLabel().setPosition(nodeLabelPosition);
        }
    }

    private void layoutIconLabelNode(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        Bounds initialNodeBounds = Bounds.newBounds().position(node.getPosition()).size(node.getSize()).build();

        Size size = this.nodeSizeProvider.getSize(optionalDiagramElementEvent, node, layoutConfigurator);
        if (!this.getRoundedSize(size).equals(this.getRoundedSize(node.getSize()))) {
            node.setSize(size);
            node.setChanged(true);
        }

        Position position = this.nodePositionProvider.getPosition(optionalDiagramElementEvent, node);
        if (!position.equals(node.getPosition())) {
            node.setPosition(position);
            node.setChanged(true);
            node.setPinned(true);
        }

        // update the border node once the current node bounds are updated
        Bounds newBounds = Bounds.newBounds().position(node.getPosition()).size(node.getSize()).build();
        List<BorderNodesOnSide> borderNodesOnSide = this.layoutBorderNodes(optionalDiagramElementEvent, node.getBorderNodes(), initialNodeBounds, newBounds, layoutConfigurator);

        // recompute the label
        if (node.getLabel() != null) {
            node.getLabel().setPosition(this.nodeLabelPositionProvider.getPosition(node, node.getLabel(), borderNodesOnSide));
        }
    }

    /**
     * Update the border nodes position according to the side length change where it is located.<br>
     * The aim is to keep the positioning ratio of the border node on its side.
     */
    private List<BorderNodesOnSide> layoutBorderNodes(Optional<IDiagramEvent> optionalDiagramElementEvent, List<NodeLayoutData> borderNodesLayoutData, Bounds initialNodeBounds, Bounds newNodeBounds,
            ISiriusWebLayoutConfigurator layoutConfigurator) {
        List<BorderNodesOnSide> borderNodesPerSide = new ArrayList<>();
        if (!borderNodesLayoutData.isEmpty()) {
            for (NodeLayoutData nodeLayoutData : borderNodesLayoutData) {
                // 1- update the position of the border node if it has been explicitly moved
                this.updateBorderNodePosition(optionalDiagramElementEvent, nodeLayoutData);

                Size size = this.nodeSizeProvider.getSize(optionalDiagramElementEvent, nodeLayoutData, layoutConfigurator);
                if (!this.getRoundedSize(size).equals(this.getRoundedSize(nodeLayoutData.getSize()))) {
                    nodeLayoutData.setSize(size);
                    nodeLayoutData.setChanged(true);
                }
            }

            // 2- recompute the border node
            borderNodesPerSide = this.snapBorderNodes(borderNodesLayoutData, initialNodeBounds.getSize(), layoutConfigurator);

            // 3 - move the border node along the side according to the side change
            this.updateBorderNodeAccordingParentResize(optionalDiagramElementEvent, initialNodeBounds, newNodeBounds, borderNodesPerSide, borderNodesLayoutData.get(0).getParent().getId());

            // 4- set the label position if the border is newly created
            this.updateBorderNodeLabel(optionalDiagramElementEvent, borderNodesPerSide);
        }
        return borderNodesPerSide;
    }

    private void updateBorderNodeLabel(Optional<IDiagramEvent> optionalDiagramElementEvent, List<BorderNodesOnSide> borderNodesPerSideList) {

        for (BorderNodesOnSide borderNodesOnSide : borderNodesPerSideList) {
            RectangleSide side = borderNodesOnSide.getSide();
            List<NodeLayoutData> borderNodes = borderNodesOnSide.getBorderNodes();
            for (NodeLayoutData borderNodeLayoutData : borderNodes) {
                this.borderNodeLabelPositionProvider.updateLabelPosition(optionalDiagramElementEvent, side, borderNodeLayoutData);
            }
        }
    }

    /**
     * Move the border node along the side according to the parent Size changes.
     */
    private void updateBorderNodeAccordingParentResize(Optional<IDiagramEvent> optionalDiagramElementEvent, Bounds initialNodeBounds, Bounds newNodeBounds,
            List<BorderNodesOnSide> borderNodesPerSideList, String parentId) {
        // @formatter:off
        boolean isParentRectangleResized = optionalDiagramElementEvent
            .filter(ResizeEvent.class::isInstance)
            .map(ResizeEvent.class::cast)
            .map(ResizeEvent::getNodeId)
            .filter(parentId::equals)
            .isPresent();
        // @formatter:on

        if (isParentRectangleResized && !initialNodeBounds.equals(newNodeBounds)) {
            EnumMap<RectangleSide, Double> sideHomotheticRatio = this.getHomotheticRatio(initialNodeBounds.getSize(), newNodeBounds.getSize());

            Size initialSize = initialNodeBounds.getSize();
            Size newSize = newNodeBounds.getSize();

            for (BorderNodesOnSide borderNodesOnSide : borderNodesPerSideList) {
                RectangleSide side = borderNodesOnSide.getSide();
                List<NodeLayoutData> borderNodes = borderNodesOnSide.getBorderNodes();
                double homotheticRatio = sideHomotheticRatio.get(side);
                for (NodeLayoutData borderNodeLayoutData : borderNodes) {
                    // The border node position is done in the parent node coordinate system
                    Position position = borderNodeLayoutData.getPosition();
                    Size size = borderNodeLayoutData.getSize();
                    if (RectangleSide.NORTH.equals(side)) {
                        borderNodeLayoutData.setPosition(Position.at((position.getX() + size.getWidth() / 2) * homotheticRatio - size.getWidth() / 2, position.getY()));
                    } else if (RectangleSide.SOUTH.equals(side)) {
                        double dySouthShift = newSize.getHeight() - initialSize.getHeight();
                        borderNodeLayoutData.setPosition(Position.at((position.getX() + size.getWidth() / 2) * homotheticRatio - size.getWidth() / 2, position.getY() + dySouthShift));
                    } else if (RectangleSide.WEST.equals(side)) {
                        borderNodeLayoutData.setPosition(Position.at(position.getX(), (position.getY() + size.getHeight() / 2) * homotheticRatio - size.getHeight() / 2));
                    } else if (RectangleSide.EAST.equals(side)) {
                        double dxEastShift = newSize.getWidth() - initialSize.getWidth();
                        borderNodeLayoutData.setPosition(Position.at(position.getX() + dxEastShift, (position.getY() + size.getHeight() / 2) * homotheticRatio - size.getHeight() / 2));
                    }
                }
            }
        }
    }

    private void updateBorderNodePosition(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData nodeLayoutData) {
        // @formatter:off
        optionalDiagramElementEvent.filter(MoveEvent.class::isInstance)
            .map(MoveEvent.class::cast)
            .map(MoveEvent::getNodeId)
            .filter(nodeLayoutData.getId()::equals)
            .ifPresent(nodeId  -> {
                Position position = this.nodePositionProvider.getPosition(optionalDiagramElementEvent, nodeLayoutData);
                if (!position.equals(nodeLayoutData.getPosition())) {
                    nodeLayoutData.setPosition(position);
                    nodeLayoutData.setChanged(true);
                    nodeLayoutData.setPinned(true);
                }
        });
        // @formatter:on
    }

    /**
     * Update the border node by snapping it to the parentRectangle, that is moving it to the closest point of the
     * parentRectangle.
     *
     * @param borderNodesLayoutData
     *            the border nodes which position is given in the rectangle upper right corner coordinates system
     * @return for each side of the given parentRectangle, the list of the updates border node
     */
    private List<BorderNodesOnSide> snapBorderNodes(List<NodeLayoutData> borderNodesLayoutData, Size parentRectangle, ISiriusWebLayoutConfigurator layoutConfigurator) {
        EnumMap<RectangleSide, List<NodeLayoutData>> borderNodesPerSide = new EnumMap<>(RectangleSide.class);

        Geometry geometry = new Geometry();

        for (NodeLayoutData borderNodeLayoutData : borderNodesLayoutData) {
            double portOffset = layoutConfigurator.configureByType(borderNodeLayoutData.getNodeType()).getProperty(CoreOptions.PORT_BORDER_OFFSET).doubleValue();

            Bounds borderNodeRectangle = Bounds.newBounds().position(borderNodeLayoutData.getPosition()).size(borderNodeLayoutData.getSize()).build();
            PointOnRectangleInfo borderNodePositionOnSide = geometry.snapBorderNodeOnRectangle(borderNodeRectangle, parentRectangle, portOffset);
            // update the border node
            borderNodeLayoutData.setPosition(borderNodePositionOnSide.getPosition());

            borderNodesPerSide.computeIfAbsent(borderNodePositionOnSide.getSide(), side -> new ArrayList<>());
            borderNodesPerSide.get(borderNodePositionOnSide.getSide()).add(borderNodeLayoutData);
        }

        // @formatter:off
        return borderNodesPerSide.entrySet().stream()
                .map(entry -> new BorderNodesOnSide(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        // @formatter:on
    }

    /**
     * This method compares two rectangles and for each side return the homothetic ratio.
     */
    private EnumMap<RectangleSide, Double> getHomotheticRatio(Size rectangle1, Size rectangle2) {
        EnumMap<RectangleSide, Double> sideToHomotheticRatio = new EnumMap<>(RectangleSide.class);
        double initialHeight = rectangle1.getHeight();
        double initialWidth = rectangle1.getWidth();
        double newHeight = rectangle2.getHeight();
        double newWidth = rectangle2.getWidth();

        double verticalRatio = 0;
        if (initialHeight != 0) {
            verticalRatio = newHeight / initialHeight;
        }
        double horizontalRatio = 0;
        if (initialWidth != 0) {
            horizontalRatio = newWidth / initialWidth;
        }
        sideToHomotheticRatio.put(RectangleSide.NORTH, horizontalRatio);
        sideToHomotheticRatio.put(RectangleSide.SOUTH, horizontalRatio);
        sideToHomotheticRatio.put(RectangleSide.EAST, verticalRatio);
        sideToHomotheticRatio.put(RectangleSide.WEST, verticalRatio);

        return sideToHomotheticRatio;
    }

    private Ratio getPositionProportionOfEdgeEndAbsolutePosition(NodeLayoutData nodeLayoutData, Position absolutePosition) {
        Position nodeAbsolutePosition = nodeLayoutData.getAbsolutePosition();
        double edgeX = absolutePosition.getX() - nodeAbsolutePosition.getX();
        double edgeY = absolutePosition.getY() - nodeAbsolutePosition.getY();

        double edgeXProportion = edgeX / nodeLayoutData.getSize().getWidth();
        double edgeYProportion = edgeY / nodeLayoutData.getSize().getHeight();

        return Ratio.of(edgeXProportion, edgeYProportion);
    }

    private void layoutEdge(Optional<IDiagramEvent> optionalDiagramElementEvent, Optional<Position> optionalDelta, EdgeLayoutData edge) {
        // @formatter:off
        optionalDiagramElementEvent.filter(DoublePositionEvent.class::isInstance)
                .map(DoublePositionEvent.class::cast)
                .ifPresent(doublePositionEvent -> {
                    Ratio edgeSourceAnchorRelativePosition = this.getPositionProportionOfEdgeEndAbsolutePosition(edge.getSource(), doublePositionEvent.getSourcePosition());
                    Ratio edgeTargetAnchorRelativePosition = this.getPositionProportionOfEdgeEndAbsolutePosition(edge.getTarget(), doublePositionEvent.getTargetPosition());
                    edge.setSourceAnchorRelativePosition(edgeSourceAnchorRelativePosition);
                    edge.setTargetAnchorRelativePosition(edgeTargetAnchorRelativePosition);
                });
        // @formatter:on

        // recompute the edge routing points
        edge.setRoutingPoints(this.edgeRoutingPointsProvider.getRoutingPoints(optionalDiagramElementEvent, optionalDelta, edge));

        // recompute edge labels
        if (edge.getCenterLabel() != null) {
            edge.getCenterLabel().setPosition(this.edgeLabelPositionProvider.getCenterPosition(edge, edge.getCenterLabel()));
        }
    }

    /**
     * States whether or not a node has changed (size and/or position). This indicates that the related edges must be
     * recomputed.
     *
     * @param node
     *            the node
     * @return <true> if the node has moved / been resized
     */
    private boolean hasChanged(NodeLayoutData node) {
        boolean result = false;
        if (node.hasChanged()) {
            result = true;
        } else {
            IContainerLayoutData parent = node.getParent();
            if (parent instanceof NodeLayoutData) {
                result = this.hasChanged((NodeLayoutData) parent);
            }
        }
        return result;
    }

    /**
     * Round size to 1/1000 of a pixel. It is needed when an image has a width or height with a very big decimal part
     * (e.g: 140.0004672837)
     *
     * @param size
     *            the {@link Size} to round.
     * @return the rounded size.
     */
    private Size getRoundedSize(Size size) {
        BigDecimal roundedWidth = BigDecimal.valueOf(size.getWidth()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedHeight = BigDecimal.valueOf(size.getHeight()).setScale(4, RoundingMode.HALF_UP);
        return Size.of(roundedWidth.doubleValue(), roundedHeight.doubleValue());
    }
}
