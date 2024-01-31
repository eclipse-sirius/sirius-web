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
package org.eclipse.sirius.components.deck.renderer.component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.Lane;
import org.eclipse.sirius.components.deck.description.DeckDescription;
import org.eclipse.sirius.components.deck.renderer.elements.DeckElementProps;
import org.eclipse.sirius.components.deck.renderer.events.IDeckEvent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to render the Deck representation.
 *
 * @author fbarbin
 */
public class DeckComponent implements IComponent {

    private final DeckComponentProps props;

    public DeckComponent(DeckComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.variableManager();
        DeckDescription deckDescription = this.props.deckDescription();
        Optional<Deck> optionalPreviousDeck = this.props.optionalPreviousDeck();
        Optional<IDeckEvent> optionalDeckEvent = this.props.optionalDeckEvent();
        List<Lane> previousLanes = optionalPreviousDeck.map(Deck::lanes).orElse(List.of());
        String deckId = optionalPreviousDeck.map(Deck::getId).orElseGet(() -> UUID.randomUUID().toString());
        String targetObjectId = deckDescription.targetObjectIdProvider().apply(variableManager);
        String label = optionalPreviousDeck.map(Deck::getLabel).orElseGet(() -> deckDescription.labelProvider().apply(variableManager));

        List<Element> children = deckDescription.laneDescriptions()//
                .stream()//
                .map(laneDescription -> {
                    LaneComponentProps laneComponentProps = new LaneComponentProps(variableManager, laneDescription, deckId, previousLanes, optionalDeckEvent);
                    return new Element(LaneComponent.class, laneComponentProps);
                })//
                .toList();

        DeckElementProps deckElementProps = new DeckElementProps(deckId, deckDescription.getId(), targetObjectId, label, children);
        return new Element(DeckElementProps.TYPE, deckElementProps);
    }

}
