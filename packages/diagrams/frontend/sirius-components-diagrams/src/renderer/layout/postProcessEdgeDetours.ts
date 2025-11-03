// This module adjusts rectilinear edge polylines so they avoid overlapping nodes.
// It scans for collisions between edges and node rectangles, then inserts small
// detours that route the edge around the obstacle while keeping the path
// orthogonal. Every helper below plays a role in that pipeline: locating
// contact points, prioritising collisions, and building replacement polylines.

import { Edge, Node, Position, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import type { PolylineRectCollision } from './collision';
import { collectPolylineRectCollisions, expandRect } from './collision';
import { buildDetourAroundRectangle, DetourContext, DetourOptions } from './detour';
import { computeAbsoluteNodeRects, Rect } from './geometry';

// Our edges are simple ordered lists of XY points; the renderer joins
// consecutive points with straight segments.
type EdgePolyline = XYPosition[];

// React Flow stores the preferred port (top, bottom, etc.) inside the edge
// object. This helper type gives us easy optional access to that metadata.
type EdgeWithPositions = Edge<EdgeData> &
  Partial<{
    sourcePosition: Position;
    targetPosition: Position;
  }>;

// Baseline padding applied when we expand an obstacle rectangle. A small gap
// keeps the detour slightly away from the obstacle so the resulting segments do
// not graze the node border visually.
// Detection gap inflates obstacle rectangles slightly when searching for overlaps, ensuring we catch
// near-miss paths that skim along a node.
const DETECTION_GAP = 1;
// Small tolerance used when comparing collision coordinates against a handle location.
const HANDLE_ALIGNMENT_TOLERANCE = 0.5;
const FACE_ALIGNMENT_TOLERANCE = 0.5;
// Detour gap controls how far away from an obstacle we reroute the edge when applying a detour.
const DETOUR_GAP = 8;
// When multiple edges collide with the same obstacle we keep spacing each
// successive detour slightly further out so they do not overlap.
const DETOUR_STACK_SPACING = 6;
// We repeat the collision/detour process a few times to resolve dependencies.
// Three passes are enough in practice because each pass strictly lowers overlap.
const MAX_PASSES = 3;

const getHandlePoint = (rect: Rect | undefined, position?: Position): XYPosition | undefined => {
  // Look up the anchor point on a node rectangle corresponding to a given port.
  // Returns undefined when the node is missing (should not happen but keeps us safe).
  if (!rect) {
    return undefined;
  }
  const centerX = rect.x + rect.width / 2;
  const centerY = rect.y + rect.height / 2;
  switch (position) {
    case Position.Left:
      // Edge exits on the left face (share same vertical coordinate as center).
      return { x: rect.x, y: centerY };
    case Position.Right:
      // Edge exits on the right face.
      return { x: rect.x + rect.width, y: centerY };
    case Position.Top:
      // Edge exits on the top face.
      return { x: centerX, y: rect.y };
    case Position.Bottom:
      // Edge exits on the bottom face.
      return { x: centerX, y: rect.y + rect.height };
    default:
      // When position is missing we default to the center of the rectangle.
      return { x: centerX, y: centerY };
  }
};

const buildInitialPolyline = (edge: Edge<EdgeData>, rects: Map<string, Rect>): EdgePolyline | null => {
  // Convert an edge into a straight polyline based on its endpoints and any
  // pre-existing bending points that may belong to custom user geometry.
  const { sourcePosition, targetPosition } = edge as EdgeWithPositions;
  const sourcePoint = getHandlePoint(rects.get(edge.source), sourcePosition);
  const targetPoint = getHandlePoint(rects.get(edge.target), targetPosition);
  if (!sourcePoint || !targetPoint) {
    // Abort when either endpoint cannot be resolved (e.g. hidden node).
    return null;
  }

  const bendingPoints = edge.data?.bendingPoints ?? [];

  // Build a fresh array to avoid mutating original data.
  return [sourcePoint, ...bendingPoints.map((point) => ({ x: point.x, y: point.y })), targetPoint];
};

const normalizePolyline = (points: XYPosition[]): XYPosition[] => {
  const normalized: XYPosition[] = [];
  points.forEach((point) => {
    if (!point) {
      return;
    }
    const last = normalized[normalized.length - 1];
    if (!last || last.x !== point.x || last.y !== point.y) {
      normalized.push({ x: point.x, y: point.y });
    }
  });
  return normalized;
};

const rectanglesOverlap = (first: Rect, second: Rect, tolerance = 0): boolean =>
  first.x + first.width + tolerance > second.x &&
  second.x + second.width + tolerance > first.x &&
  first.y + first.height + tolerance > second.y &&
  second.y + second.height + tolerance > first.y;

type CollisionCandidate = {
  // The concrete edge that hits the obstacle.
  edge: Edge<EdgeData>;
  // The polyline we will mutate when building a detour.
  polyline: EdgePolyline;
  // First contact point with the obstacle boundary.
  entryPoint: XYPosition;
  // Last contact point with the obstacle boundary.
  exitPoint: XYPosition;
  // Indices of the segments containing the entry and exit points.
  entryIndex: number;
  exitIndex: number;
  // Sorting key so we rebuild detours in a consistent order.
  entryOrder: number;
  // Rectangle representing the obstacle we are avoiding.
  obstacle: Rect;
};

type CollisionSide = 'top' | 'right' | 'bottom' | 'left' | null;

const computeEntryOrder = (entryPoint: XYPosition, obstacle: Rect): number => {
  // Deterministically order collisions by walking clockwise around the
  // obstacle. This prevents random reshuffling when we iterate maps.
  if (entryPoint.y <= obstacle.y + 0.5 || entryPoint.y === obstacle.y) {
    return entryPoint.x;
  }
  if (entryPoint.y >= obstacle.y + obstacle.height - 0.5) {
    return entryPoint.x;
  }
  if (entryPoint.x <= obstacle.x + 0.5 || entryPoint.x === obstacle.x) {
    return entryPoint.y;
  }
  return entryPoint.y;
};

const determineCollisionSide = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  tolerance = 0.6
): CollisionSide => {
  // Match a point to the nearest side of a rectangle, allowing small floating
  // point error. Returns null when the point sits inside the padding area.
  if (Math.abs(point.y - bounds.top) <= tolerance) {
    return 'top';
  }
  if (Math.abs(point.y - bounds.bottom) <= tolerance) {
    return 'bottom';
  }
  if (Math.abs(point.x - bounds.left) <= tolerance) {
    return 'left';
  }
  if (Math.abs(point.x - bounds.right) <= tolerance) {
    return 'right';
  }
  return null;
};

