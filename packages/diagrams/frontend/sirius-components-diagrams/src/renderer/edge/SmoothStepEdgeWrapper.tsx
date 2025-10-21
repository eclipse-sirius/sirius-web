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
import {
  Edge,
  EdgeProps,
  getSmoothStepPath,
  InternalNode,
  Node,
  Position,
  useInternalNode,
  XYPosition,
} from '@xyflow/react';
import { memo, useContext } from 'react';
import parse from 'svg-path-parser';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useStore } from '../../representation/useStore';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

const AUTO_LAYOUT_NODE_GAP = 50;
const MIN_TARGET_OFFSET = 12;
const MAX_TARGET_OFFSET = AUTO_LAYOUT_NODE_GAP - 10;
const MAX_PERPENDICULAR_OFFSET = AUTO_LAYOUT_NODE_GAP - MIN_TARGET_OFFSET;
const PERPENDICULAR_STEP = 12;

const clamp = (value: number, lower: number, upper: number): number => Math.max(lower, Math.min(upper, value));

const isHorizontalPosition = (position: Position): boolean => position === Position.Left || position === Position.Right;

const getApproachPoint = (targetX: number, targetY: number, targetPosition: Position, offset: number): XYPosition => {
  if (targetPosition === Position.Left) {
    return { x: targetX - offset, y: targetY };
  }

  if (targetPosition === Position.Right) {
    return { x: targetX + offset, y: targetY };
  }

  if (targetPosition === Position.Top) {
    return { x: targetX, y: targetY - offset };
  }

  return { x: targetX, y: targetY + offset };
};

const dedupeConsecutivePoints = (points: XYPosition[]): XYPosition[] => {
  if (points.length === 0) {
    return points;
  }
  const deduped: XYPosition[] = [];
  for (const point of points) {
    const previous = deduped[deduped.length - 1];
    if (!previous || previous.x !== point.x || previous.y !== point.y) {
      deduped.push(point);
    }
  }
  return deduped;
};

const getNodeCenterCoordinate = (node: Node<NodeData> | undefined, axis: 'x' | 'y'): number => {
  if (!node) {
    return 0;
  }
  if (axis === 'x') {
    return node.position.x + (node.width ?? 0) / 2;
  }
  return node.position.y + (node.height ?? 0) / 2;
};

type TargetOffsetResult = {
  offset: number;
  index: number;
  count: number;
};

type PositionAwareEdge = Edge<EdgeData> & { targetPosition?: Position };

