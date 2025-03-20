/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { GQLAction } from './useActions.types';
import {
  GQLErrorPayload,
  GQLInvokeActionData,
  GQLInvokeActionInput,
  GQLInvokeActionPayload,
  GQLInvokeActionVariables,
  GQLSuccessPayload,
  UseInvokeActionValue,
} from './useInvokeAction.types';

const invokeActionMutation = gql`
  mutation invokeAction($input: InvokeActionInput!) {
    invokeAction(input: $input) {
      __typename
      ... on SuccessPayload {
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

const isErrorPayload = (payload: GQLInvokeActionPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLInvokeActionPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useInvokeAction = (diagramElementId: string): UseInvokeActionValue => {
  const { addMessages, addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [invokeActionFrom, { data: invokeActionData, error: invokeActionError }] = useMutation<
    GQLInvokeActionData,
    GQLInvokeActionVariables
  >(invokeActionMutation);

  useEffect(() => {
    if (invokeActionError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (invokeActionData) {
      const { invokeAction } = invokeActionData;
      if (isSuccessPayload(invokeAction)) {
        addMessages(invokeAction.messages);
      }
      if (isErrorPayload(invokeAction)) {
        addMessages(invokeAction.messages);
      }
    }
  }, [invokeActionData, invokeActionError]);

  const invokeAction = (action: GQLAction) => {
    const { id: actionId } = action;
    const input: GQLInvokeActionInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramElementId,
      actionId,
    };
    invokeActionFrom({
      variables: { input },
    });
  };

  return { invokeAction };
};
