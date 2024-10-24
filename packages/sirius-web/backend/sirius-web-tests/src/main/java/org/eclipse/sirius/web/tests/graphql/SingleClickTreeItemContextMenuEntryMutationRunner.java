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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.dto.InvokeSingleClickTreeItemContextMenuEntryInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to execute a single click context menu entry with the GraphQL API.
 *
 * @author Jerome Gout
 */
@Service
public class SingleClickTreeItemContextMenuEntryMutationRunner implements IMutationRunner<InvokeSingleClickTreeItemContextMenuEntryInput> {

    private static final String INVOKE_SINGLE_CLICK_CONTEXT_MENU_ENTRY = """
            mutation invokeSingleClickTreeItemContextMenuEntry($input: InvokeSingleClickTreeItemContextMenuEntryInput!) {
              invokeSingleClickTreeItemContextMenuEntry(input: $input) {
                __typename
               ... on ErrorPayload {
                    message
                    messages {
                      level
                      body
                    }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public SingleClickTreeItemContextMenuEntryMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(InvokeSingleClickTreeItemContextMenuEntryInput input) {
        Map<String, Object> inputMap = Map.of(
                "id", input.id(),
                "editingContextId", input.editingContextId(),
                "representationId", input.representationId(),
                "treeItemId", input.treeItemId(),
                "menuEntryId", input.menuEntryId()
        );
        Map<String, Object> variables = Map.of("input", inputMap);
        return this.graphQLRequestor.execute(INVOKE_SINGLE_CLICK_CONTEXT_MENU_ENTRY, variables);
    }
}
