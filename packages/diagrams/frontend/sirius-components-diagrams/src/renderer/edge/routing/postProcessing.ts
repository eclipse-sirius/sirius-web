// Edge routing post-processing pipeline: detours, spacing, straightening.
import { Edge, Node, Position, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { computeAbsoluteNodeRects, Rect } from './geometry';

/**
 * The pipeline is organised in three independent phases:
 * 1. Detours: detect collisions against absolute node rectangles and rebuild orthogonal polylines.
 *    Validated through routing harness fixtures in `postProcessEdgeDetours.test.ts`.
 * 2. Parallel spacing: bucket overlapping segments and fan edges per side via `buildSpacedPolylines`.
 *    Covered in `postProcessEdgeParallelism.test.ts` and the SmoothStep overlap tests.
 * 3. Straightening: snap almost-axis-aligned paths via `straightenAlmostStraightPolyline`.
 *    Exercised directly in `postProcessEdgeStraighten.test.ts`.
 * Keeping these phases co-located clarifies the drop-in contract consumed by SmoothStepEdgeWrapper.
 */
// -----------------------------------------------------------------------------
// Detour + obstacle avoidance
// -----------------------------------------------------------------------------

// This module adjusts rectilinear edge polylines so they avoid overlapping nodes.
// It scans for collisions between edges and node rectangles, then inserts small
// detours that route the edge around the obstacle while keeping the path
// orthogonal. Every helper below plays a role in that pipeline: locating
// contact points, prioritising collisions, and building replacement polylines.

// Our edges are simple ordered lists of XY points; the renderer joins
// consecutive points with straight segments.
type EdgePolyline = XYPosition[];

export interface DetouredPolylineCollection {
  current: EdgePolyline;
  polylines: Map<string, EdgePolyline>;
}

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
const DETECTION_PADDING = DETECTION_GAP;

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

type RectCollisionCandidate = {
  nodeId: string;
  rect: Rect;
  detectionRect: Rect;
  paddedBounds: { left: number; right: number; top: number; bottom: number };
};

type CachedRectEntry = RectCollisionCandidate & { signature: string };
const rectEntryCache = new Map<string, CachedRectEntry>();

const createRectSignature = (rect: Rect): string =>
  `${rect.x}:${rect.y}:${rect.width}:${rect.height}:${DETECTION_PADDING}`;

const getCachedRectEntry = (nodeId: string, rect: Rect): RectCollisionCandidate => {
  const signature = createRectSignature(rect);
  const cached = rectEntryCache.get(nodeId);
  if (cached && cached.signature === signature) {
    return cached;
  }
  const detectionRect = expandRect(rect, DETECTION_PADDING);
  const entry: CachedRectEntry = {
    nodeId,
    rect,
    detectionRect,
    paddedBounds: {
      left: detectionRect.x,
      right: detectionRect.x + detectionRect.width,
      top: detectionRect.y,
      bottom: detectionRect.y + detectionRect.height,
    },
    signature,
  };
  rectEntryCache.set(nodeId, entry);
  return entry;
};

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
  /**
   * Walk every edge/node combination and record where the current polylines collide
   * with obstacle rectangles. Collisions are grouped by obstacle and approximate
   * entry coordinate so we can later apply stacked detours in a consistent order.
   */
  const collisions = new Map<string, CollisionCandidate[]>();

  type EdgeCollisionCandidate = {
    edge: EdgeWithPositions;
    polyline: EdgePolyline;
    bounds: PolylineBounds;
  };

  const edgeCandidates: EdgeCollisionCandidate[] = [];
  edges.forEach((edge) => {
    if (edge.hidden || edge.source === edge.target) {
      return;
    }
    const polyline = polylines.get(edge.id);
    if (!polyline || polyline.length < 2) {
      return;
    }
    const bounds = measurePolylineBounds(polyline);
    if (!bounds) {
      return;
    }
    edgeCandidates.push({
      edge: edge as EdgeWithPositions,
      polyline,
      bounds,
    });
  });
  if (edgeCandidates.length === 0 || rects.size === 0) {
    return collisions;
  }
  edgeCandidates.sort((first, second) => first.bounds.minX - second.bounds.minX);

  const rectEntries: RectCollisionCandidate[] = [];
  rects.forEach((rect, nodeId) => {
    if (excludedObstacleIds?.has(nodeId)) {
      return;
    }
    const entry = getCachedRectEntry(nodeId, rect);
    rectEntries.push(entry);
  });
  if (rectEntries.length === 0) {
    return collisions;
  }
  rectEntries.sort((first, second) => first.paddedBounds.left - second.paddedBounds.left);

  const activeEdges: EdgeCollisionCandidate[] = [];
  let candidateIndex = 0;

  rectEntries.forEach((rectEntry) => {
    const minXThreshold = rectEntry.paddedBounds.left - EPSILON;
    const maxXThreshold = rectEntry.paddedBounds.right + EPSILON;

    while (candidateIndex < edgeCandidates.length && edgeCandidates[candidateIndex]!.bounds.minX <= maxXThreshold) {
      activeEdges.push(edgeCandidates[candidateIndex]!);
      candidateIndex++;
    }

    let writeIndex = 0;
    for (let readIndex = 0; readIndex < activeEdges.length; readIndex++) {
      const candidate = activeEdges[readIndex]!;
      if (candidate.bounds.maxX >= minXThreshold) {
        activeEdges[writeIndex++] = candidate;
      }
    }
    activeEdges.length = writeIndex;

    for (let edgeIndex = 0; edgeIndex < activeEdges.length; edgeIndex++) {
      const candidate = activeEdges[edgeIndex]!;
      const { bounds, edge, polyline } = candidate;
      if (bounds.maxY < rectEntry.paddedBounds.top - EPSILON || bounds.minY > rectEntry.paddedBounds.bottom + EPSILON) {
        continue;
      }

      const isSourceNode = edge.source === rectEntry.nodeId;
      const isTargetNode = edge.target === rectEntry.nodeId;
      if (isSourceNode && isTargetNode) {
        continue;
      }

      const sourceHandlePoint = isSourceNode ? getHandlePoint(rectEntry.rect, edge.sourcePosition) : undefined;
      const collisionSpans = collectPolylineRectCollisions(polyline, rectEntry.detectionRect);
      if (collisionSpans.length === 0) {
        continue;
      }

      const preferredTargetSide = isTargetNode ? preferredTargetEntrySide(edge.targetPosition) : null;
      const preferredSourceSide = isSourceNode ? preferredSourceExitSide(edge.sourcePosition) : null;

      const adjustedSpans: PolylineRectCollision[] = [];

      collisionSpans.forEach((span) => {
        if (isSourceNode && span.entryIndex === 0 && span.exitIndex === 0 && sourceHandlePoint) {
          const sourceFace = edge.sourcePosition ?? Position.Right;
          const entryAtHandle =
            Math.abs(span.entryPoint.x - sourceHandlePoint.x) <= HANDLE_ALIGNMENT_TOLERANCE &&
            Math.abs(span.entryPoint.y - sourceHandlePoint.y) <= HANDLE_ALIGNMENT_TOLERANCE;

          if (entryAtHandle) {
            const sameFace = (() => {
              switch (sourceFace) {
                case Position.Left:
                  return Math.abs(span.exitPoint.x - rectEntry.detectionRect.x) <= FACE_ALIGNMENT_TOLERANCE;
                case Position.Right:
                  return (
                    Math.abs(span.exitPoint.x - (rectEntry.detectionRect.x + rectEntry.detectionRect.width)) <=
                    FACE_ALIGNMENT_TOLERANCE
                  );
                case Position.Top:
                  return Math.abs(span.exitPoint.y - rectEntry.detectionRect.y) <= FACE_ALIGNMENT_TOLERANCE;
                case Position.Bottom:
                default:
                  return (
                    Math.abs(span.exitPoint.y - (rectEntry.detectionRect.y + rectEntry.detectionRect.height)) <=
                    FACE_ALIGNMENT_TOLERANCE
                  );
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
                    span.exitPoint.x <= rectEntry.rect.x + HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.y - sourceHandlePoint.y) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
                case Position.Right:
                  return (
                    span.exitPoint.x >= rectEntry.rect.x + rectEntry.rect.width - HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.y - sourceHandlePoint.y) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
                case Position.Top:
                  return (
                    span.exitPoint.y <= rectEntry.rect.y + HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.x - sourceHandlePoint.x) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
                case Position.Bottom:
                default:
                  return (
                    span.exitPoint.y >= rectEntry.rect.y + rectEntry.rect.height - HANDLE_ALIGNMENT_TOLERANCE &&
                    Math.abs(span.exitPoint.x - sourceHandlePoint.x) <= HANDLE_ALIGNMENT_TOLERANCE
                  );
              }
            })();
            if (sameFace && lateralDelta <= HANDLE_ALIGNMENT_TOLERANCE && alignedAndOutward) {
              return;
            }
          }
        }

        const spanCopy = {
          entryPoint: { ...span.entryPoint },
          exitPoint: { ...span.exitPoint },
          entryIndex: span.entryIndex,
          exitIndex: span.exitIndex,
        };
        adjustedSpans.push(spanCopy);
      });

      if (adjustedSpans.length === 0) {
        continue;
      }

      adjustedSpans.forEach((span) => {
        let entrySide = determineCollisionSide(span.entryPoint, rectEntry.paddedBounds);
        let exitSide = determineCollisionSide(span.exitPoint, rectEntry.paddedBounds);

        if (!entrySide) {
          entrySide =
            (isSourceNode && preferredSourceSide) ||
            (isTargetNode && preferredTargetSide) ||
            closestCollisionSide(span.entryPoint, rectEntry.paddedBounds);
        }

        if (!exitSide) {
          exitSide =
            (isTargetNode && preferredTargetSide) ||
            (isSourceNode && preferredSourceSide) ||
            closestCollisionSide(span.exitPoint, rectEntry.paddedBounds);
        }

        if (isTargetNode && preferredTargetSide && entrySide === preferredTargetSide) {
          return;
        }
        if (isSourceNode && preferredSourceSide && exitSide === preferredSourceSide) {
          return;
        }

        if (isTargetNode && preferredTargetSide) {
          exitSide = preferredTargetSide;
        }

        const entryPoint = projectPointToSide(span.entryPoint, rectEntry.paddedBounds, entrySide);
        const exitPoint = projectPointToSide(span.exitPoint, rectEntry.paddedBounds, exitSide);

        const key = `${rectEntry.nodeId}::${Math.round(entryPoint.x)}::${Math.round(entryPoint.y)}`;
        const candidates = collisions.get(key) ?? [];
        candidates.push({
          edge,
          polyline,
          entryPoint,
          exitPoint,
          entryIndex: span.entryIndex,
          exitIndex: span.exitIndex,
          entryOrder: computeEntryOrder(entryPoint, rectEntry.rect),
          obstacle: rectEntry.rect,
        });
        collisions.set(key, candidates);
      });
    }
  });

  return collisions;
};

