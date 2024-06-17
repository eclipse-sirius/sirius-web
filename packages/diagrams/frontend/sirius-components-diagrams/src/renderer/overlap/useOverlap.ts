/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useCallback, useState } from 'react';
import { Node } from 'reactflow';
import { UseOverlapValue } from './useOverlap.types';

const nodesOverlap = (nodeA: Node, nodeB: Node): boolean => {
  const topLeftX = Math.max(nodeA.position.x, nodeB.position.x);
  const topLeftY = Math.max(nodeA.position.y, nodeB.position.y);
  const bottomRightX = Math.min(nodeA.position.x + (nodeA.width ?? 0), nodeB.position.x + (nodeB.width ?? 0));
  const bottomRightY = Math.min(nodeA.position.y + (nodeA.height ?? 0), nodeB.position.y + (nodeB.height ?? 0));
  return topLeftX < bottomRightX && topLeftY < bottomRightY;
};

const getIntersectingNodes = (node: Node, nodes: Node[]): Node[] => {
  return nodes.filter((n) => {
    if (n.parentNode !== node.parentNode || n.id === node.id) {
      return false;
    }
    return nodesOverlap(node, n);
  });
};

export const useOverlap = (): UseOverlapValue => {
  const [enabled, setEnabled] = useState<boolean>(true);

  const applyOverlap = (movingNode: Node, nodes: Node[], direction: 'horizontal' | 'vertical' = 'horizontal'): void => {
    getIntersectingNodes(movingNode, nodes).forEach((node) => {
      const overlapNode = nodes.find((n) => n.id === node.id);
      if (overlapNode) {
        if (overlapNode.data.pinned) {
          if (direction === 'horizontal') {
            movingNode.position = {
              ...movingNode.position,
              x: overlapNode.position.x + (overlapNode.width ?? 0) + 40,
            };
          } else {
            movingNode.position = {
              ...movingNode.position,
              y: overlapNode.position.y + (overlapNode.height ?? 0) + 40,
            };
          }
          applyOverlap(movingNode, nodes, direction);
        } else {
          if (direction === 'horizontal') {
            overlapNode.position = {
              ...overlapNode.position,
              x: movingNode.position.x + (movingNode.width ?? 0) + 40,
            };
          } else {
            overlapNode.position = {
              ...overlapNode.position,
              y: movingNode.position.y + (movingNode.height ?? 0) + 40,
            };
          }
          applyOverlap(overlapNode, nodes, direction);
        }
      }
    });
  };

  const resolveNodeOverlap = useCallback(
    (nodes: Node[], direction: 'horizontal' | 'vertical'): Node[] => {
      if (enabled) {
        nodes.filter((node) => !node.data.pinned).forEach((node) => applyOverlap(node, nodes, direction));
      }
      return nodes;
    },
    [enabled]
  );

  return {
    nodeOverlapEnabled: enabled,
    setNodeOverlapEnabled: setEnabled,
    resolveNodeOverlap,
  };
};
