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

import { Edge, Node, XYPosition } from '@xyflow/react';
import parse, { Command } from 'svg-path-parser';
import { EdgeCrossingMap, EdgeCrossingWindow, ParsedSegment, NodeBounds, ParsedEdge } from './edgeCrossing.types';
import { MultiLabelEdgeData } from '../MultiLabelEdge.types';
import { NodeData } from '../../DiagramRenderer.types';

/**
 * Prototype crossing detector that only inspects orthogonal line segments (L commands) built from
 * the stored SVG path. Curved or diagonal edges are ignored so we do not attempt to clip Bézier
 * curves yet. The algorithm is deliberately deterministic: when two edges intersect we always fade
 * the lexicographically "higher" edge so we get stable visuals while the user drags nodes around.
 */

const MIN_SEGMENT_LENGTH = 1;
const MIN_WINDOW_LENGTH = 2;
const DEFAULT_WINDOW_LENGTH = 18;
const NODE_PROXIMITY_PADDING = 12;

/**
 * Parse an SVG path definition and extract every command that exposes absolute coordinates.
 *
 * @param pathDefinition - Raw `d` attribute string rendered by React Flow.
 * @returns An ordered list of XY coordinates describing the path's polyline vertices.
 *
 * The detector only needs straight segments, so we collapse all commands to their
 * coordinate payloads. Quadratic commands already include the end point that React Flow
 * uses to approximate them, which keeps the crossing math simple.
 */
const toPoints = (pathDefinition: string): XYPosition[] => {
  let commands: Command[] = [];
  try {
    commands = parse(pathDefinition);
  } catch (error) {
    return [];
  }
  return commands
    .filter((command) => typeof command.x === 'number' && typeof command.y === 'number')
    .map((command) => ({ x: command.x as number, y: command.y as number }));
};

/**
 * Convert the parsed points into concrete line segments along with cumulative lengths.
 *
 * @param pathDefinition - Raw `d` attribute string rendered by React Flow.
 * @returns A pair containing all parsed segments and the path total length.
 *
 * The cumulative lengths allow us to convert intersection ratios back into normalized
 * [0..1] windows later on, because every segment knows where it sits along the full path.
 */
const parseSegments = (pathDefinition: string): { segments: ParsedSegment[]; totalLength: number } => {
  if (!pathDefinition) {
    return { segments: [], totalLength: 0 };
  }
  const points = toPoints(pathDefinition);
  if (points.length < 2) {
    return { segments: [], totalLength: 0 };
  }

  const segments: ParsedSegment[] = [];
  let cursor = 0;
  for (let i = 1; i < points.length; i++) {
    const start = points[i - 1];
    const end = points[i];
    if (!start || !end) {
      continue;
    }
    const length = Math.hypot(end.x - start.x, end.y - start.y);
    if (length < MIN_SEGMENT_LENGTH) {
      continue;
    }
    segments.push({
      start,
      end,
      startLength: cursor,
      endLength: cursor + length,
      length,
    });
    cursor += length;
  }
  return { segments, totalLength: cursor };
};

const INTERSECTION_EPSILON = 0.001;
const COLINEAR_TOLERANCE = 0.001;
const PATH_ENDPOINT_PADDING = 18;

type SegmentIntersection = {
  point: XYPosition;
  ratioA: number;
  ratioB: number;
};

/**
 * Solve the intersection between two finite line segments.
 *
 * @param segmentA - First segment with start/end coordinates and cached lengths.
 * @param segmentB - Second segment with start/end coordinates and cached lengths.
 * @returns The intersection point and normalized ratios within each segment, or null if they do not cross.
 *
 * The computation uses the standard 2×2 linear system. Ratios outside (0,1) indicate
 * that the lines would meet only when extended beyond their actual segment limits.
 */
const getSegmentIntersection = (segmentA: ParsedSegment, segmentB: ParsedSegment): SegmentIntersection | null => {
  const ax = segmentA.end.x - segmentA.start.x;
  const ay = segmentA.end.y - segmentA.start.y;
  const bx = segmentB.end.x - segmentB.start.x;
  const by = segmentB.end.y - segmentB.start.y;

  const denominator = ax * by - ay * bx;
  if (Math.abs(denominator) < COLINEAR_TOLERANCE) {
    return null;
  }

  const dx = segmentB.start.x - segmentA.start.x;
  const dy = segmentB.start.y - segmentA.start.y;

  const ratioA = (dx * by - dy * bx) / denominator;
  const ratioB = (dx * ay - dy * ax) / denominator;

  if (
    ratioA <= INTERSECTION_EPSILON ||
    ratioA >= 1 - INTERSECTION_EPSILON ||
    ratioB <= INTERSECTION_EPSILON ||
    ratioB >= 1 - INTERSECTION_EPSILON
  ) {
    return null;
  }

  const point: XYPosition = {
    x: segmentA.start.x + ratioA * ax,
    y: segmentA.start.y + ratioA * ay,
  };

  return { point, ratioA, ratioB };
};

