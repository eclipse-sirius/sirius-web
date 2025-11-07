import { describe, expect, it } from 'vitest';
import { straightenAlmostStraightPolyline } from '../postProcessing';

// Unit-level coverage for the straightening helper.
describe('straightenAlmostStraightPolyline', () => {
  // Happy-path: the helper prefers the target column because more points already sit there.
  it('snaps a nearly vertical polyline to the dominant column', () => {
    const polyline = [
      { x: 100, y: 40 },
      { x: 112, y: 80 },
      { x: 112, y: 160 },
      { x: 112, y: 200 },
    ];
    const result = straightenAlmostStraightPolyline(polyline, {
      axis: 'vertical',
      threshold: 20,
      sourceCount: 1,
      targetCount: 3,
    });
    expect(result).toBeDefined();
    expect(result?.every((point) => point.x === 112)).toBe(true);
    expect(result?.map((point) => point.y)).toEqual(polyline.map((point) => point.y));
  });

  // When the source side is busier we avoid moving it even if coordinates drift a little.
  it('prefers keeping the anchor with more competing edges', () => {
    const polyline = [
      { x: 10, y: 10 },
      { x: 10, y: 16 },
      { x: 2, y: 16 },
      { x: 2, y: 24 },
    ];
    const result = straightenAlmostStraightPolyline(polyline, {
      axis: 'horizontal',
      threshold: 20,
      sourceCount: 4,
      targetCount: 1,
    });
    expect(result).toBeDefined();
    expect(result?.every((point) => point.y === 10)).toBe(true);
  });

  // Large deviations are ignored so users can still draw intentional elbows.
  it('skips polylines that deviate beyond the threshold', () => {
    const polyline = [
      { x: 0, y: 0 },
      { x: 25, y: 0 },
      { x: 25, y: 100 },
    ];
    const result = straightenAlmostStraightPolyline(polyline, {
      axis: 'vertical',
      threshold: 20,
      sourceCount: 1,
      targetCount: 1,
    });
    expect(result).toBeUndefined();
  });
});
