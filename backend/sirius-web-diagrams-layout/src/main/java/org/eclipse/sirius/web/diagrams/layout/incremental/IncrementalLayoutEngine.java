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
package org.eclipse.sirius.web.diagrams.layout.incremental;

import java.util.Objects;

import org.eclipse.sirius.web.diagrams.MoveEvent;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.DiagramLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.IContainerLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.NodeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.EdgeLabelPositionProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.EdgeRoutingPointsProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodeLabelPositionProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodePositionProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.provider.NodeSizeProvider;
import org.eclipse.sirius.web.diagrams.layout.incremental.updater.ContainmentUpdater;
import org.eclipse.sirius.web.diagrams.layout.incremental.updater.OverlapsUpdater;
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

    private final NodeLabelPositionProvider nodeLabelPositionProvider = new NodeLabelPositionProvider();

    private final EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

    private final EdgeLabelPositionProvider edgeLabelPositionProvider = new EdgeLabelPositionProvider();

    private final NodeSizeProvider nodeSizeProvider;

    public IncrementalLayoutEngine(NodeSizeProvider nodeSizeProvider) {
        this.nodeSizeProvider = Objects.requireNonNull(nodeSizeProvider);
    }

    public void layout(MoveEvent moveEvent, Position startingPosition, DiagramLayoutData diagram) {
        NodePositionProvider nodePositionProvider = new NodePositionProvider(startingPosition);

        // first we layout all the nodes
        for (NodeLayoutData node : diagram.getChildrenNodes()) {
            this.layoutNode(moveEvent, nodePositionProvider, node);
        }

        // resolve overlaps due to previous changes
        new OverlapsUpdater().update(diagram);

        // resize according to the content
        new ContainmentUpdater().update(diagram);

        // finally we recompute the edges that needs to
        for (EdgeLayoutData edge : diagram.getEdges()) {
            if (this.hasChanged(edge.getSource()) || this.hasChanged(edge.getTarget())) {
                this.layoutEdge(edge);
            }
        }
    }

    private void layoutNode(MoveEvent moveEvent, NodePositionProvider nodePositionProvider, NodeLayoutData node) {
        // first layout border & child nodes
        for (NodeLayoutData borderNode : node.getBorderNodes()) {
            this.layoutNode(moveEvent, nodePositionProvider, borderNode);
        }
        for (NodeLayoutData childNode : node.getChildrenNodes()) {
            this.layoutNode(moveEvent, nodePositionProvider, childNode);
        }

        // compute the node size according to what has been done in the previous steps
        Size size = this.nodeSizeProvider.getSize(node);
        if (!size.equals(node.getSize())) {
            node.setSize(size);
            node.setChanged(true);
        }
        // recompute the node position
        if (moveEvent != null && moveEvent.getNodeId().equals(node.getId())) {
            Position newPosition = moveEvent.getNewPosition();
            node.setPosition(newPosition);
            node.setChanged(true);
            node.setPinned(true);
        } else if (node.getPosition().getX() == -1 && node.getPosition().getY() == -1) {
            node.setPosition(nodePositionProvider.getPosition(node));
            node.setChanged(true);
            node.setPinned(true);
        }

        // resolve overlaps due to previous changes
        new OverlapsUpdater().update(node);

        // resize / change position according to the content
        new ContainmentUpdater().update(node);

        // recompute the label
        if (node.getLabel() != null) {
            node.getLabel().setPosition(this.nodeLabelPositionProvider.getPosition(node, node.getLabel()));
        }
    }

    private void layoutEdge(EdgeLayoutData edge) {
        // recompute the edge bendpoints
        edge.setRoutingPoints(this.edgeRoutingPointsProvider.getRoutingPoints(edge));

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

}
