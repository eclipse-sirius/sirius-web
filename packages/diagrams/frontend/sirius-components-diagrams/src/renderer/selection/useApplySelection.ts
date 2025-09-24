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
import { Selection } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow, useStoreApi } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseApplySelectionValue } from './useApplySelection.types';
import { useRevealNodes } from './useRevealNodes';

export const useApplySelection = (): UseApplySelectionValue => {
  const { getNodes, setNodes, getEdges, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();
  const { revealNodes } = useRevealNodes();

  const hasAncestorOrSelfSelected = (nodeId: string, targetObjectId: string, selectedNodes: Set<string>) => {
    const node = nodeLookup.get(nodeId);
    if (!node) {
      return false;
    } else if (node.data.targetObjectId && selectedNodes.has(node.id)) {
      return true;
    } else if (node.parentId) {
      return hasAncestorOrSelfSelected(node.parentId, targetObjectId, selectedNodes);
    }
    return false;
  };

  const applySelection = (selection: Selection, fitSelection: boolean) => {
    const nodesToReveal: Set<string> = new Set();
    const semanticElementsSelected: Set<string> = new Set();
    const newNodes = getNodes().map((node) => {
      const shouldSelect =
        !node.hidden &&
        selection.entries.some(
          (entry) =>
            entry.id === node.data.targetObjectId &&
            (node.parentId === undefined ||
              !hasAncestorOrSelfSelected(node.parentId, node.data.targetObjectId, nodesToReveal))
        );
      if (shouldSelect) {
        nodesToReveal.add(node.id);
        semanticElementsSelected.add(node.data.targetObjectId);
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
        !edge.hidden &&
        selection.entries.some(
          (entry) => entry.id === edge.data?.targetObjectId && !semanticElementsSelected.has(edge.data?.targetObjectId)
        );
      if (shouldSelect) {
        nodesToReveal.add(edge.source);
        nodesToReveal.add(edge.target);
        if (edge.data?.targetObjectId) {
          semanticElementsSelected.add(edge.data?.targetObjectId);
        }
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

    if (fitSelection && nodesToReveal.size > 0) {
      revealNodes(getNodes().filter((node) => nodesToReveal.has(node.id)));
    }
  };
  return { applySelection };
};
