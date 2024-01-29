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

import { ApolloError, useMutation, useSubscription } from '@apollo/client';
import {
  RepresentationComponentProps,
  UseSelectionValue,
  useMultiToast,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { Theme, makeStyles, useTheme } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { Deck } from '../Deck';
import { Card, CardMetadata, Lane } from '../Deck.types';
import {
  convertToTrelloDeckData,
  findLaneById,
  moveCardInDeckLanes,
  moveLaneInDeck,
  updateCard,
  updateLane,
} from '../utils/deckGQLHelper';
import { DeckRepresentationState } from './DeckRepresentation.types';
import { deckEventSubscription } from './deckSubscription';
import {
  GQLDeckEventPayload,
  GQLDeckEventSubscription,
  GQLDeckRefreshedEventPayload,
  GQLErrorPayload,
  GQLLane,
} from './deckSubscription.types';

import {
  GQLCreateCardData,
  GQLCreateCardVariables,
  GQLCreateDeckCardInput,
  GQLCreateDeckCardPayload,
  GQLDeleteCardData,
  GQLDeleteCardVariables,
  GQLDeleteDeckCardInput,
  GQLDeleteDeckCardPayload,
  GQLDropDeckCardData,
  GQLDropDeckCardInput,
  GQLDropDeckCardVariables,
  GQLDropDeckLaneData,
  GQLDropDeckLaneInput,
  GQLDropDeckLaneVariables,
  GQLEditCardData,
  GQLEditCardVariables,
  GQLEditDeckCardInput,
  GQLEditDeckCardPayload,
  GQLEditDeckLaneInput,
  GQLEditLaneData,
  GQLEditLaneVariables,
  GQLSuccessPayload,
} from './deckMutation.types';

import {
  createCardMutation,
  deleteCardMutation,
  dropDeckCardMutation,
  dropDeckLaneMutation,
  editCardMutation,
  editLaneMutation,
} from './deckMutation';
const useDeckRepresentationStyles = makeStyles(() => ({
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
}));

const isDeckRefreshedEventPayload = (payload: GQLDeckEventPayload): payload is GQLDeckRefreshedEventPayload =>
  payload.__typename === 'DeckRefreshedEventPayload';
const isErrorPayload = (payload: GQLDeckEventPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isStandardErrorPayload = (field): field is GQLErrorPayload => field.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload: GQLDeleteDeckCardPayload | GQLEditDeckCardPayload | GQLCreateDeckCardPayload
): payload is GQLSuccessPayload => payload.__typename === 'SuccessPayload';

export const DeckRepresentation = ({ editingContextId, representationId }: RepresentationComponentProps) => {
  const theme: Theme = useTheme();
  const classes = useDeckRepresentationStyles();
  const { selection, setSelection }: UseSelectionValue = useSelection();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ id, deck, complete }, setState] = useState<DeckRepresentationState>({
    id: crypto.randomUUID(),
    deck: undefined,
    complete: false,
  });

  const { error } = useSubscription<GQLDeckEventSubscription>(deckEventSubscription, {
    variables: {
      input: {
        id,
        editingContextId,
        deckId: representationId,
      },
    },
    fetchPolicy: 'no-cache',

    onData: ({ data }) => {
      if (data?.data) {
        const { deckEvent } = data.data;
        if (isDeckRefreshedEventPayload(deckEvent)) {
          setState((prevState) => {
            return { ...prevState, deck: deckEvent.deck };
          });
        } else if (isErrorPayload(deckEvent)) {
          addMessages(deckEvent.messages);
        }
      }
    },
    onComplete: () => {
      setState((prevState) => {
        return { ...prevState, complete: true, deck: undefined };
      });
    },
  });

  //---------------------------------
  // Mutations
  const [deleteDeckCard, { loading: deleteDeckCardLoading, data: deleteDeckCardData, error: deleteDeckCardError }] =
    useMutation<GQLDeleteCardData, GQLDeleteCardVariables>(deleteCardMutation);

  const [editDeckCard, { loading: editCardLoading, data: editCardData, error: editCardError }] = useMutation<
    GQLEditCardData,
    GQLEditCardVariables
  >(editCardMutation);

  const [createCard, { loading: createCardLoading, data: createCardData, error: createCardError }] = useMutation<
    GQLCreateCardData,
    GQLCreateCardVariables
  >(createCardMutation);

  const [dropDeckCard, { loading: dropDeckCardLoading, data: dropDeckCardData, error: dropDeckCardError }] =
    useMutation<GQLDropDeckCardData, GQLDropDeckCardVariables>(dropDeckCardMutation);

  const [editDeckLane, { loading: editLaneLoading, data: editLaneData, error: editLaneError }] = useMutation<
    GQLEditLaneData,
    GQLEditLaneVariables
  >(editLaneMutation);

  const [dropDeckLane, { loading: dropDeckLaneLoading, data: dropDeckLaneData, error: dropDeckLaneError }] =
    useMutation<GQLDropDeckLaneData, GQLDropDeckLaneVariables>(dropDeckLaneMutation);

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  useEffect(() => {
    if (deck && selection.entries) {
      const selectionIds: string[] = selection.entries.map((entry) => entry.id);
      const tempselectedElementIds: string[] = [];
      deck.lanes
        .flatMap((lane) => lane.cards)
        .forEach((card) => {
          if (selectionIds.includes(card.targetObjectId)) {
            tempselectedElementIds.push(card.id);
          }
        });
      setState((prevState) => {
        return { ...prevState, selectedElementIds: tempselectedElementIds };
      });
    }
  }, [selection]);
  const handleError = (
    loading: boolean,
    data:
      | GQLEditCardData
      | GQLDeleteCardData
      | GQLCreateCardData
      | GQLDropDeckCardData
      | GQLEditLaneData
      | GQLDropDeckLaneData
      | null
      | undefined,
    error: ApolloError | undefined
  ) => {
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      if (data) {
        const keys = Object.keys(data);
        if (keys.length > 0) {
          const firstKey = keys[0];
          if (firstKey) {
            const firstField = data[firstKey];
            if (isStandardErrorPayload(firstField) || isSuccessPayload(firstField)) {
              const { messages } = firstField;
              addMessages(messages);
            }
          }
        }
      }
    }
  };

  useEffect(() => {
    handleError(deleteDeckCardLoading, deleteDeckCardData, deleteDeckCardError);
  }, [deleteDeckCardLoading, deleteDeckCardData, deleteDeckCardError]);
  useEffect(() => {
    handleError(editCardLoading, editCardData, editCardError);
  }, [editCardLoading, editCardData, editCardError]);
  useEffect(() => {
    handleError(createCardLoading, createCardData, createCardError);
  }, [createCardLoading, createCardData, createCardError]);
  useEffect(() => {
    handleError(dropDeckCardLoading, dropDeckCardData, dropDeckCardError);
  }, [dropDeckCardLoading, dropDeckCardData, dropDeckCardError]);
  useEffect(() => {
    handleError(dropDeckLaneLoading, dropDeckLaneData, dropDeckLaneError);
  }, [dropDeckLaneLoading, dropDeckLaneData, dropDeckLaneError]);

  useEffect(() => {
    handleError(editLaneLoading, editLaneData, editLaneError);
  }, [editLaneLoading, editLaneData, editLaneError]);

  const handleEditCard = (_laneId: string, card: Card) => {
    const input: GQLEditDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      cardId: card.id,
      newTitle: card.title,
      newLabel: card.label,
      newDescription: card.description,
    };

    if (deck) {
      // to avoid blink because useMutation implies a re-render as the card value is the old one
      const updatedDeck = updateCard(deck, card);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
    }
    editDeckCard({ variables: { input } });
  };

  const handleEditLane = (laneId: string, newValue: { title: string }) => {
    const input: GQLEditDeckLaneInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      laneId,
      newTitle: newValue.title,
    };

    if (deck) {
      // to avoid blink because useMutation implies a re-render as the lane value is the old one
      const updatedDeck = updateLane(deck, laneId, newValue.title);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
    }
    editDeckLane({ variables: { input } });
  };

  const handleDeleteCard = (cardId: string, _laneId: string) => {
    const input: GQLDeleteDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      cardId,
    };
    deleteDeckCard({ variables: { input } });
  };
  const handleCreateCard = (card: Card, laneId: string) => {
    const input: GQLCreateDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      currentLaneId: laneId,
      title: card.title,
      label: card.label,
      description: card.description,
    };
    createCard({ variables: { input } });
  };

  const handleCardClicked = (_cardId: string, metadata: CardMetadata, _laneId: string) => {
    setSelection({
      entries: [metadata.selection],
    });
  };

  const handleDropDeckCard = (oldLaneId: string, newLaneId: string, cardId: string, addedIndex: number) => {
    if (deck) {
      const input: GQLDropDeckCardInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        oldLaneId,
        newLaneId,
        cardId,
        addedIndex,
      };
      const updatedDeck = moveCardInDeckLanes(deck, oldLaneId, newLaneId, cardId, addedIndex);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
      dropDeckCard({ variables: { input } });
    }
  };

  const handleLaneClicked = (laneId: string) => {
    if (deck) {
      const lane: GQLLane | undefined = findLaneById(deck, laneId);
      if (lane) {
        setSelection({
          entries: [
            {
              id: lane.targetObjectId,
              kind: 'lane',
              label: lane.label,
            },
          ],
        });
      }
    }
  };

  const handleLaneDragEnd = (oldIndex: number, newIndex: number, lane: Lane) => {
    if (deck) {
      const input: GQLDropDeckLaneInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId,
        laneId: lane.id,
        newIndex,
      };
      const updatedDeck = moveLaneInDeck(deck, oldIndex, newIndex);
      setState((prevState) => {
        return { ...prevState, deck: updatedDeck };
      });
      dropDeckLane({ variables: { input } });
    }
  };

  let content: JSX.Element | null = null;
  if (complete) {
    content = (
      <div className={classes.complete}>
        <Typography variant="h5" align="center">
          The Deck does not exist anymore
        </Typography>
      </div>
    );
  } else if (deck) {
    const selectedElementIds: string[] = selection.entries.map((entry) => entry.id);
    const data = convertToTrelloDeckData(deck, selectedElementIds, theme);
    content = (
      <Deck
        editingContextId={editingContextId}
        representationId={representationId}
        data={data}
        onCardClick={handleCardClicked}
        onCardDelete={handleDeleteCard}
        onCardAdd={handleCreateCard}
        onCardUpdate={handleEditCard}
        onCardMoveAcrossLanes={handleDropDeckCard}
        onLaneClick={handleLaneClicked}
        onLaneUpdate={handleEditLane}
        handleLaneDragEnd={handleLaneDragEnd}
      />
    );
  }
  return <>{content}</>;
};
