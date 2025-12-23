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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * The query runner to retrieve projects omnibox commands.
 *
 * @author gdaniel
 */
@Service
public class ProjectsOmniboxCommandsQueryRunner implements IQueryRunner {

    private static final String PROJECTS_OMNIBOX_COMMANDS = """
            query getProjectsOmniboxCommands($query: String!) {
              viewer {
                projectsOmniboxCommands(query: $query) {
                  edges {
                    node {
                      id
                      label
                      iconURLs
                      description
                    }
                  }
                  pageInfo {
                    count
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ProjectsOmniboxCommandsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(PROJECTS_OMNIBOX_COMMANDS, variables);
    }
}
