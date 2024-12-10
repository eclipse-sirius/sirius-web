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
  GQLSelectionDialogTreeEventData,
  GQLSelectionDialogTreeEventInput,
  GQLSelectionDialogTreeEventVariables,
  GQLTreeEventPayload,
  GQLTreeRefreshedEventPayload,
  UseSelectionDialogTreeSubscriptionState,
  UseSelectionDialogTreeSubscriptionValue,
} from './useSelectionDialogTreeSubscription.types';

const isTreeRefreshedEventPayload = (payload: GQLTreeEventPayload): payload is GQLTreeRefreshedEventPayload =>
  payload.__typename === 'TreeRefreshedEventPayload';

export const useSelectionDialogTreeSubscription = (
  editingContextId: string,
  treeId: string,
  expanded: string[],
  maxDepth: number
): UseSelectionDialogTreeSubscriptionValue => {
  const [state, setState] = useState<UseSelectionDialogTreeSubscriptionState>({
    id: crypto.randomUUID(),
    tree: null,
    complete: false,
  });

  const input: GQLSelectionDialogTreeEventInput = {
    id: state.id,
    editingContextId,
    representationId: `${treeId}&expandedIds=[${expanded.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLSelectionDialogTreeEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLSelectionDialogTreeEventData>) => {
    flushSync(() => {
      if (data.data) {
        const { selectionDialogTreeEvent } = data.data;
        if (isTreeRefreshedEventPayload(selectionDialogTreeEvent)) {
          const { tree } = selectionDialogTreeEvent;
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

  const { loading } = useSubscription<GQLSelectionDialogTreeEventData, GQLSelectionDialogTreeEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'selectionDialogTreeEvent', 'SelectionDialogTreeEventInput')),
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
