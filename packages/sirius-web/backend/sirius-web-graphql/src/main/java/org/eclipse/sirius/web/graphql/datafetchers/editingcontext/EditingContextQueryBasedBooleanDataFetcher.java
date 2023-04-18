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

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedBooleanInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedBooleanSuccessPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to execute a query returning a Boolean result in an editing context.
 *
 * @author fbarbin
 */
@QueryDataFetcher(type = "EditingContext", field = "queryBasedBoolean")
public class EditingContextQueryBasedBooleanDataFetcher implements IDataFetcherWithFieldCoordinates<CompletableFuture<Boolean>> {

    private static final String QUERY_ARGUMENT = "query";

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public EditingContextQueryBasedBooleanDataFetcher(IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public CompletableFuture<Boolean> get(DataFetchingEnvironment environment) throws Exception {
        String editingContextId = environment.getSource();
        String query = environment.getArgument(QUERY_ARGUMENT);
        QueryBasedBooleanInput input = new QueryBasedBooleanInput(UUID.randomUUID(), query, Map.of());

        return this.editingContextEventProcessorRegistry.dispatchEvent(editingContextId, input)
                .filter(QueryBasedBooleanSuccessPayload.class::isInstance)
                .map(QueryBasedBooleanSuccessPayload.class::cast)
                .map(QueryBasedBooleanSuccessPayload::result)
                .toFuture();
    }
}