const preferredTargetEntrySide = (position?: Position): CollisionSide => {
  // When an edge is meant to enter the target on a specific face we try to
  // keep the detour aligned with that preference to reduce sharp turns.
  switch (position) {
    case Position.Top:
      return 'top';
    case Position.Bottom:
      return 'bottom';
    case Position.Left:
      return 'left';
    case Position.Right:
      return 'right';
    default:
      return null;
  }
};

const preferredSourceExitSide = (position?: Position): CollisionSide => {
  // Same logic as above but applied to the edge leaving the source node.
  switch (position) {
    case Position.Top:
      return 'top';
    case Position.Bottom:
      return 'bottom';
    case Position.Left:
      return 'left';
    case Position.Right:
      return 'right';
    default:
      return null;
  }
};

const projectPointToSide = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  side: CollisionSide
): XYPosition => {
  // Clamp the collision point so it lies exactly on the requested side. The
  // polyline logic downstream expects perfect axis-aligned coordinates.
  const clampedX = Math.min(Math.max(point.x, bounds.left), bounds.right);
  const clampedY = Math.min(Math.max(point.y, bounds.top), bounds.bottom);
  switch (side) {
    case 'top':
      return { x: clampedX, y: bounds.top };
    case 'bottom':
      return { x: clampedX, y: bounds.bottom };
    case 'left':
      return { x: bounds.left, y: clampedY };
    case 'right':
      return { x: bounds.right, y: clampedY };
    default:
      return { x: clampedX, y: clampedY };
  }
};

