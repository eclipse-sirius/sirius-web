import { XYPosition } from '@xyflow/react';

/**
 * Detects long overlapping segments between rectilinear edge polylines and nudges
 * them apart so parallel edges remain legible. The algorithm groups segments by
 * shared coordinates, assigns offset multiples, and rewrites the polylines with
 * the requested spacing.
 */
type Axis = 'horizontal' | 'vertical';

type PolylineSegment = {
  edgeId: string;
  axis: Axis;
  coordinate: number;
  start: number;
  end: number;
  startIndex: number;
  endIndex: number;
};

type CoordinateGroup = {
  axis: Axis;
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

const createCoordinateKey = (axis: Axis, coordinate: number, tolerance: number): string => {
  const rounded = Math.round(coordinate / tolerance) * tolerance;
  return `${axis}${AXIS_KEY_SEPARATOR}${rounded}`;
};

/**
 * Retrieve (or lazily create) the bucket describing all segments aligned on a
 * given coordinate. The tolerance keeps near-identical floats in the same bin.
 */
const ensureGroup = (index: SegmentIndex, axis: Axis, coordinate: number, tolerance: number): CoordinateGroup => {
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

const adjustPoint = (point: XYPosition, axis: Axis, targetCoordinate: number): XYPosition => {
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

export const buildSpacedPolyline = (
  currentEdgeId: string,
  polylines: Map<string, XYPosition[]>,
  options?: Partial<ParallelEdgeSpacingOptions>
): XYPosition[] => {
  /**
   * Entry point used by the edge renderer. It analyses the current edge alongside
   * its neighbours and returns a spaced version of the polyline when overlaps are
   * detected. Returns the untouched geometry when no spacing is needed.
   */
  const mergedOptions: ParallelEdgeSpacingOptions = {
    ...DEFAULT_PARALLEL_EDGE_SPACING_OPTIONS,
    ...options,
  };

  if (!polylines.has(currentEdgeId) || polylines.size <= 1) {
    return polylines.get(currentEdgeId) ?? [];
  }

  if (process.env.NODE_ENV === 'test') {
    // eslint-disable-next-line no-console
    console.log(
      '[parallel-spacing] input polylines',
      currentEdgeId,
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
      currentEdgeId,
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
    return polylines.get(currentEdgeId) ?? [];
  }

  const adjusted = applyAssignments(polylines, segmentIndex, assignments, mergedOptions);
  return adjusted.get(currentEdgeId) ?? polylines.get(currentEdgeId) ?? [];
};
