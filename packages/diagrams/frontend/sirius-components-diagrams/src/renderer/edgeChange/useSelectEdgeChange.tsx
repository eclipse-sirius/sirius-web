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
import { Edge, EdgeChange, Node, useStoreApi } from '@xyflow/react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { UseSelectEdgeChangeValue } from './useSelectEdgeChange.types';

export const useSelectEdgeChange = (): UseSelectEdgeChangeValue => {
  const reactFlowStore = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { setNodes } = useStore();

  const onEdgeSelectedChange = (changes: EdgeChange<Edge<EdgeData>>[]) => {
    // Show / hide the handles of an edge
    let handlesToRefresh: string[] = [];
    if (changes.length === 1 && changes[0] && changes[0].type === 'select') {
      const selectedEdgeId = changes[0]['id'];
      const selectedEdge = reactFlowStore.getState().edgeLookup.get(selectedEdgeId);
      if (selectedEdge) {
        const nodeSource = reactFlowStore.getState().nodeLookup.get(selectedEdge.source);
        const nodeTarget = reactFlowStore.getState().nodeLookup.get(selectedEdge.target);
        setNodes((previousNodes) =>
          previousNodes.map((previousNode) => {
            if (nodeSource?.id === previousNode.id || nodeTarget?.id === previousNode.id) {
              if (nodeSource?.id === previousNode.id) {
                handlesToRefresh.push(nodeSource?.id || '');
              }
              if (nodeTarget?.id === previousNode.id) {
                handlesToRefresh.push(nodeTarget?.id || '');
              }
              const newHandles = previousNode.data.connectionHandles.map((handle) => {
                if (handle.edgeId === selectedEdgeId && changes[0]) {
                  return {
                    ...handle,
                    isHidden: !changes[0]['selected'],
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
            return previousNode;
          })
        );
      }
    }
  };

  return { onEdgeSelectedChange };
};
