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
package org.eclipse.sirius.web.services.portals;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.portals.dto.LayoutPortalInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to move/resize the views inside a portal.
 *
 * @author pcdavid
 */
@Service
public class LayoutPortalMutationRunner implements IMutationRunner<LayoutPortalInput> {

    private static final String LAYOUT_PORTAL_MUTATION = """
              mutation layoutPortal($input: LayoutPortalInput!) {
                layoutPortal(input: $input) {
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

    public LayoutPortalMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(LayoutPortalInput input) {
        return this.graphQLRequestor.execute(LAYOUT_PORTAL_MUTATION, input);
    }

}
