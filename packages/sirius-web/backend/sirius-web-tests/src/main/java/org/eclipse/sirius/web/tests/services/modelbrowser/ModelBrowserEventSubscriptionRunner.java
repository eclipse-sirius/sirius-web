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
package org.eclipse.sirius.web.tests.services.modelbrowser;

import java.util.Objects;

import graphql.execution.DataFetcherResult;
import org.eclipse.sirius.components.collaborative.browser.dto.ModelBrowserEventInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the model browser event subscription with the GraphQL API.
 *
 * @author Jerome Gout
 */
@Service
public class ModelBrowserEventSubscriptionRunner implements ISubscriptionRunner<ModelBrowserEventInput> {

    private static final String MODEL_BROWSER_EVENT_SUBSCRIPTION = """
            subscription modelBrowserEvent($input: ModelBrowserEventInput!) {
              modelBrowserEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ModelBrowserEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(ModelBrowserEventInput input) {
        return this.graphQLRequestor.subscribe(MODEL_BROWSER_EVENT_SUBSCRIPTION, input)
                .filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData);
    }

}
