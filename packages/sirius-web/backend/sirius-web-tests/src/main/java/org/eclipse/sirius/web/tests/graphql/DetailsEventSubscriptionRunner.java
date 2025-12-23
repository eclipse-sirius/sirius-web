/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLSubscriptionResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.eclipse.sirius.web.application.views.details.dto.DetailsEventInput;
import org.springframework.stereotype.Service;

import graphql.execution.DataFetcherResult;

/**
 * Used to get the properties event subscription with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class DetailsEventSubscriptionRunner implements ISubscriptionRunner<DetailsEventInput> {

    private static final String DETAILS_EVENT_SUBSCRIPTION = """
            subscription detailsEvent($input: DetailsEventInput!) {
              detailsEvent(input: $input) {
                __typename
                ... on ErrorPayload {
                  message
                  messages {
                    level
                    body
                  }
                }
                ... on FormCapabilitiesRefreshedEventPayload {
                  capabilities {
                    canEdit
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DetailsEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLSubscriptionResult run(DetailsEventInput input) {
        var rawResult = this.graphQLRequestor.subscribe(DETAILS_EVENT_SUBSCRIPTION, input);
        var flux = rawResult.flux()
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
        return new GraphQLSubscriptionResult(flux, rawResult.errors(), rawResult.extensions());
    }

}
