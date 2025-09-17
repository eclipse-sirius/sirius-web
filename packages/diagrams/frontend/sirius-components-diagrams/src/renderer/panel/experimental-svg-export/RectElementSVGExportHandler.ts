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

      const borders: SVGElement[] = [];
      if (style.borderWidth.includes('0px')) {
        // The rectangle is not complete, thus we have to handle each side with an svg path
        if (style.borderTopWidth !== '0px') {
          const topBorder: SVGPathElement = svgDocument.createElementNS(svgNamespace, 'path');
          topBorder.setAttribute('d', `M${bounds.left} ${bounds.top} h ${bounds.width}`);
          topBorder.setAttribute('fill', 'none');
          topBorder.setAttribute('stroke', style.borderTopColor);
          topBorder.setAttribute('stroke-width', style.borderTopWidth);
          this.setBorderStyle(topBorder, style.borderTopStyle);
          borders.push(topBorder);
        }
        if (style.borderBottomWidth !== '0px') {
          const bottomBorder: SVGPathElement = svgDocument.createElementNS(svgNamespace, 'path');
          bottomBorder.setAttribute('d', `M${bounds.left} ${bounds.bottom} h ${bounds.width}`);
          bottomBorder.setAttribute('fill', 'none');
          bottomBorder.setAttribute('stroke', style.borderBottomColor);
          bottomBorder.setAttribute('stroke-width', style.borderBottomWidth);
          this.setBorderStyle(bottomBorder, style.borderBottomStyle);
          borders.push(bottomBorder);
        }
        if (style.borderLeftWidth !== '0px') {
          const leftBorder: SVGPathElement = svgDocument.createElementNS(svgNamespace, 'path');
          leftBorder.setAttribute('d', `M${bounds.left} ${bounds.top} v ${bounds.height}`);
          leftBorder.setAttribute('fill', 'none');
          leftBorder.setAttribute('stroke', style.borderLeftColor);
          leftBorder.setAttribute('stroke-width', style.borderLeftWidth);
          this.setBorderStyle(leftBorder, style.borderLeftStyle);
          borders.push(leftBorder);
        }
        if (style.borderRightWidth !== '0px') {
          const rightBorder: SVGPathElement = svgDocument.createElementNS(svgNamespace, 'path');
          rightBorder.setAttribute('d', `M${bounds.right} ${bounds.top} v ${bounds.height}`);
          rightBorder.setAttribute('fill', 'none');
          rightBorder.setAttribute('stroke', style.borderRightColor);
          rightBorder.setAttribute('stroke-width', style.borderRightWidth);
          this.setBorderStyle(rightBorder, style.borderRightStyle);
          borders.push(rightBorder);
        }
      } else {
        rect.setAttribute('stroke', style.borderColor);
        rect.setAttribute('stroke-width', style.borderWidth);
        this.setBorderStyle(rect, style.borderStyle);
      }

      rect.setAttribute('width', String(bounds.width));
      rect.setAttribute('height', String(bounds.height));
      rect.setAttribute('x', String(bounds.left));
      rect.setAttribute('y', String(bounds.top));
      rect.setAttribute('rx', String(style.borderRadius));
      rect.setAttribute('fill', style.backgroundColor);

      if (style.backgroundColor === 'rgba(0, 0, 0, 0)') {
        /*
         * When the background is 'transparent', backgroundColor=rgba(0, 0, 0, 0) which is not supported by all svg reader.
         * In that case add fill opacity.
         * See: https://www.w3.org/TR/css-color-3/#transparent
         */
        rect.setAttribute('fill-opacity', '0');
      }

      if (style.opacity) {
        rect.setAttribute('opacity', style.opacity);
      }

      parentSvgElement.appendChild(rect);
      parentSvgElement.append(...borders);
    }
    return elementToAddAtTheEnd;
  }

  private setBorderStyle(svgElement: SVGRectElement | SVGPathElement, borderStyle: string): void {
    switch (borderStyle) {
      case 'dotted':
        svgElement.setAttribute('stroke-dasharray', '0.5');
        break;
      case 'dashed':
        svgElement.setAttribute('stroke-dasharray', '2, 1');
        break;
      default:
        break;
    }
  }
}
