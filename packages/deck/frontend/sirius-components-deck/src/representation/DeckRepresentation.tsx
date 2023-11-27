/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { RepresentationComponentProps, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useEffect, useState } from 'react';
import { Deck } from '../Deck';
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
  const { addErrorMessage, addMessages } = useMultiToast();
  const [{ id, deck, complete }, setState] = useState<DeckRepresentationState>({
    id: crypto.randomUUID(),
    deck: null,
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
        return { ...prevState, complete: true, deck: null };
      });
    },
  });

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

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
    const lanes = deck.lanes ?? [];
    const data = {
      lanes,
    };
    content = <Deck data={data} />;
  }
  return <>{content}</>;
};
