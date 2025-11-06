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
import { useImpactAnalysisDialog } from '@eclipse-sirius/sirius-components-impactanalysis';
import { useContext, useEffect, useState } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useDialog } from '../../dialog/useDialog';
import { useInvokeImpactAnalysis } from '../palette/impact-analysis/useDiagramImpactAnalysis';
import { GQLInvokeImpactAnalysisToolVariables } from '../palette/impact-analysis/useDiagramImpactAnalysis.types';
import { GQLSingleClickOnDiagramElementTool, GQLTool } from '../palette/Palette.types';
import {
  GQLInvokeSingleClickOnDiagramElementToolData,
  GQLInvokeSingleClickOnDiagramElementToolInput,
  GQLInvokeSingleClickOnDiagramElementToolPayload,
  GQLInvokeSingleClickOnDiagramElementToolSuccessPayload,
  GQLInvokeSingleClickOnDiagramElementToolVariables,
  GQLToolVariable,
  UseSingleClickToolState,
  UseSingleClickToolValue,
} from './useSingleClickTool.types';

const invokeSingleClickOnDiagramElementToolMutation = gql`
  mutation invokeSingleClickOnDiagramElementTool($input: InvokeSingleClickOnDiagramElementToolInput!) {
    invokeSingleClickOnDiagramElementTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnDiagramElementToolSuccessPayload {
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

const isErrorPayload = (payload: GQLInvokeSingleClickOnDiagramElementToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isInvokeSingleClickSuccessPayload = (
  payload: GQLInvokeSingleClickOnDiagramElementToolPayload
): payload is GQLInvokeSingleClickOnDiagramElementToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnDiagramElementToolSuccessPayload';

const isSingleClickOnDiagramElementTool = (tool: GQLTool): tool is GQLSingleClickOnDiagramElementTool =>
  tool.__typename === 'SingleClickOnDiagramElementTool';

export const useSingleClickTool = (): UseSingleClickToolValue => {
  const { registerPostToolSelection } = useContext<DiagramContextValue>(DiagramContext);
  const { addMessages, addErrorMessage } = useMultiToast();
  const { showDialog } = useDialog();
  const { showImpactAnalysisDialog } = useImpactAnalysisDialog();

  const [invokeSingleClickOnDiagramElementTool, { loading, data, error }] = useMutation<
    GQLInvokeSingleClickOnDiagramElementToolData,
    GQLInvokeSingleClickOnDiagramElementToolVariables
  >(invokeSingleClickOnDiagramElementToolMutation);

  useEffect(() => {
    if (!loading) {
      if (data) {
        const { invokeSingleClickOnDiagramElementTool } = data;
        if (isInvokeSingleClickSuccessPayload(invokeSingleClickOnDiagramElementTool)) {
          const { id, newSelection } = invokeSingleClickOnDiagramElementTool;
          if (newSelection?.entries.length ?? 0 > 0) {
            registerPostToolSelection(id, newSelection);
          }
          addMessages(invokeSingleClickOnDiagramElementTool.messages);
        }
        if (isErrorPayload(invokeSingleClickOnDiagramElementTool)) {
          addMessages(invokeSingleClickOnDiagramElementTool.messages);
        }
      }
      if (error) {
        addErrorMessage('An unexpected error has occurred, please refresh the page');
      }
    }
  }, [loading, data, error]);

  const invokeTool = (
    editingContextId: string,
    diagramId: string,
    tool: GQLSingleClickOnDiagramElementTool,
    diagramElementIds: string[],
    variables: GQLToolVariable[],
    x: number,
    y: number
  ) => {
    const { id: toolId } = tool;
    const input: GQLInvokeSingleClickOnDiagramElementToolInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramElementIds,
      toolId,
      startingPositionX: x,
      startingPositionY: y,
      variables,
    };

    invokeSingleClickOnDiagramElementTool({
      variables: { input },
    });
  };

  const { getImpactAnalysisReport, loading: impactAnalysisLoading, impactAnalysisReport } = useInvokeImpactAnalysis();

  const [state, setState] = useState<UseSingleClickToolState>({
    currentTool: null,
    onToolExecution: () => {},
  });

  useEffect(() => {
    if (state.currentTool && (impactAnalysisLoading || impactAnalysisReport)) {
      showImpactAnalysisDialog(
        impactAnalysisReport,
        impactAnalysisLoading,
        state.currentTool.label,
        state.onToolExecution
      );
    }
  }, [impactAnalysisLoading, impactAnalysisReport, state.currentTool]);

  const invokeGetDiagramAnalysisReport = (
    editingContextId: string,
    representationId: string,
    toolId: string,
    diagramElementIds: string[],
    variables: GQLToolVariable[]
  ) => {
    if (diagramElementIds.length === 1 && diagramElementIds[0]) {
      const getImpactAnalysisVariables: GQLInvokeImpactAnalysisToolVariables = {
        editingContextId,
        representationId,
        toolId,
        diagramElementId: diagramElementIds[0],
        variables,
      };
      getImpactAnalysisReport({ variables: getImpactAnalysisVariables });
    }
  };

  const invokeSingleClickTool = (
    editingContextId: string,
    diagramId: string,
    tool: GQLTool,
    diagramElementIds: string[],
    targetObjectId: string,
    x: number,
    y: number
  ) => {
    if (isSingleClickOnDiagramElementTool(tool)) {
      const executeTool = (variables: GQLToolVariable[]) =>
        invokeTool(editingContextId, diagramId, tool, diagramElementIds, variables, x, y);

      let executeProcess: (variables: GQLToolVariable[]) => void = executeTool;
      if (tool.withImpactAnalysis) {
        const executeToolWithImpactAnalysis = (variables: GQLToolVariable[]) => {
          setState((prevState) => ({ ...prevState, currentTool: tool, onToolExecution: () => executeTool(variables) }));
          invokeGetDiagramAnalysisReport(editingContextId, diagramId, tool.id, diagramElementIds, variables);
        };
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
