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
import org.eclipse.sirius.web.application.project.dto.RenameProjectInput;
import org.springframework.stereotype.Service;

/**
 * Used to rename a project with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class RenameProjectMutationRunner implements IMutationRunner<RenameProjectInput> {

    private static final String RENAME_PROJECT_MUTATION = """
            mutation renameProject($input: RenameProjectInput!) {
              renameProject(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public RenameProjectMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(RenameProjectInput input) {
        return this.graphQLRequestor.execute(RENAME_PROJECT_MUTATION, input);
    }
}
