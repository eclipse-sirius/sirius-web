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
import org.eclipse.sirius.web.application.document.dto.CreateDocumentInput;
import org.springframework.stereotype.Service;

/**
 * Used to create new documents with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class CreateDocumentMutationRunner implements IMutationRunner<CreateDocumentInput> {

    private static final String CREATE_DOCUMENT = """
            mutation createDocument($input: CreateDocumentInput!) {
              createDocument(input: $input) {
                __typename
                ... on CreateDocumentSuccessPayload {
                  document {
                    id
                    name
                    kind
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateDocumentMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateDocumentInput input) {
        return this.graphQLRequestor.execute(CREATE_DOCUMENT, input);
    }
}
