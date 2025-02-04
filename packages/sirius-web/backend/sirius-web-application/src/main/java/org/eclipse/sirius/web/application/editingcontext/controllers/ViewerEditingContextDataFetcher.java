/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.application.editingcontext.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the field Viewer#editingContext.
 *
 * @author sbegaudeau
 */
@QueryDataFetcher(type = "Viewer", field = "editingContext")
public class ViewerEditingContextDataFetcher implements IDataFetcherWithFieldCoordinates<DataFetcherResult<String>> {

    private static final String EDITING_CONTEXT_ID_ARGUMENT = "editingContextId";

    private final IEditingContextSearchService editingContextSearchService;

    public ViewerEditingContextDataFetcher(IEditingContextSearchService editingContextSearchService) {
        this.editingContextSearchService = Objects.requireNonNull(editingContextSearchService);
    }

    @Override
    public DataFetcherResult<String> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getArgument(EDITING_CONTEXT_ID_ARGUMENT);
        if (!this.editingContextSearchService.existsById(editingContextId)) {
            return null;
        }

        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, editingContextId);

        return DataFetcherResult.<String>newResult()
                .data(editingContextId)
                .localContext(localContext)
                .build();
    }
}
