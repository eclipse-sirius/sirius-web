/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
import { Edge, InternalNode, Node, NodeChange, NodeSelectionChange, useStoreApi, XYPosition } from '@xyflow/react';
import { pointToRendererPoint, SelectionRect } from '@xyflow/system';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { findClosest } from './distanceComputation';
import { UseLastElementSelectedChangeValue } from './useLastElementSelectedChange.types';

function isNodeSelectionChange(change: NodeChange<Node<NodeData>>): change is NodeSelectionChange {
  return change.type === 'select';
}

export const useLastElementSelectedChange = (): UseLastElementSelectedChangeValue => {
  const reactFlowStore = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  /**
   * When a rectangular selection is active, the closest element from the cursor will be the last selected element.
   */
  const computeLastElementSelectedForRectangleSelection = (
    selectedChangeIds: string[],
    userSelectionRect: SelectionRect
  ): string | null => {
    const changedNodes: InternalNode<Node<NodeData>>[] = selectedChangeIds
      .map((id) => reactFlowStore.getState().nodeLookup.get(id))
      .filter((node): node is InternalNode<Node<NodeData>> => !!node);

    const cursorPanePosition: XYPosition = {
      x:
        userSelectionRect.x === userSelectionRect.startX
          ? userSelectionRect.x + userSelectionRect.width
          : userSelectionRect.x,
      y:
        userSelectionRect.y === userSelectionRect.startY
          ? userSelectionRect.y + userSelectionRect.height
          : userSelectionRect.y,
    };
    const cursorPosition: XYPosition = pointToRendererPoint(cursorPanePosition, reactFlowStore.getState().transform);

    return findClosest(cursorPosition, changedNodes)?.id ?? null;
  };

  const computedLastSelectedElement = (
    changes: NodeChange<Node<NodeData>>[],
    sortedSelectedElementIds: string[]
  ): string | null => {
    const atLeastOneSelectedChange = changes.filter(isNodeSelectionChange).find((change) => change.selected);

    let candidateIds: string[];

    // In case there is a newly selected element, then the last select change will give the last selected element
    if (atLeastOneSelectedChange) {
      candidateIds = changes
        .filter(isNodeSelectionChange)
        .filter((change) => change.selected)
        .filter((change) => !reactFlowStore.getState().nodeLookup.get(change.id)?.data.isBorderNode)
        .map((change) => change.id);
      // Whereas the changes includes only unselect changes, then the last selected element will be the last from the sorted previous selection
    } else {
      const unselectedNodeIds = new Set(
        changes
          .filter(isNodeSelectionChange)
          .filter((change) => !change.selected)
          .map((change) => change.id)
      );
      candidateIds = sortedSelectedElementIds
        .filter((elementId) => !unselectedNodeIds.has(elementId))
        .map((elementId) => reactFlowStore.getState().nodeLookup.get(elementId))
        .filter((node): node is InternalNode<Node<NodeData>> => !!node && !node.data.isBorderNode)
        .map((node) => node.id);
    }

    const userSelectionRect = reactFlowStore.getState().userSelectionRect;

    // While a rectangular selection is active
    if (reactFlowStore.getState().userSelectionActive && !!userSelectionRect) {
      return computeLastElementSelectedForRectangleSelection(candidateIds, userSelectionRect);
    } else {
      return candidateIds.at(-1) ?? null;
    }
  };

  const applyLastElementSelected = useCallback(
    (
      changes: NodeChange<Node<NodeData>>[],
      nodes: Node<NodeData>[],
      sortedSelectedElementIds: string[]
    ): Node<NodeData>[] => {
      if (!changes.some(isNodeSelectionChange)) {
        return nodes;
      }

      const containsBorderNodeSelectionChange = changes
        .filter(isNodeSelectionChange)
        .map((change) => reactFlowStore.getState().nodeLookup.get(change.id))
        .find((node) => node && node.data.isBorderNode);

      if (!containsBorderNodeSelectionChange) {
        const candidateId = computedLastSelectedElement(changes, sortedSelectedElementIds);
        return nodes.map((previousNode) => ({
          ...previousNode,
          data: {
            ...previousNode.data,
            isLastNodeSelected: previousNode.id === candidateId,
          },
        }));
      } else {
        return nodes;
      }
    },
    [reactFlowStore]
  );

  return { applyLastElementSelected };
};
