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
import org.eclipse.sirius.components.diagrams.Node;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Used to let us switch between the various layout data to send to the frontend.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Node", field = "childNodes")
public class NodeChildNodesDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<List<Node>>> {
    @Override
    public DataFetcherResult<List<Node>> get(DataFetchingEnvironment environment) throws Exception {
        Node node = environment.getSource();

        Map<String, Object> parentLocalContext = environment.getLocalContext();
        Map<String, Object> localContext = new HashMap<>();
        localContext.put("diagram", parentLocalContext.get("diagram"));

        return DataFetcherResult.<List<Node>>newResult()
                .data(node.getChildNodes())
                .localContext(localContext)
                .build();
    }
}
