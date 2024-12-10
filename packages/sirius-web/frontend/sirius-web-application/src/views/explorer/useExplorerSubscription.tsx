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
import { getTreeEventSubscription } from '@eclipse-sirius/sirius-components-trees';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLExplorerEventData,
  GQLExplorerEventInput,
  GQLExplorerEventVariables,
  UseExplorerSubscriptionState,
  UseExplorerSubscriptionValue,
} from './useExplorerSubscription.types';

export const useExplorerSubscription = (
  editingContextId: string,
  treeDescriptionId: string,
  activeFilterIds: string[],
  expanded: string[],
  maxDepth: number
): UseExplorerSubscriptionValue => {
  const [state, setState] = useState<UseExplorerSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLExplorerEventInput = {
    id: state.id,
    editingContextId,
    representationId: `explorer://?treeDescriptionId=${encodeURIComponent(treeDescriptionId)}&expandedIds=[${expanded
      .map(encodeURIComponent)
      .join(',')}]&activeFilterIds=[${activeFilterIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLExplorerEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLExplorerEventData>) => {
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.explorerEvent, complete: false }));
    });
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLExplorerEventData, GQLExplorerEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'explorerEvent', 'ExplorerEventInput')),
    {
      variables,
      fetchPolicy: 'no-cache',
      onData,
      onComplete,
      onError,
      skip: treeDescriptionId === null,
    }
  );

  return {
    loading,
    payload: state.payload,
    complete: state.complete,
  };
};
