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
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback } from 'react';
import { DraggableData } from 'react-draggable';
import { EdgeData, NodeData, OutsideLabel } from '../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../edge/MultiLabelEdge.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { UseLabelMoveValue } from './useLabelMove.types';

export const useLabelMove = (): UseLabelMoveValue => {
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const onNodeLabelMoveStop = useCallback(
    (eventData: DraggableData, nodeId: string) => {
      const nodes: Node<NodeData>[] = [...getNodes()];
      const node = nodes.find((n) => n.id === nodeId);
      if (node && node.data.outsideLabels) {
        const firstLabel: OutsideLabel | undefined = node.data.outsideLabels.BOTTOM_MIDDLE;
        if (firstLabel) {
          firstLabel.position = { x: eventData.x, y: eventData.y };
          firstLabel.movedByUser = true;
        }
      }
      const finalDiagram: RawDiagram = {
        nodes: nodes,
        edges: getEdges(),
      };
      synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
    },
    [getNodes, synchronizeLayoutData]
  );

  const onEdgeLabelMoveStop = useCallback(
    (eventData: DraggableData, edgeId: string, labelPosition: 'begin' | 'center' | 'end') => {
      const edges: Edge<MultiLabelEdgeData>[] = getEdges();
      const edge: Edge<MultiLabelEdgeData> | undefined = edges.find((e) => e.id === edgeId);
      switch (labelPosition) {
        case 'begin':
          if (edge && edge.data?.beginLabel) {
            edge.data.beginLabel.position = { x: eventData.x, y: eventData.y };
            edge.data.beginLabel.movedByUser = true;
          }
          break;
        case 'center':
          if (edge && edge.data?.label) {
            edge.data.label.position = { x: eventData.x, y: eventData.y };
            edge.data.label.movedByUser = true;
          }
          break;
        case 'end':
          if (edge && edge.data?.endLabel) {
            edge.data.endLabel.position = { x: eventData.x, y: eventData.y };
            edge.data.endLabel.movedByUser = true;
          }
          break;
      }
      const finalDiagram: RawDiagram = {
        nodes: [...getNodes()],
        edges: edges,
      };
      synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
    },
    [getEdges, synchronizeLayoutData]
  );

  return { onNodeLabelMoveStop, onEdgeLabelMoveStop };
};
