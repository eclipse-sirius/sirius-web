/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { useRef, RefObject, useContext } from 'react';
import { useViewport } from '@xyflow/react';
import Draggable, { DraggableData } from 'react-draggable';
import { BendPointProps, TemporaryMovingLineProps } from './BendPoint.types';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';

export const BendPoint = ({ x, y, index, onDrag, onDragStop }: BendPointProps) => {
  const { zoom } = useViewport();
  const nodeRef = useRef<SVGCircleElement>(null);
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  if (readOnly) {
    return null;
  }

  return (
    <Draggable
      position={{ x: x, y: y }}
      scale={zoom}
      onDrag={(_e, eventData: DraggableData) => onDrag(eventData, index)}
      onStop={(_e, eventData: DraggableData) => onDragStop(eventData, index)}
      nodeRef={nodeRef as unknown as RefObject<HTMLElement>}>
      <circle style={{ pointerEvents: 'all' }} ref={nodeRef} r={3} fill={'black'} data-testid={`bend-point-${index}`} />
    </Draggable>
  );
};

export const TemporaryMovingLine = ({
  x,
  y,
  direction,
  segmentLength,
  index,
  onDrag,
  onDragStop,
}: TemporaryMovingLineProps) => {
  const { zoom } = useViewport();
  const nodeRef = useRef<SVGRectElement>(null);
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);

  if (readOnly) {
    return null;
  }

  const segmentLengthWithoutBoundaries = segmentLength - 2;

  const width = direction === 'x' ? segmentLengthWithoutBoundaries : 4;
  const height = direction === 'y' ? segmentLengthWithoutBoundaries : 4;

  const offsetX = direction === 'x' ? -width / 2 : -2;
  const offsetY = direction === 'y' ? -height / 2 : -2;
  return (
    <Draggable
      position={{ x: x, y: y }}
      positionOffset={{ x: offsetX, y: offsetY }}
      scale={zoom}
      axis={direction === 'x' ? 'y' : 'x'}
      onDrag={(_e, eventData: DraggableData) => onDrag(eventData, index, direction)}
      onStop={(_e, eventData: DraggableData) => onDragStop(eventData, index)}
      nodeRef={nodeRef as unknown as RefObject<HTMLElement>}>
      <rect
        style={{
          pointerEvents: 'all',
          cursor: `${direction === 'x' ? 'ns-resize' : 'ew-resize'}`,
        }}
        width={width}
        height={height}
        ref={nodeRef}
        fill={'transparent'}
        data-testid={`temporary-moving-line-${index}`}
      />
    </Draggable>
  );
};
