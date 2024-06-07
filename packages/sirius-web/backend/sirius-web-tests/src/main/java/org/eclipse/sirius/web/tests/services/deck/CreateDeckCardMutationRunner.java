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
package org.eclipse.sirius.web.tests.services.deck;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.deck.dto.input.CreateDeckCardInput;
import org.eclipse.sirius.components.graphql.tests.api.IGraphQLRequestor;
import org.eclipse.sirius.components.graphql.tests.api.IMutationRunner;
import org.springframework.stereotype.Service;

/**
 * Used to create a card in a deck representation.
 *
 * @author sbegaudeau
 */
@Service
public class CreateDeckCardMutationRunner implements IMutationRunner<CreateDeckCardInput> {

    private static final String CREATE_DECK_CARD_MUTATION = """
            mutation createDeckCard($input: CreateDeckCardInput!) {
              createDeckCard(input: $input) {
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

    public CreateDeckCardMutationRunner(IGraphQLRequestor graphQLRequestor) {
        this.graphQLRequestor = Objects.requireNonNull(graphQLRequestor);
    }

    @Override
    public String run(CreateDeckCardInput input) {
        return this.graphQLRequestor.execute(CREATE_DECK_CARD_MUTATION, input);
    }
}
