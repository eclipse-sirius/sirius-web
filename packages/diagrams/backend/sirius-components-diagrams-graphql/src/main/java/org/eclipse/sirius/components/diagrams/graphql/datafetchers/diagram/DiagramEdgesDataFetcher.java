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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.Edge;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to let us switch between the various layout data to send to the frontend.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Diagram", field = "edges")
public class DiagramEdgesDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<List<Edge>>> {
    @Override
    public DataFetcherResult<List<Edge>> get(DataFetchingEnvironment environment) throws Exception {
        Diagram diagram = environment.getSource();

        Map<String, Object> localContext = new HashMap<>();
        localContext.put("diagram", diagram);

        return DataFetcherResult.<List<Edge>>newResult()
                .data(diagram.getEdges())
                .localContext(localContext)
                .build();
    }
}
