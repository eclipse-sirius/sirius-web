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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.project.dto.DeleteProjectInput;
import org.springframework.stereotype.Service;

/**
 * Used to delete a project with the GraphQL API.
 * @author gdaniel
 */
@Service
public class DeleteProjectMutationRunner implements IMutationRunner<DeleteProjectInput> {

    private static final String DELETE_PROJECT_MUTATION = """
            mutation deleteProject($input: DeleteProjectInput!) {
              deleteProject(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteProjectMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteProjectInput input) {
        return this.graphQLRequestor.execute(DELETE_PROJECT_MUTATION, input);
    }
}
