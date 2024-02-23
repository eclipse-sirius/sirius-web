/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.viewer.controllers;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Query#viewer.
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
