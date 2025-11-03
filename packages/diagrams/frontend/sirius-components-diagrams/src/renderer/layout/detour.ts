import { XYPosition } from '@xyflow/react';
import { collectPolylineRectIntersections, expandRect } from './collision';
import { Rect } from './geometry';

type DetourSide = 'top' | 'right' | 'bottom' | 'left';

const edgeCount = 4;

const perimeterVertices = (rect: Rect): XYPosition[] => [
  { x: rect.x, y: rect.y },
  { x: rect.x + rect.width, y: rect.y },
  { x: rect.x + rect.width, y: rect.y + rect.height },
  { x: rect.x, y: rect.y + rect.height },
];

const sideForEdge = (edgeIndex: number): DetourSide => {
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

const almostEqual = (a: number, b: number, epsilon = 0.5): boolean => Math.abs(a - b) <= epsilon;

const edgeContainsPoint = (start: XYPosition, end: XYPosition, point: XYPosition): boolean =>
  (almostEqual(start.x, end.x) &&
    almostEqual(point.x, start.x) &&
    point.y >= Math.min(start.y, end.y) - 0.5 &&
    point.y <= Math.max(start.y, end.y) + 0.5) ||
  (almostEqual(start.y, end.y) &&
    almostEqual(point.y, start.y) &&
    point.x >= Math.min(start.x, end.x) - 0.5 &&
    point.x <= Math.max(start.x, end.x) + 0.5);

const edgeIndexForPoint = (rect: Rect, point: XYPosition): number => {
  const vertices = perimeterVertices(rect);
  for (let i = 0; i < edgeCount; i++) {
    const start = vertices[i];
    const end = vertices[(i + 1) % edgeCount];
    if (!start || !end) {
      continue;
    }
    if (edgeContainsPoint(start, end, point)) {
      return i;
    }
  }
  return 0;
};

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
    distance += Math.hypot(current.x - prev.x, current.y - prev.y);
  }
  return distance;
};

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
    if (!previous || previous.x !== current.x || previous.y !== current.y) {
      result.push(current);
    }
  }
  return result;
};

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
    sides.push(sideForEdge(currentEdge));
    if (currentEdge === exitEdge) {
      points.push(exitPoint);
      break;
    }

    const nextCornerIndex = direction === 1 ? (currentEdge + 1) % edgeCount : currentEdge;
    const nextCorner = vertices[nextCornerIndex];
    if (nextCorner) {
      points.push(nextCorner);
    }
    currentEdge = (currentEdge + direction + edgeCount) % edgeCount;
    guard++;
  }

  return { points: dedupeSequentialPoints(points), sides };
};

const selectShortestPerimeter = (
  rect: Rect,
  entryPoint: XYPosition,
  exitPoint: XYPosition,
  entryEdge: number,
  exitEdge: number
): { points: XYPosition[]; sideSequence: DetourSide[] } => {
  const clockwise = buildPerimeterTrace(rect, entryPoint, exitPoint, entryEdge, exitEdge, 1);
  const counterClockwise = buildPerimeterTrace(rect, entryPoint, exitPoint, entryEdge, exitEdge, -1);

  const choice =
    pathLength(clockwise.points) <= pathLength(counterClockwise.points) ? clockwise : counterClockwise;

  return { points: choice.points, sideSequence: choice.sides.slice(0, Math.max(choice.points.length - 1, 0)) };
};

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
      rectified.push(target);
      continue;
    }

    const penultimate = rectified.length >= 2 ? rectified[rectified.length - 2] : undefined;
    const cameFromHorizontal = !!penultimate && penultimate.y === previous.y && penultimate.x !== previous.x;
    const cameFromVertical = !!penultimate && penultimate.x === previous.x && penultimate.y !== previous.y;

    let bridge: XYPosition | undefined;
    if (cameFromHorizontal && !cameFromVertical) {
      bridge = { x: target.x, y: previous.y };
    } else if (cameFromVertical && !cameFromHorizontal) {
      bridge = { x: previous.x, y: target.y };
    } else {
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

const mergePolyline = (
  original: XYPosition[],
  entryIndex: number,
  exitIndex: number,
  detour: XYPosition[]
): XYPosition[] => {
  const prefix = original.slice(0, entryIndex + 1);
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
    return point.x !== previous.x || point.y !== previous.y;
  });

  if (cleanedDetour.length === 0) {
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

export const buildDetourAroundRectangle = (
  polyline: XYPosition[],
  obstacle: Rect,
  options: DetourOptions,
  context?: DetourContext
): XYPosition[] | null => {
  if (polyline.length < 2) {
    return null;
  }

  const paddedRect = expandRect(obstacle, options.baseGap);

  let entryPoint = context?.entryPoint;
  let exitPoint = context?.exitPoint;
  let entryIndex = context?.entryIndex;
  let exitIndex = context?.exitIndex;

  if (!context) {
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
