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
package org.eclipse.sirius.web.graphql.datafetchers.object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning an Object result in an Object.
 *
 * @author fbarbin
 */
@QueryDataFetcher(type = "Object", field = "queryBasedObject")
public class ObjectQueryBasedObjectDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<DataFetcherResult<Object>>> {

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IObjectQueryBasedDataFetcherService objectQueryBasedDataFetcherService;

    public ObjectQueryBasedObjectDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IObjectQueryBasedDataFetcherService objectQueryBasedDataFetcherService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.objectQueryBasedDataFetcherService = Objects.requireNonNull(objectQueryBasedDataFetcherService);
    }

    @Override
    public CompletableFuture<DataFetcherResult<Object>> get(DataFetchingEnvironment environment) throws Exception {
        String query = environment.getArgument(QUERY_ARGUMENT);
        ObjectLocalContext localContext = environment.getLocalContext();
        String editingContextId = localContext.getEditingContextId();

        Map<String, Object> newVariables = this.objectQueryBasedDataFetcherService.computeNewVariables(environment);
        QueryBasedObjectInput input = new QueryBasedObjectInput(UUID.randomUUID(), query, new HashMap<>(newVariables));

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(QueryBasedObjectSuccessPayload.class::isInstance)
                .map(QueryBasedObjectSuccessPayload.class::cast)
                .map(QueryBasedObjectSuccessPayload::result)
                .map(result -> this.toDataFetcherResult(result, editingContextId, newVariables))
                .toFuture();
    }

    private DataFetcherResult<Object> toDataFetcherResult(Object result, String editingContextId, Map<String, Object> newVariables) {
        // @formatter:off
        return DataFetcherResult.<Object>newResult()
                .data(result)
                .localContext(new ObjectLocalContext(editingContextId, newVariables))
                .build();
        // @formatter:on
    }

}
