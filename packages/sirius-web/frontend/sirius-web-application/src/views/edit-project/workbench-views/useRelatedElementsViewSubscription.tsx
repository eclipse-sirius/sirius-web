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
  GQLFormRefreshedEventPayload,
  GQLRelatedElementsEventInput,
  GQLRelatedElementsEventPayload,
  GQLRelatedElementsEventSubscription,
  GQLRelatedElementsEventVariables,
  UseRelatedElementsViewSubscriptionState,
  UseRelatedElementsViewSubscriptionValue,
} from './useRelatedElementsViewSubscription.types';

const isFormRefreshedEventPayload = (
  payload: GQLRelatedElementsEventPayload
): payload is GQLFormRefreshedEventPayload => payload.__typename === 'FormRefreshedEventPayload';

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
    form: null,
    complete: false,
  });

  const input: GQLRelatedElementsEventInput = {
    id: state.id,
    editingContextId,
    objectIds,
  };

  const variables: GQLRelatedElementsEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLRelatedElementsEventSubscription>) => {
    const { data: gqlRelatedElementsEventSubscription } = data;
    if (gqlRelatedElementsEventSubscription) {
      const { relatedElementsEvent: payload } = gqlRelatedElementsEventSubscription;
      if (isFormRefreshedEventPayload(payload)) {
        const { form } = payload;
        setState((prevState) => ({ ...prevState, form }));
      }
    }
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { error, loading } = useSubscription<GQLRelatedElementsEventSubscription, GQLRelatedElementsEventVariables>(
    gql(getRelatedElementsViewEventSubscription),
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
    form: state.form,
    complete: state.complete,
  };
};