type NodeWithPosition = Node<NodeData> & {
  positionAbsolute?: XYPosition;
};

/**
 * Resolve the absolute rectangle occupied by a node.
 *
 * @param node - Optional node coming either from the edge instance or the diagram store.
 * @returns The node's bounding box with positive width/height, or null if unavailable.
 *
 * Crossing detection needs the bounds to avoid cutting fades right on top of handles.
 * The helper inspects both explicit width/height and default values coming from the
 * node description so it also works for collapsed nodes.
 */
const getNodeBounds = (node?: Node<NodeData>): NodeBounds | null => {
  if (!node) {
    return null;
  }
  const width = node.width ?? node.data?.defaultWidth ?? 0;
  const height = node.height ?? node.data?.defaultHeight ?? 0;
  const withPosition = node as NodeWithPosition;
  const baseX = withPosition.positionAbsolute?.x ?? node.position?.x ?? 0;
  const baseY = withPosition.positionAbsolute?.y ?? node.position?.y ?? 0;
  return {
    x: baseX,
    y: baseY,
    width: Math.max(width, 0),
    height: Math.max(height, 0),
  };
};

/**
 * Determine whether a point sits inside (or within `padding` pixels of) a rectangle.
 *
 * @param bounds - Rectangle describing the node.
 * @param point - Intersection point along the path.
 * @param padding - Optional inflation margin around the rectangle.
 * @returns True when the point is inside the padded bounds.
 */
const isPointInsideRect = (bounds: NodeBounds, point: XYPosition, padding = 0): boolean => {
  return (
    point.x >= bounds.x - padding &&
    point.x <= bounds.x + bounds.width + padding &&
    point.y >= bounds.y - padding &&
    point.y <= bounds.y + bounds.height + padding
  );
};

/**
 * Check whether a point on the path sits too close to either endpoint.
 *
 * @param edge - Parsed edge that carries the total length.
 * @param position - Absolute position along the path (pixels) where the crossing happened.
 * @returns True when the crossing should be ignored because it is too close to the handles.
 */
const isNearEdgePathEndpoint = (edge: ParsedEdge, position: number): boolean => {
  return position <= PATH_ENDPOINT_PADDING || edge.totalLength - position <= PATH_ENDPOINT_PADDING;
};

/**
 * Detect whether the intersection falls within the padded bounds of the edge endpoints.
 *
 * @param edge - Parsed edge with resolved source/target nodes.
 * @param point - Intersection coordinates.
 * @param nodeLookup - Cache for resolving nodes when they were not already attached to the edge.
 * @returns True when the point should be ignored.
 */
const isPointNearEdgeEndpoints = (
  edge: ParsedEdge,
  point: XYPosition,
  nodeLookup: Map<string, Node<NodeData>>
): boolean => {
  const resolvedSource = nodeLookup.get(edge.edge.source);
  const resolvedTarget = nodeLookup.get(edge.edge.target);
  const nodes: (Node<NodeData> | undefined)[] = [resolvedSource, resolvedTarget];
  return nodes.some((node) => {
    const bounds = getNodeBounds(node);
    if (!bounds) {
      return false;
    }
    return isPointInsideRect(bounds, point, NODE_PROXIMITY_PADDING);
  });
};

/**
 * Decide which edge should appear “under” at a crossing.
 *
 * @param first - First edge participating in the intersection.
 * @param second - Second edge participating in the intersection.
 * @returns The edge that must receive the fade window.
 *
 * The function first respects `zIndex` when provided, then falls back to
 * deterministic alphabetical ordering so repeated drags keep the same edge faded.
 */
const selectUnderEdge = (first: ParsedEdge, second: ParsedEdge): ParsedEdge => {
  if (typeof first.edge.zIndex === 'number' && typeof second.edge.zIndex === 'number') {
    if (first.edge.zIndex !== second.edge.zIndex) {
      return first.edge.zIndex < second.edge.zIndex ? second : first;
    }
  }
  return first.edge.id.localeCompare(second.edge.id) < 0 ? second : first;
};

