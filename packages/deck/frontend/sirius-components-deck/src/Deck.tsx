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
import { DeckLaneHeader } from './laneHeader/DeckLaneHeader';
import { Toolbar } from './toolbar/Toolbar';

export const Deck = ({
  editingContextId,
  representationId,
  data,
  onCardClick,
  onLaneClick,
  onCardDelete,
  onCardAdd,
  onCardUpdate,
  onCardMoveAcrossLanes,
  onLaneUpdate,
  handleLaneDragEnd,
}: DeckProps) => {
  const theme: Theme = useTheme();
  const boardStyle = {
    backgroundColor: theme.palette.background.default,
  };
  const components = {
    Card: DeckCard,
    LaneHeader: DeckLaneHeader,
  };
  return (
    <div>
      <Toolbar editingContextId={editingContextId} representationId={representationId} />
      <Board
        data={data}
        draggable={true}
        onCardClick={onCardClick}
        onLaneClick={onLaneClick}
        components={components}
        onCardDelete={onCardDelete}
        onCardAdd={onCardAdd}
        onCardUpdate={onCardUpdate}
        onLaneUpdate={onLaneUpdate}
        onCardMoveAcrossLanes={onCardMoveAcrossLanes}
        handleLaneDragEnd={handleLaneDragEnd}
        data-testid={`deck-representation`}
        style={boardStyle}
      />
    </div>
  );
};
