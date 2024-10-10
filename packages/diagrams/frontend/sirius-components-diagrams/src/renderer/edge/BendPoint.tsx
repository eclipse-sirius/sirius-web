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
import { useRef } from 'react';
import { useViewport } from '@xyflow/react';
import Draggable, { DraggableData } from 'react-draggable';
import { BendPointProps, TemporaryBendPointProps } from './BendPoint.types';

export const BendPoint = ({ x, y, index, onDragStop, onDoubleClick }: BendPointProps) => {
  const { zoom } = useViewport();
  const nodeRef = useRef(null);

  return (
    <Draggable
      position={{ x: x, y: y }}
      scale={zoom}
      onStop={(_e, eventData: DraggableData) => onDragStop(eventData, index)}
      nodeRef={nodeRef}>
      <circle
        style={{ pointerEvents: 'all' }}
        ref={nodeRef}
        r={3}
        fill={'black'}
        onDoubleClick={() => onDoubleClick(index)}
      />
    </Draggable>
  );
};

export const TemporaryBendPoint = ({ x, y, index, onDragStop }: TemporaryBendPointProps) => {
  const { zoom } = useViewport();
  const nodeRef = useRef(null);
  return (
    <Draggable
      position={{ x: x, y: y }}
      scale={zoom}
      onStop={(_e, eventData: DraggableData) => onDragStop(eventData, index)}
      nodeRef={nodeRef}>
      <circle
        style={{ pointerEvents: 'all' }}
        ref={nodeRef}
        r={2}
        strokeOpacity={0.4}
        stroke={'black'}
        fill={'transparent'}
      />
    </Draggable>
  );
};
