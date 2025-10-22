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
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

// Constants below are tuned around the diagram auto-layout heuristics: nodes keep a 50px gap,
// so we stay within that corridor to avoid hitting obstacles while still fanning edges apart.
const AUTO_LAYOUT_NODE_GAP = 50;
const MIN_TARGET_OFFSET = 12; // keeps a visible elbow without touching the node border
const MAX_TARGET_OFFSET = AUTO_LAYOUT_NODE_GAP - 10;
const MAX_PERPENDICULAR_OFFSET = AUTO_LAYOUT_NODE_GAP - MIN_TARGET_OFFSET;
const PERPENDICULAR_STEP = 12; // spacing between parallel edges on the same side
const STRAIGHT_AXIS_TOLERANCE = 14; // treat paths within 14px as visually straight

type AutoBendAnchor = 'source' | 'target' | 'midpoint';
const ANCHOR_PREFERENCE_ORDER: AutoBendAnchor[] = ['target', 'midpoint', 'source'];

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

const getAnchorPreferenceOrder = (preferredAnchor: AutoBendAnchor): AutoBendAnchor[] => [
  preferredAnchor,
  ...ANCHOR_PREFERENCE_ORDER.filter((anchor) => anchor !== preferredAnchor),
];

type Rectangle = { left: number; right: number; top: number; bottom: number };

const getNodeAbsolutePosition = (
  node: Node<NodeData>,
  nodeMap: Map<string, Node<NodeData>>,
  cache: Map<string, XYPosition>
): XYPosition => {
  const cachedPosition = cache.get(node.id);
  if (cachedPosition) {
    return cachedPosition;
  }

  const positionAbsolute = (node as Partial<{ positionAbsolute?: XYPosition }>).positionAbsolute;
  if (positionAbsolute) {
    cache.set(node.id, positionAbsolute);
    return positionAbsolute;
  }

  const parentId = getParentId(node);
  if (!parentId) {
    const absolutePosition = { x: node.position.x, y: node.position.y };
    cache.set(node.id, absolutePosition);
    return absolutePosition;
  }

  const parentNode = nodeMap.get(parentId);
  if (!parentNode) {
    const absolutePosition = { x: node.position.x, y: node.position.y };
    cache.set(node.id, absolutePosition);
    return absolutePosition;
  }

  const parentAbsolute = getNodeAbsolutePosition(parentNode, nodeMap, cache);
  const absolutePosition = {
    x: parentAbsolute.x + node.position.x,
    y: parentAbsolute.y + node.position.y,
  };
  cache.set(node.id, absolutePosition);
  return absolutePosition;
};

const getNodeRectangle = (
  node: Node<NodeData>,
  nodeMap: Map<string, Node<NodeData>>,
  cache: Map<string, XYPosition>
): Rectangle | null => {
  const width = node.width ?? 0;
  const height = node.height ?? 0;

  if (width <= 0 || height <= 0) {
    return null;
  }

  const absolutePosition = getNodeAbsolutePosition(node, nodeMap, cache);

  return {
    left: absolutePosition.x,
    right: absolutePosition.x + width,
    top: absolutePosition.y,
    bottom: absolutePosition.y + height,
  };
};

const isPointInsideRect = (point: XYPosition, rect: Rectangle): boolean =>
  point.x > rect.left && point.x < rect.right && point.y > rect.top && point.y < rect.bottom;

const doesSegmentOverlapRect = (start: XYPosition, end: XYPosition, rect: Rectangle): boolean => {
  if (start.x === end.x && start.y === end.y) {
    return isPointInsideRect(start, rect);
  }

  if (start.y === end.y) {
    const segmentY = start.y;
    if (segmentY <= rect.top || segmentY >= rect.bottom) {
      return false;
    }
    const segMinX = Math.min(start.x, end.x);
    const segMaxX = Math.max(start.x, end.x);
    return segMaxX > rect.left && segMinX < rect.right;
  }

  if (start.x === end.x) {
    const segmentX = start.x;
    if (segmentX <= rect.left || segmentX >= rect.right) {
      return false;
    }
    const segMinY = Math.min(start.y, end.y);
    const segMaxY = Math.max(start.y, end.y);
    return segMaxY > rect.top && segMinY < rect.bottom;
  }

  // Diagonal segments should not occur for rectilinear edges; fall back to a conservative overlap check.
  const segLeft = Math.min(start.x, end.x);
  const segRight = Math.max(start.x, end.x);
  const segTop = Math.min(start.y, end.y);
  const segBottom = Math.max(start.y, end.y);
  return segRight > rect.left && segLeft < rect.right && segBottom > rect.top && segTop < rect.bottom;
};

