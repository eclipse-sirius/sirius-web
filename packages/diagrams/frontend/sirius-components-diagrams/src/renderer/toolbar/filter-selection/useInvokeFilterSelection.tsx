/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import {
  GQLErrorPayload,
  GQLInvokeFilterSelectionData,
  GQLInvokeFilterSelectionInput,
  GQLInvokeFilterSelectionPayload,
  GQLInvokeFilterSelectionSuccessPayload,
  GQLInvokeFilterSelectionVariables,
  UseInvokeFilterSelectionValue,
} from './useInvokeFilterSelection.types';

const invokeFilterSelectionMutation = gql`
  mutation invokeFilterSelection($input: InvokeFilterSelectionInput!) {
    invokeFilterSelection(input: $input) {
      __typename
      ... on InvokeFilterSelectionSuccessPayload {
        id
        newSelection
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLInvokeFilterSelectionPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (
  payload: GQLInvokeFilterSelectionPayload
): payload is GQLInvokeFilterSelectionSuccessPayload => payload.__typename === 'InvokeFilterSelectionSuccessPayload';

export const useInvokeFilterSelection = (): UseInvokeFilterSelectionValue => {
  const { addMessages, addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [invokeFilterSelectionFrom, { data: invokeFilterSelectionData, error: invokeFilterSelectionError }] =
    useMutation<GQLInvokeFilterSelectionData, GQLInvokeFilterSelectionVariables>(invokeFilterSelectionMutation);

  useEffect(() => {
    if (invokeFilterSelectionError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (invokeFilterSelectionData) {
      const { invokeFilterSelection } = invokeFilterSelectionData;
      if (isSuccessPayload(invokeFilterSelection)) {
        addMessages(invokeFilterSelection.messages);
      }
      if (isErrorPayload(invokeFilterSelection)) {
        addMessages(invokeFilterSelection.messages);
      }
    }
  }, [invokeFilterSelectionData, invokeFilterSelectionError]);

  const invokeFilterSelection = (diagramElementIds: string[], filterSelectionId: string) => {
    const input: GQLInvokeFilterSelectionInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramElementIds,
      filterSelectionId,
    };
    invokeFilterSelectionFrom({
      variables: { input },
    });
  };

  return {
    invokeFilterSelection,
    invokeFilterSelectionData:
      !!invokeFilterSelectionData && isSuccessPayload(invokeFilterSelectionData.invokeFilterSelection)
        ? invokeFilterSelectionData.invokeFilterSelection
        : null,
  };
};
