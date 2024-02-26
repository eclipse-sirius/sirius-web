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
package org.eclipse.sirius.web.application.editingcontext.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.components.collaborative.dto.EditingContextEventInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.reactivestreams.Publisher;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

/**
 * Data fetcher for the field Subscription#editingContextEvent.
 *
 * @author sbegaudeau
 */
@SubscriptionDataFetcher(type = "Subscription", field = "editingContextEvent")
public class SubscriptionEditingContextEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<DataFetcherResult<IPayload>>> {

    private final ObjectMapper objectMapper;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public SubscriptionEditingContextEventDataFetcher(ObjectMapper objectMapper, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public Publisher<DataFetcherResult<IPayload>> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument("input");
        var input = this.objectMapper.convertValue(argument, EditingContextEventInput.class);

        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, input.editingContextId());

        return this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(input.editingContextId())
                .map(IEditingContextEventProcessor::getOutputEvents)
                .orElse(Flux.empty())
                .map(payload ->  DataFetcherResult.<IPayload>newResult()
                        .data(payload)
                        .localContext(localContext)
                        .build());
    }
}
