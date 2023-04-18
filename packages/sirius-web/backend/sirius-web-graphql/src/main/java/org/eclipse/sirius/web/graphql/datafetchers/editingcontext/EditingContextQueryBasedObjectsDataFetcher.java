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
package org.eclipse.sirius.web.graphql.datafetchers.editingcontext;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectsInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectsSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.web.graphql.datafetchers.object.ObjectLocalContext;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning an Object[] result in an editing context.
 *
 * @author fbarbin
 */
@QueryDataFetcher(type = "EditingContext", field = "queryBasedObjects")
public class EditingContextQueryBasedObjectsDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<DataFetcherResult<List<Object>>>> {

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextQueryBasedObjectsDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<DataFetcherResult<List<Object>>> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String query = environment.getArgument(QUERY_ARGUMENT);
        QueryBasedObjectsInput input = new QueryBasedObjectsInput(UUID.randomUUID(), query, Map.of());

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(QueryBasedObjectsSuccessPayload.class::isInstance)
                .map(QueryBasedObjectsSuccessPayload.class::cast)
                .map(QueryBasedObjectsSuccessPayload::result)
                .map(result -> this.toDataFetcherResult(result, editingContextId))
                .toFuture();
    }

    private DataFetcherResult<List<Object>> toDataFetcherResult(List<Object> result, String editingContextId) {
        return DataFetcherResult.<List<Object>>newResult()
                .data(result)
                .localContext(new ObjectLocalContext(editingContextId, Map.of()))
                .build();
    }

}
