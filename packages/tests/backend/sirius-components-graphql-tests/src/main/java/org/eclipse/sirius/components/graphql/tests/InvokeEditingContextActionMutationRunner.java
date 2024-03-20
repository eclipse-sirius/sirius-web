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

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.dto.InvokeEditingContextActionInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke an editing context action with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class InvokeEditingContextActionMutationRunner implements IMutationRunner<InvokeEditingContextActionInput> {

    private static final String INVOKE_EDITING_CONTEXT_ACTION = """
            mutation invokeEditingContextAction($input: InvokeEditingContextActionInput!) {
              invokeEditingContextAction(input: $input) {
                __typename
                ... on ErrorPayload {
                  message
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeEditingContextActionMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(InvokeEditingContextActionInput input) {
        return this.graphQLRequestor.execute(INVOKE_EDITING_CONTEXT_ACTION, input);
    }

}
