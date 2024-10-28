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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@mui/material/styles';
import { BaseEdge, Edge, EdgeLabelRenderer, Position, useViewport } from '@xyflow/react';
import { memo, useMemo, useRef } from 'react';
import Draggable, { DraggableData } from 'react-draggable';
import { Label } from '../Label';
import { useLabelMove } from '../move/useLabelMove';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { MultiLabelEdgeData, MultiLabelEdgeProps } from './MultiLabelEdge.types';

const multiLabelEdgeStyle = (
  theme: Theme,
  style: React.CSSProperties | undefined,
  selected: boolean | undefined,
  faded: boolean | undefined
): React.CSSProperties => {
  const multiLabelEdgeStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    ...style,
    stroke: style?.stroke ? getCSSColor(String(style.stroke), theme) : undefined,
  };

  if (selected) {
    multiLabelEdgeStyle.stroke = `${theme.palette.selected}`;
  }

  return multiLabelEdgeStyle;
};

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

export const MultiLabelEdge = memo(
  ({
    id,
    data,
    style,
    markerEnd,
    markerStart,
    selected,
    sourcePosition,
    targetPosition,
    sourceX,
    sourceY,
    targetX,
    targetY,
    edgeCenterX,
    edgeCenterY,
    svgPathString,
  }: MultiLabelEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const { beginLabel, endLabel, label, faded } = data || {};
    const theme = useTheme();
    const { zoom } = useViewport();
    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);
    const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
    const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);
    const { onEdgeLabelMoveStop } = useLabelMove();

    const onStop = (_e, eventData: DraggableData, labelPosition: 'begin' | 'center' | 'end') => {
      onEdgeLabelMoveStop(eventData, id, labelPosition);
    };

    const beginLabelNodeRef = useRef<HTMLInputElement | null>(null);
    const centerLabelNodeRef = useRef<HTMLInputElement | null>(null);
    const endLabelNodeRef = useRef<HTMLInputElement | null>(null);

    return (
      <>
        <BaseEdge
          id={id}
          path={svgPathString}
          style={edgeStyle}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 2)}--selected')` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 2)}--selected')` : markerStart}
        />
        {selected ? (
          <DiagramElementPalette
            diagramElementId={id}
            targetObjectId={data?.targetObjectId ?? ''}
            labelId={label ? label.id : null}
          />
        ) : null}
        <EdgeLabelRenderer>
          {beginLabel && (
            <Draggable
              position={{ x: beginLabel.position.x, y: beginLabel.position.y }}
              positionOffset={{ x: sourceX, y: sourceY }}
              onStop={(_e, eventData) => onStop(_e, eventData, 'begin')}
              scale={zoom}
              nodeRef={beginLabelNodeRef}>
              <div style={{ position: 'absolute', zIndex: 1001 }} ref={beginLabelNodeRef}>
                <div style={{ transform: sourceLabelTranslation, padding: 5 }}>
                  <Label diagramElementId={id} label={beginLabel} faded={!!faded} highlighted={selected} />
                </div>
              </div>
            </Draggable>
          )}
          {label && (
            <Draggable
              position={{ x: label.position.x, y: label.position.y }}
              positionOffset={{ x: edgeCenterX, y: edgeCenterY }}
              onStop={(_e, eventData) => onStop(_e, eventData, 'center')}
              scale={zoom}
              nodeRef={centerLabelNodeRef}>
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
              nodeRef={endLabelNodeRef}>
              <div style={{ position: 'absolute', zIndex: 1001 }} ref={endLabelNodeRef}>
                <div style={{ transform: targetLabelTranslation, padding: 5 }}>
                  <Label diagramElementId={id} label={endLabel} faded={!!faded} highlighted={selected} />
                </div>
              </div>
            </Draggable>
          )}
        </EdgeLabelRenderer>
      </>
    );
  }
);
