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

import { XYPosition } from '@xyflow/react';
import { determineSegmentAxis, getMiddlePoint, isMultipleOfTwo } from '../rectilinear-edge/RectilinearEdgeCalculation';
import { BendPointData } from '../rectilinear-edge/BendingPointsCalculation.types';
import { computeMiddlePoints } from '../rectilinear-edge/TemporaryLinesCalculation';
import { MiddlePoint } from '../rectilinear-edge/TemporaryLinesCalculation.types';
import { EdgeGeometry } from './edgeGeometry.types';

export const createGeometry = (
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
