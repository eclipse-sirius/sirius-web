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
import { EdgeLabelRenderer, Position, ReactFlowState, useStore } from '@xyflow/react';
import { useContext, useMemo, useRef } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { Label } from '../Label';
import { useLabelMove } from '../move/useLabelMove';
import { DraggableEdgeLabelsProps } from './DraggableEdgeLabels.types';

const getTranslateFromHandlePositon = (position: Position) => {
  switch (position) {
    case Position.Right:
      return 'translate(2%, -100%)';
    case Position.Left:
      return 'translate(-102%, -100%)';
    case Position.Top:
      return 'translate(2%, -100%)';
    case Position.Bottom:
      return 'translate(2%, 0%)';
  }
};

const zoomSelector = (state: ReactFlowState) => state.panZoom?.getViewport().zoom || 1;

export const DraggableEdgeLabels = ({
  id,
  data,
  selected,
  sourcePosition,
  targetPosition,
  sourceX,
  sourceY,
  targetX,
  targetY,
  edgeCenter,
}: DraggableEdgeLabelsProps) => {
  const { beginLabel, endLabel, label, faded } = data || {};
  const zoom = useStore(zoomSelector);
  const { onEdgeLabelMoveStop } = useLabelMove();
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const beginLabelNodeRef = useRef<HTMLInputElement | null>(null);
  const centerLabelNodeRef = useRef<HTMLInputElement | null>(null);
  const endLabelNodeRef = useRef<HTMLInputElement | null>(null);
  const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
  const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);

  const onStop = (_e, eventData: DraggableData, labelPosition: 'begin' | 'center' | 'end') => {
    onEdgeLabelMoveStop(eventData, id, labelPosition);
  };

  let labelCenterOffset = {
    x: edgeCenter?.x ?? 0,
    y: edgeCenter?.y ?? 0,
  };
  if (data.label?.width) {
    labelCenterOffset.x = labelCenterOffset.x - data.label.width / 2;
  }
  if (data.label?.height) {
    labelCenterOffset.y = labelCenterOffset.y - data.label.height / 2;
  }
  return (
    <EdgeLabelRenderer>
      {beginLabel && (
        <Draggable
          position={{ x: beginLabel.position.x, y: beginLabel.position.y }}
          positionOffset={{ x: sourceX, y: sourceY }}
          onStop={(_e, eventData) => onStop(_e, eventData, 'begin')}
          scale={zoom}
          nodeRef={beginLabelNodeRef}
          disabled={readOnly}>
          <div style={{ position: 'absolute', zIndex: 1001 }} ref={beginLabelNodeRef}>
            <div style={{ transform: sourceLabelTranslation, padding: 5 }}>
              <Label diagramElementId={id} label={beginLabel} faded={!!faded} highlighted={selected} />
            </div>
          </div>
        </Draggable>
      )}
      {label && edgeCenter && (
        <Draggable
          position={{ x: label.position.x, y: label.position.y }}
          positionOffset={labelCenterOffset}
          onStop={(_e, eventData) => onStop(_e, eventData, 'center')}
          scale={zoom}
          nodeRef={centerLabelNodeRef}
          disabled={readOnly}>
          <div style={{ position: 'absolute', zIndex: 1001 }} ref={centerLabelNodeRef}>
            <div style={{ padding: 5 }}>
              <Label diagramElementId={id} label={label} faded={!!faded} highlighted={selected} />
            </div>
          </div>
        </Draggable>
      )}
      {endLabel && (
        <Draggable
          position={{ x: endLabel.position.x, y: endLabel.position.y }}
          positionOffset={{ x: targetX, y: targetY }}
          onStop={(_e, eventData) => onStop(_e, eventData, 'end')}
          scale={zoom}
          nodeRef={endLabelNodeRef}
          disabled={readOnly}>
          <div style={{ position: 'absolute', zIndex: 1001 }} ref={endLabelNodeRef}>
            <div style={{ transform: targetLabelTranslation, padding: 5 }}>
              <Label diagramElementId={id} label={endLabel} faded={!!faded} highlighted={selected} />
            </div>
          </div>
        </Draggable>
      )}
    </EdgeLabelRenderer>
  );
};
