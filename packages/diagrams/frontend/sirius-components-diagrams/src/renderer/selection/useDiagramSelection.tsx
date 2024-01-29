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
import {
  EdgeChange,
  EdgeSelectionChange,
  NodeChange,
  NodeSelectionChange,
  OnEdgesChange,
  OnNodesChange,
  useKeyPress,
  useReactFlow,
} from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../edge/MultiLabelEdge.types';
import { useDiagramPalette } from '../palette/useDiagramPalette';
import { UseDiagramSelectionValue } from './useDiagramSelection.types';

const isNodeSelectChange = (change: NodeChange): change is NodeSelectionChange => change.type === 'select';
const isEdgeSelectChange = (change: EdgeChange): change is EdgeSelectionChange => change.type === 'select';

export const useDiagramSelection = (): UseDiagramSelectionValue => {
  const { hideDiagramPalette } = useDiagramPalette();
  const { selection, setSelection, addToSelection, removeFromSelection } = useSelection();
  useKeyPress();

  // const store = useStoreApi();
  // const reactFlowInstance = useReactFlow<NodeData, EdgeData>();
  const { getNodes, getEdges } = useReactFlow<NodeData, MultiLabelEdgeData>();

  // useEffect(() => {
  //   const selectionEntryIds = selection.entries.map((entry) => entry.id);
  //   const edgesMatchingWorkbenchSelection = getEdges().filter((edge) =>
  //     selectionEntryIds.includes(edge.data ? edge.data.targetObjectId : '')
  //   );
  //   const nodesMatchingWorkbenchSelection = getNodes().filter((node) =>
  //     selectionEntryIds.includes(node.data.targetObjectId)
  //   );

  //   const alreadySelectedNodesMatchingWorkbenchSelection = nodesMatchingWorkbenchSelection.filter(
  //     (node) => node.selected
  //   );
  //   const firstNodeMatchingWorkbenchSelection =
  //     alreadySelectedNodesMatchingWorkbenchSelection[0] ?? nodesMatchingWorkbenchSelection[0];

  //   const alreadySelectedEdgesMatchingWorkbenchSelection = edgesMatchingWorkbenchSelection.filter(
  //     (edge) => edge.selected
  //   );
  //   const firstEdgeMatchingWorkbenchSelection =
  //     alreadySelectedEdgesMatchingWorkbenchSelection[0] ?? edgesMatchingWorkbenchSelection[0];

  //   if (firstNodeMatchingWorkbenchSelection && alreadySelectedEdgesMatchingWorkbenchSelection.length === 0) {
  //     const firstNodeIdMatchingWorkbenchSelection = firstNodeMatchingWorkbenchSelection.id;

  //     // Support single graphical selection to display the palette on node containing compartment based on the same targetObjectId.
  //     const reactFlowState = store.getState();
  //     const currentlySelectedNodes = getNodes().filter((node) => node.selected);

  //     const isAlreadySelected = currentlySelectedNodes
  //       .map((node) => node.id)
  //       .includes(firstNodeIdMatchingWorkbenchSelection);
  //     if (!isAlreadySelected) {
  //       reactFlowState.addSelectedNodes([firstNodeIdMatchingWorkbenchSelection]);

  //       const selectedNodes = reactFlowState
  //         .getNodes()
  //         .filter((node) => firstNodeIdMatchingWorkbenchSelection === node.id);
  //       reactFlowInstance.fitView({ nodes: selectedNodes, maxZoom: 2, duration: 1000 });
  //     }
  //   } else if (edgesMatchingWorkbenchSelection.length > 0 && firstEdgeMatchingWorkbenchSelection) {
  //     const firstEdgeIdMatchingWorkbenchSelection = firstEdgeMatchingWorkbenchSelection.id;

  //     const reactFlowState = store.getState();
  //     const currentlySelectedEdges = reactFlowState.edges.filter((edge) => edge.selected);

  //     const isAlreadySelected = currentlySelectedEdges
  //       .map((edge) => edge.id)
  //       .includes(firstEdgeIdMatchingWorkbenchSelection);

  //     if (!isAlreadySelected) {
  //       reactFlowState.addSelectedEdges([firstEdgeIdMatchingWorkbenchSelection]);

  //       const selectedEdges = reactFlowState.edges.filter((edge) => firstEdgeIdMatchingWorkbenchSelection === edge.id);
  //       // React Flow does not support "fit on edge", so fit on its source & target nodes to ensure it is visible and in context
  //       reactFlowInstance.fitView({
  //         nodes: selectedEdges
  //           .flatMap((edge) => [edge.source, edge.target])
  //           .flatMap((id) => {
  //             const nodes = reactFlowState.getNodes().filter((node) => node.id === id);
  //             return nodes;
  //           }),
  //         maxZoom: 2,
  //         duration: 1000,
  //       });
  //     }
  //   } else {
  //     const reactFlowState = store.getState();
  //     reactFlowState.unselectNodesAndEdges();
  //   }
  // }, [selection]);

  const updateSelectionOnNodesChange: OnNodesChange = (changes: NodeChange[]) => {
    // if (changes[0] && isNodeSelectChange(changes[0])) {
    //   debugger;
    // }

    // - Déselectionner le diagram si on selectionne un node
    // - Soucis avec la deselection (infinite loop)
    // - Faire la même chose avec les edges

    // ----------------------

    // Cet algo va dependre, s'il est possible de mettre facilement à jour la sélection de reactFlow en fonction de la selection du workbench (algo dans le useEffect ci-dessus)
    // Quoi qu'il se passe, si on passe ici, retirer la representation de la selection
    // Il faut donc être capable de faire un removeFromSelection de la selection entry du diagramme.
    // Si c'est pas possible de construire la selection entry de la representation sans passer par une recherche dans la selection du workbench courante, ne pas oublier de mettre cette fonction dans un useCallback
    // Ensuite, ajouter ou supprimer des SelectionEntry en fonction du nodeChange
    // Faire exactement la même chose pour les edge
    // Idée pour optimiser :
    //   - Ne pas gerer la de-selection du diagramme dans `updateSelectionOnNodeChange` ou dans `updateSelectionOnEdgesChange`
    //   mais utiliser le hook reactFlow `useOnSelectionChange` qui de-selection le diagramme seulement si la le onSelectionChange contient un ajout dans la selection.
    //   Faire attention à ce que l'utilisation de ce hook n'empiette pas sur le OnPaneCkick du `DiagramRenderer` qui ajoute le diagramme à la selection.
    //   Cela serait dommage que le OnPaneClick ajoute le diagramme à la selection mais que l'utilisation de ce hook la retire parce que la selection change...

    const nodeSelectionChanges = changes.filter(isNodeSelectChange);
    nodeSelectionChanges.forEach((nodeSelectionChange) => {
      const node = getNodes().find((node) => node.id === nodeSelectionChange.id);
      if (node) {
        const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
        const selectionEntry: SelectionEntry = {
          id: targetObjectId,
          kind: targetObjectKind,
          label: targetObjectLabel,
        };
        if (nodeSelectionChange.selected) {
          addToSelection(selectionEntry);
        } else {
          removeFromSelection(selectionEntry);
        }
      }
    });

    const selectedDiagramNodes = getNodes().filter((node) => node.selected);
    console.log('Selected Nodes');
    console.log(selectedDiagramNodes);
    console.log('-----------');

    // const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    // const newSelectionEntries: SelectionEntry[] = selection.entries.reduce<SelectionEntry[]>(
    //   (previousEntries: SelectionEntry[], currentEntry: SelectionEntry) => {
    //     const updatedEntries: SelectionEntry[] = [...previousEntries];
    //     changes.filter(isNodeSelectChange).forEach(nodeSelectionChange => {
    //       const node = getNodes().find(node => node.id === nodeSelectionChange.id);
    //       if (node) {
    //         const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
    //         if (targetObjectId === currentEntry.id && nodeSelectionChange.selected) {
    //           updatedEntries.push(currentEntry);
    //         }
    //       }
    //     });
    //     return updatedEntries;
    //   },
    //   []
    // );

    // const selectionEntries: SelectionEntry[] = changes
    //   .filter(isNodeSelectChange)
    //   .filter((change) => change.selected)
    //   .flatMap((change) => getNodes().filter((node) => node.id === change.id))
    //   .map((node) => {
    //     const { targetObjectId, targetObjectKind, targetObjectLabel } = node.data;
    //     return {
    //       id: targetObjectId,
    //       kind: targetObjectKind,
    //       label: targetObjectLabel,
    //     };
    //   });

    // const currentSelectionEntryIds = selection.entries.map((selectionEntry) => selectionEntry.id);
    // const shouldUpdateSelection =
    //   selectionEntries.map((entry) => entry.id).filter((entryId) => currentSelectionEntryIds.includes(entryId))
    //     .length !== selectionEntries.length;

    // if (selectionEntries.length > 0 && shouldUpdateSelection) {
    //   setSelection({ entries: selectionEntries });
    // }

    if (nodeSelectionChanges.length > 0) {
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
