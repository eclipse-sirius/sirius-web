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
import { memo, useState } from 'react';
import { diagramEventSubscription } from '../graphql/subscription/diagramEventSubscription';
import {
  GQLDiagramEventPayload,
  GQLDiagramRefreshedEventPayload,
} from '../graphql/subscription/diagramEventSubscription.types';
import { DiagramRenderer } from '../renderer/DiagramRenderer';
import { GQLDiagramEventData, GQLDiagramEventVariables } from './DiagramRepresentation.types';
import { DiagramSubscriptionProviderProps, DiagramSubscriptionState } from './DiagramSubscriptionProvider.types';

const subscription = gql(diagramEventSubscription);

const isDiagramRefreshedEventPayload = (payload: GQLDiagramEventPayload): payload is GQLDiagramRefreshedEventPayload =>
  payload.__typename === 'DiagramRefreshedEventPayload';

export const DiagramSubscriptionProvider = memo(({ diagramId, editingContextId }: DiagramSubscriptionProviderProps) => {
  const [state, setState] = useState<DiagramSubscriptionState>({
    id: crypto.randomUUID(),
    diagramRefreshedEventPayload: null,
    complete: false,
    message: '',
  });

  const variables: GQLDiagramEventVariables = {
    input: {
      id: state.id,
      editingContextId,
      diagramId,
    },
  };

  const onData = ({ data }: OnDataOptions<GQLDiagramEventData>) => {
    if (data.data) {
      const { diagramEvent } = data.data;
      if (isDiagramRefreshedEventPayload(diagramEvent)) {
        setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: diagramEvent }));
      }
    }
  };

  const onComplete = () => {
    setState((prevState) => ({ ...prevState, diagramRefreshedEventPayload: null, complete: true }));
  };

  const { error } = useSubscription<GQLDiagramEventData>(subscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
  });

  if (error) {
    return <div>{error.message}</div>;
  }
  if (state.complete) {
    return <div>The representation is not available anymore</div>;
  }
  if (!state.diagramRefreshedEventPayload) {
    return <div></div>;
  }

  return <DiagramRenderer diagramRefreshedEventPayload={state.diagramRefreshedEventPayload} />;
});
