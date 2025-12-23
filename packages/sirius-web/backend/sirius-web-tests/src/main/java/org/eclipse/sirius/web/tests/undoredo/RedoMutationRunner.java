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

package org.eclipse.sirius.web.tests.undoredo;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.undo.dto.RedoInput;
import org.springframework.stereotype.Service;

/**
 * Used to redo a mutation.
 *
 * @author mcharfadi
 */
@Service
public class RedoMutationRunner implements IMutationRunner<RedoInput> {

    private static final String CREATE_REDO_MUTATION = """
            mutation redo($input: RedoInput!) {
                redo(input: $input) {
                  __typename
                  ... on SuccessPayload {
                    id
                  }
                  ... on ErrorPayload {
                    message
                  }
                }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public RedoMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(RedoInput input) {
        return this.graphQLRequestor.execute(CREATE_REDO_MUTATION, input);
    }
}