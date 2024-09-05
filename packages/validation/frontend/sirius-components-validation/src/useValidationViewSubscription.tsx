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
import {
  GQLValidationEventInput,
  GQLValidationEventSubscription,
  GQLValidationEventVariables,
  UseValidationViewSubscriptionState,
  UseValidationViewSubscriptionValue,
} from './useValidationViewSubscription.types';

const getValidationViewEventSubscription = `
  subscription validationEvent($input: ValidationEventInput!) {
    validationEvent(input: $input) {
      __typename
      ... on ValidationRefreshedEventPayload {
        id
        validation {
          id
          diagnostics {
            id
            kind
            message
          }
        }
      }
    }
  }
`;

export const useValidationViewSubscription = (
  editingContextId: string,
  skip?: boolean
): UseValidationViewSubscriptionValue => {
  const [state, setState] = useState<UseValidationViewSubscriptionState>({
    id: crypto.randomUUID(),
    complete: false,
  });

  const input: GQLValidationEventInput = {
    id: state.id,
    editingContextId,
    representationId: 'validation://',
  };

  const variables: GQLValidationEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({}: OnDataOptions<GQLValidationEventSubscription>) =>
    setState((prevState) => ({ ...prevState, complete: false }));

  const { data, error, loading } = useSubscription<GQLValidationEventSubscription, GQLValidationEventVariables>(
    gql(getValidationViewEventSubscription),
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
    payload: data?.validationEvent ?? null,
    complete: state.complete,
  };
};
