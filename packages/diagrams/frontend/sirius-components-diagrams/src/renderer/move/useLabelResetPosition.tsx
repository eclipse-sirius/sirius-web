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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../edge/MultiLabelEdge.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseLabelResetPositionValue } from './useLabelResetPosition.types';

export const useLabelResetPosition = (): UseLabelResetPositionValue => {
  const { getEdges, getNodes, setEdges, setNodes } = useStore();
  const { nodeLookup, edgeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const synchronizeDiagramLayoutData = useCallback(
    (edges: Edge<EdgeData>[], nodes: Node<NodeData>[]): void => {
      const finalDiagram: RawDiagram = {
        nodes: nodes,
        edges: edges,
      };
      synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
    },
    [synchronizeLayoutData]
  );

  const removeEdgeLabelLayoutData = useCallback(
    (edgeId: string): void => {
      let edges: Edge<MultiLabelEdgeData>[] = getEdges();
      edges = edges.map((previousEdge) => {
        if (previousEdge.id === edgeId) {
          if (previousEdge && previousEdge.data && previousEdge.data.beginLabel) {
            previousEdge.data.beginLabel.position = { x: 0, y: 0 };
          }
          if (previousEdge && previousEdge.data && previousEdge.data.label) {
            previousEdge.data.label.position = { x: 0, y: 0 };
          }
          if (previousEdge && previousEdge.data && previousEdge.data.endLabel) {
            previousEdge.data.endLabel.position = { x: 0, y: 0 };
          }
        }
        return previousEdge;
      });

      setEdges(edges);
      synchronizeDiagramLayoutData(edges, [...getNodes()] as Node<NodeData, DiagramNodeType>[]);
    },
    [getEdges, getNodes]
  );

  const removeNodeLabelLayoutData = useCallback(
    (nodeId: string): void => {
      const nodes = getNodes().map((previousNode) => {
        if (previousNode.id === nodeId) {
          if (previousNode.data.outsideLabels.BOTTOM_MIDDLE) {
            previousNode.data.outsideLabels.BOTTOM_MIDDLE.position = { x: 0, y: 0 };
          }
        }
        return previousNode;
      });
      setNodes(nodes);
      synchronizeDiagramLayoutData(getEdges(), nodes as Node<NodeData, DiagramNodeType>[]);
    },
    [getEdges, getNodes]
  );

  const removeOutsideLabelLayoutData = (diagramElementId: string) => {
    const node = nodeLookup.get(diagramElementId);
    if (node) {
      removeNodeLabelLayoutData(diagramElementId);
    } else {
      const edge = edgeLookup.get(diagramElementId);
      if (edge) {
        removeEdgeLabelLayoutData(diagramElementId);
      }
    }
  };

  return {
    removeOutsideLabelLayoutData,
  };
};
