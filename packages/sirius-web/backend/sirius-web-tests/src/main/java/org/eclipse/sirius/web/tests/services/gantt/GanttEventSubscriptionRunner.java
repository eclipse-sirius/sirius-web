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
package org.eclipse.sirius.web.tests.services.gantt;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.dto.input.GanttEventInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the gantt event subscription with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class GanttEventSubscriptionRunner implements ISubscriptionRunner<GanttEventInput> {

    private static final String GANTT_EVENT_SUBSCRIPTION = """
            subscription ganttEvent($input: GanttEventInput!) {
              ganttEvent(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public GanttEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(GanttEventInput input) {
        return this.graphQLRequestor.subscribe(GANTT_EVENT_SUBSCRIPTION, input);
    }
}
