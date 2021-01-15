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

import java.security.Principal;
import java.util.Objects;

import org.eclipse.sirius.web.annotations.graphql.GraphQLSubscriptionTypes;
import org.eclipse.sirius.web.annotations.spring.graphql.SubscriptionDataFetcher;
import org.eclipse.sirius.web.collaborative.api.dto.PreDestroyPayload;
import org.eclipse.sirius.web.collaborative.api.dto.SubscribersUpdatedEventPayload;
import org.eclipse.sirius.web.collaborative.api.services.IEditingContextEventProcessorRegistry;
import org.eclipse.sirius.web.collaborative.api.services.IRepresentationEventProcessor;
import org.eclipse.sirius.web.collaborative.api.services.SubscriptionDescription;
import org.eclipse.sirius.web.collaborative.trees.api.ITreeEventProcessor;
import org.eclipse.sirius.web.collaborative.trees.api.TreeConfiguration;
import org.eclipse.sirius.web.collaborative.trees.api.TreeEventInput;
import org.eclipse.sirius.web.collaborative.trees.api.TreeRefreshedEventPayload;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.graphql.datafetchers.IDataFetchingEnvironmentService;
import org.eclipse.sirius.web.graphql.schema.SubscriptionTypeProvider;
import org.eclipse.sirius.web.spring.graphql.api.IDataFetcherWithFieldCoordinates;
import org.reactivestreams.Publisher;

import graphql.schema.DataFetchingEnvironment;
import reactor.core.publisher.Flux;

/**
 * The data fetcher used to send the refreshed tree to a subscription.
 * <p>
 * It will be used to fetch the data for the following GraphQL field:
 * </p>
 *
 * <pre>
 * type Subscription {
 *   treeEvent(input: TreeEventInput): TreeEventPayload
 * }
 * </pre>
 *
 * @author hmarchadour
 * @author pcdavid
 */
// @formatter:off
@GraphQLSubscriptionTypes(
    input = TreeEventInput.class,
    payloads = {
        TreeRefreshedEventPayload.class,
        SubscribersUpdatedEventPayload.class,
        PreDestroyPayload.class,
    }
)
@SubscriptionDataFetcher(type = SubscriptionTypeProvider.TYPE, field = SubscriptionTreeEventDataFetcher.TREE_EVENT_FIELD)
// @formatter:on
public class SubscriptionTreeEventDataFetcher implements IDataFetcherWithFieldCoordinates<Publisher<IPayload>> {

    public static final String TREE_EVENT_FIELD = "treeEvent"; //$NON-NLS-1$

    private final IDataFetchingEnvironmentService dataFetchingEnvironmentService;

    private final IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry;

    public SubscriptionTreeEventDataFetcher(IDataFetchingEnvironmentService dataFetchingEnvironmentService, IEditingContextEventProcessorRegistry editingContextEventProcessorRegistry) {
        this.dataFetchingEnvironmentService = Objects.requireNonNull(dataFetchingEnvironmentService);
        this.editingContextEventProcessorRegistry = Objects.requireNonNull(editingContextEventProcessorRegistry);
    }

    @Override
    public Publisher<IPayload> get(DataFetchingEnvironment environment) throws Exception {
        var input = this.dataFetchingEnvironmentService.getInput(environment, TreeEventInput.class);

        String subscriptionId = this.dataFetchingEnvironmentService.getSubscriptionId(environment);
        Principal principal = this.dataFetchingEnvironmentService.getPrincipal(environment).orElse(null);

        var treeConfiguration = new TreeConfiguration(input.getProjectId(), input.getExpanded());

        // @formatter:off
        return this.editingContextEventProcessorRegistry.getOrCreateEditingContextEventProcessor(input.getProjectId())
                .flatMap(processor -> processor.acquireRepresentationEventProcessor(ITreeEventProcessor.class, treeConfiguration, new SubscriptionDescription(principal, subscriptionId)))
                .map(IRepresentationEventProcessor::getOutputEvents)
                .orElse(Flux.empty());
        // @formatter:on
    }

}
