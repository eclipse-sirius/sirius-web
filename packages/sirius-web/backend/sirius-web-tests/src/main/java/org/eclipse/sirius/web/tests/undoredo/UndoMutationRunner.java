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

package org.eclipse.sirius.web.tests.undoredo;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.UndoInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to undo a mutation.
 *
 * @author mcharfadi
 */
@Service
public class UndoMutationRunner implements IMutationRunner<UndoInput> {

    private static final String CREATE_UNDO_MUTATION = """
            mutation undo($input: UndoInput!) {
                undo(input: $input) {
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

    public UndoMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(UndoInput input) {
        return this.graphQLRequestor.execute(CREATE_UNDO_MUTATION, input);
    }
}
