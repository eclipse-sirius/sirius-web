/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useContext } from 'react';
import { DiagramContext } from '../../../contexts/DiagramContext';
import { DiagramContextValue } from '../../../contexts/DiagramContext.types';
import { useDialog } from '../../../dialog/useDialog';
import { GQLSingleClickOnDiagramElementTool, GQLTool } from '../Palette.types';
import {
  GQLErrorPayload,
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolPayload,
  GQLInvokeSingleClickOnDiagramElementToolSuccessPayload,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLToolVariable,
  InvokeSingleClickTool,
  InvokeSingleClickToolProps,
} from './useSingleClickOnDiagramElementTool.types';

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

const isInvokeSingleClickSuccessPayload = (
  payload: GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLInvokeSingleClickOnDiagramElementToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnDiagramElementToolSuccessPayload';

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

const isErrorPayload = (payload: GQLInvokeSingleClickOnDiagramElementToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useSingleClickOnDiagramElementTool = ({
  x,
  y,
  diagramElementId,
  targetObjectId,
}: InvokeSingleClickToolProps): InvokeSingleClickTool => {
  const { addMessages } = useMultiToast();
  const { diagramId, editingContextId } = useContext<DiagramContextValue>(DiagramContext);
  const { setSelection } = useSelection();
  const { showDialog } = useDialog();

  const [invokeSingleClickOnDiagramElementTool] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation);

  const executeTool = useCallback(
    async (tool: GQLTool, variables: GQLToolVariable[]) => {
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
    [
      x,
      y,
      editingContextId,
      diagramId,
      diagramElementId,
      invokeSingleClickOnDiagramElementToolMutation,
      isSingleClickOnDiagramElementTool,
    ]
  );

  const handleDialogDescription = (tool: GQLSingleClickOnDiagramElementTool) => {
    const onConfirm = (variables: GQLToolVariable[]) => executeTool(tool, variables);
    showDialog(tool.dialogDescriptionId, [{ name: 'targetObjectId', value: targetObjectId }], onConfirm, () => {});
  };

  const invokeSingleClickTool = (tool: GQLTool, variables: GQLToolVariable[]) => {
    if (isSingleClickOnDiagramElementTool(tool)) {
      if (tool.dialogDescriptionId) {
        handleDialogDescription(tool);
      } else {
        executeTool(tool, variables);
      }
    }
  };

  return { invokeSingleClickTool };
};
