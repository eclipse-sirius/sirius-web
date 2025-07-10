/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Edge, Node, useStoreApi, useUpdateNodeInternals } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { UseHandlesLayoutValue } from './useHandlesLayout.types';

export const useHandlesLayout = (): UseHandlesLayoutValue => {
  const { getEdges, setNodes } = useStore();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const updateNodeInternals = useUpdateNodeInternals();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();

  const synchronizeNodeLayoutData = useCallback(
    (nodes: Node<NodeData>[]): void => {
      const finalDiagram: RawDiagram = {
        nodes: nodes,
        edges: getEdges(),
      };
      synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
    },
    [getEdges, synchronizeLayoutData]
  );

  const getNodeWithoutHandles = (node: Node<NodeData>, nodeIdsToConsider: string[], edgeId: string): Node<NodeData> => {
    //Remove only the handles from the edge
    if (nodeIdsToConsider.includes(node.id)) {
      const handles = node.data.connectionHandles.map((handle) => {
        if (edgeId === handle.edgeId) {
          return {
            ...handle,
            XYPosition: null,
          };
        } else {
          return handle;
        }
      });

      return {
        ...node,
        data: {
          ...node.data,
          connectionHandles: handles,
        },
      };
    } else {
      return node;
    }
  };

  const removeNodeHandleLayoutData = useCallback(
    (nodeIds: string[], edgeId: string): void => {
      const nodes = store.getState().nodes.map((previousNode) => {
        return getNodeWithoutHandles(previousNode, nodeIds, edgeId);
      });

      setNodes((previousNodes) =>
        previousNodes.map((previousNode) => {
          return getNodeWithoutHandles(previousNode, nodeIds, edgeId);
        })
      );

      updateNodeInternals(nodeIds);
      synchronizeNodeLayoutData(nodes);
    },
    [synchronizeNodeLayoutData]
  );

  return {
    removeNodeHandleLayoutData,
  };
};