/**
 * Persist a crossing window on the under-edge.
 *
 * @param map - Mutable map storing edge-id → windows.
 * @param edge - Under-edge whose dash array will eventually be updated.
 * @param positionOnPath - Absolute position (pixels) along that edge where the crossing occurs.
 * @param point - Intersection coordinates, stored for debugging.
 * @param fadeWindowLength - Desired fade length in pixels.
 * @param seenKeys - Set of already recorded crossing coordinates to avoid duplicates.
 */
const recordWindow = (
  map: EdgeCrossingMap,
  edge: ParsedEdge,
  positionOnPath: number,
  point: XYPosition,
  fadeWindowLength: number,
  seenKeys: Set<string>
): void => {
  const key = `${edge.edge.id}:${point.x.toFixed(2)}:${point.y.toFixed(2)}`;
  if (seenKeys.has(key)) {
    return;
  }
  seenKeys.add(key);

  // Center the fade window on the intersection and clamp it inside the path length.
  const halfWindow = fadeWindowLength / 2;
  const windowStart = Math.max(0, positionOnPath - halfWindow);
  const windowEnd = Math.min(edge.totalLength, positionOnPath + halfWindow);
  if (edge.totalLength <= 0 || windowEnd - windowStart < MIN_WINDOW_LENGTH) {
    return;
  }

  const crossingWindow: EdgeCrossingWindow = {
    startRatio: windowStart / edge.totalLength,
    endRatio: windowEnd / edge.totalLength,
    intersection: point,
  };

  const windows = map.get(edge.edge.id) ?? [];
  windows.push(crossingWindow);
  map.set(edge.edge.id, windows);
};

/**
 * Produce crossing windows for a set of edges and nodes.
 *
 * @param edges - React Flow edges containing their latest rendered path string.
 * @param nodeLookup - Cache of nodes keyed by id, used to avoid fading inside node bounds.
 * @param fadeWindowLength - Length (pixels) of the fade window centered on each crossing.
 * @returns A map keyed by edge id with all windows that should be rendered.
 *
 * The function parses every SVG path, tests all possible segment pairs, filters out
 * near-endpoint and near-node intersections, and records the winning edge for each crossing.
 * The caller later normalizes the windows and injects them back into the edge data.
 */
export const detectEdgeCrossings = (
  edges: Edge<MultiLabelEdgeData>[],
  nodeLookup: Map<string, Node<NodeData>>,
  fadeWindowLength = DEFAULT_WINDOW_LENGTH
): EdgeCrossingMap => {
  if (!edges.length) {
    return new Map();
  }

  const parsedEdges: ParsedEdge[] = edges
    .filter((edge) => !edge.hidden)
    .map((edge) => {
      const { segments, totalLength } = parseSegments(edge.data?.edgePath ?? '');
      return { edge, segments, totalLength };
    })
    .filter((entry) => entry.segments.length > 0 && entry.totalLength > 0);

  const result: EdgeCrossingMap = new Map();
  const seenKeys = new Set<string>();

  // Walk every pair of parsed edges; each guard keeps the nested loops readable even when edges are undefined.
  for (let i = 0; i < parsedEdges.length; i++) {
    const first = parsedEdges[i];
    if (!first) {
      continue;
    }
    for (let j = i + 1; j < parsedEdges.length; j++) {
      const second = parsedEdges[j];
      if (!second) {
        continue;
      }
      // Test every segment combination. These are short arrays (rectilinear routes), so clarity trumped micro-optimizations.
      first.segments.forEach((segmentA) => {
        second.segments.forEach((segmentB) => {
          const intersection = getSegmentIntersection(segmentA, segmentB);
          if (!intersection) {
            return;
          }
          const positionOnFirst = segmentA.startLength + intersection.ratioA * segmentA.length;
          const positionOnSecond = segmentB.startLength + intersection.ratioB * segmentB.length;
          if (isNearEdgePathEndpoint(first, positionOnFirst) || isNearEdgePathEndpoint(second, positionOnSecond)) {
            return;
          }
          if (
            isPointNearEdgeEndpoints(first, intersection.point, nodeLookup) ||
            isPointNearEdgeEndpoints(second, intersection.point, nodeLookup)
          ) {
            return;
          }
          const underEdge = selectUnderEdge(first, second);
          const positionOnUnderEdge = underEdge.edge.id === first.edge.id ? positionOnFirst : positionOnSecond;
          recordWindow(result, underEdge, positionOnUnderEdge, intersection.point, fadeWindowLength, seenKeys);
        });
      });
    }
  }

  return result;
};
