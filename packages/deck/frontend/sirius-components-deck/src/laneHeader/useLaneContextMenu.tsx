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
import { useContext, useState } from 'react';
import { Card } from '../Deck.types';
import { DeckContext } from '../representation/DeckContext';
import { DeckContextValue } from '../representation/DeckContext.types';
import { useDeckMutations } from '../representation/useDeckMutations';
import { LaneContextMenu } from './LaneContextMenu';
import { DeckLaneHeaderStateValue, UseLaneContextMenuValue } from './useLaneContextMenu.types';

export const useLaneContextMenu = (cards: Card[], id: string): UseLaneContextMenuValue => {
  const { editingContextId, deckId } = useContext<DeckContextValue>(DeckContext);

  const { changeCardsVisibility } = useDeckMutations(editingContextId, deckId);
  const initialState: DeckLaneHeaderStateValue = {
    showContextMenu: false,
    menuAnchor: null,
  };
  const [state, setState] = useState<DeckLaneHeaderStateValue>(initialState);
  const { showContextMenu, menuAnchor } = state;

  // Context menu handling
  const openContextMenu = (event) => {
    if (!showContextMenu) {
      const { currentTarget } = event;
      if (currentTarget instanceof Element) {
        setState(() => {
          return {
            showContextMenu: true,
            menuAnchor: currentTarget,
          };
        });
      }
    }
  };

  let contextMenu: JSX.Element | null = null;
  if (showContextMenu && menuAnchor) {
    const applyChanges = (visibleCardsIds) => {
      const hiddenCards = cards.map((card) => card.id).filter((cardId) => !visibleCardsIds.includes(cardId));
      changeCardsVisibility(hiddenCards, visibleCardsIds);
      closeContextMenu();
    };

    const closeContextMenu = () => {
      setState(() => {
        return {
          modalDisplayed: null,
          showContextMenu: false,
          menuAnchor: null,
        };
      });
    };
    contextMenu = (
      <LaneContextMenu
        menuAnchor={menuAnchor}
        laneId={id}
        cards={cards}
        onClose={closeContextMenu}
        onChangesApplied={applyChanges}
      />
    );
  }

  return { openContextMenu, contextMenu };
};
