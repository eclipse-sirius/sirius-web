import { XYPosition } from '@xyflow/react';

/**
 * Axis orientation recognised by the straightening heuristic.
 * - `horizontal`: the edge primarily runs left/right, so we align all points to a shared Y coordinate.
 * - `vertical`: the edge primarily runs up/down, so we align all points to a shared X coordinate.
 */
type Axis = 'horizontal' | 'vertical';

/**
 * Configuration passed to the straightening routine.
 *
 * @property axis        declares whether we snap along the X or Y axis.
 * @property threshold   maximum allowed deviation (in pixels) before we give up on straightening.
 * @property sourceCount number of edges that fan out from the source port (used to keep busyness-focused priority).
 * @property targetCount number of edges that fan in to the target port.
 */
export type StraightenOptions = {
  axis: Axis;
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
const adjustCoordinate = (point: XYPosition, axis: Axis, coordinate: number): XYPosition => {
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
