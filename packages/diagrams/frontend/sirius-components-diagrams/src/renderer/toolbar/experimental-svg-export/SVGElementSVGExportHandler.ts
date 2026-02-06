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
import { IElementSVGExportHandler } from './SVGExportEngine.types';

/**
 * Make a deep clone of a svg element marked as a svg element with the attribute 'data-svg="svg"'.
 * It removes the 'viewbox' and 'style' attributes.
 */
export class SVGElementSVGExportHandler implements IElementSVGExportHandler {
  canHandle(element: Element): boolean {
    return element.getAttribute('data-svg') === 'svg';
  }
  handle(element: Element, parentSvgElement: SVGElement, svgDocument: XMLDocument): SVGElement[] {
    if (element instanceof SVGSVGElement) {
      const bounds = element.getBoundingClientRect();
      const svgNode = svgDocument.importNode(element, true);
      svgNode.removeAttribute('viewBox');
      svgNode.removeAttribute('data-svg');
      svgNode.removeAttribute('style');
      svgNode.setAttribute('x', String(bounds.left));
      svgNode.setAttribute('y', String(bounds.top));
      parentSvgElement.appendChild(svgNode);
    }
    return [];
  }
}
