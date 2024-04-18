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

import org.eclipse.sirius.components.collaborative.diagrams.dto.DropOnDiagramInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to drop elements on a diagram.
 *
 * @author sbegaudeau
 */
@Service
public class DropOnDiagramMutationRunner implements IMutationRunner<DropOnDiagramInput> {

    private static final String DROP_ON_DIAGRAM_MUTATION = """
            mutation dropOnDiagram($input: DropOnDiagramInput!) {
              dropOnDiagram(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DropOnDiagramMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DropOnDiagramInput input) {
        return this.graphQLRequestor.execute(DROP_ON_DIAGRAM_MUTATION, input);
    }
}
