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

import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@mui/material/ListItemIcon';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Typography from '@mui/material/Typography';
import { memo, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { GQLTool } from './../palette/Palette.types';
import { ConnectorPaletteProps } from './ConnectorPalette.types';
import { useConnectorPalette } from './context/useConnectorPalette';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { UseConnectorPaletteContentValue } from './useConnectorPaletteContents.types';
import { useTemporaryEdge } from './useTemporaryEdge';

const getToolsCount = (connectorTools: GQLTool[]): number => connectorTools.length;

export const ConnectorPalette = memo(({}: ConnectorPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const {
    isOpened,
    x: paletteX,
    y: paletteY,
    sourceDiagramElementId,
    targetDiagramElementId,
    hideConnectorPalette,
  } = useConnectorPalette();

  const { invokeConnectorTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);
  const { addTempConnectionLine, removeTempConnectionLine } = useTemporaryEdge();

  const { connectorTools }: UseConnectorPaletteContentValue = useConnectorPaletteContents(
    sourceDiagramElementId || '',
    targetDiagramElementId || ''
  );
  const toolsCount = connectorTools ? getToolsCount(connectorTools) : 0;

  const onClose = () => {
    hideConnectorPalette();
    removeTempConnectionLine();
  };

  const onToolClick = (tool: GQLTool) => {
    invokeConnectorTool(tool);
    hideConnectorPalette();
    removeTempConnectionLine();
  };

  useEffect(() => {
    if (connectorTools && getToolsCount(connectorTools) === 1) {
      connectorTools.map(onToolClick);
    } else if (
      connectorTools &&
      sourceDiagramElementId &&
      targetDiagramElementId &&
      getToolsCount(connectorTools) > 1
    ) {
      addTempConnectionLine(sourceDiagramElementId, targetDiagramElementId);
    }
  }, [connectorTools.length]);

  const shouldRender = !!toolsCount && sourceDiagramElementId && connectorTools && isOpened && paletteX && paletteY;

  const connectionTarget: HTMLElement | null = targetDiagramElementId
    ? document.querySelector(`[data-id="${targetDiagramElementId}"]`)
    : null;

  if (readOnly) {
    return null;
  }
  return shouldRender ? (
    <Menu
      open={!!shouldRender}
      onClose={onClose}
      anchorEl={connectionTarget}
      anchorReference="anchorPosition"
      data-testid="connectorContextualMenu"
      anchorPosition={{ left: paletteX, top: paletteY }}>
      {connectorTools.map((tool) => (
        <MenuItem key={tool.id} onClick={() => onToolClick(tool)} data-testid={`connectorContextualMenu-${tool.label}`}>
          <ListItemIcon>
            <IconOverlay iconURLs={tool.iconURL} alt={tool.label} title={tool.label} />
          </ListItemIcon>
          <Typography>{tool.label}</Typography>
        </MenuItem>
      ))}
    </Menu>
  ) : null;
});
