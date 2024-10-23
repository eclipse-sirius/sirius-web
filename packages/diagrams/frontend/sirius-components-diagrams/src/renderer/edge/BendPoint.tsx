/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useRef, RefObject } from 'react';
import { useViewport } from '@xyflow/react';
import Draggable, { DraggableData } from 'react-draggable';
import { BendPointProps, TemporaryBendPointProps } from './BendPoint.types';

export const BendPoint = ({ x, y, index, onDrag, onDragStop, onDoubleClick }: BendPointProps) => {
  const { zoom } = useViewport();
  const nodeRef = useRef<SVGCircleElement>(null);

  return (
    <Draggable
      position={{ x: x, y: y }}
      scale={zoom}
      onDrag={(_e, eventData: DraggableData) => onDrag(eventData, index)}
      onStop={(_e, eventData: DraggableData) => onDragStop(eventData, index)}
      nodeRef={nodeRef as unknown as RefObject<HTMLElement>}>
      <circle
        style={{ pointerEvents: 'all' }}
        ref={nodeRef}
        r={3}
        fill={'black'}
        onDoubleClick={() => onDoubleClick(index)}
        data-testid={`bend-point-${index}`}
      />
    </Draggable>
  );
};

export const TemporaryBendPoint = ({ x, y, index, onDrag, onDragStop }: TemporaryBendPointProps) => {
  const { zoom } = useViewport();
  const nodeRef = useRef<SVGCircleElement>(null);
  return (
    <Draggable
      position={{ x: x, y: y }}
      scale={zoom}
      onDrag={(_e, eventData: DraggableData) => onDrag(eventData, index)}
      onStop={(_e, eventData: DraggableData) => onDragStop(eventData, index)}
      nodeRef={nodeRef as unknown as RefObject<HTMLElement>}>
      <circle
        style={{ pointerEvents: 'all' }}
        ref={nodeRef}
        r={2}
        strokeOpacity={0.4}
        stroke={'black'}
        fill={'transparent'}
        data-testid={`temporary-bend-point-${index}`}
      />
    </Draggable>
  );
};