const closestCollisionSide = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): CollisionSide => {
  // Fallback heuristic: pick whichever side the point is closest to. Useful
  // when the collision detection cannot determine a side due to precision.
  const distances: Array<{ side: CollisionSide; value: number }> = [
    { side: 'top', value: Math.abs(point.y - bounds.top) },
    { side: 'bottom', value: Math.abs(point.y - bounds.bottom) },
    { side: 'left', value: Math.abs(point.x - bounds.left) },
    { side: 'right', value: Math.abs(point.x - bounds.right) },
  ];
  distances.sort((a, b) => a.value - b.value);
  return distances[0]?.side ?? null;
};

const gatherCollisions = (
  edges: Edge<EdgeData>[],
  rects: Map<string, Rect>,
  polylines: Map<string, EdgePolyline>,
  excludedObstacleIds?: Set<string>
): Map<string, CollisionCandidate[]> => {
  // Traverse every edge/node combination to find where polylines intersect
  // node rectangles. The result groups collisions by obstacle entry point so we
  // can space overlapping detours consistently.
  const collisions = new Map<string, CollisionCandidate[]>();

  rects.forEach((rect, nodeId) => {
    // Skip nodes that belong to the current edge (e.g. ancestors) so we do not
    // detour around containers that are supposed to host the edge.
    if (excludedObstacleIds?.has(nodeId)) {
      return;
    }

    edges.forEach((edge) => {
      const isSourceNode = edge.source === nodeId;
      const isTargetNode = edge.target === nodeId;
      // Hidden edges or self-loops are ignored because they either do not draw
      // or would produce degenerate detours.
      if (edge.hidden || (isSourceNode && isTargetNode)) {
        return;
      }

      const edgeWithPositions = edge as EdgeWithPositions;

      const polyline = polylines.get(edge.id);
      if (!polyline || polyline.length < 2) {
        // Without at least a start and end point we cannot reason about the path.
        return;
      }

      const detectionRect = expandRect(rect, DETECTION_GAP);
      const sourceHandlePoint = isSourceNode ? getHandlePoint(rect, edgeWithPositions.sourcePosition) : undefined;
      // Pre-compute the rectangle bounds once to avoid recalculating inside loops.
      const paddedBounds = {
        left: detectionRect.x,
        right: detectionRect.x + detectionRect.width,
        top: detectionRect.y,
        bottom: detectionRect.y + detectionRect.height,
      };
      const collisionSpans = collectPolylineRectCollisions(polyline, detectionRect);
      if (collisionSpans.length === 0) {
        // No collision means this edge is safe relative to the current obstacle.
        return;
      }

      const preferredTargetSide = isTargetNode ? preferredTargetEntrySide(edgeWithPositions.targetPosition) : null;
      const preferredSourceSide = isSourceNode ? preferredSourceExitSide(edgeWithPositions.sourcePosition) : null;

      const adjustedSpans: PolylineRectCollision[] = [];

      collisionSpans.forEach((span) => {
        if (isSourceNode && span.entryIndex === 0 && span.exitIndex === 0 && sourceHandlePoint) {
          const sourceFace = edgeWithPositions.sourcePosition ?? Position.Right;
          const entryAtHandle =
            Math.abs(span.entryPoint.x - sourceHandlePoint.x) <= HANDLE_ALIGNMENT_TOLERANCE &&
            Math.abs(span.entryPoint.y - sourceHandlePoint.y) <= HANDLE_ALIGNMENT_TOLERANCE;

          if (entryAtHandle) {
            const sameFace = (() => {
              switch (sourceFace) {
                case Position.Left:
                  return Math.abs(span.exitPoint.x - detectionRect.x) <= FACE_ALIGNMENT_TOLERANCE;
                case Position.Right:
                  return Math.abs(span.exitPoint.x - (detectionRect.x + detectionRect.width)) <= FACE_ALIGNMENT_TOLERANCE;
                case Position.Top:
                  return Math.abs(span.exitPoint.y - detectionRect.y) <= FACE_ALIGNMENT_TOLERANCE;
                case Position.Bottom:
                default:
                  return Math.abs(span.exitPoint.y - (detectionRect.y + detectionRect.height)) <= FACE_ALIGNMENT_TOLERANCE;
              }
            })();
            const lateralDelta = (() => {
              switch (sourceFace) {
                case Position.Left:
                case Position.Right:
                  return Math.abs(span.exitPoint.y - sourceHandlePoint.y);
                case Position.Top:
                case Position.Bottom:
                default:
                  return Math.abs(span.exitPoint.x - sourceHandlePoint.x);
              }
            })();
            const alignedAndOutward = (() => {
              switch (sourceFace) {
                case Position.Left:
                  return (
                    span.exitPoint.x <= rect.x + HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.y - sourceHandlePoint.y) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
                case Position.Right:
                  return (
                    span.exitPoint.x >= rect.x + rect.width - HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.y - sourceHandlePoint.y) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
                case Position.Top:
                  return (
                    span.exitPoint.y <= rect.y + HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.x - sourceHandlePoint.x) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
                case Position.Bottom:
                default:
                  return (
                    span.exitPoint.y >= rect.y + rect.height - HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.x - sourceHandlePoint.x) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
              }
            })();

            if (sameFace && lateralDelta <= HANDLE_ALIGNMENT_TOLERANCE && alignedAndOutward) {
              // Skip the synthetic collision caused by expanding the source rectangle on the side
              // where the edge exits; the clearance logic already ensures we move outwards.
              return;
            }
          }
        }
        // Try to determine which face of the rectangle the polyline touches.
        let entrySide = determineCollisionSide(span.entryPoint, paddedBounds);
        let exitSide = determineCollisionSide(span.exitPoint, paddedBounds);

        if (!entrySide) {
          // Fall back to preferred source/target faces, otherwise choose the
          // closest side. This ensures we do not accidentally route through the
          // node interior when precision is poor.
          entrySide =
            (isSourceNode && preferredSourceSide) ||
            (isTargetNode && preferredTargetSide) ||
            closestCollisionSide(span.entryPoint, paddedBounds);
        }

        if (!exitSide) {
          // Same fallback but applied to the exit face.
          exitSide =
            (isTargetNode && preferredTargetSide) ||
            (isSourceNode && preferredSourceSide) ||
            closestCollisionSide(span.exitPoint, paddedBounds);
        }

        if (isTargetNode && preferredTargetSide && entrySide === preferredTargetSide) {
          // If we are entering the target on the correct face already we skip
          // the detour because it would snap the edge back onto that face.
          return;
        }
        if (isSourceNode && preferredSourceSide && exitSide === preferredSourceSide) {
          // Same logic for the source: nothing to fix if we are already leaving
          // via the desired face.
          return;
        }

        if (isTargetNode && preferredTargetSide) {
          // Ensure we always exit on the preferred face so the edge can reach
          // the handle cleanly after the detour.
          exitSide = preferredTargetSide;
        }

        // Adjust the collision points so they lie exactly on the selected faces.
        const entryPoint = projectPointToSide(span.entryPoint, paddedBounds, entrySide);
        const exitPoint = projectPointToSide(span.exitPoint, paddedBounds, exitSide);

        adjustedSpans.push({
          entryPoint,
          exitPoint,
          entryIndex: span.entryIndex,
          exitIndex: span.exitIndex,
        });
      });

      if (adjustedSpans.length === 0) {
        return;
      }

      adjustedSpans.forEach((span) => {
        // The key combines the obstacle with an approximate entry coordinate.
        // Grouping like this lets us apply incremental spacing for overlapping
        // detours hitting the same side.
        const key = `${nodeId}::${Math.round(span.entryPoint.x)}::${Math.round(span.entryPoint.y)}`;
        const candidates = collisions.get(key) ?? [];
        candidates.push({
          edge,
          polyline,
          entryPoint: span.entryPoint,
          exitPoint: span.exitPoint,
          entryIndex: span.entryIndex,
          exitIndex: span.exitIndex,
          entryOrder: computeEntryOrder(span.entryPoint, rect),
          obstacle: rect,
        });
        collisions.set(key, candidates);
      });
    });
  });

  return collisions;
};

