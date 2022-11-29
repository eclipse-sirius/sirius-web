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
package org.eclipse.sirius.components.diagrams.layout.incremental.provider;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.sirius.components.diagrams.Position;
import org.eclipse.sirius.components.diagrams.Ratio;
import org.eclipse.sirius.components.diagrams.layout.api.Bounds;
import org.eclipse.sirius.components.diagrams.layout.api.Geometry;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layout.incremental.data.NodeLayoutData;

/**
 * Provides the position to apply to an Edge Label.
 *
 * @author wpiers
 */
public class EdgeLabelPositionProvider {

    private final Map<String, ElkGraphElement> elementId2ElkElement;

    public EdgeLabelPositionProvider(Map<String, ElkGraphElement> elementId2ElkNode) {
        this.elementId2ElkElement = Objects.requireNonNull(elementId2ElkNode);
    }

    public Position getCenterPosition(EdgeLayoutData edge, LabelLayoutData label) {
        double spacingEdgeLabel = this.elementId2ElkElement.get(edge.getId()).getProperty(CoreOptions.SPACING_EDGE_LABEL);
        Position position = Position.UNDEFINED;
        List<Position> routingPoints = edge.getRoutingPoints();

        if (routingPoints.size() == 0) {
            // Straight edge between two elements.

            Bounds sourceBounds = this.getAbsoluteBounds(edge.getSource());
            Bounds targetBounds = this.getAbsoluteBounds(edge.getTarget());
            Position sourceAbsolutePosition = this.toAbsolutePosition(edge.getSourceAnchorRelativePosition(), sourceBounds);
            Position targetAbsolutePosition = this.toAbsolutePosition(edge.getTargetAnchorRelativePosition(), targetBounds);

            Geometry geometry = new Geometry();
            Optional<Position> optionalSourceIntersection = geometry.getIntersection(targetAbsolutePosition, sourceAbsolutePosition, sourceBounds);
            Optional<Position> optionalTargetIntersection = geometry.getIntersection(sourceAbsolutePosition, targetAbsolutePosition, targetBounds);

            if (optionalSourceIntersection.isPresent() && optionalTargetIntersection.isPresent()) {
                Position sourceAnchor = optionalSourceIntersection.get();
                Position targetAnchor = optionalTargetIntersection.get();
                double x = ((sourceAnchor.getX() + targetAnchor.getX()) / 2) - (label.getTextBounds().getSize().getWidth() / 2);
                double y = (sourceAnchor.getY() + targetAnchor.getY()) / 2 + spacingEdgeLabel;
                position = Position.at(x, y);
            }
        }

        if (position == Position.UNDEFINED && !routingPoints.isEmpty()) {
            if ((routingPoints.size() & 1) == 0) {
                int sourceRefIndex = routingPoints.size() / 2;
                Position sourceRef = routingPoints.get(sourceRefIndex - 1);
                Position targetRef = routingPoints.get(sourceRefIndex);

                // Self loop edge
                double x = ((sourceRef.getX() + targetRef.getX()) / 2) - (label.getTextBounds().getSize().getWidth() / 2);
                double y = (sourceRef.getY() + targetRef.getY()) / 2 - spacingEdgeLabel - label.getTextBounds().getSize().getHeight();
                position = Position.at(x, y);
            }

            if ((routingPoints.size() & 1) == 1) {
                int labelPositionRefIndex = routingPoints.size() / 2;
                Position labelPositionref = routingPoints.get(labelPositionRefIndex);

                double x = labelPositionref.getX() - (label.getTextBounds().getSize().getWidth() / 2);
                double y = labelPositionref.getY() - spacingEdgeLabel - label.getTextBounds().getSize().getHeight();
                position = Position.at(x, y);
            }
        }

        return position;
    }

    private Position toAbsolutePosition(Ratio anchorRelativePosition, Bounds sourceBounds) {
        double edgeAbsoluteX = sourceBounds.getPosition().getX() + sourceBounds.getSize().getWidth() * anchorRelativePosition.getX();
        double edgeAbsoluteY = sourceBounds.getPosition().getY() + sourceBounds.getSize().getHeight() * anchorRelativePosition.getY();
        return Position.at(edgeAbsoluteX, edgeAbsoluteY);
    }

    private Bounds getAbsoluteBounds(NodeLayoutData nodeLayoutData) {
      // @formatter:off
      return Bounds.newBounds()
              .position(nodeLayoutData.getAbsolutePosition())
              .size(nodeLayoutData.getSize())
              .build();
      // @formatter:on
    }
}
