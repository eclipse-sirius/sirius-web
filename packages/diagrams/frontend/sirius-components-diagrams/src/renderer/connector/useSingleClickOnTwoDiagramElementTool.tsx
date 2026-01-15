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
import { GQLErrorPayload, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useStoreApi, useViewport } from '@xyflow/react';
import { useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramDialogVariable } from '../../dialog/DialogContextExtensionPoints.types';
import { useDialog } from '../../dialog/useDialog';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isCursorNearCenterOfTheNode } from '../edge/EdgeLayout';
import { GQLTool, GQLToolVariable } from '../palette/Palette.types';
import { GQLSingleClickOnTwoDiagramElementsTool } from './useConnector.types';
import { useConnectorPalette } from './useConnectorPalette';
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

const isSingleClickOnTwoDiagramElementsTool = (tool: GQLTool): tool is GQLSingleClickOnTwoDiagramElementsTool =>
  tool.__typename === 'SingleClickOnTwoDiagramElementsTool';

export const useSingleClickOnTwoDiagramElementTool = (): UseSingleClickOnTwoDiagramElementToolValue => {
  const [invokeTool, { loading, data, error }] = useMutation<
    GQLInvokeSingleClickOnTwoDiagramElementsToolData,
    GQLInvokeSingleClickOnTwoDiagramElementsToolVariables
  >(invokeSingleClickOnTwoDiagramElementsToolMutation);
  const { registerPostToolSelection } = useContext<DiagramContextValue>(DiagramContext);
  const { addMessages, addErrorMessage } = useMultiToast();
  const { showDialog, isOpened } = useDialog();
  const {
    hideConnectorPalette,
    sourceDiagramElementId,
    targetDiagramElementId,
    x: toolX,
    y: toolY,
  } = useConnectorPalette();
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
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

  const invokeConnectorTool = (tool: GQLTool) => {
    if (isSingleClickOnTwoDiagramElementsTool(tool)) {
      if (tool.dialogDescriptionId) {
        if (!isOpened) {
          invokeOpenSelectionDialog(tool);
        }
      } else {
        invokeToolMutation(tool, []);
      }
    }
  };

  const invokeOpenSelectionDialog = (tool: GQLSingleClickOnTwoDiagramElementsTool) => {
    const onConfirm = (variables: GQLToolVariable[]) => {
      invokeToolMutation(tool, variables);
    };

    const { nodeLookup } = store.getState();

    const sourceNode = nodeLookup.get(sourceDiagramElementId || '');
    const targetNode = nodeLookup.get(targetDiagramElementId || '');
    if (sourceNode && targetNode) {
      const variables: DiagramDialogVariable[] = [
        { name: 'targetObjectId', value: sourceNode.data.targetObjectId },
        { name: 'sourceDiagramElementTargetObjectId', value: sourceNode.data.targetObjectId },
        { name: 'targetDiagramElementTargetObjectId', value: targetNode.data.targetObjectId },
      ];
      showDialog(tool.dialogDescriptionId, variables, onConfirm, hideConnectorPalette);
    }
  };

  const invokeToolMutation = (tool: GQLTool, variables: GQLToolVariable[]) => {
    let x: number = toolX || 0;
    let y: number = toolY || 0;
    if (viewportZoom !== 0 && x && y) {
      x = (x - viewportX) / viewportZoom;
      y = (y - viewportY) / viewportZoom;
    }
    const target = store.getState().nodeLookup.get(targetDiagramElementId || '');
    if (target) {
      const isNearCenter = isCursorNearCenterOfTheNode(target, {
        x,
        y,
      });

      if (isNearCenter) {
        x = 0;
        y = 0;
      }
    }
    if (sourceDiagramElementId && targetDiagramElementId) {
      const input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: diagramId,
        diagramSourceElementId: sourceDiagramElementId,
        diagramTargetElementId: targetDiagramElementId,
        toolId: tool.id,
        sourcePositionX: 0,
        sourcePositionY: 0,
        targetPositionX: x,
        targetPositionY: y,
        variables,
      };
      invokeSingleClickOnTwoDiagramElementsTool(input);
    }
  };

  return {
    invokeConnectorTool,
    loading: loading,
    data: data || null,
  };
};
