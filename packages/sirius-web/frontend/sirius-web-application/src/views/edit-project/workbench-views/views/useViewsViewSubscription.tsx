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
import { GQLViewsEventPayload, UseViewsViewSubscriptionValue } from './useViewsViewSubscription.types';

export const useViewsViewSubscription = (
  editingContextId: string,
  expanded: string[]
): UseViewsViewSubscriptionValue => {
  const { addErrorMessage } = useMultiToast();

  const [state, setState] = useState<{ id: string; payload: GQLViewsEventPayload | null }>({
    id: crypto.randomUUID(),
    payload: null,
  });

  const input = {
    id: state.id,
    editingContextId,
    representationId: `views://?treeDescriptionId=views_form_description&expandedIds=[${expanded
      .map(encodeURIComponent)
      .join(',')}]`,
  };

  const onData = ({ data }: OnDataOptions<{ viewsEvent: GQLViewsEventPayload }>) => {
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.viewsEvent }));
    });
  };

  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  useSubscription(gql(getTreeEventSubscription(2, 'viewsEvent', 'ViewsEventInput')), {
    variables: { input },
    fetchPolicy: 'no-cache',
    onData,
    onError,
  });

  return {
    payload: state.payload,
  };
};
