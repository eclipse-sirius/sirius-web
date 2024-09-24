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
  GQLDiagramFilterEventSubscription,
  GQLDiagramFilterEventVariables,
  UseDiagramFilterSubscriptionState,
  UseDiagramFilterSubscriptionValue,
} from './useDiagramFilterSubscription.types';

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
    complete: false,
  });

  const input: GQLDiagramFilterEventInput = {
    id: state.id,
    editingContextId,
    representationId: `diagramFilter://?objectIds=[${objectIds.map(encodeURIComponent).join(',')}]`,
  };

  const variables: GQLDiagramFilterEventVariables = { input };

  const onComplete = () => setState((prevState) => ({ ...prevState, complete: true }));

  const onData = ({}: OnDataOptions<GQLDiagramFilterEventSubscription>) =>
    setState((prevState) => ({ ...prevState, complete: false }));

  const { data, error, loading } = useSubscription<GQLDiagramFilterEventSubscription, GQLDiagramFilterEventVariables>(
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
    payload: data?.diagramFilterEvent ?? null,
    complete: state.complete,
  };
};
