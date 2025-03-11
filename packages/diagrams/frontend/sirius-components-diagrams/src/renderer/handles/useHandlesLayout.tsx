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
import { Node, useUpdateNodeInternals } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { UseHandlesLayoutValue } from './useHandlesLayout.types';

export const useHandlesLayout = (): UseHandlesLayoutValue => {
  const { getEdges, getNodes, setNodes } = useStore();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const updateNodeInternals = useUpdateNodeInternals();

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

  const removeNodeHandleLayoutData = useCallback(
    (nodeIds: string[], edgeId: string): void => {
      const nodes: Node<NodeData>[] = getNodes().map((node) => {
        //Remove only the handles from the edge
        if (nodeIds.includes(node.id)) {
          const handles = node.data.connectionHandles.map((handle) => {
            if (edgeId === handle.edgeId) {
              return {
                ...handle,
                XYPosition: undefined,
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
        }
        return node;
      });

      setNodes(nodes);
      updateNodeInternals(nodeIds);
      synchronizeNodeLayoutData(nodes);
    },
    [synchronizeNodeLayoutData, getNodes]
  );

  return {
    removeNodeHandleLayoutData,
  };
};
