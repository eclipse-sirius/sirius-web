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

import {
  GQLPalette,
  GQLPaletteDivider,
  GQLPaletteEntry,
  GQLTool,
  GQLToolSection,
  Palette,
} from '@eclipse-sirius/sirius-components-palette';
import { Edge, Node, useStoreApi, useViewport, XYPosition } from '@xyflow/react';
import React, { useContext, useEffect, useState } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useGetUpdatedModalPosition } from '../hooks/useGetUpdatedModalPosition';
import { DiagramToolExecutorContext } from '../tools/DiagramToolExecutorContext';
import { DiagramToolExecutorContextValue } from '../tools/DiagramToolExecutorContext.types';
import { DraggablePaletteProps, DraggablePaletteState } from './DraggablePalette.types';
import { useDiagramPalette } from './useDiagramPalette';
import { usePaletteContents } from './usePaletteContents';
import { UsePaletteContentValue } from './usePaletteContents.types';

export const isToolSection = (entry: GQLPaletteEntry): entry is GQLToolSection => entry.__typename === 'ToolSection';

export const isPaletteDivider = (entry: GQLPaletteDivider): entry is GQLToolSection =>
  entry.__typename === 'PaletteDivider';

export const isTool = (entry: GQLPaletteEntry): entry is GQLTool => !isPaletteDivider(entry) && !isToolSection(entry);

const getPaletteToolCount = (palette: GQLPalette): number => {
  return (
    palette.paletteEntries.filter(isTool).length +
    palette.quickAccessTools.filter(isTool).length +
    palette.paletteEntries.filter(isToolSection).filter((toolSection) => toolSection.tools.filter(isTool).length > 0)
      .length
  );
};

export const DraggablePalette = ({
  x: paletteX,
  y: paletteY,
  diagramElementId,
  targetObjectId,
  onDirectEditClick,
  onClose,
  children,
}: DraggablePaletteProps) => {
  const { domNode, nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { getUpdatedModalPosition, getUpdatedBounds } = useGetUpdatedModalPosition();
  const { x: viewportX, y: viewportY, zoom: viewportZoom } = useViewport();
  const nodeRef = React.useRef<HTMLDivElement>(null);

  const [state, setState] = useState<DraggablePaletteState>({
    controlledPosition: { x: paletteX, y: paletteY },
  });

  const diagramElement = nodeLookup.get(diagramElementId) || edgeLookup.get(diagramElementId);

  const { palette }: UsePaletteContentValue = usePaletteContents(diagramElementId);
  const { setLastToolInvoked, getLastToolInvoked } = useDiagramPalette();
  const { executeTool } = useContext<DiagramToolExecutorContextValue>(DiagramToolExecutorContext);

  const lastToolInvoked = palette ? getLastToolInvoked(palette.id) : null;

  const handleToolClick = (
    toolExecutedPosition: XYPosition,
    representationElementId: string,
    onDirectEditClick: () => void,
    tool: GQLTool
  ) => {
    onClose();
    domNode?.focus();
    let x: number = 0;
    let y: number = 0;
    if (viewportZoom !== 0 && toolExecutedPosition.x && toolExecutedPosition.y) {
      x = (paletteX - viewportX) / viewportZoom;
      y = (paletteY - viewportY) / viewportZoom;
    }

    executeTool(x, y, representationElementId, targetObjectId, onDirectEditClick, tool);

    if (palette) {
      setLastToolInvoked(palette.id, tool);
    }

    const position = getUpdatedModalPosition({ x: state.controlledPosition.x, y: state.controlledPosition.y }, nodeRef);
    setState((prevState) => {
      return { ...prevState, controlledPosition: position };
    });
  };

  const shouldRender = palette && (getPaletteToolCount(palette) > 0 || !!diagramElement);

  useEffect(() => {
    if (!nodeRef.current) return;
    // If the palette size changes when opening a section then update the position of the modal
    const resizeObserver = new ResizeObserver(() => {
      setState((prevState) => {
        return {
          ...prevState,
          controlledPosition: getUpdatedModalPosition(
            { x: prevState.controlledPosition.x, y: prevState.controlledPosition.y },
            nodeRef
          ),
        };
      });
    });
    resizeObserver.observe(nodeRef.current);

    return () => resizeObserver.disconnect();
  }, [shouldRender]);

  const draggableBounds = getUpdatedBounds(nodeRef);

  const onPaletteDragStop = (_event, data: DraggableData) =>
    setState((prevState) => ({ ...prevState, controlledPosition: data }));

  useEffect(() => {}, []);
  if (!shouldRender) {
    return null;
  }
  console.log(state.controlledPosition);
  console.log(draggableBounds);
  return (
    <Draggable
      nodeRef={nodeRef}
      handle="#tool-palette-header"
      bounds={draggableBounds}
      position={state.controlledPosition}
      onStop={onPaletteDragStop}>
      <Palette
        paletteXYPosition={{ x: paletteX, y: paletteY }}
        representationElementId={diagramElementId}
        palette={palette}
        lastToolInvoked={lastToolInvoked}
        onToolClick={handleToolClick}
        onDirectEditClick={onDirectEditClick}
        onClose={onClose}
        extensions={children}
        ref={nodeRef}></Palette>
    </Draggable>
  );
};
