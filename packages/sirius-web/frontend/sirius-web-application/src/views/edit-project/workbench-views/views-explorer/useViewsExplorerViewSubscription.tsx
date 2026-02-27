/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
  GQLViewsExplorerEventData,
  UseViewsExplorerViewSubscriptionState,
  UseViewsExplorerViewSubscriptionValue,
} from './useViewsExplorerViewSubscription.types';

export const useViewsExplorerViewSubscription = (
  editingContextId: string,
  expanded: string[]
): UseViewsExplorerViewSubscriptionValue => {
  const { addErrorMessage } = useMultiToast();

  const [state, setState] = useState<UseViewsExplorerViewSubscriptionState>({
    id: crypto.randomUUID(),
    payload: null,
    complete: false,
  });

  const input = {
    id: state.id,
    editingContextId,
    representationId: `viewsexplorer://?expandedIds=[${expanded.map(encodeURIComponent).join(',')}]`,
  };

  const onData = ({ data }: OnDataOptions<GQLViewsExplorerEventData>) => {
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.viewsExplorerEvent }));
    });
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription(
    gql(getTreeEventSubscription(2, 'viewsExplorerEvent', 'ViewsExplorerEventInput')),
    {
      variables: { input },
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
