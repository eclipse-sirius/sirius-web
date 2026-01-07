/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
  GQLRelatedViewsEventInput,
  GQLRelatedViewsEventSubscription,
  GQLRelatedViewsEventVariables,
  UseRelatedViewsViewSubscriptionState,
  UseRelatedViewsViewSubscriptionValue,
} from './useRelatedViewsViewSubscription.types';

export const getRelatedViewsViewEventSubscription = `
  subscription relatedViewsEvent($input: RelatedViewsEventInput!) {
    relatedViewsEvent(input: $input) {
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

export const useRelatedViewsViewSubscription = (
  editingContextId: string,
  objectIds: string[],
  skip?: boolean
): UseRelatedViewsViewSubscriptionValue => {
  const [state, setState] = useState<UseRelatedViewsViewSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLRelatedViewsEventInput = {
    id: state.id,
    editingContextId,
    representationId: `relatedviews://?objectIds=[${objectIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLRelatedViewsEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLRelatedViewsEventSubscription>) =>
    flushSync(() => {
      setState((prevState) => ({ ...prevState, payload: data.data.relatedViewsEvent, complete: false }));
    });

  const { addErrorMessage } = useMultiToast();
  const onError = ({ message }: ApolloError) => {
    addErrorMessage(message);
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { loading } = useSubscription<GQLRelatedViewsEventSubscription, GQLRelatedViewsEventVariables>(
    gql(getRelatedViewsViewEventSubscription),
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