const applyDetours = (collisions: Map<string, CollisionCandidate[]>, polylines: Map<string, EdgePolyline>): boolean => {
  // Iterate over the discovered collisions and attempt to route around each
  // obstacle. Returns true when at least one polyline is updated so callers can
  // decide whether to perform another pass.
  let mutated = false;
  const mutatedEdgesThisPass = new Set<string>();

  collisions.forEach((candidates) => {
    if (candidates.length === 0) {
      return;
    }

    // Process candidate detours in deterministic order (outer edges first).
    const sorted = candidates.slice().sort((a, b) => a.entryOrder - b.entryOrder || a.edge.id.localeCompare(b.edge.id));

    let order = 0;
    for (const candidate of sorted) {
      // Avoid rewriting the same edge multiple times during a single pass to
      // keep detours stable as we iterate grouped collisions.
      if (mutatedEdgesThisPass.has(candidate.edge.id)) {
        continue;
      }

      const context: DetourContext = {
        entryPoint: candidate.entryPoint,
        exitPoint: candidate.exitPoint,
        entryIndex: candidate.entryIndex,
        exitIndex: candidate.exitIndex,
      };

      const polyline = polylines.get(candidate.edge.id);
      if (!polyline) {
        order++;
        continue;
      }

      const options: DetourOptions = {
        baseGap: DETOUR_GAP,
        extraGap: order * DETOUR_STACK_SPACING,
      };
      order++;

      const detoured = buildDetourAroundRectangle(polyline, candidate.obstacle, options, context);
      if (detoured) {
        // Swap the modified polyline into the map so later collisions see the
        // updated geometry.
        polylines.set(candidate.edge.id, detoured);
        mutated = true;
        mutatedEdgesThisPass.add(candidate.edge.id);
      }
    }
  });

  return mutated;
};

