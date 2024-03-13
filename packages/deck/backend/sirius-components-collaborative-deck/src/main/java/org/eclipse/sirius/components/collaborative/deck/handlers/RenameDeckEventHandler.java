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
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckContext;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventHandler;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckInput;
import org.eclipse.sirius.components.collaborative.deck.dto.input.RenameDeckInput;
import org.eclipse.sirius.components.collaborative.deck.message.ICollaborativeDeckMessageService;
import org.eclipse.sirius.components.collaborative.dto.RenameRepresentationSuccessPayload;
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
 * Handler used to rename a deck representation.
 *
 * @author fbarbin
 */
@Service
public class RenameDeckEventHandler implements IDeckEventHandler {

    private final IRepresentationSearchService representationSearchService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final ICollaborativeDeckMessageService messageService;

    private final Counter counter;

    public RenameDeckEventHandler(IRepresentationSearchService representationSearchService, IRepresentationPersistenceService representationPersistenceService,
            ICollaborativeDeckMessageService messageService, MeterRegistry meterRegistry) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.messageService = Objects.requireNonNull(messageService);

        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IDeckInput deckInput) {
        return deckInput instanceof RenameDeckInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IDeckContext deckContext, IDeckInput deckInput) {
        this.counter.increment();

        String message = this.messageService.invalidInput(deckInput.getClass().getSimpleName(), RenameDeckInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(deckInput.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, deckInput.representationId(), deckInput);

        if (deckInput instanceof RenameDeckInput renameDeckInput) {
            String representationId = renameDeckInput.representationId();
            String newLabel = renameDeckInput.newLabel();
            Optional<Deck> optionalDiagram = this.representationSearchService.findById(editingContext, representationId, Deck.class);
            if (optionalDiagram.isPresent()) {
                Deck currentDeck = optionalDiagram.get();
                Deck renamedDeck = new Deck(representationId, currentDeck.descriptionId(), currentDeck.targetObjectId(), newLabel, currentDeck.style(), currentDeck.lanes());
                this.representationPersistenceService.save(renameDeckInput, editingContext, renamedDeck);

                payload = new RenameRepresentationSuccessPayload(deckInput.id(), renamedDeck);
                changeDescription = new ChangeDescription(ChangeKind.REPRESENTATION_RENAMING, renameDeckInput.representationId(), deckInput);
            }
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }
}
