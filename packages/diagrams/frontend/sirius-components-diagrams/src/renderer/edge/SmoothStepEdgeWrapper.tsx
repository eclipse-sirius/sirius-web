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
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { buildDetouredPolyline } from '../layout/postProcessEdgeDetours';
import { buildSpacedPolylines, DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS } from '../layout/postProcessEdgeParallelism';
import { straightenAlmostStraightPolyline } from '../layout/postProcessEdgeStraighten';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData, RectilinearTurnPreference } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';
import type { RoutingTracePhase } from './RoutingTraceContext';
import { ROUTING_TRACE_NOOP_COLLECTOR, useRoutingTraceCollector } from './RoutingTraceContext';

/**
 * Converts the default XYFlow smooth-step edges into annotated rectilinear polylines.
 * The wrapper coordinates every post-processing stage required by Sirius:
 * - resolve custom handle coordinates provided by node layout handlers
 * - derive a Manhattan polyline from either stored bends or XYFlow's quadratic path
 * - fan edges per side so multiple connections remain readable
 * - steer the polyline around detected node collisions and re-run spacing
 * - straighten near-axis-aligned paths
 * The final geometry (endpoints + bends) is then rendered by the multi-label edge component.
 */

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
// Enables a segment overlap detection to make the edges then parallel, disabled currently as it seems to lead to regressions.
const DEFAULT_PARALLEL_SPACING_ENABLED = true;
// Enables the almost-straight post-processing pass unless an edge opts out.
const DEFAULT_STRAIGHTEN_ENABLED = true;
// Maximum deviation (in pixels) we tolerate before declaring that a zig-zag is meaningful.
const DEFAULT_STRAIGHTEN_THRESHOLD = 20;

type PositionAwareEdge = Edge<MultiLabelEdgeData> & {
  targetPosition?: Position;
  sourcePosition?: Position;
};
type Axis = 'horizontal' | 'vertical';

type ParallelSpacingSnapshot = {
  contextSignature: string;
  baselineFingerprint: string;
  spacedFingerprint: string;
  baselinePolylines: Map<string, XYPosition[]>;
  spacedPolylines: Map<string, XYPosition[]>;
};

let parallelSpacingSnapshot: ParallelSpacingSnapshot | null = null;

const clonePolylineMap = (polylines: Map<string, XYPosition[]>): Map<string, XYPosition[]> =>
  new Map(
    Array.from(polylines.entries()).map(([edgeId, points]) => [
      edgeId,
      points.map((point) => ({ x: point.x, y: point.y })),
    ])
  );

const createPolylinesFingerprint = (polylines: Map<string, XYPosition[]>): string => {
  const serialisable = Array.from(polylines.entries())
    .map(([edgeId, points]) => ({
      edgeId,
      points: points.map((point) => ({
        x: Number(point.x.toFixed(2)),
        y: Number(point.y.toFixed(2)),
      })),
    }))
    .sort((a, b) => a.edgeId.localeCompare(b.edgeId));
  return JSON.stringify(serialisable);
};

const createSpacingContextSignature = (
  edges: Edge<MultiLabelEdgeData>[],
  resolveNode: (id: string) => Node<NodeData> | undefined
): string => {
  const serialisable = edges
    .map((edge) => {
      const sourceNode = resolveNode(edge.source);
      const targetNode = resolveNode(edge.target);
      const sourcePosition = sourceNode?.position ?? { x: 0, y: 0 };
      const targetPosition = targetNode?.position ?? { x: 0, y: 0 };
      const sourceDimensions = {
        width: sourceNode?.width ?? 0,
        height: sourceNode?.height ?? 0,
      };
      const targetDimensions = {
        width: targetNode?.width ?? 0,
        height: targetNode?.height ?? 0,
      };
      return {
        id: edge.id,
        source: edge.source,
        target: edge.target,
        sourceX: Number(sourcePosition.x.toFixed(2)),
        sourceY: Number(sourcePosition.y.toFixed(2)),
        sourceWidth: Number(sourceDimensions.width.toFixed(2)),
        sourceHeight: Number(sourceDimensions.height.toFixed(2)),
        targetX: Number(targetPosition.x.toFixed(2)),
        targetY: Number(targetPosition.y.toFixed(2)),
        targetWidth: Number(targetDimensions.width.toFixed(2)),
        targetHeight: Number(targetDimensions.height.toFixed(2)),
      };
    })
    .sort((a, b) => a.id.localeCompare(b.id));
  return JSON.stringify(serialisable);
};

