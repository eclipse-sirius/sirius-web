/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { Palette } from './../palette/Palette';
import { GQLTool } from './../palette/Palette.types';
import { PalettePortal } from './../palette/PalettePortal';
import { ConnectorPaletteProps } from './ConnectorPalette.types';
import { useConnectorPalette } from './useConnectorPalette';
import { useConnectorPaletteContents } from './useConnectorPaletteContents';
import { UseConnectorPaletteContentValue } from './useConnectorPaletteContents.types';
import { useSingleClickOnTwoDiagramElementTool } from './useSingleClickOnTwoDiagramElementTool';
import { GQLInvokeSingleClickOnTwoDiagramElementsToolInput } from './useSingleClickOnTwoDiagramElementTool.types';

export const ConnectorPalette = memo(({}: ConnectorPaletteProps) => {
  const { readOnly, editingContextId, diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const {
    isOpened,
    x: paletteX,
    y: paletteY,
    sourceDiagramElementId,
    targetDiagramElementId,
    hideConnectorPalette,
  } = useConnectorPalette();

  const { invokeSingleClickOnTwoDiagramElementsTool } = useSingleClickOnTwoDiagramElementTool();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const { connectorPalette }: UseConnectorPaletteContentValue = useConnectorPaletteContents(
    sourceDiagramElementId,
    targetDiagramElementId
  );

  const onKeyDown = useCallback(
    (event: React.KeyboardEvent<Element>) => {
      const { key } = event;
      if (isOpened && key === 'Escape') {
        event.stopPropagation();
        hideConnectorPalette();
      }
    },
    [hideConnectorPalette, isOpened]
  );

  const onToolClick = (tool: GQLTool) => {
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && paletteX && paletteY) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }

    if (sourceDiagramElementId && targetDiagramElementId) {
      const input: GQLInvokeSingleClickOnTwoDiagramElementsToolInput = {
        id: tool.id,
        editingContextId: editingContextId,
        representationId: diagramId,
        diagramSourceElementId: sourceDiagramElementId,
        diagramTargetElementId: targetDiagramElementId,
        sourcePositionX: 0,
        sourcePositionY: 0,
        targetPositionX: x,
        targetPositionY: y,
        toolId: tool.id,
        variables: [],
      };
      //TODO remove and use toolExecutor and make sure dialog is working
      invokeSingleClickOnTwoDiagramElementsTool(input);
      hideConnectorPalette();
    }
  };

  if (readOnly) {
    return null;
  }

  const shouldRender = connectorPalette && isOpened && paletteX && paletteY;

  return shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <Palette
          x={paletteX}
          y={paletteY}
          diagramElementId={sourceDiagramElementId || ''}
          palette={connectorPalette}
          onToolClick={onToolClick}
          onClose={hideConnectorPalette}
          paletteToolListExtensions={[]}
        />
      </div>
    </PalettePortal>
  ) : null;
});
