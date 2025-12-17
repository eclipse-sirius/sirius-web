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
import org.eclipse.sirius.web.application.library.api.IPublishLibraryInput;
import org.springframework.stereotype.Service;

/**
 * Used to create a project with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class PublishLibrariesMutationRunner implements IMutationRunner<IPublishLibraryInput> {

    private static final String PUBLISH_LIBRARIES = """
            mutation publishLibraries($input: PublishLibrariesInput!) {
              publishLibraries(input: $input) {
                __typename
                ... on SuccessPayload {
                    messages {
                      level
                      body
                    }
                }
               ... on ErrorPayload {
                    messages {
                      level
                      body
                    }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public PublishLibrariesMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(IPublishLibraryInput input) {
        return this.graphQLRequestor.execute(PUBLISH_LIBRARIES, input);
    }

}