const computeTargetOffset = (
  edges: Edge<EdgeData>[],
  edgeId: string,
  targetId: string,
  targetHandleId: string | null | undefined,
  targetPosition: Position,
  getNode: (id: string) => Node<NodeData> | undefined
): TargetOffsetResult => {
  const normalizedHandleId = targetHandleId ?? '';
  const edgesWithTarget = edges as PositionAwareEdge[];

  const sameSideEdges = edgesWithTarget.filter(
    (edge) => edge.target === targetId && ((edge.targetPosition ?? targetPosition) === targetPosition)
  );

  const targetEdgesFallback = edgesWithTarget.filter((edge) => edge.target === targetId);
  const edgesForTarget = sameSideEdges.length > 0 ? sameSideEdges : targetEdgesFallback;

  if (edgesForTarget.length === 0) {
    return { offset: MIN_TARGET_OFFSET, index: 0, count: 1 };
  }

  const handleEdges = edgesForTarget.filter((edge) => (edge.targetHandle ?? '') === normalizedHandleId);
  const candidateEdges = handleEdges.length > 1 ? handleEdges : edgesForTarget;

  const targetAxis: 'x' | 'y' = isHorizontalPosition(targetPosition) ? 'y' : 'x';

  const sortedEdges = candidateEdges
    .slice()
    .sort((edgeA, edgeB) => {
      const coordA = getNodeCenterCoordinate(getNode(edgeA.source), targetAxis);
      const coordB = getNodeCenterCoordinate(getNode(edgeB.source), targetAxis);
      if (coordA !== coordB) {
        return coordA - coordB;
      }
      return edgeA.id.localeCompare(edgeB.id);
    });

  const targetNode = getNode(targetId);
  const targetAxisCoordinate = getNodeCenterCoordinate(targetNode, targetAxis);

  const categorizeEdgeSide = (edge: PositionAwareEdge): number => {
    const sourceNode = getNode(edge.source);
    const sourceCoord = getNodeCenterCoordinate(sourceNode, targetAxis);
    const delta = sourceCoord - targetAxisCoordinate;
    if (delta > 0) {
      return 1;
    }
    if (delta < 0) {
      return -1;
    }
    return 0;
  };

  const currentEdge = sortedEdges.find((edge) => edge.id === edgeId) ?? candidateEdges.find((edge) => edge.id === edgeId);
  const currentSide = currentEdge ? categorizeEdgeSide(currentEdge) : 0;

  const edgesSameSide = sortedEdges.filter((edge) => {
    const side = categorizeEdgeSide(edge);
    if (currentSide === 0) {
      return side === 0;
    }
    if (side === 0) {
      return true;
    }
    return side === currentSide;
  });

  const sideEdges = edgesSameSide.length > 0 ? edgesSameSide : sortedEdges;

  const sideIndex = sideEdges.findIndex((edge) => edge.id === edgeId);
  const effectiveIndex = sideIndex === -1 ? 0 : sideIndex;
  const sideCount = sideEdges.length;

  if (sideCount <= 1) {
    return { offset: MIN_TARGET_OFFSET, index: effectiveIndex, count: sideCount };
  }

  const step = (MAX_TARGET_OFFSET - MIN_TARGET_OFFSET) / (sideCount - 1);
  const orientedIndex = currentSide > 0 && sideCount > 1 ? sideCount - 1 - effectiveIndex : effectiveIndex;
  const offset = MIN_TARGET_OFFSET + orientedIndex * step;

  return {
    offset: clamp(offset, MIN_TARGET_OFFSET, MAX_TARGET_OFFSET),
    index: effectiveIndex,
    count: sideCount,
  };
};

type AutoBendPointContext = {
  sourceX: number;
  sourceY: number;
  targetX: number;
  targetY: number;
  targetPosition: Position;
  offset: number;
  index: number;
  count: number;
};

const buildAutoBendingPoints = (rawPoints: XYPosition[], context: AutoBendPointContext): XYPosition[] => {
  const { sourceX, sourceY, targetX, targetY, targetPosition, offset, index, count } = context;
  const approachOffset = clamp(offset, MIN_TARGET_OFFSET, MAX_TARGET_OFFSET);
  const approachPoint = getApproachPoint(targetX, targetY, targetPosition, approachOffset);
  const deltaFromTarget = isHorizontalPosition(targetPosition) ? sourceY - targetY : sourceX - targetX;
  const directionSign = deltaFromTarget === 0 ? 1 : Math.sign(deltaFromTarget);
  const maxSpread = MAX_PERPENDICULAR_OFFSET;
  const perEdgeStep =
    count > 1 ? Math.min(PERPENDICULAR_STEP, maxSpread / (count - 1)) : 0;
  const perpendicularShift = clamp(directionSign * perEdgeStep * index, -maxSpread, maxSpread);

  const tailPoints: XYPosition[] = isHorizontalPosition(targetPosition)
    ? perpendicularShift !== 0
      ? [
          { x: approachPoint.x, y: targetY + perpendicularShift },
          { x: approachPoint.x, y: targetY },
        ]
      : [{ x: approachPoint.x, y: targetY }]
    : perpendicularShift !== 0
      ? [
          { x: targetX + perpendicularShift, y: approachPoint.y },
          { x: targetX, y: approachPoint.y },
        ]
      : [{ x: targetX, y: approachPoint.y }];

  if (rawPoints.length <= 2) {
    const basePoints: XYPosition[] = [];
    if (isHorizontalPosition(targetPosition)) {
      basePoints.push({ x: approachPoint.x, y: sourceY });
    } else {
      basePoints.push({ x: sourceX, y: approachPoint.y });
    }
    return dedupeConsecutivePoints([...basePoints, ...tailPoints]);
  }

  const adjustedPoints = rawPoints.map((point) => ({ ...point }));
  const stablePoints = adjustedPoints.slice(0, Math.max(0, adjustedPoints.length - 2));
  const resultPoints: XYPosition[] = [...stablePoints, ...tailPoints];

  return dedupeConsecutivePoints(resultPoints);
};

