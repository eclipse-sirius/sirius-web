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
package org.eclipse.sirius.web.diagrams.components;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.web.components.Element;
import org.eclipse.sirius.web.diagrams.Bounds;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.elements.NodeElementProps;
import org.eclipse.sirius.web.diagrams.utils.Geometry;

/**
 * Provides the routing points to apply to a new edge.
 *
 * @author fbarbin
 */
public class EdgeRoutingPointsProvider {

    public List<Position> getRoutingPoints(Element source, Element target) {
        Optional<Bounds> optionalSourceBounds = this.getElementBounds(source);
        Optional<Bounds> optionalTargetBounds = this.getElementBounds(target);

        if (optionalSourceBounds.isPresent() && optionalTargetBounds.isPresent()) {
            Position sourceAbsolutePosition = this.getCenter(optionalSourceBounds.get());
            Position targetAbsolutePosition = this.getCenter(optionalTargetBounds.get());
            Geometry geometry = new Geometry();
            Position sourceIntersection = geometry.getIntersection(targetAbsolutePosition, sourceAbsolutePosition, optionalSourceBounds.get());
            Position targetIntersection = geometry.getIntersection(sourceAbsolutePosition, targetAbsolutePosition, optionalTargetBounds.get());

            return List.of(sourceIntersection, targetIntersection);
        }
        return List.of();
    }

    private Optional<Bounds> getElementBounds(Element element) {
        return this.getNodeElementProps(element).map(nodeElementProps -> {
            // @formatter:off
            return Bounds.newBounds()
                    .position(nodeElementProps.getAbsolutePosition())
                    .size(nodeElementProps.getSize())
                    .build();
            // @formatter:on
        });
    }

    private Optional<NodeElementProps> getNodeElementProps(Element element) {
        // @formatter:off
        return Optional.ofNullable(element)
                .map(Element::getProps)
                .filter(NodeElementProps.class::isInstance)
                .map(NodeElementProps.class::cast);
        // @formatter:on
    }

    private Position getCenter(Bounds bounds) {
        // @formatter:off
        return Position.newPosition()
                .x(bounds.getPosition().getX() + (bounds.getSize().getWidth() / 2))
                .y(bounds.getPosition().getY() + (bounds.getSize().getHeight() / 2))
                .build();
        // @formatter:on

    }
}
