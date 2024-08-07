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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { formRefreshedEventPayloadFragment } from '@eclipse-sirius/sirius-components-forms';
import { useEffect, useState } from 'react';
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
  });

  const input: GQLRelatedElementsEventInput = {
    id: state.id,
    editingContextId,
    objectIds,
  };

  const variables: GQLRelatedElementsEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({}: OnDataOptions<GQLRelatedElementsEventSubscription>) =>
    setState((prevState) => ({ ...prevState, complete: false }));

  const { data, error, loading } = useSubscription<
    GQLRelatedElementsEventSubscription,
    GQLRelatedElementsEventVariables
  >(gql(getRelatedElementsViewEventSubscription), {
    variables,
    fetchPolicy: 'no-cache',
    skip,
    onData,
    onComplete,
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
  }, [error]);

  return {
    loading,
    payload: data?.relatedElementsEvent ?? null,
    complete: state.complete,
  };
};
