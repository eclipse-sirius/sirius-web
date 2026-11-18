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

import { Node, NodeChange } from '@xyflow/react';
import { useCallback } from 'react';
import { NodeData } from '../DiagramRenderer.types';
import { getHandlePosition } from '../connector/useHandles';
import { isHandleNode } from '../node/HandleNode.types';
import {
  isDimensionChange,
  isPositionChange,
  isReplaceAndBoundsChange,
  isReplaceChange,
} from '../node/nodeChangePredicates';
import { UseCreationHandleChangeValue } from './useCreationHandleChange.types';

/** Updates existing creation handles when their owner is moved. */
export const useCreationHandleChange = (): UseCreationHandleChangeValue => {
  const updateCreationHandlePositions = useCallback(
    (ownerIds: string[], newNodes: Node<NodeData>[]): Node<NodeData>[] => {
      if (ownerIds.length === 0) {
        return newNodes;
      }

      const nodeLookup = new Map(newNodes.map((node) => [node.id, node]));
      const changedOwnerIds = new Set(ownerIds);
      return newNodes.map((node) => {
        if (!isHandleNode(node) || node.data.nodeId === null) {
          return node;
        }
        const owner = nodeLookup.get(node.data.nodeId);
        if (!owner) {
          return node;
        }
        const absolutePosition = { ...owner.position };
        let ownerOrAncestor = owner;
        let ownerChanged = changedOwnerIds.has(owner.id);
        while (ownerOrAncestor.parentId) {
          const parent = nodeLookup.get(ownerOrAncestor.parentId);
          if (!parent) {
            break;
          }
          absolutePosition.x += parent.position.x;
          absolutePosition.y += parent.position.y;
          ownerChanged = ownerChanged || changedOwnerIds.has(parent.id);
          ownerOrAncestor = parent;
        }
        if (!ownerChanged) {
          return node;
        }
        const position = getHandlePosition(
          node.data.position,
          absolutePosition,
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
    (
      changes: NodeChange<Node<NodeData>>[],
      previousNodes: Node<NodeData>[],
      newNodes: Node<NodeData>[]
    ): Node<NodeData>[] => {
      const previousNodeLookup = changes.some(isReplaceChange)
        ? new Map(previousNodes.map((node) => [node.id, node]))
        : null;
      const ownerIds = changes.flatMap((change): string[] => {
        if (
          isDimensionChange(change) ||
          isPositionChange(change) ||
          (isReplaceChange(change) &&
            previousNodeLookup !== null &&
            isReplaceAndBoundsChange(change, previousNodeLookup.get(change.id)))
        ) {
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
