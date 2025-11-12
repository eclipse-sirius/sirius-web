import { describe, expect, it } from 'vitest';
import { simplifyRectilinearBends } from '../../ExperimentalEdgeWrapper';

describe('simplifyRectilinearBends', () => {
  it('removes horizontal notch excursions that keep heading in the same direction', () => {
    const source = { x: 469.9, y: 567.8 };
    const target = { x: 226.9, y: 603.9 };
    const bends = [
      { x: 469.9, y: 595.9 },
      { x: 348.4, y: 595.9 },
      { x: 348.4, y: 583.9 },
      { x: 226.9, y: 583.9 },
    ];

    const simplified = simplifyRectilinearBends(bends, source, target);

    expect(simplified, 'Horizontal notch should collapse into a single westbound column').toEqual([
      { x: 469.9, y: 595.9 },
      { x: 226.9, y: 595.9 },
    ]);
  });

  it('removes vertical notch excursions with parallel legs', () => {
    const source = { x: 0, y: 0 };
    const target = { x: 90, y: 160 };
    const bends = [
      { x: 0, y: 80 },
      { x: 50, y: 80 },
      { x: 50, y: 110 },
      { x: 70, y: 110 },
      { x: 70, y: 130 },
      { x: 90, y: 130 },
    ];

    const simplified = simplifyRectilinearBends(bends, source, target);

    expect(simplified, 'Vertical notch should collapse while preserving the downstream run to the target').toEqual([
      { x: 0, y: 80 },
      { x: 50, y: 80 },
      { x: 50, y: 130 },
      { x: 90, y: 130 },
    ]);
  });
});
