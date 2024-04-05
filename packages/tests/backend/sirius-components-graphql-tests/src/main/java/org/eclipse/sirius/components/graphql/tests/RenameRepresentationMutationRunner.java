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

import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to rename a representation with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class RenameRepresentationMutationRunner implements IMutationRunner<RenameRepresentationInput> {

    private static final String RENAME_REPRESENTATION_MUTATION = """
            mutation renameRepresentation($input: RenameRepresentationInput!) {
              renameRepresentation(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public RenameRepresentationMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(RenameRepresentationInput input) {
        return this.graphQLRequestor.execute(RENAME_REPRESENTATION_MUTATION, input);
    }
}
