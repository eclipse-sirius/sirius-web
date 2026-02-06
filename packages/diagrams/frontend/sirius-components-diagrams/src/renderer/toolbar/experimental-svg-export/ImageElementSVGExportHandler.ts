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
import { IElementSVGExportHandler, svgNamespace } from './SVGExportEngine.types';

/**
 * Convert a dom element marked as an image with the attribute 'data-svg="image"' into a svg image element.
 * It handles the css background image property using the css `url` function.
 */
export class ImageElementSVGExportHandler implements IElementSVGExportHandler {
  canHandle(element: Element): boolean {
    return element.getAttribute('data-svg') === 'image';
  }
  handle(element: Element, parentSvgElement: SVGElement, svgDocument: XMLDocument): SVGElement[] {
    if (element instanceof HTMLElement) {
      const image: SVGImageElement = svgDocument.createElementNS(svgNamespace, 'image');
      const bounds = element.getBoundingClientRect();
      const style = window.getComputedStyle(element);
      image.setAttribute('width', String(bounds.width));
      image.setAttribute('height', String(bounds.height));
      image.setAttribute('x', String(bounds.left));
      image.setAttribute('y', String(bounds.top));
      if (style.opacity) {
        image.setAttribute('opacity', style.opacity);
      }
      if (this.canHandleUrlBackgroundImage(style)) {
        // It removes {url("} at start and {")} at the end
        const imageContent = style.backgroundImage.substring(5, style.backgroundImage.length - 2);
        image.setAttribute('href', imageContent);
        image.setAttribute('preserveAspectRatio', 'xMaxYMax');
        // Prevent image background corner to extends beyond the node border.
        image.setAttribute('clip-path', `inset(0 0 round ${style.borderRadius})`);
        parentSvgElement.appendChild(image);
      }
    }
    return [];
  }

  private canHandleUrlBackgroundImage(style: CSSStyleDeclaration): boolean {
    return style.backgroundImage.startsWith('url("');
  }
}
