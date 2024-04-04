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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.dto.PropertiesEventInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the representations event subscription with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class RepresentationsEventSubscriptionRunner implements ISubscriptionRunner<PropertiesEventInput> {

    private static final String REPRESENTATIONS_EVENT_SUBSCRIPTION = """
            subscription representationsEvent($input: PropertiesEventInput!) {
              representationsEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public RepresentationsEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(PropertiesEventInput input) {
        return this.graphQLRequestor.subscribe(REPRESENTATIONS_EVENT_SUBSCRIPTION, input);
    }
}
