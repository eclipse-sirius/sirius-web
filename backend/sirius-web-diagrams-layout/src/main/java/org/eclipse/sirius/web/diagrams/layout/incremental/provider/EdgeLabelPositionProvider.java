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
package org.eclipse.sirius.web.diagrams.layout.incremental.provider;

import java.util.List;
import java.util.Objects;

import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.sirius.web.diagrams.Position;
import org.eclipse.sirius.web.diagrams.layout.ISiriusWebLayoutConfigurator;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.EdgeLayoutData;
import org.eclipse.sirius.web.diagrams.layout.incremental.data.LabelLayoutData;

/**
 * Provides the position to apply to an Edge Label.
 *
 * @author wpiers
 */
public class EdgeLabelPositionProvider {

    private final ISiriusWebLayoutConfigurator layoutConfigurator;

    public EdgeLabelPositionProvider(ISiriusWebLayoutConfigurator layoutConfigurator) {
        this.layoutConfigurator = Objects.requireNonNull(layoutConfigurator);
    }

    public Position getCenterPosition(EdgeLayoutData edge, LabelLayoutData label) {
        Double spacingEdgeLabel = null;
        spacingEdgeLabel = this.layoutConfigurator.configureByElementClass(ElkEdge.class).getProperty(CoreOptions.SPACING_EDGE_LABEL);
        if (spacingEdgeLabel == null) {
            spacingEdgeLabel = CoreOptions.SPACING_EDGE_LABEL.getDefault();
        }
        Position position;
        List<Position> routingPoints = edge.getRoutingPoints();
        if (routingPoints.size() < 2) {
            position = Position.UNDEFINED;
        } else {
            Position sourceAnchor = routingPoints.get(0);
            Position targetAnchor = routingPoints.get(routingPoints.size() - 1);
            double x = ((sourceAnchor.getX() + targetAnchor.getX()) / 2) - (label.getTextBounds().getSize().getWidth() / 2);
            double y = (sourceAnchor.getY() + targetAnchor.getY()) / 2 + spacingEdgeLabel;
            return Position.at(x, y);
        }
        return position;
    }
}
