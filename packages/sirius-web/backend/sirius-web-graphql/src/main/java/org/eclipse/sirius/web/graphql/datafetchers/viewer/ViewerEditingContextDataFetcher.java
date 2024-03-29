/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.viewer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve an editing context for a user.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Viewer {
 *   editingContext(editingContextId: ID!): EditingContext
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Viewer", field = "editingContext")
public class ViewerEditingContextDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<String>> {
    private static final String EDITING_CONTEXT_ID_ARGUMENT = "editingContextId";

    @Override
    public DataFetcherResult<String> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getArgument(EDITING_CONTEXT_ID_ARGUMENT);
        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, editingContextId);

        // @formatter:off
        return DataFetcherResult.<String>newResult()
                .data(editingContextId)
                .localContext(localContext)
                .build();
        // @formatter:on
    }

}
