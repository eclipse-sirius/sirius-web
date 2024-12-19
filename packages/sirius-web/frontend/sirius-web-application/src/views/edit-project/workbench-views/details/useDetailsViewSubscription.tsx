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
import { formRefreshedEventPayloadFragment } from '@eclipse-sirius/sirius-components-forms';
import { useState } from 'react';
import { flushSync } from 'react-dom';
import {
  GQLDetailsEventInput,
  GQLDetailsEventSubscription,
  GQLDetailsEventVariables,
  UseDetailsViewSubscriptionState,
  UseDetailsViewSubscriptionValue,
} from './useDetailsViewSubscription.types';

export const getDetailsViewEventSubscription = `
  subscription detailsEvent($input: DetailsEventInput!) {
    detailsEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
  `;

export const useDetailsViewSubscription = (
  editingContextId: string,
  objectIds: string[],
  skip?: boolean
): UseDetailsViewSubscriptionValue => {
  const [state, setState] = useState<UseDetailsViewSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLDetailsEventInput = {
    id: state.id,
    editingContextId,
    representationId: `details://?objectIds=[${objectIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLDetailsEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLDetailsEventSubscription>) => {
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.detailsEvent, complete: false }));
    });
  };

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { loading } = useSubscription<GQLDetailsEventSubscription, GQLDetailsEventVariables>(
    gql(getDetailsViewEventSubscription),
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
