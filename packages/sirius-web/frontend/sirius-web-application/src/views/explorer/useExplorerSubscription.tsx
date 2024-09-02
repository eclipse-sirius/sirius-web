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

import { gql, useSubscription } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { getTreeEventSubscription } from '@eclipse-sirius/sirius-components-trees';
import { useEffect, useState } from 'react';
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
  });

  const input: GQLExplorerEventInput = {
    id: state.id,
    editingContextId,
    representationId: `explorer://?treeDescriptionId=${encodeURIComponent(treeDescriptionId)}&expandedIds=[${expanded
      .map(encodeURIComponent)
      .join(',')}]&activeFilterIds=[${activeFilterIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLExplorerEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { data, error, loading } = useSubscription<GQLExplorerEventData, GQLExplorerEventVariables>(
    gql(getTreeEventSubscription(maxDepth, 'explorerEvent', 'ExplorerEventInput')),
    {
      variables,
      fetchPolicy: 'no-cache',
      onComplete,
      skip: treeDescriptionId === null,
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
    payload: !!data?.explorerEvent ? data.explorerEvent : null,
    complete: state.complete,
  };
};
