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

import org.eclipse.sirius.components.collaborative.deck.dto.input.ChangeCardsVisibilityInput;
import org.eclipse.sirius.components.graphql.tests.api.GraphQLResult;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to change the visibility of some cards.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
@Service
public class ChangeCardsVisibilityMutationRunner implements IMutationRunner<ChangeCardsVisibilityInput> {

    private static final String CHANGE_DECK_CARDS_VISIBILITY_MUTATION = """
            mutation changeCardsVisibility($input: ChangeCardsVisibilityInput!) {
              changeCardsVisibility(input: $input) {
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

    public ChangeCardsVisibilityMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public GraphQLResult run(ChangeCardsVisibilityInput input) {
        return this.graphQLRequestor.execute(CHANGE_DECK_CARDS_VISIBILITY_MUTATION, input);
    }
}
