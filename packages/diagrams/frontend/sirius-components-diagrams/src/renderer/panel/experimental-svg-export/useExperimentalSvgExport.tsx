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

import { useData } from '@eclipse-sirius/sirius-components-core';
import { EdgeData, NodeData } from '@eclipse-sirius/sirius-components-diagrams';
import { Edge, getViewportForBounds, Node as ReactFlowNode, Rect, useReactFlow, Viewport } from '@xyflow/react';
import { toSvg } from 'html-to-image';
import { useCallback, useEffect, useRef } from 'react';
import { createRoot } from 'react-dom/client';
import { SVGExportEngine } from './SVGExportEngine';
import { svgExportIElementSVGExportHandlerExtensionPoint } from './SVGExportHandlerExtensionPoints';
import { UseExperimentalSvgExport } from './useExperimentalSvgExport.types';

function createImage(url: string): Promise<HTMLImageElement> {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.onload = () => {
      img.decode().then(() => {
        requestAnimationFrame(() => resolve(img));
      });
    };
    img.onerror = reject;
    img.crossOrigin = 'anonymous';
    img.decoding = 'async';
    img.src = url;
  });
}

export const useExperimentalSvgExport = (): UseExperimentalSvgExport => {
  const reactFlow = useReactFlow<ReactFlowNode<NodeData>, Edge<EdgeData>>();

  const { data: elementSvgExportHandlerContributions } = useData(svgExportIElementSVGExportHandlerExtensionPoint);

  const exportSvg = useCallback((callback: (dataUrl: string) => void) => {
    const nodesBounds: Rect = reactFlow.getNodesBounds(reactFlow.getNodes());
    const imageWidth: number = nodesBounds.width;
    const imageHeight: number = nodesBounds.height;

    const viewport: Viewport = getViewportForBounds(nodesBounds, imageWidth, imageHeight, 0.5, 2, 0.05);
    const edges: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__edges');
    const edgeMarkers: HTMLElement | null = document.getElementById('edge-markers');
    const reactFlowViewportContainer: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__viewport');
    if (reactFlowViewportContainer && edges && edgeMarkers) {
      const clonedEdgeMarkers: Node = edgeMarkers.cloneNode(true);
      edges.insertBefore(clonedEdgeMarkers, edges.firstChild);

      const cssTransform = `translate(${viewport.x}px, ${viewport.y}px) scale(${viewport.zoom})`;
      toSvg(reactFlowViewportContainer, {
        backgroundColor: '#ffffff',
        width: imageWidth,
        height: imageHeight,
        style: {
          width: imageWidth.toString(),
          height: imageHeight.toString(),
          transform: cssTransform,
        },
      })
        .then(createImage)
        .then((image) => {
          const textSvg = decodeURIComponent(image.src.replace('data:image/svg+xml;charset=utf-8,', ''));
          const parser = new DOMParser();
          const doc = parser.parseFromString(textSvg, 'image/svg+xml');
          const otherViewport = doc.querySelector<HTMLElement>('.react-flow__viewport');
          const style = otherViewport?.querySelector<SVGStyleElement>('style');
          if (otherViewport) {
            const hiddenContainer: HTMLDivElement = document.createElement('div');
            hiddenContainer.id = 'hidden-svg-export-container';
            hiddenContainer.style.display = 'inline-block';
            hiddenContainer.style.position = 'absolute';
            hiddenContainer.style.top = '0';
            hiddenContainer.style.visibility = 'hidden';
            hiddenContainer.style.zIndex = '-1';
            document.body.appendChild(hiddenContainer);

            const Element = () => {
              const divRef = useRef<HTMLDivElement | null>(null);

              useEffect(() => {
                if (divRef && divRef.current && divRef.current.firstChild instanceof SVGSVGElement) {
                  const svgData: string = buildSvg(
                    imageWidth,
                    imageHeight,
                    `scale(${viewport.zoom})`,
                    divRef.current.firstChild,
                    style,
                    otherViewport
                  );
                  callback(URL.createObjectURL(new Blob([svgData], { type: 'image/svg+xml' })));
                  hiddenContainer.parentNode?.removeChild(hiddenContainer);
                }
              }, [divRef]);

              return <div ref={divRef} dangerouslySetInnerHTML={{ __html: textSvg }} />;
            };

            const root = createRoot(hiddenContainer);
            root.render(<Element />);
            return root;
          }
          return null;
        });
    }
  }, []);

  const buildSvg = (
    width: number,
    height: number,
    transform: string,
    element: SVGSVGElement,
    style: SVGStyleElement | null | undefined,
    htmlToImageViewport: HTMLElement
  ): string => {
    const elementsToExport = element?.querySelectorAll<HTMLElement>('[data-svg]');
    const edgeContainer: HTMLElement | null = element.querySelector<HTMLElement>('.react-flow__edges');
    const svgExportEngine = new SVGExportEngine(width, height, transform, style, htmlToImageViewport.style.transform);

    elementSvgExportHandlerContributions.forEach((contribution) =>
      svgExportEngine.registerElementSVGExportHandlerContribution(contribution)
    );
    const { svgDocument } = svgExportEngine.svgExport(Array.from(elementsToExport ?? []), edgeContainer);

    return new XMLSerializer().serializeToString(svgDocument);
  };

  return { experimentalExportToSvg: exportSvg };
};
