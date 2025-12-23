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
package org.eclipse.sirius.components.diagrams.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.InvokeActionInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to invoke action on a diagram element.
 *
 * @author arichard
 */
@Service
public class InvokeActionMutationRunner implements IMutationRunner<InvokeActionInput> {

    private static final String INVOKE_ACTION_MUTATION = """
            mutation invokeAction($input: InvokeActionInput!) {
              invokeAction(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeActionMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(InvokeActionInput input) {
        return this.graphQLRequestor.execute(INVOKE_ACTION_MUTATION, input);
    }
}
