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

import org.eclipse.sirius.components.collaborative.tables.dto.EditCheckboxCellInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to edit a checkbox with the GraphQL API.
 *
 * @author lfasani
 */
@Service
public class EditCheckboxCellMutationRunner implements IMutationRunner<EditCheckboxCellInput> {

    private static final String EDIT_CHECKBOX_CELL_MUTATION = """
          mutation editCheckboxCell($input: EditCheckboxCellInput!) {
            editCheckboxCell(input: $input) {
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

    public EditCheckboxCellMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(EditCheckboxCellInput input) {
        return this.graphQLRequestor.execute(EDIT_CHECKBOX_CELL_MUTATION, input);
    }

}
