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
import { Edge, Node } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { DiagramPayloadContext } from '../../contexts/DiagramPayloadContext';
import { DiagramPayloadContextValue } from '../../contexts/DiagramPayloadContext.types';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseEditableEdgePathValue } from './useEditableEdgePath.types';

export const useEditableEdgePath = (): UseEditableEdgePathValue => {
  const { getEdges, getNodes, setEdges } = useStore();
  const { refreshEventPayloadId } = useContext<DiagramPayloadContextValue>(DiagramPayloadContext);
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const synchronizeEdgeLayoutData = useCallback(
    (edges: Edge<EdgeData>[]): void => {
      const finalDiagram: RawDiagram = {
        nodes: [...getNodes()] as Node<NodeData, DiagramNodeType>[],
        edges: edges,
      };
      synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
    },
    [refreshEventPayloadId, getNodes, synchronizeLayoutData]
  );

  const removeEdgeLayoutData = useCallback(
    (edgeId: string): void => {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === edgeId);
      if (edge?.data) {
        edge.data.bendingPoints = null;
      }
      setEdges(edges);
      synchronizeEdgeLayoutData(edges);
    },
    [setEdges, getEdges]
  );

  return {
    synchronizeEdgeLayoutData,
    removeEdgeLayoutData,
  };
};
