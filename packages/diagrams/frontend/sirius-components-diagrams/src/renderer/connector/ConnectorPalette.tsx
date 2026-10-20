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

import { GQLPalette, GQLTool } from '@eclipse-sirius/sirius-components-palette';
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
import { GQLSingleClickOnTwoDiagramElementsTool } from './useConnector.types';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { GQLConnectorPaletteResult, UseConnectorPaletteContentValue } from './useConnectorPaletteContents.types';
import { useTemporaryEdge } from './useTemporaryEdge';

export const isPalette = (entry: GQLConnectorPaletteResult): entry is GQLPalette => entry.__typename === 'Palette';

export const isSingleClickOnTwoDiagramElementTool = (
  entry: GQLConnectorPaletteResult
): entry is GQLSingleClickOnTwoDiagramElementsTool => entry.__typename === 'SingleClickOnTwoDiagramElementsTool';

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

  const { connectorPalette, loading }: UseConnectorPaletteContentValue = useConnectorPaletteContents(
    sourceDiagramElementId || '',
    targetDiagramElementId || ''
  );

  const palette = connectorPalette && isPalette(connectorPalette) ? connectorPalette : null;
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
    if (!isOpened || readOnly || loading || !connectorPalette) {
      return;
    }
    if (isSingleClickOnTwoDiagramElementTool(connectorPalette)) {
      onToolClick(connectorPalette);
    } else if (sourceDiagramElementId && targetDiagramElementId) {
      addTempConnectionLine(sourceDiagramElementId, targetDiagramElementId);
    }
  }, [connectorPalette, loading, isOpened, readOnly, sourceDiagramElementId, targetDiagramElementId]);

  const shouldRender =
    !readOnly && !loading && sourceDiagramElementId && palette && isOpened && paletteX !== null && paletteY !== null;

  return shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <DraggablePalette
          x={paletteX}
          y={paletteY}
          representationElementIds={[sourceDiagramElementId]}
          palette={palette}
          onToolClick={onToolClick}
          onClose={onClose}
          paletteToolListExtensions={[]}
          representationKind={DIAGRAM_REPRESENTATION_KIND}
        />
      </div>
    </PalettePortal>
  ) : null;
});
