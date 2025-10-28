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
 *     Obeo - API evolution and refactor
 *******************************************************************************/
import { Edge, EdgeProps, InternalNode, Node, Position, useInternalNode } from '@xyflow/react';
import { memo, useContext, useMemo } from 'react';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelRectilinearEditableEdge } from './rectilinear-edge/MultiLabelRectilinearEditableEdge';

/**
 * SmoothStepEdgeWrapper (drop-in replacement)
 * -------------------------------------------
 * Purpose:
 *   Produce an editable **orthogonal (Manhattan)** edge based on the source/target
 *   handle geometry. If `data.bendingPoints` are provided, they are honored
 *   (controlled mode). Otherwise, *robust* auto-computed bending points are
 *   generated (uncontrolled mode) with clear invariants:
 *
 * Invariants:
 *   - All consecutive segments are axis-aligned (horizontal OR vertical).
 *   - The polyline starts at (sourceX, sourceY) and ends at (targetX, targetY).
 *   - No NaN coordinates. No zero-length consecutive segments.
 *
 * Performance:
 *   - The auto-computation is **memoized** on the 6 scalar inputs
 *     [sourceX, sourceY, sourcePosition, targetX, targetY, targetPosition].
 *
 * Extensibility:
 *   - Precise handle anchoring is delegated to `getHandleCoordinatesByPosition`,
 *     which itself can be customized by node types through `NodeTypeContext`.
 *
 * Notes:
 *   - Compared to previous versions that parsed a smooth SVG path, this version
 *     computes Manhattan elbows directly from endpoint positions. This avoids
 *     string parsing overhead and removes a runtime dependency while improving
 *     determinism and testability.
 */

// ---- Tunables (make magic numbers explicit) ---------------------------------
const DEFAULT_HANDLE_RADIUS = 2; // px, when no marker is present
const MARKER_HANDLE_RADIUS = 3; // px, when a marker is present

// Small numeric epsilon used to filter out degenerate points after adjustments.
const EPS = 1e-6;

// ---- Helper utilities --------------------------------------------------------

/** True if the value is a finite number. */
function isFiniteNumber(n: unknown): n is number {
  return typeof n === 'number' && Number.isFinite(n);
}

/** Remove consecutive duplicate points and zero-length segments. */
function dedupeAxisAligned(points: { x: number; y: number }[]): { x: number; y: number }[] {
  const out: { x: number; y: number }[] = [];
  for (const p of points) {
    if (!isFiniteNumber(p.x) || !isFiniteNumber(p.y)) continue;
    const last = out[out.length - 1];
    if (!last || Math.abs(last.x - p.x) > EPS || Math.abs(last.y - p.y) > EPS) {
      out.push({ x: p.x, y: p.y });
    }
  }
  // Also collapse any consecutive collinear points that don't change direction
  // (A -> B -> C where AB and BC are both horizontal or both vertical).
  if (out.length <= 2) return out;
  const collapsed: { x: number; y: number }[] = [out[0]!];
  for (let i = 1; i < out.length - 1; i++) {
    const a = collapsed[collapsed.length - 1]!;
    const b = out[i]!;
    const c = out[i + 1]!;
    const abH = Math.abs(a.y - b.y) <= EPS;
    const bcH = Math.abs(b.y - c.y) <= EPS;
    const abV = Math.abs(a.x - b.x) <= EPS;
    const bcV = Math.abs(b.x - c.x) <= EPS;
    // If the direction doesn't change, skip the middle point.
    if ((abH && bcH) || (abV && bcV)) continue;
    collapsed.push(b);
  }
  collapsed.push(out[out.length - 1]!);
  return collapsed;
}

/**
 * Compute orthogonal bending points between source and target.
 * Strategy:
 *  - Prefer at most 2 elbows (3–4 points total) depending on endpoint faces.
 *  - If faces are opposite (e.g., Right -> Left), route via mid X or mid Y.
 *  - If faces are orthogonal (e.g., Right -> Top), use an "L" shape.
 */
export function computeAutoBendingPoints(
  sourceX: number,
  sourceY: number,
  sourcePosition: Position,
  targetX: number,
  targetY: number,
  targetPosition: Position
): { x: number; y: number }[] {
  // Guard
  if (![sourceX, sourceY, targetX, targetY].every(isFiniteNumber) || sourcePosition == null || targetPosition == null) {
    // Fallback: simple L-shape
    return dedupeAxisAligned([
      { x: sourceX, y: sourceY },
      { x: targetX, y: sourceY },
      { x: targetX, y: targetY },
    ]);
  }

  const points: { x: number; y: number }[] = [{ x: sourceX, y: sourceY }];

  // Same X or same Y → direct straight segment
  if (Math.abs(sourceX - targetX) <= EPS || Math.abs(sourceY - targetY) <= EPS) {
    points.push({ x: targetX, y: targetY });
    return dedupeAxisAligned(points);
  }

  const horizontal = (p: Position) => p === Position.Left || p === Position.Right;
  const vertical = (p: Position) => p === Position.Top || p === Position.Bottom;

  // Orthogonal faces (one horizontal, one vertical): classic L
  if (
    (horizontal(sourcePosition) && vertical(targetPosition)) ||
    (vertical(sourcePosition) && horizontal(targetPosition))
  ) {
    // From the source, first move along the axis aligned with source face, then to target
    if (horizontal(sourcePosition)) {
      points.push({ x: targetX, y: sourceY });
    } else {
      points.push({ x: sourceX, y: targetY });
    }
    points.push({ x: targetX, y: targetY });
    return dedupeAxisAligned(points);
  }

  // Same orientation (both horizontal or both vertical):
  // Use a midline between source and target to avoid crossing nodes.
  if (horizontal(sourcePosition) && horizontal(targetPosition)) {
    const midX = (sourceX + targetX) / 2;
    points.push({ x: midX, y: sourceY });
    points.push({ x: midX, y: targetY });
    points.push({ x: targetX, y: targetY });
    return dedupeAxisAligned(points);
  }

  if (vertical(sourcePosition) && vertical(targetPosition)) {
    const midY = (sourceY + targetY) / 2;
    points.push({ x: sourceX, y: midY });
    points.push({ x: targetX, y: midY });
    points.push({ x: targetX, y: targetY });
    return dedupeAxisAligned(points);
  }

  // Ultimate fallback (should not happen): L
  points.push({ x: targetX, y: sourceY });
  points.push({ x: targetX, y: targetY });
  return dedupeAxisAligned(points);
}

