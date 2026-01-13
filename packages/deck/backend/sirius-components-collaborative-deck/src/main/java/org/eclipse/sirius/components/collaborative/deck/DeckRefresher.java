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
package org.eclipse.sirius.components.collaborative.deck;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IRepresentationEventProcessor;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceStrategy;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckCreationService;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventProcessor;
import org.eclipse.sirius.components.collaborative.representations.api.IRepresentationRefresher;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Used to refresh deck representations.
 *
 * @author sbegaudeau
 */
@Service
public class DeckRefresher implements IRepresentationRefresher {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IDeckCreationService deckCreationService;

    private final IRepresentationPersistenceStrategy representationPersistenceStrategy;

    public DeckRefresher(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService, IDeckCreationService deckCreationService, IRepresentationPersistenceStrategy representationPersistenceStrategy) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.deckCreationService = Objects.requireNonNull(deckCreationService);
        this.representationPersistenceStrategy = Objects.requireNonNull(representationPersistenceStrategy);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        var representation = representationEventProcessor.getRepresentation();
        return representation instanceof Deck
                && (this.isRegularRefresh(changeDescription) || this.isReloadRefresh(changeDescription, representation));
    }

    private boolean isRegularRefresh(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind()) || DeckChangeKind.DECK_REPRESENTATION_UPDATE.equals(changeDescription.getKind());
    }

    private boolean isReloadRefresh(ChangeDescription changeDescription, IRepresentation representation) {
        return changeDescription.getKind().equals(ChangeKind.RELOAD_REPRESENTATION) && changeDescription.getSourceId().equals(representation.getId());
    }

    @Override
    public void refresh(IEditingContext editingContext, IRepresentationEventProcessor representationEventProcessor, ChangeDescription changeDescription) {
        if (representationEventProcessor instanceof IDeckEventProcessor deckEventProcessor && deckEventProcessor.getRepresentation() instanceof Deck existingDeck) {
            if (this.isRegularRefresh(changeDescription)) {
                var optionalDeckDescription = this.representationDescriptionSearchService.findById(editingContext, existingDeck.getDescriptionId())
                        .filter(DeckDescription.class::isInstance)
                        .map(DeckDescription.class::cast);

                var optionalObject = this.objectSearchService.getObject(editingContext, existingDeck.targetObjectId());

                if (optionalDeckDescription.isPresent() && optionalObject.isPresent()) {
                    DeckDescription deckDescription = optionalDeckDescription.get();
                    Object object = optionalObject.get();

                    var refreshedDeck = this.deckCreationService.create(editingContext, deckDescription, object, new DeckContext(existingDeck, deckEventProcessor.getDeckContext().deckEvents()));
                    this.representationPersistenceStrategy.applyPersistenceStrategy(changeDescription.getCause(), editingContext, refreshedDeck);
                    deckEventProcessor.update(changeDescription.getCause(), refreshedDeck);
                }
            } else if (this.isReloadRefresh(changeDescription, existingDeck)) {
                this.representationSearchService.findById(editingContext, existingDeck.getId(), Deck.class)
                        .ifPresent(deck -> deckEventProcessor.update(changeDescription.getCause(), deck));
            }
        }
    }
}
