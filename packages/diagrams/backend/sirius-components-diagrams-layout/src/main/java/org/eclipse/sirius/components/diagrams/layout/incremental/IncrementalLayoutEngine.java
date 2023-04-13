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

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.events.DoublePositionEvent;
import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.events.MoveEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeEvent;
import org.eclipse.sirius.components.diagrams.events.ReconnectEdgeKind;
import org.eclipse.sirius.components.diagrams.events.UpdateEdgeRoutingPointsEvent;
import org.eclipse.sirius.components.diagrams.layout.ILayoutEngineHandlerSwitchProvider;
import org.eclipse.sirius.components.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.ILayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.EdgeLabelPositionProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.EdgeRoutingPointsProvider;
import org.eclipse.sirius.components.diagrams.layout.incremental.provider.NodePositionProvider;
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

    private final EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

    private EdgeLabelPositionProvider edgeLabelPositionProvider;

    private final NodePositionProvider nodePositionProvider = new NodePositionProvider();

    private final ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider;

    public IncrementalLayoutEngine(ILayoutEngineHandlerSwitchProvider layoutEngineHandlerSwitchProvider) {
        this.layoutEngineHandlerSwitchProvider = Objects.requireNonNull(layoutEngineHandlerSwitchProvider);
    }

    public void layout(Optional<IDiagramEvent> optionalDiagramElementEvent, IncrementalLayoutConvertedDiagram diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
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
                    ILayoutData iLayoutData = id2LayoutData.get(moveEvent.nodeId());
                    if (iLayoutData instanceof NodeLayoutData nodeLayoutData) {
                        Position fromPosition = nodeLayoutData.getPosition();
                        Position toPosition = moveEvent.newPosition();
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
        boolean isEdgePositioned = true;

        isEdgePositioned = isEdgePositioned && edge.getSourceAnchorRelativePosition() != null;
        isEdgePositioned = isEdgePositioned && edge.getTargetAnchorRelativePosition() != null;
        isEdgePositioned = isEdgePositioned && !edge.getSourceAnchorRelativePosition().equals(Ratio.UNDEFINED);
        isEdgePositioned = isEdgePositioned && !edge.getTargetAnchorRelativePosition().equals(Ratio.UNDEFINED);

        return isEdgePositioned;
    }

    private boolean isLabelPositioned(EdgeLayoutData edge) {
        boolean isLabelPositioned = true;
        if (edge.getCenterLabel() != null) {
            Position position = edge.getCenterLabel().getPosition();
            isLabelPositioned = isLabelPositioned && position.getX() != -1 || position.getY() != -1;
        }
        if (edge.getBeginLabel() != null) {
            // The code below is commented since it cannot work without a fix for this issue:
            // https://github.com/eclipse-sirius/sirius-components/issues/1529

            // Position position = edge.getBeginLabel().getPosition();
            // isLabelPositioned = isLabelPositioned && position.getX() != -1 || position.getY() != -1;
        }
        if (edge.getEndLabel() != null) {
            // The code below is commented since it cannot work without a fix for this issue:
            // https://github.com/eclipse-sirius/sirius-components/issues/1529

            // Position position = edge.getEndLabel().getPosition();
            // isLabelPositioned = isLabelPositioned && position.getX() != -1 || position.getY() != -1;
        }
        return isLabelPositioned;
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
        var optionalNodeIncrementalLayoutEngine = this.layoutEngineHandlerSwitchProvider.getLayoutEngineHandlerSwitch().apply(node.getNodeType());
        if (optionalNodeIncrementalLayoutEngine.isPresent()) {
            var nodeIncrementalLayoutEngine = optionalNodeIncrementalLayoutEngine.get();
            NodeLayoutData laidOutNode = nodeIncrementalLayoutEngine.layout(optionalDiagramElementEvent, node, layoutConfigurator, Optional.empty());
            Position position = this.nodePositionProvider.getPosition(optionalDiagramElementEvent, laidOutNode);
            if (!position.equals(laidOutNode.getPosition())) {
                laidOutNode.setPosition(position);
                laidOutNode.setChanged(true);
                laidOutNode.setPinned(true);
            }
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
                    Ratio edgeSourceAnchorRelativePosition = this.getPositionProportionOfEdgeEndAbsolutePosition(edge.getSource(), doublePositionEvent.sourcePosition());
                    Ratio edgeTargetAnchorRelativePosition = this.getPositionProportionOfEdgeEndAbsolutePosition(edge.getTarget(), doublePositionEvent.targetPosition());
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
            if (parent instanceof NodeLayoutData parentLayoutData) {
                result = this.hasChanged(parentLayoutData);
            }
        }
        return result;
    }
}
