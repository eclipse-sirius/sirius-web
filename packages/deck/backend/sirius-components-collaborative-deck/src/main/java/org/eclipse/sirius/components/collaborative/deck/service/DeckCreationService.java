/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.deck.service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.renderer.DeckRenderer;
import org.eclipse.sirius.components.deck.renderer.component.DeckComponent;
import org.eclipse.sirius.components.deck.renderer.component.DeckComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * Service used to create deck representations.
 *
 * @author fbarbin
 */
@Service
public class DeckCreationService implements IDeckCreationService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    private final Timer timer;

    public DeckCreationService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectService objectService, MeterRegistry meterRegistry) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH).tag(Monitoring.NAME, "deck").register(meterRegistry);
    }

    @Override
    public Deck create(String label, Object targetObject, DeckDescription deckDescription, IEditingContext editingContext) {
        return this.doRender(label, targetObject, editingContext, deckDescription, Optional.empty());
    }

    @Override
    public Optional<Deck> refresh(IEditingContext editingContext, Deck previousDeck) {
        var optionalObject = this.objectService.getObject(editingContext, previousDeck.targetObjectId());
        var optionalDeckDescription = this.representationDescriptionSearchService.findById(editingContext, previousDeck.getDescriptionId()).filter(DeckDescription.class::isInstance)
                .map(DeckDescription.class::cast);

        if (optionalObject.isPresent() && optionalDeckDescription.isPresent()) {
            Object object = optionalObject.get();
            DeckDescription deckDescription = optionalDeckDescription.get();
            Deck deck = this.doRender(previousDeck.getLabel(), object, editingContext, deckDescription, Optional.of(previousDeck));
            return Optional.of(deck);
        }
        return Optional.empty();
    }

    private Deck doRender(String label, Object targetObject, IEditingContext editingContext, DeckDescription deckDescription, Optional<Deck> optionalPreviousDeck) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(DeckDescription.LABEL, label);
        variableManager.put(VariableManager.SELF, targetObject);
        variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);

        DeckComponentProps deckComponentProps = new DeckComponentProps(variableManager, deckDescription, optionalPreviousDeck);

        Element element = new Element(DeckComponent.class, deckComponentProps);
        Deck newDeck = new DeckRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newDeck;
    }

}
