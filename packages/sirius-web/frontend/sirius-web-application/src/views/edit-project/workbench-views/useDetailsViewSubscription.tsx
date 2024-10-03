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

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({}: OnDataOptions<GQLDetailsEventSubscription>) =>
    setState((prevState) => ({ ...prevState, complete: false }));

  const { data, error, loading } = useSubscription<GQLDetailsEventSubscription, GQLDetailsEventVariables>(
    gql(getDetailsViewEventSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
      skip,
      onData,
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
    payload: data?.detailsEvent ?? null,
    complete: state.complete,
  };
};
