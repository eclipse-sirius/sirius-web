/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.query;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to retrieve the viewer of a query.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Query {
 *   viewer: Viewer
 * }
 * </pre>
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Query", field = "viewer")
public class QueryViewerDataFetcher implements IDataFetcherWithFieldCoordinates<Object> {

    @Override
    public Object get(DataFetchingEnvironment environment) throws Exception {
        return new Object();
    }
}
