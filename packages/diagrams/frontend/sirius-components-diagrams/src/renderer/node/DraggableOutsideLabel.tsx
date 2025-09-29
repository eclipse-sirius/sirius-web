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
import { ReactFlowState, useStore } from '@xyflow/react';
import React, { useContext } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { Label } from '../Label';
import { useLabelMove } from '../move/useLabelMove';
import { DraggableOutsideLabelProps } from './DraggableOutsideLabel.types';

const zoomSelector = (state: ReactFlowState) => state.panZoom?.getViewport().zoom || 1;

export const DraggableOutsideLabel = ({ id, label, faded, highlighted }: DraggableOutsideLabelProps) => {
  const { onNodeLabelMoveStop } = useLabelMove();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const zoom = useStore(zoomSelector);

  const onStop = (_e, eventData: DraggableData) => {
    onNodeLabelMoveStop(eventData, id);
  };

  const nodeRef = React.useRef<HTMLInputElement | null>(null);

  return (
    <Draggable position={label.position} onStop={onStop} scale={zoom} nodeRef={nodeRef} disabled={readOnly}>
      <div ref={nodeRef}>
        <Label diagramElementId={id} label={label} faded={faded} highlighted={highlighted} />
      </div>
    </Draggable>
  );
};
