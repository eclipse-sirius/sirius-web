/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.diagrams.NodeType;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.Size;
import org.eclipse.sirius.components.diagrams.events.DoublePositionEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.events.UpdateEdgeRoutingPointsEvent;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ILayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
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

    private final EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

    private EdgeLabelPositionProvider edgeLabelPositionProvider;

    private final NodePositionProvider nodePositionProvider = new NodePositionProvider();

    private final NodeSizeProvider nodeSizeProvider;

    private final List<ICustomNodeLabelPositionProvider> customLabelPositionProviders;

    private final ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider;

    private final IBorderNodeLayoutEngine borderNodeLayoutEngine;

    public IncrementalLayoutEngine(NodeSizeProvider nodeSizeProvider, List<ICustomNodeLabelPositionProvider> customLabelPositionProviders,
            ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider, IBorderNodeLayoutEngine borderNodeLayoutEngine) {
        this.layoutEngineHandlerSwitchProvider = Objects.requireNonNull(layoutEngineHandlerSwitchProvider);
        this.nodeSizeProvider = Objects.requireNonNull(nodeSizeProvider);
        this.customLabelPositionProviders = Objects.requireNonNull(customLabelPositionProviders);
        this.borderNodeLayoutEngine = Objects.requireNonNull(borderNodeLayoutEngine);
    }

    public void layout(Optional<IDiagramEvent> optionalDiagramElementEvent, IncrementalLayoutConvertedDiagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
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
        shouldLayoutEdge = shouldLayoutEdge || this.isReconnectedEdge(optionalDiagramElementEvent, edge);

        return shouldLayoutEdge;
    }

    private boolean isReconnectedEdge(Optional<IDiagramEvent> optionalDiagramElementEvent, EdgeLayoutData edge) {
        // @formatter:off
        return optionalDiagramElementEvent.filter(ReconnectEdgeEvent.class::isInstance)
                .map(ReconnectEdgeEvent.class::cast)
                .map(ReconnectEdgeEvent::getEdgeId)
                .filter(edge.getId()::equals)
                .isPresent();
        // @formatter:on
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

        var optionalNodeIncrementalLayoutEngine = this.layoutEngineHandlerSwitchProvider.getLayoutEngineHandlerSwitch().apply(node.getNodeType());

        if (optionalNodeIncrementalLayoutEngine.isPresent() && node.getChildrenLayoutStrategy() instanceof ListLayoutStrategy) {
            var nodeIncrementalLayoutEngine = optionalNodeIncrementalLayoutEngine.get();
            NodeLayoutData layoutedNode = nodeIncrementalLayoutEngine.layout(optionalDiagramElementEvent, node, layoutConfigurator);

            // recompute the node position
            Position position = this.nodePositionProvider.getPosition(optionalDiagramElementEvent, layoutedNode);
            if (!position.equals(layoutedNode.getPosition())) {
                layoutedNode.setPosition(position);
                layoutedNode.setChanged(true);
                layoutedNode.setPinned(true);
            }

        } else {
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
            List<BorderNodesOnSide> borderNodesOnSide = this.borderNodeLayoutEngine.layoutBorderNodes(optionalDiagramElementEvent, node.getBorderNodes(), initialNodeBounds, newBounds,
                    layoutConfigurator);

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
        List<BorderNodesOnSide> borderNodesOnSide = this.borderNodeLayoutEngine.layoutBorderNodes(optionalDiagramElementEvent, node.getBorderNodes(), initialNodeBounds, newBounds, layoutConfigurator);

        // recompute the label
        if (node.getLabel() != null) {
            node.getLabel().setPosition(this.nodeLabelPositionProvider.getPosition(node, node.getLabel(), borderNodesOnSide));
        }
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

        // @formatter:off
        optionalDiagramElementEvent.filter(ReconnectEdgeEvent.class::isInstance)
                .map(ReconnectEdgeEvent.class::cast)
                .filter(reconnectionEvent -> reconnectionEvent.getEdgeId().equals(edge.getId()))
                .ifPresent(reconnectionEvent -> {
                    if (reconnectionEvent.getKind() == ReconnectEdgeKind.SOURCE) {
                        Ratio reconnectAnchor = this.getPositionProportionOfEdgeEndAbsolutePosition(edge.getSource(), reconnectionEvent.getNewEdgeEndAnchor());
                        edge.setSourceAnchorRelativePosition(reconnectAnchor);
                    }
                    if (reconnectionEvent.getKind() == ReconnectEdgeKind.TARGET) {
                        Ratio reconnectAnchor = this.getPositionProportionOfEdgeEndAbsolutePosition(edge.getTarget(), reconnectionEvent.getNewEdgeEndAnchor());
                        edge.setTargetAnchorRelativePosition(reconnectAnchor);
                    }
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