const doesPathOverlapNodes = (
  pathPoints: XYPosition[],
  nodes: Node<NodeData>[],
  nodeMap: Map<string, Node<NodeData>>,
  ignoredNodeIds: Set<string>,
  anchor: AutoBendAnchor,
  edgeId: string
): boolean => {
  const absolutePositionCache = new Map<string, XYPosition>();
  const collidableNodes = nodes
    .filter((node) => !ignoredNodeIds.has(node.id))
    .map((node) => {
      const rect = getNodeRectangle(node, nodeMap, absolutePositionCache);
      return rect ? { node, rect } : null;
    })
    .filter((entry): entry is { node: Node<NodeData>; rect: Rectangle } => entry !== null);

  for (let index = 0; index < pathPoints.length - 1; index++) {
    const segmentStart = pathPoints[index];
    const segmentEnd = pathPoints[index + 1];

    if (!segmentStart || !segmentEnd) {
      continue;
    }

    if (segmentStart.x === segmentEnd.x && segmentStart.y === segmentEnd.y) {
      continue;
    }

    for (const { node, rect } of collidableNodes) {
      if (doesSegmentOverlapRect(segmentStart, segmentEnd, rect)) {
        console.debug('auto-route overlap', { edgeId, anchor, segmentStart, segmentEnd, nodeId: node.id, rect });
        return true;
      }
    }
  }
  return false;
};

const getParentId = (node: Node<NodeData> | undefined): string | undefined => {
  if (!node) {
    return undefined;
  }
  const parentNode = (node as Partial<Node<NodeData>> & { parentNode?: string }).parentNode;
  if (typeof parentNode === 'string') {
    return parentNode;
  }
  const parentId = (node as Partial<{ parentId?: string }>).parentId;
  if (typeof parentId === 'string') {
    return parentId;
  }
  return undefined;
};

