import { XYPosition } from '@xyflow/react';
import { Rect } from './geometry';

const EPSILON = 0.0001;

type SegmentIntersection = {
  point: XYPosition;
  t: number;
};

const rectToBounds = (rect: Rect): { left: number; right: number; top: number; bottom: number } => ({
  left: rect.x,
  right: rect.x + rect.width,
  top: rect.y,
  bottom: rect.y + rect.height,
});

const pointInRect = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): boolean =>
  point.x > bounds.left + EPSILON &&
  point.x < bounds.right - EPSILON &&
  point.y > bounds.top + EPSILON &&
  point.y < bounds.bottom - EPSILON;

const segmentIntersectionWithParam = (
  a1: XYPosition,
  a2: XYPosition,
  b1: XYPosition,
  b2: XYPosition
): SegmentIntersection | undefined => {
  const denominator = (a1.x - a2.x) * (b1.y - b2.y) - (a1.y - a2.y) * (b1.x - b2.x);
  if (Math.abs(denominator) <= EPSILON) {
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
  if (!withinSegment(candidate, a1, a2) || !withinSegment(candidate, b1, b2)) {
    return undefined;
  }

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

const collectSegmentBoundaryPoints = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): SegmentIntersection[] => {
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
      return;
    }
    deduped.push(candidate);
  });
  return deduped;
};

const segmentOverlapsRect = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): boolean => {
  if (pointInRect(start, bounds) || pointInRect(end, bounds)) {
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

const getEntryExitForSegment = (
  start: XYPosition,
  end: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  insideStart: boolean,
  insideEnd: boolean
): { entryPoint?: XYPosition; exitPoint?: XYPosition } => {
  const intersections = collectSegmentBoundaryPoints(start, end, bounds);
  if (insideStart && insideEnd) {
    return { entryPoint: { ...start }, exitPoint: { ...end } };
  }

  if (insideStart && !insideEnd) {
    const exitPoint = intersections[0]?.point ?? { ...end };
    return { entryPoint: { ...start }, exitPoint };
  }

  if (!insideStart && insideEnd) {
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
      continue;
    }

    const insideStart = pointInRect(start, bounds);
    const insideEnd = pointInRect(end, bounds);
    const { entryPoint, exitPoint } = getEntryExitForSegment(start, end, bounds, insideStart, insideEnd);

    if (!inside) {
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
          collisions.push({
            entryPoint: pendingEntryPoint,
            exitPoint: finalExitPoint,
            entryIndex: pendingEntryIndex ?? index,
            exitIndex: index,
          });
        }
      }
      pendingEntryPoint = insideEnd && !isLastSegment ? { ...end } : undefined;
      pendingEntryIndex = insideEnd && !isLastSegment ? index : undefined;
      inside = insideEnd;
    }
  }

  return collisions;
};

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

export const expandRect = (rect: Rect, padding: number): Rect => ({
  x: rect.x - padding,
  y: rect.y - padding,
  width: rect.width + padding * 2,
  height: rect.height + padding * 2,
});
