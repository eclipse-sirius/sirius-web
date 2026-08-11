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

import { Edge, Node, useStoreApi } from '@xyflow/react';
import { MouseEvent, useCallback, useRef } from 'react';
import { flushSync } from 'react-dom';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseRectangleSelectionValue } from './useRectangleSelection.types';

/**
 * Starting a rectangle selection inside a node should not select that node nor any of its containers,
 * even though the rectangle necessarily overlaps them.
 *
 * Here, instead of returning the current selection, we make those nodes unselectable during the rectangle-selection drag event.
 */
export const useRectangleSelection = (): UseRectangleSelectionValue => {
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { setNodes } = useStore();

  const excludedNodeIds = useRef<Set<string>>(new Set());

  const setNodesSelectable = useCallback(
    (nodeIds: Set<string>, selectable: boolean): void => {
      setNodes((previousNodes) =>
        previousNodes.map((previousNode) =>
          nodeIds.has(previousNode.id) ? { ...previousNode, selectable } : previousNode
        )
      );
    },
    [setNodes]
  );

  const restoreExcludedNodes = useCallback((): void => {
    const previouslyExcludedNodeIds = excludedNodeIds.current;
    if (previouslyExcludedNodeIds.size > 0) {
      excludedNodeIds.current = new Set();
      setNodesSelectable(previouslyExcludedNodeIds, true);
    }
  }, [setNodesSelectable]);

  const onRectangleSelectionStart = useCallback(
    (event: MouseEvent): void => {
      // A previous gesture may not have been ended properly, so better to clean up here too
      restoreExcludedNodes();

      const { nodeLookup } = store.getState();
      const nodeIdsToExclude = new Set<string>();
      let currentNodeId: string | null =
        event.target instanceof Element
          ? event.target.closest<HTMLElement>('.react-flow__node')?.dataset.id ?? null
          : null;

      while (currentNodeId !== null) {
        const internalNode = nodeLookup.get(currentNodeId);
        if (internalNode?.selectable !== false) {
          nodeIdsToExclude.add(currentNodeId);
        }
        currentNodeId = internalNode?.parentId ?? null;
      }

      if (nodeIdsToExclude.size > 0) {
        excludedNodeIds.current = nodeIdsToExclude;
        flushSync(() => setNodesSelectable(nodeIdsToExclude, false));
      }
    },
    [store, setNodesSelectable, restoreExcludedNodes]
  );

  const onRectangleSelectionEnd = useCallback((): void => {
    restoreExcludedNodes();
  }, [restoreExcludedNodes]);

  return {
    onRectangleSelectionStart,
    onRectangleSelectionEnd,
  };
};
