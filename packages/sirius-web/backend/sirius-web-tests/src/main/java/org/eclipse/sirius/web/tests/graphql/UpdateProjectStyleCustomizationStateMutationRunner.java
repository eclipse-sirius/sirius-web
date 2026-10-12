/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.UpdateProjectStyleCustomizationStateInput;
import org.springframework.stereotype.Service;

/**
 * Used to update the project style customization state with GraphQL API.
 *
 * @author gcoutable
 */
@Service
public class UpdateProjectStyleCustomizationStateMutationRunner implements IMutationRunner<UpdateProjectStyleCustomizationStateInput> {

    public static final String UPDATE_PROJECT_STYLE_CUSTOMIZATION_STATE = """
            mutation updateProjectStyleCustomizationState($input: UpdateProjectStyleCustomizationStateInput!) {
                updateProjectStyleCustomizationState(input: $input) {
                  __typename
                  ... on ErrorPayload {
                    messages {
                      body
                      level
                    }
                  }
                  ... on SuccessPayload {
                    messages {
                      body
                      level
                    }
                  }
                }
              }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public UpdateProjectStyleCustomizationStateMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(UpdateProjectStyleCustomizationStateInput input) {
        return this.graphQLRequestor.execute(UPDATE_PROJECT_STYLE_CUSTOMIZATION_STATE, input);
    }
}
