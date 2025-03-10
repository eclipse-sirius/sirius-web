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
import { useDialog } from '../../dialog/useDialog';
import { useImpactAnalysisDialog } from '../palette/impact-analysis/useImpactAnalysisDialog';
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

export const useSingleClickTool = (): UseSingleClickToolValue => {
  const { addMessages, addErrorMessage } = useMultiToast();
  const { setSelection } = useSelection();
  const { showDialog } = useDialog();
  const { showImpactAnalysisDialog } = useImpactAnalysisDialog();

  const [invokeSingleClickOnDiagramElementTool, { loading, data }] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation, {
    onCompleted(data) {
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
    },
    onError: () => addErrorMessage('An unexpected error has occurred, please refresh the page'),
  });

  const invokeTool = (
    editingContextId: string,
    diagramId: string,
    tool: GQLSingleClickOnDiagramElementTool,
    diagramElementId: string,
    variables: GQLToolVariable[],
    x: number,
    y: number
  ) => {
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

    invokeSingleClickOnDiagramElementTool({
      variables: { input },
    });
  };

  const invokeSingleClickTool = (
    editingContextId: string,
    diagramId: string,
    tool: GQLTool,
    diagramElementId: string,
    targetObjectId: string,
    x: number,
    y: number
  ) => {
    if (isSingleClickOnDiagramElementTool(tool)) {
      const executeTool = (variables: GQLToolVariable[]) =>
        invokeTool(editingContextId, diagramId, tool, diagramElementId, variables, x, y);

      let executeProcess: (variables: GQLToolVariable[]) => void = executeTool;
      if (tool.withImpactAnalysis) {
        const executeToolWithImpactAnalysis = (variables: GQLToolVariable[]) =>
          showImpactAnalysisDialog(editingContextId, diagramId, tool.id, tool.label, diagramElementId, variables, () =>
            executeTool(variables)
          );
        executeProcess = executeToolWithImpactAnalysis;

        if (tool.dialogDescriptionId) {
          const executeToolWithImpactAnalysisAndDialog = () =>
            showDialog(
              tool.dialogDescriptionId,
              [{ name: 'targetObjectId', value: targetObjectId }],
              executeToolWithImpactAnalysis,
              () => {}
            );
          executeProcess = executeToolWithImpactAnalysisAndDialog;
        }
      } else if (tool.dialogDescriptionId) {
        const executeToolWithDialog = () =>
          showDialog(
            tool.dialogDescriptionId,
            [{ name: 'targetObjectId', value: targetObjectId }],
            executeTool,
            () => {}
          );
        executeProcess = executeToolWithDialog;
      }

      executeProcess([]);
    }
  };

  return { invokeSingleClickTool, loading, data: data ?? null };
};
