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

import { Edge, Node, useReactFlow, useViewport } from '@xyflow/react';
import { memo, useCallback, useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useDiagramDirectEdit } from '../direct-edit/useDiagramDirectEdit';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { DiagramPaletteProps } from './DiagramPalette.types';
import { Palette } from './Palette';
import { GQLTool } from './Palette.types';
import { PalettePortal } from './PalettePortal';
import { useDiagramPalette } from './useDiagramPalette';
import { usePaletteContents } from './usePaletteContents';
import { UsePaletteContentValue } from './usePaletteContents.types';

export const DiagramPalette = memo(({ diagramId, diagramTargetObjectId }: DiagramPaletteProps) => {
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { isOpened, x: paletteX, y: paletteY, diagramElementIds, hideDiagramPalette } = useDiagramPalette();
  const { executeTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);
  const { setCurrentlyEditedLabelId, currentlyEditedLabelId } = useDiagramDirectEdit();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const { getNode, getEdge } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const elementId = diagramElementIds[0] ? diagramElementIds[0] : diagramId;
  const elementsIds = diagramElementIds[0] ? diagramElementIds : [diagramId];
  let { palette }: UsePaletteContentValue = usePaletteContents(elementsIds, false);

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

  const handleDirectEditClick = useCallback(() => {
    let currentlyEditedLabelId: string | null = null;
    let isLabelEditable = false;
    if (diagramElementIds.length === 1 && diagramElementIds[0]) {
      const node = getNode(diagramElementIds[0]);
      if (node) {
        if (node.data.insideLabel) {
          currentlyEditedLabelId = node.data.insideLabel.id;
        } else if (node.data.outsideLabels.BOTTOM_MIDDLE) {
          currentlyEditedLabelId = node.data.outsideLabels.BOTTOM_MIDDLE.id;
        }
        isLabelEditable = node.data.labelEditable;
      } else {
        const edge = getEdge(diagramElementIds[0]);
        if (edge && edge.data && edge.data.label) {
          currentlyEditedLabelId = edge.data.label.id;
          isLabelEditable = edge.data.centerLabelEditable;
        }
      }
      if (isLabelEditable && currentlyEditedLabelId) {
        setCurrentlyEditedLabelId('palette', currentlyEditedLabelId, null);
      }
    }
  }, [diagramElementIds]);

  const targetObjectId =
    elementId === diagramId
      ? diagramTargetObjectId
      : getNode(diagramElementIds[0] || '')?.data.targetObjectId ||
        getEdge(diagramElementIds[0] || '')?.data?.targetObjectId ||
        diagramTargetObjectId;

  const onToolClick = (tool: GQLTool) => {
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && paletteX && paletteY) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }
    executeTool(x, y, elementsIds, targetObjectId, handleDirectEditClick, tool);
  };

  if (readOnly) {
    return null;
  }

  const shouldRender = isOpened && !!paletteX && !!paletteY && !currentlyEditedLabelId;

  return palette && shouldRender ? (
    <PalettePortal>
      <div onKeyDown={onKeyDown}>
        <Palette
          x={paletteX}
          y={paletteY}
          diagramElementIds={diagramElementIds.length > 0 ? diagramElementIds : [diagramId]}
          palette={palette}
          onToolClick={onToolClick}
          onClose={hideDiagramPalette}
        />
      </div>
    </PalettePortal>
  ) : null;
});
