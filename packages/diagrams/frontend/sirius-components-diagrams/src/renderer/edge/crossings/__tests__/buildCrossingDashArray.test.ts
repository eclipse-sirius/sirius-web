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

import { afterEach, describe, it, assert, vi } from 'vitest';
import { buildCrossingDashArray } from '../buildCrossingDashArray';

// The dash builder relies on the DOM-only getTotalLength API, so the tests stub it.
let mockPathLength: number | null = 100;

vi.mock('../pathMeasurement', () => ({
  getPathTotalLength: vi.fn(() => mockPathLength),
}));

describe('buildCrossingDashArray', () => {
  afterEach(() => {
    // Reset the mock length after every test so each scenario stays isolated.
    mockPathLength = 100;
  });

  it('merges windows, clamps ratios, and produces a deterministic dash pattern', () => {
    // Arrange a set of normalized windows that overlap, exceed the path bounds, and start before 0.
    const dashArray = buildCrossingDashArray('M 0 0 L 100 0', [
      { startRatio: 0.1, endRatio: 0.2 },
      { startRatio: 0.2, endRatio: 0.25 },
      { startRatio: 0.8, endRatio: 1.1 },
      { startRatio: -0.1, endRatio: 0.05 },
    ]);

    // Assert that the merged gaps collapse into one dash array with deterministic spacing.
    assert.strictEqual(
      dashArray,
      '0 5 5 15 55 20',
      'should convert the normalized windows into a merged strokeDasharray string'
    );
  });

  it('returns null when the path cannot be measured or when no valid windows remain', () => {
    // Arrange by simulating a DOM failure that prevents measuring the path length.
    mockPathLength = null;
    const invalidMeasurement = buildCrossingDashArray('M 0 0 L 10 0', [{ startRatio: 0.2, endRatio: 0.3 }]);

    // Assert that the dash builder bails out when no measurement is available.
    assert.isNull(invalidMeasurement, 'should return null when getTotalLength fails');

    // Arrange another call where the windows collapse to zero length.
    mockPathLength = 100;
    const noWindows = buildCrossingDashArray('M 0 0 L 100 0', [{ startRatio: 0.4, endRatio: 0.4 }]);

    // Assert the guard rail for empty/invalid gaps.
    assert.isNull(noWindows, 'should return null when all provided windows collapse to zero length');
  });
});
