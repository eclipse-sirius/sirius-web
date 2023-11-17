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

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramLayoutDataPayload;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Used to retrieve the layout data from a diagram.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Diagram", field = "layoutData")
public class DiagramLayoutDataDataFetcher implements IDataFetcherWithFieldCoordinates<DiagramLayoutDataPayload> {
    @Override
    public DiagramLayoutDataPayload get(DataFetchingEnvironment environment) throws Exception {
        Diagram diagram = environment.getSource();
        var nodeLayoutData = diagram.getLayoutData()
                .nodeLayoutData()
                .values()
                .stream()
                .toList();
        return new DiagramLayoutDataPayload(nodeLayoutData);
    }
}
