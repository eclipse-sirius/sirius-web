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
import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme } from '@material-ui/core/styles';
import { Card, DeckData, Lane } from '../Deck.types';
import { GQLCard, GQLDeck, GQLDeckElementStyle, GQLLane } from '../representation/deckSubscription.types';

export const convertToTrelloDeckData = (deck: GQLDeck, selectedElementIds: string[], theme: Theme): DeckData => {
  const backgroundColor: string | undefined = getCSSColor(deck.style?.backgroundColor, theme);
  const data: DeckData = {
    lanes: [],
    style: { backgroundColor },
  };

  for (const lane of deck.lanes) {
    const cards: Card[] = lane.cards.map((card) => convertGQLCard(card, selectedElementIds, theme));

    const { id, label, title, targetObjectId, collapsed, collapsible, style } = lane;
    const selectedLane: boolean = selectedElementIds.includes(targetObjectId);

    const cssStyleFromDescription: React.CSSProperties = style ? convertElementStyle(style, theme) : {};
    const { backgroundColor, ...otherStyleProps } = cssStyleFromDescription;
    const laneStyle: React.CSSProperties = {
      border: selectedLane ? `2px solid ${theme.palette.selected}` : undefined,
      width: '280px',
      backgroundColor,
    };

    const convertedLane: Lane = {
      id,
      label,
      title,
      editLaneTitle: selectedLane,
      editable: true,
      cards,
      style: laneStyle,
      titleStyle: otherStyleProps,
      collapsible,
      collapsed,
      'data-testid': `lane-${lane.title}`,
    };
    data.lanes.push(convertedLane);
  }
  return data;
};

const convertGQLCard = (card: GQLCard, selectedElementIds: string[], theme: Theme): Card => {
  let editable: boolean = false;
  const { targetObjectId, targetObjectLabel, targetObjectKind, style, ...otherCardProps } = card;
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
  const cssStyle: React.CSSProperties | undefined = style ? convertElementStyle(style, theme) : {};
  const { backgroundColor, ...otherStyle } = cssStyle;
  return {
    ...otherCardProps,
    editable,
    metadata,
    style: { backgroundColor },
    titleStyle: otherStyle,
  };
};

const convertElementStyle = (style: GQLDeckElementStyle, theme: Theme): React.CSSProperties => {
  return {
    backgroundColor: getCSSColor(style.backgroundColor, theme),
    color: getCSSColor(style.color, theme),
    fontSize: style.fontSize,
    fontStyle: style.italic ? 'italic' : 'normal',
    fontWeight: style.bold ? 'bold' : 'normal',
    textDecoration: getTextDecoration(style),
  };
};

const getTextDecoration = (style: GQLDeckElementStyle): string => {
  let decoration: string = '';
  if (style?.strikeThrough) {
    decoration = decoration + 'line-through';
  }
  if (style?.underline) {
    let separator: string = decoration.length > 0 ? ' ' : '';
    decoration = decoration + separator + 'underline';
  }
  return decoration.length > 0 ? decoration : 'none';
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
