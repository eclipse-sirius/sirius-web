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
import { Node, NodeChange, NodePositionChange } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { UseMoveChangeValue } from './useMoveChange.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const applyPositionChangeToParentIfUndraggable = (
  movedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  change: NodePositionChange
): NodeChange<Node<NodeData>> => {
  const parentNode = nodes.find((node) => movedNode?.parentId === node.id);
  if (parentNode && change.position && isListData(parentNode) && !parentNode.data.areChildNodesDraggable) {
    if (change.dragging) {
      change.id = parentNode.id;
      change.position.x = parentNode.position.x + (change.position.x - movedNode.position.x);
      change.position.y = parentNode.position.y + (change.position.y - movedNode.position.y);
      return applyPositionChangeToParentIfUndraggable(parentNode, nodes, change);
    } else {
      change.position = undefined;
      return change;
    }
  } else {
    return change;
  }
};

const isMove = (change: NodeChange<Node<NodeData>>): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean';

export const useMoveChange = (): UseMoveChangeValue => {
  const { getNodes } = useStore();

  const transformUndraggableListNodeChanges = useCallback(
    (changes: NodeChange<Node<NodeData>>[]): NodeChange<Node<NodeData>>[] => {
      return changes.map((change) => {
        if (isMove(change)) {
          const movedNode = getNodes().find((node) => change.id === node.id);
          if (movedNode?.parentId && !movedNode.data.isBorderNode) {
            applyPositionChangeToParentIfUndraggable(movedNode, getNodes(), change);
          }
          if (movedNode?.data.pinned) {
            change.position = undefined; // canceled move if node is pinned
          }
        }
        return change;
      });
    },
    [getNodes]
  );

  const applyMoveChange = (changes: NodeChange<Node<NodeData>>[], nodes: Node<NodeData>[]): Node<NodeData>[] => {
    changes.forEach((change) => {
      if (isMove(change)) {
        const movedNode = nodes.find((node) => node.id === change.id);
        if (movedNode) {
          movedNode.zIndex = 1;
        }
      }
    });
    return nodes;
  };

  return { transformUndraggableListNodeChanges, applyMoveChange };
};
