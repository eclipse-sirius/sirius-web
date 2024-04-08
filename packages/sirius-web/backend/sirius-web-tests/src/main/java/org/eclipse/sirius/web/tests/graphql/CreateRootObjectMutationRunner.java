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

import org.eclipse.sirius.components.collaborative.dto.CreateRootObjectInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to create a root object with the GraphQL API.
 *
 * @author sbegaudeau
 */
@Service
public class CreateRootObjectMutationRunner implements IMutationRunner<CreateRootObjectInput> {

    private static final String CREATE_ROOT_OBJECT = """
            mutation createRootObject($input: CreateRootObjectInput!) {
              createRootObject(input: $input) {
                __typename
                ... on CreateRootObjectSuccessPayload {
                  object {
                    id
                    label
                    kind
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateRootObjectMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateRootObjectInput input) {
        return this.graphQLRequestor.execute(CREATE_ROOT_OBJECT, input);
    }
}
