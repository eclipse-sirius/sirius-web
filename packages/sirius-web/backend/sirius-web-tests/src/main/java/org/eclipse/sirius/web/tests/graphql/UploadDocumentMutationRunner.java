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
import org.eclipse.sirius.web.application.document.dto.UploadDocumentInput;
import org.springframework.stereotype.Service;

/**
 * Used to upload new documents with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class UploadDocumentMutationRunner implements IMutationRunner<UploadDocumentInput> {

    private static final String UPLOAD_DOCUMENT = """
            mutation uploadDocument($input: UploadDocumentInput!) {
              uploadDocument(input: $input) {
                __typename
                ... on UploadDocumentSuccessPayload {
                    report
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public UploadDocumentMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(UploadDocumentInput input) {
        Map<String, Object> inputMap = Map.of(
                "id", input.id(),
                "editingContextId", input.editingContextId(),
                "file", input.file()
        );
        Map<String, Object> variables = Map.of("input", inputMap);
        return this.graphQLRequestor.execute(UPLOAD_DOCUMENT, variables);
    }
}
