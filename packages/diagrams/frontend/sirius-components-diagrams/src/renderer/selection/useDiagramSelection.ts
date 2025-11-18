/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import { SelectionEntry, useSelection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, OnSelectionChangeFunc } from '@xyflow/react';
import { useCallback, useState } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseDiagramSelectionValue } from './useDiagramSelection.types';

/**
 * Compute the list of new selected diagram elements while keeping the order of any
 * elements which was already selected before. This ensure we keep the selection order stable.
 */
const mergeNewSelectedElementIds = (previousSelectedElementsIds: string[], newSelectedElementId: string[]) => {
  if (newSelectedElementId.length === 0) {
    return [];
  } else {
    return previousSelectedElementsIds
      .filter((id) => newSelectedElementId.includes(id))
      .concat(newSelectedElementId.filter((id) => !previousSelectedElementsIds.includes(id)));
  }
};

/**
 * Configures React Flow so that when the selection changes on the diagram, the diagram-local & global selections are updated.
 */
export const useDiagramSelection = (): UseDiagramSelectionValue => {
  const { setSelection } = useSelection();
  const [selectedElementsIds, setSelectedElementsIds] = useState<string[]>([]);

  /**
   * The callback invoked by XYFlow when the graphical selection of nodes & edges on a diagram changes.
   * We receive in `nodes` and `edges` the complete list of elements selected after the change.
   */
  const onSelectionChange: OnSelectionChangeFunc<Node<NodeData>, Edge<EdgeData>> = useCallback(({ nodes, edges }) => {
    const newSelectedElementsIds: string[] = [];
    let entries: SelectionEntry[] = [];

    nodes.forEach((node) => {
      entries.push({ id: node.data.targetObjectId });
      newSelectedElementsIds.push(node.id);
    });
    edges.forEach((edge) => {
      if (edge.data) {
        entries.push({ id: edge.data.targetObjectId });
      }
      newSelectedElementsIds.push(edge.id);
    });

    // Update diagram-local selection
    setSelectedElementsIds((previousSelectedElementsIds) =>
      mergeNewSelectedElementIds(previousSelectedElementsIds, newSelectedElementsIds)
    );

    // Publish semantic selection globally (if any)
    if (entries.length > 0) {
      setSelection({ entries });
    }
  }, []); // The dependency array must stay empty, otherwise XYFlow will call this function if one dependency changes.

  return {
    onSelectionChange,
    selectedElementsIds,
  };
};