const QUADRATIC_SEGMENT_PATTERN =
  /Q\s*(-?\d+(?:\.\d+)?(?:e[+-]?\d+)?)\s*,\s*(-?\d+(?:\.\d+)?(?:e[+-]?\d+)?)\s+(-?\d+(?:\.\d+)?(?:e[+-]?\d+)?)\s*,\s*(-?\d+(?:\.\d+)?(?:e[+-]?\d+)?)/gi;

/**
 * Extract the corner coordinates from a smooth-step path string. The XYFlow implementation
 * always emits quadratic segments for the rounded bends, so we only need to read the `Q`
 * commands and collect their end points to reconstruct the rectilinear polyline.
 */
const extractQuadraticCornerPoints = (path: string): XYPosition[] => {
  if (!path || path.includes('NaN')) {
    return [];
  }

  const points: XYPosition[] = [];
  QUADRATIC_SEGMENT_PATTERN.lastIndex = 0;

  for (const match of path.matchAll(QUADRATIC_SEGMENT_PATTERN)) {
    const x = Number(match[3]);
    const y = Number(match[4]);
    if (Number.isFinite(x) && Number.isFinite(y)) {
      points.push({ x, y });
    }
  }

  return points;
};

function interpolateHalfway(from: number, to: number): number {
  return from + (to - from) * 0.5;
}

/**
 * Decide where the first turn after the source node should occur based on the
 * requested preference. The value is always finite and falls back to whichever
 * coordinate keeps the path stable when the preference cannot be honoured.
 */
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

/**
 * Ensure the first segment leaving a node exits in the correct direction and
 * travels at least `minOutwardLength` pixels before any lateral turns. This keeps
 * markers from overlapping the node border and mitigates tiny zig-zags around ports.
 */
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

/**
 * Read the centre coordinate for either axis so we can sort edges consistently when
 * computing the fan arrangements. Missing nodes default to 0 which naturally keeps
 * orphan edges at the beginning of the ordering.
 */
function getNodeCenterCoordinate(node: Node<NodeData> | undefined, axis: 'x' | 'y'): number {
  if (!node) {
    return 0;
  }
  if (axis === 'x') {
    return node.position.x + (node.width ?? 0) / 2;
  }
  return node.position.y + (node.height ?? 0) / 2;
}

/**
 * Compute a symmetric offset so the N parallel edges on a face are spread evenly.
 * The index is relative to the sorted order and always yields values within the
 * configured spread window.
 */
function symmetricOffset(index: number, count: number): number {
  if (count <= 1) {
    return 0;
  }
  const step = Math.min(FAN_SPACING, FAN_MAX_SPREAD / (count - 1));
  const half = (count - 1) / 2;
  return clamp((index - half) * step, -FAN_MAX_SPREAD, FAN_MAX_SPREAD);
}

/**
 * Shallow equality check for polylines. Used to avoid unnecessary re-renders.
 */
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

const normalisePolyline = (polyline: XYPosition[] | undefined): XYPosition[] | undefined => {
  if (!polyline) {
    return undefined;
  }
  if (polyline.length < 2) {
    return polyline.map((point) => ({ x: point.x, y: point.y }));
  }
  const sourcePoint = polyline[0];
  const targetPoint = polyline[polyline.length - 1];
  if (!sourcePoint || !targetPoint) {
    return polyline.map((point) => ({ x: point.x, y: point.y }));
  }
  const simplifiedBends = simplifyRectilinearBends(polyline.slice(1, -1), sourcePoint, targetPoint);
  return [
    { x: sourcePoint.x, y: sourcePoint.y },
    ...simplifiedBends.map((point) => ({ x: point.x, y: point.y })),
    { x: targetPoint.x, y: targetPoint.y },
  ];
};

