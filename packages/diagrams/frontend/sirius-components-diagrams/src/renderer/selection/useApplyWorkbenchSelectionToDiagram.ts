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

import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Edge, FitView, Node } from '@xyflow/react';
import { Dispatch, SetStateAction } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

export const useApplyWorkbenchSelectionToDiagram = (
  selection: Selection,
  getNodes: () => Node<NodeData>[],
  setNodes: Dispatch<SetStateAction<Node<NodeData>[]>>,
  getEdges: () => Edge<EdgeData>[],
  setEdges: Dispatch<SetStateAction<Edge<EdgeData>[]>>,
  fitView: FitView
) => {
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

  if (JSON.stringify(displayedSemanticElementsToSelect) !== JSON.stringify(semanticElementsAlreadySelectedOnDiagram)) {
    const nodesToReveal: Set<string> = new Set();
    const newNodes = getNodes().map((node) => {
      const selected = displayedSemanticElementsToSelect.includes(node.data.targetObjectId);
      const newNode = { ...node, selected };
      if (selected) {
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

    fitView({ nodes: getNodes().filter((node) => nodesToReveal.has(node.id)), maxZoom: 1.5, duration: 1000 });
  }
};
