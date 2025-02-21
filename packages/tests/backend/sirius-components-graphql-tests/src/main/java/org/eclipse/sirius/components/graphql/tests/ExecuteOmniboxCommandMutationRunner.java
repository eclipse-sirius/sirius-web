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

import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteOmniboxCommandInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to execute an omnibox command with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class ExecuteOmniboxCommandMutationRunner implements IMutationRunner<ExecuteOmniboxCommandInput> {

    private static final String EXECUTE_OMNIBOX_COMMAND = """
            mutation executeOmniboxCommand($input: ExecuteOmniboxCommandInput!) {
              executeOmniboxCommand(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ExecuteOmniboxCommandMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(ExecuteOmniboxCommandInput input) {
        return this.graphQLRequestor.execute(EXECUTE_OMNIBOX_COMMAND, input);
    }

}