const buildPolyline = (
  sourceX: number,
  sourceY: number,
  bends: XYPosition[],
  targetX: number,
  targetY: number
): XYPosition[] => {
  const result: XYPosition[] = new Array(bends.length + 2);
  result[0] = { x: sourceX, y: sourceY };
  for (let i = 0; i < bends.length; i++) {
    const bend = bends[i]!;
    result[i + 1] = { x: bend.x, y: bend.y };
  }
  result[result.length - 1] = { x: targetX, y: targetY };
  return result;
};

/**
 * Seeds the orthogonal path reconstruction with the correct starting axis based
 * on the port orientation. Horizontal handles produce horizontal-first paths.
 */
function determineInitialAxis(position: Position): Axis {
  return isHorizontalPosition(position) ? 'horizontal' : 'vertical';
}

/**
 * Metadata describing how an edge should be offset when several connections use
 * the same face: `offset` tells how far we shift the column, `index` is the sorted
 * position among siblings, `count` is the total number of siblings considered.
 */
type FanArrangement = {
  offset: number;
  count: number;
  index: number;
};

type EndpointRole = 'source' | 'target';

/**
 * Lightweight descriptor capturing how a concrete Edge touches a node. It records
 * whether the node acts as source/target, which handle/side is involved, and the
 * opposite node id so we can sort siblings deterministically.
 */
type NodeSideEdge = {
  edge: PositionAwareEdge;
  role: EndpointRole;
  handleId: string;
  position?: Position;
  oppositeNodeId: string;
};

const collectNodeSideEdges = (edges: Edge<MultiLabelEdgeData>[], nodeId: string): NodeSideEdge[] => {
  /**
   * Materialise every edge touching the specified node, labelling whether the node
   * acts as source or target. The result lets the fan logic treat incoming and
   * outgoing connections uniformly for a given node side.
   */
  const positionedEdges = edges as PositionAwareEdge[];
  const descriptors: NodeSideEdge[] = [];

  positionedEdges.forEach((edge) => {
    if (edge.source === nodeId) {
      descriptors.push({
        edge,
        role: 'source',
        handleId: edge.sourceHandle ?? '',
        position: edge.sourcePosition,
        oppositeNodeId: edge.target,
      });
    }
    if (edge.target === nodeId) {
      descriptors.push({
        edge,
        role: 'target',
        handleId: edge.targetHandle ?? '',
        position: edge.targetPosition,
        oppositeNodeId: edge.source,
      });
    }
  });

  return descriptors;
};

function determineFanArrangementForSide(
  edges: Edge<MultiLabelEdgeData>[],
  currentEdgeId: string,
  currentRole: EndpointRole,
  nodeId: string,
  position: Position,
  handleId: string | null | undefined,
  getNode: (id: string) => Node<NodeData> | undefined
): FanArrangement {
  /**
   * Compute the symmetrical fan arrangement for a node face regardless of whether
   * edges are entering or leaving that face. Incoming and outgoing edges that share
   * the same side contribute to the spacing count so the visual spread is consistent.
   */
  const normalizedHandleId = handleId ?? '';
  const sideDescriptors = collectNodeSideEdges(edges, nodeId);

  const sameSideDescriptors = sideDescriptors.filter((descriptor) => {
    const descriptorPosition = descriptor.position ?? position;
    return descriptorPosition === position;
  });

  if (sameSideDescriptors.length <= 1) {
    return { offset: 0, index: 0, count: sameSideDescriptors.length };
  }

  const candidateDescriptors = (() => {
    const sameHandleDescriptors = sameSideDescriptors.filter(
      (descriptor) => descriptor.handleId === normalizedHandleId
    );
    if (sameHandleDescriptors.length > 1) {
      return sameHandleDescriptors;
    }
    return sameSideDescriptors;
  })();

  if (candidateDescriptors.length <= 1) {
    return { offset: 0, index: 0, count: candidateDescriptors.length };
  }

  const axis: 'x' | 'y' = isHorizontalPosition(position) ? 'y' : 'x';
  const sortedDescriptors = candidateDescriptors.slice().sort((descriptorA, descriptorB) => {
    const coordA = getNodeCenterCoordinate(getNode(descriptorA.oppositeNodeId), axis);
    const coordB = getNodeCenterCoordinate(getNode(descriptorB.oppositeNodeId), axis);
    if (coordA !== coordB) {
      return coordA - coordB;
    }
    if (descriptorA.role !== descriptorB.role) {
      return descriptorA.role === 'source' ? -1 : 1;
    }
    return descriptorA.edge.id.localeCompare(descriptorB.edge.id);
  });

  let descriptorIndex = sortedDescriptors.findIndex(
    (descriptor) => descriptor.edge.id === currentEdgeId && descriptor.role === currentRole
  );
  if (descriptorIndex === -1) {
    descriptorIndex = 0;
  }

  return {
    offset: symmetricOffset(descriptorIndex, sortedDescriptors.length),
    count: sortedDescriptors.length,
    index: descriptorIndex,
  };
}

