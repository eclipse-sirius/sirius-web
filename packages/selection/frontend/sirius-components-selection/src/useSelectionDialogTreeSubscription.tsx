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
import { getTreeEventSubscription } from '@eclipse-sirius/sirius-components-trees';
import { useEffect, useState } from 'react';
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
    representationId: `${treeId}&expandedIds=[${expanded.join(',')}]`,
  };

  const variables: GQLSelectionDialogTreeEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLSelectionDialogTreeEventData>) => {
    const { data: gqlTreeData } = data;
    if (gqlTreeData) {
      const { selectionDialogTreeEvent: payload } = gqlTreeData;
      if (isTreeRefreshedEventPayload(payload)) {
        const { tree } = payload;
        setState((prevState) => ({ ...prevState, tree }));
      }
    }
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { error, loading } = useSubscription<GQLSelectionDialogTreeEventData, GQLSelectionDialogTreeEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'selectionDialogTreeEvent', 'SelectionDialogTreeEventInput')),
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
