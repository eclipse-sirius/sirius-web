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

import { Edge, Node, useReactFlow, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useFitView } from '../fit-to-screen/useFitView';
import { UseRevealNodesType } from './useRevealNodes.types';

export const useRevealNodes = (): UseRevealNodesType => {
  const { fitView } = useFitView();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { flowToScreenPosition } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const isNodeFullyVisible = useCallback(
    (node: Node): boolean => {
      const viewportRect = document.getElementsByClassName('react-flow__pane')[0]?.getBoundingClientRect();
      const topLeft = flowToScreenPosition(node.position);
      const bottomRight = flowToScreenPosition({
        x: node.position.x + (node.width ?? 0),
        y: node.position.y + (node.height ?? 0),
      });
      if (viewportRect && topLeft && bottomRight) {
        return (
          topLeft.x >= viewportRect.left &&
          topLeft.y >= viewportRect.top &&
          bottomRight.x <= viewportRect.right &&
          bottomRight.y <= viewportRect.bottom
        );
      }
      return false;
    },
    [flowToScreenPosition]
  );

  const revealNodes = useCallback(
    (nodesToReveal: Node[]) => {
      if (nodesToReveal.some((node) => !isNodeFullyVisible(node))) {
        const zoom = store.getState().transform[2];
        fitView({
          nodes: nodesToReveal,
          minZoom: zoom,
          maxZoom: zoom,
          duration: 400,
        });
      }
    },
    [isNodeFullyVisible]
  );

  return { revealNodes };
};
