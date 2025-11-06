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
import { useContext, useMemo } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useLabelMove } from '../move/useLabelMove';
import { useLabelResize } from '../move/useLabelResize';
import { DraggableEdgeLabelsProps } from './DraggableEdgeLabels.types';
import { DraggableResizableLabel } from './DraggableResizableLabel';
import 'react-resizable/css/styles.css';

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
  const { readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const { onEdgeLabelMoveStop } = useLabelMove();
  const { onEdgeLabelResizeStop } = useLabelResize();
  const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
  const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);

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
        <DraggableResizableLabel
          id={id}
          label={beginLabel}
          position={{ x: beginLabel.position.x, y: beginLabel.position.y }}
          positionOffset={{ x: sourceX, y: sourceY }}
          onDragStop={(_e, eventData) => onEdgeLabelMoveStop(eventData, id, 'begin')}
          onResizeStop={(_e, { size }) => onEdgeLabelResizeStop(id, size, 'begin')}
          zoom={zoom}
          readOnly={readOnly}
          selected={selected}
          faded={faded}
          resizeHandlePosition={'ne'}
          transform={sourceLabelTranslation}
        />
      )}
      {label && edgeCenter && (
        <DraggableResizableLabel
          id={id}
          label={label}
          position={{ x: label.position.x, y: label.position.y }}
          positionOffset={labelCenterOffset}
          onDragStop={(_e, eventData) => onEdgeLabelMoveStop(eventData, id, 'center')}
          onResizeStop={(_e, { size }) => onEdgeLabelResizeStop(id, size, 'center')}
          zoom={zoom}
          readOnly={readOnly}
          selected={selected}
          faded={faded}
          resizeHandlePosition={'se'}
        />
      )}
      {endLabel && (
        <DraggableResizableLabel
          id={id}
          label={endLabel}
          position={{ x: endLabel.position.x, y: endLabel.position.y }}
          positionOffset={{ x: targetX, y: targetY }}
          onDragStop={(_e, eventData) => onEdgeLabelMoveStop(eventData, id, 'end')}
          onResizeStop={(_e, { size }) => onEdgeLabelResizeStop(id, size, 'end')}
          zoom={zoom}
          readOnly={readOnly}
          selected={selected}
          faded={faded}
          resizeHandlePosition={'nw'}
          transform={targetLabelTranslation}
        />
      )}
    </EdgeLabelRenderer>
  );
};
