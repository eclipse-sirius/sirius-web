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
import { InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { NodeData } from '../../DiagramRenderer.types';
import { edgeBendPointOffset } from '../../layout/layoutParams';
import { BendPointData } from './useBendingPoints.types';

export const getMiddlePoint = (p1: XYPosition, p2: XYPosition): XYPosition => {
  return { x: (p1.x + p2.x) / 2, y: (p1.y + p2.y) / 2 };
};

export const determineSegmentAxis = (p1: XYPosition, p2: XYPosition): 'x' | 'y' => {
  const deltaX = Math.abs(p2.x - p1.x);
  const deltaY = Math.abs(p2.y - p1.y);

  if (deltaX > deltaY) {
    return 'x';
  }
  return 'y';
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
            const prevPoint = bendPoints[i - 1];
            const nextNextPoint = bendPoints[i + 2];
            i++;
            if (prevPoint && nextNextPoint) {
              if (determineSegmentAxis(prevPoint, nextNextPoint) === 'y') {
                nextNextPoint.x = prevPoint.x;
              } else {
                nextNextPoint.y = prevPoint.y;
              }
            }
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

export const isOutOfLines = (
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  node: InternalNode<Node<NodeData>>
): boolean => {
  return (
    (direction === 'x' && node.internals.positionAbsolute.y > newY) ||
    (direction === 'y' && node.internals.positionAbsolute.x > newX) ||
    (direction === 'x' && node.internals.positionAbsolute.y + (node.height ?? 0) < newY) ||
    (direction === 'y' && node.internals.positionAbsolute.x + (node.width ?? 0) < newX)
  );
};

export const generateNewHandlePoint = (
  handleXYPosition: XYPosition,
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  node: InternalNode<Node<NodeData>>
): XYPosition => {
  const newHandlePosition: XYPosition = { ...handleXYPosition };
  if (direction === 'x' && node.internals.positionAbsolute.y > newY) {
    newHandlePosition.y = node.internals.positionAbsolute.y;
    newHandlePosition.x = node.internals.positionAbsolute.x + (node.width ?? 0) / 2;
  } else if (direction === 'y' && node.internals.positionAbsolute.x > newX) {
    newHandlePosition.x = node.internals.positionAbsolute.x;
    newHandlePosition.y = node.internals.positionAbsolute.y + (node.height ?? 0) / 2;
  } else if (direction === 'x' && node.internals.positionAbsolute.y + (node.height ?? 0) < newY) {
    newHandlePosition.y = node.internals.positionAbsolute.y + (node.height ?? 0);
    newHandlePosition.x = node.internals.positionAbsolute.x + (node.width ?? 0) / 2;
  } else if (direction === 'y' && node.internals.positionAbsolute.x + (node.width ?? 0) < newX) {
    newHandlePosition.x = node.internals.positionAbsolute.x + (node.width ?? 0);
    newHandlePosition.y = node.internals.positionAbsolute.y + (node.height ?? 0) / 2;
  }
  return newHandlePosition;
};

export const generateNewBendPointOnSegment = (
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  nodeXYPosition: XYPosition,
  position: Position,
  nodeHeight: number,
  nodeWidth: number,
  newPointPathOrder: number
): BendPointData | null => {
  let newPoint: BendPointData | null = null;
  if (position === Position.Top) {
    newPoint = {
      x: direction === 'x' ? nodeXYPosition.x : newX,
      y: direction === 'y' ? nodeXYPosition.y + nodeHeight / 2 : newY,
      pathOrder: newPointPathOrder,
    };
  } else if (position === Position.Bottom) {
    newPoint = {
      x: direction === 'x' ? nodeXYPosition.x + nodeHeight : newX,
      y: direction === 'y' ? nodeXYPosition.y + nodeHeight / 2 : newY,
      pathOrder: newPointPathOrder,
    };
  } else if (position === Position.Left) {
    newPoint = {
      x: direction === 'x' ? nodeXYPosition.x + nodeWidth / 2 : newX,
      y: direction === 'y' ? nodeXYPosition.y : newY,
      pathOrder: newPointPathOrder,
    };
  } else if (position === Position.Right) {
    newPoint = {
      x: direction === 'x' ? nodeXYPosition.x + nodeWidth / 2 : newX,
      y: direction === 'y' ? nodeXYPosition.y + nodeWidth : newY,
      pathOrder: newPointPathOrder,
    };
  }
  return newPoint;
};

export const getHandlePositionFromXYPosition = (
  node: InternalNode<Node<NodeData>>,
  handleXYPosition: XYPosition,
  segmentDirection: 'x' | 'y'
): Position => {
  if (segmentDirection === 'x') {
    if (handleXYPosition.x < node.internals.positionAbsolute.x + (node.width ?? 0) / 2) {
      return Position.Left;
    } else {
      return Position.Right;
    }
  } else {
    if (handleXYPosition.y < node.internals.positionAbsolute.y + (node.height ?? 0) / 2) {
      return Position.Top;
    } else {
      return Position.Bottom;
    }
  }
};

export const getNewPointToGoAroundNode = (
  segmentAxis: 'x' | 'y',
  position: Position,
  pointX: number,
  pointY: number
): XYPosition | null => {
  if (segmentAxis === 'x' && (position === Position.Right || position === Position.Left)) {
    const newPointX = position === Position.Right ? pointX + edgeBendPointOffset : pointX - edgeBendPointOffset;
    return { x: newPointX, y: pointY };
  } else if (segmentAxis === 'y' && (position === Position.Top || position === Position.Bottom)) {
    const newPointY = position === Position.Bottom ? pointY + edgeBendPointOffset : pointY - edgeBendPointOffset;
    return { x: pointX, y: newPointY };
  }
  return null;
};
