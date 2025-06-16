/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLProjectEventInput,
  GQLProjectEventSubscription,
  GQLProjectEventVariables,
  UseProjectEventSubscriptionState,
  UseProjectEventSubscriptionValue,
} from './useProjectEventSubscription.types';

const getProjectEventSubscription = `
  subscription projectEvent($input: ProjectEventInput!) {
    projectEvent(input: $input) {
      __typename
      ... on ProjectRenamedEventPayload {
        projectId
        newName
      }
    }
  }
`;

export const useProjectEventSubscription = (projectId: string): UseProjectEventSubscriptionValue => {
  const [state, setState] = useState<UseProjectEventSubscriptionState>({
    id: crypto.randomUUID(),
    payload: null,
    complete: false,
  });

  const input: GQLProjectEventInput = {
    id: state.id,
    projectId,
  };

  const variables: GQLProjectEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({ data }: OnDataOptions<GQLProjectEventSubscription>) => {
    flushSync(() => {
      if (data.data) {
        const { projectEvent } = data.data;
        setState((prevState) => ({ ...prevState, payload: projectEvent }));
      }
    });
  };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLProjectEventSubscription, GQLProjectEventVariables>(
    gql(getProjectEventSubscription),
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
    payload: state.payload,
    complete: state.complete,
  };
};
