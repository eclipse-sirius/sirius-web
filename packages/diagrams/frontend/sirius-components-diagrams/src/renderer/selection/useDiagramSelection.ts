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
        const selected = displayedSemanticElementsToSelect.includes(node.data.targetObjectId);
        const newNode = { ...node, selected };
        if (selected === node.selected) {
          nodesToReveal.add(newNode.id);
        }
        return newNode;
      });
      const newEdges = getEdges().map((edge) => {
        const selected = displayedSemanticElementsToSelect.includes(edge.data ? edge.data.targetObjectId : '');
        const newEdge = { ...edge, selected };
        if (selected) {
          // React Flow does not support "fit on edge", so include its source & target nodes
          // to ensure the edge is visible and in context
          nodesToReveal.add(newEdge.source);
          nodesToReveal.add(newEdge.target);
        }
        return newEdge;
      });

      setEdges(newEdges);
      setNodes(newNodes);

      //Set the focus in order to use direct edit
      const selectedNode = newNodes.filter((node) => node.selected);
      const selectedEdges = newEdges.filter((edge) => edge.selected);
      if (selectedNode.length + selectedEdges.length === 1) {
        const selectedElement = selectedNode[0] || selectedEdges[0];
        if (selectedElement) {
          let domElement = document.querySelector(`[data-id='${selectedElement.id}']`);
          if (domElement) {
            (domElement as HTMLElement).focus();
          }
        }
      }

      fitView({ nodes: getNodes().filter((node) => nodesToReveal.has(node.id)), maxZoom: 1.5, duration: 1000 });
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

      const semanticElementsSelectedInWorkbench: Set<string> = new Set(
        selection.entries
          .filter((entry) => entry.kind.startsWith('siriusComponents://semantic?'))
          .map((entry) => entry.id)
      );

      const nextSemanticElementsToSelect: Set<string> = new Set(
        [...semanticElementsSelectedOnDiagram, ...semanticElementsSelectedInWorkbench].filter(
          (id) => !semanticElementsUnselectedOnDiagram.has(id)
        )
      );

      const selectionEntriesFromDiagram: SelectionEntry[] = [...nodes, ...edges].map((node) => {
        const { targetObjectId, targetObjectKind } = node.data;
        return {
          id: targetObjectId,
          kind: targetObjectKind,
        };
      });
      const selectionEntriesFromWorkbench: SelectionEntry[] = selection.entries.filter(
        (entry) => entry.kind.startsWith('siriusComponents://semantic?') && nextSemanticElementsToSelect.has(entry.id)
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
