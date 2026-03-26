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

import { memo, useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { Palette } from '../palette/Palette';
import { PalettePortal } from '../palette/PalettePortal';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { isTool, isToolSection } from './../palette/Palette';
import { GQLTool } from './../palette/Palette.types';
import { ConnectorPaletteProps } from './ConnectorPalette.types';
import { useConnectorPalette } from './context/useConnectorPalette';
import { GQLPalette } from './useConnector.types';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { UseConnectorPaletteContentValue } from './useConnectorPaletteContents.types';
import { useTemporaryEdge } from './useTemporaryEdge';

const getToolsCount = (connectorTools: GQLPalette): number =>
  connectorTools ? connectorTools.paletteEntries.length : 0;

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

  const { connectorPalette }: UseConnectorPaletteContentValue = useConnectorPaletteContents(
    sourceDiagramElementId || '',
    targetDiagramElementId || ''
  );
  const toolsCount = connectorPalette ? getToolsCount(connectorPalette) : 0;

  const onClose = () => {
    hideConnectorPalette();
    removeTempConnectionLine();
  };

  const onKeyDown = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      if (isOpened && key === 'Escape') {
        event.stopPropagation();
        onClose();
      }
    },
    [hideConnectorPalette, isOpened]
  );

  const onToolClick = (tool: GQLTool) => {
    invokeConnectorTool(tool);
    onClose();
  };

  useEffect(() => {
    if (connectorPalette && getToolsCount(connectorPalette) === 1) {
      connectorPalette.quickAccessTools
        .concat(
          connectorPalette.paletteEntries
            .filter(isTool)
            .concat(connectorPalette.paletteEntries.filter(isToolSection).flatMap((toolSection) => toolSection.tools))
        )
        .map(onToolClick);
    } else if (
      connectorPalette &&
      sourceDiagramElementId &&
      targetDiagramElementId &&
      getToolsCount(connectorPalette) > 1
    ) {
      addTempConnectionLine(sourceDiagramElementId, targetDiagramElementId);
    }
  }, [connectorPalette?.paletteEntries.length]);

  const shouldRender = !!toolsCount && sourceDiagramElementId && connectorPalette && isOpened && paletteX && paletteY;

  if (readOnly) {
    return null;
  }
  return shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <Palette
          x={paletteX}
          y={paletteY}
          diagramElementIds={[sourceDiagramElementId]}
          palette={connectorPalette}
          onToolClick={onToolClick}
          onClose={onClose}
          paletteToolListExtensions={[]}
        />
      </div>
    </PalettePortal>
  ) : null;
});
