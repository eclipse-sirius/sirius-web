/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.graphql.datafetchers.subscriptions;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.web.collaborative.api.dto.ProjectEventInput;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.graphql.datafetchers.IDataFetchingEnvironmentService;
import org.eclipse.sirius.web.graphql.schema.SubscriptionTypeProvider;
import org.eclipse.sirius.web.services.api.dto.IPayload;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.reactivestreams.Publisher;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

/**
 * The data fetcher used to send the project related events to a subscription.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Subscription {
 *   projectEvent(input: ProjectEventInput): ProjectEventPayload
 * }
 * </pre>
 *
 * @author arichard
 */
@SubscriptionDataFetcher(type = SubscriptionTypeProvider.TYPE, field = SubscriptionTypeProvider.PROJECT_EVENT_FIELD)
public class SubscriptionProjectEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<IPayload>> {

    private final IDataFetchingEnvironmentService dataFetchingEnvironmentService;

    private final IProjectEventProcessorRegistry projectEventProcessorRegistry;

    public SubscriptionProjectEventDataFetcher(IDataFetchingEnvironmentService dataFetchingEnvironmentService, IProjectEventProcessorRegistry projectEventProcessorRegistry) {
        this.dataFetchingEnvironmentService = Objects.requireNonNull(dataFetchingEnvironmentService);
        this.projectEventProcessorRegistry = Objects.requireNonNull(projectEventProcessorRegistry);
    }

    @Override
    public Publisher<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        var input = this.dataFetchingEnvironmentService.getInput(environment, ProjectEventInput.class);

        // @formatter:off
        UUID projectId = input.getProjectId();
        return this.projectEventProcessorRegistry.getOrCreateProjectEventProcessor(projectId)
                .map(IProjectEventProcessor::getOutputEvents)
                .orElse(Flux.empty());
        // @formatter:on
    }

}
