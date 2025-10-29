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
import { buildDetouredPolyline } from '../layout/postProcessEdgeDetours';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData, RectilinearTurnPreference } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

const DEFAULT_TURN_PREFERENCE: RectilinearTurnPreference = 'target';
const DEFAULT_MIN_OUTWARD_LENGTH = 10;
const DEFAULT_FAN_IN_ENABLED = true;
const DEFAULT_FAN_OUT_ENABLED = true;
const FAN_SPACING = 12;
const FAN_MAX_SPREAD = 50;
const MIN_DETOUR_SPAN = 6;

type PositionAwareEdge = Edge<MultiLabelEdgeData> & {
  targetPosition?: Position;
  sourcePosition?: Position;
};
type Axis = 'horizontal' | 'vertical';

function interpolateHalfway(from: number, to: number): number {
  return from + (to - from) * 0.5;
}

function computePreferredTurnCoordinate(
  defaultCoordinate: number,
  sourceCoordinate: number,
  targetCoordinate: number,
  preference: RectilinearTurnPreference
): number {
  const safeDefault = Number.isFinite(defaultCoordinate) ? defaultCoordinate : sourceCoordinate;
  switch (preference) {
    case 'source':
      return interpolateHalfway(safeDefault, sourceCoordinate);
    case 'target':
      return interpolateHalfway(safeDefault, targetCoordinate);
    case 'middle': {
      const midpoint = (sourceCoordinate + targetCoordinate) / 2;
      return Number.isFinite(midpoint) ? midpoint : safeDefault;
    }
    default:
      return safeDefault;
  }
}

function enforceMinimumClearance(
  coordinate: number,
  origin: number,
  fallback: number,
  directionHint: number,
  minOutwardLength: number
): number {
  // Always exit the source node in the expected outward direction while keeping a minimum offset.
  const fallbackOffset = fallback - origin;
  const direction =
    directionHint !== 0
      ? directionHint
      : fallbackOffset !== 0
      ? Math.sign(fallbackOffset)
      : coordinate >= origin
      ? 1
      : -1;

  if (direction === 0) {
    return coordinate;
  }

  const offset = coordinate - origin;
  const hasCorrectDirection = offset * direction >= 0;

  if (!hasCorrectDirection) {
    const fallbackHasCorrectDirection = fallbackOffset * direction >= minOutwardLength;
    if (fallbackHasCorrectDirection) {
      return fallback;
    }
    return origin + minOutwardLength * direction;
  }

  const clearance = Math.abs(offset);
  if (clearance >= minOutwardLength) {
    return coordinate;
  }

  const fallbackClearance = Math.abs(fallbackOffset);
  if (fallbackClearance >= minOutwardLength && fallbackOffset * direction >= 0) {
    return fallback;
  }

  return origin + minOutwardLength * direction;
}

function clamp(value: number, min: number, max: number): number {
  return Math.max(min, Math.min(max, value));
}

function isHorizontalPosition(position: Position): boolean {
  return position === Position.Left || position === Position.Right;
}

function getNodeCenterCoordinate(node: Node<NodeData> | undefined, axis: 'x' | 'y'): number {
  if (!node) {
    return 0;
  }
  if (axis === 'x') {
    return node.position.x + (node.width ?? 0) / 2;
  }
  return node.position.y + (node.height ?? 0) / 2;
}

function symmetricOffset(index: number, count: number): number {
  if (count <= 1) {
    return 0;
  }
  const step = Math.min(FAN_SPACING, FAN_MAX_SPREAD / (count - 1));
  const half = (count - 1) / 2;
  return clamp((index - half) * step, -FAN_MAX_SPREAD, FAN_MAX_SPREAD);
}

function polylinesEqual(first: XYPosition[], second: XYPosition[]): boolean {
  if (first.length !== second.length) {
    return false;
  }
  for (let i = 0; i < first.length; i++) {
    const a = first[i];
    const b = second[i];
    if (!a || !b || a.x !== b.x || a.y !== b.y) {
      return false;
    }
  }
  return true;
}

