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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnTwoDiagramElementsToolInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke a tool on two diagram elements with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class InvokeSingleClickOnTwoDiagramElementsToolMutationRunner implements IMutationRunner<InvokeSingleClickOnTwoDiagramElementsToolInput> {

    private static final String INVOKE_SINGLE_CLICK_ON_TWO_DIAGRAM_ELEMENTS_TOOL_MUTATION = """
            mutation invokeSingleClickOnTwoDiagramElementsTool($input: InvokeSingleClickOnTwoDiagramElementsToolInput!) {
              invokeSingleClickOnTwoDiagramElementsTool(input: $input) {
                __typename
                ... on InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
                  newSelection {
                    entries {
                      id
                      kind
                      __typename
                    }
                    __typename
                  }
                  messages {
                    body
                    level
                    __typename
                  }
                  __typename
                }
                ... on ErrorPayload {
                  messages {
                    body
                    level
                    __typename
                  }
                  __typename
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeSingleClickOnTwoDiagramElementsToolMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(InvokeSingleClickOnTwoDiagramElementsToolInput input) {
        return this.graphQLRequestor.execute(INVOKE_SINGLE_CLICK_ON_TWO_DIAGRAM_ELEMENTS_TOOL_MUTATION, input);
    }
}
