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
import { diagramEventSubscription } from '../graphql/subscription/diagramEventSubscription';
import {
  GQLDiagramEventSubscription,
  GQLDiagramEventVariables,
  UseDiagramSubscriptionState,
  UseDiagramSubscriptionValue,
} from './useDiagramSubscription.types';

export const useDiagramSubscription = (editingContextId: string, diagramId: string): UseDiagramSubscriptionValue => {
  const [state, setState] = useState<UseDiagramSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const variables: GQLDiagramEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      diagramId,
    },
  };

  const onData = ({ data }: OnDataOptions<GQLDiagramEventSubscription>) => {
    flushSync(() => {
      setState((prevState) => ({
        ...prevState,
        payload: data.data?.diagramEvent ?? null,
        complete: false,
      }));
    });
  };

  const onComplete = () => {
    setState((prevState) => ({ ...prevState, complete: true }));
  };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLDiagramEventSubscription, GQLDiagramEventVariables>(
    gql(diagramEventSubscription),
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