function determineInitialAxis(position: Position): Axis {
  return isHorizontalPosition(position) ? 'horizontal' : 'vertical';
}

type FanArrangement = {
  offset: number;
  count: number;
  index: number;
};

function determineFanArrangement(
  edges: Edge<MultiLabelEdgeData>[],
  currentEdgeId: string,
  targetId: string,
  targetHandleId: string | null | undefined,
  targetPosition: Position,
  getNode: (id: string) => Node<NodeData> | undefined
): FanArrangement {
  const normalizedHandleId = targetHandleId ?? '';
  const positionedEdges = edges as PositionAwareEdge[];

  const sameTargetEdges = positionedEdges.filter((edge) => edge.target === targetId);
  if (sameTargetEdges.length <= 1) {
    return { offset: 0, index: 0, count: sameTargetEdges.length };
  }

  const sameSideEdges = sameTargetEdges.filter(
    (edge) => (edge.targetPosition ?? targetPosition) === targetPosition
  );
  const candidateEdges = (() => {
    if (sameSideEdges.length === 0) {
      return sameTargetEdges;
    }
    const sameHandleEdges = sameSideEdges.filter((edge) => (edge.targetHandle ?? '') === normalizedHandleId);
    if (sameHandleEdges.length > 1) {
      return sameHandleEdges;
    }
    return sameSideEdges;
  })();

  if (candidateEdges.length <= 1) {
    return { offset: 0, index: 0, count: candidateEdges.length };
  }

  const axis: 'x' | 'y' = isHorizontalPosition(targetPosition) ? 'y' : 'x';
  const sortedEdges = candidateEdges
    .slice()
    .sort((edgeA, edgeB) => {
      const coordA = getNodeCenterCoordinate(getNode(edgeA.source), axis);
      const coordB = getNodeCenterCoordinate(getNode(edgeB.source), axis);
      if (coordA !== coordB) {
        return coordA - coordB;
      }
      return edgeA.id.localeCompare(edgeB.id);
    });

  let edgeIndex = sortedEdges.findIndex((edge) => edge.id === currentEdgeId);
  if (edgeIndex === -1) {
    edgeIndex = 0;
  }

  return {
    offset: symmetricOffset(edgeIndex, sortedEdges.length),
    count: sortedEdges.length,
    index: edgeIndex,
  };
}

function determineSourceFanArrangement(
  edges: Edge<MultiLabelEdgeData>[],
  currentEdgeId: string,
  sourceId: string,
  sourceHandleId: string | null | undefined,
  sourcePosition: Position,
  getNode: (id: string) => Node<NodeData> | undefined
): FanArrangement {
  const normalizedHandleId = sourceHandleId ?? '';
  const positionedEdges = edges as PositionAwareEdge[];

  const sameSourceEdges = positionedEdges.filter((edge) => edge.source === sourceId);
  if (sameSourceEdges.length <= 1) {
    return { offset: 0, index: 0, count: sameSourceEdges.length };
  }

  const sameSideEdges = sameSourceEdges.filter(
    (edge) => (edge.sourcePosition ?? sourcePosition) === sourcePosition
  );
  const candidateEdges = (() => {
    if (sameSideEdges.length === 0) {
      return sameSourceEdges;
    }
    const sameHandleEdges = sameSideEdges.filter((edge) => (edge.sourceHandle ?? '') === normalizedHandleId);
    if (sameHandleEdges.length > 1) {
      return sameHandleEdges;
    }
    return sameSideEdges;
  })();

  if (candidateEdges.length <= 1) {
    return { offset: 0, index: 0, count: candidateEdges.length };
  }

  const axis: 'x' | 'y' = isHorizontalPosition(sourcePosition) ? 'y' : 'x';
  const sortedEdges = candidateEdges
    .slice()
    .sort((edgeA, edgeB) => {
      const coordA = getNodeCenterCoordinate(getNode(edgeA.target), axis);
      const coordB = getNodeCenterCoordinate(getNode(edgeB.target), axis);
      if (coordA !== coordB) {
        return coordA - coordB;
      }
      return edgeA.id.localeCompare(edgeB.id);
    });

  let edgeIndex = sortedEdges.findIndex((edge) => edge.id === currentEdgeId);
  if (edgeIndex === -1) {
    edgeIndex = 0;
  }

  return {
    offset: symmetricOffset(edgeIndex, sortedEdges.length),
    count: sortedEdges.length,
    index: edgeIndex,
  };
}

