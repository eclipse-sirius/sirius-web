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
package org.eclipse.sirius.web.tests.services.explorer;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.eclipse.sirius.web.application.views.explorer.ExplorerEventInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the explorer event subscription with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class ExplorerEventSubscriptionRunner implements ISubscriptionRunner<ExplorerEventInput> {

    private static final String EXPLORER_EVENT_SUBSCRIPTION = """
            subscription explorerEvent($input: ExplorerEventInput!) {
              explorerEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ExplorerEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(ExplorerEventInput input) {
        return this.graphQLRequestor.subscribe(EXPLORER_EVENT_SUBSCRIPTION, input);
    }

}
