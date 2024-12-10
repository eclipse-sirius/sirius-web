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
import { formRefreshedEventPayloadFragment } from '@eclipse-sirius/sirius-components-forms';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLDiagramFilterEventInput,
  GQLDiagramFilterEventSubscription,
  GQLDiagramFilterEventVariables,
  UseDiagramFilterSubscriptionState,
  UseDiagramFilterSubscriptionValue,
} from './useDiagramFilterSubscription.types';

export const getDiagramFilterEventSubscription = `
  subscription diagramFilterEvent($input: DiagramFilterEventInput!) {
    diagramFilterEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
  `;

export const useDiagramFilterSubscription = (
  editingContextId: string,
  objectIds: string[],
  skip?: boolean
): UseDiagramFilterSubscriptionValue => {
  const [state, setState] = useState<UseDiagramFilterSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLDiagramFilterEventInput = {
    id: state.id,
    editingContextId,
    representationId: `diagramFilter://?objectIds=[${objectIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLDiagramFilterEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({ data }: OnDataOptions<GQLDiagramFilterEventSubscription>) => {
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.diagramFilterEvent, complete: false }));
    });
  };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLDiagramFilterEventSubscription, GQLDiagramFilterEventVariables>(
    gql(getDiagramFilterEventSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
      skip,
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