export const SmoothStepEdgeWrapper = memo((props: EdgeProps<Edge<MultiLabelEdgeData>>) => {
  const {
    id,
    source,
    target,
    markerEnd,
    markerStart,
    sourcePosition,
    targetPosition,
    sourceHandleId,
    targetHandleId,
    data,
  } = props;
  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);
  const { getEdges, getNode } = useStore();

  const sourceNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(source);
  const targetNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(target);

  if (!sourceNode || !targetNode) {
    return null;
  }

  const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
  );

  let { x: sourceX, y: sourceY } = getHandleCoordinatesByPosition(
    sourceNode,
    sourcePosition,
    sourceHandleId ?? '',
    sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );
  let { x: targetX, y: targetY } = getHandleCoordinatesByPosition(
    targetNode,
    targetPosition,
    targetHandleId ?? '',
    targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );

  // trick to have the source of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleSourceRadius = markerStart == undefined || markerStart.includes('None') ? 2 : 3;
  switch (sourcePosition) {
    case Position.Right:
      sourceX = sourceX + handleSourceRadius;
      break;
    case Position.Left:
      sourceX = sourceX - handleSourceRadius;
      break;
    case Position.Top:
      sourceY = sourceY - handleSourceRadius;
      break;
    case Position.Bottom:
      sourceY = sourceY + handleSourceRadius;
      break;
  }
  // trick to have the target of the edge positioned at the very border of a node
  // if the edge has a marker, then only the marker need to touch the node
  const handleTargetRadius = markerEnd == undefined || markerEnd.includes('None') ? 2 : 3;
  switch (targetPosition) {
    case Position.Right:
      targetX = targetX + handleTargetRadius;
      break;
    case Position.Left:
      targetX = targetX - handleTargetRadius;
      break;
    case Position.Top:
      targetY = targetY - handleTargetRadius;
      break;
    case Position.Bottom:
      targetY = targetY + handleTargetRadius;
      break;
  }

  let bendingPoints: XYPosition[] = [];
  if (data && data.bendingPoints && data.bendingPoints.length > 0) {
    bendingPoints = data.bendingPoints;
  } else {
    const edges = getEdges();
    const { offset: computedOffset, index: targetEdgeIndex, count: targetEdgeCount } = computeTargetOffset(
      edges,
      id,
      target,
      targetHandleId,
      targetPosition,
      getNode
    );

    const [smoothEdgePath] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
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

        bendingPoints = buildAutoBendingPoints(bendingPoints, {
          sourceX,
          sourceY,
          targetX,
          targetY,
          targetPosition,
          offset: computedOffset,
          index: targetEdgeIndex,
          count: targetEdgeCount,
        });
      } else {
        bendingPoints = buildAutoBendingPoints([], {
          sourceX,
          sourceY,
          targetX,
          targetY,
          targetPosition,
          offset: computedOffset,
          index: targetEdgeIndex,
          count: targetEdgeCount,
        });
      }
    } else {
      bendingPoints = buildAutoBendingPoints([], {
        sourceX,
        sourceY,
        targetX,
        targetY,
        targetPosition,
        offset: computedOffset,
        index: targetEdgeIndex,
        count: targetEdgeCount,
      });
    }
  }

  return (
    <MultiLabelRectilinearEditableEdge
      {...props}
      sourceX={sourceX}
      sourceY={sourceY}
      targetX={targetX}
      targetY={targetY}
      bendingPoints={bendingPoints}
      customEdge={!!(data && data.bendingPoints && data.bendingPoints.length > 0)}
      sourceNode={sourceNode}
      targetNode={targetNode}
    />
  );
});
