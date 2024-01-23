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
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { Deck } from '../Deck';
import { Card, CardMetadata } from '../Deck.types';
import { convertToTrelloDeckData } from '../utils/deckGQLConverter';
import { DeckRepresentationState } from './DeckRepresentation.types';
import { deckEventSubscription } from './deckSubscription';
import {
  GQLDeckEventPayload,
  GQLDeckEventSubscription,
  GQLDeckRefreshedEventPayload,
  GQLErrorPayload,
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
  GQLEditCardData,
  GQLEditCardVariables,
  GQLEditDeckCardInput,
  GQLEditDeckCardPayload,
  GQLSuccessPayload,
} from './deckMutation.types';

import { createCardMutation, deleteCardMutation, editCardMutation } from './deckMutation';
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
  const classes = useDeckRepresentationStyles();
  const { selection, setSelection }: UseSelectionValue = useSelection();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ id, deck, complete, selectedCardIds }, setState] = useState<DeckRepresentationState>({
    id: crypto.randomUUID(),
    deck: undefined,
    complete: false,
    selectedCardIds: [],
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

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  useEffect(() => {
    if (deck && selection.entries) {
      const selectionIds: string[] = selection.entries.map((entry) => entry.id);
      const tempSelectedCardIds: string[] = [];
      deck.lanes
        .flatMap((lane) => lane.cards)
        .forEach((card) => {
          if (selectionIds.includes(card.targetObjectId)) {
            tempSelectedCardIds.push(card.id);
          }
        });
      setState((prevState) => {
        return { ...prevState, selectedCardIds: tempSelectedCardIds };
      });
    }
  }, [selection]);
  const handleError = (
    loading: boolean,
    data: GQLEditCardData | GQLDeleteCardData | GQLCreateCardData | null | undefined,
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

    // // to avoid blink because useMutation implies a re-render as the task value is the old one
    // updateTask(gantt, task.id, newDetail);
    editDeckCard({ variables: { input } });
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
    const data = convertToTrelloDeckData(deck, selectedCardIds);
    content = (
      <Deck
        editingContextId={editingContextId}
        representationId={representationId}
        data={data}
        onCardClick={handleCardClicked}
        onCardDelete={handleDeleteCard}
        onCardAdd={handleCreateCard}
        onCardUpdate={handleEditCard}
      />
    );
  }
  return <>{content}</>;
};
