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
import { Node, NodeChange, NodeDimensionChange, Dimensions, XYPosition } from 'reactflow';
import { UseOverlapValue } from './useOverlap.types';

export type Rect = Dimensions & XYPosition;
export const getOverlappingArea = (rectA: Rect, rectB: Rect): number => {
  const xOverlap = Math.max(0, Math.min(rectA.x + rectA.width, rectB.x + rectB.width) - Math.max(rectA.x, rectB.x));
  const yOverlap = Math.max(0, Math.min(rectA.y + rectA.height, rectB.y + rectB.height) - Math.max(rectA.y, rectB.y));

  return Math.ceil(xOverlap * yOverlap);
};

export const nodeToRect = (node): Rect => {
  const { position } = node;

  return {
    ...position,
    width: node.computed?.width ?? node.width ?? 0,
    height: node.computed?.height ?? node.height ?? 0,
  };
};

const isResize = (change: NodeChange): change is NodeDimensionChange =>
  change.type === 'dimensions' && !change.resizing;

export const useOverlap = (): UseOverlapValue => {
  const [enabled, setEnabled] = useState<boolean>(true);

  const getIntersectingNodes = useCallback((node, nodes) => {
    const nodeRect = nodeToRect(node);
    if (!nodeRect) {
      return [];
    }
    return nodes.filter((n) => {
      if (n.id === node!.id) {
        return false;
      }

      const currNodeRect = nodeToRect(n);
      const overlappingArea = getOverlappingArea(currNodeRect, nodeRect);
      return overlappingArea > 0;
    });
  }, []);

  const applyOverlap = (movingNode: Node, nodes: Node[], direction: 'horizontal' | 'vertical' = 'horizontal'): void => {
    getIntersectingNodes(movingNode, nodes)
      .filter((n) => n.parentNode === movingNode.parentNode)
      .forEach((node) => {
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

  const handleNodeOverlapForChanges = useCallback(
    (changes: NodeChange[], nodes: Node[]): Node[] => {
      if (enabled) {
        changes.map((change) => {
          if (isResize(change)) {
            const priorityNode = nodes.find((n) => n.id === change.id);
            if (priorityNode) {
              applyOverlap(priorityNode, nodes);
            }
          }
        });
      }
      return nodes;
    },
    [enabled]
  );

  const resolveNodeOverlap = useCallback(
    (nodes: Node[], direction: 'horizontal' | 'vertical'): Node[] => {
      if (enabled) {
        nodes.filter((node) => !node.data.pinned).forEach((node) => applyOverlap(node, nodes, direction));
      }
      return nodes;
    },
    [enabled]
  );

  const handleNodeOverlapWithPriority = useCallback(
    (priorityNodeId: string | undefined, nodes: Node[]): Node[] => {
      if (enabled) {
        const priorityNode = nodes.find((n) => n.id === priorityNodeId);
        if (priorityNode) {
          applyOverlap(priorityNode, nodes);
        }
      }
      return nodes;
    },
    [enabled]
  );

  return {
    nodeOverlapEnabled: enabled,
    setNodeOverlapEnabled: setEnabled,
    resolveNodeOverlap,
    handleNodeOverlapForChanges,
    handleNodeOverlapWithPriority,
  };
};
