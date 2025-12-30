/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { Edge, Node } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseEditableEdgePathValue } from './useEditableEdgePath.types';

export const useEditableEdgePath = (): UseEditableEdgePathValue => {
  const { getEdges, getNodes, setEdges } = useStore();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const synchronizeEdgeLayoutData = useCallback(
    (edges: Edge<EdgeData>[], nodes: Node<NodeData>[]): void => {
      const finalDiagram: RawDiagram = {
        nodes: nodes,
        edges: edges,
      };
      synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
    },
    [synchronizeLayoutData]
  );

  const removeEdgeLayoutData = useCallback(
    (edgeIds: string[]): void => {
      const updatedEdges: Edge<EdgeData>[] = getEdges().map((edge) => {
        if (edgeIds.find((edgeId) => edgeId === edge.id) && edge.data) {
          return {
            ...edge,
            data: {
              ...edge.data,
              bendingPoints: null,
            },
          };
        }
        return edge;
      });
      setEdges(updatedEdges);
      synchronizeEdgeLayoutData(updatedEdges, [...getNodes()] as Node<NodeData, DiagramNodeType>[]);
    },
    [setEdges, getEdges]
  );

  return {
    synchronizeEdgeLayoutData,
    removeEdgeLayoutData,
  };
};
