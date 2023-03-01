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

import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.EdgeLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Ratio;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to let us switch between the various layout data to send to the frontend.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Edge", field = "sourceAnchorRelativePosition")
public class EdgeSourceAnchorRelativePositionDataFetcher implements IDataFetcherWithFieldCoordinates<Ratio> {
    @Override
    public Ratio get(DataFetchingEnvironment environment) throws Exception {
        Edge edge = environment.getSource();
        Map<String, Object> localContext = environment.getLocalContext();

        return Optional.ofNullable(localContext.get("diagram"))
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .flatMap(diagram -> this.sourceAnchorRelativePosition(diagram, edge))
                .orElse(new Ratio(0, 0));
    }

    private Optional<Ratio> sourceAnchorRelativePosition(Diagram diagram, Edge edge) {
        Optional<Ratio> optionalRatio = Optional.empty();

        if (diagram.getLabel().endsWith("__EXPERIMENTAL")) {
            optionalRatio = Optional.of(diagram.getLayoutData())
                    .map(DiagramLayoutData::edgeLayoutData)
                    .map(layoutData -> layoutData.get(edge.getId()))
                    .map(EdgeLayoutData::sourceAnchorRelativePosition);
        } else {
            optionalRatio = Optional.of(new Ratio(edge.getSourceAnchorRelativePosition().getX(), edge.getSourceAnchorRelativePosition().getY()));
        }

        return optionalRatio;
    }
}
