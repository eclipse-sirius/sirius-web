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

import { Position, XYPosition } from '@xyflow/react';
import { determineSegmentAxis, getMiddlePoint, isMultipleOfTwo } from '../rectilinear-edge/RectilinearEdgeCalculation';
import { BendPointData } from '../rectilinear-edge/BendingPointsCalculation.types';
import { computeMiddlePoints } from '../rectilinear-edge/TemporaryLinesCalculation';
import { MiddlePoint } from '../rectilinear-edge/TemporaryLinesCalculation.types';
import { EdgeDragControl, EdgeDragData, EdgeGeometry, EdgeGeometryInput } from './edgeGeometry.types';

const copyPoints = (points: XYPosition[]): BendPointData[] => {
  return points.map((point, index) => {
    return { ...point, pathOrder: index };
  });
};

const createGeometry = (
  source: XYPosition,
  target: XYPosition,
  bendingPoints: BendPointData[],
  customEdge: boolean,
  middleBendingPoints: MiddlePoint[] | null = null,
  isDragging: boolean = false
): EdgeGeometry => {
  const orderedPoints = [...bendingPoints].sort((left, right) => {
    return left.pathOrder - right.pathOrder;
  });
  let path = `M ${source.x} ${source.y}`;
  orderedPoints.forEach((point) => {
    path += ` L ${point.x} ${point.y}`;
  });
  path += ` L ${target.x} ${target.y}`;

  let center: EdgeGeometry['center'];
  if (isMultipleOfTwo(orderedPoints.length)) {
    const previousPoint = orderedPoints.length === 0 ? source : orderedPoints[orderedPoints.length / 2 - 1];
    const nextPoint = orderedPoints.length === 0 ? target : orderedPoints[orderedPoints.length / 2];
    center =
      previousPoint && nextPoint
        ? {
            position: getMiddlePoint(previousPoint, nextPoint),
            segmentDirection: determineSegmentAxis(previousPoint, nextPoint),
          }
        : { position: { x: 0, y: 0 }, segmentDirection: null };
  } else {
    const point = orderedPoints[Math.floor(orderedPoints.length / 2)];
    center = { position: point ? { x: point.x, y: point.y } : { x: 0, y: 0 }, segmentDirection: null };
  }
  return {
    isDragging,
    source,
    target,
    bendingPoints,
    middlePoints: middleBendingPoints ?? computeMiddlePoints(bendingPoints, source, target),
    path,
    center,
    customEdge,
  };
};

/** Resolves terminal bend coordinates without constructing drawing-only geometry. */
export const getBendingPoints = (input: EdgeGeometryInput): BendPointData[] => {
  const points = copyPoints(input.bendingPoints);
  if (input.customEdge) {
    const firstPoint = points[0];
    if (firstPoint) {
      if (input.sourcePosition === Position.Left || input.sourcePosition === Position.Right) {
        firstPoint.y = input.source.y;
      } else {
        firstPoint.x = input.source.x;
      }
    }
    const lastPoint = points[points.length - 1];
    if (lastPoint) {
      if (input.targetPosition === Position.Left || input.targetPosition === Position.Right) {
        lastPoint.y = input.target.y;
      } else {
        lastPoint.x = input.target.x;
      }
    }
  }
  return points;
};

/** Derives drawing fields from the same bend calculation used by node changes. */
export const computeEdgeGeometry = (input: EdgeGeometryInput): EdgeGeometry => {
  return createGeometry({ ...input.source }, { ...input.target }, getBendingPoints(input), input.customEdge);
};

/** Captures the original geometry before any handle visibility or measurement changes. */
export const initializeEdgeDrag = (input: EdgeGeometryInput, control: EdgeDragControl): EdgeDragData => {
  const geometry = computeEdgeGeometry(input);
  const originalGeometry: EdgeGeometryInput = {
    ...input,
    source: { ...geometry.source },
    target: { ...geometry.target },
    sourceNode: {
      ...input.sourceNode,
      internals: {
        ...input.sourceNode.internals,
        positionAbsolute: { ...input.sourceNode.internals.positionAbsolute },
      },
    },
    targetNode: {
      ...input.targetNode,
      internals: {
        ...input.targetNode.internals,
        positionAbsolute: { ...input.targetNode.internals.positionAbsolute },
      },
    },
    bendingPoints: geometry.bendingPoints.map((point) => {
      return { x: point.x, y: point.y };
    }),
  };
  return {
    ...control,
    edgeId: input.edgeId,
    originalGeometry,
    geometry: { ...geometry, isDragging: true },
    isSourceSegment: false,
    isTargetSegment: false,
    middleBendingPoints: geometry.middlePoints.map((point) => {
      return { ...point };
    }),
  };
};
