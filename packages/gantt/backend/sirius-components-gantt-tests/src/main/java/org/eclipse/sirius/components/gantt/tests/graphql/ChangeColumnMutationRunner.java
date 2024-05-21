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

import org.eclipse.sirius.components.collaborative.gantt.dto.input.ChangeGanttColumnInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the column in gantt table with the GraphQL API.
 *
 * @author lfasani
 */
@Service
public class ChangeColumnMutationRunner implements IMutationRunner<ChangeGanttColumnInput> {

    private static final String CHANGE_COLUMN_MUTATION = """
                mutation changeGanttColumn($input: ChangeGanttColumnInput!) {
                  changeGanttColumn(input: $input) {
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

    public ChangeColumnMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(ChangeGanttColumnInput input) {
        return this.graphQLRequestor.execute(CHANGE_COLUMN_MUTATION, input);
    }
}