const applyDetours = (collisions: Map<string, CollisionCandidate[]>, polylines: Map<string, EdgePolyline>): boolean => {
  /**
   * Apply detours to the polylines involved in the provided collision groups.
   * Returns true when at least one polyline changed so callers can decide to
   * iterate another pass for secondary collisions.
   */
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
  /**
   * Collect the IDs of container nodes that wrap the provided node. Edges may
   * legitimately travel within their own ancestry chain, so we exclude those
   * rectangles from obstacle consideration when computing detours.
   */
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

/**
 * Lightweight descriptor used by the simplification pass to reason about
 * padded obstacle rectangles. Each entry stores the rectangle itself and the
 * precomputed bounds so the inner loop can perform a quick AABB test before
 * running the expensive segment/rectangle collision checks.
 */
type SimplificationObstacle = {
  nodeId: string;
  rect: Rect;
  bounds: { left: number; right: number; top: number; bottom: number };
};

/**
 * Reuse the expanded rectangles computed for detours and convert them into the
 * `SimplificationObstacle` shape consumed by the simplifier.
 */
const buildSimplificationObstacles = (rects: Map<string, Rect>): SimplificationObstacle[] =>
  Array.from(rects.entries()).map(([nodeId, rect]) => {
    const detectionRect = expandRect(rect, DETECTION_PADDING);
    return {
      nodeId,
      rect: detectionRect,
      bounds: rectToBounds(detectionRect),
    };
  });

/**
 * Fast axis-aligned bounding-box overlap predicate used to reject most
 * candidate paths before we fall back to the precise collision detection.
 */
const boundsOverlap = (
  pathBounds: { minX: number; maxX: number; minY: number; maxY: number },
  obstacleBounds: { left: number; right: number; top: number; bottom: number }
): boolean =>
  pathBounds.maxX >= obstacleBounds.left - EPSILON &&
  pathBounds.minX <= obstacleBounds.right + EPSILON &&
  pathBounds.maxY >= obstacleBounds.top - EPSILON &&
  pathBounds.minY <= obstacleBounds.bottom + EPSILON;
const simplifyDetouredPolyline = (polyline: EdgePolyline, obstacles: SimplificationObstacle[]): EdgePolyline => {
  /**
   * Remove redundant turns that may remain after the detour pass.
   *
   * Strategy:
   * 1. Slide a four-point window (three segments / two turns) along the path.
   * 2. Propose alternative subpaths (straight segment, vertical-then-horizontal L, horizontal-then-vertical L).
   * 3. Keep the first candidate that shortens the path, stays away from the portals (first/last segments),
   *    and avoids every obstacle rectangle.
   * 4. Continue until a full sweep yields no changes.
   */
  if (polyline.length < 5) {
    return polyline;
  }

  // Operate on a copy so callers receive the same reference when nothing changes.
  let working = polyline.slice();
  let mutated = false;

  const collidesWithObstacle = (candidatePath: XYPosition[]): boolean => {
    if (candidatePath.length < 2) {
      return false;
    }

    // Quick bounding-box computation lets us reject most rectangles cheaply.
    let minX = candidatePath[0]!.x;
    let maxX = minX;
    let minY = candidatePath[0]!.y;
    let maxY = minY;
    for (let index = 1; index < candidatePath.length; index++) {
      const point = candidatePath[index];
      if (!point) {
        continue;
      }
      if (point.x < minX) {
        minX = point.x;
      } else if (point.x > maxX) {
        maxX = point.x;
      }
      if (point.y < minY) {
        minY = point.y;
      } else if (point.y > maxY) {
        maxY = point.y;
      }
    }

    for (let index = 0; index < obstacles.length; index++) {
      const obstacle = obstacles[index]!;
      if (!boundsOverlap({ minX, maxX, minY, maxY }, obstacle.bounds)) {
        continue;
      }
      if (collectPolylineRectCollisions(candidatePath, obstacle.rect).length > 0) {
        return true;
      }
    }
    return false;
  };

  const trySimplifyWindow = (entryIndex: number): boolean => {
    const exitIndex = entryIndex + 3;
    if (exitIndex >= working.length) {
      return false;
    }

    const removalFirst = entryIndex + 1;
    const removalSecond = entryIndex + 2;
    const lastInteriorIndex = working.length - 2;
    // Guard rail: never strip the first/last interior bend so the polyline still
    // leaves and re-enters its nodes orthogonally.
    if (removalFirst <= 1 || removalSecond <= 1) {
      return false;
    }
    if (removalFirst >= lastInteriorIndex || removalSecond >= lastInteriorIndex) {
      return false;
    }

    const entry = working[entryIndex];
    const exit = working[exitIndex];
    if (!entry || !exit) {
      return false;
    }

    const subPathLength = exitIndex - entryIndex + 1;
    if (subPathLength < 4) {
      return false;
    }

    const candidates: XYPosition[][] = [];

    // Candidate 1: entry and exit already align along an axis, so we can merge
    // the window into a single straight segment.
    if (entry.x === exit.x || entry.y === exit.y) {
      candidates.push([entry, exit]);
    }

    // Candidate 2: move vertically first (align X), then horizontally.
    const verticalThenHorizontal = { x: entry.x, y: exit.y };
    if (
      (verticalThenHorizontal.x !== entry.x || verticalThenHorizontal.y !== entry.y) &&
      (verticalThenHorizontal.x !== exit.x || verticalThenHorizontal.y !== exit.y)
    ) {
      candidates.push([entry, verticalThenHorizontal, exit]);
    }

    // Candidate 3: mirror of the previous one (horizontal leg first, vertical leg second).
    const horizontalThenVertical = { x: exit.x, y: entry.y };
    const duplicatePivot =
      horizontalThenVertical.x === verticalThenHorizontal.x && horizontalThenVertical.y === verticalThenHorizontal.y;
    if (
      !duplicatePivot &&
      (horizontalThenVertical.x !== entry.x || horizontalThenVertical.y !== entry.y) &&
      (horizontalThenVertical.x !== exit.x || horizontalThenVertical.y !== exit.y)
    ) {
      candidates.push([entry, horizontalThenVertical, exit]);
    }

    for (let candidateIndex = 0; candidateIndex < candidates.length; candidateIndex++) {
      const candidatePath = candidates[candidateIndex]!;
      if (candidatePath.length >= subPathLength) {
        continue;
      }
      if (collidesWithObstacle(candidatePath)) {
        continue;
      }
      // Stitch the accepted candidate back into the polyline by replacing the window interior.
      working = [
        ...working.slice(0, entryIndex + 1),
        ...candidatePath.slice(1, candidatePath.length - 1),
        ...working.slice(exitIndex),
      ];
      return true;
    }

    return false;
  };

  // Keep iterating until no window yields a simplification.
  while (working.length >= 5) {
    let simplified = false;
    for (let entryIndex = 0; entryIndex <= working.length - 4; entryIndex++) {
      if (trySimplifyWindow(entryIndex)) {
        simplified = true;
        mutated = true;
        break;
      }
    }
    if (!simplified) {
      break;
    }
  }

  return mutated ? working : polyline;
};

export function buildDetouredPolyline(
  currentEdge: Edge<EdgeData>,
  currentPolyline: EdgePolyline,
  edges: Edge<EdgeData>[],
  nodes: Node<NodeData, DiagramNodeType>[]
): EdgePolyline;
/**
 * Re-route the provided edge polyline so it avoids overlapping node rectangles.
 * Overloads let callers either obtain just the updated polyline or the entire
 * set of detoured polylines required for downstream spacing logic.
 */
export function buildDetouredPolyline(
  currentEdge: Edge<EdgeData>,
  currentPolyline: EdgePolyline,
  edges: Edge<EdgeData>[],
  nodes: Node<NodeData, DiagramNodeType>[],
  options: { collectAll: true }
): DetouredPolylineCollection;
export function buildDetouredPolyline(
  currentEdge: Edge<EdgeData>,
  currentPolyline: EdgePolyline,
  edges: Edge<EdgeData>[],
  nodes: Node<NodeData, DiagramNodeType>[],
  options?: { collectAll?: boolean }
): EdgePolyline | DetouredPolylineCollection {
  // Entry point used by the edge renderer. It takes the current edge polyline
  // plus the surrounding graph context and returns a new polyline that avoids
  // rectangular obstacles.
  const collectAll = options?.collectAll ?? false;

  if (currentPolyline.length < 2 || edges.length === 0 || nodes.length === 0) {
    // Without enough geometry or graph data we cannot improve the path, so we
    // return the baseline untouched.
    return collectAll
      ? {
          current: currentPolyline,
          polylines: new Map<string, EdgePolyline>([[currentEdge.id, currentPolyline]]),
        }
      : currentPolyline;
  }

  // Pre-compute node rectangles in absolute coordinates so collision tests are
  // straightforward.
  const nodeLookup = new Map(nodes.map((node) => [node.id, node]));
  const excludedObstacleIds = computeExcludedObstacleIds(currentEdge, nodeLookup);
  const rects = computeAbsoluteNodeRects(nodes, { excludedNodeIds: excludedObstacleIds });
  if (rects.size === 0) {
    return currentPolyline;
  }
  // Cache the padded rectangles in the format expected by the simplifier. This
  // builds upon the same geometry already used for detours, so there is no
  // additional coordinate work later on.
  const simplificationObstacles = buildSimplificationObstacles(rects);

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

  const storedPolyline = polylines.get(currentEdge.id) ?? currentPolyline;
  const simplifiedCurrent = simplifyDetouredPolyline(storedPolyline, simplificationObstacles);
  polylines.set(currentEdge.id, simplifiedCurrent);

  // Return the adjusted polyline for the current edge. Fallback to the input
  // polyline if something unexpected prevented us from storing a result.
  const current = polylines.get(currentEdge.id) ?? currentPolyline;
  if (collectAll) {
    return {
      current,
      polylines: new Map(polylines),
    };
  }
  return current;
}

// -----------------------------------------------------------------------------
// Collision helpers
// -----------------------------------------------------------------------------

// Utility helpers to detect where rectilinear polylines touch or cross axis-aligned rectangles.
// The detour and layout logic rely on these functions to decide when edges need to bend around nodes.

// Small tolerance used across comparisons so floating-point coordinates do not introduce jitter.
const EPSILON = 0.0001;

// Compact representation for a segment/rectangle intersection: the point itself and the parametric
// coordinate t along the segment (0 at the start, 1 at the end). The t value lets us sort intersections.
type SegmentIntersection = {
  point: XYPosition;
  t: number;
};

type PolylineBounds = {
  minX: number;
  maxX: number;
  minY: number;
  maxY: number;
};

const polylineBoundsMemo = new WeakMap<EdgePolyline, PolylineBounds>();

const measurePolylineBounds = (polyline: EdgePolyline): PolylineBounds | undefined => {
  const cached = polylineBoundsMemo.get(polyline);
  if (cached) {
    return cached;
  }
  if (polyline.length === 0) {
    return undefined;
  }
  let minX = polyline[0]!.x;
  let maxX = minX;
  let minY = polyline[0]!.y;
  let maxY = minY;
  for (let index = 1; index < polyline.length; index++) {
    const point = polyline[index];
    if (!point) {
      continue;
    }
    if (point.x < minX) {
      minX = point.x;
    } else if (point.x > maxX) {
      maxX = point.x;
    }
    if (point.y < minY) {
      minY = point.y;
    } else if (point.y > maxY) {
      maxY = point.y;
    }
  }
  const bounds = { minX, maxX, minY, maxY };
  polylineBoundsMemo.set(polyline, bounds);
  return bounds;
};

const insertIntersectionSorted = (intersections: SegmentIntersection[], candidate: SegmentIntersection): void => {
  let index = 0;
  while (index < intersections.length && intersections[index]!.t < candidate.t - EPSILON) {
    index++;
  }
  if (index < intersections.length && Math.abs(intersections[index]!.t - candidate.t) <= EPSILON) {
    // Skip duplicates caused by tangential contacts (corner touches, shared endpoints, etc.).
    return;
  }
  intersections.splice(index, 0, candidate);
};

/**
 * Convert a rectangle into explicit boundary coordinates.
 * @param rect - Rectangle in absolute coordinates.
 * @returns Object containing top/bottom/left/right values.
 */
const rectToBounds = (rect: Rect): { left: number; right: number; top: number; bottom: number } => ({
  left: rect.x,
  right: rect.x + rect.width,
  top: rect.y,
  bottom: rect.y + rect.height,
});

/**
 * Test whether a point lies strictly inside a rectangle (excluding the border).
 * @param point - Candidate point.
 * @param bounds - Rectangle bounds produced by rectToBounds.
 * @returns True when the point sits inside the bounds.
 */
const pointInRect = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): boolean =>
  point.x > bounds.left + EPSILON &&
  point.x < bounds.right - EPSILON &&
  point.y > bounds.top + EPSILON &&
  point.y < bounds.bottom - EPSILON;

