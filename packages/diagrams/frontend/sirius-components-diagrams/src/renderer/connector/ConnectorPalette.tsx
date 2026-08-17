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

import { GQLTool, isTool } from '@eclipse-sirius/sirius-components-palette';
import { memo, useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DIAGRAM_REPRESENTATION_KIND } from '../palette/DiagramPalette';
import { DraggablePalette } from '../palette/DraggablePalette';
import { PalettePortal } from '../palette/PalettePortal';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { ConnectorPaletteProps } from './ConnectorPalette.types';
import { useConnectorPalette } from './context/useConnectorPalette';
import { GQLPalette } from './useConnector.types';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { UseConnectorPaletteContentValue } from './useConnectorPaletteContents.types';
import { useTemporaryEdge } from './useTemporaryEdge';

const getToolsCount = (connectorPalette: GQLPalette | null): number =>
  connectorPalette?.paletteEntries.filter(isTool).length ?? 0;

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

  const toolsCount = getToolsCount(connectorPalette);

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
    if (connectorPalette && toolsCount === 1) {
      connectorPalette.paletteEntries.filter(isTool).map(onToolClick);
    } else if (connectorPalette && sourceDiagramElementId && targetDiagramElementId && toolsCount > 1) {
      addTempConnectionLine(sourceDiagramElementId, targetDiagramElementId);
    }
  }, [connectorPalette?.paletteEntries.length]);

  const shouldRender =
    !readOnly && toolsCount > 1 && sourceDiagramElementId && connectorPalette && isOpened && paletteX && paletteY;

  return shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <DraggablePalette
          x={paletteX}
          y={paletteY}
          representationElementIds={[sourceDiagramElementId]}
          palette={connectorPalette}
          onToolClick={onToolClick}
          onClose={onClose}
          paletteToolListExtensions={[]}
          representationKind={DIAGRAM_REPRESENTATION_KIND}
        />
      </div>
    </PalettePortal>
  ) : null;
});
