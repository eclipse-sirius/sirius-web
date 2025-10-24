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
  GQLProjectEventSubscription,
  GQLProjectEventVariables,
  UseProjectSubscriptionState,
  UseProjectSubscriptionValue,
} from './useProjectSubscription.types';

const projectEventSubscription = gql`
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

export const useProjectSubscription = (projectId: string): UseProjectSubscriptionValue => {
  const [state, setState] = useState<UseProjectSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const variables: GQLProjectEventVariables = {
    input: {
      id: state.id,
      projectId,
    },
  };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onData = ({ data }: OnDataOptions<GQLProjectEventSubscription>) => {
    flushSync(() => {
      setState((prevState) => ({
        ...prevState,
        payload: data.data?.projectEvent ?? null,
        complete: false,
      }));
    });
  };

  const onComplete = () => {
    setState((prevState) => ({ ...prevState, complete: true }));
  };

  const { loading } = useSubscription<GQLProjectEventSubscription>(projectEventSubscription, {
    variables,
    fetchPolicy: 'no-cache',
    onData,
    onComplete,
    onError,
  });

  return { loading, payload: state.payload, complete: state.complete };
};
