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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IQueryRunner;
import org.springframework.stereotype.Service;

/**
 * The query runner to retrieve omnibox commands.
 *
 * @author gcoutable
 */
@Service
public class OmniboxCommandsQueryRunner implements IQueryRunner {

    private static final String OMNIBOX_COMMANDS = """
            query getOmniboxCommands($contextEntries: [OmniboxContextEntry!]!, $query: String!) {
                viewer {
                  omniboxCommands(contextEntries: $contextEntries, query: $query) {
                    edges {
                      node {
                        id
                        label
                        kind
                        iconURLs
                      }
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public OmniboxCommandsQueryRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(Map<String, Object> variables) {
        return this.graphQLRequestor.execute(OMNIBOX_COMMANDS, variables);
    }
}
