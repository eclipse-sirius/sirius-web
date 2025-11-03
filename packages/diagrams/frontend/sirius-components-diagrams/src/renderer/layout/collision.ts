import { XYPosition } from '@xyflow/react';
import { Rect } from './geometry';

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
  // Enumerate the four rectangle edges in clockwise order using their endpoints.
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

  const intersections = edges
    .map(([edgeStart, edgeEnd]) => segmentIntersectionWithParam(start, end, edgeStart, edgeEnd))
    .filter((candidate): candidate is SegmentIntersection => !!candidate)
    .sort((first, second) => first.t - second.t);

  const deduped: SegmentIntersection[] = [];
  intersections.forEach((candidate) => {
    const last = deduped[deduped.length - 1];
    if (!last) {
      deduped.push(candidate);
      return;
    }
    if (Math.abs(last.t - candidate.t) <= EPSILON) {
      // Skip duplicates caused by the segment touching a rectangle corner.
      return;
    }
    deduped.push(candidate);
  });
  return deduped;
};

/**
 * Determine whether a segment overlaps a rectangle (by either endpoint or intersection).
 * @param start - Segment start point.
 * @param end - Segment end point.
 * @param bounds - Rectangle bounds.
 * @returns True when the segment touches or crosses the rectangle interior.
 */
const segmentOverlapsRect = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): boolean => {
  if (pointInRect(start, bounds) || pointInRect(end, bounds)) {
    // Quick exit if either endpoint lies inside the rectangle.
    return true;
  }
  const intersections = collectSegmentBoundaryPoints(start, end, bounds);
  return intersections.length > 0;
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
  insideEnd: boolean
): { entryPoint?: XYPosition; exitPoint?: XYPosition } => {
  const intersections = collectSegmentBoundaryPoints(start, end, bounds);
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
export const collectPolylineRectCollisions = (
  points: XYPosition[],
  rect: Rect
): PolylineRectCollision[] => {
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

    const overlaps = segmentOverlapsRect(start, end, bounds);
    if (!overlaps) {
      // Nothing to do if this segment does not touch the rectangle.
      continue;
    }

    const insideStart = pointInRect(start, bounds);
    const insideEnd = pointInRect(end, bounds);
    const { entryPoint, exitPoint } = getEntryExitForSegment(start, end, bounds, insideStart, insideEnd);

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
