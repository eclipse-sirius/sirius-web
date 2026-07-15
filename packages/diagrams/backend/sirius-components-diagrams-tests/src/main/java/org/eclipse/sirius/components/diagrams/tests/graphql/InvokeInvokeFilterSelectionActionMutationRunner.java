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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.toolbar.tools.InvokeFilterSelectionInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke filter action in the diagram toolbar filter menu on diagram elements.
 *
 * @author mcharfadi
 */
@Service
public class InvokeInvokeFilterSelectionActionMutationRunner implements IMutationRunner<InvokeFilterSelectionInput> {

    private static final String INVOKE_FILTER_MUTATION = """
              mutation invokeFilterSelection($input: InvokeFilterSelectionInput!) {
                invokeFilterSelection(input: $input) {
                  __typename
                  ... on InvokeFilterSelectionSuccessPayload {
                    id
                    newSelection
                    messages {
                      body
                      level
                    }
                  }
                  ... on ErrorPayload {
                    messages {
                      body
                      level
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeInvokeFilterSelectionActionMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(InvokeFilterSelectionInput input) {
        return this.graphQLRequestor.execute(INVOKE_FILTER_MUTATION, input);
    }
}
