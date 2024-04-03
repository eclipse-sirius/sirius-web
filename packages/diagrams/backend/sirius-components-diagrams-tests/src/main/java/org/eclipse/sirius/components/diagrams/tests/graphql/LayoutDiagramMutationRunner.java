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

import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutDiagramInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to update the layout of a diagram.
 *
 * @author pcdavid
 */
@Service
public class LayoutDiagramMutationRunner implements IMutationRunner<LayoutDiagramInput> {

    private static final String LAYOUT_DIAGRAM_MUTATION = """
          mutation layoutDiagram($input: LayoutDiagramInput!) {
            layoutDiagram(input: $input) {
              __typename
              ... on SuccessPayload {
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

    public LayoutDiagramMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(LayoutDiagramInput input) {
        return this.graphQLRequestor.execute(LAYOUT_DIAGRAM_MUTATION, input);
    }
}
