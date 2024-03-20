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
import org.eclipse.sirius.web.application.project.dto.CreateProjectFromTemplateInput;
import org.springframework.stereotype.Service;

/**
 * Used to create a project from a template with the GraphQL API.
 *
 * @author gdaniel
 */
@Service
public class CreateProjectFromTemplateMutationRunner implements IMutationRunner<CreateProjectFromTemplateInput> {

    private static final String CREATE_PROJECT_FROM_TEMPLATE_MUTATION = """
            mutation createProjectFromTemplate($input: CreateProjectFromTemplateInput!) {
              createProjectFromTemplate(input: $input) {
                __typename
                ... on CreateProjectFromTemplateSuccessPayload {
                  project {
                    id
                  }
                  representationToOpen {
                    id
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public CreateProjectFromTemplateMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateProjectFromTemplateInput input) {
        return this.graphQLRequestor.execute(CREATE_PROJECT_FROM_TEMPLATE_MUTATION, input);
    }

}