/**
 * Compute the intersection between two closed segments (if any).
 * @param a1 - First endpoint of segment A.
 * @param a2 - Second endpoint of segment A.
 * @param b1 - First endpoint of segment B.
 * @param b2 - Second endpoint of segment B.
 * @returns Intersection point and parametric value along segment A, or undefined when no overlap.
 */
const segmentIntersectionWithParam = (
  a1: XYPosition,
  a2: XYPosition,
  b1: XYPosition,
  b2: XYPosition
): SegmentIntersection | undefined => {
  // Solve the intersection using the standard 2x2 determinant approach.
  const denominator = (a1.x - a2.x) * (b1.y - b2.y) - (a1.y - a2.y) * (b1.x - b2.x);
  if (Math.abs(denominator) <= EPSILON) {
    // Segments are parallel or extremely close to parallel.
    //TOCHECK: returning undefined here means colinear overlaps (segment riding the rectangle edge) are ignored; confirm callers don't need to catch grazing cases.
    return undefined;
  }

  const numerator1 = a1.x * a2.y - a1.y * a2.x;
  const numerator2 = b1.x * b2.y - b1.y * b2.x;

  const x = (numerator1 * (b1.x - b2.x) - (a1.x - a2.x) * numerator2) / denominator;
  const y = (numerator1 * (b1.y - b2.y) - (a1.y - a2.y) * numerator2) / denominator;

  const withinSegment = (p: XYPosition, start: XYPosition, end: XYPosition) =>
    p.x >= Math.min(start.x, end.x) - EPSILON &&
    p.x <= Math.max(start.x, end.x) + EPSILON &&
    p.y >= Math.min(start.y, end.y) - EPSILON &&
    p.y <= Math.max(start.y, end.y) + EPSILON;

  const candidate = { x, y };
  // Reject intersections that lie outside either of the finite segments.
  if (!withinSegment(candidate, a1, a2) || !withinSegment(candidate, b1, b2)) {
    return undefined;
  }

  // Compute the parametric coordinate along segment A so callers can sort intersections.
  const dx = a2.x - a1.x;
  const dy = a2.y - a1.y;
  const t =
    Math.abs(dx) >= Math.abs(dy)
      ? dx === 0
        ? 0
        : (candidate.x - a1.x) / dx
      : dy === 0
      ? 0
      : (candidate.y - a1.y) / dy;

  return { point: candidate, t };
};

/**
 * Find all intersection points between a segment and the boundary of a rectangle.
 * @param start - Segment start point.
 * @param end - Segment end point.
 * @param bounds - Rectangle bounds.
 * @returns Ordered list of intersection points along the segment.
 */
const collectSegmentBoundaryPoints = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): SegmentIntersection[] => {
  const dx = end.x - start.x;
  const dy = end.y - start.y;
  const isVertical = Math.abs(dx) <= EPSILON;
  const isHorizontal = Math.abs(dy) <= EPSILON;

  if (isVertical && isHorizontal) {
    // Degenerate segment reduced to a single point: there cannot be a boundary intersection
    // (a point would have been caught earlier through the pointInRect checks).
    return [];
  }

  if (isVertical) {
    return collectAxisAlignedSegmentBoundaryPoints(start, end, bounds, 'vertical');
  }

  if (isHorizontal) {
    return collectAxisAlignedSegmentBoundaryPoints(start, end, bounds, 'horizontal');
  }

  return collectGeneralSegmentBoundaryPoints(start, end, bounds);
};

const collectAxisAlignedSegmentBoundaryPoints = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  orientation: 'vertical' | 'horizontal'
): SegmentIntersection[] => {
  const intersections: SegmentIntersection[] = [];

  if (orientation === 'vertical') {
    const x = start.x;
    if (x < bounds.left - EPSILON || x > bounds.right + EPSILON) {
      return intersections;
    }
    const minY = Math.min(start.y, end.y);
    const maxY = Math.max(start.y, end.y);
    if (maxY < bounds.top - EPSILON || minY > bounds.bottom + EPSILON) {
      return intersections;
    }

    const span = end.y - start.y;
    const clampedX = x < bounds.left ? bounds.left : x > bounds.right ? bounds.right : x;
    const addIntersectionAtY = (yEdge: number) => {
      if (yEdge < minY - EPSILON || yEdge > maxY + EPSILON) {
        return;
      }
      const t = span === 0 ? 0 : (yEdge - start.y) / span;
      insertIntersectionSorted(intersections, {
        point: { x: clampedX, y: yEdge },
        t,
      });
    };

    addIntersectionAtY(bounds.top);
    addIntersectionAtY(bounds.bottom);
    return intersections;
  }

  const y = start.y;
  if (y < bounds.top - EPSILON || y > bounds.bottom + EPSILON) {
    return intersections;
  }
  const minX = Math.min(start.x, end.x);
  const maxX = Math.max(start.x, end.x);
  if (maxX < bounds.left - EPSILON || minX > bounds.right + EPSILON) {
    return intersections;
  }

  const span = end.x - start.x;
  const clampedY = y < bounds.top ? bounds.top : y > bounds.bottom ? bounds.bottom : y;
  const addIntersectionAtX = (xEdge: number) => {
    if (xEdge < minX - EPSILON || xEdge > maxX + EPSILON) {
      return;
    }
    const t = span === 0 ? 0 : (xEdge - start.x) / span;
    insertIntersectionSorted(intersections, {
      point: { x: xEdge, y: clampedY },
      t,
    });
  };

  addIntersectionAtX(bounds.left);
  addIntersectionAtX(bounds.right);
  return intersections;
};

