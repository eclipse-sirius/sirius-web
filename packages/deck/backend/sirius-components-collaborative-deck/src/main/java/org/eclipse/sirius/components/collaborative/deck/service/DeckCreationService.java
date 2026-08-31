/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.collaborative.deck.DeckContext;
import org.eclipse.sirius.components.collaborative.deck.api.IDeckCreationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.renderer.DeckRenderer;
import org.eclipse.sirius.components.deck.renderer.component.DeckComponent;
import org.eclipse.sirius.components.deck.renderer.component.DeckComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.RepresentationVariables;
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

    private final Timer timer;

    public DeckCreationService(MeterRegistry meterRegistry) {
        this.timer = Timer.builder(Monitoring.REPRESENTATION_EVENT_PROCESSOR_REFRESH)
                .tag(Monitoring.NAME, "deck")
                .register(meterRegistry);
    }

    @Override
    public Deck create(IEditingContext editingContext, DeckDescription deckDescription, Object targetObject, DeckContext deckContext) {
        long start = System.currentTimeMillis();

        VariableManager variableManager = new VariableManager();
        variableManager.put(RepresentationVariables.SELF.name(), targetObject);
        variableManager.put(CoreVariables.EDITING_CONTEXT.name(), editingContext);
        variableManager.put(DeckDescription.DECK_TARGET, targetObject);
        Optional<Deck> optionalPreviousDeck = Optional.ofNullable(deckContext).map(DeckContext::deck);
        var deckEvents = Optional.ofNullable(deckContext).map(DeckContext::deckEvents).orElse(List.of());

        DeckComponentProps deckComponentProps = new DeckComponentProps(variableManager, deckDescription, deckEvents, optionalPreviousDeck);

        Element element = new Element(DeckComponent.class, deckComponentProps);
        Deck newDeck = new DeckRenderer().render(element);

        long end = System.currentTimeMillis();
        this.timer.record(end - start, TimeUnit.MILLISECONDS);
        return newDeck;
    }

}
