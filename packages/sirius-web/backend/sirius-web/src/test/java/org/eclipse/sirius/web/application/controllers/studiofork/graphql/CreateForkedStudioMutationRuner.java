/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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

package org.eclipse.sirius.web.application.controllers.studiofork.graphql;

import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.eclipse.sirius.web.view.fork.dto.CreateForkedStudioInput;

import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Used to send a createForkedStudio GraphQL mutation.
 *
 * @author mcharfadi
 */
@Service
public class CreateForkedStudioMutationRuner implements IMutationRunner<CreateForkedStudioInput> {

    private static final String CREATE_FORK_MUTATION = """
            mutation createForkedStudio($input: CreateForkedStudioInput!) {
                 createForkedStudio(input: $input) {
                   __typename
                   ... on CreateProjectSuccessPayload {
                     project {
                       id
                     }
                   }
                   ... on ErrorPayload {
                     message
                   }
                 }
               }
          """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateForkedStudioMutationRuner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }
    @Override
    public String run(CreateForkedStudioInput input) {
        return this.graphQLRequestor.execute(CREATE_FORK_MUTATION, input);
    }
}
