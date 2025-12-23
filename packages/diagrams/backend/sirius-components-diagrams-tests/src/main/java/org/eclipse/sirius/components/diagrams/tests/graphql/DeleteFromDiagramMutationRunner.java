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

import org.eclipse.sirius.components.collaborative.diagrams.dto.DeleteFromDiagramInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Delete from diagram mutation runner.
 *
 * @author mcharfadi
 */
@Service
public class DeleteFromDiagramMutationRunner implements IMutationRunner<DeleteFromDiagramInput> {

    private static final String DELETE_FROM_DIAGRAM_MUTATION = """
            mutation deleteFromDiagram($input: DeleteFromDiagramInput!) {
               deleteFromDiagram(input: $input) {
                 __typename
                 ... on ErrorPayload {
                   messages {
                     body
                     level
                   }
                 }
                 ... on DeleteFromDiagramSuccessPayload {
                   messages {
                     body
                     level
                   }
                 }
               }
             }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteFromDiagramMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(DeleteFromDiagramInput input) {
        return this.graphQLRequestor.execute(DELETE_FROM_DIAGRAM_MUTATION, input);
    }
}
