/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.diagrams.dto.HideDiagramElementInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to hide or show faded elements with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class HideDiagramElementMutationRunner implements IMutationRunner<HideDiagramElementInput> {

    private static final String HIDE_DIAGRAM_ELEMENT_MUTATION = """
            mutation hideDiagramElement($input: HideDiagramElementInput!) {
              hideDiagramElement(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public HideDiagramElementMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(HideDiagramElementInput input) {
        return this.graphQLRequestor.execute(HIDE_DIAGRAM_ELEMENT_MUTATION, input);
    }
}
