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
import { DiagramContext } from '../../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../../contexts/DiagramContext.types';
import { GQLManageVisibilityAction } from './useManageVisibilityActions.types';
import {
  GQLErrorPayload,
  GQLManageVisibilityInvokeActionData,
  GQLManageVisibilityInvokeActionInput,
  GQLManageVisibilityInvokeActionPayload,
  GQLManageVisibilityInvokeActionVariables,
  GQLSuccessPayload,
  UseManageVisibilityInvokeActionValue,
} from './useManageVisibilityInvokeAction.types';

const invokeActionMutation = gql`
  mutation invokeManageVisibilityAction($input: InvokeManageVisibilityActionInput!) {
    invokeManageVisibilityAction(input: $input) {
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

const isErrorPayload = (payload: GQLManageVisibilityInvokeActionPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLManageVisibilityInvokeActionPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useManageVisibilityInvokeAction = (): UseManageVisibilityInvokeActionValue => {
  const { addMessages, addErrorMessage } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);

  const [invokeActionFrom, { data: invokeActionData, error: invokeActionError }] = useMutation<
    GQLManageVisibilityInvokeActionData,
    GQLManageVisibilityInvokeActionVariables
  >(invokeActionMutation);

  useEffect(() => {
    if (invokeActionError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (invokeActionData) {
      const { invokeManageVisibilityAction } = invokeActionData;
      if (isSuccessPayload(invokeManageVisibilityAction)) {
        addMessages(invokeManageVisibilityAction.messages);
      } else if (isErrorPayload(invokeManageVisibilityAction)) {
        addMessages(invokeManageVisibilityAction.messages);
      }
    }
  }, [invokeActionData, invokeActionError]);

  const invokeAction = (diagramElementId: string, action: GQLManageVisibilityAction) => {
    const { id: actionId } = action;
    const input: GQLManageVisibilityInvokeActionInput = {
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
