/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

import org.eclipse.sirius.components.collaborative.tables.dto.InvokeRowContextMenuEntryInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke a row context menu entry with the GraphQL API.
 *
 * @author Jerome Gout
 */
@Service
public class InvokeRowContextMenuEntryMutationRunner implements IMutationRunner<InvokeRowContextMenuEntryInput> {

    private static final String INVOKE_ROW_CONTEXT_MENU_ENTRY_MUTATION = """
            mutation invokeRowContextMenuEntry($input: InvokeRowContextMenuEntryInput!) {
              invokeRowContextMenuEntry(input: $input) {
                __typename
                ... on ErrorPayload {
                  messages {
                    body
                    level
                  }
                }
                ... on SuccessPayload {
                  messages {
                    body
                    level
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeRowContextMenuEntryMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(InvokeRowContextMenuEntryInput input) {
        return this.graphQLRequestor.execute(INVOKE_ROW_CONTEXT_MENU_ENTRY_MUTATION, input);
    }
}
