/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.EditLabelAppearanceInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to edit the label's appearance.
 *
 * @author sbegaudeau
 */
@Service
public class EditLabelAppearanceMutationRunner implements IMutationRunner<EditLabelAppearanceInput> {

    private static final String EDIT_LABEL_APPEARANCE_MUTATION = """
            mutation editLabelAppearance($input: EditLabelAppearanceInput!) {
                editLabelAppearance(input: $input) {
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

    public EditLabelAppearanceMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(EditLabelAppearanceInput input) {
        return this.graphQLRequestor.execute(EDIT_LABEL_APPEARANCE_MUTATION, input);
    }
}
