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

import org.eclipse.sirius.components.collaborative.diagrams.dto.PinDiagramElementInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to pin or unpin elements with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class PinDiagramElementMutationRunner implements IMutationRunner<PinDiagramElementInput> {

    private static final String PIN_DIAGRAM_ELEMENT_MUTATION = """
            mutation pinDiagramElement($input: PinDiagramElementInput!) {
              pinDiagramElement(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public PinDiagramElementMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = graphQLRequestor;
    }

    @Override
    public String run(PinDiagramElementInput input) {
        return this.graphQLRequestor.execute(PIN_DIAGRAM_ELEMENT_MUTATION, input);
    }
}
