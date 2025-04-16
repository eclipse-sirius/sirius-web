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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@mui/material/styles';
import { BaseEdge, Edge, EdgeLabelRenderer, Position, XYPosition } from '@xyflow/react';
import { memo, useEffect, useMemo, useState } from 'react';
import { useStore } from '../../../representation/useStore';
import { useConnectorEdgeStyle } from '../../connector/useConnectorEdgeStyle';
import { Label } from '../../Label';
import { DiagramElementPalette } from '../../palette/DiagramElementPalette';
import { BendPoint, TemporaryMovingLine } from '../BendPoint';
import { EdgeCreationHandle } from '../EdgeCreationHandle';
import { MultiLabelEdgeData } from '../MultiLabelEdge.types';
import { MultiLabelEditableEdgeProps } from './MultiLabelRectilinearEditableEdge.types';
import { determineSegmentAxis } from './RectilinearEdgeCalculation';
import { useBendingPoints } from './useBendingPoints';
import { useTemporaryLines } from './useTemporaryLines';

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

const labelContainerStyle = (transform: string): React.CSSProperties => {
  return {
    transform,
    position: 'absolute',
    padding: 5,
    zIndex: 1001,
  };
};

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

export const MultiLabelRectilinearEditableEdge = memo(
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
    bendingPoints,
    customEdge,
    sourceNode,
    sourceHandleId,
    targetNode,
    targetHandleId,
  }: MultiLabelEditableEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const { beginLabel, endLabel, label, faded } = data || {};
    const { setEdges } = useStore();
    const theme = useTheme();

    const [source, setSource] = useState<XYPosition>({ x: sourceX, y: sourceY });
    const [target, setTarget] = useState<XYPosition>({ x: targetX, y: targetY });

    useEffect(() => {
      setSource({ x: sourceX, y: sourceY });
    }, [sourceX, sourceY]);

    useEffect(() => {
      setTarget({ x: targetX, y: targetY });
    }, [targetX, targetY]);

    const { localBendingPoints, setLocalBendingPoints, onBendingPointDragStop, onBendingPointDrag } = useBendingPoints(
      id,
      bendingPoints,
      source.x,
      source.y,
      target.x,
      target.y,
      customEdge
    );

    const { middleBendingPoints, onTemporaryLineDragStop, onTemporaryLineDrag } = useTemporaryLines(
      id,
      bendingPoints,
      localBendingPoints,
      setLocalBendingPoints,
      sourceNode,
      sourceHandleId ?? '',
      sourcePosition,
      source,
      setSource,
      targetNode,
      targetHandleId ?? '',
      targetPosition,
      target,
      setTarget
    );

    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);
    const { style: connectionFeedbackStyle } = useConnectorEdgeStyle(data ? data.descriptionId : '', !!data?.isHovered);

    const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
    const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);

    const edgeCenter: XYPosition = useMemo(() => {
      let pointsSource = localBendingPoints.map((bendingPoint) => ({ x: bendingPoint.x, y: bendingPoint.y }));
      if (isMultipleOfTwo(localBendingPoints.length)) {
        pointsSource = middleBendingPoints;
      }
      return pointsSource[Math.floor(pointsSource.length / 2)] ?? { x: 0, y: 0 };
    }, [
      middleBendingPoints.map((point) => `${point.x}:${point.y}`).join(),
      localBendingPoints.map((point) => `${point.x}:${point.y}`).join(),
    ]);

    const edgePath: string = useMemo(() => {
      let edgePath = `M ${source.x} ${source.y}`;
      const reorderBendPoint = [...localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
      for (let i = 0; i < reorderBendPoint.length; i++) {
        const currentPoint = reorderBendPoint[i];
        if (currentPoint) {
          if (i === 0) {
            if (determineSegmentAxis({ x: source.x, y: source.y }, currentPoint) !== 'x') {
              edgePath += ` L ${source.x} ${currentPoint.y}`;
            } else {
              edgePath += ` L ${currentPoint.x} ${source.y}`;
            }
          } else {
            edgePath += ` L ${currentPoint.x} ${currentPoint.y}`;
          }
        }
      }
      edgePath += ` L ${target.x} ${target.y}`;
      return edgePath;
    }, [localBendingPoints.map((point) => point.x + point.y).join(), source.x, source.y, target.x, target.y]);

    useEffect(() => {
      setEdges((prevEdges) =>
        prevEdges.map((prevEdge) => {
          if (prevEdge.id === id && prevEdge.data) {
            return {
              ...prevEdge,
              data: {
                ...prevEdge.data,
                edgePath,
              },
            };
          }
          return prevEdge;
        })
      );
    }, [edgePath]);
    return (
      <>
        <BaseEdge
          id={id}
          path={edgePath}
          style={{
            ...edgeStyle,
            ...connectionFeedbackStyle,
          }}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 2)}--selected')` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 2)}--selected')` : markerStart}
        />
        {selected ? (
          <>
            <DiagramElementPalette
              diagramElementId={id}
              targetObjectId={data?.targetObjectId ?? ''}
              labelId={label ? label.id : null}
            />
            <EdgeCreationHandle edgeId={id} edgePath={edgePath}></EdgeCreationHandle>
          </>
        ) : null}
        {selected &&
          localBendingPoints &&
          localBendingPoints.map((point, index) => (
            <BendPoint
              key={index}
              x={point.x}
              y={point.y}
              index={index}
              onDrag={onBendingPointDrag}
              onDragStop={onBendingPointDragStop}
            />
          ))}
        {selected &&
          middleBendingPoints &&
          middleBendingPoints.map((point, index) => (
            <TemporaryMovingLine
              key={index}
              x={point.x}
              y={point.y}
              direction={point.direction}
              segmentLength={point.segmentLength}
              index={index}
              onDrag={onTemporaryLineDrag}
              onDragStop={onTemporaryLineDragStop}
            />
          ))}
        <EdgeLabelRenderer>
          {beginLabel && (
            <div style={labelContainerStyle(`${sourceLabelTranslation} translate(${source.x}px,${source.y}px)`)}>
              <Label diagramElementId={id} label={beginLabel} faded={!!faded} />
            </div>
          )}
          {label && (
            <div style={labelContainerStyle(`translate(${edgeCenter.x}px,${edgeCenter.y}px)`)}>
              <Label diagramElementId={id} label={label} faded={!!faded} />
            </div>
          )}
          {endLabel && (
            <div style={labelContainerStyle(`${targetLabelTranslation} translate(${target.x}px,${target.y}px)`)}>
              <Label diagramElementId={id} label={endLabel} faded={!!faded} />
            </div>
          )}
        </EdgeLabelRenderer>
      </>
    );
  }
);
