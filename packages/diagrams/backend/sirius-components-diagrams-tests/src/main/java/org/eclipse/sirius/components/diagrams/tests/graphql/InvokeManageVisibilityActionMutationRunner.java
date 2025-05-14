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

import org.eclipse.sirius.components.collaborative.diagrams.dto.managevisibility.InvokeManageVisibilityActionInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Used to invoke manage visibility action on a diagram element.
 *
 * @author mcharfadi
 */
@Service
public class InvokeManageVisibilityActionMutationRunner implements IMutationRunner<InvokeManageVisibilityActionInput> {

    private static final String INVOKE_ACTION_MUTATION = """
            mutation invokeManageVisibilityAction($input: InvokeManageVisibilityActionInput!) {
              invokeManageVisibilityAction(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public InvokeManageVisibilityActionMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(InvokeManageVisibilityActionInput input) {
        return this.graphQLRequestor.execute(INVOKE_ACTION_MUTATION, input);
    }
}
