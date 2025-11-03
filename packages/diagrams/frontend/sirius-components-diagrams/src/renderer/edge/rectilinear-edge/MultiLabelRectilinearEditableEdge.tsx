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
import { BaseEdge, Edge, XYPosition } from '@xyflow/react';
import { memo, useEffect, useMemo, useState } from 'react';
import { useStore } from '../../../representation/useStore';
import { useConnectorEdgeStyle } from '../../connector/useConnectorEdgeStyle';
import { BendPoint, TemporaryMovingLine } from '../BendPoint';
import { DraggableEdgeLabels } from '../DraggableEdgeLabels';
import { EdgeCreationHandle } from '../EdgeCreationHandle';
import { MultiLabelEdgeData } from '../MultiLabelEdge.types';
import { MultiLabelEditableEdgeProps } from './MultiLabelRectilinearEditableEdge.types';
import { determineSegmentAxis, getMiddlePoint } from './RectilinearEdgeCalculation';
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
    const { faded } = data || {};
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
      source,
      setSource,
      sourceNode,
      sourceHandleId ?? '',
      sourcePosition,
      target,
      setTarget,
      targetNode,
      targetHandleId ?? '',
      targetPosition,
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

    const edgeCenter: XYPosition | undefined = useMemo(() => {
      let pointsSource = bendingPoints.map((bendingPoint) => ({ x: bendingPoint.x, y: bendingPoint.y }));
      if (isMultipleOfTwo(pointsSource.length)) {
        //if there is an even number of bend points, this means that there is an odd number of segments
        const prevPoint: XYPosition | undefined =
          pointsSource.length === 0 ? source : pointsSource[Math.floor(pointsSource.length / 2) - 1];
        const middlePoint: XYPosition | undefined =
          pointsSource.length === 0 ? target : pointsSource[Math.floor(pointsSource.length / 2)];
        return prevPoint && middlePoint ? getMiddlePoint(prevPoint, middlePoint) : undefined; //Place in the center of the middle segment
      } else {
        return pointsSource[Math.floor(pointsSource.length / 2)]; //Place on the middle point
      }
    }, [source, target, bendingPoints.map((point) => `${point.x}:${point.y}`).join()]);

    const edgePath: string = useMemo(() => {
      let edgePath = `M ${source.x} ${source.y}`;
      const reorderBendPoint = [...localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
      for (let i = 0; i < reorderBendPoint.length; i++) {
        const currentPoint = reorderBendPoint[i];
        if (currentPoint) {
          edgePath += ` L ${currentPoint.x} ${currentPoint.y}`;
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
          className={`target_handle_${targetPosition} source_handle_${sourcePosition}`}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 2)}--selected')` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 2)}--selected')` : markerStart}
        />
        {selected ? <EdgeCreationHandle edgeId={id} edgePath={edgePath}></EdgeCreationHandle> : null}
        {selected &&
          localBendingPoints &&
          localBendingPoints.map((point, index) => {
            const reorderBendPoint = [...localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
            const prevPoint = reorderBendPoint[point.pathOrder - 1];
            const direction = prevPoint ? determineSegmentAxis(prevPoint, point) : determineSegmentAxis(source, point);
            return (
              <BendPoint
                key={index}
                x={point.x}
                y={point.y}
                index={index}
                direction={direction}
                onDrag={onBendingPointDrag}
                onDragStop={onBendingPointDragStop}
              />
            );
          })}
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
        {data ? (
          <DraggableEdgeLabels
            id={id}
            data={data}
            selected={!!selected}
            sourcePosition={sourcePosition}
            targetPosition={targetPosition}
            sourceX={sourceX}
            sourceY={sourceY}
            targetX={targetX}
            targetY={targetY}
            edgeCenter={edgeCenter}
          />
        ) : null}
      </>
    );
  }
);
