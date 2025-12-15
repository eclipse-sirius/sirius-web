/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.tables.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.tables.dto.InvokeToolMenuEntryInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke a tool menu entry with the GraphQL API.
 *
 * @author frouene
 */
@Service
public class InvokeToolMenuEntryMutationRunner implements IMutationRunner<InvokeToolMenuEntryInput> {

    private static final String INVOKE_TOOL_MENU_ENTRY_MUTATION = """
            mutation invokeToolMenuEntry($input: InvokeToolMenuEntryInput!) {
              invokeToolMenuEntry(input: $input) {
                __typename
                ... on ErrorPayload {
                  message
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeToolMenuEntryMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(InvokeToolMenuEntryInput input) {
        return this.graphQLRequestor.execute(INVOKE_TOOL_MENU_ENTRY_MUTATION, input);
    }
}
