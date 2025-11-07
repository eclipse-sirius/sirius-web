import { describe, expect, it } from 'vitest';
import type { XYPosition } from '@xyflow/react';
import { buildSpacedPolyline } from '../../edge/routing/postProcessing';

const toPolyline = (points: Array<[number, number]>): XYPosition[] =>
  points.map(([x, y]) => ({ x, y }));

describe('buildSpacedPolyline', () => {
  it('offsets overlapping vertical segments symmetrically', () => {
    const edgeA = 'edge-a';
    const edgeB = 'edge-b';
    const polylines = new Map<string, XYPosition[]>();
    polylines.set(
      edgeA,
      toPolyline([
        [0, 0],
        [10, 0],
        [10, 10],
        [20, 10],
      ])
    );
    polylines.set(
      edgeB,
      toPolyline([
        [0, 2],
        [10, 2],
        [10, 12],
        [20, 12],
      ])
    );

    const spacedA = buildSpacedPolyline(edgeA, polylines);
    const spacedB = buildSpacedPolyline(edgeB, polylines);

    expect(spacedA[1]?.x).toBeCloseTo(4);
    expect(spacedA[2]?.x).toBeCloseTo(4);
    expect(spacedB[1]?.x).toBeCloseTo(16);
    expect(spacedB[2]?.x).toBeCloseTo(16);
  });

  it('inserts a horizontal offset when an overlapping vertical segment touches the endpoint', () => {
    const first = 'edge-diagonal-down';
    const second = 'edge-diagonal-up';
    const polylines = new Map<string, XYPosition[]>();
    polylines.set(
      first,
      toPolyline([
        [190, 174],
        [190, 282],
        [630, 282],
        [630, 318],
      ])
    );
    polylines.set(
      second,
      toPolyline([
        [190, 318],
        [190, 217],
        [722, 217],
        [722, 136],
        [702, 136],
      ])
    );

    const spacedFirst = buildSpacedPolyline(first, polylines);
    const spacedSecond = buildSpacedPolyline(second, polylines);

    expect(spacedFirst[1]?.x).not.toBe(190);
    expect(spacedSecond[1]?.x).not.toBe(190);
    expect(spacedFirst[1]?.x).toBeLessThan(190);
    expect(spacedSecond[1]?.x).toBeGreaterThan(190);
  });

  it('does not move non-overlapping segments', () => {
    const edgeA = 'edge-a';
    const edgeB = 'edge-b';
    const polylines = new Map<string, XYPosition[]>();
    polylines.set(
      edgeA,
      toPolyline([
        [0, 0],
        [10, 0],
        [10, 10],
        [20, 10],
      ])
    );
    polylines.set(
      edgeB,
      toPolyline([
        [0, 20],
        [10, 20],
        [10, 30],
        [20, 30],
      ])
    );

    const spacedA = buildSpacedPolyline(edgeA, polylines);

    expect(spacedA[1]?.x).toBeCloseTo(10);
    expect(spacedA[2]?.x).toBeCloseTo(10);
  });

  it('returns the original polyline when only one edge is provided', () => {
    const edgeId = 'edge-single';
    const polyline = toPolyline([
      [0, 0],
      [0, 10],
      [10, 10],
    ]);
    const polylines = new Map<string, XYPosition[]>([[edgeId, polyline]]);

    const spaced = buildSpacedPolyline(edgeId, polylines);
    expect(spaced).toEqual(polyline);
  });
});
