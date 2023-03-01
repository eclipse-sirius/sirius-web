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
import org.eclipse.sirius.components.diagrams.Label;
import org.eclipse.sirius.components.diagrams.layoutdata.DiagramLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.LabelLayoutData;
import org.eclipse.sirius.components.diagrams.layoutdata.Position;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to let us switch between the various layout data to send to the frontend.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Label", field = "alignment")
public class LabelAlignmentDataFetcher implements IDataFetcherWithFieldCoordinates<Position> {
    @Override
    public Position get(DataFetchingEnvironment environment) throws Exception {
        Label label = environment.getSource();
        Map<String, Object> localContext = environment.getLocalContext();

        return Optional.ofNullable(localContext.get("diagram"))
                .filter(Diagram.class::isInstance)
                .map(Diagram.class::cast)
                .flatMap(diagram -> this.alignment(diagram, label))
                .orElse(new Position(0, 0));
    }

    private Optional<Position> alignment(Diagram diagram, Label label) {
        Optional<Position> optionalAlignment = Optional.empty();

        if (diagram.getLabel().endsWith("__EXPERIMENTAL")) {
            optionalAlignment = Optional.of(diagram.getLayoutData())
                    .map(DiagramLayoutData::labelLayoutData)
                    .map(layoutData -> layoutData.get(label.getId()))
                    .map(LabelLayoutData::alignment);
        } else {
            optionalAlignment = Optional.of(new Position(label.getAlignment().getX(), label.getAlignment().getY()));
        }

        return optionalAlignment;
    }
}
