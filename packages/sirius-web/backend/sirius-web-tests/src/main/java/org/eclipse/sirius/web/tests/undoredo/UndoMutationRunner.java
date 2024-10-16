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

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.undo.dto.UndoInput;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Used to undo a mutation.
 *
 * @author mcharfadi
 */
@Service
public class UndoMutationRunner implements IMutationRunner<UndoInput> {

    private static final String CREATE_UNDO_MUTATION = """
            mutation undo($input: UndoRedoInput!) {
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