const collectAncestorIds = (nodeId: string, nodeMap: Map<string, Node<NodeData>>): Set<string> => {
  const ancestors = new Set<string>();
  let currentId: string | undefined = nodeId;
  while (currentId) {
    const node = nodeMap.get(currentId);
    const parentId = getParentId(node);
    if (!parentId || ancestors.has(parentId)) {
      break;
    }
    ancestors.add(parentId);
    currentId = parentId;
  }
  return ancestors;
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

  // Keep only edges that enter through the same side; the other sides can overlap safely.
  const sameSideEdges = edgesWithTarget.filter(
    (edge) => edge.target === targetId && (edge.targetPosition ?? targetPosition) === targetPosition
  );

  const targetEdgesFallback = edgesWithTarget.filter((edge) => edge.target === targetId);
  const edgesForTarget = sameSideEdges.length > 0 ? sameSideEdges : targetEdgesFallback;

  if (edgesForTarget.length === 0) {
    return { offset: MIN_TARGET_OFFSET, index: 0, count: 1 };
  }

  // Prefer grouping by exact handle when several edges share it, otherwise fan out all edges on the side.
  const handleEdges = edgesForTarget.filter((edge) => (edge.targetHandle ?? '') === normalizedHandleId);
  const candidateEdges = handleEdges.length > 1 ? handleEdges : edgesForTarget;

  const targetAxis: 'x' | 'y' = isHorizontalPosition(targetPosition) ? 'y' : 'x';

  const sortedEdges = candidateEdges.slice().sort((edgeA, edgeB) => {
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

  const currentEdge =
    sortedEdges.find((edge) => edge.id === edgeId) ?? candidateEdges.find((edge) => edge.id === edgeId);
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
  // For edges on the “positive” side (right or bottom) flip the index so
  // the outermost edge stays closest to the node, reducing crossings.
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
  anchor: 'source' | 'target' | 'midpoint';
};

const resolveAutoBendAnchor = (): 'source' | 'target' | 'midpoint' => {
  // Default behaviour keeps the bendpoints near the target as it produces fewer crossings for most diagrams.
  // This helper centralises the decision so future heuristics can route specific edges from the source side
  // or from the middle without refactoring the routing again.
  return 'target';
};

const buildAutoBendingPoints = (rawPoints: XYPosition[], context: AutoBendPointContext): XYPosition[] => {
  const { sourceX, sourceY, targetX, targetY, targetPosition, offset, index, count, anchor } = context;
  const approachOffset = clamp(offset, MIN_TARGET_OFFSET, MAX_TARGET_OFFSET);
  const approachPoint = getApproachPoint(targetX, targetY, targetPosition, approachOffset);
  const deltaFromTarget = isHorizontalPosition(targetPosition) ? sourceY - targetY : sourceX - targetX;
  const directionSign = deltaFromTarget === 0 ? 1 : Math.sign(deltaFromTarget);
  const maxSpread = MAX_PERPENDICULAR_OFFSET;
  const perEdgeStep = count > 1 ? Math.min(PERPENDICULAR_STEP, maxSpread / (count - 1)) : 0;
  const perpendicularShift = clamp(directionSign * perEdgeStep * index, -maxSpread, maxSpread);

  if (anchor === 'midpoint') {
    const midpointPoints: XYPosition[] = [];
    if (isHorizontalPosition(targetPosition)) {
      const middleX = Math.round((sourceX + targetX) / 2);
      midpointPoints.push({ x: middleX, y: sourceY });
      if (perpendicularShift !== 0) {
        midpointPoints.push({ x: middleX, y: sourceY + perpendicularShift });
        midpointPoints.push({ x: middleX, y: targetY + perpendicularShift });
      }
      midpointPoints.push({ x: middleX, y: targetY });
    } else {
      const middleY = Math.round((sourceY + targetY) / 2);
      midpointPoints.push({ x: sourceX, y: middleY });
      if (perpendicularShift !== 0) {
        midpointPoints.push({ x: sourceX + perpendicularShift, y: middleY });
        midpointPoints.push({ x: targetX + perpendicularShift, y: middleY });
      }
      midpointPoints.push({ x: targetX, y: middleY });
    }
    return dedupeConsecutivePoints(midpointPoints);
  }

  // Tail points define the last two turns right before the node. We shift them per edge so parallel edges
  // don’t overlap, but we always finish exactly on the handle to stay rectilinear.
  const tailPoints: XYPosition[] =
    anchor === 'target'
      ? isHorizontalPosition(targetPosition)
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
        : [{ x: targetX, y: approachPoint.y }]
      : isHorizontalPosition(targetPosition)
      ? perpendicularShift !== 0
        ? [
            { x: sourceX, y: sourceY + perpendicularShift },
            { x: sourceX, y: sourceY },
          ]
        : [{ x: sourceX, y: sourceY }]
      : perpendicularShift !== 0
      ? [
          { x: sourceX + perpendicularShift, y: sourceY },
          { x: sourceX, y: sourceY },
        ]
      : [{ x: sourceX, y: sourceY }];

  if (rawPoints.length <= 2) {
    const basePoints: XYPosition[] = [];
    if (anchor === 'target') {
      if (isHorizontalPosition(targetPosition)) {
        basePoints.push({ x: approachPoint.x, y: sourceY });
      } else {
        basePoints.push({ x: sourceX, y: approachPoint.y });
      }
      return dedupeConsecutivePoints([...basePoints, ...tailPoints]);
    } else {
      if (isHorizontalPosition(targetPosition)) {
        basePoints.push({ x: sourceX, y: targetY });
      } else {
        basePoints.push({ x: targetX, y: sourceY });
      }
      return dedupeConsecutivePoints([...tailPoints, ...basePoints]);
    }
  }

  const adjustedPoints = rawPoints.map((point) => ({ ...point }));
  const stablePoints =
    anchor === 'target' ? adjustedPoints.slice(0, Math.max(0, adjustedPoints.length - 2)) : adjustedPoints.slice(2);
  const resultPoints: XYPosition[] =
    anchor === 'target' ? [...stablePoints, ...tailPoints] : [...tailPoints, ...stablePoints];

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
  const { getEdges, getNode, getNodes } = useStore();
  const hasCustomBendPoints = !!(data && data.bendingPoints && data.bendingPoints.length > 0);

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
  if (hasCustomBendPoints && data?.bendingPoints) {
    bendingPoints = data.bendingPoints;
  } else {
    const edges = getEdges();
    const {
      offset: computedOffset,
      index: targetEdgeIndex,
      count: targetEdgeCount,
    } = computeTargetOffset(edges, id, target, targetHandleId, targetPosition, getNode);

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

    const autoRawPoints: XYPosition[] = [];
    if (quadraticCurvePoints.length > 0) {
      const firstPoint = quadraticCurvePoints[0];
      if (firstPoint) {
        switch (sourcePosition) {
          case Position.Right:
          case Position.Left:
            autoRawPoints.push({ x: firstPoint.x, y: sourceY });
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
                if (isMultipleOfTwo(i)) {
                  autoRawPoints.push({ x: currentPoint.x, y: previousPoint.y });
                } else {
                  autoRawPoints.push({ x: previousPoint.x, y: currentPoint.y });
                }
              }
            }
            break;
          case Position.Top:
          case Position.Bottom:
            autoRawPoints.push({ x: sourceX, y: firstPoint.y });
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (currentPoint && previousPoint) {
                if (isMultipleOfTwo(i)) {
                  autoRawPoints.push({ x: previousPoint.x, y: currentPoint.y });
                } else {
                  autoRawPoints.push({ x: currentPoint.x, y: previousPoint.y });
                }
              }
            }
            break;
        }
      }
    }

    const nodes: Node<NodeData>[] = getNodes() ?? [];
    const nodeEntries: Array<[string, Node<NodeData>]> = nodes.map((node) => [node.id, node]);
    const nodeMap = new Map<string, Node<NodeData>>(nodeEntries);
    const ignoredNodeIds = new Set<string>([source, target]);
    collectAncestorIds(source, nodeMap).forEach((ancestorId) => ignoredNodeIds.add(ancestorId));
    collectAncestorIds(target, nodeMap).forEach((ancestorId) => ignoredNodeIds.add(ancestorId));
    const anchorPreference = getAnchorPreferenceOrder(resolveAutoBendAnchor());
    let fallbackBendingPoints: XYPosition[] = [];
    let selectedBendingPoints: XYPosition[] | undefined;

    for (const anchor of anchorPreference) {
      const candidatePoints = buildAutoBendingPoints(autoRawPoints, {
        sourceX,
        sourceY,
        targetX,
        targetY,
        targetPosition,
        offset: computedOffset,
        index: targetEdgeIndex,
        count: targetEdgeCount,
        anchor,
      });
      const pathPoints = dedupeConsecutivePoints([
        { x: sourceX, y: sourceY },
        ...candidatePoints,
        { x: targetX, y: targetY },
      ]);
      if (!doesPathOverlapNodes(pathPoints, nodes, nodeMap, ignoredNodeIds, anchor, id)) {
        selectedBendingPoints = candidatePoints;
        break;
      }
      fallbackBendingPoints = candidatePoints;
    }

    bendingPoints = selectedBendingPoints ?? fallbackBendingPoints;
  }

  if (!hasCustomBendPoints) {
    const deltaX = Math.abs(sourceX - targetX);
    const deltaY = Math.abs(sourceY - targetY);

    const alignVertical = deltaX <= STRAIGHT_AXIS_TOLERANCE && deltaY > STRAIGHT_AXIS_TOLERANCE;
    const alignHorizontal = deltaY <= STRAIGHT_AXIS_TOLERANCE && deltaX > STRAIGHT_AXIS_TOLERANCE;
    const alignPoint = deltaX <= STRAIGHT_AXIS_TOLERANCE && deltaY <= STRAIGHT_AXIS_TOLERANCE;

    if (alignVertical || alignHorizontal || alignPoint) {
      if (alignVertical || alignPoint) {
        const alignX = Math.round((sourceX + targetX) / 2);
        sourceX = alignX;
        targetX = alignX;
      }
      if (alignHorizontal || alignPoint) {
        const alignY = Math.round((sourceY + targetY) / 2);
        sourceY = alignY;
        targetY = alignY;
      }
      bendingPoints = [];
    } else {
      const pathPoints: XYPosition[] = [{ x: sourceX, y: sourceY }, ...bendingPoints, { x: targetX, y: targetY }];
      if (pathPoints.length >= 2) {
        const xValues = pathPoints.map((point) => point.x);
        const yValues = pathPoints.map((point) => point.y);

        const xSpan = Math.max(...xValues) - Math.min(...xValues);
        const ySpan = Math.max(...yValues) - Math.min(...yValues);

        // SmoothStep occasionally adds tiny detours: if the resulting points almost form a straight line,
        // collapse them to a true straight segment instead of displaying a micro zig-zag.
        if (xSpan <= STRAIGHT_AXIS_TOLERANCE && ySpan > STRAIGHT_AXIS_TOLERANCE) {
          const alignX = Math.round((sourceX + targetX) / 2);
          sourceX = alignX;
          targetX = alignX;
          bendingPoints = [];
        } else if (ySpan <= STRAIGHT_AXIS_TOLERANCE && xSpan > STRAIGHT_AXIS_TOLERANCE) {
          const alignY = Math.round((sourceY + targetY) / 2);
          sourceY = alignY;
          targetY = alignY;
          bendingPoints = [];
        } else if (xSpan <= STRAIGHT_AXIS_TOLERANCE && ySpan <= STRAIGHT_AXIS_TOLERANCE) {
          const alignX = Math.round((sourceX + targetX) / 2);
          const alignY = Math.round((sourceY + targetY) / 2);
          sourceX = alignX;
          targetX = alignX;
          sourceY = alignY;
          targetY = alignY;
          bendingPoints = [];
        }
      }
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
      customEdge={hasCustomBendPoints}
      sourceNode={sourceNode}
      targetNode={targetNode}
    />
  );
});
