/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { type Locator, type Page, expect } from '@playwright/test';

export class PlaywrightEdge {
  readonly page: Page;
  readonly edgeLocator: Locator;

  constructor(page: Page, index = 0) {
    this.page = page;
    this.edgeLocator = page.locator('[data-testid^="rf__edge-"]').nth(index);
  }

  async click() {
    await this.edgeLocator.click();
  }

  async controlClick() {
    await this.edgeLocator.click({ modifiers: ['ControlOrMeta'] });
  }

  async isSelected() {
    await expect(this.edgeLocator).toHaveClass(/selected/);
  }

  async getEdgePath() {
    return await this.edgeLocator.locator('path').first().getAttribute('d');
  }

  async getEdgeStyle() {
    return await this.edgeLocator.locator('.react-flow__edge-path').first();
  }

  async openPalette() {
    await this.edgeLocator.click({ button: 'right' });
  }

  async closePalette() {
    await this.page.getByTestId('Close-palette').click();
  }

  async getEdgeColor() {
    const path = this.edgeLocator.locator('path').first();
    return await path.evaluate((el) => {
      return window.getComputedStyle(el).getPropertyValue('stroke');
    });
  }
}

const extractPoints = (path: string) => {
  const points: { x: number; y: number }[] = [];
  const regex = /[ML]\s*(-?\d+\.?\d*)\s*(-?\d+\.?\d*)/g;
  let match;
  while ((match = regex.exec(path)) !== null) {
    points.push({ x: parseFloat(match[1]), y: parseFloat(match[2]) });
  }
  return points;
};

export const checkPathUniformOffsets = (expectedPath: string | null, receivedPath: string | null, errorMargin = 1) => {
  if (!expectedPath || !receivedPath) {
    return false;
  }
  const expectedPoints = extractPoints(expectedPath);
  const receivedPoints = extractPoints(receivedPath);
  if (expectedPoints.length !== receivedPoints.length) {
    return false;
  }

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

    return isDxUniform && isDyUniform;
  }
  return false;
};
