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
import { memo, useMemo, useEffect } from 'react';
import { MultiLabelEdgeData } from '../MultiLabelEdge.types';
import { Edge, BaseEdge, XYPosition } from '@xyflow/react';
import { useTheme } from '@mui/material/styles';
import { useStore } from '../../../representation/useStore';
import { buildCrossingDashArray } from '../crossings/buildCrossingDashArray';
import { BendPoint } from '../BendPoint';
import { DraggableEdgeLabels } from '../DraggableEdgeLabels';
import { multiLabelEdgeStyle } from '../MultiLabelEdge';
import { isMultipleOfTwo, getMiddlePoint } from '../rectilinear-edge/RectilinearEdgeCalculation';
import { useBendingPoints } from './useBendingPoints';
import { MultiLabelObliqueEditableEdgeProps } from './MultiLabelObliqueEditableEdge.types';

export const MultiLabelObliqueEditableEdge = memo(
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
  }: MultiLabelObliqueEditableEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const faded = !!data?.faded;
    const theme = useTheme();
    const { setEdges } = useStore();

    const { localBendingPoints, draggingPointIndex, onBendingPointDragStop, onBendingPointDrag } = useBendingPoints(
      id,
      bendingPoints,
      sourceX,
      sourceY,
      targetX,
      targetY
    );

    const edgePath: string = useMemo(() => {
      let edgePath = `M ${sourceX} ${sourceY}`;
      const persistedBendingPoints = localBendingPoints.filter(
        (_, index) => !isMultipleOfTwo(index) || index === draggingPointIndex
      );
      for (let i = 0; i < persistedBendingPoints.length; i++) {
        const currentPoint = persistedBendingPoints[i];
        if (currentPoint) {
          edgePath += ` L ${currentPoint.x} ${currentPoint.y}`;
        }
      }
      edgePath += ` L ${targetX} ${targetY}`;
      return edgePath;
    }, [localBendingPoints.map((point) => point.x + ':' + point.y).join(), sourceX, sourceY, targetX, targetY]);

    const edgeCenter: XYPosition = useMemo(() => {
      if (isMultipleOfTwo(bendingPoints.length)) {
        //if there is an even number of bend points, this means that there is an odd number of segments
        const prevPoint: XYPosition | undefined =
          bendingPoints.length === 0
            ? { x: sourceX, y: sourceY }
            : bendingPoints[Math.floor(bendingPoints.length / 2) - 1];
        const middlePoint: XYPosition | undefined =
          bendingPoints.length === 0 ? { x: targetX, y: targetY } : bendingPoints[Math.floor(bendingPoints.length / 2)];
        return prevPoint && middlePoint ? getMiddlePoint(prevPoint, middlePoint) : { x: 0, y: 0 }; //Place in the center of the middle segment
      } else {
        return bendingPoints[Math.floor(bendingPoints.length / 2)] ?? { x: 0, y: 0 }; //Place on the middle point
      }
    }, [bendingPoints.map((point) => `${point.x}:${point.y}`).join(), sourceX, sourceY, targetX, targetY]);

    const baseEdgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);

    const crossingDashArray = useMemo(() => {
      if (!data?.crossingGaps || data.crossingGaps.length === 0) {
        return null;
      }
      if (style?.strokeDasharray) {
        return null;
      }
      return buildCrossingDashArray(edgePath, data.crossingGaps);
    }, [data?.crossingGaps, edgePath, style?.strokeDasharray]);

    const edgeStyle: React.CSSProperties = useMemo(() => {
      if (!crossingDashArray) {
        return baseEdgeStyle;
      }
      return {
        ...baseEdgeStyle,
        strokeDasharray: crossingDashArray,
        strokeLinecap: 'round',
      };
    }, [baseEdgeStyle, crossingDashArray]);

    useEffect(() => {
      setEdges((prevEdges) =>
        prevEdges.map((prevEdge) => {
          if (prevEdge.id === id && prevEdge.data) {
            return {
              ...prevEdge,
              data: {
                ...prevEdge.data,
                edgePath: edgePath,
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
          style={edgeStyle}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 2)}--selected')` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 2)}--selected')` : markerStart}
        />
        {selected &&
          localBendingPoints &&
          localBendingPoints.map((point, index) => {
            return (
              <BendPoint
                key={index}
                x={point.x}
                y={point.y}
                index={index}
                direction={'x'}
                onDrag={onBendingPointDrag}
                onDragStop={onBendingPointDragStop}
                temporary={isMultipleOfTwo(index)}
              />
            );
          })}
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
