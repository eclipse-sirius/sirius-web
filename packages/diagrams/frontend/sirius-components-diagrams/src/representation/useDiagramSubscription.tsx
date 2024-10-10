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
import { useEffect, useState } from 'react';
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

  const onComplete = () => {
    setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: null, complete: true }));
  };

  const { error, loading, data } = useSubscription<GQLDiagramEventSubscription, GQLDiagramEventVariables>(
    gql(diagramEventSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
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
    payload: data?.diagramEvent ?? null,
    complete: state.complete,
  };
};
