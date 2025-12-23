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
package org.eclipse.sirius.components.gantt.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.dto.input.EditGanttTaskInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to edit a Task with the GraphQL API.
 *
 * @author lfasani
 */
@Service
public class EditTaskMutationRunner implements IMutationRunner<EditGanttTaskInput> {

    private static final String EDIT_TASK_MUTATION = """
            mutation editGanttTask($input: EditGanttTaskInput!) {
              editGanttTask(input: $input) {
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

    public EditTaskMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(EditGanttTaskInput input) {
        return this.graphQLRequestor.execute(EDIT_TASK_MUTATION, input);
    }
}
