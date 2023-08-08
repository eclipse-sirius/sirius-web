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
package org.eclipse.sirius.components.gantt.graphql.datafetchers.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.components.collaborative.gantt.GanttConfiguration;
import org.eclipse.sirius.components.collaborative.gantt.api.IGanttEventProcessor;
import org.eclipse.sirius.components.collaborative.gantt.dto.input.GanttEventInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.components.graphql.api.LocalContextConstants;
import org.reactivestreams.Publisher;

import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to send the refreshed gantt diagram to a subscription.
 *
 * @author lfasani
 */
@SubscriptionDataFetcher(type = "Subscription", field = "ganttEvent")
public class SubscriptionGanttDiagramEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<DataFetcherResult<IPayload>>> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider;

    public SubscriptionGanttDiagramEventDataFetcher(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.eventProcessorSubscriptionProvider = Objects.requireNonNull(eventProcessorSubscriptionProvider);
    }

    @Override
    public Publisher<DataFetcherResult<IPayload>> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, GanttEventInput.class);
        var ganttConfiguration = new GanttConfiguration(input.ganttId());

        Map<String, Object> localContext = new HashMap<>();
        localContext.put(LocalContextConstants.EDITING_CONTEXT_ID, input.editingContextId());
        localContext.put(LocalContextConstants.REPRESENTATION_ID, input.ganttId());

        return this.exceptionWrapper.wrapFlux(() -> this.eventProcessorSubscriptionProvider.getSubscription(input.editingContextId(), IGanttEventProcessor.class, ganttConfiguration, input), input)
                .map(payload ->  DataFetcherResult.<IPayload>newResult()
                        .data(payload)
                        .localContext(localContext)
                        .build());
    }
}
