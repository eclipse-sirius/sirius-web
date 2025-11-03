import { XYPosition } from '@xyflow/react';
import { collectPolylineRectIntersections, expandRect } from './collision';
import { Rect } from './geometry';

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
  const choice =
    pathLength(clockwise.points) <= pathLength(counterClockwise.points) ? clockwise : counterClockwise;

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
      if (latest && (latest.x !== target.x && latest.y !== target.y)) {
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

  if (
    !entryPoint ||
    !exitPoint ||
    typeof entryIndex !== 'number' ||
    typeof exitIndex !== 'number'
  ) {
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
