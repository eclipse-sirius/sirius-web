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
import org.eclipse.sirius.web.application.project.dto.CreateProjectInput;
import org.springframework.stereotype.Service;

/**
 * Used to create a project with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class CreateProjectMutationRunner implements IMutationRunner<CreateProjectInput> {

    private static final String CREATE_PROJECT = """
            mutation createProject($input: CreateProjectInput!) {
              createProject(input: $input) {
                __typename
                ... on CreateProjectSuccessPayload {
                  project {
                    id
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateProjectMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateProjectInput input) {
        return this.graphQLRequestor.execute(CREATE_PROJECT, input);
    }

}