function computeFanOutwardLength(arrangement: FanArrangement, minOutwardLength: number): number {
  const { count, index } = arrangement;
  if (count <= 1) {
    return minOutwardLength;
  }
  const center = (count - 1) / 2;
  if (center === 0) {
    return minOutwardLength;
  }
  const distanceFromCenter = Math.abs(index - center);
  const normalized = clamp(1 - distanceFromCenter / center, 0, 1);
  const spread = Math.min(FAN_SPACING * (count - 1), FAN_MAX_SPREAD);
  const bonus = (spread / 2) * normalized;
  return minOutwardLength + bonus;
}

function alignTailCoordinate(points: XYPosition[], axis: 'x' | 'y', value: number): void {
  let pointer = points.length - 1;
  if (pointer < 0) {
    return;
  }
  const base = points[pointer]?.[axis];
  if (base === undefined) {
    return;
  }
  while (pointer >= 0) {
    const current = points[pointer];
    if (!current) {
      pointer--;
      continue;
    }
    const coordinate = current[axis];
    if (coordinate === undefined || Math.abs(coordinate - base) > 0.01) {
      break;
    }
    points[pointer] =
      axis === 'x' ? { x: value, y: current.y } : { x: current.x, y: value };
    pointer--;
  }
}

function alignHeadCoordinate(points: XYPosition[], axis: 'x' | 'y', value: number): void {
  if (points.length === 0) {
    return;
  }
  const base = points[0]?.[axis];
  if (base === undefined) {
    return;
  }
  for (let i = 0; i < points.length; i++) {
    const current = points[i];
    if (!current) {
      continue;
    }
    const coordinate = current[axis];
    if (coordinate === undefined || Math.abs(coordinate - base) > 0.01) {
      break;
    }
    points[i] = axis === 'x' ? { x: value, y: current.y } : { x: current.x, y: value };
  }
}

type FanSourceApplicationResult = {
  bendingPoints: XYPosition[];
  sourceX: number;
  sourceY: number;
};

function applyFanOffsetAtSource(
  bendingPoints: XYPosition[],
  sourcePosition: Position,
  sourceCoordinates: { x: number; y: number },
  perpendicularOffset: number,
  minOutwardLength: number,
  arrangement?: FanArrangement
): FanSourceApplicationResult {
  const adjustedPoints = bendingPoints.map((point) => ({ ...point }));
  const hasFanArrangement = arrangement ? arrangement.count > 1 : false;
  const outwardLength = hasFanArrangement
    ? computeFanOutwardLength(arrangement as FanArrangement, minOutwardLength)
    : Math.max(minOutwardLength, 1);

  let sourceX = sourceCoordinates.x;
  let sourceY = sourceCoordinates.y;

  if (isHorizontalPosition(sourcePosition)) {
    sourceY = sourceCoordinates.y + perpendicularOffset;
    if (adjustedPoints.length === 0) {
      const direction = sourcePosition === Position.Right ? 1 : -1;
      const columnX = sourceCoordinates.x + direction * outwardLength;
      adjustedPoints.push({ x: columnX, y: sourceY });
    }
    if (perpendicularOffset !== 0) {
      alignHeadCoordinate(adjustedPoints, 'y', sourceY);
    }
    if (hasFanArrangement) {
      const direction = sourcePosition === Position.Right ? 1 : -1;
      const desiredX = sourceCoordinates.x + direction * outwardLength;
      alignHeadCoordinate(adjustedPoints, 'x', desiredX);
    }
    return {
      bendingPoints: adjustedPoints,
      sourceX,
      sourceY,
    };
  }

  sourceX = sourceCoordinates.x + perpendicularOffset;
  if (adjustedPoints.length === 0) {
    const direction = sourcePosition === Position.Bottom ? 1 : -1;
    const columnY = sourceCoordinates.y + direction * outwardLength;
    adjustedPoints.push({ x: sourceX, y: columnY });
  }
  if (perpendicularOffset !== 0) {
    alignHeadCoordinate(adjustedPoints, 'x', sourceX);
  }
  if (hasFanArrangement) {
    const direction = sourcePosition === Position.Bottom ? 1 : -1;
    const desiredY = sourceCoordinates.y + direction * outwardLength;
    alignHeadCoordinate(adjustedPoints, 'y', desiredY);
  }

  return {
    bendingPoints: adjustedPoints,
    sourceX,
    sourceY,
  };
}

