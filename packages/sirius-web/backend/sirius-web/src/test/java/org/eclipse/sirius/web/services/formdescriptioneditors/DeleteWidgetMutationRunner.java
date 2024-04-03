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
package org.eclipse.sirius.web.services.formdescriptioneditors;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.formdescriptioneditors.dto.DeleteWidgetInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to delete a widget to a form description editor.
 *
 * @author pcdavid
 */
@Service
public class DeleteWidgetMutationRunner implements IMutationRunner<DeleteWidgetInput> {

    private static final String DELETE_WIDGET_MUTATION = """
            mutation deleteWidget($input: DeleteWidgetInput!) {
              deleteWidget(input: $input) {
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

    public DeleteWidgetMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(DeleteWidgetInput input) {
        return this.graphQLRequestor.execute(DELETE_WIDGET_MUTATION, input);
    }
}
