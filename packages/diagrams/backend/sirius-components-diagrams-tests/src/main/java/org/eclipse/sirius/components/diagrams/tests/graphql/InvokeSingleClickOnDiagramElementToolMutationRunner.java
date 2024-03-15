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

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeSingleClickOnDiagramElementToolInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke a tool on a single diagram element with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class InvokeSingleClickOnDiagramElementToolMutationRunner implements IMutationRunner<InvokeSingleClickOnDiagramElementToolInput> {

    private static final String INVOKE_SINGLE_CLICK_ON_DIAGRAM_ELEMENT_TOOL_MUTATION = """
            mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
              invokeSingleClickOnDiagramElementTool(input: $input) {
                __typename
                ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
                  newSelection {
                    entries {
                      id
                      label
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

    public InvokeSingleClickOnDiagramElementToolMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = graphQLRequestor;
    }

    @Override
    public String run(InvokeSingleClickOnDiagramElementToolInput input) {
        return this.graphQLRequestor.execute(INVOKE_SINGLE_CLICK_ON_DIAGRAM_ELEMENT_TOOL_MUTATION, input);
    }
}
