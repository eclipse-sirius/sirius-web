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

import { MutationResult, gql, useMutation } from '@apollo/client';
import { GQLErrorPayload, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import { Card, Lane } from '../Deck.types';
import {
  GQLChangeCardsVisibilityData,
  GQLChangeCardsVisibilityInput,
  GQLChangeCardsVisibilityVariables,
  GQLChangeLaneCollapsedStateData,
  GQLChangeLaneCollapsedStateInput,
  GQLChangeLaneCollapsedStateVariables,
  GQLCreateCardData,
  GQLCreateCardVariables,
  GQLCreateDeckCardInput,
  GQLDeleteCardData,
  GQLDeleteCardVariables,
  GQLDeleteDeckCardInput,
  GQLDropDeckCardData,
  GQLDropDeckCardInput,
  GQLDropDeckCardVariables,
  GQLDropDeckLaneData,
  GQLDropDeckLaneInput,
  GQLDropDeckLaneVariables,
  GQLEditCardData,
  GQLEditCardVariables,
  GQLEditDeckCardInput,
  GQLEditDeckLaneInput,
  GQLEditLaneData,
  GQLEditLaneVariables,
  GQLPayload,
  UseDeckMutationsValue,
} from './useDeckMutation.types';

const deleteCardMutation = gql`
  mutation deleteDeckCard($input: DeleteDeckCardInput!) {
    deleteDeckCard(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const editCardMutation = gql`
  mutation editDeckCard($input: EditDeckCardInput!) {
    editDeckCard(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const createCardMutation = gql`
  mutation createDeckCard($input: CreateDeckCardInput!) {
    createDeckCard(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const dropDeckCardMutation = gql`
  mutation dropDeckCard($input: DropDeckCardInput!) {
    dropDeckCard(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const editLaneMutation = gql`
  mutation editDeckLane($input: EditDeckLaneInput!) {
    editDeckLane(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const dropDeckLaneMutation = gql`
  mutation dropDeckLane($input: DropDeckLaneInput!) {
    dropDeckLane(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const changeLaneCollapsedStateMutation = gql`
  mutation changeLaneCollapsedState($input: ChangeLaneCollapsedStateInput!) {
    changeLaneCollapsedState(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const changeCardsVisibilityMutation = gql`
  mutation changeCardsVisibility($input: ChangeCardsVisibilityInput!) {
    changeCardsVisibility(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload): payload is GQLErrorPayload => payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload): payload is GQLPayload => payload.__typename === 'SuccessPayload';

function useErrorReporting<T>(result: MutationResult<T>, extractPayload: (data: T | null) => GQLPayload | undefined) {
  const { addErrorMessage, addMessages } = useMultiToast();
  useEffect(() => {
    const { loading, data, error } = result;
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      const payload: GQLPayload | undefined = extractPayload(data || null);
      if (payload && (isSuccessPayload(payload) || isErrorPayload(payload))) {
        const { messages } = payload;
        addMessages(messages);
      }
    }
  }, [result.loading, result.data, result.error]);
}

export const useDeckMutations = (editingContextId: string, representationId: string): UseDeckMutationsValue => {
  const [rawDeleteDeckCard, rawDeleteDeckCardResult] = useMutation<GQLDeleteCardData, GQLDeleteCardVariables>(
    deleteCardMutation
  );
  useErrorReporting(rawDeleteDeckCardResult, (data) => data?.deleteDeckCard);

  const deleteCard = (cardId: string) => {
    const input: GQLDeleteDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      cardId,
    };
    rawDeleteDeckCard({ variables: { input } });
  };

  const [rawEditDeckCard, rawEditDeckCardResult] = useMutation<GQLEditCardData, GQLEditCardVariables>(editCardMutation);
  useErrorReporting(rawEditDeckCardResult, (data) => data?.editDeckCard);

  const editDeckCard = (card: Card) => {
    const input: GQLEditDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      cardId: card.id,
      newTitle: card.title,
      newLabel: card.label,
      newDescription: card.description,
    };
    rawEditDeckCard({ variables: { input } });
  };

  const [rawcreateCard, rawCreateCardResult] = useMutation<GQLCreateCardData, GQLCreateCardVariables>(
    createCardMutation
  );
  useErrorReporting(rawCreateCardResult, (data) => data?.createCard);

  const createCard = (card: Card, laneId: string) => {
    const input: GQLCreateDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      currentLaneId: laneId,
      title: card.title,
      label: card.label,
      description: card.description,
    };
    rawcreateCard({ variables: { input } });
  };

  const [rawDropDeckCard, rawDropDeckCardResult] = useMutation<GQLDropDeckCardData, GQLDropDeckCardVariables>(
    dropDeckCardMutation
  );
  useErrorReporting(rawDropDeckCardResult, (data) => data?.dropDeckCard);

  const dropDeckCard = (oldLaneId: string, newLaneId: string, cardId: string, addedIndex: number) => {
    const input: GQLDropDeckCardInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      oldLaneId,
      newLaneId,
      cardId,
      addedIndex,
    };
    rawDropDeckCard({ variables: { input } });
  };

  const [rawEditDeckLane, rawEditDeckLaneResult] = useMutation<GQLEditLaneData, GQLEditLaneVariables>(editLaneMutation);
  useErrorReporting(rawEditDeckLaneResult, (data) => data?.editDeckLane);

  const editDeckLane = (laneId: string, newValue: { title: string }) => {
    const input: GQLEditDeckLaneInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      laneId,
      newTitle: newValue.title,
    };
    rawEditDeckLane({ variables: { input } });
  };

  const [rawDropDeckLane, rawDropDeckLaneResult] = useMutation<GQLDropDeckLaneData, GQLDropDeckLaneVariables>(
    dropDeckLaneMutation
  );
  useErrorReporting(rawDropDeckLaneResult, (data) => data?.dropDeckLane);

  const dropDeckLane = (newIndex: number, lane: Lane) => {
    const input: GQLDropDeckLaneInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      laneId: lane.id,
      newIndex,
    };
    rawDropDeckLane({ variables: { input } });
  };

  const [rawChangeLaneCollapsedState, rawChangeLaneCollapsedStateResult] = useMutation<
    GQLChangeLaneCollapsedStateData,
    GQLChangeLaneCollapsedStateVariables
  >(changeLaneCollapsedStateMutation);
  useErrorReporting(rawChangeLaneCollapsedStateResult, (data) => data?.changeLaneCollapsedState);

  const changeLaneCollapsedState = (laneId: string, collapsed: boolean) => {
    const input: GQLChangeLaneCollapsedStateInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      laneId,
      collapsed,
    };

    rawChangeLaneCollapsedState({ variables: { input } });
  };

  const [rawChangeCardsVisibility, rawChangeCardsVisibilityResult] = useMutation<
    GQLChangeCardsVisibilityData,
    GQLChangeCardsVisibilityVariables
  >(changeCardsVisibilityMutation);
  useErrorReporting(rawChangeCardsVisibilityResult, (data) => data?.changeCardsVisibility);

  const changeCardsVisibility = (hiddenCardsIds: string[], visibleCardsIds: string[]) => {
    const input: GQLChangeCardsVisibilityInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      hiddenCardsIds,
      visibleCardsIds,
    };

    rawChangeCardsVisibility({ variables: { input } });
  };

  return {
    deleteCard,
    editDeckCard,
    createCard,
    dropDeckCard,
    editDeckLane,
    dropDeckLane,
    changeLaneCollapsedState,
    changeCardsVisibility,
  };
};
