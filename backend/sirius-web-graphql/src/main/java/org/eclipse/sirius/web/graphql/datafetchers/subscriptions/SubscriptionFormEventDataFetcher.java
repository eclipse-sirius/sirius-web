/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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

import java.security.Principal;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLSubscriptionTypes;
import org.eclipse.sirius.web.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.dto.SubscribersUpdatedEventPayload;
import org.eclipse.sirius.web.collaborative.api.services.IProjectEventProcessorRegistry;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.collaborative.forms.api.FormConfiguration;
import org.eclipse.sirius.web.collaborative.forms.api.IFormEventProcessor;
import org.eclipse.sirius.web.collaborative.forms.api.dto.FormEventInput;
import org.eclipse.sirius.web.collaborative.forms.api.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.web.collaborative.forms.api.dto.WidgetSubscriptionsUpdatedEventPayload;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.graphql.datafetchers.IDataFetchingEnvironmentService;
import org.eclipse.sirius.web.graphql.schema.SubscriptionTypeProvider;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.reactivestreams.Publisher;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

/**
 * The data fetcher used to send the refreshed form to a subscription.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Subscription {
 *   formEvent(input: FormEventInput): FormEventPayload
 * }
 * </pre>
 *
 * @author sbegaudeau
 * @author pcdavid
 */
// @formatter:off
@GraphQLSubscriptionTypes(
    input = FormEventInput.class,
    payloads = {
        FormRefreshedEventPayload.class,
        WidgetSubscriptionsUpdatedEventPayload.class,
        SubscribersUpdatedEventPayload.class,
        PreDestroyPayload.class,
    }
)
@SubscriptionDataFetcher(type = SubscriptionTypeProvider.TYPE, field = SubscriptionFormEventDataFetcher.FORM_EVENT_FIELD)
// @formatter:on
public class SubscriptionFormEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<IPayload>> {

    public static final String FORM_EVENT_FIELD = "formEvent"; //$NON-NLS-1$

    private final IDataFetchingEnvironmentService dataFetchingEnvironmentService;

    private final IProjectEventProcessorRegistry projectEventProcessorRegistry;

    public SubscriptionFormEventDataFetcher(IDataFetchingEnvironmentService dataFetchingEnvironmentService, IProjectEventProcessorRegistry projectEventProcessorRegistry) {
        this.dataFetchingEnvironmentService = Objects.requireNonNull(dataFetchingEnvironmentService);
        this.projectEventProcessorRegistry = Objects.requireNonNull(projectEventProcessorRegistry);
    }

    @Override
    public Publisher<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        var input = this.dataFetchingEnvironmentService.getInput(environment, FormEventInput.class);

        Principal principal = this.dataFetchingEnvironmentService.getPrincipal(environment).orElse(null);
        String subscriptionId = this.dataFetchingEnvironmentService.getSubscriptionId(environment);

        var formConfiguration = new FormConfiguration(input.getFormId());

        // @formatter:off
        return this.projectEventProcessorRegistry.getOrCreateProjectEventProcessor(input.getProjectId())
                .flatMap(processor -> processor.acquireRepresentationEventProcessor(IFormEventProcessor.class, formConfiguration, new SubscriptionDescription(principal, subscriptionId)))
                .map(IRepresentationEventProcessor::getOutputEvents)
                .orElse(Flux.empty());
        // @formatter:on
    }

}