const collectGeneralSegmentBoundaryPoints = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): SegmentIntersection[] => {
  const intersections: SegmentIntersection[] = [];
  const corners = [
    { x: bounds.left, y: bounds.top },
    { x: bounds.right, y: bounds.top },
    { x: bounds.right, y: bounds.bottom },
    { x: bounds.left, y: bounds.bottom },
  ];

  const edges: [XYPosition, XYPosition][] = [
    [corners[0]!, corners[1]!],
    [corners[1]!, corners[2]!],
    [corners[2]!, corners[3]!],
    [corners[3]!, corners[0]!],
  ];

  edges.forEach(([edgeStart, edgeEnd]) => {
    const candidate = segmentIntersectionWithParam(start, end, edgeStart, edgeEnd);
    if (candidate) {
      insertIntersectionSorted(intersections, candidate);
    }
  });

  return intersections;
};

export type PolylineRectCollision = {
  entryPoint: XYPosition;
  exitPoint: XYPosition;
  entryIndex: number;
  exitIndex: number;
};

/**
 * Compute entry/exit points for a single polyline segment relative to a rectangle.
 * @param start - Segment start point.
 * @param end - Segment end point.
 * @param bounds - Rectangle bounds.
 * @param insideStart - Whether the start point is inside the rectangle.
 * @param insideEnd - Whether the end point is inside the rectangle.
 * @returns Entry/exit points (when present) describing how the segment crosses the rectangle.
 */
const getEntryExitForSegment = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  insideStart: boolean,
  insideEnd: boolean,
  providedIntersections?: SegmentIntersection[]
): { entryPoint?: XYPosition; exitPoint?: XYPosition } => {
  const intersections = providedIntersections ?? collectSegmentBoundaryPoints(start, end, bounds);
  if (insideStart && insideEnd) {
    // Segment is entirely inside: treat start/end as the collision span.
    return { entryPoint: { ...start }, exitPoint: { ...end } };
  }

  if (insideStart && !insideEnd) {
    // Segment leaves the rectangle: start is entry, first intersection is exit.
    const exitPoint = intersections[0]?.point ?? { ...end };
    return { entryPoint: { ...start }, exitPoint };
  }

  if (!insideStart && insideEnd) {
    // Segment enters the rectangle: first intersection is entry.
    const entryPoint = intersections[0]?.point ?? { ...start };
    return { entryPoint, exitPoint: { ...end } };
  }

  if (intersections.length === 0) {
    return {};
  }

  const entryPoint = intersections[0]!.point;
  const exitPoint = intersections[intersections.length - 1]!.point;
  return { entryPoint, exitPoint };
};

/**
 * Scan a rectilinear polyline and capture each span that overlaps a rectangle.
 * @param points - Polyline points (must contain at least two points to form a segment).
 * @param rect - Rectangle to test against.
 * @returns List of collision spans including entry/exit points and segment indices.
 */
export const collectPolylineRectCollisions = (points: XYPosition[], rect: Rect): PolylineRectCollision[] => {
  const collisions: PolylineRectCollision[] = [];
  if (points.length < 2) {
    return collisions;
  }

  const bounds = rectToBounds(rect);
  let inside = false;
  let pendingEntryPoint: XYPosition | undefined;
  let pendingEntryIndex: number | undefined;

  for (let index = 0; index < points.length - 1; index++) {
    const start = points[index];
    const end = points[index + 1];
    if (!start || !end) {
      continue;
    }

    const insideStart = pointInRect(start, bounds);
    const insideEnd = pointInRect(end, bounds);
    let intersections: SegmentIntersection[] | undefined;
    let overlaps = false;

    if (insideStart || insideEnd) {
      overlaps = true;
      if (insideStart !== insideEnd) {
        intersections = collectSegmentBoundaryPoints(start, end, bounds);
      }
    } else {
      intersections = collectSegmentBoundaryPoints(start, end, bounds);
      overlaps = intersections.length > 0;
    }

    if (!overlaps) {
      // Nothing to do if this segment does not touch the rectangle.
      continue;
    }

    const { entryPoint, exitPoint } = getEntryExitForSegment(start, end, bounds, insideStart, insideEnd, intersections);

    if (!inside) {
      // Record the entry point so we can emit a single collision for contiguous segments.
      pendingEntryPoint = entryPoint ?? (insideStart ? { ...start } : undefined);
      pendingEntryIndex = index;
      inside = true;
    }

    const isLastSegment = index === points.length - 2;
    const exitsNow = !insideEnd || isLastSegment;
    if (inside && exitsNow) {
      const finalExitPoint = exitPoint ?? (insideEnd ? { ...end } : undefined);
      if (pendingEntryPoint && finalExitPoint) {
        const entryIndexValue = pendingEntryIndex ?? index;
        const sameSegment = entryIndexValue === index;
        const alignsVerticalBoundary =
          Math.abs(pendingEntryPoint.x - finalExitPoint.x) <= EPSILON &&
          (Math.abs(pendingEntryPoint.x - bounds.left) <= EPSILON ||
            Math.abs(pendingEntryPoint.x - bounds.right) <= EPSILON);
        const alignsHorizontalBoundary =
          Math.abs(pendingEntryPoint.y - finalExitPoint.y) <= EPSILON &&
          (Math.abs(pendingEntryPoint.y - bounds.top) <= EPSILON ||
            Math.abs(pendingEntryPoint.y - bounds.bottom) <= EPSILON);
        const segmentOnBoundary =
          sameSegment &&
          ((alignsVerticalBoundary &&
            Math.abs(start.x - pendingEntryPoint.x) <= EPSILON &&
            Math.abs(end.x - pendingEntryPoint.x) <= EPSILON) ||
            (alignsHorizontalBoundary &&
              Math.abs(start.y - pendingEntryPoint.y) <= EPSILON &&
              Math.abs(end.y - pendingEntryPoint.y) <= EPSILON));

        const entryExitNearlySame =
          Math.abs(finalExitPoint.x - pendingEntryPoint.x) <= EPSILON &&
          Math.abs(finalExitPoint.y - pendingEntryPoint.y) <= EPSILON;
        if (!entryExitNearlySame && !segmentOnBoundary) {
          // Register the span only when it has measurable length and is not already covered by a
          // segment that runs purely along a rectangle boundary.
          collisions.push({
            entryPoint: pendingEntryPoint,
            exitPoint: finalExitPoint,
            entryIndex: pendingEntryIndex ?? index,
            exitIndex: index,
          });
        }
      }
      // Prepare for the next span in case the polyline immediately re-enters the rectangle.
      pendingEntryPoint = insideEnd && !isLastSegment ? { ...end } : undefined;
      pendingEntryIndex = insideEnd && !isLastSegment ? index : undefined;
      inside = insideEnd;
    }
  }

  return collisions;
};

/**
 * Convenience wrapper returning each intersection point individually.
 * @param points - Polyline points.
 * @param rect - Rectangle bounds.
 * @returns Entry and exit points paired with their segment index.
 */
export const collectPolylineRectIntersections = (
  points: XYPosition[],
  rect: Rect
): { index: number; point: XYPosition }[] => {
  const collisions = collectPolylineRectCollisions(points, rect);
  const intersections: { index: number; point: XYPosition }[] = [];
  collisions.forEach((collision) => {
    intersections.push({ index: collision.entryIndex, point: collision.entryPoint });
    intersections.push({ index: collision.exitIndex, point: collision.exitPoint });
  });
  return intersections;
};

/**
 * Expand a rectangle evenly on all sides by a padding amount.
 * @param rect - Original rectangle.
 * @param padding - Distance to extend on every side.
 * @returns New rectangle with the expanded bounds.
 */
export const expandRect = (rect: Rect, padding: number): Rect => ({
  x: rect.x - padding,
  y: rect.y - padding,
  width: rect.width + padding * 2,
  height: rect.height + padding * 2,
});

// -----------------------------------------------------------------------------
// Detour helpers
// -----------------------------------------------------------------------------

// This module shapes the concrete polyline used to bypass a rectangular obstacle.
// Each helper function does a single job, from working out which side of the
// rectangle we touch, to measuring path length so we can pick the best detour.

type DetourSide = 'top' | 'right' | 'bottom' | 'left';

// Every rectangle has four edges; we keep the count in a constant so loops and
// modular arithmetic can refer to it consistently.
const edgeCount = 4;

/**
 * Build the list of corner vertices for a rectangle.
 * @param rect - Rectangle expressed in absolute coordinates.
 * @returns The four corners in clockwise order starting from the top-left.
 */
