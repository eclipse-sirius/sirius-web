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
import { GQLErrorPayload, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import {
  GQLInvokeSingleClickOnTwoDiagramElementsToolData,
  GQLInvokeSingleClickOnTwoDiagramElementsToolInput,
  GQLInvokeSingleClickOnTwoDiagramElementsToolPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolVariables,
  UseSingleClickOnTwoDiagramElementToolValue,
} from './useSingleClickOnTwoDiagramElementTool.types';

export const invokeSingleClickOnTwoDiagramElementsToolMutation = gql`
  mutation invokeSingleClickOnTwoDiagramElementsTool($input: InvokeSingleClickOnTwoDiagramElementsToolInput!) {
    invokeSingleClickOnTwoDiagramElementsTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
        id
        newSelection {
          entries {
            id
          }
        }
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

const isSuccessPayload = (
  payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload
): payload is GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload';

const isErrorPayload = (payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useSingleClickOnTwoDiagramElementTool = (): UseSingleClickOnTwoDiagramElementToolValue => {
  const [invokeTool, { loading, data, error }] = useMutation<
    GQLInvokeSingleClickOnTwoDiagramElementsToolData,
    GQLInvokeSingleClickOnTwoDiagramElementsToolVariables
  >(invokeSingleClickOnTwoDiagramElementsToolMutation);
  const { registerPostToolSelection } = useContext<DiagramContextValue>(DiagramContext);
  const { addMessages, addErrorMessage } = useMultiToast();

  const invokeSingleClickOnTwoDiagramElementsTool = (input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput) =>
    invokeTool({ variables: { input } });

  useEffect(() => {
    if (!loading) {
      if (data) {
        const { invokeSingleClickOnTwoDiagramElementsTool } = data;
        if (isSuccessPayload(invokeSingleClickOnTwoDiagramElementsTool)) {
          const { id, newSelection } = invokeSingleClickOnTwoDiagramElementsTool;
          if (newSelection?.entries.length ?? 0 > 0) {
            registerPostToolSelection(id, newSelection);
          }
          addMessages(invokeSingleClickOnTwoDiagramElementsTool.messages);
        }
        if (isErrorPayload(invokeSingleClickOnTwoDiagramElementsTool)) {
          addMessages(invokeSingleClickOnTwoDiagramElementsTool.messages);
        }
      }
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
    }
  }, [loading, data, error]);

  return {
    invokeSingleClickOnTwoDiagramElementsTool,
    loading: loading,
    data: data || null,
  };
};
