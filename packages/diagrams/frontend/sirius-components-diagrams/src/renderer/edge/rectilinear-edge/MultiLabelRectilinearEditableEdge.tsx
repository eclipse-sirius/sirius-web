/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import { useTheme } from '@mui/material/styles';
import { BaseEdge, Edge } from '@xyflow/react';
import { memo, useMemo, useContext } from 'react';
import { EdgeGeometryContext } from '../../../contexts/EdgeGeometryContext';
import { useConnectorEdgeStyle } from '../../connector/useConnectorEdgeStyle';
import { BendPoint, TemporaryMovingLine } from '../BendPoint';
import { buildCrossingDashArray } from '../crossings/buildCrossingDashArray';
import { DraggableEdgeLabels } from '../DraggableEdgeLabels';
import { EdgeCreationHandle } from '../EdgeCreationHandle';
import { multiLabelEdgeStyle } from '../MultiLabelEdge';
import { MultiLabelEdgeData } from '../MultiLabelEdge.types';
import { MultiLabelEditableEdgeProps } from './MultiLabelRectilinearEditableEdge.types';
import { determineSegmentAxis } from './RectilinearEdgeCalculation';

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
    geometry,
  }: MultiLabelEditableEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const faded = !!data?.faded;
    const { onDragStart, onBendingPointDrag, onBendingPointDragStop, onTemporaryLineDrag, onTemporaryLineDragStop } =
      useContext(EdgeGeometryContext);
    const theme = useTheme();
    const { source, target, bendingPoints, middlePoints, path: edgePath, center: edgeCenter } = geometry;

    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);
    const { style: connectionFeedbackStyle } = useConnectorEdgeStyle(data ? data.descriptionId : '', !!data?.isHovered);

    const hasDashOverride = useMemo(() => {
      return Boolean(style?.strokeDasharray || connectionFeedbackStyle?.strokeDasharray);
    }, [style?.strokeDasharray, connectionFeedbackStyle?.strokeDasharray]);

    const crossingDashArray = useMemo(() => {
      if (!data?.crossingGaps || data.crossingGaps.length === 0) {
        return null;
      }
      if (hasDashOverride) {
        return null;
      }
      return buildCrossingDashArray(edgePath, data.crossingGaps);
    }, [data?.crossingGaps, edgePath, hasDashOverride]);

    const strokeStyle: React.CSSProperties = useMemo(() => {
      const mergedStyle = {
        ...edgeStyle,
        ...connectionFeedbackStyle,
      };
      if (!crossingDashArray) {
        return mergedStyle;
      }
      return {
        ...mergedStyle,
        strokeDasharray: crossingDashArray,
        strokeLinecap: 'round',
      };
    }, [connectionFeedbackStyle, crossingDashArray, edgeStyle]);

    return (
      <>
        <BaseEdge
          id={id}
          path={edgePath}
          style={strokeStyle}
          className={`target_handle_${targetPosition} source_handle_${sourcePosition}`}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 2)}--selected')` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 2)}--selected')` : markerStart}
        />
        {selected ? (
          <EdgeCreationHandle edgeId={id} edgePath={edgePath} isPathDragged={geometry.isDragging}></EdgeCreationHandle>
        ) : null}
        {selected &&
          bendingPoints &&
          bendingPoints.map((point, index) => {
            const reorderBendPoint = [...bendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
            const prevPoint = reorderBendPoint[point.pathOrder - 1];
            const direction = prevPoint ? determineSegmentAxis(prevPoint, point) : determineSegmentAxis(source, point);
            return (
              <BendPoint
                key={index}
                x={point.x}
                y={point.y}
                index={index}
                direction={direction}
                onDragStart={(_eventData, index, direction) => onDragStart(id, index, direction, false)}
                onDrag={(eventData, index, direction) => onBendingPointDrag(id, eventData, index, direction)}
                onDragStop={(eventData, index) => onBendingPointDragStop(id, eventData, index)}
                temporary={false}
              />
            );
          })}
        {selected &&
          middlePoints &&
          middlePoints.map((point, index) => (
            <TemporaryMovingLine
              key={index}
              x={point.x}
              y={point.y}
              direction={point.direction}
              segmentLength={point.segmentLength}
              index={index}
              onDragStart={(_eventData, index, direction) => onDragStart(id, index, direction, true)}
              onDrag={(eventData, index, direction) => onTemporaryLineDrag(id, eventData, index, direction)}
              onDragStop={(eventData, index) => onTemporaryLineDragStop(id, eventData, index)}
            />
          ))}
        {data ? (
          <DraggableEdgeLabels
            id={id}
            data={data}
            selected={!!selected}
            sourcePosition={sourcePosition}
            targetPosition={targetPosition}
            sourceX={source.x}
            sourceY={source.y}
            targetX={target.x}
            targetY={target.y}
            edgeCenterPosition={edgeCenter.position}
            edgeCenterSegmentDirection={edgeCenter.segmentDirection}
          />
        ) : null}
      </>
    );
  }
);
