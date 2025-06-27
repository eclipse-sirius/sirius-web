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
  const { setNodes } = useStore();
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
        const candidateEdge = edgesToUpdate.find(
          (edge) => edge.source === previousNode.id || edge.target === previousNode.id
        );
        const isNodeCandidate = nodesToUpdate.map((node) => node.id).find((nodeId) => nodeId === previousNode.id);
        if (isNodeCandidate && candidateEdge) {
          const candidateChange = changes.find((change) => change['id'] === candidateEdge.id);
          if (candidateChange) {
            const newHandles = previousNode.data.connectionHandles.map((handle) => {
              if (handle.edgeId === candidateChange['id']) {
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
  };

  return { onEdgeSelectedChange };
};
