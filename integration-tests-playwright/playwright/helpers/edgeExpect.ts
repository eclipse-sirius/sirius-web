/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import { expect } from '@playwright/test';

const extractPoints = (path: string) => {
  const points: { x: number; y: number }[] = [];
  const regex = /[ML]\s*(-?\d+\.?\d*)\s*(-?\d+\.?\d*)/g;
  let match;
  while ((match = regex.exec(path)) !== null) {
    points.push({ x: parseFloat(match[1]), y: parseFloat(match[2]) });
  }
  return points;
};

export const edgeExpect = expect.extend({
  async toHaveSamePath(received: string | null, expected: string | null, options?: { errorMargin?: number }) {
    const assertionName = 'toHaveSamePath';
    let pass = false;
    const { errorMargin = 1 } = options ?? { errorMargin: 1 };
    if (received && expected) {
      const receivedPoints = extractPoints(received);
      const expectedPoints = extractPoints(expected);
      if (expectedPoints.length === receivedPoints.length) {
        const firstExpectedPoint = expectedPoints[0];
        const firstReceivedPoint = receivedPoints[0];
        if (firstExpectedPoint && firstReceivedPoint) {
          const normalizedExpectedPoints = expectedPoints.map((point) => ({
            x: point.x - firstExpectedPoint.x,
            y: point.y - firstExpectedPoint.y,
          }));
          const normalizedReceivedPoints = receivedPoints.map((point) => ({
            x: point.x - firstReceivedPoint.x,
            y: point.y - firstReceivedPoint.y,
          }));
          const deltasX: number[] = [];
          const deltasY: number[] = [];
          for (let i = 0; i < normalizedExpectedPoints.length; i++) {
            const normalizedExpectedPoint = normalizedExpectedPoints[i];
            const normalizedReceivedPoint = normalizedReceivedPoints[i];
            if (normalizedExpectedPoint && normalizedReceivedPoint) {
              const dx = normalizedReceivedPoint.x - normalizedExpectedPoint.x;
              const dy = normalizedReceivedPoint.y - normalizedExpectedPoint.y;
              deltasX.push(dx);
              deltasY.push(dy);
            } else {
              deltasY.push(errorMargin + 1); //Force to fail the next check
            }
          }
          const isDxUniform = deltasX.every((dx) => dx <= errorMargin);
          const isDyUniform = deltasY.every((dy) => dy <= errorMargin);

          pass = isDxUniform && isDyUniform;
        }
      }
    }
    return {
      message: () => `Expected ${assertionName} to pass with expected path ${expected} but received ${received}`,
      pass,
      name: assertionName,
      expected,
    };
  },
});
