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
import { useCallback, useContext, useState } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseDiagramSelectionValue } from './useDiagramSelection.types';
/**
 * Configures React Flow so that when the selection changes on the diagram, the global selection is updated.
 */
export const useDiagramSelection = (): UseDiagramSelectionValue => {
  const { setSelection } = useSelection();
  const [selectedElementsIds, setSelectedElementsIds] = useState<string[]>([]);
  const { diagramId } = useContext<DiagramContextValue>(DiagramContext);

  const setSelectedElementsIdsInTheCorrectOrder = (
    previousSelectedElementsIds: string[],
    newSelectedElementId: string[]
  ) => {
    if (newSelectedElementId.length === 0) {
      return [];
    } else {
      return previousSelectedElementsIds
        .filter((id) => newSelectedElementId.includes(id))
        .concat(newSelectedElementId.filter((id) => !previousSelectedElementsIds.includes(id)));
    }
  };

  //The dependency array must stay empty, otherwise XYFlow will call this function if one dependency changes.
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

    if (entries.length === 0) {
      entries = [
        {
          id: diagramId,
        },
      ];
    }

    setSelectedElementsIds((previousSelectedElementsIds) =>
      setSelectedElementsIdsInTheCorrectOrder(previousSelectedElementsIds, newSelectedElementsIds)
    );
    setSelection({ entries });
  }, []);

  return {
    onSelectionChange,
    selectedElementsIds,
  };
};
