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
package org.eclipse.sirius.web.tests.services.formdescriptioneditors;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.AddPageInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Used to add a page to a form description editor.
 *
 * @author mcharfadi
 */
@Service
public class AddPageMutationRunner implements IMutationRunner<AddPageInput> {

    private static final String ADD_PAGE_MUTATION = """
            mutation addPage($input: AddPageInput!) {
                addPage(input: $input) {
                  __typename
                  ... on SuccessPayload {
                    id
                  }
                  ... on ErrorPayload {
                    message
                  }
                }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public AddPageMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(AddPageInput input) {
        return this.graphQLRequestor.execute(ADD_PAGE_MUTATION, input);
    }
}