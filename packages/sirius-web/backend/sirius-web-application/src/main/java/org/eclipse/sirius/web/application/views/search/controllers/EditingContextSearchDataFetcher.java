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

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;
import org.eclipse.sirius.web.application.views.search.dto.SearchInput;

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

    public EditingContextSearchDataFetcher(IEditingContextDispatcher editingContextDispatcher) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
    }

    @Override
    public CompletableFuture<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String query = environment.getArgument(QUERY_ARGUMENT);

        var input = new SearchInput(UUID.randomUUID(), editingContextId, query);
        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .toFuture();
    }
}