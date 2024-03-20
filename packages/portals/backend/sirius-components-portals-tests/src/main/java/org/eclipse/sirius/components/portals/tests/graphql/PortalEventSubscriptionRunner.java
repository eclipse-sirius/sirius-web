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
package org.eclipse.sirius.components.portals.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.portals.dto.PortalEventInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the portal event subscription with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class PortalEventSubscriptionRunner implements ISubscriptionRunner<PortalEventInput> {

    private static final String PORTAL_EVENT_SUBSCRIPTION = """
            subscription portalEvent($input: PortalEventInput!) {
              portalEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public PortalEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(PortalEventInput input) {
        return this.graphQLRequestor.subscribe(PORTAL_EVENT_SUBSCRIPTION, input);
    }

}
