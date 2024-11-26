/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.tables.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.dto.ResizeTableRowInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the row size in table with the GraphQL API.
 *
 * @author Jerome Gout
 */
@Service
public class ResizeTableRowMutationRunner implements IMutationRunner<ResizeTableRowInput> {

    private static final String RESIZE_ROW_MUTATION = """
                mutation resizeTableRow($input: ResizeTableRowInput!) {
                  resizeTableRow(input: $input) {
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

    public ResizeTableRowMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(ResizeTableRowInput input) {
        return this.graphQLRequestor.execute(RESIZE_ROW_MUTATION, input);
    }
}