type FanApplicationResult = {
  bendingPoints: XYPosition[];
  targetX: number;
  targetY: number;
};

function applyFanOffset(
  bendingPoints: XYPosition[],
  targetPosition: Position,
  sourceCoordinates: { x: number; y: number },
  targetCoordinates: { x: number; y: number },
  perpendicularOffset: number,
  minOutwardLength: number,
  arrangement?: FanArrangement
): FanApplicationResult {
  const adjustedPoints = bendingPoints.map((point) => ({ ...point }));
  const hasFanArrangement = arrangement ? arrangement.count > 1 : false;
  const outwardLength = hasFanArrangement
    ? computeFanOutwardLength(arrangement as FanArrangement, minOutwardLength)
    : Math.max(minOutwardLength, 1);

  let targetX = targetCoordinates.x;
  let targetY = targetCoordinates.y;

  if (isHorizontalPosition(targetPosition)) {
    targetY = targetCoordinates.y + perpendicularOffset;
    if (adjustedPoints.length === 0) {
      const direction = targetPosition === Position.Right ? 1 : -1;
      const columnX = targetCoordinates.x + direction * outwardLength;
      adjustedPoints.push({ x: columnX, y: sourceCoordinates.y });
      adjustedPoints.push({ x: columnX, y: targetY });
    } else if (perpendicularOffset !== 0) {
      alignTailCoordinate(adjustedPoints, 'y', targetY);
    }

    if (hasFanArrangement) {
      const direction = targetPosition === Position.Left ? -1 : 1;
      const desiredX = targetCoordinates.x + direction * outwardLength;
      alignTailCoordinate(adjustedPoints, 'x', desiredX);
    }

    return {
      bendingPoints: adjustedPoints,
      targetX,
      targetY,
    };
  }

  targetX = targetCoordinates.x + perpendicularOffset;
  if (adjustedPoints.length === 0) {
    const direction = targetPosition === Position.Bottom ? 1 : -1;
    const columnY = targetCoordinates.y + direction * outwardLength;
    adjustedPoints.push({ x: sourceCoordinates.x, y: columnY });
    adjustedPoints.push({ x: targetX, y: columnY });
  } else if (perpendicularOffset !== 0) {
    alignTailCoordinate(adjustedPoints, 'x', targetX);
  }

  if (hasFanArrangement) {
    const direction = targetPosition === Position.Top ? -1 : 1;
    const desiredY = targetCoordinates.y + direction * outwardLength;
    alignTailCoordinate(adjustedPoints, 'y', desiredY);
  }

  return {
    bendingPoints: adjustedPoints,
    targetX,
    targetY: targetCoordinates.y,
  };
}