const perimeterVertices = (rect: Rect): XYPosition[] => [
  // Top-left corner.
  { x: rect.x, y: rect.y },
  // Top-right corner.
  { x: rect.x + rect.width, y: rect.y },
  // Bottom-right corner.
  { x: rect.x + rect.width, y: rect.y + rect.height },
  // Bottom-left corner.
  { x: rect.x, y: rect.y + rect.height },
];

/**
 * Translate an edge index into a human-readable side name.
 * @param edgeIndex - Index along the rectangle perimeter (can be >= 4).
 * @returns The logical side string we use for orientation decisions.
 */
const sideForEdge = (edgeIndex: number): DetourSide => {
  // Wrap the index so we stay within 0..3 even when rotating around the shape.
  switch (edgeIndex % edgeCount) {
    case 0:
      return 'top';
    case 1:
      return 'right';
    case 2:
      return 'bottom';
    default:
      return 'left';
  }
};

/**
 * Compare two floating-point numbers with tolerance.
 * @param a - First value to compare.
 * @param b - Second value to compare.
 * @param epsilon - Allowed delta before we treat the values as different.
 * @returns True when the numbers are close enough to be considered equal.
 */
const almostEqual = (a: number, b: number, epsilon = 0.5): boolean => Math.abs(a - b) <= epsilon;

/**
 * Check whether a point lies on a given rectangle edge segment.
 * @param start - First endpoint of the edge.
 * @param end - Second endpoint of the edge.
 * @param point - Point we want to test.
 * @returns True when the point falls on the edge within a small tolerance.
 */
const edgeContainsPoint = (start: XYPosition, end: XYPosition, point: XYPosition): boolean =>
  // Handle vertical edges by comparing x and clamping y within the segment span.
  (almostEqual(start.x, end.x) &&
    almostEqual(point.x, start.x) &&
    point.y >= Math.min(start.y, end.y) - 0.5 &&
    point.y <= Math.max(start.y, end.y) + 0.5) ||
  // Handle horizontal edges by comparing y and clamping x.
  (almostEqual(start.y, end.y) &&
    almostEqual(point.y, start.y) &&
    point.x >= Math.min(start.x, end.x) - 0.5 &&
    point.x <= Math.max(start.x, end.x) + 0.5);

/**
 * Identify which edge index of the rectangle contains the given point.
 * @param rect - Rectangle we are walking around.
 * @param point - Point on or very near the rectangle perimeter.
 * @returns Zero-based index of the matching edge.
 */
const edgeIndexForPoint = (rect: Rect, point: XYPosition): number => {
  const vertices = perimeterVertices(rect);
  for (let i = 0; i < edgeCount; i++) {
    const start = vertices[i];
    const end = vertices[(i + 1) % edgeCount];
    // Skip safety-checks when a vertex is unexpectedly missing.
    if (!start || !end) {
      continue;
    }
    if (edgeContainsPoint(start, end, point)) {
      return i;
    }
  }
  // Default to the first edge to keep the algorithm stable even on failure.
  return 0;
};

/**
 * Measure the total length of a polyline.
 * @param points - Ordered series of points describing the path.
 * @returns Scalar distance travelled along the path segments.
 */
const pathLength = (points: XYPosition[]): number => {
  if (points.length < 2) {
    return 0;
  }
  let distance = 0;
  for (let i = 1; i < points.length; i++) {
    const prev = points[i - 1];
    const current = points[i];
    if (!prev || !current) {
      continue;
    }
    // Measure the straight-line distance between consecutive points and add it.
    distance += Math.hypot(current.x - prev.x, current.y - prev.y);
  }
  return distance;
};

/**
 * Remove consecutive duplicate points from a polyline so segments stay valid.
 * @param points - Original path points that may contain duplicates.
 * @returns New array containing the same route without zero-length hops.
 */
const dedupeSequentialPoints = (points: XYPosition[]): XYPosition[] => {
  if (points.length === 0) {
    return points;
  }
  const first = points[0];
  if (!first) {
    return [];
  }
  const result: XYPosition[] = [first];
  for (let i = 1; i < points.length; i++) {
    const previous = result[result.length - 1];
    const current = points[i];
    if (!current) {
      continue;
    }
    // Only keep the new point when it actually changes position.
    if (!previous || previous.x !== current.x || previous.y !== current.y) {
      result.push(current);
    }
  }
  return result;
};

/**
 * Walk around the rectangle perimeter from entry to exit along one direction.
 * @param rect - Obstacle rectangle expressed in absolute coordinates.
 * @param entryPoint - First point where the polyline meets the rectangle.
 * @param exitPoint - Final point where the polyline leaves the rectangle.
 * @param entryEdge - Edge index aligned with the entry point.
 * @param exitEdge - Edge index aligned with the exit point.
 * @param direction - +1 for clockwise, -1 for counter-clockwise traversal.
 * @returns Points to follow along the perimeter and the corresponding side order.
 */
const buildPerimeterTrace = (
  rect: Rect,
  entryPoint: XYPosition,
  exitPoint: XYPosition,
  entryEdge: number,
  exitEdge: number,
  direction: 1 | -1
): { points: XYPosition[]; sides: DetourSide[] } => {
  const vertices = perimeterVertices(rect);
  const points: XYPosition[] = [entryPoint];
  const sides: DetourSide[] = [];

  let currentEdge = entryEdge;
  let guard = 0;

  while (guard < edgeCount + 2) {
    // Record the side we are presently walking.
    sides.push(sideForEdge(currentEdge));
    if (currentEdge === exitEdge) {
      // Once we reach the exit edge we append the exit point and stop.
      points.push(exitPoint);
      break;
    }

    // Determine the next corner based on the traversal direction.
    const nextCornerIndex = direction === 1 ? (currentEdge + 1) % edgeCount : currentEdge;
    const nextCorner = vertices[nextCornerIndex];
    if (nextCorner) {
      points.push(nextCorner);
    }
    // Step to the adjacent edge, wrapping around using modular arithmetic.
    currentEdge = (currentEdge + direction + edgeCount) % edgeCount;
    guard++;
  }

  return { points: dedupeSequentialPoints(points), sides };
};

/**
 * Choose the shortest perimeter trace between entry and exit points.
 * @param rect - Rectangle being avoided.
 * @param entryPoint - Where we enter the rectangle.
 * @param exitPoint - Where we leave the rectangle.
 * @param entryEdge - Index of the edge containing the entry point.
 * @param exitEdge - Index of the edge containing the exit point.
 * @returns Optimal perimeter points and the parallel list of sides traversed.
 */
const selectShortestPerimeter = (
  rect: Rect,
  entryPoint: XYPosition,
  exitPoint: XYPosition,
  entryEdge: number,
  exitEdge: number
): { points: XYPosition[]; sideSequence: DetourSide[] } => {
  const clockwise = buildPerimeterTrace(rect, entryPoint, exitPoint, entryEdge, exitEdge, 1);
  const counterClockwise = buildPerimeterTrace(rect, entryPoint, exitPoint, entryEdge, exitEdge, -1);

  // Pick whichever direction yields the shorter travel distance.
  const choice = pathLength(clockwise.points) <= pathLength(counterClockwise.points) ? clockwise : counterClockwise;

  return { points: choice.points, sideSequence: choice.sides.slice(0, Math.max(choice.points.length - 1, 0)) };
};

/**
 * Convert a side name into a normal vector that points outward from the rectangle.
 * @param side - Logical rectangle side we are offsetting.
 * @returns Unit vector pointing away from the rectangle interior.
 */
const sideNormal = (side: DetourSide): XYPosition => {
  switch (side) {
    case 'top':
      return { x: 0, y: -1 };
    case 'right':
      return { x: 1, y: 0 };
    case 'bottom':
      return { x: 0, y: 1 };
    case 'left':
    default:
      return { x: -1, y: 0 };
  }
};

/**
 * Offset perimeter points outward to create extra spacing around the obstacle.
 * @param points - Raw perimeter points including corners.
 * @param sideSequence - Ordered list describing which side each segment follows.
 * @param extraGap - Additional distance to move points away from the rectangle.
 * @returns Adjusted point list with the requested spacing applied.
 */
const applyOffsetAlongPerimeter = (
  points: XYPosition[],
  sideSequence: DetourSide[],
  extraGap: number
): XYPosition[] => {
  if (extraGap <= 0 || points.length < 2) {
    return points;
  }
  const adjusted = points.map((point) => ({ ...point }));
  for (let i = 0; i < sideSequence.length; i++) {
    const side = sideSequence[i];
    if (side === undefined) {
      continue;
    }
    const normal = sideNormal(side);
    const start = adjusted[i];
    const end = adjusted[i + 1];
    if (start) {
      // Move the starting point away from the obstacle along the outward normal.
      start.x += normal.x * extraGap;
      start.y += normal.y * extraGap;
    }
    if (end) {
      end.x += normal.x * extraGap;
      end.y += normal.y * extraGap;
    }
  }
  return dedupeSequentialPoints(adjusted);
};

/**
 * Ensure a polyline only contains horizontal or vertical segments.
 * @param points - Candidate polyline that may contain diagonal steps.
 * @returns Rectified list of points with missing elbows reinserted.
 */
