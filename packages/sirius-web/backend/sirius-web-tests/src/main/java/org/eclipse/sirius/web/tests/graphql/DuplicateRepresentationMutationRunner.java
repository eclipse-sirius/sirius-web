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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.views.explorer.dto.DuplicateRepresentationInput;
import org.springframework.stereotype.Service;

/**
 * Used to duplicate a representation with the GraphQL API.
 *
 * @author frouene
 */
@Service
public class DuplicateRepresentationMutationRunner implements IMutationRunner<DuplicateRepresentationInput> {

    private static final String DUPLICATE_REPRESENTATION = """
            mutation duplicateRepresentation($input: DuplicateRepresentationInput!) {
              duplicateRepresentation(input: $input) {
                __typename
                ... on DuplicateRepresentationSuccessPayload {
                  representationMetadata {
                    id
                    label
                    kind
                  }
                }
               ... on ErrorPayload {
                    message
                    messages {
                      level
                      body
                    }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DuplicateRepresentationMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DuplicateRepresentationInput input) {
        return this.graphQLRequestor.execute(DUPLICATE_REPRESENTATION, input);
    }
}
