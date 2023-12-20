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
import { useCallback } from 'react';
import { Node, NodeChange, NodePositionChange, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ListNodeData } from '../node/ListNode.types';
import { UseMoveChangeValue } from './useMoveChange.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

const applyPositionChangeToParentIfUndraggable = (
  movedNode: Node<NodeData>,
  nodes: Node<NodeData>[],
  change: NodePositionChange
): NodeChange => {
  const parentNode = nodes.find((node) => movedNode?.parentNode === node.id);
  if (parentNode && change.position && isListData(parentNode) && !parentNode.data.areChildNodesDraggable) {
    change.id = parentNode.id;
    change.position.x = parentNode.position.x + (change.position.x - movedNode.position.x);
    change.position.y = parentNode.position.y + (change.position.y - movedNode.position.y);
    return applyPositionChangeToParentIfUndraggable(parentNode, nodes, change);
  } else {
    return change;
  }
};

const isMove = (change: NodeChange): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean' && change.dragging;

export const useMoveChange = (): UseMoveChangeValue => {
  const { getNodes } = useReactFlow<NodeData, EdgeData>();

  const transformUndraggableListNodeChanges = useCallback((changes: NodeChange[]): NodeChange[] => {
    return changes.map((change) => {
      if (isMove(change)) {
        const movedNode = getNodes().find((node) => change.id === node.id);
        if (movedNode?.parentNode) {
          applyPositionChangeToParentIfUndraggable(movedNode, getNodes(), change);
        }
        if (movedNode?.data.pinned) {
          change.position = undefined; // canceled move if node is pinned
        }
      }
      return change;
    });
  }, []);

  const applyMoveChange = (changes: NodeChange[], nodes: Node<NodeData>[]): Node<NodeData>[] => {
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
