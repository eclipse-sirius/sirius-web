/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { IconOverlay, useMultiToast, useSelection } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { Edge, Node, useReactFlow, useStoreApi, XYPosition } from '@xyflow/react';
import { memo, useContext, useEffect, useMemo } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramDialogVariable } from '../../dialog/DialogContextExtensionPoints.types';
import { useDialog } from '../../dialog/useDialog';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isCursorNearCenterOfTheNode } from '../edge/EdgeLayout';
import {
  ConnectorContextualMenuProps,
  GQLErrorPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolData,
  GQLInvokeSingleClickOnTwoDiagramElementsToolInput,
  GQLInvokeSingleClickOnTwoDiagramElementsToolPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload,
  GQLInvokeSingleClickOnTwoDiagramElementsToolVariables,
  GQLToolVariable,
} from './ConnectorContextualMenu.types';
import { useConnector } from './useConnector';
import { GQLConnectorTool } from './useConnector.types';

export const invokeSingleClickOnTwoDiagramElementsToolMutation = gql`
  mutation invokeSingleClickOnTwoDiagramElementsTool($input: InvokeSingleClickOnTwoDiagramElementsToolInput!) {
    invokeSingleClickOnTwoDiagramElementsTool(input: $input) {
      __typename
      ... on InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload {
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

const isErrorPayload = (payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

const isSuccessPayload = (
  payload: GQLInvokeSingleClickOnTwoDiagramElementsToolPayload
): payload is GQLInvokeSingleClickOnTwoDiagramElementsToolSuccessPayload =>
  payload.__typename === 'InvokeSingleClickOnTwoDiagramElementsToolSuccessPayload';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const {
    connection,
    position,
    toolCandidates,
    onConnectorContextualMenuClose,
    addTempConnectionLine,
    removeTempConnectionLine,
  } = useConnector();
  const { addMessages, addErrorMessage } = useMultiToast();
  const { setSelection } = useSelection();

  const { showDialog, isOpened } = useDialog();

  const connectionSource: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.source}"]`)
    : null;

  const connectionTarget: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.target}"]`)
    : null;

  const sourceDiagramElementId = connectionSource?.dataset.id ?? '';
  const targetDiagramElementId = connectionTarget?.dataset.id ?? '';

  const sourceNode = nodeLookup.get(sourceDiagramElementId);
  const targetNode = nodeLookup.get(targetDiagramElementId);

  const invokeOpenSelectionDialog = (tool: GQLConnectorTool) => {
    const onConfirm = (variables: GQLToolVariable[]) => {
      invokeToolMutation(tool, variables);
    };

    const onClose = () => {
      onShouldConnectorContextualMenuClose();
    };

    if (sourceNode && targetNode) {
      const variables: DiagramDialogVariable[] = [
        { name: 'targetObjectId', value: sourceNode.data.targetObjectId },
        { name: 'sourceDiagramElementTargetObjectId', value: sourceNode.data.targetObjectId },
        { name: 'targetDiagramElementTargetObjectId', value: targetNode.data.targetObjectId },
      ];
      showDialog(tool.dialogDescriptionId, variables, onConfirm, onClose);
    }
  };

  const [
    invokeSingleClickOnTwoDiagramElementsTool,
    {
      data: invokeSingleClickOnTwoDiagramElementToolData,
      error: invokeSingleClickOnTwoDiagramElementToolError,
      called: invokeSingleClickOnTwoDiagramElementToolCalled,
      reset,
    },
  ] = useMutation<
    GQLInvokeSingleClickOnTwoDiagramElementsToolData,
    GQLInvokeSingleClickOnTwoDiagramElementsToolVariables
  >(invokeSingleClickOnTwoDiagramElementsToolMutation);

  const invokeTool = (tool: GQLConnectorTool) => {
    if (tool.dialogDescriptionId) {
      if (!isOpened) {
        invokeOpenSelectionDialog(tool);
      }
    } else {
      invokeToolMutation(tool, []);
    }
  };

  const invokeToolMutation = (tool: GQLConnectorTool, variables: GQLToolVariable[]) => {
    let targetHandlePosition: XYPosition = { x: 0, y: 0 };
    if (position) {
      targetHandlePosition = screenToFlowPosition({ x: position.x, y: position.y });
    }

    if (targetNode && position) {
      const isNearCenter = isCursorNearCenterOfTheNode(targetNode, {
        x: targetHandlePosition.x,
        y: targetHandlePosition.y,
      });

      if (isNearCenter) {
        targetHandlePosition = { x: 0, y: 0 };
      }
    }

    const input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: diagramId,
      diagramSourceElementId: sourceDiagramElementId,
      diagramTargetElementId: targetDiagramElementId,
      toolId: tool.id,
      sourcePositionX: 0,
      sourcePositionY: 0,
      targetPositionX: targetHandlePosition.x,
      targetPositionY: targetHandlePosition.y,
      variables,
    };
    invokeSingleClickOnTwoDiagramElementsTool({ variables: { input } });
  };

  const onShouldConnectorContextualMenuClose = () => {
    onConnectorContextualMenuClose();
    reset();
  };

  useEffect(() => {
    if (invokeSingleClickOnTwoDiagramElementToolData) {
      const payload = invokeSingleClickOnTwoDiagramElementToolData.invokeSingleClickOnTwoDiagramElementsTool;
      if (isErrorPayload(payload)) {
        addMessages(payload.messages);
      }
      if (isSuccessPayload(payload)) {
        const { newSelection } = payload;
        if (newSelection?.entries.length ?? 0 > 0) {
          setSelection(newSelection);
        }
        addMessages(payload.messages);
        onShouldConnectorContextualMenuClose();
      }
    }
    if (invokeSingleClickOnTwoDiagramElementToolError?.message) {
      addErrorMessage(invokeSingleClickOnTwoDiagramElementToolError.message);
    }
  }, [invokeSingleClickOnTwoDiagramElementToolData, invokeSingleClickOnTwoDiagramElementToolError]);

  const connectorTools: GQLConnectorTool[] = useMemo(() => {
    const result: GQLConnectorTool[] = [];
    toolCandidates.forEach((tool) => {
      if (targetNode && tool.candidatesDescriptionId.includes(targetNode.data.descriptionId)) {
        result.push(tool);
      }
    });
    return result;
  }, [targetDiagramElementId]);

  useEffect(() => {
    if (connectorTools.length > 1) {
      addTempConnectionLine();
    }
  }, [connection, connectorTools.length]);

  useEffect(() => {
    return () => removeTempConnectionLine();
  }, []);

  useEffect(() => {
    if (!invokeSingleClickOnTwoDiagramElementToolCalled && connectorTools.length === 1 && connectorTools[0]) {
      invokeTool(connectorTools[0]);
    }
  }, [connectorTools]);

  return connectorTools.length > 1 ? (
    <Menu
      open={!!connection}
      onClose={onShouldConnectorContextualMenuClose}
      anchorEl={connectionTarget}
      anchorReference="anchorPosition"
      data-testid="connectorContextualMenu"
      anchorPosition={{ left: position?.x || 0, top: position?.y || 0 }}>
      {connectorTools.map((tool) => (
        <MenuItem key={tool.id} onClick={() => invokeTool(tool)} data-testid={`connectorContextualMenu-${tool.label}`}>
          <ListItemIcon>
            <IconOverlay iconURL={tool.iconURL} alt={tool.label} title={tool.label} />
          </ListItemIcon>
          <Typography>{tool.label}</Typography>
        </MenuItem>
      ))}
    </Menu>
  ) : null;
});

export const ConnectorContextualMenu = memo(({}: ConnectorContextualMenuProps) => {
  const { isConnectionInProgress } = useConnector();
  return isConnectionInProgress() ? <ConnectorContextualMenuComponent /> : null;
});
