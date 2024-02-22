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
package org.eclipse.sirius.web.application.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.IExceptionWrapper;
import org.eclipse.sirius.web.application.project.dto.ProjectEventInput;
import org.eclipse.sirius.web.application.project.services.api.IProjectSubscriptions;
import org.reactivestreams.Publisher;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

/**
 * Data fetcher for the field Subscription#projectEvent.
 *
 * @author sbegaudeau
 */
@SubscriptionDataFetcher(type = "Subscription", field = "projectEvent")
public class SubscriptionProjectEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<IPayload>> {

    private final ObjectMapper objectMapper;

    private final IExceptionWrapper exceptionWrapper;

    private final IProjectSubscriptions projectSubscriptions;

    public SubscriptionProjectEventDataFetcher(ObjectMapper objectMapper, IExceptionWrapper exceptionWrapper, IProjectSubscriptions projectSubscriptions) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
        this.exceptionWrapper = Objects.requireNonNull(exceptionWrapper);
        this.projectSubscriptions = Objects.requireNonNull(projectSubscriptions);
    }

    @Override
    public Publisher<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        Object argument = environment.getArgument("input");
        var input = this.objectMapper.convertValue(argument, ProjectEventInput.class);

        Supplier<Flux<IPayload>> supplier = () -> this.projectSubscriptions.findProjectSubscriptionById(input.projectId()).orElse(Flux.empty());
        return this.exceptionWrapper.wrapFlux(supplier, input);
    }
}
