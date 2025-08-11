/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { formRefreshedEventPayloadFragment } from '../form/FormEventFragments';
import {
  GQLFormEventInput,
  GQLFormEventPayload,
  GQLFormEventSubscription,
  GQLFormEventVariables,
  GQLFormRefreshedEventPayload,
  UseFormSubscriptionState,
  UseFormSubscriptionValue,
} from './useFormSubscription.types';

export const formEventSubscription = `
  subscription formEvent($input: FormEventInput!) {
    formEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
`;

const isFormRefreshedEventPayload = (payload: GQLFormEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload.__typename === 'FormRefreshedEventPayload';

export const useFormSubscription = (editingContextId: string, formId: string): UseFormSubscriptionValue => {
  const [state, setState] = useState<UseFormSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLFormEventInput = {
    id: state.id,
    editingContextId,
    formId,
  };

  const variables: GQLFormEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onData = ({ data }: OnDataOptions<GQLFormEventSubscription>) => {
    flushSync(() => {
      if (data.data) {
        const { formEvent } = data.data;
        setState((prevState) => ({ ...prevState, payload: formEvent, complete: false }));
        if (isFormRefreshedEventPayload(formEvent)) {
          setState((prevState) => ({ ...prevState, complete: false }));
        }
      }
    });
  };

  const { loading } = useSubscription<GQLFormEventSubscription, GQLFormEventVariables>(gql(formEventSubscription), {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
    onError,
  });

  return {
    loading,
    payload: state.payload,
    complete: state.complete,
  };
};
