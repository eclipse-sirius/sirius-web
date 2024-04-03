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
package org.eclipse.sirius.web.services.hierarchy;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.charts.HierarchyEventInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the hierarchy event subscription with the GraphQL API.
 *
 * @author pcdavid
 */
@Service
public class HierarchyEventSubscriptionRunner implements ISubscriptionRunner<HierarchyEventInput> {

    private static final String HIERARCHY_EVENT_SUBSCRIPTION = """
            subscription hierarchyEvent($input: HierarchyEventInput!) {
              hierarchyEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public HierarchyEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(HierarchyEventInput input) {
        return this.graphQLRequestor.subscribe(HIERARCHY_EVENT_SUBSCRIPTION, input);
    }

}
