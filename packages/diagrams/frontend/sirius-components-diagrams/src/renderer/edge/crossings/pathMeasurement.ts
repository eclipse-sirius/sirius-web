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

const SVG_NS = 'http://www.w3.org/2000/svg';

/**
 * Create a detached SVGPathElement to measure path lengths in the browser.
 *
 * @param pathDefinition - Raw `d` attribute string produced by React Flow.
 * @returns The DOM path element or null when running outside a browser (e.g., in Node/Vitest).
 */
const createSvgPath = (pathDefinition: string): SVGPathElement | null => {
  if (typeof document === 'undefined') {
    return null;
  }
  const svgPath = document.createElementNS(SVG_NS, 'path');
  svgPath.setAttribute('d', pathDefinition);
  return svgPath;
};

/**
 * Compute the total length of an SVG path using the browser's geometry API.
 *
 * @param pathDefinition - Raw `d` attribute string.
 * @returns The total length in pixels, or null when the API is unavailable/throws.
 *
 * The dash-array builder relies on this helper to translate normalized gaps into real pixels.
 * During unit tests the function is stubbed so that the rest of the logic can run in Node.
 */
export const getPathTotalLength = (pathDefinition: string): number | null => {
  if (!pathDefinition) {
    return null;
  }
  const svgPath = createSvgPath(pathDefinition);
  if (!svgPath) {
    return null;
  }
  try {
    return svgPath.getTotalLength();
  } catch (error) {
    return null;
  }
};
