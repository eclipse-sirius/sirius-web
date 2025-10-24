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

import graphql.execution.DataFetcherResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.eclipse.sirius.web.application.views.relatedelements.dto.RelatedElementsEventInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the related elements event subscription with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class RelatedElementsEventSubscriptionRunner implements ISubscriptionRunner<RelatedElementsEventInput> {

    private static final String RELATED_ELEMENTS_EVENT_SUBSCRIPTION = """
            subscription relatedElementsEvent($input: RelatedElementsEventInput!) {
              relatedElementsEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public RelatedElementsEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(RelatedElementsEventInput input) {
        return this.graphQLRequestor.subscribe(RELATED_ELEMENTS_EVENT_SUBSCRIPTION, input)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
    }


}
