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
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.deck.dto.DeckRefreshedEventPayload;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DeckEventInput;
import org.eclipse.sirius.components.collaborative.dto.CreateRepresentationInput;
import org.eclipse.sirius.web.tests.services.api.IGivenCommittedTransaction;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedDeckSubscription;
import org.eclipse.sirius.web.tests.services.api.IGivenCreatedRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.test.context.transaction.TestTransaction;

import graphql.execution.DataFetcherResult;
import reactor.core.publisher.Flux;

/**
 * Used to create a deck and subscribe to it.
 *
 * @author sbegaudeau
 */
@Service
public class GivenCreatedDeckSubscription implements IGivenCreatedDeckSubscription {

    private final IGivenCommittedTransaction givenCommittedTransaction;

    private final IGivenCreatedRepresentation givenCreatedRepresentation;

    private final DeckEventSubscriptionRunner deckEventSubscriptionRunner;

    public GivenCreatedDeckSubscription(IGivenCommittedTransaction givenCommittedTransaction, IGivenCreatedRepresentation givenCreatedRepresentation, DeckEventSubscriptionRunner deckEventSubscriptionRunner) {
        this.givenCommittedTransaction = Objects.requireNonNull(givenCommittedTransaction);
        this.givenCreatedRepresentation = Objects.requireNonNull(givenCreatedRepresentation);
        this.deckEventSubscriptionRunner = Objects.requireNonNull(deckEventSubscriptionRunner);
    }

    @Override
    public Flux<DeckRefreshedEventPayload> createAndSubscribe(CreateRepresentationInput input) {
        this.givenCommittedTransaction.commit();

        String representationId = this.givenCreatedRepresentation.createRepresentation(input);

        var deckEventInput = new DeckEventInput(UUID.randomUUID(), input.editingContextId(), UUID.fromString(representationId));
        var flux = this.deckEventSubscriptionRunner.run(deckEventInput);

        TestTransaction.flagForCommit();
        TestTransaction.end();
        TestTransaction.start();

        return flux.filter(DataFetcherResult.class::isInstance)
                .map(DataFetcherResult.class::cast)
                .map(DataFetcherResult::getData)
                .filter(DeckRefreshedEventPayload.class::isInstance)
                .map(DeckRefreshedEventPayload.class::cast);
    }
}
