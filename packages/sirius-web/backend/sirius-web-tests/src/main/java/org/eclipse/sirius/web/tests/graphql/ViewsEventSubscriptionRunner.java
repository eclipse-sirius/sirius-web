/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.web.application.views.views.ViewsEventInput;
import org.springframework.stereotype.Service;

import graphql.execution.DataFetcherResult;

/**
 * Used to get the "views" view event subscription with the GraphQL API.
 *
 * @author tgiraudet
 */
@Service
public class ViewsEventSubscriptionRunner implements ISubscriptionRunner<ViewsEventInput> {

    private static final String REPRESENTATIONS_EVENT_SUBSCRIPTION = """
            subscription viewsEvent($input: ViewsEventInput!) {
              viewsEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ViewsEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLSubscriptionResult run(ViewsEventInput input) {
        var rawResult = this.graphQLRequestor.subscribe(REPRESENTATIONS_EVENT_SUBSCRIPTION, input);
        var flux = rawResult.flux()
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
        return new GraphQLSubscriptionResult(flux, rawResult.errors(), rawResult.extensions());
    }
}