// Re-introduce missing elbows when merging detours so every segment remains axis aligned.
const ensureOrthogonalSegments = (points: XYPosition[]): XYPosition[] => {
  if (points.length === 0) {
    return points;
  }

  const deduped = dedupeSequentialPoints(points);
  if (deduped.length <= 1) {
    return deduped;
  }

  const rectified: XYPosition[] = [deduped[0]!];

  for (let i = 1; i < deduped.length; i++) {
    const target = deduped[i];
    const previous = rectified[rectified.length - 1];
    if (!target || !previous) {
      continue;
    }

    if (previous.x === target.x || previous.y === target.y) {
      // Segment already aligned; simply append the target point.
      rectified.push(target);
      continue;
    }

    const penultimate = rectified.length >= 2 ? rectified[rectified.length - 2] : undefined;
    const cameFromHorizontal = !!penultimate && penultimate.y === previous.y && penultimate.x !== previous.x;
    const cameFromVertical = !!penultimate && penultimate.x === previous.x && penultimate.y !== previous.y;

    let bridge: XYPosition | undefined;
    if (cameFromHorizontal && !cameFromVertical) {
      // Continue the horizontal trend before turning vertically.
      bridge = { x: target.x, y: previous.y };
    } else if (cameFromVertical && !cameFromHorizontal) {
      // Continue the vertical trend before turning horizontally.
      bridge = { x: previous.x, y: target.y };
    } else {
      // We do not have a dominant direction; try both options and keep the one
      // that best matches the upcoming point.
      const horizontalCandidate: XYPosition = { x: target.x, y: previous.y };
      const verticalCandidate: XYPosition = { x: previous.x, y: target.y };

      const lookahead = deduped[i + 1];
      if (lookahead) {
        const exitsHorizontally = lookahead.y === target.y && lookahead.x !== target.x;
        const exitsVertically = lookahead.x === target.x && lookahead.y !== target.y;
        if (exitsVertically && !exitsHorizontally) {
          bridge = verticalCandidate;
        } else if (exitsHorizontally && !exitsVertically) {
          bridge = horizontalCandidate;
        }
      }

      if (!bridge) {
        bridge = horizontalCandidate;
      }
    }

    if (bridge) {
      if (bridge.x !== previous.x || bridge.y !== previous.y) {
        rectified.push(bridge);
      }

      const latest = rectified[rectified.length - 1];
      if (latest && latest.x !== target.x && latest.y !== target.y) {
        const secondary: XYPosition = { x: target.x, y: latest.y };
        if (secondary.x !== latest.x || secondary.y !== latest.y) {
          rectified.push(secondary);
        }
      }

      const lastBeforeTarget = rectified[rectified.length - 1];
      if (!lastBeforeTarget || lastBeforeTarget.x !== target.x || lastBeforeTarget.y !== target.y) {
        rectified.push(target);
      }
    } else {
      rectified.push(target);
    }
  }

  return dedupeSequentialPoints(rectified);
};

/**
 * Merge a detour polyline back into the original path while maintaining orthogonality.
 * @param original - Original edge polyline before the obstacle fix.
 * @param entryIndex - Index of the segment where the polyline enters the obstacle.
 * @param exitIndex - Index of the segment where the polyline leaves the obstacle.
 * @param detour - New points describing the path around the obstacle.
 * @returns Updated polyline that incorporates the detour.
 */
const mergePolyline = (
  original: XYPosition[],
  entryIndex: number,
  exitIndex: number,
  detour: XYPosition[]
): XYPosition[] => {
  // Keep the portion before we hit the obstacle untouched.
  const prefix = original.slice(0, entryIndex + 1);
  // Keep the portion after we exit the obstacle untouched.
  const suffix = original.slice(exitIndex + 1);

  const cleanedDetour = detour.filter((point, idx, arr): point is XYPosition => {
    if (!point) {
      return false;
    }
    if (idx === 0) {
      return true;
    }
    const previous = arr[idx - 1];
    if (!previous) {
      return true;
    }
    // Drop duplicates so the orthogonal rectifier does not see zero-length steps.
    return point.x !== previous.x || point.y !== previous.y;
  });

  if (cleanedDetour.length === 0) {
    // No detour to merge; return a shallow copy of the original.
    return original.slice();
  }

  return ensureOrthogonalSegments([...prefix, ...cleanedDetour, ...suffix]);
};

export type DetourOptions = {
  baseGap: number;
  extraGap: number;
};

export type DetourContext = {
  entryPoint: XYPosition;
  exitPoint: XYPosition;
  entryIndex: number;
  exitIndex: number;
};

/**
 * Construct a detoured version of a polyline that avoids a rectangular obstacle.
 * @param polyline - Original rectilinear polyline to adjust.
 * @param obstacle - Rectangle the polyline currently intersects.
 * @param options - Gap settings controlling how far the detour stands off.
 * @param context - Optional precomputed collision context (entry/exit points).
 * @returns A new polyline that skirts around the rectangle or null when no detour is required.
 */
export const buildDetourAroundRectangle = (
  polyline: XYPosition[],
  obstacle: Rect,
  options: DetourOptions,
  context?: DetourContext
): XYPosition[] | null => {
  if (polyline.length < 2) {
    return null;
  }

  // Widen the obstacle slightly so edges do not visually hug the node.
  const paddedRect = expandRect(obstacle, options.baseGap);
  const bounds = {
    left: paddedRect.x,
    right: paddedRect.x + paddedRect.width,
    top: paddedRect.y,
    bottom: paddedRect.y + paddedRect.height,
  };

  const snapToPerimeter = (point: XYPosition): XYPosition => {
    const clampedX = Math.min(Math.max(point.x, bounds.left), bounds.right);
    const clampedY = Math.min(Math.max(point.y, bounds.top), bounds.bottom);

    const candidates: XYPosition[] = [
      { x: clampedX, y: bounds.top },
      { x: bounds.right, y: clampedY },
      { x: clampedX, y: bounds.bottom },
      { x: bounds.left, y: clampedY },
    ];

    let best = candidates[0]!;
    let bestDistance = Math.hypot(best.x - point.x, best.y - point.y);
    for (let i = 1; i < candidates.length; i++) {
      const candidate = candidates[i]!;
      const distance = Math.hypot(candidate.x - point.x, candidate.y - point.y);
      if (distance < bestDistance) {
        best = candidate;
        bestDistance = distance;
      }
    }

    return best;
  };

  let entryPoint = context?.entryPoint;
  let exitPoint = context?.exitPoint;
  let entryIndex = context?.entryIndex;
  let exitIndex = context?.exitIndex;

  if (!context) {
    // Derive entry/exit points automatically by intersecting the polyline with the padded rectangle.
    const intersections = collectPolylineRectIntersections(polyline, paddedRect);
    if (intersections.length < 2) {
      return null;
    }
    const entry = intersections[0]!;
    const exit = intersections[intersections.length - 1]!;
    entryPoint = entry.point;
    exitPoint = exit.point;
    entryIndex = entry.index;
    exitIndex = exit.index;
  }

  if (!entryPoint || !exitPoint || typeof entryIndex !== 'number' || typeof exitIndex !== 'number') {
    return null;
  }

  entryPoint = snapToPerimeter(entryPoint);
  exitPoint = snapToPerimeter(exitPoint);

  // Work out the edges touched by the entry and exit to guide the perimeter walk.
  const entryEdge = edgeIndexForPoint(paddedRect, entryPoint);
  const exitEdge = edgeIndexForPoint(paddedRect, exitPoint);

  const { points: perimeterPoints, sideSequence } = selectShortestPerimeter(
    paddedRect,
    entryPoint,
    exitPoint,
    entryEdge,
    exitEdge
  );

  const detourPoints = applyOffsetAlongPerimeter(perimeterPoints, sideSequence, options.extraGap);
  const detourWithAnchors = [entryPoint, ...detourPoints, exitPoint];
  return mergePolyline(polyline, entryIndex, exitIndex, detourWithAnchors);
};

// -----------------------------------------------------------------------------
// Parallel spacing
// -----------------------------------------------------------------------------

/**
 * Detects long overlapping segments between rectilinear edge polylines and nudges
 * them apart so parallel edges remain legible. The algorithm groups segments by
 * shared coordinates, assigns offset multiples, and rewrites the polylines with
 * the requested spacing.
 */
type SpacingAxis = 'horizontal' | 'vertical';

type PolylineSegment = {
  edgeId: string;
  axis: SpacingAxis;
  coordinate: number;
  start: number;
  end: number;
  startIndex: number;
  endIndex: number;
};

type CoordinateGroup = {
  axis: SpacingAxis;
  coordinate: number;
  segmentsByEdge: Map<string, PolylineSegment[]>;
};

type CoordinateAssignments = Map<string, Map<string, number>>;

type SegmentIndex = Map<string, CoordinateGroup>;

export type ParallelEdgeSpacingOptions = {
  spacing: number;
  tolerance: number;
  minOverlap: number;
};

const DEFAULT_SPACING = 6;
const DEFAULT_TOLERANCE = 0.5;
const DEFAULT_MIN_OVERLAP = 4;

export const DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS: ParallelEdgeSpacingOptions = {
  spacing: DEFAULT_SPACING,
  tolerance: DEFAULT_TOLERANCE,
  minOverlap: DEFAULT_MIN_OVERLAP,
};

const AXIS_KEY_SEPARATOR = '|';

const createCoordinateKey = (axis: SpacingAxis, coordinate: number, tolerance: number): string => {
  const rounded = Math.round(coordinate / tolerance) * tolerance;
  return `${axis}${AXIS_KEY_SEPARATOR}${rounded}`;
};

/**
 * Retrieve (or lazily create) the bucket describing all segments aligned on a
 * given coordinate. The tolerance keeps near-identical floats in the same bin.
 */
