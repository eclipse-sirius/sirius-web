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
import { Edge, InternalNode, Node, NodeChange, NodeSelectionChange, XYPosition, useStoreApi } from '@xyflow/react';
import { pointToRendererPoint } from '@xyflow/system';
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
   * Set data.isLastNodeSelected on the last selected node excluding border nodes
   */
  const computeLastElementSelected = (changes: NodeChange<Node<NodeData>>[], nodes: Node<NodeData>[]) => {
    const selectedChangeIds = changes
      .filter(isNodeSelectionChange)
      .filter((change) => change.selected)
      .map((change) => change.id);

    const changedNodes: InternalNode<Node<NodeData>>[] = selectedChangeIds
      .map((id) => reactFlowStore.getState().nodeLookup.get(id))
      .filter((node): node is InternalNode<Node<NodeData>> => !!node);

    const userSelectionRect = reactFlowStore.getState().userSelectionRect;
    // We are in the context of a rectangle group selection, so we return the closest node to the group selection end position
    if (reactFlowStore.getState().userSelectionActive && !!userSelectionRect) {
      // The user selection rectangle is expressed in pane coordinates whereas the nodes are
      // positioned in flow coordinates, so the cursor position has to be converted first.
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

      return findClosest(cursorPosition, changedNodes);
      // We are in the context of a single selection so we return the element selected
    } else {
      return nodes.find((node) => node.id === selectedChangeIds[0]);
    }
  };

  const applyLastElementSelected = useCallback(
    (
      changes: NodeChange<Node<NodeData>>[],
      nodes: Node<NodeData>[],
      selectedElementsIds: string[]
    ): Node<NodeData>[] => {
      const selectChanges: NodeSelectionChange[] = changes.filter(isNodeSelectionChange);

      const previousCandidateLastSelectedElementIds = selectedElementsIds
        .map((elementId) => reactFlowStore.getState().nodeLookup.get(elementId))
        .filter((node) => !!node && !node.data.isBorderNode)
        .map((node) => node?.id);

      const isOnlyOneUnSelectChange =
        changes.filter(isNodeSelectionChange).filter((change) => !change.selected).length === 1;

      const lastNodeSelected = computeLastElementSelected(changes, nodes);

      const isBorderNodeSelectionChange = changes
        .filter(isNodeSelectionChange)
        .map((change) => reactFlowStore.getState().nodeLookup.get(change.id))
        .find((node) => node && node.data.isBorderNode);

      if (selectChanges.length > 0 && !isBorderNodeSelectionChange) {
        return nodes.map((previousNode) => {
          if (!!lastNodeSelected && previousNode.id === lastNodeSelected.id) {
            return {
              ...previousNode,
              data: {
                ...previousNode.data,
                isLastNodeSelected: true,
              },
            };
          }
          const unSelectChange = changes.filter(isNodeSelectionChange).find((change) => !change.selected);
          const candidateIds = previousCandidateLastSelectedElementIds.filter(
            (id) => !!unSelectChange && id !== unSelectChange.id
          );
          const candidateId = candidateIds.at(candidateIds.length - 1);
          // When the selection changes, then the previous LastNodeSelected can be invalidated
          if (previousNode.data.isLastNodeSelected && previousNode.id != candidateId) {
            return {
              ...previousNode,
              data: {
                ...previousNode.data,
                isLastNodeSelected: false,
              },
            };
          }
          // When an element is unSelected then if there was another one selected previously then it's the LastNodeSelected
          if (isOnlyOneUnSelectChange && previousNode.id === candidateId) {
            return {
              ...previousNode,
              data: {
                ...previousNode.data,
                isLastNodeSelected: true,
              },
            };
          }

          return previousNode;
        });
      } else {
        return nodes;
      }
    },

    [reactFlowStore]
  );

  return { applyLastElementSelected };
};
