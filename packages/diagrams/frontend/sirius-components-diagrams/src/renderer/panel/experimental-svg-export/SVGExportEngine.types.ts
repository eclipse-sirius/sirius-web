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

export const svgNamespace = 'http://www.w3.org/2000/svg';

export interface SvgExportResult {
  svg: SVGSVGElement;
  svgDocument: XMLDocument;
}

export interface ISVGExportEngine {
  registerElementSVGExportHandlerContribution(elementSVGExportHandlerContribution: IElementSVGExportHandler);

  svgExport(
    nodeContainer: HTMLElement | null,
    edgeContainer: HTMLElement | null,
    edgeLabelContainer: HTMLElement | null
  ): SvgExportResult;
}

export interface IElementSVGExportHandler {
  canHandle(element: Element): boolean;

  /**
   * Handle the element
   * @param element The element to export
   * @param parentSvgElement The svg element that will contain the exported element
   * @param svgDocument The document used to create the svg element
   * @returns a list of svg element to add to the parent svg after all elements have been handled
   */
  handle(element: Element, parentSvgElement: SVGElement, svgDocument: XMLDocument): SVGElement[];
}
