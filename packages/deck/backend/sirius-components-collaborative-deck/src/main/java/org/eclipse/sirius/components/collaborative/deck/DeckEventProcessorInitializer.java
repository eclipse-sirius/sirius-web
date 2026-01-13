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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckCreationService;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckEventProcessorInitializer;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.springframework.stereotype.Service;

/**
 * Used to perform the initial refresh of the deck representation for its event processor.
 *
 * @author sbegaudeau
 */
@Service
public class DeckEventProcessorInitializer implements IDeckEventProcessorInitializer {

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IRepresentationSearchService representationSearchService;

    private final IDeckCreationService deckCreationService;

    public DeckEventProcessorInitializer(IObjectSearchService objectSearchService, IRepresentationDescriptionSearchService representationDescriptionSearchService, IRepresentationSearchService representationSearchService, IDeckCreationService deckCreationService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
        this.deckCreationService = Objects.requireNonNull(deckCreationService);
    }

    @Override
    public Optional<Deck> getRefreshedRepresentation(IEditingContext editingContext, String representationId) {
        var optionalDeck = this.representationSearchService.findById(editingContext, representationId, Deck.class);
        if (optionalDeck.isPresent()) {
            Deck previousDeck = optionalDeck.get();

            var optionalDeckDescription = this.representationDescriptionSearchService.findById(editingContext, previousDeck.getDescriptionId())
                    .filter(DeckDescription.class::isInstance)
                    .map(DeckDescription.class::cast);

            var optionalObject = this.objectSearchService.getObject(editingContext, previousDeck.targetObjectId());

            if (optionalDeckDescription.isPresent() && optionalObject.isPresent()) {
                DeckDescription deckDescription = optionalDeckDescription.get();
                Object object = optionalObject.get();

                return Optional.of(this.deckCreationService.create(editingContext, deckDescription, object, new DeckContext(previousDeck, new ArrayList<>())));
            }
        }

        return Optional.empty();
    }
}