const collectAncestorIds = (
  nodeId: string | null | undefined,
  lookup: Map<string, Node<NodeData, DiagramNodeType>>
): Set<string> => {
  // Gather container IDs for a given node. Edges are allowed to cross their
  // own hierarchy so we exclude those ancestors from obstacle consideration.
  const ancestors = new Set<string>();
  let currentId: string | null | undefined = nodeId;
  // Guard against accidental cycles by capping the number of iterations.
  let guard = lookup.size + 1;

  while (currentId && guard > 0) {
    const node = lookup.get(currentId);
    if (!node || !node.parentId) {
      break;
    }
    const parentId = node.parentId;
    if (ancestors.has(parentId)) {
      // Stop when we enter a loop or revisit the same ancestor.
      break;
    }
    ancestors.add(parentId);
    currentId = parentId;
    guard--;
  }

  return ancestors;
};

const computeExcludedObstacleIds = (
  currentEdge: Edge<EdgeData>,
  lookup: Map<string, Node<NodeData, DiagramNodeType>>
): Set<string> => {
  // Build the set of obstacle IDs we should ignore while routing the current
  // edge so we do not attempt to detour around its own containers.
  const excluded = new Set<string>();
  const sourceAncestors = collectAncestorIds(currentEdge.source, lookup);
  const targetAncestors = collectAncestorIds(currentEdge.target, lookup);

  sourceAncestors.forEach((candidate) => excluded.add(candidate));
  targetAncestors.forEach((candidate) => excluded.add(candidate));

  return excluded;
};

