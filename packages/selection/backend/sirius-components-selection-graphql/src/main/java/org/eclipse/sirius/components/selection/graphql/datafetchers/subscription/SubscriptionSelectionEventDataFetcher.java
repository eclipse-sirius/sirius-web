/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.selection.graphql.datafetchers.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.components.collaborative.selection.api.SelectionConfiguration;
import org.eclipse.sirius.components.collaborative.selection.dto.SelectionEventInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.reactivestreams.Publisher;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to send the refreshed tree to a subscription.
 *
 * @author arichard
 */
@SubscriptionDataFetcher(type = "Subscription", field = "selectionEvent")
public class SubscriptionSelectionEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<DataFetcherResult<IPayload>>> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider;

    public SubscriptionSelectionEventDataFetcher(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.eventProcessorSubscriptionProvider = Objects.requireNonNull(eventProcessorSubscriptionProvider);
    }

    @Override
    public Publisher<DataFetcherResult<IPayload>> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, SelectionEventInput.class);
        var selectionConfiguration = new SelectionConfiguration(UUID.randomUUID().toString(), input.selectionId(), input.targetObjectId());

        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, input.editingContextId());
        localContext.put(LocalContextConstants.REPRESENTATION_ID, selectionConfiguration.getId());

        return this.exceptionWrapper.wrapFlux(() -> this.eventProcessorSubscriptionProvider.getSubscription(input.editingContextId(), selectionConfiguration, input), input)
                .map(payload ->  DataFetcherResult.<IPayload>newResult()
                        .data(payload)
                        .localContext(localContext)
                        .build());
    }

}
