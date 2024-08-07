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
  GQLFormEventPayload,
  GQLFormEventSubscription,
  GQLFormEventVariables,
  GQLFormRefreshedEventPayload,
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

const isFormRefreshedEventPayload = (payload: GQLFormEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload.__typename === 'FormRefreshedEventPayload';

export const useFormSubscription = (editingContextId: string, formId: string): UseFormSubscriptionValue => {
  const [state, setState] = useState<UseFormSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
  });

  const input: GQLFormEventInput = {
    id: state.id,
    editingContextId,
    formId,
  };

  const variables: GQLFormEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({ data }: OnDataOptions<GQLFormEventSubscription>) => {
    const { data: gqlDetailsEventSubscription } = data;
    if (gqlDetailsEventSubscription) {
      const { formEvent: payload } = gqlDetailsEventSubscription;
      setState((prevState) => ({ ...prevState, payload, complete: false }));
      if (isFormRefreshedEventPayload(payload)) {
        setState((prevState) => ({ ...prevState, complete: false }));
      }
    }
  };

  const { data, error, loading } = useSubscription<GQLFormEventSubscription, GQLFormEventVariables>(
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
    payload: !!data?.formEvent ? data.formEvent : null,
    complete: state.complete,
  };
};
