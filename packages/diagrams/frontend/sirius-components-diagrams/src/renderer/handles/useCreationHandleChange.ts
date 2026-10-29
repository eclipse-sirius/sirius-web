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

import { Edge, Node, NodeChange, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getHandlePosition } from '../connector/useHandles';
import { isHandleNode } from '../node/HandleNode.types';
import { evaluateAbsolutePosition } from '../node/NodeUtils';
import { UseCreationHandleChangeValue } from './useCreationHandleChange.types';

/** Updates existing creation handles when their owner is moved. */
export const useCreationHandleChange = (): UseCreationHandleChangeValue => {
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const updateCreationHandlePositions = useCallback(
    (ownerIds: string[], newNodes: Node<NodeData>[]): Node<NodeData>[] => {
      if (ownerIds.length === 0) {
        return newNodes;
      }

      const { nodeLookup } = storeApi.getState();
      return newNodes.map((node) => {
        if (!isHandleNode(node) || node.data.nodeId === null) {
          return node;
        }
        const owner = newNodes.find((newNode) => newNode.id === node.data.nodeId);
        if (!ownerIds.includes(node.data.nodeId) || !owner) {
          return node;
        }
        const position = getHandlePosition(
          node.data.position,
          evaluateAbsolutePosition(owner, nodeLookup),
          owner.width ?? owner.measured?.width ?? 0,
          owner.height ?? owner.measured?.height ?? 0
        );
        if (position.x === node.position.x && position.y === node.position.y) {
          return node;
        }
        return { ...node, position };
      });
    },
    []
  );

  const applyCreationHandleChange = useCallback(
    (changes: NodeChange<Node<NodeData>>[], newNodes: Node<NodeData>[]): Node<NodeData>[] => {
      const ownerIds = changes.flatMap((change): string[] => {
        if (change.type === 'dimensions' || change.type === 'position') {
          return [change.id];
        }
        return [];
      });
      return updateCreationHandlePositions(ownerIds, newNodes);
    },
    [updateCreationHandlePositions]
  );

  return { applyCreationHandleChange, updateCreationHandlePositions };
};
