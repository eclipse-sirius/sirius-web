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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.Size;
import org.eclipse.sirius.web.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.web.diagrams.layout.ISiriusWebLayoutConfigurator;
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

    private NodeLabelPositionProvider nodeLabelPositionProvider;

    private final EdgeRoutingPointsProvider edgeRoutingPointsProvider = new EdgeRoutingPointsProvider();

    private EdgeLabelPositionProvider edgeLabelPositionProvider;

    private final NodePositionProvider nodePositionProvider = new NodePositionProvider();

    private final NodeSizeProvider nodeSizeProvider;

    public IncrementalLayoutEngine(NodeSizeProvider nodeSizeProvider) {
        this.nodeSizeProvider = Objects.requireNonNull(nodeSizeProvider);
    }

    public void layout(Optional<IDiagramEvent> optionalDiagramElementEvent, DiagramLayoutData diagram, ISiriusWebLayoutConfigurator layoutConfigurator) {
        this.nodePositionProvider.reset();
        this.nodeLabelPositionProvider = new NodeLabelPositionProvider(layoutConfigurator);
        this.edgeLabelPositionProvider = new EdgeLabelPositionProvider(layoutConfigurator);

        // first we layout all the nodes
        for (NodeLayoutData node : diagram.getChildrenNodes()) {
            this.layoutNode(optionalDiagramElementEvent, node, layoutConfigurator);
        }

        // resolve overlaps due to previous changes
        new OverlapsUpdater().update(diagram);

        // resize according to the content
        new ContainmentUpdater().update(diagram);

        // finally we recompute the edges that needs to
        for (EdgeLayoutData edge : diagram.getEdges()) {
            if (this.hasChanged(edge.getSource()) || this.hasChanged(edge.getTarget()) || !this.isLabelPositioned(edge)) {
                this.layoutEdge(edge);
            }
        }
    }

    private boolean isLabelPositioned(EdgeLayoutData edge) {
        if (edge.getCenterLabel() != null) {
            Position position = edge.getCenterLabel().getPosition();
            return position.getX() != -1 || position.getY() != -1;
        }
        return false;
    }

    private void layoutNode(Optional<IDiagramEvent> optionalDiagramElementEvent, NodeLayoutData node, ISiriusWebLayoutConfigurator layoutConfigurator) {
        // first layout border & child nodes
        for (NodeLayoutData borderNode : node.getBorderNodes()) {
            this.layoutNode(optionalDiagramElementEvent, borderNode, layoutConfigurator);
        }
        for (NodeLayoutData childNode : node.getChildrenNodes()) {
            this.layoutNode(optionalDiagramElementEvent, childNode, layoutConfigurator);
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

        // recompute the label
        if (node.getLabel() != null) {
            node.getLabel().setPosition(this.nodeLabelPositionProvider.getPosition(node, node.getLabel()));
        }
    }

    private void layoutEdge(EdgeLayoutData edge) {
        // recompute the edge routing points
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

    /**
     * Round size to 1/1000 of a pixel. It is needed when an image has a width or height with a very big decimal part
     * (e.g: 140.0004672837)
     *
     * @param size
     *            the {@link Size} to round.
     * @return the rounded size.
     */
    private Size getRoundedSize(Size size) {
        BigDecimal roundedWidth = new BigDecimal(size.getWidth()).setScale(4, RoundingMode.HALF_UP);
        BigDecimal roundedHeight = new BigDecimal(size.getHeight()).setScale(4, RoundingMode.HALF_UP);
        return Size.of(roundedWidth.doubleValue(), roundedHeight.doubleValue());
    }
}