type FanOffsetResult = {
  bendingPoints: XYPosition[];
  x: number;
  y: number;
};

/**
 * Translate the abstract fan arrangement into a concrete outward travel distance.
 * Edges closer to the centre travel the minimum distance, whereas outer edges gain
 * a bit more runway so the group forms a gentle fan rather than a flat stack.
 */
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

/**
 * Force the last few points of the polyline to share a column/row value. This keeps
 * the fan offsets aligned when multiple bends sit flush against the node boundary.
 */
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
    points[pointer] = axis === 'x' ? { x: value, y: current.y } : { x: current.x, y: value };
    pointer--;
  }
}

/**
 * Same as alignTailCoordinate but applied to the first column/row leaving the source.
 */
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

const applyFanOffsetGeneric = (
  role: EndpointRole,
  bendingPoints: XYPosition[],
  position: Position,
  sourceCoordinates: { x: number; y: number },
  targetCoordinates: { x: number; y: number },
  perpendicularOffset: number,
  minOutwardLength: number,
  arrangement?: FanArrangement
): FanOffsetResult => {
  /**
   * Shared logic used to offset the first/last column of the rectilinear polyline.
   * The behaviour mirrors the previous source/target helpers while making the axis
   * adjustments configurable based on the endpoint role.
   */
  const adjustedPoints = bendingPoints.map((point) => ({ ...point }));
  const hasFanArrangement = arrangement ? arrangement.count > 1 : false;
  const outwardLength = hasFanArrangement
    ? computeFanOutwardLength(arrangement as FanArrangement, minOutwardLength)
    : Math.max(minOutwardLength, 1);

  const coordinates = role === 'source' ? sourceCoordinates : targetCoordinates;
  const counterpartCoordinates = role === 'source' ? targetCoordinates : sourceCoordinates;
  const align = role === 'source' ? alignHeadCoordinate : alignTailCoordinate;
  const isHorizontal = isHorizontalPosition(position);
  const outwardDirection = (() => {
    switch (position) {
      case Position.Right:
      case Position.Bottom:
        return 1;
      case Position.Left:
      case Position.Top:
      default:
        return -1;
    }
  })();

  let baseX = coordinates.x;
  let baseY = coordinates.y;

  if (isHorizontal) {
    baseY = coordinates.y + perpendicularOffset;
    if (adjustedPoints.length === 0) {
      const columnX = coordinates.x + outwardDirection * outwardLength;
      if (role === 'source') {
        adjustedPoints.push({ x: columnX, y: baseY });
      } else {
        adjustedPoints.push({ x: columnX, y: counterpartCoordinates.y });
        adjustedPoints.push({ x: columnX, y: baseY });
      }
    }
    if (perpendicularOffset !== 0) {
      align(adjustedPoints, 'y', baseY);
    }
    if (hasFanArrangement) {
      const desiredX = coordinates.x + outwardDirection * outwardLength;
      align(adjustedPoints, 'x', desiredX);
    }
  } else {
    baseX = coordinates.x + perpendicularOffset;
    if (adjustedPoints.length === 0) {
      const columnY = coordinates.y + outwardDirection * outwardLength;
      if (role === 'source') {
        adjustedPoints.push({ x: baseX, y: columnY });
      } else {
        adjustedPoints.push({ x: counterpartCoordinates.x, y: columnY });
        adjustedPoints.push({ x: baseX, y: columnY });
      }
    }
    if (perpendicularOffset !== 0) {
      align(adjustedPoints, 'x', baseX);
    }
    if (hasFanArrangement) {
      const desiredY = coordinates.y + outwardDirection * outwardLength;
      align(adjustedPoints, 'y', desiredY);
    }
  }

  // Return the adjusted endpoint coordinates so callers can re-anchor markers after the fan shift.
  const finalX = isHorizontal ? coordinates.x : baseX;
  const finalY = isHorizontal ? baseY : coordinates.y;

  return {
    bendingPoints: adjustedPoints,
    x: finalX,
    y: finalY,
  };
};

