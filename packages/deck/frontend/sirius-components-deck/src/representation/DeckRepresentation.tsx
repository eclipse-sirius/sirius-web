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

import { useSubscription } from '@apollo/client';
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
import { CardMetadata } from '../Deck.types';
import { convertToTrelloDeckData } from '../utils/deckGQLConverter';
import {
  DeckRepresentationState,
  GQLDeckEventPayload,
  GQLDeckEventSubscription,
  GQLDeckRefreshedEventPayload,
  GQLErrorPayload,
} from './DeckRepresentation.types';
import { deckEventSubscription } from './deckGQLQuery';
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

export const DeckRepresentation = ({ editingContextId, representationId }: RepresentationComponentProps) => {
  const classes = useDeckRepresentationStyles();
  const { selection, setSelection }: UseSelectionValue = useSelection();
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ id, deck, complete, selectedCardIds }, setState] = useState<DeckRepresentationState>({
    id: crypto.randomUUID(),
    deck: null,
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
        return { ...prevState, complete: true, deck: null };
      });
    },
  });

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

  const handleCardClicked = (cardId: string, metadata: CardMetadata, _laneId: string) => {
    let editedCardId: string | undefined = undefined;
    if (selectedCardIds.includes(cardId)) {
      editedCardId = cardId;
    }
    setState((prevState) => {
      return { ...prevState, editedCardId };
    });
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
    content = <Deck data={data} onCardClick={handleCardClicked} />;
  }
  return <>{content}</>;
};