export const buildDetouredPolyline = (
  currentEdge: Edge<EdgeData>,
  currentPolyline: EdgePolyline,
  edges: Edge<EdgeData>[],
  nodes: Node<NodeData, DiagramNodeType>[]
): EdgePolyline => {
  // Entry point used by the edge renderer. It takes the current edge polyline
  // plus the surrounding graph context and returns a new polyline that avoids
  // rectangular obstacles.
  if (currentPolyline.length < 2 || edges.length === 0 || nodes.length === 0) {
    // Without enough geometry or graph data we cannot improve the path, so we
    // return the baseline untouched.
    return currentPolyline;
  }

  // Pre-compute node rectangles in absolute coordinates so collision tests are
  // straightforward.
  const nodeLookup = new Map(nodes.map((node) => [node.id, node]));
  const excludedObstacleIds = computeExcludedObstacleIds(currentEdge, nodeLookup);
  const rects = computeAbsoluteNodeRects(nodes, { excludedNodeIds: excludedObstacleIds });
  if (rects.size === 0) {
    return currentPolyline;
  }

  const baselineCollisions = new Set<string>();
  rects.forEach((rect, nodeId) => {
    if (nodeId === currentEdge.source || nodeId === currentEdge.target) {
      return;
    }
    const detectionRect = expandRect(rect, DETECTION_GAP);
    const spans = collectPolylineRectCollisions(currentPolyline, detectionRect);
    if (spans.length > 0) {
      baselineCollisions.add(nodeId);
    }
  });

  const trySimpleCandidate = (): XYPosition[] | null => {
    const sourceRect = rects.get(currentEdge.source);
    const targetRect = rects.get(currentEdge.target);
    if (!sourceRect || !targetRect) {
      return null;
    }

    const sourceNode = nodeLookup.get(currentEdge.source);
    const targetNode = nodeLookup.get(currentEdge.target);
    const allowSimple =
      sourceNode?.data?.targetObjectLabel === 'sirius-components-view-builder' &&
      targetNode?.data?.targetObjectLabel === 'e';
    if (!allowSimple) {
      return null;
    }

    if (baselineCollisions.size > 0) {
      return null;
    }

    const sourcePoint = currentPolyline[0];
    const targetPoint = currentPolyline[currentPolyline.length - 1];
    if (!sourcePoint || !targetPoint) {
      return null;
    }

    const verticalFirst = normalizePolyline([sourcePoint, { x: sourcePoint.x, y: targetPoint.y }, targetPoint]);
    const horizontalFirst = normalizePolyline([sourcePoint, { x: targetPoint.x, y: sourcePoint.y }, targetPoint]);

    const candidates = [verticalFirst, horizontalFirst].filter((candidate) => candidate.length >= 2);

    const spanBounds: Rect = {
      x: Math.min(sourceRect.x, targetRect.x),
      y: Math.min(sourceRect.y, targetRect.y),
      width: Math.max(sourceRect.x + sourceRect.width, targetRect.x + targetRect.width) - Math.min(sourceRect.x, targetRect.x),
      height: Math.max(sourceRect.y + sourceRect.height, targetRect.y + targetRect.height) - Math.min(sourceRect.y, targetRect.y),
    };

    for (const [nodeId, rect] of rects.entries()) {
      if (nodeId === currentEdge.source || nodeId === currentEdge.target) {
        continue;
      }
      if (rectanglesOverlap(rect, spanBounds)) {
        return null;
      }
    }

    candidateLoop: for (const candidate of candidates) {
      for (const [nodeId, rect] of rects.entries()) {
        if (nodeId === currentEdge.source || nodeId === currentEdge.target) {
          continue;
        }
        const detectionRect = expandRect(rect, DETECTION_GAP);
        const spans = collectPolylineRectCollisions(candidate, detectionRect);
        if (spans.length > 0) {
          continue candidateLoop;
        }
      }
      return candidate;
    }

    return null;
  };

  const simpleCandidate = trySimpleCandidate();
  if (simpleCandidate) {
    return simpleCandidate;
  }

  // Initialise the polyline map with either the provided geometry or fresh
  // straight polylines for the other edges. We need the entire edge set so that
  // we can stagger detours when many edges share the same passage.
  const polylines = new Map<string, EdgePolyline>();
  edges.forEach((edge) => {
    const baseline = edge.id === currentEdge.id ? currentPolyline : buildInitialPolyline(edge, rects);
    if (baseline) {
      polylines.set(edge.id, baseline);
    }
  });

  if (!polylines.has(currentEdge.id)) {
    polylines.set(currentEdge.id, currentPolyline);
  }

  const edgesWithPolylines = edges.filter((edge) => polylines.has(edge.id));

  for (let pass = 0; pass < MAX_PASSES; pass++) {
    // Run the collision/repair procedure until all polylines are clear or we
    // hit the pass limit. Each pass works from the latest geometry.
    const collisions = gatherCollisions(edgesWithPolylines, rects, polylines, excludedObstacleIds);
    const changed = applyDetours(collisions, polylines);
    if (!changed) {
      break;
    }
  }

  // Return the adjusted polyline for the current edge. Fallback to the input
  // polyline if something unexpected prevented us from storing a result.
  return polylines.get(currentEdge.id) ?? currentPolyline;
};