export function ensureRectilinearPath(
  bendingPoints: XYPosition[],
  source: XYPosition,
  target: XYPosition,
  initialAxis: Axis
): XYPosition[] {
  /**
   * Convert a list of bends produced by the smooth-step sampling into a strict
   * orthogonal polyline, inserting elbows whenever the curve drifts off-axis.
   * The function alternates between horizontal and vertical segments to keep
   * the resulting path Manhattan-style.
   */
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
      const intermediate = axis === 'horizontal' ? { x: current.x, y: prev.y } : { x: prev.x, y: current.y };
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

/**
 * Collapse redundant bends while keeping the path orthogonal. This helper removes:
 * - duplicate points introduced by earlier passes
 * - straight collinear runs that add no visual information
 * - tiny "S" detours that often appear after detouring around obstacles
 * The result still starts/ends at the provided source/target coordinates.
 */
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

/**
 * Guarantee that the polyline approaches the target face with enough clearance.
 * When the last column/row sits too close to the handle we propagate a correction
 * backwards so arrowheads do not clip inside the node.
 */
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

/**
 * Primary edge renderer entry point. Each render reconciles store state, post-processing rules,
 * and user supplied metadata to produce a final rectilinear polyline:
 * - gather node layout handlers to determine custom handle coordinates
 * - expand the default smooth-step curve into rectilinear bends when necessary
 * - apply per-side fan spreading, obstacle detours, straightening, and parallel spacing
 * The resulting coordinates are forwarded to the editable multi-label rectilinear edge component.
 */
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

  const routingTraceCollector = useRoutingTraceCollector();
  const tracingEnabled = routingTraceCollector !== ROUTING_TRACE_NOOP_COLLECTOR;
  const traceRoutingDecision = tracingEnabled
    ? (phase: RoutingTracePhase, message: string, polyline: XYPosition[], metadata?: Record<string, unknown>) => {
        routingTraceCollector({
          edgeId: id,
          phase,
          message,
          polyline: polyline.map((point) => ({ x: point.x, y: point.y })),
          metadata,
        });
      }
    : undefined;
  const traceAdjustments = tracingEnabled
    ? {
        fanOut: false,
        fanIn: false,
        simplified: false,
        straightened: false,
        detoured: false,
        parallelSpacing: false,
      }
    : null;

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
  // Fetch the complete edge list only once; we need it for fan calculations, detours, and spacing.
  const edgesFromStore = (getEdges?.() ?? []) as Edge<MultiLabelEdgeData>[];

  // Pre-compute the fan arrangement for both endpoints so subsequent passes can reuse the same metadata.
  let sourceArrangement: FanArrangement | undefined;
  let targetArrangement: FanArrangement | undefined;

  if (edgesFromStore.length > 0) {
    // Determine how many siblings leave the same side of the source node.
    sourceArrangement = determineFanArrangementForSide(
      edgesFromStore,
      id,
      'source',
      source,
      sourcePosition,
      sourceHandleId,
      (nodeId) => getNode(nodeId) as Node<NodeData> | undefined
    );
    // Symmetric calculation for the target side.
    targetArrangement = determineFanArrangementForSide(
      edgesFromStore,
      id,
      'target',
      target,
      targetPosition,
      targetHandleId,
      (nodeId) => getNode(nodeId) as Node<NodeData> | undefined
    );
  }
  if (data && data.bendingPoints && data.bendingPoints.length > 0) {
    // Preserve user-authored geometry; downstream component treats this as a custom edge.
    bendingPoints = data.bendingPoints;
    if (traceRoutingDecision) {
      traceRoutingDecision(
        'initial',
        'Using custom bending points from edge data',
        buildPolyline(sourceX, sourceY, bendingPoints, targetX, targetY),
        { customEdge: true, bendCount: bendingPoints.length }
      );
    }
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

    const quadraticCurvePoints = extractQuadraticCornerPoints(smoothEdgePath);

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

        // Apply the fan-out only when multiple edges share the source side and the feature is enabled.
        if (fanOutEnabled && sourceArrangement && sourceArrangement.count > 1) {
          const fanResult = applyFanOffsetGeneric(
            'source',
            bendingPoints,
            sourcePosition,
            { x: sourceX, y: sourceY },
            { x: targetX, y: targetY },
            sourceArrangement.offset,
            minOutwardLength,
            sourceArrangement
          );
          bendingPoints = fanResult.bendingPoints;
          sourceX = fanResult.x;
          sourceY = fanResult.y;
          if (traceRoutingDecision) {
            if (traceAdjustments) {
              traceAdjustments.fanOut = true;
            }
            traceRoutingDecision(
              'fan-out',
              'Applied fan-out spacing at source handle',
              buildPolyline(sourceX, sourceY, bendingPoints, targetX, targetY),
              {
                count: sourceArrangement.count,
                index: sourceArrangement.index,
                offset: sourceArrangement.offset,
              }
            );
          }
        }

        // Apply the fan-in only when the target side receives more than one edge.
        if (fanInEnabled && targetArrangement && targetArrangement.count > 1) {
          const fanResult = applyFanOffsetGeneric(
            'target',
            bendingPoints,
            targetPosition,
            { x: sourceX, y: sourceY },
            { x: targetX, y: targetY },
            targetArrangement.offset,
            minOutwardLength,
            targetArrangement
          );
          bendingPoints = fanResult.bendingPoints;
          targetX = fanResult.x;
          targetY = fanResult.y;
          if (traceRoutingDecision) {
            if (traceAdjustments) {
              traceAdjustments.fanIn = true;
            }
            traceRoutingDecision(
              'fan-in',
              'Applied fan-in spacing at target handle',
              buildPolyline(sourceX, sourceY, bendingPoints, targetX, targetY),
              {
                count: targetArrangement.count,
                index: targetArrangement.index,
                offset: targetArrangement.offset,
              }
            );
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

  if (!customEdge && traceRoutingDecision) {
    traceRoutingDecision(
      'initial',
      'Derived bending points from smooth-step path',
      buildPolyline(sourceX, sourceY, bendingPoints, targetX, targetY),
      { customEdge: false, bendCount: bendingPoints.length }
    );
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
    : simplifyRectilinearBends(baselineBendingPoints, { x: sourceX, y: sourceY }, { x: targetX, y: targetY });

  if (traceRoutingDecision) {
    const removedBends = baselineBendingPoints.length - rectifiedBendingPoints.length;
    if (traceAdjustments && removedBends !== 0) {
      traceAdjustments.simplified = true;
    }
    traceRoutingDecision(
      'simplify',
      removedBends !== 0
        ? `Simplified polyline by removing ${removedBends} bend${removedBends === 1 ? '' : 's'}`
        : 'Baseline polyline after simplification',
      buildPolyline(sourceX, sourceY, rectifiedBendingPoints, targetX, targetY),
      {
        customEdge,
        removedBends,
      }
    );
  }

  // The straightening pass only runs for automatically routed edges; custom polylines remain untouched.
  const straightenEnabled = (data?.rectilinearStraightenEnabled ?? DEFAULT_STRAIGHTEN_ENABLED) && !customEdge;
  if (straightenEnabled) {
    const sourceFanCount = sourceArrangement?.count ?? 1;
    const targetFanCount = targetArrangement?.count ?? 1;
    // Never collapse the micro zig-zag when a fan already spreads the edges around the ports.
    const fansWouldBeFlattened = sourceFanCount > 1 || targetFanCount > 1;
    // Both ends must share the same axis preference otherwise the edge is intentionally L-shaped.
    const sourceIsHorizontal = isHorizontalPosition(sourcePosition);
    const targetIsHorizontal = isHorizontalPosition(targetPosition);
    if (!fansWouldBeFlattened && sourceIsHorizontal === targetIsHorizontal) {
      // Horizontal handles imply we snap along Y, vertical handles snap along X.
      const axis: Axis = sourceIsHorizontal ? 'horizontal' : 'vertical';
      // Ask the helper to return a straightened version of the polyline (if applicable).
      const straightenedPolyline = straightenAlmostStraightPolyline(
        [
          { x: sourceX, y: sourceY },
          ...rectifiedBendingPoints.map((point) => ({ x: point.x, y: point.y })),
          { x: targetX, y: targetY },
        ],
        {
          axis,
          threshold: DEFAULT_STRAIGHTEN_THRESHOLD,
          sourceCount: sourceArrangement?.count ?? 1,
          targetCount: targetArrangement?.count ?? 1,
        }
      );
      if (straightenedPolyline && straightenedPolyline.length >= 2) {
        // Re-anchor both endpoints to the straightened coordinates.
        const firstPoint = straightenedPolyline[0];
        const lastPoint = straightenedPolyline[straightenedPolyline.length - 1];
        if (firstPoint && lastPoint) {
          sourceX = firstPoint.x;
          sourceY = firstPoint.y;
          targetX = lastPoint.x;
          targetY = lastPoint.y;
          // Discard the endpoints while keeping the interior bends for the simplifier.
          rectifiedBendingPoints = straightenedPolyline.slice(1, -1);
          // Run one additional simplification pass so redundant elbows vanish.
          rectifiedBendingPoints = simplifyRectilinearBends(
            rectifiedBendingPoints,
            { x: sourceX, y: sourceY },
            { x: targetX, y: targetY }
          );
          if (traceRoutingDecision) {
            if (traceAdjustments) {
              traceAdjustments.straightened = true;
            }
            traceRoutingDecision(
              'straighten',
              `Straightened polyline along ${axis} axis`,
              buildPolyline(sourceX, sourceY, rectifiedBendingPoints, targetX, targetY),
              {
                axis,
                threshold: DEFAULT_STRAIGHTEN_THRESHOLD,
              }
            );
          }
        }
      }
    }
  }

  const parallelSpacingEnabled = data?.rectilinearParallelSpacingEnabled ?? DEFAULT_PARALLEL_SPACING_ENABLED;
  // Parallel spacing piggybacks on the pre-fetched edge list; skip when the context does not provide it.
  const shouldCollectParallelPolylines =
    parallelSpacingEnabled && !customEdge && edgesFromStore.length > 0 && !!getNodes;
  let detouredPolyline: XYPosition[] | undefined;
  let detourPolylines: Map<string, XYPosition[]> | undefined;

  // Reuse the shared edge cache when building detours so we do not poll the store repeatedly.
  if (!customEdge && edgesFromStore.length > 0) {
    const edgesForDetour = edgesFromStore;
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

      if (shouldCollectParallelPolylines) {
        const detourResult = buildDetouredPolyline(currentEdge, baselinePolyline, edgesForDetour, nodesAsNodes, {
          collectAll: true,
        });
        detouredPolyline = detourResult.current;
        detourPolylines = detourResult.polylines;
      } else {
        detouredPolyline = buildDetouredPolyline(currentEdge, baselinePolyline, edgesForDetour, nodesAsNodes);
      }

      if (detouredPolyline && !polylinesEqual(detouredPolyline, baselinePolyline)) {
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
        if (traceRoutingDecision) {
          if (traceAdjustments) {
            traceAdjustments.detoured = true;
          }
          traceRoutingDecision('detour', 'Inserted detour to avoid detected obstacles', detouredPolyline, {
            addedBends: Math.max(detouredPolyline.length - baselinePolyline.length, 0),
          });
        }
      }
    }
  }

  if (shouldCollectParallelPolylines) {
    const spacingContextSignature = createSpacingContextSignature(
      edgesFromStore,
      (nodeId) => getNode(nodeId) as Node<NodeData> | undefined
    );
    const hasContextSnapshot = parallelSpacingSnapshot?.contextSignature === spacingContextSignature;
    const baselinePolylines = hasContextSnapshot
      ? clonePolylineMap(parallelSpacingSnapshot!.baselinePolylines)
      : new Map<string, XYPosition[]>();

    detourPolylines?.forEach((polyline, edgeId) => {
      const normalised = normalisePolyline(polyline);
      if (!normalised) {
        return;
      }
      const shouldOverride = edgeId === id || !baselinePolylines.has(edgeId);
      if (shouldOverride) {
        baselinePolylines.set(edgeId, normalised);
      }
    });

    const referencePolyline = detouredPolyline ?? [
      { x: sourceX, y: sourceY },
      ...rectifiedBendingPoints.map((point) => ({ x: point.x, y: point.y })),
      { x: targetX, y: targetY },
    ];
    const referenceClone = referencePolyline.map((point) => ({ x: point.x, y: point.y }));
    baselinePolylines.set(id, referenceClone);

    const baselineFingerprint = createPolylinesFingerprint(baselinePolylines);
    const canReuseSpacing = hasContextSnapshot && parallelSpacingSnapshot?.baselineFingerprint === baselineFingerprint;

    let spacedPolylines: Map<string, XYPosition[]>;
    if (canReuseSpacing && parallelSpacingSnapshot) {
      spacedPolylines = clonePolylineMap(parallelSpacingSnapshot.spacedPolylines);
    } else {
      spacedPolylines = buildSpacedPolylines(baselinePolylines, DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS);
    }

    const hasOtherPolylines = Array.from(baselinePolylines.keys()).some((edgeId) => edgeId !== id);
    if (hasOtherPolylines) {
      const spacedPolyline = spacedPolylines.get(id);
      if (spacedPolyline && !polylinesEqual(spacedPolyline, referencePolyline)) {
        const firstPoint = spacedPolyline[0];
        const lastPoint = spacedPolyline[spacedPolyline.length - 1];
        if (firstPoint) {
          sourceX = firstPoint.x;
          sourceY = firstPoint.y;
        }
        if (lastPoint) {
          targetX = lastPoint.x;
          targetY = lastPoint.y;
        }
        rectifiedBendingPoints = spacedPolyline.slice(1, -1);
        if (traceRoutingDecision) {
          if (traceAdjustments) {
            traceAdjustments.parallelSpacing = true;
          }
          traceRoutingDecision('parallel-spacing', 'Shifted polyline to resolve overlapping segments', spacedPolyline, {
            spacing: DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS.spacing,
            tolerance: DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS.tolerance,
          });
        }
      }
    }

    const spacedFingerprint = createPolylinesFingerprint(spacedPolylines);
    parallelSpacingSnapshot = {
      contextSignature: spacingContextSignature,
      baselineFingerprint,
      spacedFingerprint,
      baselinePolylines: clonePolylineMap(baselinePolylines),
      spacedPolylines: clonePolylineMap(spacedPolylines),
    };
  }

  const simplifyEnabled = data?.rectilinearSimplifyEnabled ?? true;
  let finalBendingPoints = rectifiedBendingPoints;
  if (simplifyEnabled && !customEdge) {
    const postProcessSimplified = simplifyRectilinearBends(
      rectifiedBendingPoints,
      { x: sourceX, y: sourceY },
      { x: targetX, y: targetY }
    );
    const removedAfterPostProcess = rectifiedBendingPoints.length - postProcessSimplified.length;
    if (removedAfterPostProcess > 0) {
      if (traceAdjustments) {
        traceAdjustments.simplified = true;
      }
      if (traceRoutingDecision) {
        traceRoutingDecision(
          'simplify',
          `Removed ${removedAfterPostProcess} residual bend${
            removedAfterPostProcess === 1 ? '' : 's'
          } after post-processing`,
          buildPolyline(sourceX, sourceY, postProcessSimplified, targetX, targetY),
          {
            stage: 'post-processing',
            removedBends: removedAfterPostProcess,
          }
        );
      }
    }
    finalBendingPoints = postProcessSimplified;
  }

  if (traceRoutingDecision) {
    traceRoutingDecision(
      'final',
      'Final polyline emitted to the renderer',
      buildPolyline(sourceX, sourceY, finalBendingPoints, targetX, targetY),
      {
        customEdge,
        simplifyEnabled,
        parallelSpacingEnabled: shouldCollectParallelPolylines,
        adjustments: traceAdjustments ? { ...traceAdjustments } : undefined,
      }
    );
  }

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
