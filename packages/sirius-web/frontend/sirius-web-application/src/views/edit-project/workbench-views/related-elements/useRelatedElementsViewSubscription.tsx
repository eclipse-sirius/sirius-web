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
  GQLRelatedElementsEventInput,
  GQLRelatedElementsEventSubscription,
  GQLRelatedElementsEventVariables,
  UseRelatedElementsViewSubscriptionState,
  UseRelatedElementsViewSubscriptionValue,
} from './useRelatedElementsViewSubscription.types';

export const getRelatedElementsViewEventSubscription = `
  subscription relatedElementsEvent($input: RelatedElementsEventInput!) {
    relatedElementsEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
  `;

export const useRelatedElementsViewSubscription = (
  editingContextId: string,
  objectIds: string[],
  skip?: boolean
): UseRelatedElementsViewSubscriptionValue => {
  const [state, setState] = useState<UseRelatedElementsViewSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLRelatedElementsEventInput = {
    id: state.id,
    editingContextId,
    representationId: `relatedElements://?objectIds=[${objectIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLRelatedElementsEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({ data }: OnDataOptions<GQLRelatedElementsEventSubscription>) =>
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.relatedElementsEvent, complete: false }));
    });

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const { loading } = useSubscription<GQLRelatedElementsEventSubscription, GQLRelatedElementsEventVariables>(
    gql(getRelatedElementsViewEventSubscription),
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
