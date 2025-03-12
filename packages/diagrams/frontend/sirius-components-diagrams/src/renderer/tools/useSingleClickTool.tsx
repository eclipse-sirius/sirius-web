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
import { GQLErrorPayload, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useCallback } from 'react';
import { useDialog } from '../../dialog/useDialog';
import { GQLSingleClickOnDiagramElementTool, GQLTool } from '../palette/Palette.types';
import {
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolPayload,
  GQLInvokeSingleClickOnDiagramElementToolSuccessPayload,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLToolVariable,
  UseSingleClickToolValue,
} from './useSingleClickTool.types';

const invokeSingleClickOnDiagramElementToolMutation = gql`
  mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
    invokeSingleClickOnDiagramElementTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
        newSelection {
          entries {
            id
            kind
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

const isErrorPayload = (payload: GQLInvokeSingleClickOnDiagramElementToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isInvokeSingleClickSuccessPayload = (
  payload: GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLInvokeSingleClickOnDiagramElementToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnDiagramElementToolSuccessPayload';

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const useSingleClickTool = (editingContextId: string, diagramId: string): UseSingleClickToolValue => {
  const { addMessages } = useMultiToast();
  const { setSelection } = useSelection();
  const { showDialog } = useDialog();

  const [invokeSingleClickOnDiagramElementTool] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation);

  const invokeSingleClickTool = useCallback(
    async (tool: GQLTool, diagramElementId: string, variables: GQLToolVariable[], x: number, y: number) => {
      if (isSingleClickOnDiagramElementTool(tool)) {
        const { id: toolId } = tool;
        const input: GQLInvokeSingleClickOnDiagramElementToolInput = {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: diagramId,
          diagramElementId,
          toolId,
          startingPositionX: x,
          startingPositionY: y,
          variables,
        };

        const { data } = await invokeSingleClickOnDiagramElementTool({
          variables: { input },
        });
        if (data) {
          const { invokeSingleClickOnDiagramElementTool } = data;
          if (isInvokeSingleClickSuccessPayload(invokeSingleClickOnDiagramElementTool)) {
            const { newSelection } = invokeSingleClickOnDiagramElementTool;
            if (newSelection?.entries.length ?? 0 > 0) {
              setSelection(newSelection);
            }
            addMessages(invokeSingleClickOnDiagramElementTool.messages);
          }
          if (isErrorPayload(invokeSingleClickOnDiagramElementTool)) {
            addMessages(invokeSingleClickOnDiagramElementTool.messages);
          }
        }
      }
    },
    [editingContextId, diagramId, invokeSingleClickOnDiagramElementToolMutation]
  );

  const handleDialogDescription = (
    tool: GQLSingleClickOnDiagramElementTool,
    diagramElementId: string,
    targetObjectId: string,
    variables: GQLToolVariable[],
    x: number,
    y: number
  ) => {
    const onConfirm = () => {
      invokeSingleClickTool(tool, diagramElementId, variables, x, y);
    };
    showDialog(tool.dialogDescriptionId, [{ name: 'targetObjectId', value: targetObjectId }], onConfirm, () => {});
  };

  const invokeTool = (tool: GQLTool, diagramElementId: string, targetObjectId: string, x: number, y: number) => {
    if (isSingleClickOnDiagramElementTool(tool)) {
      if (tool.dialogDescriptionId) {
        handleDialogDescription(tool, diagramElementId, targetObjectId, [], x, y);
      } else {
        invokeSingleClickTool(tool, diagramElementId, [], x, y);
      }
    }
  };

  return { invokeSingleClickTool: invokeTool };
};