const ensureGroup = (
  index: SegmentIndex,
  axis: SpacingAxis,
  coordinate: number,
  tolerance: number
): CoordinateGroup => {
  const key = createCoordinateKey(axis, coordinate, tolerance);
  let group = index.get(key);
  if (!group) {
    group = {
      axis,
      coordinate,
      segmentsByEdge: new Map(),
    };
    index.set(key, group);
  }
  return group;
};

const extractSegments = (polylines: Map<string, XYPosition[]>, tolerance: number, minOverlap: number): SegmentIndex => {
  /**
   * Scan every polyline and collect the axis-aligned segments that are long enough
   * to matter. Each segment is grouped by its axis/coordinate so we can later
   * detect overlapping edges that ought to be spaced apart.
   */
  const index: SegmentIndex = new Map();

  for (const [edgeId, polyline] of polylines.entries()) {
    if (!polyline || polyline.length < 2) {
      continue;
    }

    for (let i = 1; i < polyline.length; i++) {
      const prev = polyline[i - 1];
      const current = polyline[i];
      if (!prev || !current) {
        continue;
      }

      if (Math.abs(prev.x - current.x) <= tolerance && Math.abs(prev.y - current.y) <= tolerance) {
        continue;
      }

      const lengthY = Math.abs(current.y - prev.y);
      const lengthX = Math.abs(current.x - prev.x);

      if (Math.abs(prev.x - current.x) <= tolerance) {
        if (lengthY < minOverlap) {
          continue;
        }
        const start = Math.min(prev.y, current.y);
        const end = Math.max(prev.y, current.y);
        //TOCHECK: Horizontal/vertical branch bodies are nearly identical; a shared helper could cut duplication.
        const segment: PolylineSegment = {
          edgeId,
          axis: 'vertical',
          coordinate: prev.x,
          start,
          end,
          startIndex: i - 1,
          endIndex: i,
        };
        const key = createCoordinateKey('vertical', prev.x, tolerance);
        const group = ensureGroup(index, 'vertical', prev.x, tolerance);
        if (process.env.NODE_ENV === 'test') {
          // eslint-disable-next-line no-console
          console.log('[parallel-spacing] segment(vertical)', edgeId, prev.x, start, end);
        }
        const collection = group.segmentsByEdge.get(edgeId);
        if (collection) {
          collection.push(segment);
        } else {
          group.segmentsByEdge.set(edgeId, [segment]);
        }
        if (process.env.NODE_ENV === 'test') {
          // eslint-disable-next-line no-console
          console.log(
            '[parallel-spacing] group state',
            key,
            Array.from(group.segmentsByEdge.entries()).map(([id, items]) => ({
              edgeId: id,
              count: items.length,
            }))
          );
        }
        continue;
      }

      if (Math.abs(prev.y - current.y) <= tolerance) {
        if (lengthX < minOverlap) {
          continue;
        }
        const start = Math.min(prev.x, current.x);
        const end = Math.max(prev.x, current.x);
        const segment: PolylineSegment = {
          edgeId,
          axis: 'horizontal',
          coordinate: prev.y,
          start,
          end,
          startIndex: i - 1,
          endIndex: i,
        };
        //const key = createCoordinateKey('horizontal', prev.y, tolerance);
        const group = ensureGroup(index, 'horizontal', prev.y, tolerance);
        const collection = group.segmentsByEdge.get(edgeId);
        if (collection) {
          collection.push(segment);
        } else {
          group.segmentsByEdge.set(edgeId, [segment]);
        }
      }
    }
  }

  return index;
};

const segmentsOverlap = (first: PolylineSegment, second: PolylineSegment, minOverlap: number): boolean => {
  const overlapStart = Math.max(first.start, second.start);
  const overlapEnd = Math.min(first.end, second.end);
  return overlapEnd - overlapStart >= minOverlap;
};

const collectParticipants = (group: CoordinateGroup, minOverlap: number): Set<string> => {
  /**
   * Determine which edges should be offset on the provided coordinate. Only edges
   * whose segments truly overlap along the shared axis participate in spacing.
   */
  const participants = new Set<string>();
  const entries = Array.from(group.segmentsByEdge.entries());

  for (let i = 0; i < entries.length; i++) {
    const [edgeIdA, segmentsA] = entries[i]!;
    for (let j = i + 1; j < entries.length; j++) {
      const [edgeIdB, segmentsB] = entries[j]!;
      const hasOverlap = segmentsA.some((segmentA) =>
        segmentsB.some((segmentB) => segmentsOverlap(segmentA, segmentB, minOverlap))
      );
      if (hasOverlap) {
        participants.add(edgeIdA);
        participants.add(edgeIdB);
      }
    }
  }

  return participants;
};

const computeOffsetMultiple = (index: number, count: number): number => {
  if (count === 0) {
    return 0;
  }
  const half = Math.floor(count / 2);
  if (count % 2 === 1) {
    return index - half;
  }
  if (index < half) {
    return -(half - index);
  }
  return index - half + 1;
};

const assignOffsets = (index: SegmentIndex, options: ParallelEdgeSpacingOptions): CoordinateAssignments => {
  /**
   * Attribute offset multiples (…,-1,0,+1,…) to every edge that shares an axis-aligned
   * segment with another edge. Edges are sorted for determinism so spacing remains
   * stable across renders.
   */
  const assignments: CoordinateAssignments = new Map();

  index.forEach((group, key) => {
    const participants = collectParticipants(group, options.minOverlap);
    if (participants.size < 2) {
      return;
    }

    const sortedParticipants = Array.from(participants).sort((a, b) => a.localeCompare(b));
    const offsets = new Map<string, number>();
    sortedParticipants.forEach((edgeId, participantIndex) => {
      offsets.set(edgeId, computeOffsetMultiple(participantIndex, sortedParticipants.length));
    });

    assignments.set(key, offsets);
  });

  return assignments;
};

const adjustPoint = (point: XYPosition, axis: SpacingAxis, targetCoordinate: number): XYPosition => {
  if (axis === 'vertical') {
    return { x: targetCoordinate, y: point.y };
  }
  return { x: point.x, y: targetCoordinate };
};

const applyAssignments = (
  polylines: Map<string, XYPosition[]>,
  index: SegmentIndex,
  assignments: CoordinateAssignments,
  options: ParallelEdgeSpacingOptions
): Map<string, XYPosition[]> => {
  /**
   * Rewrite the polylines using the requested offsets. A copy-on-write approach is
   * used so we only clone polylines that actually receive spacing adjustments.
   */
  if (assignments.size === 0) {
    return polylines;
  }

  const updated = new Map<string, XYPosition[]>();

  assignments.forEach((offsets, key) => {
    const group = index.get(key);
    if (!group) {
      return;
    }
    offsets.forEach((multiple, edgeId) => {
      const segments = group.segmentsByEdge.get(edgeId);
      if (!segments || segments.length === 0) {
        return;
      }

      const originalPolyline = updated.get(edgeId) ?? polylines.get(edgeId);
      if (!originalPolyline) {
        return;
      }

      const points = updated.get(edgeId) ?? originalPolyline.map((point) => ({ x: point.x, y: point.y }));
      const targetCoordinate = group.coordinate + multiple * options.spacing;
      let indexShift = 0;

      const sortedSegments = [...segments].sort(
        (a, b) => Math.min(a.startIndex, a.endIndex) - Math.min(b.startIndex, b.endIndex)
      );

      sortedSegments.forEach((segment) => {
        let from = Math.min(segment.startIndex, segment.endIndex) + indexShift;
        let to = Math.max(segment.startIndex, segment.endIndex) + indexShift;

        if (from === 0) {
          const anchor = points[0];
          if (anchor) {
            const inserted = adjustPoint(anchor, group.axis, targetCoordinate);
            points.splice(1, 0, inserted);
            indexShift += 1;
            from += 1;
            to += 1;
          }
        }

        if (to === points.length - 1) {
          const anchor = points[points.length - 1];
          if (anchor) {
            const inserted = adjustPoint(anchor, group.axis, targetCoordinate);
            points.splice(points.length - 1, 0, inserted);
            indexShift += 1;
            to = points.length - 2;
          }
        }

        for (let indexPointer = from; indexPointer <= to; indexPointer++) {
          if (indexPointer === 0 || indexPointer === points.length - 1) {
            continue;
          }
          const point = points[indexPointer];
          if (!point) {
            continue;
          }
          points[indexPointer] = adjustPoint(point, group.axis, targetCoordinate);
        }
      });

      updated.set(edgeId, points);
    });
  });

  if (updated.size === 0) {
    return polylines;
  }

  const result = new Map<string, XYPosition[]>(polylines);
  updated.forEach((points, edgeId) => {
    result.set(edgeId, points);
  });
  return result;
};

