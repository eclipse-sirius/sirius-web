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
import Board from '@ObeoNetwork/react-trello';
import { Theme, useTheme } from '@material-ui/core/styles';
import { DeckProps } from './Deck.types';
import { DeckCard } from './card/DeckCard';
import { Toolbar } from './toolbar/Toolbar';

export const Deck = ({
  editingContextId,
  representationId,
  data,
  onCardClick,
  onCardDelete,
  onCardAdd,
  onCardUpdate,
  onCardMoveAcrossLanes,
}: DeckProps) => {
  const theme: Theme = useTheme();
  const boardStyle = {
    backgroundColor: theme.palette.background.default,
  };
  const components = {
    Card: DeckCard,
  };
  return (
    <div>
      <Toolbar editingContextId={editingContextId} representationId={representationId} />
      <Board
        data={data}
        draggable={true}
        onCardClick={onCardClick}
        components={components}
        onCardDelete={onCardDelete}
        onCardAdd={onCardAdd}
        onCardUpdate={onCardUpdate}
        onCardMoveAcrossLanes={onCardMoveAcrossLanes}
        data-testid={`deck-representation`}
        style={boardStyle}
      />
    </div>
  );
};
