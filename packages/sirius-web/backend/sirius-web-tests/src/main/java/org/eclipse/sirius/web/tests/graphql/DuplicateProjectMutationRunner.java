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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.project.dto.DuplicateProjectInput;
import org.springframework.stereotype.Service;

/**
 * Used to duplicate a project with the GraphQL API.
 * @author Arthur Daussy
 */
@Service
public class DuplicateProjectMutationRunner implements IMutationRunner<DuplicateProjectInput> {

    private static final String DUPLICATE_PROJECT_MUTATION = """
          mutation duplicateProject($input: DuplicateProjectInput!) {
            duplicateProject(input: $input) {
              __typename
              ... on DuplicateProjectSuccessPayload {
                project {
                  id
                }
              }
            }
          }
          """;

    private final IGraphQLRequestor graphQLRequestor;

    public DuplicateProjectMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DuplicateProjectInput input) {
        return this.graphQLRequestor.execute(DUPLICATE_PROJECT_MUTATION, input);
    }
}
