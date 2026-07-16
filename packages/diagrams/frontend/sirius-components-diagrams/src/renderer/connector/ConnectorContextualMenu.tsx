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
import { memo, useContext, useEffect } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ConnectorContextualMenuProps, GQLTool } from './ConnectorContextualMenu.types';
import { ConnectorPaletteContext } from './context/ConnectorPaletteContext';
import { ConnectorPaletteContextValue } from './context/ConnectorPaletteContext.types';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { useSingleClickOnTwoDiagramElementTool } from './useSingleClickOnTwoDiagramElementTool';
import { useTemporaryEdge } from './useTemporaryEdge';

const ConnectorContextualMenuComponent = memo(({}: ConnectorContextualMenuProps) => {
  const {
    isOpened,
    x: paletteX,
    y: paletteY,
    sourceDiagramElementId,
    targetDiagramElementId,
    hideConnectorPalette,
  } = useContext<ConnectorPaletteContextValue>(ConnectorPaletteContext);
  const { addTempConnectionLine, removeTempConnectionLine } = useTemporaryEdge();
  const { addMessages } = useMultiToast();
  const { screenToFlowPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { invokeConnectorTool, data: invokeSingleClickOnTwoDiagramElementToolCalled } =
    useSingleClickOnTwoDiagramElementTool();

  const { connectorTools, loading } = useConnectorPaletteContents(
    sourceDiagramElementId || '',
    targetDiagramElementId || ''
  );

  const connectionTarget: HTMLElement | null = targetDiagramElementId
    ? document.querySelector(`[data-id="${targetDiagramElementId}"]`)
    : null;

  useEffect(() => {
    if (connectorTools.length > 1 && !!sourceDiagramElementId && !!targetDiagramElementId) {
      addTempConnectionLine(sourceDiagramElementId, targetDiagramElementId);
    }
  }, [sourceDiagramElementId, targetDiagramElementId, connectorTools.length]);

  useEffect(() => {
    if (!loading && sourceDiagramElementId && targetDiagramElementId && connectorTools.length === 0) {
      addMessages([{ body: 'No edge found between source and target selected', level: 'WARNING' }]);
    }
  }, [loading, connectorTools, sourceDiagramElementId, targetDiagramElementId, connectorTools.length]);

  useEffect(() => {
    return () => removeTempConnectionLine();
  }, []);

  useEffect(() => {
    if (!invokeSingleClickOnTwoDiagramElementToolCalled && connectorTools.length === 1 && connectorTools[0]) {
      invokeTool(connectorTools[0]);
    }
  }, [connectorTools.length]);

  const invokeTool = (tool: GQLTool) => {
    if (!!sourceDiagramElementId && !!targetDiagramElementId && !!paletteX && !!paletteY) {
      const { x: cursorPositionX, y: cursorPositionY } = screenToFlowPosition({ x: paletteX, y: paletteY });
      invokeConnectorTool(tool, sourceDiagramElementId, targetDiagramElementId, cursorPositionX, cursorPositionY);
    }
  };

  if (!connectorTools || connectorTools.length <= 1) {
    return null;
  }

  return (
    <Menu
      open={!!isOpened}
      onClose={hideConnectorPalette}
      anchorEl={connectionTarget}
      anchorReference="anchorPosition"
      data-testid="connectorContextualMenu"
      anchorPosition={{ left: paletteX || 0, top: paletteY || 0 }}>
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
  const { isOpened } = useContext<ConnectorPaletteContextValue>(ConnectorPaletteContext);
  return !!isOpened ? <ConnectorContextualMenuComponent /> : null;
});
