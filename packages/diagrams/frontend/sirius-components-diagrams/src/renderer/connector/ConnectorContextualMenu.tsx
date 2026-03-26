/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { IconOverlay, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { memo, useEffect } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ConnectorContextualMenuProps, GQLTool } from './ConnectorContextualMenu.types';
import { useConnector } from './useConnector';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { useSingleClickOnTwoDiagramElementTool } from './useSingleClickOnTwoDiagramElementTool';
import { useTemporaryEdge } from './useTemporaryEdge';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const { connection, position, onConnectorContextualMenuClose } = useConnector();
  const { addTempConnectionLine, removeTempConnectionLine } = useTemporaryEdge();
  const { addMessages } = useMultiToast();
  const { screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { invokeConnectorTool, data: invokeSingleClickOnTwoDiagramElementToolCalled } =
    useSingleClickOnTwoDiagramElementTool();

  const connectionSource: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.source}"]`)
    : null;

  const connectionTarget: HTMLElement | null = connection
    ? document.querySelector(`[data-id="${connection.target}"]`)
    : null;

  const sourceDiagramElementId = connectionSource?.dataset.id ?? '';
  const targetDiagramElementId = connectionTarget?.dataset.id ?? '';

  const { connectorTools, loading } = useConnectorPaletteContents(sourceDiagramElementId, targetDiagramElementId);

  useEffect(() => {
    if (connectorTools.length > 1) {
      addTempConnectionLine(sourceDiagramElementId, targetDiagramElementId);
    }
  }, [connection, connectorTools.length]);

  useEffect(() => {
    if (!loading && connection && connectorTools.length === 0) {
      addMessages([{ body: 'No edge found between source and target selected', level: 'WARNING' }]);
    }
  }, [loading, connectorTools, connection, connectorTools.length]);

  useEffect(() => {
    return () => removeTempConnectionLine();
  }, []);

  useEffect(() => {
    if (!invokeSingleClickOnTwoDiagramElementToolCalled && connectorTools.length === 1 && connectorTools[0]) {
      invokeTool(connectorTools[0]);
    }
  }, [connectorTools.length]);

  const invokeTool = (tool: GQLTool) => {
    const { x: cursorPositionX, y: cursorPositionY } = screenToFlowPosition({ x: position.x, y: position.y });
    invokeConnectorTool(tool, sourceDiagramElementId, targetDiagramElementId, cursorPositionX, cursorPositionY);
  };

  if (!connectorTools || connectorTools.length <= 1) {
    return null;
  }

  return (
    <Menu
      open={!!connection}
      onClose={onConnectorContextualMenuClose}
      anchorEl={connectionTarget}
      anchorReference="anchorPosition"
      data-testid="connectorContextualMenu"
      anchorPosition={{ left: position?.x || 0, top: position?.y || 0 }}>
      {connectorTools.map((tool) => (
        <MenuItem key={tool.id} onClick={() => invokeTool(tool)} data-testid={`connectorContextualMenu-${tool.label}`}>
          <ListItemIcon>
            <IconOverlay iconURLs={tool.iconURL} alt={tool.label} title={tool.label} />
          </ListItemIcon>
          <Typography>{tool.label}</Typography>
        </MenuItem>
      ))}
    </Menu>
  );
});

export const ConnectorContextualMenu = memo(({}: ConnectorContextualMenuProps) => {
  const { connection } = useConnector();
  return !!connection ? <ConnectorContextualMenuComponent /> : null;
});
