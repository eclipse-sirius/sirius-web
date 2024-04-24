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

import org.eclipse.sirius.components.collaborative.diagrams.dto.FadeDiagramElementInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to fade or reveal faded elements with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class FadeDiagramElementMutationRunner implements IMutationRunner<FadeDiagramElementInput> {

    private static final String FADE_DIAGRAM_ELEMENT_MUTATION = """
            mutation fadeDiagramElement($input: FadeDiagramElementInput!) {
              fadeDiagramElement(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public FadeDiagramElementMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(FadeDiagramElementInput input) {
        return this.graphQLRequestor.execute(FADE_DIAGRAM_ELEMENT_MUTATION, input);
    }
}
