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

import { useViewport } from '@xyflow/react';
import { memo, useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { DiagramPaletteProps } from './DiagramPalette.types';
import { Palette } from './Palette';
import { GQLTool } from './Palette.types';
import { PalettePortal } from './PalettePortal';
import { useDiagramPalette } from './useDiagramPalette';

export const DiagramPalette = memo(({ diagramElementId, targetObjectId }: DiagramPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isOpened, x: paletteX, y: paletteY, hideDiagramPalette } = useDiagramPalette();
  const { executeTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();

  const onKeyDown = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      if (isOpened && key === 'Escape') {
        event.stopPropagation();
        hideDiagramPalette();
      }
    },
    [hideDiagramPalette, isOpened]
  );

  if (readOnly) {
    return null;
  }

  const handleDirectEditClick = () => {};

  const onToolClick = (tool: GQLTool) => {
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && paletteX && paletteY) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }
    executeTool(x, y, diagramElementId, targetObjectId, handleDirectEditClick, tool);
  };

  return isOpened && paletteX && paletteY ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <Palette
          x={paletteX}
          y={paletteY}
          diagramElementId={diagramElementId}
          onToolClick={onToolClick}
          onClose={hideDiagramPalette}
          children={[]}
        />
      </div>
    </PalettePortal>
  ) : null;
});
