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

import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeTaskCollapseStateInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the state collapse/expand of a Task with the GraphQL API.
 *
 * @author lfasani
 */
@Service
public class ChangeTaskCollapseStateMutationRunner implements IMutationRunner<ChangeTaskCollapseStateInput> {

    private static final String CHANGE_TASK_COLLAPSE_STATE_MUTATION = """
              mutation changeGanttTaskCollapseState($input: ChangeGanttTaskCollapseStateInput!) {
                changeGanttTaskCollapseState(input: $input) {
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

    public ChangeTaskCollapseStateMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(ChangeTaskCollapseStateInput input) {
        return this.graphQLRequestor.execute(CHANGE_TASK_COLLAPSE_STATE_MUTATION, input);
    }
}
