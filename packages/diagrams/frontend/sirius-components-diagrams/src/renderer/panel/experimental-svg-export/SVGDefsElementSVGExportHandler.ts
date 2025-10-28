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
import { IElementSVGExportHandler } from './SVGExportEngine.types';

/**
 * Make a deep clone of a svg defs element marked as a defs element with the attribute 'data-svg="defs"'.
 * It removes the 'style' attribute.
 */
export class SVGDefsElementSVGExportHandler implements IElementSVGExportHandler {
  canHandle(element: Element): boolean {
    return element.getAttribute('data-svg') === 'defs';
  }
  handle(element: Element, parentSvgElement: SVGElement, svgDocument: XMLDocument): SVGElement[] {
    if (element instanceof SVGDefsElement) {
      const clonedDefs = svgDocument.importNode(element, true);
      clonedDefs.removeAttribute('style');
      parentSvgElement.appendChild(clonedDefs);
    }
    return [];
  }
}
