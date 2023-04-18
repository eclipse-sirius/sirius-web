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
import org.eclipse.sirius.components.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedIntSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning an Int result in an Object.
 *
 * @author fbarbin
 */
@QueryDataFetcher(type = "Object", field = "queryBasedInt")
public class ObjectQueryBasedIntDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Integer>> {

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    private final IObjectQueryBasedDataFetcherService objectQueryBasedDataFetcherService;

    public ObjectQueryBasedIntDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry, IObjectQueryBasedDataFetcherService objectQueryBasedDataFetcherService) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
        this.objectQueryBasedDataFetcherService = Objects.requireNonNull(objectQueryBasedDataFetcherService);
    }

    @Override
    public CompletableFuture<Integer> get(DataFetchingEnvironment environment) throws Exception {
        String query = environment.getArgument(QUERY_ARGUMENT);
        ObjectLocalContext localContext = environment.getLocalContext();
        String editingContextId = localContext.getEditingContextId();

        Map<String, Object> newVariables = this.objectQueryBasedDataFetcherService.computeNewVariables(environment);
        QueryBasedIntInput input = new QueryBasedIntInput(UUID.randomUUID(), query, new HashMap<>(newVariables));

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(QueryBasedIntSuccessPayload.class::isInstance)
                .map(QueryBasedIntSuccessPayload.class::cast)
                .map(QueryBasedIntSuccessPayload::result)
                .toFuture();
    }

}
