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

import { ApolloError, gql, OnDataOptions, useSubscription } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';

import { useState } from 'react';

import { flushSync } from 'react-dom';
import { getTreeEventSubscription } from '../views/getTreeEventSubscription';
import {
  GQLTreeEventData,
  GQLTreeEventInput,
  GQLTreeEventPayload,
  GQLTreeEventVariables,
  GQLTreeRefreshedEventPayload,
  UseTreeSubscriptionState,
  UseTreeSubscriptionValue,
} from './useTreeSubscription.types';

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload.__typename === 'TreeRefreshedEventPayload';

export const useTreeSubscription = (
  editingContextId: string,
  treeId: string,
  expanded: string[],
  maxDepth: number
): UseTreeSubscriptionValue => {
  const [state, setState] = useState<UseTreeSubscriptionState>({
    id: crypto.randomUUID(),
    tree: null,
    complete: false,
  });

  const input: GQLTreeEventInput = {
    id: state.id,
    editingContextId,
    representationId: `${treeId}?expandedIds=[${expanded.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLTreeEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLTreeEventData>) => {
    flushSync(() => {
      if (data.data) {
        const { treeEvent: payload } = data.data;
        if (isTreeRefreshedEventPayload(payload)) {
          const { tree } = payload;
          setState((prevState) => ({ ...prevState, tree }));
        }
      }
    });
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLTreeEventData, GQLTreeEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'treeEvent', 'TreeEventInput')),
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
    tree: state.tree,
    complete: state.complete,
  };
};
