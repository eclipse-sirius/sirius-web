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
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { GQLFilterSelectionMenuItem, UseFilterContentValue } from './useFilterContents.types';

const selectAllNodes: GQLFilterSelectionMenuItem = {
  id: 'select_all_nodes',
  label: 'Select all nodes',
};
const selectAllEdges: GQLFilterSelectionMenuItem = {
  id: 'select_all_edges',
  label: 'Select all edges',
};
const unselectNodes: GQLFilterSelectionMenuItem = {
  id: 'unselect_all_nodes',
  label: 'Unselect all nodes',
};
const unselectChildNodes: GQLFilterSelectionMenuItem = {
  id: 'unselect_child_nodes',
  label: 'Unselect child nodes',
};
const unselectEdges: GQLFilterSelectionMenuItem = {
  id: 'unselect_all_edges',
  label: 'Unselect edges',
};

export const useFilterContents = (): UseFilterContentValue => {
  const { edgeLookup, nodeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();

  const fetchFilterMenuItems = (diagramElementIds: string[]): GQLFilterSelectionMenuItem[] => {
    const filterSelectionMenuItems: GQLFilterSelectionMenuItem[] = [];
    if (diagramElementIds.length > 0) {
      const containsSelectedEdge = diagramElementIds.find((id) => !!edgeLookup.get(id));
      if (containsSelectedEdge) {
        filterSelectionMenuItems.push(unselectEdges);
      }
      const containsSelectedNode = diagramElementIds.find((id) => !!nodeLookup.get(id));
      if (containsSelectedNode) {
        filterSelectionMenuItems.push(unselectNodes);
      }
      const containsSelectedChildNode = diagramElementIds.find((id) => !!nodeLookup.get(id)?.parentId);
      if (containsSelectedChildNode) {
        filterSelectionMenuItems.push(unselectChildNodes);
      }
    } else {
      filterSelectionMenuItems.push(selectAllNodes, selectAllEdges);
    }

    return filterSelectionMenuItems;
  };

  return { fetchFilterMenuItems };
};
