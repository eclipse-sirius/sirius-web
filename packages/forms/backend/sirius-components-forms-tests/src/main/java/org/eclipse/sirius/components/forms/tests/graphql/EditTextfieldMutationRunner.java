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
package org.eclipse.sirius.components.forms.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.dto.EditTextfieldInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to edit a textfield with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class EditTextfieldMutationRunner implements IMutationRunner<EditTextfieldInput> {

    private static final String EDIT_TEXTFIELD_MUTATION = """
            mutation editTextfield($input: EditTextfieldInput!) {
              editTextfield(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public EditTextfieldMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(EditTextfieldInput input) {
        return this.graphQLRequestor.execute(EDIT_TEXTFIELD_MUTATION, input);
    }
}
