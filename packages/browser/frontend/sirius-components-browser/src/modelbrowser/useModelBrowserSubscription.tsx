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
import { getTreeEventSubscription } from '@eclipse-sirius/sirius-components-trees';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLModelBrowserEventData,
  GQLModelBrowserEventInput,
  GQLModelBrowserEventVariables,
  GQLTreeEventPayload,
  GQLTreeRefreshedEventPayload,
  UseModelBrowserSubscriptionState,
  UseModelBrowserSubscriptionValue,
} from './useModelBrowserSubscription.types';

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload.__typename === 'TreeRefreshedEventPayload';

export const useModelBrowserSubscription = (
  editingContextId: string,
  treeId: string,
  expanded: string[],
  maxDepth: number
): UseModelBrowserSubscriptionValue => {
  const [state, setState] = useState<UseModelBrowserSubscriptionState>({
    id: crypto.randomUUID(),
    tree: null,
    complete: false,
  });

  const input: GQLModelBrowserEventInput = {
    id: state.id,
    editingContextId,
    representationId: `${treeId}&expandedIds=[${expanded.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLModelBrowserEventVariables = { input };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onData = ({ data }: OnDataOptions<GQLModelBrowserEventData>) => {
    flushSync(() => {
      if (data.data) {
        const { modelBrowserEvent } = data.data;
        if (isTreeRefreshedEventPayload(modelBrowserEvent)) {
          const { tree } = modelBrowserEvent;
          setState((prevState) => ({ ...prevState, tree }));
        }
      }
    });
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { loading } = useSubscription<GQLModelBrowserEventData, GQLModelBrowserEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'modelBrowserEvent', 'ModelBrowserEventInput')),
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
