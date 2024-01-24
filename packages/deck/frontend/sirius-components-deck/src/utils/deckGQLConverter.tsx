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
import { Card, DeckData } from '../Deck.types';
import { GQLDeck } from '../representation/deckSubscription.types';

export const convertToTrelloDeckData = (deck: GQLDeck, selectedCardIds: string[]): DeckData => {
  const data: DeckData = {
    lanes: [],
  };

  for (const lane of deck.lanes) {
    const cards: Card[] = lane.cards.map((card) => {
      let editable: boolean = false;
      let className: string | undefined;
      let style: object | undefined;
      const { targetObjectId, targetObjectLabel, targetObjectKind, ...otherCardProps } = card;
      const metadata = {
        selection: {
          id: targetObjectId,
          label: targetObjectLabel,
          kind: targetObjectKind,
        },
      };
      if (selectedCardIds.includes(card.id)) {
        editable = true;
      }
      return {
        ...otherCardProps,
        editable,
        metadata,
        className,
        style,
      };
    });
    data.lanes.push({
      ...lane,
      editable: true,
      cards,
      'data-testid': `lane-${lane.title}`,
    });
  }
  return data;
};