function ensureRectilinearPath(
  bendingPoints: XYPosition[],
  source: XYPosition,
  target: XYPosition,
  initialAxis: Axis
): XYPosition[] {
  const rectified: XYPosition[] = [];
  const checkpoints = [...bendingPoints, target];
  let prev = source;
  let axis = initialAxis;

  for (let i = 0; i < checkpoints.length; i++) {
    const current = checkpoints[i];
    if (!current) {
      continue;
    }
    let dx = current.x - prev.x;
    let dy = current.y - prev.y;
    let guard = 0;

    while (!(axis === 'horizontal' ? dy === 0 : dx === 0) && (dx !== 0 || dy !== 0) && guard < 4) {
      const intermediate =
        axis === 'horizontal'
          ? { x: current.x, y: prev.y }
          : { x: prev.x, y: current.y };
      if (intermediate.x === prev.x && intermediate.y === prev.y) {
        break;
      }
      rectified.push(intermediate);
      prev = intermediate;
      axis = axis === 'horizontal' ? 'vertical' : 'horizontal';
      dx = current.x - prev.x;
      dy = current.y - prev.y;
      guard++;
    }

    if (i < checkpoints.length - 1 && (current.x !== prev.x || current.y !== prev.y)) {
      rectified.push(current);
    }
    prev = current;
    axis = axis === 'horizontal' ? 'vertical' : 'horizontal';
  }

  return rectified;
}

export function simplifyRectilinearBends(
  bendingPoints: XYPosition[],
  source: XYPosition,
  target: XYPosition
): XYPosition[] {
  if (bendingPoints.length === 0) {
    return bendingPoints;
  }

  const points: XYPosition[] = [source, ...bendingPoints, target];

  // Remove duplicates that would introduce zero-length segments.
  const deduplicated: XYPosition[] = [];
  for (const point of points) {
    const previous = deduplicated[deduplicated.length - 1];
    if (!previous || previous.x !== point.x || previous.y !== point.y) {
      deduplicated.push(point);
    }
  }

  // Simplify collinear runs and small S-shaped detours.
  const working: XYPosition[] = [...deduplicated];
  let mutated = true;
  while (mutated && working.length >= 3) {
    mutated = false;

    for (let i = 1; i < working.length - 1; i++) {
      const prev = working[i - 1];
      const curr = working[i];
      const next = working[i + 1];
      if (!prev || !curr || !next) {
        continue;
      }
      const sameX = prev.x === curr.x && curr.x === next.x;
      const sameY = prev.y === curr.y && curr.y === next.y;
      if (sameX || sameY) {
        working.splice(i, 1);
        mutated = true;
        break;
      }
    }
    if (mutated) {
      continue;
    }

    for (let i = 0; i < working.length - 3; i++) {
      const touchesSource = i === 0;
      const touchesTarget = i + 3 === working.length - 1;
      if (touchesSource || touchesTarget) {
        continue;
      }

      const a = working[i];
      const b = working[i + 1];
      const c = working[i + 2];
      const d = working[i + 3];
      if (!a || !b || !c || !d) {
        continue;
      }

      const horizontalAB = a.y === b.y && a.x !== b.x;
      const verticalBC = b.x === c.x && b.y !== c.y;
      const horizontalCD = c.y === d.y && c.x !== d.x;
      if (horizontalAB && verticalBC && horizontalCD) {
        const dir1 = Math.sign(b.x - a.x);
        const dir3 = Math.sign(d.x - c.x);
        const span1 = Math.abs(b.x - a.x);
        const spanMiddle = Math.abs(c.y - b.y);
        const span3 = Math.abs(d.x - c.x);
        const isSmallDetour = Math.max(span1, span3, spanMiddle) <= MIN_DETOUR_SPAN;
        if (dir1 !== 0 && dir3 !== 0 && dir1 === -dir3 && a.x === d.x && isSmallDetour) {
          working.splice(i + 1, 2);
          mutated = true;
          break;
        }
      }

      const verticalAB = a.x === b.x && a.y !== b.y;
      const horizontalBC = b.y === c.y && b.x !== c.x;
      const verticalCD = c.x === d.x && c.y !== d.y;
      if (verticalAB && horizontalBC && verticalCD) {
        const dir1 = Math.sign(b.y - a.y);
        const dir3 = Math.sign(d.y - c.y);
        const span1 = Math.abs(b.y - a.y);
        const spanMiddle = Math.abs(c.x - b.x);
        const span3 = Math.abs(d.y - c.y);
        const isSmallDetour = Math.max(span1, span3, spanMiddle) <= MIN_DETOUR_SPAN;
        if (dir1 !== 0 && dir3 !== 0 && dir1 === -dir3 && a.y === d.y && isSmallDetour) {
          working.splice(i + 1, 2);
          mutated = true;
          break;
        }
      }
    }
  }

  return working.slice(1, working.length - 1);
}

