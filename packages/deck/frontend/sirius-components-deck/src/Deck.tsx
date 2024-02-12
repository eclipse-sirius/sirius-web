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
import { Theme, makeStyles, useTheme } from '@material-ui/core/styles';
import { CSSProperties, useEffect, useRef } from 'react';
import { DeckProps } from './Deck.types';
import { DeckCard } from './card/DeckCard';
import { useZoom } from './hooks/useZoom';
import { UseZoomValue } from './hooks/useZoom.types';
import { DeckLaneHeader } from './laneHeader/DeckLaneHeader';
import { DeckContext } from './representation/DeckContext';
import { DeckToolbar } from './toolbar/DeckToolbar';

const useDeckStyle = makeStyles((theme) => ({
  boardContainer: {
    //We need to make the board display flex to fit to screen when the deck is smaller than the representation container.
    //Without that, the board div occupies the whole representation container width.
    display: 'flex',
  },
  deckContainer: {
    backgroundColor: theme.palette.background.default,
  },
}));

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
  onLaneCollapseUpdate,
  handleLaneDragEnd,
}: DeckProps) => {
  const theme: Theme = useTheme();
  const boardContainerRef = useRef<HTMLDivElement | null>(null);
  const deckContainerRef = useRef<HTMLDivElement | null>(null);
  const representationContainerRef = useRef<HTMLDivElement | null>(null);
  const boardRef = useRef<HTMLDivElement | null>(null);
  const { zoom, zoomIn, zoomOut, fitToScreen, resetZoom }: UseZoomValue = useZoom(boardRef, representationContainerRef);

  const deckClasses = useDeckStyle();

  useEffect(() => {
    const representationContainer = deckContainerRef.current?.parentElement;
    const boardElement = boardContainerRef.current?.firstChild;
    if (representationContainer?.nodeName === 'DIV' && boardElement?.nodeName === 'DIV') {
      //We retrieve the board div element ref and the representation container ref. It is needed to compute the zoom ratio for the fit to screen.
      boardRef.current = boardElement as HTMLDivElement;
      representationContainerRef.current = representationContainer as HTMLDivElement;
    }
  }, [boardContainerRef, deckContainerRef]);

  const boardStyle: CSSProperties = {
    backgroundColor: theme.palette.background.default,
    transform: `scale(${zoom})`,
    transformOrigin: '0 0',
    transitionDuration: '0.3s',
  };

  const components = {
    Card: DeckCard,
    LaneHeader: DeckLaneHeader,
  };

  const getGhostParent = (): HTMLDivElement | undefined => {
    return boardContainerRef.current ? boardContainerRef.current : undefined;
  };

  return (
    <DeckContext.Provider value={{ editingContextId, deckId: representationId }}>
      <div ref={deckContainerRef} className={deckClasses.deckContainer}>
        <DeckToolbar
          editingContextId={editingContextId}
          representationId={representationId}
          onZoomIn={zoomIn}
          onZoomOut={zoomOut}
          onFitToScreen={fitToScreen}
          onResetZoom={resetZoom}
          fullscreenNode={deckContainerRef}
        />

        <div ref={boardContainerRef} className={deckClasses.boardContainer}>
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
            onLaneCollapseUpdate={onLaneCollapseUpdate}
            onCardMoveAcrossLanes={onCardMoveAcrossLanes}
            handleLaneDragEnd={handleLaneDragEnd}
            getGhostParent={getGhostParent}
            style={boardStyle}
            data-testid={`deck-representation`}
          />
        </div>
      </div>
    </DeckContext.Provider>
  );
};
