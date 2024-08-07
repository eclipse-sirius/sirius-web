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
import { useEffect, useState } from 'react';
import { formRefreshedEventPayloadFragment } from '../form/FormEventFragments';
import {
  GQLFormEventInput,
  GQLFormEventSubscription,
  GQLFormEventVariables,
  UseFormSubscriptionState,
  UseFormSubscriptionValue,
} from './useFormSubscription.types';

export const formEventSubscription = `
  subscription formEvent($input: FormEventInput!) {
    formEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
`;

export const useFormSubscription = (editingContextId: string, formId: string): UseFormSubscriptionValue => {
  const [state, setState] = useState<UseFormSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
    payload: null,
  });

  const input: GQLFormEventInput = {
    id: state.id,
    editingContextId,
    formId,
  };

  const variables: GQLFormEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLFormEventSubscription>) => {
    const { data: gqlFormEventSubscription } = data;
    if (gqlFormEventSubscription) {
      const { formEvent: payload } = gqlFormEventSubscription;
      setState((prevState) => ({ ...prevState, payload }));
    }
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { error, loading } = useSubscription<GQLFormEventSubscription, GQLFormEventVariables>(
    gql(formEventSubscription),
    {
      variables,
      fetchPolicy: 'no-cache',
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
    payload: state.payload,
    complete: state.complete,
  };
};
