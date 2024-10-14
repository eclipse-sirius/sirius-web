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

package org.eclipse.sirius.components.portals.tests.graphql;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.portals.dto.AddPortalViewInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to add a representation to a portal.
 *
 * @author gcoutable
 */
@Service
public class AddPortalViewMutationRunner implements IMutationRunner<AddPortalViewInput> {

    private static final String ADD_PORTAL_VIEW = """
              mutation addPortalView($input: AddPortalViewInput!) {
                addPortalView(input: $input) {
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

    public AddPortalViewMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(AddPortalViewInput input) {
        return this.graphQLRequestor.execute(ADD_PORTAL_VIEW, input);
    }
}
