/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.gantt.dto.input.GetNonWorkingDaysInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to get non-working days of a Gantt with the GraphQL API.
 *
 * @author ncouvert
 */
@Service
public class GetNonWorkingDaysQueryRunner implements IMutationRunner<GetNonWorkingDaysInput> {

    private static final String GET_NON_WORKING_DAYS_QUERY = """
              query getNonWorkingDays($editingContextId: ID!, $representationId: ID!) {
                  viewer {
                    editingContext(editingContextId: $editingContextId) {
                      getNonWorkingDays(representationId: $representationId) {
                        holidays
                        weekends
                      }
                    }
                  }
                }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public GetNonWorkingDaysQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(GetNonWorkingDaysInput input) {
        Map<String, Object> variables = Map.of(
                "editingContextId", input.editingContextId(),
                "representationId", input.representationId()
        );

        return this.graphQLRequestor.execute(
                GET_NON_WORKING_DAYS_QUERY,
                variables
        );
    }
}
