/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { ApolloError, gql, OnDataOptions, useSubscription } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLEditingContextEventInput,
  GQLEditingContextEventSubscription,
  GQLEditingContextEventVariables,
  UseEditingContextEventSubscriptionState,
  UseEditingContextEventSubscriptionValue,
} from './useEditingContextEventSubscription.types';

const getEditingContextEventSubscription = `
  subscription editingContextEvent($input: EditingContextEventInput!) {
    editingContextEvent(input: $input) {
      __typename
      ... on RepresentationRenamedEventPayload {
        representationId
        newLabel
      }
    }
  }
`;

export const useEditingContextEventSubscription = (
  editingContextId: string
): UseEditingContextEventSubscriptionValue => {
  const [state, setState] = useState<UseEditingContextEventSubscriptionState>({
    id: crypto.randomUUID(),
    payload: null,
    complete: false,
  });

  const input: GQLEditingContextEventInput = {
    id: state.id,
    editingContextId,
  };

  const variables: GQLEditingContextEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({ data }: OnDataOptions<GQLEditingContextEventSubscription>) => {
    flushSync(() => {
      if (data.data) {
        const { editingContextEvent } = data.data;
        setState((prevState) => ({ ...prevState, payload: editingContextEvent }));
      }
    });
  };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLEditingContextEventSubscription, GQLEditingContextEventVariables>(
    gql(getEditingContextEventSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
      onData,
      onComplete,
      onError,
    }
  );

  return {
    loading,
    payload: state.payload,
    complete: state.complete,
  };
};
