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
import { Edge, Node, NodeChange, NodeDimensionChange, useReactFlow } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { UseResizeChangeValue } from './useResizeChange.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const getLeftRightBorderWidth = (resizedNode: Node<NodeData>): number => {
  let borderLeftWidth: number = 1;
  if (resizedNode.data.style.borderLeftWidth) {
    if (typeof resizedNode.data.style.borderLeftWidth === 'number') {
      borderLeftWidth = resizedNode.data.style.borderLeftWidth;
    } else {
      borderLeftWidth = parseFloat(resizedNode.data.style.borderLeftWidth);
    }
  }
  let borderRightWidth: number = 1;
  if (resizedNode.data.style.borderRightWidth) {
    if (typeof resizedNode.data.style.borderRightWidth === 'number') {
      borderRightWidth = resizedNode.data.style.borderRightWidth;
    } else {
      borderRightWidth = parseFloat(resizedNode.data.style.borderRightWidth);
    }
  }
  return borderLeftWidth + borderRightWidth;
};

const applyResizeToListContain = (
  resizedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  change: NodeDimensionChange
): NodeChange<Node<NodeData>>[] => {
  const newChanges: NodeChange<Node<NodeData>>[] = [];
  if (isListData(resizedNode)) {
    const borderWidth: number = getLeftRightBorderWidth(resizedNode);
    nodes.forEach((node) => {
      if (node.parentNode === resizedNode.id && change.dimensions?.width) {
        newChanges.push({
          id: node.id,
          type: 'dimensions',
          resizing: true,
          dimensions: { width: change.dimensions?.width - borderWidth, height: node.height ?? 0 },
        });
        newChanges.push(...applyResizeToListContain(node, nodes, change));
      }
    });
  }
  return newChanges;
};

const isResize = (change: NodeChange): change is NodeDimensionChange =>
  change.type === 'dimensions' && (change.resizing ?? false);

export const useResizeChange = (): UseResizeChangeValue => {
  const { getNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const transformResizeListNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      const newChanges: NodeChange<Node<NodeData>>[] = [];
      const updatedChanges: NodeChange<Node<NodeData>>[] = changes.map((change) => {
        if (isResize(change)) {
          const resizedNode = getNodes().find((node) => change.id === node.id);
          if (resizedNode) {
            newChanges.push(...applyResizeToListContain(resizedNode, getNodes(), change));
          }
        }
        return change;
      });
      return [...newChanges, ...updatedChanges];
    },
    []
  );

  return { transformResizeListNodeChanges };
};
