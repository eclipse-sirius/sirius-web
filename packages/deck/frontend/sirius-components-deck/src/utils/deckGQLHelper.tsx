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
import { Theme } from '@material-ui/core/styles';
import { Card, DeckData, Lane } from '../Deck.types';
import { GQLCard, GQLDeck, GQLLane } from '../representation/deckSubscription.types';

export const convertToTrelloDeckData = (deck: GQLDeck, selectedElementIds: string[], theme: Theme): DeckData => {
  const data: DeckData = {
    lanes: [],
  };

  for (const lane of deck.lanes) {
    const cards: Card[] = lane.cards.map((card) => {
      let editable: boolean = false;
      const { targetObjectId, targetObjectLabel, targetObjectKind, ...otherCardProps } = card;
      const metadata = {
        selection: {
          id: targetObjectId,
          label: targetObjectLabel,
          kind: targetObjectKind,
        },
      };
      if (selectedElementIds.includes(card.targetObjectId)) {
        editable = true;
      }
      return {
        ...otherCardProps,
        editable,
        metadata,
      };
    });

    const { id, label, title, targetObjectId } = lane;
    const selectedLane: boolean = selectedElementIds.includes(targetObjectId);

    const style: React.CSSProperties = {
      border: selectedLane ? `2px solid ${theme.palette.selected}` : undefined,
    };

    const convertedLane: Lane = {
      id,
      label,
      title,
      editLaneTitle: selectedLane,
      editable: true,
      cards,
      style,
      'data-testid': `lane-${lane.title}`,
    };
    data.lanes.push(convertedLane);
  }
  return data;
};

export const moveCardInDeckLanes = (
  deck: GQLDeck,
  oldLaneId: string,
  newLaneId: string,
  cardId: string,
  addedIndex: number
): GQLDeck => {
  const deckToReturn = {
    ...deck,
  };
  const newLane: GQLLane | undefined = deckToReturn.lanes.find((lane) => lane.id === newLaneId);
  let oldLane: GQLLane | undefined;
  if (oldLaneId !== newLaneId) {
    oldLane = deckToReturn.lanes.find((lane) => lane.id === oldLaneId);
  } else {
    oldLane = newLane;
  }

  const cardToMove: GQLCard | undefined = oldLane?.cards.find((card) => card.id === cardId);
  if (oldLane && cardToMove) {
    const index = oldLane.cards.indexOf(cardToMove);
    if (index != undefined && index > -1) {
      //We remove the card from the previous location (could be a different lane or the same)
      oldLane.cards.splice(index, 1);
    }
  }
  if (newLane && cardToMove) {
    // We add it at the new location
    newLane.cards.splice(addedIndex, 0, cardToMove);
  }
  return deckToReturn;
};

export const findLaneById = (deck: GQLDeck, laneId: string): GQLLane | undefined => {
  return deck.lanes.find((lane) => lane.id === laneId);
};

export const updateCard = (deck: GQLDeck, newCard: Card): GQLDeck => {
  const newDeck: GQLDeck = {
    ...deck,
  };

  newDeck.lanes.forEach((lane) => {
    const index: number = lane.cards.findIndex((card) => card.id === newCard.id);
    if (index > -1) {
      const removedCards: GQLCard[] = lane.cards.splice(index, 1);
      if (removedCards.length === 1) {
        const removedCard = removedCards[0] as GQLCard;
        const cardToUpdate: GQLCard = {
          ...removedCard,
          description: newCard.description,
          label: newCard.label,
          title: newCard.title,
        };
        lane.cards.splice(index, 0, cardToUpdate);
      }
    }
  });
  return newDeck;
};

export const updateLane = (deck: GQLDeck, laneId: string, newTitle: string): GQLDeck => {
  const newDeck: GQLDeck = {
    ...deck,
  };

  const index: number = newDeck.lanes.findIndex((lane) => lane.id === laneId);
  if (index > -1) {
    const removedLanes: GQLLane[] = newDeck.lanes.splice(index, 1);
    if (removedLanes.length === 1) {
      const removedLane = removedLanes[0] as GQLLane;
      const laneToUpdate: GQLLane = {
        ...removedLane,
        title: newTitle,
      };
      newDeck.lanes.splice(index, 0, laneToUpdate);
    }
  }
  return newDeck;
};

export const moveLaneInDeck = (deck: GQLDeck, oldIndex: number, newIndex: number): GQLDeck => {
  const deckToReturn = {
    ...deck,
  };
  const laneToMove: GQLLane | undefined = deckToReturn.lanes[oldIndex];
  if (laneToMove) {
    deckToReturn.lanes.splice(oldIndex, 1);
    deckToReturn.lanes.splice(newIndex, 0, laneToMove);
  }
  return deckToReturn;
};
