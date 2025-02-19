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
import { IElementSVGExportHandler, svgNamespace } from './SVGExportEngine.types';

/**
 * Convert a dom element marked as a rectangle with the attribute 'data-svg="rect"' into a svg rect element.
 * If such element has borders on some sides (not all at the same time), it creates another svg rect element for each side the element has a border.
 * The svg rect elements will be put in the svg at the end to be above all other element, and thus, be visible.
 */
export class RectElementSVGExportHandler implements IElementSVGExportHandler {
  canHandle(element: Element): boolean {
    return element.getAttribute('data-svg') === 'rect';
  }

  handle(element: Element, parentSvgElement: SVGElement, svgDocument: XMLDocument): SVGElement[] {
    const elementToAddAtTheEnd: SVGElement[] = [];
    if (element instanceof HTMLElement) {
      const rect: SVGRectElement = svgDocument.createElementNS(svgNamespace, 'rect');
      const bounds = element.getBoundingClientRect();
      const style = window.getComputedStyle(element);
      rect.setAttribute('width', String(bounds.width));
      rect.setAttribute('height', String(bounds.height));
      rect.setAttribute('x', String(bounds.left));
      rect.setAttribute('y', String(bounds.top));
      rect.setAttribute('rx', String(style.borderRadius));
      rect.setAttribute('fill', style.backgroundColor);

      if (style.borderWidth.includes('0px')) {
        if (style.borderTopWidth !== '0px') {
          const topBorder: SVGRectElement = svgDocument.createElementNS(svgNamespace, 'rect');
          const width = bounds.width + parseFloat(style.borderTopWidth);
          topBorder.setAttribute('width', String(width));
          // 0.1 is to tell svg there is something to display
          topBorder.setAttribute('height', '0.1');
          topBorder.setAttribute('x', String(bounds.left));
          topBorder.setAttribute('y', String(bounds.top));
          topBorder.setAttribute('stroke', style.borderTopColor);
          topBorder.setAttribute('stroke-width', style.borderTopWidth);
          elementToAddAtTheEnd.push(topBorder);
        }
        if (style.borderBottomWidth !== '0px') {
          const bottomBorder: SVGRectElement = svgDocument.createElementNS(svgNamespace, 'rect');
          const width = bounds.width + parseFloat(style.borderBottomWidth);
          bottomBorder.setAttribute('width', String(width));
          // 0.1 is to tell svg there is something to display
          bottomBorder.setAttribute('height', '0.1');
          bottomBorder.setAttribute('x', String(bounds.left));
          bottomBorder.setAttribute('y', String(bounds.bottom));
          bottomBorder.setAttribute('stroke', style.borderBottomColor);
          bottomBorder.setAttribute('stroke-width', style.borderBottomWidth);
          elementToAddAtTheEnd.push(bottomBorder);
        }
        if (style.borderLeftWidth !== '0px') {
          const leftBorder: SVGRectElement = svgDocument.createElementNS(svgNamespace, 'rect');
          const height = bounds.height + parseFloat(style.borderLeftWidth);
          // 0.1 is to tell svg there is something to display
          leftBorder.setAttribute('width', '0.1');
          leftBorder.setAttribute('height', String(height));
          leftBorder.setAttribute('x', String(bounds.left));
          leftBorder.setAttribute('y', String(bounds.top));
          leftBorder.setAttribute('stroke', style.borderLeftColor);
          leftBorder.setAttribute('stroke-width', style.borderLeftWidth);
          elementToAddAtTheEnd.push(leftBorder);
        }
        if (style.borderRightWidth !== '0px') {
          const rightBorder: SVGRectElement = svgDocument.createElementNS(svgNamespace, 'rect');
          const height = bounds.height + parseFloat(style.borderRightWidth);
          // 0.1 is to tell svg there is something to display
          rightBorder.setAttribute('width', '0.1');
          rightBorder.setAttribute('height', String(height));
          rightBorder.setAttribute('x', String(bounds.right));
          rightBorder.setAttribute('y', String(bounds.top));
          rightBorder.setAttribute('stroke', style.borderRightColor);
          rightBorder.setAttribute('stroke-width', style.borderRightWidth);
          elementToAddAtTheEnd.push(rightBorder);
        }
      } else {
        rect.setAttribute('stroke', style.borderColor);
        rect.setAttribute('stroke-width', style.borderWidth);
      }

      parentSvgElement.appendChild(rect);
    }
    return elementToAddAtTheEnd;
  }
}
