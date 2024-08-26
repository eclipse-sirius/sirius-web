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

import { gql, OnDataOptions, useSubscription } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';

import { useEffect, useState } from 'react';

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
    representationId: `${treeId}?expandedIds=[${expanded.join(',')}]`,
  };

  const variables: GQLTreeEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLTreeEventData>) => {
    const { data: gqlTreeData } = data;
    if (gqlTreeData) {
      const { treeEvent: payload } = gqlTreeData;
      if (isTreeRefreshedEventPayload(payload)) {
        const { tree } = payload;
        setState((prevState) => ({ ...prevState, tree }));
      }
    }
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { error, loading } = useSubscription<GQLTreeEventData, GQLTreeEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'treeEvent', 'TreeEventInput')),
    {
      variables,
      fetchPolicy: 'no-cache',
      onData,
      onComplete,
    }
  );

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [error]);

  return {
    loading,
    tree: state.tree,
    complete: state.complete,
  };
};
