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
package org.eclipse.sirius.components.graphql.tests;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.dto.DeleteRepresentationInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to delete a representation with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class DeleteRepresentationMutationRunner implements IMutationRunner<DeleteRepresentationInput> {

    private static final String DELETE_REPRESENTATION_MUTATION = """
            mutation deleteRepresentation($input: DeleteRepresentationInput!) {
              deleteRepresentation(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteRepresentationMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteRepresentationInput input) {
        return this.graphQLRequestor.execute(DELETE_REPRESENTATION_MUTATION, input);
    }

}
