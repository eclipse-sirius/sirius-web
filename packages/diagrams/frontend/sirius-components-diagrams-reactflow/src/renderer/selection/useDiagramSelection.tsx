/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { Selection, SelectionEntry } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  EdgeChange,
  EdgeSelectionChange,
  NodeChange,
  NodeSelectionChange,
  OnEdgesChange,
  OnNodesChange,
  useReactFlow,
  useStoreApi,
} from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../edge/MultiLabelEdge.types';
import { useDiagramPalette } from '../palette/useDiagramPalette';
import { UseDiagramSelectionValue } from './useDiagramSelection.types';

const isNodeSelectChange = (change: NodeChange): change is NodeSelectionChange => change.type === 'select';
const isEdgeSelectChange = (change: EdgeChange): change is EdgeSelectionChange => change.type === 'select';

export const useDiagramSelection = (
  selection: Selection,
  setSelection: (selection: Selection) => void
): UseDiagramSelectionValue => {
  const { hideDiagramPalette } = useDiagramPalette();
  const store = useStoreApi();
  const reactFlowInstance = useReactFlow<NodeData, EdgeData>();
  const { getNodes, getEdges } = useReactFlow<NodeData, MultiLabelEdgeData>();

  useEffect(() => {
    const selectionEntryIds = selection.entries.map((entry) => entry.id);
    const edgesMatchingWorkbenchSelection = getEdges().filter((edge) =>
      selectionEntryIds.includes(edge.data ? edge.data.targetObjectId : '')
    );
    const nodesMatchingWorkbenchSelection = getNodes().filter((node) =>
      selectionEntryIds.includes(node.data.targetObjectId)
    );

    const alreadySelectedNodesMatchingWorkbenchSelection = nodesMatchingWorkbenchSelection.filter(
      (node) => node.selected
    );
    const firstNodeMatchingWorkbenchSelection =
      alreadySelectedNodesMatchingWorkbenchSelection[0] ?? nodesMatchingWorkbenchSelection[0];

    const alreadySelectedEdgesMatchingWorkbenchSelection = edgesMatchingWorkbenchSelection.filter(
      (edge) => edge.selected
    );
    const firstEdgeMatchingWorkbenchSelection =
      alreadySelectedEdgesMatchingWorkbenchSelection[0] ?? edgesMatchingWorkbenchSelection[0];

    if (firstNodeMatchingWorkbenchSelection && alreadySelectedEdgesMatchingWorkbenchSelection.length === 0) {
      const firstNodeIdMatchingWorkbenchSelection = firstNodeMatchingWorkbenchSelection.id;

      // Support single graphical selection to display the palette on node containing compartment based on the same targetObjectId.
      const reactFlowState = store.getState();
      const currentlySelectedNodes = getNodes().filter((node) => node.selected);

      const isAlreadySelected = currentlySelectedNodes
        .map((node) => node.id)
        .includes(firstNodeIdMatchingWorkbenchSelection);
      if (!isAlreadySelected) {
        reactFlowState.addSelectedNodes([firstNodeIdMatchingWorkbenchSelection]);

        const selectedNodes = reactFlowState
          .getNodes()
          .filter((node) => firstNodeIdMatchingWorkbenchSelection === node.id);
        reactFlowInstance.fitView({ nodes: selectedNodes, maxZoom: 2, duration: 1000 });
      }
    } else if (edgesMatchingWorkbenchSelection.length > 0 && firstEdgeMatchingWorkbenchSelection) {
      const firstEdgeIdMatchingWorkbenchSelection = firstEdgeMatchingWorkbenchSelection.id;

      const reactFlowState = store.getState();
      const currentlySelectedEdges = reactFlowState.edges.filter((edge) => edge.selected);

      const isAlreadySelected = currentlySelectedEdges
        .map((edge) => edge.id)
        .includes(firstEdgeIdMatchingWorkbenchSelection);

      if (!isAlreadySelected) {
        reactFlowState.addSelectedEdges([firstEdgeIdMatchingWorkbenchSelection]);

        const selectedEdges = reactFlowState.edges.filter((edge) => firstEdgeIdMatchingWorkbenchSelection === edge.id);
        // React Flow does not support "fit on edge", so fit on its source & target nodes to ensure it is visible and in context
        reactFlowInstance.fitView({
          nodes: selectedEdges
            .flatMap((edge) => [edge.source, edge.target])
            .flatMap((id) => {
              const nodes = reactFlowState.getNodes().filter((node) => node.id === id);
              return nodes;
            }),
          maxZoom: 2,
          duration: 1000,
        });
      }
    } else {
      const reactFlowState = store.getState();
      reactFlowState.unselectNodesAndEdges();
    }
  }, [selection]);

  const updateSelectionOnNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    const selectionEntries: SelectionEntry[] = changes
      .filter(isNodeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => getNodes().filter((node) => node.id === change.id))
      .map((node) => {
        const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
        return {
          id: targetObjectId,
          kind: targetObjectKind,
          label: targetObjectLabel,
        };
      });

    const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    const shouldUpdateSelection =
      selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
        .length !== selectionEntries.length;

    if (selectionEntries.length > 0 && shouldUpdateSelection) {
      setSelection({ entries: selectionEntries });
    }

    if (selectionEntries.length > 0) {
      hideDiagramPalette();
    }
  };

  const updateSelectionOnEdgesChange: OnEdgesChange = (changes: EdgeChange[]) => {
    const selectionEntries: SelectionEntry[] = changes
      .filter(isEdgeSelectChange)
      .filter((change) => change.selected)
      .flatMap((change) => getEdges().filter((edge) => edge.id === change.id))
      .map((edge) => {
        if (edge.data) {
          const { targetObjectId, targetObjectKind, targetObjectLabel } = edge.data;
          return {
            id: targetObjectId,
            kind: targetObjectKind,
            label: targetObjectLabel,
          };
        } else {
          return { id: '', kind: '', label: '' };
        }
      });

    const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    const shouldUpdateSelection =
      selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
        .length !== selectionEntries.length;

    if (selectionEntries.length > 0 && shouldUpdateSelection) {
      setSelection({ entries: selectionEntries });
    }
    if (selectionEntries.length > 0) {
      hideDiagramPalette();
    }
  };

  return {
    updateSelectionOnNodesChange,
    updateSelectionOnEdgesChange,
  };
};
