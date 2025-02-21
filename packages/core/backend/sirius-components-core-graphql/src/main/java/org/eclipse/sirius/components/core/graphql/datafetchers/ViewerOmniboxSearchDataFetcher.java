/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.core.graphql.datafetchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxContextEntry;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxSearchInput;
import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxSearchPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEditingContextDispatcher;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Mono;

/**
 * Data fetcher for the field Viewer#omniboxSearch.
 *
 * @author gdaniel
 */
@QueryDataFetcher(type = "Viewer", field = "omniboxSearch")
public class ViewerOmniboxSearchDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<List<Object>>> {

    private static final String EDITING_CONTEXT_ID_ARGUMENT = "editingContextId";

    private static final String CONTEXT_ENTRIES_ARGUMENT = "contextEntries";

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextDispatcher editingContextDispatcher;

    private final ObjectMapper objectMapper;

    public ViewerOmniboxSearchDataFetcher(IEditingContextDispatcher editingContextDispatcher, ObjectMapper objectMapper) {
        this.editingContextDispatcher = Objects.requireNonNull(editingContextDispatcher);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public CompletableFuture<List<Object>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getArgument(EDITING_CONTEXT_ID_ARGUMENT);
        Object argument = environment.getArgument(CONTEXT_ENTRIES_ARGUMENT);
        List<OmniboxContextEntry> omniboxContextEntries = this.objectMapper.convertValue(argument, new TypeReference<List<OmniboxContextEntry>>() { });
        String query = environment.getArgument(QUERY_ARGUMENT);

        var input = new OmniboxSearchInput(UUID.randomUUID(), editingContextId, omniboxContextEntries, query);

        return this.editingContextDispatcher.dispatchQuery(input.editingContextId(), input)
                .filter(OmniboxSearchPayload.class::isInstance)
                .map(OmniboxSearchPayload.class::cast)
                .map(OmniboxSearchPayload::objects)
                .switchIfEmpty(Mono.just(List.of()))
                .toFuture();
    }
}
