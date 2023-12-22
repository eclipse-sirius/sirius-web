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
package org.eclipse.sirius.components.collaborative.deck.handlers;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckCardService;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventHandler;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.DeleteDeckCardInput;
import org.eclipse.sirius.components.collaborative.deck.message.ICollaborativeDeckMessageService;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.deck.Deck;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handle "Delete Card" events.
 *
 * @author fbarbin
 */
@Service
public class DeleteCardEventHandler implements IDeckEventHandler {

    private final IDeckCardService deckCardService;

    private final ICollaborativeDeckMessageService messageService;

    private final Counter counter;

    public DeleteCardEventHandler(IDeckCardService deckCardService, ICollaborativeDeckMessageService messageService, MeterRegistry meterRegistry) {
        this.messageService = Objects.requireNonNull(messageService);
        this.deckCardService = Objects.requireNonNull(deckCardService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDeckInput deckInput) {
        return deckInput instanceof DeleteDeckCardInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, Deck deck, IDeckInput deckInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(deckInput.getClass().getSimpleName(), DeleteDeckCardInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(deckInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, deckInput.representationId(), deckInput);

        if (deckInput instanceof DeleteDeckCardInput input) {
            payload = this.deckCardService.deleteCard(input, editingContext, deck);

            changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, deckInput.representationId(), deckInput);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
