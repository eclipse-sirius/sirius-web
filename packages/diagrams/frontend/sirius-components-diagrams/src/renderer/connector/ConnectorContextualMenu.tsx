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

import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import { GQLToolVariable } from '@eclipse-sirius/sirius-components-palette';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { Edge, Node, useReactFlow, XYPosition } from '@xyflow/react';
import { memo, useContext, useEffect, useMemo } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramDialogVariable } from '../../dialog/DialogContextExtensionPoints.types';
import { useDialog } from '../../dialog/useDialog';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { isCursorNearCenterOfTheNode } from '../edge/EdgeLayout';
import { ConnectorContextualMenuProps } from './ConnectorContextualMenu.types';
import { GQLConnectorTool } from './useConnector.types';
import { useConnectorContext } from './useConnectorContext';
import { useSingleClickOnTwoDiagramElementTool } from './useSingleClickOnTwoDiagramElementTool';
import { GQLInvokeSingleClickOnTwoDiagramElementsToolInput } from './useSingleClickOnTwoDiagramElementTool.types';
import { useTemporaryEdge } from './useTemporaryEdge';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const { editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { showDialog, isOpened } = useDialog();
  const { addTempConnectionLine, removeTempConnectionLine } = useTemporaryEdge();
  const { screenToFlowPosition, getInternalNode, getEdge } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { invokeSingleClickOnTwoDiagramElementsTool, data: singleClickOnTwoDiagramElementsToolData } =
    useSingleClickOnTwoDiagramElementTool();

  const { connection, position, toolCandidates, setConnection } = useConnectorContext();

  const connectionSource: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.source}"]`)
    : null;

  const connectionTarget: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.target}"]`)
    : null;

  const sourceDiagramElementId = connectionSource?.dataset.id ?? '';
  const targetDiagramElementId = connectionTarget?.dataset.id ?? '';

  const sourceNode = getInternalNode(connection?.source || '');
  const targetNode = getInternalNode(connection?.target || '');

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
    invokeSingleClickOnTwoDiagramElementsTool(input);
  };

  const onShouldConnectorContextualMenuClose = () => setConnection(null);

  const connectorTools: GQLConnectorTool[] = useMemo(() => {
    const result: GQLConnectorTool[] = [];
    const candidateTargetDescriptionId =
      getEdge(targetDiagramElementId)?.data?.descriptionId || targetNode?.data.descriptionId || '';
    toolCandidates.forEach((tool) => {
      if (tool.candidateDescriptionIds.includes(candidateTargetDescriptionId)) {
        result.push(tool);
      }
    });
    return result;
  }, [targetDiagramElementId]);

  useEffect(() => {
    if (connectorTools.length === 1 && connectorTools[0]) {
      invokeTool(connectorTools[0]);
    } else if (connection && connectorTools.length > 1) {
      addTempConnectionLine(connection);
    }
    return () => removeTempConnectionLine();
  }, [connection, connectorTools.length]);

  useEffect(() => {
    if (singleClickOnTwoDiagramElementsToolData) {
      setConnection(null);
    }
  }, [singleClickOnTwoDiagramElementsToolData]);

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
  const { connection, toolCandidates } = useConnectorContext();
  return toolCandidates.length > 0 && connection?.target ? <ConnectorContextualMenuComponent /> : null;
});
