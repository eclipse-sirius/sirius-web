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
package org.eclipse.sirius.components.deck.renderer;

import java.util.List;

import org.eclipse.sirius.components.deck.Card;
import org.eclipse.sirius.components.deck.Deck;
import org.eclipse.sirius.components.deck.Lane;
import org.eclipse.sirius.components.deck.renderer.elements.CardElementProps;
import org.eclipse.sirius.components.deck.renderer.elements.DeckElementProps;
import org.eclipse.sirius.components.deck.renderer.elements.LaneElementProps;
import org.eclipse.sirius.components.representations.IElementFactory;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Used to instantiate the elements of the Deck representation.
 *
 * @author fbarbin
 */
public class DeckElementFactory implements IElementFactory {

    @Override
    public Object instantiateElement(String type, IProps props, List<Object> children) {
        Object object = null;
        if (DeckElementProps.TYPE.equals(type) && props instanceof DeckElementProps deckElementProps) {
            object = this.instantiateDeck(deckElementProps, children);
        }

        else if (LaneElementProps.TYPE.equals(type) && props instanceof LaneElementProps laneElementProps) {
            object = this.instantiateLane(laneElementProps, children);
        }

        else if (CardElementProps.TYPE.equals(type) && props instanceof CardElementProps cardElementProps) {
            object = this.instantiateCard(cardElementProps, children);
        }
        return object;
    }

    private Deck instantiateDeck(DeckElementProps props, List<Object> children) {
        List<Lane> lanes = children.stream()//
                .filter(Lane.class::isInstance)//
                .map(Lane.class::cast)//
                .toList();
        return new Deck(props.id(), props.descriptionId(), props.targetObjectId(), props.label(), lanes);
    }

    private Lane instantiateLane(LaneElementProps props, List<Object> children) {
        List<Card> cards = children.stream()//
                .filter(Card.class::isInstance)//
                .map(Card.class::cast)//
                .toList();
        return new Lane(props.id(), props.descriptionId(), props.targetObjectId(), props.targetObjectKind(), props.targetObjectLabel(), props.title(), props.label(), cards);
    }

    private Card instantiateCard(CardElementProps props, List<Object> children) {

        return new Card(props.id(), props.descriptionId(), props.targetObjectId(), props.targetObjectKind(), props.targetObjectLabel(), props.title(), props.label(), props.description());
    }

}
