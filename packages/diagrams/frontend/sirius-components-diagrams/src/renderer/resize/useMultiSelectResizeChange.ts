/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Node, NodeChange, NodeDimensionChange, NodePositionChange } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { isResize, isMove } from '../node/nodeChangePredicates';
import { UseMultiSelectResizeChangeValue } from './useMultiSelectResizeChange.types';

const applyMultiSelectResize = (
  change: NodeDimensionChange,
  offsetWidth: number,
  offsetHeight: number,
  nodes: Node<NodeData>[]
): NodeChange<Node<NodeData>>[] => {
  return nodes
    .filter((node) => node.id !== change.id && node.selected)
    .filter(
      (node) => node.data.nodeDescription?.userResizable !== 'NONE' && !node.data.isListChild && !node.data.isBorderNode
    )
    .map((node) => {
      let newWidth: number = (node.width ?? 0) + offsetWidth;
      let newHeight: number = (node.height ?? 0) + offsetHeight;
      if (node.data.nodeDescription?.userResizable === 'HORIZONTAL') {
        newHeight = node.height ?? 0;
      }
      if (node.data.nodeDescription?.userResizable === 'VERTICAL') {
        newWidth = node.width ?? 0;
      }
      if (node.data.minComputedHeight && newHeight < node.data.minComputedHeight) {
        newHeight = node.data.minComputedHeight;
      }
      if (node.data.minComputedWidth && newWidth < node.data.minComputedWidth) {
        newWidth = node.data.minComputedWidth;
      }
      return {
        ...change,
        id: node.id,
        dimensions: {
          height: newHeight,
          width: newWidth,
        },
      };
    });
};

const applyMultiSelectMoveFromResize = (
  change: NodePositionChange,
  offsetX: number,
  offsetY: number,
  nodes: Node<NodeData>[]
): NodeChange<Node<NodeData>>[] => {
  return nodes
    .filter((node) => node.id !== change.id && node.selected)
    .filter(
      (node) => node.data.nodeDescription?.userResizable !== 'NONE' && !node.data.isListChild && !node.data.isBorderNode
    )
    .map((node) => {
      const newX: number = (node.position.x ?? 0) + offsetX;
      const newY: number = (node.position.y ?? 0) + offsetY;
      return {
        ...change,
        id: node.id,
        position: { x: newX, y: newY },
        positionAbsolute: undefined,
      };
    });
};

export const useMultiSelectResizeChange = (): UseMultiSelectResizeChangeValue => {
  const { getNode, getNodes } = useStore();
  const transformMultiSelectResizeNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      const newChanges: NodeChange<Node<NodeData>>[] = [];
      changes.forEach((currentChange) => {
        if (isResize(currentChange)) {
          const resizedNode = getNode(currentChange.id);
          const positionChangeForSameNode: NodePositionChange | undefined = changes.find(
            (change): change is NodePositionChange => isMove(change) && change.id === currentChange.id
          );
          let offsetX: number = 0;
          let offsetY: number = 0;
          if (resizedNode) {
            const offsetWidth: number = (currentChange.dimensions?.width ?? 0) - (resizedNode.width ?? 0);
            const offsetHeight: number = (currentChange.dimensions?.height ?? 0) - (resizedNode.height ?? 0);
            if (positionChangeForSameNode && positionChangeForSameNode.position) {
              offsetX = positionChangeForSameNode.position.x - resizedNode.position.x;
              offsetY = positionChangeForSameNode.position.y - resizedNode.position.y;
              newChanges.push(
                ...applyMultiSelectMoveFromResize(positionChangeForSameNode, offsetX, offsetY, getNodes())
              );
            }
            newChanges.push(...applyMultiSelectResize(currentChange, offsetWidth, offsetHeight, getNodes()));
          }
        }
      });
      return [...changes, ...newChanges];
    },
    [getNode, getNodes]
  );

  return { transformMultiSelectResizeNodeChanges };
};
