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
package org.eclipse.sirius.components.gantt.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.dto.input.CreateGanttTaskInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to create a Task with the GraphQL API.
 *
 * @author lfasani
 */
@Service
public class CreateTaskMutationRunner implements IMutationRunner<CreateGanttTaskInput> {

    private static final String CREATE_TASK_MUTATION = """
              mutation createGanttTask($input: CreateGanttTaskInput!) {
                createGanttTask(input: $input) {
                  __typename
                  ... on ErrorPayload {
                    messages {
                      body
                      level
                    }
                  }
                  ... on SuccessPayload {
                    messages {
                      body
                      level
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateTaskMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateGanttTaskInput input) {
        return this.graphQLRequestor.execute(CREATE_TASK_MUTATION, input);
    }
}
