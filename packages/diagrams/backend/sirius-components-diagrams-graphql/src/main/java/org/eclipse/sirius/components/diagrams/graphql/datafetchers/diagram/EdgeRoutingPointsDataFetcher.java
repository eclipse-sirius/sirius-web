/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.diagram;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to let us switch between the various layout data to send to the frontend.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Edge", field = "routingPoints")
public class EdgeRoutingPointsDataFetcher implements IDataFetcherWithFieldCoordinates<List<Position>> {
    @Override
    public List<Position> get(DataFetchingEnvironment environment) throws Exception {
        Edge edge = environment.getSource();

        Map<String, Object> localContext = environment.getLocalContext();

        return Optional.ofNullable(localContext.get("diagram"))
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .flatMap(diagram -> this.routingPoints(diagram, edge))
                .orElse(List.of());
    }

    private Optional<List<Position>> routingPoints(Diagram diagram, Edge edge) {
        Optional<List<Position>> optionalRoutingPoints = Optional.empty();

        if (diagram.getLabel().endsWith("__EXPERIMENTAL")) {
            optionalRoutingPoints = Optional.of(diagram.getLayoutData())
                    .map(DiagramLayoutData::edgeLayoutData)
                    .map(layoutData -> layoutData.get(edge.getId()))
                    .map(EdgeLayoutData::routingPoints);
        } else {
            optionalRoutingPoints = Optional.of(edge.getRoutingPoints().stream()
                    .map(routingPoint -> new Position(routingPoint.getX(), routingPoint.getY()))
                    .toList());
        }

        return optionalRoutingPoints;
    }
}