const buildSpacedPolylineMap = (
  polylines: Map<string, XYPosition[]>,
  options?: Partial<ParallelEdgeSpacingOptions>
): Map<string, XYPosition[]> => {
  /**
   * Analyse the provided polylines and apply spacing adjustments when overlapping
   * segments are detected. Returns either the untouched geometry or a new map
   * containing the offset polylines.
   */
  const mergedOptions: ParallelEdgeSpacingOptions = {
    ...DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS,
    ...options,
  };
  if (polylines.size <= 1) {
    return polylines;
  }

  if (process.env.NODE_ENV === 'test') {
    // eslint-disable-next-line no-console
    console.log(
      '[parallel-spacing] input polylines map',
      JSON.stringify(
        Array.from(polylines.entries()).map(([edgeId, points]) => ({
          edgeId,
          points: points.map((point) => ({ x: point.x, y: point.y })),
        })),
        null,
        2
      )
    );
  }

  const segmentIndex = extractSegments(polylines, mergedOptions.tolerance, mergedOptions.minOverlap);
  if (process.env.NODE_ENV === 'test') {
    // eslint-disable-next-line no-console
    console.log(
      '[parallel-spacing] index',
      Array.from(segmentIndex.entries()).map(([key, group]) => ({
        key,
        axis: group.axis,
        coordinate: group.coordinate,
        edges: Array.from(group.segmentsByEdge.keys()),
      }))
    );
  }
  const assignments = assignOffsets(segmentIndex, mergedOptions);
  if (assignments.size === 0) {
    if (process.env.NODE_ENV === 'test') {
      // eslint-disable-next-line no-console
      console.log(
        '[parallel-spacing] no assignments',
        Array.from(segmentIndex.entries()).map(([key, group]) => ({
          key,
          axis: group.axis,
          coordinate: group.coordinate,
          edges: Array.from(group.segmentsByEdge.keys()),
        }))
      );
    }
    return polylines;
  }

  const adjusted = applyAssignments(polylines, segmentIndex, assignments, mergedOptions);
  return adjusted;
};

/**
 * Convenience wrapper that runs the spacing algorithm on an entire batch of polylines. The returned
 * map only contains clones for edges that were actually offset so callers can keep reusing their
 * original geometry without extra copies.
 */
export const buildSpacedPolylines = (
  polylines: Map<string, XYPosition[]>,
  options?: Partial<ParallelEdgeSpacingOptions>
): Map<string, XYPosition[]> => buildSpacedPolylineMap(polylines, options);

/**
 * Single-edge variant of `buildSpacedPolylines`. Used by callers that only need the updated geometry
 * for one edge but still want the same spacing semantics.
 */
export const buildSpacedPolyline = (
  currentEdgeId: string,
  polylines: Map<string, XYPosition[]>,
  options?: Partial<ParallelEdgeSpacingOptions>
): XYPosition[] => {
  const spaced = buildSpacedPolylineMap(polylines, options);
  if (!spaced.has(currentEdgeId)) {
    return polylines.get(currentEdgeId) ?? [];
  }
  return spaced.get(currentEdgeId) ?? [];
};

// -----------------------------------------------------------------------------
// Straightening
// -----------------------------------------------------------------------------

/**
 * Axis orientation recognised by the straightening heuristic.
 * - `horizontal`: the edge primarily runs left/right, so we align all points to a shared Y coordinate.
 * - `vertical`: the edge primarily runs up/down, so we align all points to a shared X coordinate.
 */
type StraightenAxis = 'horizontal' | 'vertical';

/**
 * Configuration passed to the straightening routine.
 *
 * @property axis        declares whether we snap along the X or Y axis.
 * @property threshold   maximum allowed deviation (in pixels) before we give up on straightening.
 * @property sourceCount number of edges that fan out from the source port (used to keep busyness-focused priority).
 * @property targetCount number of edges that fan in to the target port.
 */
export type StraightenOptions = {
  axis: StraightenAxis;
  threshold: number;
  sourceCount: number;
  targetCount: number;
};

/**
 * Normalises a floating-point coordinate into a deterministic key so we can
 * count how often a specific offset appears.
 *
 * @param value coordinate to round.
 * @returns the rounded key.
 */
const roundKey = (value: number): number => Math.round(value * 1000);

/**
 * Produces a shallow clone of a point so callers never mutate shared references.
 *
 * @param point point to clone.
 * @returns cloned point.
 */
const clonePoint = (point: XYPosition): XYPosition => ({ x: point.x, y: point.y });

/**
 * Replaces the coordinate that is perpendicular to the main axis of travel.
 *
 * @param point      point to adjust.
 * @param axis       axis that should remain steady.
 * @param coordinate coordinate value we want to inject.
 * @returns the adjusted point.
 */
const adjustCoordinate = (point: XYPosition, axis: StraightenAxis, coordinate: number): XYPosition => {
  if (axis === 'vertical') {
    return { x: coordinate, y: point.y };
  }
  return { x: point.x, y: coordinate };
};

/**
 * Computes the sum of absolute distances between every coordinate and a target.
 * We use that to pick the cheapest snapping position.
 *
 * @param values list of coordinates extracted from the polyline.
 * @param target candidate axis coordinate.
 * @returns total Manhattan distance to the target coordinate.
 */
const sumDistance = (values: number[], target: number): number =>
  values.reduce((accumulator, value) => accumulator + Math.abs(value - target), 0);

/**
 * Straightens a nearly straight polyline by shifting every point to the same X or Y coordinate.
 *
 * @param polyline ordered list of points describing the edge.
 * @param options  tuning knobs (axis, deviation threshold, fan sizes).
 * @returns a new polyline with adjusted coordinates, or undefined when the path should be left untouched.
 */
export const straightenAlmostStraightPolyline = (
  polyline: XYPosition[],
  options: StraightenOptions
): XYPosition[] | undefined => {
  // Two-point polylines are already straight; we need at least one elbow to evaluate deviations.
  if (polyline.length < 3) {
    return undefined;
  }

  // Pull the tuning knobs out once so we can reuse them below.
  const { axis, threshold, sourceCount, targetCount } = options;
  // Decide whether we should inspect the X (vertical edge) or Y (horizontal edge) offsets.
  const perpendicularAxis = axis === 'vertical' ? 'x' : 'y';
  // Small epsilon that treats near-zero differences as zero to avoid floating point noise.
  const tolerance = 0.01;
  //TOCHECK: tolerance is hard-coded; when callers pass a threshold below 0.01 we bail out early—should this scale with the provided threshold instead?

  // Capture the sequence of X/Y coordinates that we plan to collapse.
  const coordinates: number[] = [];
  for (const point of polyline) {
    const rawCoordinate = point[perpendicularAxis];
    if (typeof rawCoordinate !== 'number' || !Number.isFinite(rawCoordinate)) {
      return undefined;
    }
    coordinates.push(rawCoordinate);
  }

  if (coordinates.length === 0) {
    return undefined;
  }

  // Measure how far the furthest points drift away from each other.
  const minCoordinate = Math.min(...coordinates);
  const maxCoordinate = Math.max(...coordinates);
  const deviation = maxCoordinate - minCoordinate;

  // Abort when the deviation is either meaningless (<= tolerance) or already too large (beyond the threshold).
  if (!Number.isFinite(deviation) || deviation <= tolerance || deviation > threshold) {
    return undefined;
  }

  // Source/target coordinates form the candidate axes we can try to snap to.
  const sourceCoordinate = coordinates[0]!;
  const targetCoordinate = coordinates[coordinates.length - 1]!;

  // Count how many bends already sit on each coordinate so we can favour the dominant column/row.
  const frequencyLookup = new Map<number, { count: number; value: number }>();
  coordinates.forEach((value) => {
    const key = roundKey(value);
    const entry = frequencyLookup.get(key);
    if (entry) {
      entry.count += 1;
    } else {
      frequencyLookup.set(key, { count: 1, value });
    }
  });

  // Track the coordinate that already hosts the largest number of points.
  let dominant = { count: 0, value: sourceCoordinate };
  frequencyLookup.forEach((entry) => {
    if (entry.count > dominant.count) {
      dominant = entry;
    }
  });
  const dominantCoordinate = dominant.value;

  // Start from the source coordinate, then let heuristics potentially swap to the target coordinate.
  let desiredCoordinate = sourceCoordinate;

  // Give priority to the least busy side so fan-outs are preserved as much as possible.
  if (sourceCount > targetCount) {
    desiredCoordinate = sourceCoordinate;
  } else if (targetCount > sourceCount) {
    desiredCoordinate = targetCoordinate;
  } else {
    // Both sides host the same number of edges; compare the total drift cost as a tie-breaker.
    const sourceCost = sumDistance(coordinates, sourceCoordinate);
    const targetCost = sumDistance(coordinates, targetCoordinate);
    if (Math.abs(targetCost - sourceCost) > tolerance) {
      desiredCoordinate = targetCost < sourceCost ? targetCoordinate : sourceCoordinate;
    } else {
      // Costs match: rely on the dominant coordinate so we favour the column/row that already has more points.
      const preferSource =
        Math.abs(dominantCoordinate - sourceCoordinate) <= Math.abs(dominantCoordinate - targetCoordinate);
      desiredCoordinate = preferSource ? sourceCoordinate : targetCoordinate;
    }
  }

  // Adjust every point; if a point sits too far away we abort to avoid wild jumps.
  const adjusted = polyline.map((point, index) => {
    const coordinate = coordinates[index] as number;
    const delta = Math.abs(coordinate - desiredCoordinate);
    if (delta > threshold) {
      return undefined;
    }
    return adjustCoordinate(point, axis, desiredCoordinate);
  });

  // Give up when any point breached the threshold check.
  if (adjusted.some((point) => point === undefined)) {
    return undefined;
  }

  // Detect whether the adjustment changed anything; if not we keep the original polyline to avoid churn.
  let mutated = false;
  for (let index = 0; index < polyline.length; index++) {
    const original = polyline[index];
    const next = adjusted[index];
    if (!original || !next) {
      continue;
    }
    if (original.x !== next.x || original.y !== next.y) {
      mutated = true;
      break;
    }
  }

  if (!mutated) {
    return undefined;
  }

  // Clone the adjusted points so callers can mutate the result safely.
  return adjusted.map((point) => clonePoint(point as XYPosition));
};
