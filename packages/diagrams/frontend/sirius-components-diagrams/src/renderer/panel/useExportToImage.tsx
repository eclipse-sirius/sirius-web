/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { toSvg } from 'html-to-image';
import { useCallback } from 'react';
import { Rect, Transform, getRectOfNodes, getTransformForBounds, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseExportToImage } from './useExportToImage.types';

const downloadImage = (dataUrl: string) => {
  const a: HTMLAnchorElement = document.createElement('a');
  a.setAttribute('download', 'diagram.svg');
  a.setAttribute('href', dataUrl);
  a.click();
};

export const useExportToImage = (): UseExportToImage => {
  const reactFlow = useReactFlow<NodeData, EdgeData>();

  const exportToImage = useCallback(() => {
    const nodesBounds: Rect = getRectOfNodes(reactFlow.getNodes());
    const imageWidth: number = nodesBounds.width;
    const imageHeight: number = nodesBounds.height;
    const transform: Transform = getTransformForBounds(nodesBounds, imageWidth, imageHeight, 0.5, 2, 0.2);

    const viewport: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__viewport');
    const edges: HTMLElement | null = document.querySelector<HTMLElement>('.react-flow__edges');
    const edgeMarkersDefs: HTMLElement | null = document.getElementById('edge-markers-defs');

    if (viewport && edges && edgeMarkersDefs) {
      const clonedEdgeMarkersDefs: Node = edgeMarkersDefs.cloneNode(true);
      edges.insertBefore(clonedEdgeMarkersDefs, edges.firstChild);

      toSvg(viewport, {
        backgroundColor: '#ffffff',
        width: imageWidth,
        height: imageHeight,
        style: {
          width: imageWidth.toString(),
          height: imageHeight.toString(),
          transform: `translate(${transform[0]}px, ${transform[1]}px) scale(${transform[2]})`,
        },
      })
        .then(downloadImage)
        .finally(() => edges.removeChild(clonedEdgeMarkersDefs));
    }
  }, []);
  return { exportToImage };
};
