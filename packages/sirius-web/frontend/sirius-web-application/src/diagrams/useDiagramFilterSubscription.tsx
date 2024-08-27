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
  GQLDiagramFilterEventInput,
  GQLDiagramFilterEventPayload,
  GQLDiagramFilterEventSubscription,
  GQLDiagramFilterEventVariables,
  GQLFormRefreshedEventPayload,
  UseDiagramFilterSubscriptionState,
  UseDiagramFilterSubscriptionValue,
} from './useDiagramFilterSubscription.types';

const isFormRefreshedEventPayload = (payload: GQLDiagramFilterEventPayload): payload is GQLFormRefreshedEventPayload =>
  payload.__typename === 'FormRefreshedEventPayload';

export const getDiagramFilterEventSubscription = `
  subscription diagramFilterEvent($input: DiagramFilterEventInput!) {
    diagramFilterEvent(input: $input) {
      __typename
      ... on FormRefreshedEventPayload {
        ...formRefreshedEventPayloadFragment
      }
    }
  }
  ${formRefreshedEventPayloadFragment}
  `;

export const useDiagramFilterSubscription = (
  editingContextId: string,
  objectIds: string[],
  skip?: boolean
): UseDiagramFilterSubscriptionValue => {
  const [state, setState] = useState<UseDiagramFilterSubscriptionState>({
    id: crypto.randomUUID(),
    form: null,
    complete: false,
  });

  const input: GQLDiagramFilterEventInput = {
    id: state.id,
    editingContextId,
    objectIds,
  };

  const variables: GQLDiagramFilterEventVariables = { input };

  const onData = ({ data }: OnDataOptions<GQLDiagramFilterEventSubscription>) => {
    const { data: gqlDiagramFilterEventSubscription } = data;
    if (gqlDiagramFilterEventSubscription) {
      const { diagramFilterEvent: payload } = gqlDiagramFilterEventSubscription;
      if (isFormRefreshedEventPayload(payload)) {
        const { form } = payload;
        setState((prevState) => ({ ...prevState, form, complete: false }));
      }
    }
  };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const { error, loading } = useSubscription<GQLDiagramFilterEventSubscription, GQLDiagramFilterEventVariables>(
    gql(getDiagramFilterEventSubscription),
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
