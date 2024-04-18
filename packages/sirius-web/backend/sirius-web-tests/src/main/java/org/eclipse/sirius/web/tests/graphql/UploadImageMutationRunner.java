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

import java.util.Map;
import java.util.Objects;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.images.dto.UploadImageInput;
import org.springframework.stereotype.Service;

/**
 * Used to upload new images with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class UploadImageMutationRunner implements IMutationRunner<UploadImageInput> {

    private static final String UPLOAD_IMAGE = """
            mutation uploadImage($input: UploadImageInput!) {
              uploadImage(input: $input) {
                __typename
                ... on UploadImageSuccessPayload {
                  imageId
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public UploadImageMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(UploadImageInput input) {
        Map<String, Object> inputMap = Map.of(
                "id", input.id(),
                "projectId", input.projectId(),
                "label", input.label(),
                "file", input.file()
        );
        Map<String, Object> variables = Map.of("input", inputMap);
        return this.graphQLRequestor.execute(UPLOAD_IMAGE, variables);
    }
}
