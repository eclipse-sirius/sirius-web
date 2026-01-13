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
package org.eclipse.sirius.web.tests.services.deck;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.deck.dto.input.ChangeLaneCollapsedStateInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the collapsed state of a deck lane.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
@Service
public class ChangeLaneCollapsedStateMutationRunner implements IMutationRunner<ChangeLaneCollapsedStateInput> {

    private static final String CHANGE_LANE_COLLAPSED_STATE_MUTATION = """
            mutation changeLaneCollapsedState($input: ChangeLaneCollapsedStateInput!) {
              changeLaneCollapsedState(input: $input) {
                __typename
                ... on SuccessPayload {
                  id
                }
                ... on ErrorPayload {
                  messages {
                    body
                    level
                  }
                }
              }
            }
            """;

    private final IGraphQLRequestor graphQLRequestor;

    public ChangeLaneCollapsedStateMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(ChangeLaneCollapsedStateInput input) {
        return this.graphQLRequestor.execute(CHANGE_LANE_COLLAPSED_STATE_MUTATION, input);
    }
}
