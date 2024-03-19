/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { useCallback, useEffect } from 'react';
import { useOnSelectionChange, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

export const useDiagramSelection = (): void => {
  const { selection, setSelection } = useSelection();

  const { getNodes, setNodes, getEdges, setEdges, fitView } = useReactFlow<NodeData, EdgeData>();

  useEffect(() => {
    const diagramElementIds: string[] = [
      ...getNodes().map((node) => node.data.targetObjectId),
      ...getEdges().map((edge) => edge.data?.targetObjectId ?? ''),
    ];
    const selectionDiagramEntryIds = selection.entries
      .map((entry) => entry.id)
      .filter((id) => diagramElementIds.includes(id))
      .sort((id1: string, id2: string) => id1.localeCompare(id2));
    const selectedDiagramElementIds = [...getNodes(), ...getEdges()]
      .filter((element) => element.selected)
      .map((element) => element.data?.targetObjectId ?? '')
      .sort((id1: string, id2: string) => id1.localeCompare(id2));
    if (JSON.stringify(selectionDiagramEntryIds) !== JSON.stringify(selectedDiagramElementIds)) {
      const newNodeSelection = getNodes().map((node) => {
        return { ...node, selected: selectionDiagramEntryIds.includes(node.data.targetObjectId) };
      });
      const newEdgeSelection = getEdges().map((edge) => {
        return { ...edge, selected: selectionDiagramEntryIds.includes(edge.data ? edge.data.targetObjectId : '') };
      });
      setNodes(newNodeSelection);
      setEdges(newEdgeSelection);
      const fitViewNodes = newNodeSelection.filter((node) => {
        // React Flow does not support "fit on edge", so fit on its source & target nodes to ensure it is visible and in context
        return (
          node.selected ||
          newEdgeSelection
            .filter((edge) => edge.selected)
            .flatMap((edge) => [edge.source, edge.target])
            .includes(node.id)
        );
      });
      fitView({ nodes: fitViewNodes, maxZoom: 1.5, duration: 1000 });
    }
  }, [selection]);

  const onSelectionChange = useCallback(
    ({ nodes, edges }) => {
      const selectionEntries: SelectionEntry[] = [...nodes, ...edges]
        .filter((element) => element.selected)
        .map((node) => {
          const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
          return {
            id: targetObjectId,
            kind: targetObjectKind,
            label: targetObjectLabel,
          };
        });
      const selectionDiagramEntryIds = selection.entries
        .map((selectionEntry) => selectionEntry.id)
        .sort((id1: string, id2: string) => id1.localeCompare(id2));
      const selectedDiagramElementIds = selectionEntries
        .map((entry) => entry.id)
        .sort((id1: string, id2: string) => id1.localeCompare(id2));

      if (JSON.stringify(selectedDiagramElementIds) !== JSON.stringify(selectionDiagramEntryIds)) {
        setSelection({ entries: selectionEntries });
      }
    },
    [selection]
  );
  useOnSelectionChange({
    onChange: onSelectionChange,
  });
};
