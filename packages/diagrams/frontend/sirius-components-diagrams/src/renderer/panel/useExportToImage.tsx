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

export const useExportToImage = (): UseExportToImage => {
  const reactFlow = useReactFlow<ReactFlowNode<NodeData>, Edge<EdgeData>>();

  const exportToSVG = useCallback((callback: (dataUrl: string) => void) => {
    const nodesBounds: Rect = getNodesBounds(reactFlow.getNodes());
    const imageWidth: number = nodesBounds.width;
    const imageHeight: number = nodesBounds.height;

    const viewport: Viewport = getViewportForBounds(nodesBounds, imageWidth, imageHeight, 0.5, 2, 0.05);

    const edges: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__edges');
    const edgeMarkers: HTMLElement | null = document.getElementById('edge-markers');

    const reactFlowNodeContainer: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__viewport');

    if (reactFlowNodeContainer && edges && edgeMarkers) {
      const clonedEdgeMarkers: Node = edgeMarkers.cloneNode(true);
      edges.insertBefore(clonedEdgeMarkers, edges.firstChild);

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
        .then((dataUrl) => callback(dataUrl))
        .finally(() => edges.removeChild(clonedEdgeMarkers));
    }
  }, []);

  const exportToPNG = useCallback((callback: (dataUrl: string) => void) => {
    const nodesBounds: Rect = getNodesBounds(reactFlow.getNodes());
    const imageWidth: number = nodesBounds.width;
    const imageHeight: number = nodesBounds.height;

    const viewport: Viewport = getViewportForBounds(nodesBounds, imageWidth, imageHeight, 0.5, 2, 0.05);

    const edges: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__edges');
    const edgeMarkers: HTMLElement | null = document.getElementById('edge-markers');

    const reactFlowNodeContainer: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__viewport');

    if (reactFlowNodeContainer && edges && edgeMarkers) {
      const clonedEdgeMarkersDefs: Node = edgeMarkers.cloneNode(true);
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
        .then((dataUrl) => callback(dataUrl))
        .finally(() => edges.removeChild(clonedEdgeMarkersDefs));
    }
  }, []);

  return { exportToSVG, exportToPNG };
};
