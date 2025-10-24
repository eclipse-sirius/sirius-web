/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.views.search.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.eclipse.sirius.web.application.views.search.dto.SearchInput;
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;

import graphql.schema.DataFetchingEnvironment;

/**
 * Data fetcher for the EditingContext#search field.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "EditingContext", field = "search")
public class EditingContextSearchDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<IPayload>> {

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextDispatcher editingContextDispatcher;

    private final ObjectMapper objectMapper;

    public EditingContextSearchDataFetcher(IEditingContextDispatcher editingContextDispatcher, ObjectMapper objectMapper) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Map<String, Object> localContext = environment.getLocalContext();
        String editingContextId = Optional.ofNullable(localContext.get(LocalContextConstants.EDITING_CONTEXT_ID)).map(Object::toString).orElse(null);
        Object argument = environment.getArgument(QUERY_ARGUMENT);
        var query = this.objectMapper.convertValue(argument, SearchQuery.class);
        var input = new SearchInput(UUID.randomUUID(), editingContextId, query);

        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .toFuture();
    }
}