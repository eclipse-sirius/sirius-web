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
import { XYPosition } from '@xyflow/react';
import { SegmentDirection } from '../EdgeLayout.types';
import { BendPointData } from './useBendingPoints.types';

export const getMiddlePoint = (p1: XYPosition, p2: XYPosition): XYPosition => {
  return { x: (p1.x + p2.x) / 2, y: (p1.y + p2.y) / 2 };
};

export const determineSegmentAxis = (p1: XYPosition, p2: XYPosition): SegmentDirection => {
  const deltaX = Math.abs(p2.x - p1.x);
  const deltaY = Math.abs(p2.y - p1.y);

  if (deltaX > deltaY) {
    return SegmentDirection.HORIZONTAL;
  }
  return SegmentDirection.VERTICAL;
};

export const cleanBendPoint = (bendPoints: XYPosition[]): XYPosition[] => {
  const cleanedPoints: XYPosition[] = [];

  const margin = 10;
  for (let i = 0; i < bendPoints.length; i++) {
    const currentPoint = bendPoints[i];
    if (currentPoint) {
      const { x: x1, y: y1 } = currentPoint;
      let isSimilar = false;

      if (i < bendPoints.length - 1) {
        const nextPoint = bendPoints[i + 1];
        if (nextPoint) {
          const { x: x2, y: y2 } = nextPoint;
          if (Math.abs(x1 - x2) <= margin && Math.abs(y1 - y2) <= margin) {
            isSimilar = true;
            i++;
          }
        }
      }

      if (!isSimilar) {
        cleanedPoints.push({ x: x1, y: y1 });
      }
    }
  }
  return cleanedPoints;
};

export const generateNewBendPointToPreserveRectilinearSegment = (
  existingBendPoint: XYPosition[],
  bendPointIndex: number,
  newX: number,
  newY: number,
  prevMiddlePoint: XYPosition,
  nextMiddlePoint: XYPosition
): BendPointData[] => {
  const newPoints = [...existingBendPoint.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }))];
  const currentPoint = newPoints[bendPointIndex];
  if (currentPoint) {
    newPoints.forEach((point) => {
      if (point.pathOrder > bendPointIndex) {
        point.pathOrder += 4;
      }
    });

    const segmentDirection: SegmentDirection = determineSegmentAxis(prevMiddlePoint, currentPoint);
    newPoints.push({
      ...prevMiddlePoint,
      pathOrder: bendPointIndex,
    });
    newPoints.push({
      x: segmentDirection === SegmentDirection.HORIZONTAL ? prevMiddlePoint.x : newX,
      y: segmentDirection === SegmentDirection.HORIZONTAL ? newY : prevMiddlePoint.y,
      pathOrder: bendPointIndex + 1,
    });
    newPoints[bendPointIndex] = {
      x: newX,
      y: newY,
      pathOrder: bendPointIndex + 2,
    };
    newPoints.push({
      x: segmentDirection === SegmentDirection.HORIZONTAL ? newX : nextMiddlePoint.x,
      y: segmentDirection === SegmentDirection.HORIZONTAL ? nextMiddlePoint.y : newY,
      pathOrder: bendPointIndex + 3,
    });
    newPoints.push({ ...nextMiddlePoint, pathOrder: bendPointIndex + 4 });
  }
  return newPoints;
};
