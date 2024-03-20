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

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.ISubscriptionRunner;
import org.eclipse.sirius.web.application.project.dto.ProjectEventInput;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 * Used to get the project event subscription from the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class ProjectEventSubscriptionRunner implements ISubscriptionRunner<ProjectEventInput> {

    private static final String PROJECT_EVENT_SUBSCRIPTION = """
            subscription projectEvent($input: ProjectEventInput!) {
              projectEvent(input: $input) {
                ... on ProjectRenamedEventPayload {
                  newName
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ProjectEventSubscriptionRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public Flux<Object> run(ProjectEventInput input) {
        return this.graphQLRequestor.subscribe(PROJECT_EVENT_SUBSCRIPTION, input);
    }

}
