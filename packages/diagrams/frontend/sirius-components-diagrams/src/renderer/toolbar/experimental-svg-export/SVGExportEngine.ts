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
import { ImageElementSVGExportHandler } from './ImageElementSVGExportHandler';
import { RectElementSVGExportHandler } from './RectElementSVGExportHandler';
import { SVGDefsElementSVGExportHandler } from './SVGDefsElementSVGExportHandler';
import { SVGElementSVGExportHandler } from './SVGElementSVGExportHandler';
import { IElementSVGExportHandler, ISVGExportEngine, SvgExportResult, svgNamespace } from './SVGExportEngine.types';
import { TextElementSVGExportHandler } from './TextElementSVGExportHandler';

/**
 * The engine that export a diagram in pure svg.
 * This engine takes element from a xyflow diagram.
 */
export class SVGExportEngine implements ISVGExportEngine {
  private readonly svgDocument: XMLDocument;

  private readonly svg: SVGSVGElement;

  private elementSVGExportHandlers: IElementSVGExportHandler[] = [
    new RectElementSVGExportHandler(),
    new TextElementSVGExportHandler(),
    new ImageElementSVGExportHandler(),
    new SVGElementSVGExportHandler(),
    new SVGDefsElementSVGExportHandler(),
  ];

  constructor(
    width: number,
    height: number,
    transform: string,
    style: SVGStyleElement | null | undefined,
    private readonly htmlToImageTransform: string
  ) {
    this.svgDocument = document.implementation.createDocument(svgNamespace, 'svg', null);
    this.svg = this.svgDocument.documentElement as unknown as SVGSVGElement;
    this.svg.setAttribute('xmlns:xlink', 'http://www.w3.org/1999/xlink');
    this.svg.style.transform = transform;
    this.svg.setAttribute('width', width.toString());
    this.svg.setAttribute('height', height.toString());
    this.svg.setAttribute('viewBox', `0 0 ${width} ${height}`);
    this.svg.style.width = width.toString();
    this.svg.style.height = height.toString();
    if (style) {
      const clonedStyle = this.svgDocument.importNode(style, true);
      this.svg.appendChild(clonedStyle);
    }
  }

  svgExport(
    nodeContainer: HTMLElement | null,
    edgeContainer: HTMLElement | null,
    edgeLabelContainer: HTMLElement | null
  ): SvgExportResult {
    const elementToAddAtTheEnd: SVGElement[] = [];

    const nodes = Array.from(nodeContainer?.querySelectorAll<HTMLElement>('[data-svg]') ?? []);
    nodes.forEach((child) => {
      this.elementSVGExportHandlers.forEach((svgExportHandler) => {
        if (svgExportHandler.canHandle(child)) {
          const addAtTheEnd = svgExportHandler.handle(child, this.svg, this.svgDocument);
          elementToAddAtTheEnd.push(...addAtTheEnd);
        }
      });
    });

    // Retrieve edge markers definitions
    const edgeRelatedElements = Array.from(edgeContainer?.querySelectorAll<HTMLElement>('[data-svg]') ?? []);
    edgeRelatedElements.forEach((child) => {
      this.elementSVGExportHandlers.forEach((svgExportHandler) => {
        if (svgExportHandler.canHandle(child)) {
          const addAtTheEnd = svgExportHandler.handle(child, this.svg, this.svgDocument);
          elementToAddAtTheEnd.push(...addAtTheEnd);
        }
      });
    });

    // It is not possible to use a export handler for edges, because reactflow only create a <path> for an edge and wraps each edge in a <g> in a <svg>
    // We do not have the hand on that.
    const matrix = new DOMMatrixReadOnly(this.htmlToImageTransform);
    edgeContainer?.childNodes.forEach((child) => {
      // If we stop using Html-to-image as a base for the svg export, `edge-markers` may not be a child of edge container.
      if (child instanceof SVGSVGElement && child.id !== 'edge-markers' && child.firstChild) {
        const gElement = this.svgDocument.importNode(child.firstChild, true);
        if (gElement instanceof SVGGElement) {
          gElement.removeAttribute('style');
          gElement.setAttribute('transform', `translate(${matrix.m41}, ${matrix.m42}) scale(${matrix.m11})`);
          this.svg.appendChild(gElement);
        }
      }
    });

    const edgeLabels = Array.from(edgeLabelContainer?.querySelectorAll<HTMLElement>('[data-svg]') ?? []);
    edgeLabels.forEach((child) => {
      this.elementSVGExportHandlers.forEach((svgExportHandler) => {
        if (svgExportHandler.canHandle(child)) {
          const addAtTheEnd = svgExportHandler.handle(child, this.svg, this.svgDocument);
          elementToAddAtTheEnd.push(...addAtTheEnd);
        }
      });
    });

    elementToAddAtTheEnd.forEach((element) => {
      this.svg.appendChild(element);
    });

    return { svg: this.svg, svgDocument: this.svgDocument };
  }

  registerElementSVGExportHandlerContribution(elementSVGExportHandlerContribution: IElementSVGExportHandler) {
    this.elementSVGExportHandlers.push(elementSVGExportHandlerContribution);
  }
}
