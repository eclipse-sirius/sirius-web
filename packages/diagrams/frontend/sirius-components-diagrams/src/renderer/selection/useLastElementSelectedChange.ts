/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { Edge, Node, NodeChange, NodeSelectionChange, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseLastElementSelectedChangeValue } from './useLastElementSelectedChange.types';

function isNodeSelectionChange(change: NodeChange<Node<NodeData>>): change is NodeSelectionChange {
  return change.type === 'select';
}

export const useLastElementSelectedChange = (): UseLastElementSelectedChangeValue => {
  /**
   * Set data.isLastNodeSelected on the last selected node excluding border nodes
   */
  const reactFlowStore = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
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

      const isBorderNodeSelectionChange = changes
        .filter(isNodeSelectionChange)
        .map((change) => reactFlowStore.getState().nodeLookup.get(change.id))
        .find((node) => node && node.data.isBorderNode);

      if (selectChanges.length > 0 && !isBorderNodeSelectionChange) {
        return nodes.map((previousNode) => {
          const currentChange = selectChanges.find((change) => change.id === previousNode.id);
          // When a new element is selected then it's the LastNodeSelected
          if (currentChange && currentChange.selected) {
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

    []
  );

  return { applyLastElementSelected };
};
