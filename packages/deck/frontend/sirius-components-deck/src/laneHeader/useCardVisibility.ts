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

import { useState } from 'react';
import { Card } from '../Deck.types';
import { UseCardVisibilityValue } from './useCardVisibility.types';

export const useCardVisibility = (cards: Card[]): UseCardVisibilityValue => {
  const initialState: string[] = cards.filter((card) => card.visible).map((card) => card.id);
  const [selectedCardsIds, setSelectedCardsIds] = useState<string[]>(initialState);

  const handleVisibilityChanged = (id: string) => {
    const cardIndex = selectedCardsIds.findIndex((cardId) => cardId === id);
    if (cardIndex > -1) {
      const newSelectedCards = selectedCardsIds.slice();
      newSelectedCards.splice(cardIndex, 1);
      setSelectedCardsIds(() => newSelectedCards);
    } else {
      const newSelectedCards = selectedCardsIds.slice();
      newSelectedCards.push(id);
      setSelectedCardsIds(() => newSelectedCards);
    }
  };

  return { selectedCardsIds, handleVisibilityChanged };
};
