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
package org.eclipse.sirius.components.tables.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.dto.EditSelectCellInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to edit a select cell with the GraphQL API.
 *
 * @author lfasani
 */
@Service
public class EditSelectCellMutationRunner implements IMutationRunner<EditSelectCellInput> {

    private static final String EDIT_SELECT_CELL_MUTATION = """
          mutation editSelectCell($input: EditSelectCellInput!) {
            editSelectCell(input: $input) {
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

    public EditSelectCellMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(EditSelectCellInput input) {
        return this.graphQLRequestor.execute(EDIT_SELECT_CELL_MUTATION, input);
    }

}
