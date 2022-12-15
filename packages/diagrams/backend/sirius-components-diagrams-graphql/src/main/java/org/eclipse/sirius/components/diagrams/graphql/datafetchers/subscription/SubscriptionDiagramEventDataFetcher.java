/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.graphql.datafetchers.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.components.collaborative.diagrams.api.DiagramConfiguration;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramEventProcessor;
import org.eclipse.sirius.components.collaborative.diagrams.dto.DiagramEventInput;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IEventProcessorSubscriptionProvider;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.reactivestreams.Publisher;

import graphql.schema.DataFetchingEnvironment;

/**
 * The data fetcher used to send the refreshed diagram to a subscription.
 *
 * @author sbegaudeau
 * @author pcdavid
 */
@SubscriptionDataFetcher(type = "Subscription", field = "diagramEvent")
public class SubscriptionDiagramEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<IPayload>> {

    private static final String INPUT_ARGUMENT = "input";

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider;

    public SubscriptionDiagramEventDataFetcher(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IEventProcessorSubscriptionProvider eventProcessorSubscriptionProvider) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.eventProcessorSubscriptionProvider = Objects.requireNonNull(eventProcessorSubscriptionProvider);
    }

    @Override
    public Publisher<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument(INPUT_ARGUMENT);
        var input = this.objectMapper.convertValue(argument, DiagramEventInput.class);
        var diagramConfiguration = new DiagramConfiguration(input.getDiagramId());

        return this.exceptionWrapper.wrapFlux(() -> this.eventProcessorSubscriptionProvider.getSubscription(input.getEditingContextId(), IDiagramEventProcessor.class, diagramConfiguration, input),
                input);
    }
}
