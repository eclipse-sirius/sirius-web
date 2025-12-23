/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.sirius.components.collaborative.diagrams.dto.DropNodesInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to drop nodes from one container to another.
 *
 * @author sbegaudeau
 */
@Service
public class DropNodesMutationRunner implements IMutationRunner<DropNodesInput> {

    private static final String DROP_NODE_MUTATION = """
            mutation dropNodes($input: DropNodesInput!) {
              dropNodes(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DropNodesMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(DropNodesInput input) {
        return this.graphQLRequestor.execute(DROP_NODE_MUTATION, input);
    }
}
