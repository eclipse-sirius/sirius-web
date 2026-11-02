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
import { Edge, getSmoothStepPath, InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { NodeLookup } from '@xyflow/system';
import parse from 'svg-path-parser';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { INodeLayoutHandler } from '../../layout/LayoutEngine.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { getEdgeEndpointCoordinates } from '../EdgeLayout';
import { EdgeGeometryInput } from './edgeGeometry.types';

const isMultipleOfTwo = (num: number): boolean => {
  return num % 2 === 0;
};

const roundToDecimal = (num: number): number => {
  return Math.round(num * 10) / 10;
};

const isVerticalPosition = (position: Position): boolean => {
  return position === Position.Top || position === Position.Bottom;
};

const isHorizontalPosition = (position: Position): boolean => {
  return position === Position.Left || position === Position.Right;
};

/** Derived alignment and routing never change canonical handles or bend points. */
export const resolveEdgeGeometryInput = (
  edge: Edge<EdgeData>,
  nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>,
  nodeLayoutHandlers: INodeLayoutHandler<NodeData>[]
): EdgeGeometryInput | null => {
  const sourceNode = nodeLookup.get(edge.source);
  const targetNode = nodeLookup.get(edge.target);
  if (!sourceNode || !targetNode) {
    return null;
  }
  const sourceHandleId = edge.sourceHandle ?? '';
  const targetHandleId = edge.targetHandle ?? '';
  const sourceHandle = [
    ...(sourceNode.internals.handleBounds?.source ?? []),
    ...(sourceNode.internals.handleBounds?.target ?? []),
  ].find((handle) => {
    return handle.id === sourceHandleId;
  });
  const targetHandle = [
    ...(targetNode.internals.handleBounds?.source ?? []),
    ...(targetNode.internals.handleBounds?.target ?? []),
  ].find((handle) => {
    return handle.id === targetHandleId;
  });
  if (!sourceHandle || !targetHandle) {
    return null;
  }
  const sourcePosition = sourceHandle.position;
  const targetPosition = targetHandle.position;
  const data = edge.data;
  const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
  );

  let { x: sourceX, y: sourceY } = getEdgeEndpointCoordinates(
    sourceNode,
    sourcePosition,
    sourceHandleId ?? '',
    edge.markerStart,
    sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );
  let { x: targetX, y: targetY } = getEdgeEndpointCoordinates(
    targetNode,
    targetPosition,
    targetHandleId ?? '',
    edge.markerEnd,
    targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );

  const rawSource = { x: sourceX, y: sourceY };
  const rawTarget = { x: targetX, y: targetY };
  let alignedX = false;
  let alignedY = false;

  const threshold = 10;
  if (
    Math.abs(sourceX - targetX) < threshold &&
    isVerticalPosition(sourcePosition) &&
    isVerticalPosition(targetPosition)
  ) {
    const middleX = (sourceX + targetX) / 2;
    sourceX = middleX;
    targetX = middleX;
    alignedX = true;
  }
  if (
    Math.abs(sourceY - targetY) < threshold &&
    isHorizontalPosition(sourcePosition) &&
    isHorizontalPosition(targetPosition)
  ) {
    const middleY = (sourceY + targetY) / 2;
    sourceY = middleY;
    targetY = middleY;
    alignedY = true;
  }

  let bendingPoints: XYPosition[] = [];
  const customEdge = !alignedX && !alignedY && !!data?.bendingPoints?.length;
  if (customEdge && data?.bendingPoints) {
    if (data.relativePositionBendingPoints?.length && sourceNode.data.moving && targetNode.data.moving) {
      bendingPoints = data.relativePositionBendingPoints.map((relativePositionBendingPoint) => ({
        x: relativePositionBendingPoint.x + sourceX,
        y: relativePositionBendingPoint.y + sourceY,
      }));
    } else {
      bendingPoints = data.bendingPoints.map((bendingPoint) => ({ ...bendingPoint }));
    }
  } else {
    const [smoothEdgePath] = getSmoothStepPath({
      sourceX: roundToDecimal(sourceX),
      sourceY: roundToDecimal(sourceY),
      sourcePosition,
      targetX: roundToDecimal(targetX),
      targetY: roundToDecimal(targetY),
      targetPosition,
    });

    const quadraticCurvePoints: {
      x: number;
      y: number;
    }[] = smoothEdgePath.includes('NaN') ? [] : parse(smoothEdgePath).filter((segment) => segment.code === 'Q');

    if (quadraticCurvePoints.length > 0) {
      const firstPoint = quadraticCurvePoints[0];
      if (firstPoint) {
        switch (sourcePosition) {
          case Position.Right:
          case Position.Left:
            bendingPoints.push({ x: firstPoint.x, y: sourceY });
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
                if (isMultipleOfTwo(i)) {
                  bendingPoints.push({ x: currentPoint.x, y: previousPoint.y });
                } else {
                  bendingPoints.push({ x: previousPoint.x, y: currentPoint.y });
                }
              }
            }
            break;
          case Position.Top:
          case Position.Bottom:
            bendingPoints.push({ x: sourceX, y: firstPoint.y });
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
                if (isMultipleOfTwo(i)) {
                  bendingPoints.push({ x: previousPoint.x, y: currentPoint.y });
                } else {
                  bendingPoints.push({ x: currentPoint.x, y: previousPoint.y });
                }
              }
            }
            break;
        }
      }
    }
  }

  return {
    edgeId: edge.id,
    source: { x: sourceX, y: sourceY },
    target: { x: targetX, y: targetY },
    sourcePosition,
    targetPosition,
    sourceNode,
    targetNode,
    bendingPoints,
    customEdge,
    sourceAlignmentOffset: { x: sourceX - rawSource.x, y: sourceY - rawSource.y },
    targetAlignmentOffset: { x: targetX - rawTarget.x, y: targetY - rawTarget.y },
  };
};
