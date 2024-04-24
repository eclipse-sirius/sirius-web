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
 * Used to get the related elements event subscription with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class DiagramFilterEventSubscriptionRunner implements ISubscriptionRunner<PropertiesEventInput> {

    private static final String DIAGRAM_FILTER_EVENT_SUBSCRIPTION = """
            subscription diagramFilterEvent($input: PropertiesEventInput!) {
              diagramFilterEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DiagramFilterEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(PropertiesEventInput input) {
        return this.graphQLRequestor.subscribe(DIAGRAM_FILTER_EVENT_SUBSCRIPTION, input);
    }
}
