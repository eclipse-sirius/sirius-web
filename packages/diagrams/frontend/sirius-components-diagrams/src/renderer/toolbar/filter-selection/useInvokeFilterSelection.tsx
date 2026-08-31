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
import { UseInvokeFilterSelectionValue } from './useInvokeFilterSelection.types';

export const useInvokeFilterSelection = (): UseInvokeFilterSelectionValue => {
  const { nodes, edges, edgeLookup, nodeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();

  const invokeFilterSelection = (diagramElementIds: string[], filterSelectionId: string): string[] => {
    if (filterSelectionId === 'select_all_nodes') {
      return nodes.map((node) => node.id);
    } else if (filterSelectionId === 'select_all_edges') {
      return edges.map((edge) => edge.id);
    } else if (filterSelectionId === 'unselect_all_edges') {
      return diagramElementIds.filter((id) => !edgeLookup.has(id));
    } else if (filterSelectionId === 'unselect_all_nodes') {
      return diagramElementIds.filter((id) => !nodeLookup.has(id));
    } else if (filterSelectionId === 'unselect_child_nodes') {
      return diagramElementIds.filter((id) => !nodeLookup.get(id)?.parentId);
    } else {
      return [];
    }
  };

  return {
    invokeFilterSelection,
  };
};
