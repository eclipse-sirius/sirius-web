/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import {
  Edge,
  Node as ReactFlowNode,
  Rect,
  Viewport,
  getNodesBounds,
  getViewportForBounds,
  useReactFlow,
} from '@xyflow/react';
import { toPng, toSvg } from 'html-to-image';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseExportToImage } from './useExportToImage.types';

const downloadImage = (dataUrl: string, fileName: string) => {
  const a: HTMLAnchorElement = document.createElement('a');
  a.setAttribute('download', fileName);
  a.setAttribute('href', dataUrl);
  a.click();
};

export const useExportToImage = (): UseExportToImage => {
  const reactFlow = useReactFlow<ReactFlowNode<NodeData>, Edge<EdgeData>>();

  const exportToSVG = useCallback(() => {
    const nodesBounds: Rect = getNodesBounds(reactFlow.getNodes());
    const imageWidth: number = nodesBounds.width;
    const imageHeight: number = nodesBounds.height;

    const viewport: Viewport = getViewportForBounds(nodesBounds, imageWidth, imageHeight, 0.5, 2, 0.05);

    const edges: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__edges');
    const edgeMarkersDefs: HTMLElement | null = document.getElementById('edge-markers-defs');

    const reactFlowNodeContainer: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__viewport');

    if (reactFlowNodeContainer && edges && edgeMarkersDefs) {
      const clonedEdgeMarkersDefs: Node = edgeMarkersDefs.cloneNode(true);
      edges.insertBefore(clonedEdgeMarkersDefs, edges.firstChild);

      toSvg(reactFlowNodeContainer, {
        backgroundColor: '#ffffff',
        width: imageWidth,
        height: imageHeight,
        style: {
          width: imageWidth.toString(),
          height: imageHeight.toString(),
          transform: `translate(${viewport.x}px, ${viewport.y}px) scale(${viewport.zoom})`,
        },
      })
        .then((dataUrl) => downloadImage(dataUrl, 'diagram.svg'))
        .finally(() => edges.removeChild(clonedEdgeMarkersDefs));
    }
  }, []);

  const exportToPNG = useCallback(() => {
    const nodesBounds: Rect = getNodesBounds(reactFlow.getNodes());
    const imageWidth: number = nodesBounds.width;
    const imageHeight: number = nodesBounds.height;

    const viewport: Viewport = getViewportForBounds(nodesBounds, imageWidth, imageHeight, 0.5, 2, 0.05);

    const edges: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__edges');
    const edgeMarkersDefs: HTMLElement | null = document.getElementById('edge-markers-defs');

    const reactFlowNodeContainer: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__viewport');

    if (reactFlowNodeContainer && edges && edgeMarkersDefs) {
      const clonedEdgeMarkersDefs: Node = edgeMarkersDefs.cloneNode(true);
      edges.insertBefore(clonedEdgeMarkersDefs, edges.firstChild);

      toPng(reactFlowNodeContainer, {
        backgroundColor: '#ffffff',
        width: imageWidth,
        height: imageHeight,
        style: {
          width: imageWidth.toString(),
          height: imageHeight.toString(),
          transform: `translate(${viewport.x}px, ${viewport.y}px) scale(${viewport.zoom})`,
        },
        pixelRatio: 2,
      })
        .then((dataUrl) => downloadImage(dataUrl, 'diagram.png'))
        .finally(() => edges.removeChild(clonedEdgeMarkersDefs));
    }
  }, []);

  return { exportToSVG, exportToPNG };
};
