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
package org.eclipse.sirius.web.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.images.dto.RenameImageInput;
import org.springframework.stereotype.Service;

/**
 * Used to rename an image with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class RenameImageMutationRunner implements IMutationRunner<RenameImageInput> {

    private static final String RENAME_IMAGE_MUTATION = """
            mutation renameImage($input: RenameImageInput!) {
              renameImage(input: $input) {
                __typename
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

    public RenameImageMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(RenameImageInput input) {
        return this.graphQLRequestor.execute(RENAME_IMAGE_MUTATION, input);
    }
}
