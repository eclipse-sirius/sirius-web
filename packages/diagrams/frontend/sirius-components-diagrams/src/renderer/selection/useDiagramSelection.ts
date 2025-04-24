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
import { Edge, Node, useOnSelectionChange, useReactFlow, useStoreApi } from '@xyflow/react';
import { useCallback, useEffect, useState } from 'react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

// Compute a deterministic key from a selection
const selectionKey = (entries: SelectionEntry[]) => {
  return JSON.stringify(
    entries.map((selectionEntry) => selectionEntry.id).sort((id1: string, id2: string) => id1.localeCompare(id2))
  );
};

export const useDiagramSelection = (onShiftSelection: boolean): void => {
  const { selection, setSelection } = useSelection();
  const [shiftSelection, setShiftSelection] = useState<SelectionEntry[]>([]);

  const { fitView } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { getNodes, setNodes, getEdges, setEdges } = useStore();

  // Called when the workbench-level selection is changed.
  // Apply it on our diagram by selecting exactly the diagram elements
  // present which correspond to the workbench-selected semantic elements.
  useEffect(() => {
    const allDiagramElements = [...getNodes(), ...getEdges()];
    const displayedSemanticElements: Set<string> = new Set([
      ...getNodes().map((node) => node.data.targetObjectId),
      ...getEdges().map((edge) => edge.data?.targetObjectId ?? ''),
    ]);
    const displayedSemanticElementsToSelect = selection.entries
      .map((entry) => entry.id)
      .filter((id) => displayedSemanticElements.has(id))
      .sort((id1: string, id2: string) => id1.localeCompare(id2));

    const semanticElementsAlreadySelectedOnDiagram = allDiagramElements
      .filter((element) => element.selected)
      .map((element) => element.data?.targetObjectId ?? '')
      .sort((id1: string, id2: string) => id1.localeCompare(id2));

    if (
      JSON.stringify(displayedSemanticElementsToSelect) !== JSON.stringify(semanticElementsAlreadySelectedOnDiagram)
    ) {
      const nodesToReveal: Set<string> = new Set();
      const newNodes = getNodes().map((node) => {
        const shouldSelect = displayedSemanticElementsToSelect.includes(node.data.targetObjectId) && !node.hidden;
        if (shouldSelect) {
          nodesToReveal.add(node.id);
        }
        if (shouldSelect !== node.selected) {
          return {
            ...node,
            selected: shouldSelect,
          };
        } else {
          return node;
        }
      });
      const newEdges = getEdges().map((edge) => {
        const shouldSelect =
          displayedSemanticElementsToSelect.includes(edge.data ? edge.data.targetObjectId : '') && !edge.hidden;
        if (shouldSelect) {
          nodesToReveal.add(edge.source);
          nodesToReveal.add(edge.target);
        }
        if (shouldSelect !== edge.selected) {
          return {
            ...edge,
            selected: shouldSelect,
          };
        } else {
          return edge;
        }
      });

      setEdges(newEdges);
      setNodes(newNodes);
      const zoom = store.getState().transform[2];

      if (nodesToReveal.size > 0) {
        fitView({
          nodes: getNodes().filter((node) => nodesToReveal.has(node.id)),
          minZoom: zoom,
          maxZoom: zoom,
          duration: 1000,
        });
      }
    }
  }, [selection]);

  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const onChange = useCallback(
    ({ nodes, edges }) => {
      const semanticElementsDisplayedOnDiagram: Set<string> = new Set([
        ...store.getState().nodes.map((node) => node.data.targetObjectId),
        ...store.getState().edges.map((edge) => edge.data?.targetObjectId ?? ''),
      ]);

      const semanticElementsSelectedOnDiagram: Set<string> = new Set([
        ...nodes.map((node) => node.data.targetObjectId),
        ...edges.map((edge) => edge.data?.targetObjectId ?? ''),
      ]);

      const semanticElementsUnselectedOnDiagram: Set<string> = new Set(
        [...semanticElementsDisplayedOnDiagram].filter((id) => !semanticElementsSelectedOnDiagram.has(id))
      );

      const nextSemanticElementsToSelect: Set<string> = new Set(
        [...semanticElementsSelectedOnDiagram].filter((id) => !semanticElementsUnselectedOnDiagram.has(id))
      );

      const selectionEntriesFromDiagram: SelectionEntry[] = [...nodes, ...edges].map((node) => {
        const { targetObjectId } = node.data;
        return {
          id: targetObjectId,
        };
      });
      const selectionEntriesFromWorkbench: SelectionEntry[] = selection.entries.filter((entry) =>
        nextSemanticElementsToSelect.has(entry.id)
      );

      const nextSelectionEntries = [...selectionEntriesFromDiagram];
      selectionEntriesFromWorkbench.forEach((candidate) => {
        if (!nextSelectionEntries.find((entry) => entry.id === candidate.id)) {
          nextSelectionEntries.push(candidate);
        }
      });

      if (selectionKey(nextSelectionEntries) !== selectionKey(selection.entries)) {
        if (onShiftSelection) {
          setShiftSelection(nextSelectionEntries);
        } else {
          setSelection({ entries: nextSelectionEntries });
        }
      }
    },
    [selection, onShiftSelection, store]
  );

  useOnSelectionChange({ onChange });

  useEffect(() => {
    if (shiftSelection.length > 0 && !onShiftSelection) {
      setSelection({ entries: shiftSelection });
      setShiftSelection([]);
    }
  }, [onShiftSelection]);
};
