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

import { Palette } from '@eclipse-sirius/sirius-components-palette';
import { Edge, Node, useStoreApi } from '@xyflow/react';
import React, { useEffect, useState } from 'react';
import Draggable, { DraggableData, DraggableEvent } from 'react-draggable';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useGetUpdatedModalPosition } from '../hooks/useGetUpdatedModalPosition';
import { DraggablePaletteState } from './DraggablePalette.types';
import { GQLTool, PaletteProps } from './Palette.types';
export const DraggablePalette = ({
  x: paletteX,
  y: paletteY,
  representationElementIds,
  palette,
  onToolClick,
  onClose,
  paletteToolListExtensions,
}: PaletteProps) => {
  const [state, setState] = useState<DraggablePaletteState>({
    controlledPosition: { x: paletteX, y: paletteY },
  });
  const nodeRef = React.useRef<HTMLDivElement>(null);
  const { domNode } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { getUpdatedModalPosition, getUpdatedBounds } = useGetUpdatedModalPosition();

  const handleToolClick = (tool: GQLTool) => {
    domNode?.focus();
    onToolClick(tool);
    const position = getUpdatedModalPosition({ x: state.controlledPosition.x, y: state.controlledPosition.y }, nodeRef);
    setState((prevState) => {
      return { ...prevState, controlledPosition: position };
    });
  };

  const updatedModalPosition = () => {
    setState((prevState) => {
      return {
        ...prevState,
        controlledPosition: getUpdatedModalPosition(
          { x: prevState.controlledPosition.x, y: prevState.controlledPosition.y },
          nodeRef
        ),
      };
    });
  };

  useEffect(() => {
    setState((prevState) => {
      return { ...prevState, controlledPosition: { x: paletteX, y: paletteY } };
    });
  }, [paletteX, paletteY]);

  useEffect(() => {
    if (!nodeRef.current) return;
    // If the palette size changes when opening a section then update the position of the modal
    const resizeObserver = new ResizeObserver(() => {
      updatedModalPosition();
    });
    resizeObserver.observe(nodeRef.current);
    updatedModalPosition();
    return () => resizeObserver.disconnect();
  }, [paletteX, paletteY]);

  const draggableBounds = getUpdatedBounds(nodeRef);

  const onPaletteDragStop = (_event: DraggableEvent, data: DraggableData) => {
    setState((prevState) => ({ ...prevState, controlledPosition: data }));
  };

  return (
    <Draggable
      nodeRef={nodeRef}
      handle="#tool-palette-header"
      bounds={draggableBounds}
      position={state.controlledPosition}
      onStop={onPaletteDragStop}>
      <Palette
        ref={nodeRef}
        x={paletteX}
        y={paletteY}
        representationElementIds={representationElementIds}
        palette={palette}
        onToolClick={handleToolClick}
        onClose={onClose}
        paletteToolListExtensions={paletteToolListExtensions}
      />
    </Draggable>
  );
};