function enforceTargetClearance(
  bendingPoints: XYPosition[],
  targetPosition: Position,
  target: XYPosition,
  minOutwardLength: number
): XYPosition[] {
  if (bendingPoints.length === 0 || minOutwardLength <= 0) {
    return bendingPoints;
  }

  const adjusted = [...bendingPoints];
  const tailIndex = adjusted.length - 1;
  const tailPoint = adjusted[tailIndex];
  if (!tailPoint) {
    return bendingPoints;
  }

  switch (targetPosition) {
    case Position.Left:
    case Position.Right: {
      const inwardDirection = targetPosition === Position.Left ? -1 : 1;
      const horizontalDistance = Math.abs(target.x - tailPoint.x);
      if (horizontalDistance < minOutwardLength) {
        const desiredX = target.x + inwardDirection * minOutwardLength;
        const initialColumnX = tailPoint.x;
        let index = tailIndex;
        while (index >= 0 && adjusted[index]?.x === initialColumnX) {
          const current = adjusted[index];
          if (!current) {
            break;
          }
          adjusted[index] = { x: desiredX, y: current.y };
          index--;
        }
      }
      break;
    }
    case Position.Top:
    case Position.Bottom: {
      const inwardDirection = targetPosition === Position.Top ? -1 : 1;
      const verticalDistance = Math.abs(target.y - tailPoint.y);
      if (verticalDistance < minOutwardLength) {
        const desiredY = target.y + inwardDirection * minOutwardLength;
        const initialColumnY = tailPoint.y;
        let index = tailIndex;
        while (index >= 0 && adjusted[index]?.y === initialColumnY) {
          const current = adjusted[index];
          if (!current) {
            break;
          }
          adjusted[index] = { x: current.x, y: desiredY };
          index--;
        }
      }
      break;
    }
  }

  return adjusted;
}

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

  const sourceNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(source);
  const targetNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(target);

  if (!sourceNode || !targetNode) {
    return null;
  }

  // The rendering flow:
  // 1. Resolve layout handlers to let nodes customize handle positions when needed.
  // 2. Compute handle coordinates for both endpoints (honouring node-specific overrides).
  // 3. Offset the endpoints so markers still touch the node border visually.
  // 4. Build the rectilinear bending points, either reusing user data or converting a smooth path.
  // 5. Simplify the rectilinear path to eliminate redundant elbows.
  // 6. Delegate to the multi-label rectilinear edge renderer with the assembled geometry.

  const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
    nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
  );

  // Coordinates always lie on the declared position of the node, optionally adjusted by the handler.
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
  const customEdge = !!(data && data.bendingPoints && data.bendingPoints.length > 0);
  const fanInEnabled = data?.rectilinearFanInEnabled ?? DEFAULT_FAN_IN_ENABLED;
  const fanOutEnabled = data?.rectilinearFanOutEnabled ?? DEFAULT_FAN_OUT_ENABLED;
  if (data && data.bendingPoints && data.bendingPoints.length > 0) {
    // Preserve user-authored geometry; downstream component treats this as a custom edge.
    bendingPoints = data.bendingPoints;
  } else {
    // Fallback: translate the smooth quadratic path from React Flow into Manhattan-style bends.
    const [smoothEdgePath] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });

    const quadraticCurvePoints: XYPosition[] = smoothEdgePath.includes('NaN')
      ? []
      : parse(smoothEdgePath)
          .filter((segment) => segment.code === 'Q' && typeof segment.x === 'number' && typeof segment.y === 'number')
          .map((segment) => ({ x: segment.x as number, y: segment.y as number }));

    if (quadraticCurvePoints.length > 0) {
      // When no custom bends are given, we shape the first turn according to the chosen strategy.
      const turnPreference: RectilinearTurnPreference = data?.rectilinearTurnPreference ?? DEFAULT_TURN_PREFERENCE;
      const minOutwardLengthCandidate = data?.rectilinearMinOutwardLength;
      const minOutwardLength =
        typeof minOutwardLengthCandidate === 'number' &&
        Number.isFinite(minOutwardLengthCandidate) &&
        minOutwardLengthCandidate > 0
          ? minOutwardLengthCandidate
          : DEFAULT_MIN_OUTWARD_LENGTH;
      const firstPoint = quadraticCurvePoints[0];
      if (firstPoint) {
        switch (sourcePosition) {
          case Position.Right:
          case Position.Left:
            // Seed the first bend so the polyline departs horizontally from a left/right handle.
            {
              const defaultTurnX = firstPoint.x ?? sourceX;
              const outwardDirection = sourcePosition === Position.Left ? -1 : 1;
              const preferredTurnX = enforceMinimumClearance(
                computePreferredTurnCoordinate(defaultTurnX, sourceX, targetX, turnPreference),
                sourceX,
                defaultTurnX,
                outwardDirection,
                minOutwardLength
              );
              quadraticCurvePoints[0] = { x: preferredTurnX, y: firstPoint.y };
              bendingPoints.push({ x: preferredTurnX, y: sourceY });
            }
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (!currentPoint || !previousPoint) {
                continue;
              }
              const { x: currentX, y: currentY } = currentPoint;
              const { x: previousX, y: previousY } = previousPoint;
              // Even/odd index alternation swaps axis, keeping the path rectilinear.
              if (isMultipleOfTwo(i)) {
                bendingPoints.push({ x: currentX, y: previousY });
              } else {
                bendingPoints.push({ x: previousX, y: currentY });
              }
            }
            break;
          case Position.Top:
          case Position.Bottom:
            // Seed the first bend so the polyline departs vertically from a top/bottom handle.
            {
              const defaultTurnY = firstPoint.y ?? sourceY;
              const outwardDirection = sourcePosition === Position.Top ? -1 : 1;
              const preferredTurnY = enforceMinimumClearance(
                computePreferredTurnCoordinate(defaultTurnY, sourceY, targetY, turnPreference),
                sourceY,
                defaultTurnY,
                outwardDirection,
                minOutwardLength
              );
              quadraticCurvePoints[0] = { x: firstPoint.x, y: preferredTurnY };
              bendingPoints.push({ x: sourceX, y: preferredTurnY });
            }
            for (let i = 1; i < quadraticCurvePoints.length; i++) {
              const currentPoint = quadraticCurvePoints[i];
              const previousPoint = quadraticCurvePoints[i - 1];
              if (!currentPoint || !previousPoint) {
                continue;
              }
              const { x: currentX, y: currentY } = currentPoint;
              const { x: previousX, y: previousY } = previousPoint;
              // Even/odd index alternation swaps axis, keeping the path rectilinear.
              if (isMultipleOfTwo(i)) {
                bendingPoints.push({ x: previousX, y: currentY });
              } else {
                bendingPoints.push({ x: currentX, y: previousY });
              }
            }
            break;
        }

        if (fanOutEnabled && getEdges) {
          const edges = (getEdges() ?? []) as Edge<MultiLabelEdgeData>[];
          const arrangement = determineSourceFanArrangement(
            edges,
            id,
            source,
            sourceHandleId,
            sourcePosition,
            (nodeId) => getNode(nodeId) as Node<NodeData> | undefined
          );
          if (arrangement.count > 1) {
            const fanResult = applyFanOffsetAtSource(
              bendingPoints,
              sourcePosition,
              { x: sourceX, y: sourceY },
              arrangement.offset,
              minOutwardLength,
              arrangement
            );
            bendingPoints = fanResult.bendingPoints;
            sourceX = fanResult.sourceX;
            sourceY = fanResult.sourceY;
          }
        }

        if (fanInEnabled && getEdges) {
          const edges = (getEdges() ?? []) as Edge<MultiLabelEdgeData>[];
          const arrangement = determineFanArrangement(
            edges,
            id,
            target,
            targetHandleId,
            targetPosition,
            (nodeId) => getNode(nodeId) as Node<NodeData> | undefined
          );
          if (arrangement.count > 1) {
            const fanResult = applyFanOffset(
              bendingPoints,
              targetPosition,
              { x: sourceX, y: sourceY },
              { x: targetX, y: targetY },
              arrangement.offset,
              minOutwardLength,
              arrangement
            );
            bendingPoints = fanResult.bendingPoints;
            targetX = fanResult.targetX;
            targetY = fanResult.targetY;
          }
        }

        bendingPoints = enforceTargetClearance(
          bendingPoints,
          targetPosition,
          { x: targetX, y: targetY },
          minOutwardLength
        );
      }
    }
  }

  const baselineBendingPoints = customEdge
    ? bendingPoints
    : ensureRectilinearPath(
        bendingPoints,
        { x: sourceX, y: sourceY },
        { x: targetX, y: targetY },
        determineInitialAxis(sourcePosition)
      );

  // Run the rectilinear simplification before invoking the last-chance detour so the
  // detour logic works on the definitive orthogonal backbone (otherwise a later
  // simplification pass could erase the detour we add).
  let rectifiedBendingPoints = customEdge
    ? baselineBendingPoints
    : simplifyRectilinearBends(
        baselineBendingPoints,
        { x: sourceX, y: sourceY },
        { x: targetX, y: targetY }
      );

  if (!customEdge && getEdges) {
    const edgesForDetour = (getEdges() ?? []) as Edge<MultiLabelEdgeData>[];
    const nodesForDetour = (getNodes?.() ?? []) as Node<NodeData, DiagramNodeType>[];
    if (edgesForDetour.length > 0 && nodesForDetour.length > 0) {
      const nodesAsNodes = nodesForDetour as Node<NodeData, DiagramNodeType>[];
      const baselinePolyline: XYPosition[] = [
        { x: sourceX, y: sourceY },
        ...rectifiedBendingPoints.map((point) => ({ x: point.x, y: point.y })),
        { x: targetX, y: targetY },
      ];

      const currentEdge =
        edgesForDetour.find((edge) => edge.id === id) ??
        ({
          id,
          source,
          target,
          sourceHandle: sourceHandleId ?? undefined,
          targetHandle: targetHandleId ?? undefined,
          sourcePosition,
          targetPosition,
          data,
        } as Edge<MultiLabelEdgeData>);

      const detouredPolyline = buildDetouredPolyline(
        currentEdge,
        baselinePolyline,
        edgesForDetour,
        nodesAsNodes
      );

      if (!polylinesEqual(detouredPolyline, baselinePolyline)) {
        const firstPoint = detouredPolyline[0];
        const lastPoint = detouredPolyline[detouredPolyline.length - 1];
        if (firstPoint) {
          sourceX = firstPoint.x;
          sourceY = firstPoint.y;
        }
        if (lastPoint) {
          targetX = lastPoint.x;
          targetY = lastPoint.y;
        }
        rectifiedBendingPoints = detouredPolyline.slice(1, -1);
      }
    }
  }

  const finalBendingPoints = simplifyRectilinearBends(
    rectifiedBendingPoints,
    { x: sourceX, y: sourceY },
    { x: targetX, y: targetY }
  );

  return (
    <MultiLabelRectilinearEditableEdge
      {...props}
      sourceX={sourceX}
      sourceY={sourceY}
      targetX={targetX}
      targetY={targetY}
      bendingPoints={finalBendingPoints}
      customEdge={customEdge}
      sourceNode={sourceNode}
      targetNode={targetNode}
    />
  );
});
