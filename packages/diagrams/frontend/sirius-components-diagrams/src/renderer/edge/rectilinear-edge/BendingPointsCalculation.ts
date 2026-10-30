/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import type { EdgeDragData } from '../geometry/edgeGeometry.types';
import { BendingPointsCalculationValue } from './BendingPointsCalculation.types';
import { generateNewBendPointOnSegment, generateNewHandlePoint, isOutOfLines } from './RectilinearEdgeCalculation';

export const calculateBendingPoints = (
  edgeDragData: EdgeDragData,
  eventData: XYPosition
): BendingPointsCalculationValue => {
  const { originalGeometry, index, direction } = edgeDragData;
  const {
    bendingPoints: originalBendingPoints,
    sourceNode,
    targetNode,
    sourcePosition,
    targetPosition,
  } = originalGeometry;
  let source = { ...originalGeometry.source };
  let target = { ...originalGeometry.target };
  let isSourceSegment = edgeDragData.isSourceSegment;
  let isTargetSegment = edgeDragData.isTargetSegment;
  const newPoints = [...originalBendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }))];
  const bendingPointDragged = newPoints[index];
  if (bendingPointDragged) {
    bendingPointDragged.x = eventData.x;
    bendingPointDragged.y = eventData.y;
    if (index === 0) {
      if (isOutOfLines(eventData.x, eventData.y, direction, sourceNode)) {
        const newPoint = generateNewBendPointOnSegment(
          eventData.x,
          eventData.y,
          direction,
          sourceNode.internals.positionAbsolute,
          sourcePosition,
          sourceNode.height ?? 0,
          sourceNode.width ?? 0,
          0
        );
        if (newPoint) {
          const nextPoint = newPoints[index + 1];
          newPoints.forEach((point) => {
            point.pathOrder += 1;
          });
          newPoints.push(newPoint);
          if (nextPoint) {
            if (direction === 'y') {
              nextPoint.y = eventData.y;
            } else if (direction === 'x') {
              nextPoint.x = eventData.x;
            }
          }
        }
        const newSource: XYPosition = generateNewHandlePoint(source, eventData.x, eventData.y, direction, sourceNode);
        source = newSource;
      } else {
        const newSource: XYPosition = { ...source };
        const nextPoint = newPoints[index + 1];
        if (direction === 'x') {
          newSource.y = eventData.y;
          newSource.x =
            sourceNode.internals.positionAbsolute.x + (sourcePosition === Position.Right ? sourceNode.width ?? 0 : 0);
          if (nextPoint) {
            nextPoint.x = eventData.x;
          }
        } else if (direction === 'y') {
          newSource.x = eventData.x;
          newSource.y =
            sourceNode.internals.positionAbsolute.y + (sourcePosition === Position.Bottom ? sourceNode.height ?? 0 : 0);
          if (nextPoint) {
            nextPoint.y = eventData.y;
          }
        }
        source = newSource;
        isSourceSegment = true;
      }
    }
    if (index === originalBendingPoints.length - 1) {
      const lastSegmentDirection = direction === 'x' ? 'y' : 'x';
      if (isOutOfLines(eventData.x, eventData.y, lastSegmentDirection, targetNode)) {
        const newPoint = generateNewBendPointOnSegment(
          eventData.x,
          eventData.y,
          lastSegmentDirection,
          targetNode.internals.positionAbsolute,
          targetPosition,
          targetNode.height ?? 0,
          targetNode.width ?? 0,
          index + 1
        );
        if (newPoint) {
          newPoints.push(newPoint);
          const prevPoint = newPoints[index - 1];
          if (prevPoint) {
            if (direction === 'x') {
              prevPoint.y = eventData.y;
            } else if (direction === 'y') {
              prevPoint.x = eventData.x;
            }
          }
        }
        const newTarget: XYPosition = generateNewHandlePoint(
          target,
          eventData.x,
          eventData.y,
          lastSegmentDirection,
          targetNode
        );
        target = newTarget;
      } else {
        const newTarget: XYPosition = { ...target };
        const prevPoint = newPoints[index - 1];
        if (direction === 'y') {
          newTarget.y = eventData.y;
          newTarget.x =
            targetNode.internals.positionAbsolute.x + (targetPosition === Position.Right ? targetNode.width ?? 0 : 0);
          if (prevPoint) {
            prevPoint.x = eventData.x;
          }
        } else if (direction === 'x') {
          newTarget.x = eventData.x;
          newTarget.y =
            targetNode.internals.positionAbsolute.y + (targetPosition === Position.Bottom ? targetNode.height ?? 0 : 0);
          if (prevPoint) {
            prevPoint.y = eventData.y;
          }
        }
        target = newTarget;
        isTargetSegment = true;
      }
    }
    if (index > 0 && index < originalBendingPoints.length) {
      const nextPoint = newPoints[index + 1];
      const prevPoint = newPoints[index - 1];

      if (prevPoint && nextPoint) {
        if (direction === 'x') {
          nextPoint.x = eventData.x;
          prevPoint.y = eventData.y;
        } else if (direction === 'y') {
          prevPoint.x = eventData.x;
          nextPoint.y = eventData.y;
        }
      }
    }
  }

  return {
    source,
    target,
    localBendingPoints: newPoints,
    isSourceSegment,
    isTargetSegment,
  };
};
