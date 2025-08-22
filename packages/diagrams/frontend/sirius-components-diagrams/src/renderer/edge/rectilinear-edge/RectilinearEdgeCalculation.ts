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

export const generateNewSourcePoint = (
  source: XYPosition,
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  sourceNode: InternalNode<Node<NodeData>>
): XYPosition => {
  const newSource: XYPosition = { ...source };
  if (direction === 'x' && sourceNode.internals.positionAbsolute.y > newY) {
    newSource.y = sourceNode.internals.positionAbsolute.y;
  } else if (direction === 'y' && sourceNode.internals.positionAbsolute.x > newX) {
    newSource.x = sourceNode.internals.positionAbsolute.x;
  } else if (direction === 'x' && sourceNode.internals.positionAbsolute.y + (sourceNode.height ?? 0) < newY) {
    newSource.y = sourceNode.internals.positionAbsolute.y + (sourceNode.height ?? 0);
  } else if (direction === 'y' && sourceNode.internals.positionAbsolute.x + (sourceNode.width ?? 0) < newX) {
    newSource.x = sourceNode.internals.positionAbsolute.x + (sourceNode.width ?? 0);
  }
  return newSource;
};

export const generateNewTargetPoint = (
  target: XYPosition,
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  targetNode: InternalNode<Node<NodeData>>
): XYPosition => {
  const newTarget: XYPosition = { ...target };
  if (direction === 'x' && targetNode.internals.positionAbsolute.y > newY) {
    newTarget.y = targetNode.internals.positionAbsolute.y;
  } else if (direction === 'y' && targetNode.internals.positionAbsolute.x > newX) {
    newTarget.x = targetNode.internals.positionAbsolute.x;
  } else if (direction === 'x' && targetNode.internals.positionAbsolute.y + (targetNode.height ?? 0) < newY) {
    newTarget.y = targetNode.internals.positionAbsolute.y + (targetNode.height ?? 0);
  } else if (direction === 'y' && targetNode.internals.positionAbsolute.x + (targetNode.width ?? 0) < newX) {
    newTarget.x = targetNode.internals.positionAbsolute.x + (targetNode.width ?? 0);
  }
  return newTarget;
};

export const generateNewBendPointOnSourceSegment = (
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  sourceNodeRelativePosition: XYPosition,
  sourcePosition: Position,
  sourceNodeHeight: number,
  sourceNodeWidth: number
): BendPointData | null => {
  let newPoint: BendPointData | null = null;
  if (sourcePosition === Position.Top) {
    newPoint = {
      x: direction === 'x' ? sourceNodeRelativePosition.x : newX,
      y: direction === 'y' ? sourceNodeRelativePosition.y : newY,
      pathOrder: 0,
    };
  } else if (sourcePosition === Position.Bottom) {
    newPoint = {
      x: direction === 'x' ? sourceNodeRelativePosition.x + sourceNodeHeight : newX,
      y: direction === 'y' ? sourceNodeRelativePosition.y + sourceNodeHeight : newY,
      pathOrder: 0,
    };
  } else if (sourcePosition === Position.Left) {
    newPoint = {
      x: direction === 'x' ? sourceNodeRelativePosition.x : newX,
      y: direction === 'y' ? sourceNodeRelativePosition.y : newY,
      pathOrder: 0,
    };
  } else if (sourcePosition === Position.Right) {
    newPoint = {
      x: direction === 'x' ? sourceNodeRelativePosition.x + sourceNodeWidth : newX,
      y: direction === 'y' ? sourceNodeRelativePosition.y + sourceNodeWidth : newY,
      pathOrder: 0,
    };
  }
  return newPoint;
};

export const generateNewBendPointOnTargetSegment = (
  newX: number,
  newY: number,
  direction: 'x' | 'y',
  targetNodeRelativePosition: XYPosition,
  adjacentPointIndex: number,
  targetPosition: Position,
  targetNodeHeight: number,
  targetNodeWidth: number
): BendPointData | null => {
  let newPoint: BendPointData | null = null;
  if (targetPosition === Position.Top) {
    newPoint = {
      x: direction === 'x' ? targetNodeRelativePosition.x : newX,
      y: direction === 'y' ? targetNodeRelativePosition.y : newY,
      pathOrder: adjacentPointIndex + 1,
    };
  } else if (targetPosition === Position.Bottom) {
    newPoint = {
      x: direction === 'x' ? targetNodeRelativePosition.x + targetNodeHeight : newX,
      y: direction === 'y' ? targetNodeRelativePosition.y + targetNodeHeight : newY,
      pathOrder: adjacentPointIndex + 1,
    };
  } else if (targetPosition === Position.Left) {
    newPoint = {
      x: direction === 'x' ? targetNodeRelativePosition.x : newX,
      y: direction === 'y' ? targetNodeRelativePosition.y : newY,
      pathOrder: adjacentPointIndex + 1,
    };
  } else if (targetPosition === Position.Right) {
    newPoint = {
      x: direction === 'x' ? targetNodeRelativePosition.x + targetNodeWidth : newX,
      y: direction === 'y' ? targetNodeRelativePosition.y + targetNodeWidth : newY,
      pathOrder: adjacentPointIndex + 1,
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
