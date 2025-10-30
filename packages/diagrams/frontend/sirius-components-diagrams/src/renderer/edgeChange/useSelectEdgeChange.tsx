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
import { Edge, EdgeChange, InternalNode, Node, useStoreApi } from '@xyflow/react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseSelectEdgeChangeValue } from './useSelectEdgeChange.types';

export const useSelectEdgeChange = (): UseSelectEdgeChangeValue => {
  const reactFlowStore = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { setNodes, setEdges } = useStore();
  const onEdgeSelectedChange = (changes: EdgeChange<Edge<EdgeData>>[]) => {
    const edgesId = changes.filter((change) => change.type === 'select').map((change) => change['id']);
    const edgesToUpdate: Edge<EdgeData>[] = [];
    const nodesToUpdate: InternalNode<Node<NodeData>>[] = [];
    edgesId.forEach((edgeId) => {
      const edge = reactFlowStore.getState().edgeLookup.get(edgeId);
      if (edge) {
        const source = reactFlowStore.getState().nodeLookup.get(edge.source);
        const target = reactFlowStore.getState().nodeLookup.get(edge.target);
        if (source && target) {
          edgesToUpdate.push(edge);
          nodesToUpdate.push(source, target);
        }
      }
    });

    setNodes((previousNodes) =>
      previousNodes.map((previousNode) => {
        const candidateEdges = edgesToUpdate.filter(
          (edge) => edge.source === previousNode.id || edge.target === previousNode.id
        );

        const isNodeCandidate = nodesToUpdate.map((node) => node.id).includes(previousNode.id);
        if (isNodeCandidate && candidateEdges.length > 0) {
          const candidateChanges = changes.filter((change) =>
            candidateEdges.map((edge) => edge.id).includes(change['id'])
          );

          if (candidateChanges.length > 0) {
            const newHandles = previousNode.data.connectionHandles.map((handle) => {
              const candidateChange = candidateChanges.find((change) => change['id'] === handle.edgeId);
              if (candidateChange) {
                return {
                  ...handle,
                  isHidden: !candidateChange['selected'],
                };
              }
              return handle;
            });

            return {
              ...previousNode,
              data: {
                ...previousNode.data,
                connectionHandles: newHandles,
              },
            };
          }
        }

        return previousNode;
      })
    );

    const selectedEdgesIds: string[] = changes
      .filter((change) => change.type === 'select' && change.selected)
      .map((change) => change['id']);
    const unSelectedEdgesIds = changes
      .filter((change) => change.type === 'select' && !change.selected)
      .map((change) => change['id']);

    setEdges((previousEdges) =>
      previousEdges.map((previousEdge) => {
        if (selectedEdgesIds.find((id) => id === previousEdge.id)) {
          return {
            ...previousEdge,
            reconnectable: true,
          };
        } else if (unSelectedEdgesIds.find((id) => id === previousEdge.id)) {
          return {
            ...previousEdge,
            reconnectable: false,
          };
        }
        return previousEdge;
      })
    );
  };

  return { onEdgeSelectedChange };
};
