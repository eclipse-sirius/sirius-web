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
import org.eclipse.sirius.web.application.images.dto.DeleteImageInput;
import org.springframework.stereotype.Service;

/**
 * Used to delete an image with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class DeleteImageMutationRunner implements IMutationRunner<DeleteImageInput> {

    private static final String DELETE_IMAGE_MUTATION = """
            mutation deleteImage($input: DeleteImageInput!) {
              deleteImage(input: $input) {
                __typename
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public DeleteImageMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteImageInput input) {
        return this.graphQLRequestor.execute(DELETE_IMAGE_MUTATION, input);
    }
}