/** Compute handle coordinates including small offsets for marker/handle radius. */
function getAdjustedHandleCoords(
  x: number,
  y: number,
  position: Position,
  hasMarker: boolean,
  isSource: boolean
): { x: number; y: number } {
  const r = hasMarker ? MARKER_HANDLE_RADIUS : DEFAULT_HANDLE_RADIUS;
  switch (position) {
    case Position.Right:
      return { x: x + (isSource ? +r : -r), y };
    case Position.Left:
      return { x: x + (isSource ? -r : +r), y };
    case Position.Bottom:
      return { x, y: y + (isSource ? +r : -r) };
    case Position.Top:
      return { x, y: y + (isSource ? -r : +r) };
    default:
      return { x, y };
  }
}

// ---- Component ---------------------------------------------------------------

const SmoothStepEdgeWrapper = memo((props: EdgeProps<Edge<MultiLabelEdgeData>>) => {
  const {
    source,
    target,
    sourcePosition = Position.Bottom,
    targetPosition = Position.Top,
    markerStart,
    markerEnd,
    sourceHandleId,
    targetHandleId,
    data,
  } = props;

  const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);

  const sourceNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(source);
  const targetNode: InternalNode<Node<NodeData>> | undefined = useInternalNode<Node<NodeData>>(target);

  if (!sourceNode || !targetNode) return null;

  // Resolve potential custom per-node anchoring strategy
  const sourceLayoutHandler = nodeLayoutHandlers.find((h) =>
    h.canHandle(sourceNode as unknown as Node<NodeData, DiagramNodeType>)
  );
  const targetLayoutHandler = nodeLayoutHandlers.find((h) =>
    h.canHandle(targetNode as unknown as Node<NodeData, DiagramNodeType>)
  );

  // Compute raw handle coordinates from node + handle position
  let { x: rawSourceX, y: rawSourceY } = getHandleCoordinatesByPosition(
    sourceNode,
    sourcePosition,
    sourceHandleId ?? '',
    sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );
  let { x: rawTargetX, y: rawTargetY } = getHandleCoordinatesByPosition(
    targetNode,
    targetPosition,
    targetHandleId ?? '',
    targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
  );

  // Adjust for handle/marker radii so only the marker/handle touches the node border
  const hasStartMarker = !(markerStart == null || (typeof markerStart === 'string' && markerStart.includes('None')));
  const hasEndMarker = !(markerEnd == null || (typeof markerEnd === 'string' && markerEnd.includes('None')));

  const { x: sourceX, y: sourceY } = getAdjustedHandleCoords(
    rawSourceX,
    rawSourceY,
    sourcePosition,
    hasStartMarker,
    true
  );
  const { x: targetX, y: targetY } = getAdjustedHandleCoords(
    rawTargetX,
    rawTargetY,
    targetPosition,
    hasEndMarker,
    false
  );

  // If consumer passed explicit bending points, we honor them (controlled mode).
  const customBendingPoints = data?.bendingPoints ?? [];

  const autoBendingPoints = useMemo(
    () => computeAutoBendingPoints(sourceX, sourceY, sourcePosition, targetX, targetY, targetPosition),
    // eslint-disable-next-line react-hooks/exhaustive-deps
    [sourceX, sourceY, sourcePosition, targetX, targetY, targetPosition]
  );

  // Choose which set to use, and always ensure start and end are present
  const bendingPoints =
    customBendingPoints.length > 0
      ? dedupeAxisAligned([{ x: sourceX, y: sourceY }, ...customBendingPoints, { x: targetX, y: targetY }])
      : autoBendingPoints;

  return (
    <MultiLabelRectilinearEditableEdge
      {...props}
      sourceX={sourceX}
      sourceY={sourceY}
      targetX={targetX}
      targetY={targetY}
      bendingPoints={bendingPoints}
      customEdge={customBendingPoints.length > 0}
      sourceNode={sourceNode}
      targetNode={targetNode}
    />
  );
});

export default SmoothStepEdgeWrapper;
export { SmoothStepEdgeWrapper };
