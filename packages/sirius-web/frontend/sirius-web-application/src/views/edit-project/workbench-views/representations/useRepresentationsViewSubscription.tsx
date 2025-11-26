/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import {
  formCapabilitiesRefreshedEventPayloadFragment,
  formRefreshedEventPayloadFragment,
} from '@eclipse-sirius/sirius-components-forms';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLRepresentationsEventInput,
  GQLRepresentationsEventSubscription,
  GQLRepresentationsEventVariables,
  UseRepresentationsViewSubscriptionState,
  UseRepresentationsViewSubscriptionValue,
} from './useRepresentationsViewSubscription.types';

export const getRepresentationsViewEventSubscription = `
  subscription representationsEvent($input: RepresentationsEventInput!) {
    representationsEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
      ... on FormCapabilitiesRefreshedEventPayload {
        ...formCapabilitiesRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
  ${formCapabilitiesRefreshedEventPayloadFragment}
  `;

export const useRepresentationsViewSubscription = (
  editingContextId: string,
  objectIds: string[],
  skip?: boolean
): UseRepresentationsViewSubscriptionValue => {
  const [state, setState] = useState<UseRepresentationsViewSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLRepresentationsEventInput = {
    id: state.id,
    editingContextId,
    representationId: `representations://?objectIds=[${objectIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLRepresentationsEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLRepresentationsEventSubscription>) =>
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.representationsEvent, complete: false }));
    });

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { loading } = useSubscription<GQLRepresentationsEventSubscription, GQLRepresentationsEventVariables>(
    gql(getRepresentationsViewEventSubscription),
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
