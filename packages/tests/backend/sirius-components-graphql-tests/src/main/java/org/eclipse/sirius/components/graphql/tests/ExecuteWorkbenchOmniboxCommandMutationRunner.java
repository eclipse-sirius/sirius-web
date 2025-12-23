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
package org.eclipse.sirius.components.graphql.tests;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteWorkbenchOmniboxCommandInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to execute an omnibox command with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class ExecuteWorkbenchOmniboxCommandMutationRunner implements IMutationRunner<ExecuteWorkbenchOmniboxCommandInput> {

    private static final String EXECUTE_WORKBENCH_OMNIBOX_COMMAND = """
            mutation executeWorkbenchOmniboxCommand($input: ExecuteWorkbenchOmniboxCommandInput!) {
              executeWorkbenchOmniboxCommand(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ExecuteWorkbenchOmniboxCommandMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(ExecuteWorkbenchOmniboxCommandInput input) {
        return this.graphQLRequestor.execute(EXECUTE_WORKBENCH_OMNIBOX_COMMAND, input);
    }

}
