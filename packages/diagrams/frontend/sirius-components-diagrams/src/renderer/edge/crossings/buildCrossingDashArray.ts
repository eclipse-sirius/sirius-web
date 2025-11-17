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

import { EdgeCrossingGap } from '../MultiLabelEdge.types';
import { getPathTotalLength } from './pathMeasurement';

type AbsoluteGap = { start: number; end: number };

/**
 * Force a numeric value inside the provided [min, max] interval.
 * The dash array math frequently runs at the path extremities, which means we
 * must clamp both normalized ratios and absolute coordinates to avoid negative
 * or overflowing distances when two fades overlap or when rounding produces a
 * slight overshoot.
 */
const clamp = (value: number, min: number, max: number): number => Math.max(min, Math.min(max, value));

/**
 * Merge a set of absolute gaps expressed in pixels along the path.
 *
 * @param gaps - Unsorted array of [start, end] intervals measured along the SVG path.
 * @returns A sorted/merged array where overlapping windows were fused into longer ones.
 *
 * React Flow consumes stroke dashes as alternating segment lengths (draw / gap).
 * When two crossing windows overlap, we would get zero-length entries unless we
 * merge them beforehand, so this helper keeps the dash array clean.
 */
const mergeGaps = (gaps: AbsoluteGap[]): AbsoluteGap[] => {
  if (gaps.length <= 1) {
    return gaps;
  }
  const sorted = [...gaps].sort((a, b) => a.start - b.start);
  const merged: AbsoluteGap[] = [];
  sorted.forEach((gap) => {
    const last = merged[merged.length - 1];
    if (!last) {
      merged.push(gap);
      return;
    }
    if (gap.start <= last.end) {
      last.end = Math.max(last.end, gap.end);
    } else {
      merged.push(gap);
    }
  });
  return merged;
};

/**
 * Convert normalized crossing windows into an SVG `stroke-dasharray` string.
 *
 * @param pathDefinition - The SVG path string for the edge being rendered.
 * @param crossingGaps - Normalized (0..1) windows that represent the fade spans along the path.
 * @returns A space-separated strokeDasharray string or null when no windows should be applied.
 *
 * The renderer feeds this helper every time new `crossingGaps` are injected into an edge.
 * The dash array alternates between “drawn length” and “gap length”. When we start with a
 * gap at the beginning of the path, the array must start with `0` so that React Flow skips
 * directly to the first gap. Each value is rounded for deterministic renders.
 */
export const buildCrossingDashArray = (
  pathDefinition: string,
  crossingGaps: EdgeCrossingGap[] | undefined
): string | null => {
  if (!pathDefinition || !crossingGaps || crossingGaps.length === 0) {
    return null;
  }
  const pathLength = getPathTotalLength(pathDefinition);
  if (!pathLength || pathLength <= 0) {
    return null;
  }

  // Translate normalized ratios to absolute distances along the actual path length.
  // This keeps dashes accurate even when the renderer later applies transforms.
  const absoluteGaps: AbsoluteGap[] = crossingGaps
    .map(({ startRatio, endRatio }) => {
      const start = clamp(startRatio, 0, 1) * pathLength;
      const end = clamp(endRatio, 0, 1) * pathLength;
      if (end - start <= 0) {
        return null;
      }
      return { start, end };
    })
    .filter((gap): gap is AbsoluteGap => !!gap);

  if (!absoluteGaps.length) {
    return null;
  }

  // Merge partially overlapping windows before building the alternating dash segments.
  const mergedGaps = mergeGaps(
    absoluteGaps.map((gap) => ({
      start: clamp(gap.start, 0, pathLength),
      end: clamp(gap.end, 0, pathLength),
    }))
  );

  let cursor = 0;
  const dashEntries: number[] = [];

  const firstGap = mergedGaps[0];
  if (firstGap && firstGap.start <= 0) {
    dashEntries.push(0);
  }

  // Build the alternating draw/gap pattern React Flow expects. Every iteration pushes:
  // 1. The length of the painted segment preceding the gap (if any)
  // 2. The length of the gap itself
  mergedGaps.forEach((gap) => {
    const start = clamp(gap.start, cursor, pathLength);
    if (start > cursor) {
      dashEntries.push(start - cursor);
    }
    const end = clamp(gap.end, start, pathLength);
    const windowLength = end - start;
    if (windowLength > 0) {
      dashEntries.push(windowLength);
    }
    cursor = end;
  });

  if (cursor < pathLength) {
    dashEntries.push(pathLength - cursor);
  }

  // Remove zero-length entries (except the optional first 0) and clamp to 2 decimals
  // to guarantee deterministic props for React memoization/rendering.
  const sanitizedEntries = dashEntries
    .map((value, index) => {
      if (index === 0 || value > 0) {
        return Number(value.toFixed(2));
      }
      return null;
    })
    .filter((value): value is number => value !== null);

  if (sanitizedEntries.length <= 1) {
    return null;
  }

  return sanitizedEntries.join(' ');
};
