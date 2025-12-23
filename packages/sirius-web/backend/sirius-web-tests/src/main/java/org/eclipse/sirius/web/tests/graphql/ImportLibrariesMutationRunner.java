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

import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.application.library.dto.ImportLibrariesInput;
import org.springframework.stereotype.Service;

/**
 * Used to import libraries with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class ImportLibrariesMutationRunner implements IMutationRunner<ImportLibrariesInput> {

    private static final String IMPORT_LIBRARIES = """
            mutation importLibraries($input: ImportLibrariesInput!) {
              importLibraries(input: $input) {
                __typename
                ... on SuccessPayload {
                  messages {
                    level
                    body
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

    public ImportLibrariesMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(ImportLibrariesInput input) {
        return this.graphQLRequestor.execute(IMPORT_LIBRARIES, input);
    }

}
