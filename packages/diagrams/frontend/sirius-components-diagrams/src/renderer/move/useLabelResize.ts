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
import { UseLabelResizeValue } from './useLabelResize.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { useCallback } from 'react';
import { useReactFlow, Node, Edge } from '@xyflow/react';
import { NodeData, EdgeData, OutsideLabel } from '../DiagramRenderer.types';
import { MultiLabelEdgeData } from '../edge/MultiLabelEdge.types';
import { RawDiagram } from '../layout/layout.types';

export const useLabelResize = (): UseLabelResizeValue => {
  const { getNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const onLabelResizeStop = useCallback(
    (nodeId: string, size: { width: number; height: number }) => {
      const nodes: Node<NodeData>[] = [...getNodes()];
      const node = nodes.find((n) => n.id === nodeId);
      if (node && node.data.outsideLabels) {
        const firstLabel: OutsideLabel | undefined = node.data.outsideLabels.BOTTOM_MIDDLE;
        if (firstLabel) {
          firstLabel.width = size.width;
          firstLabel.height = size.height;
          firstLabel.resizedByUser = true;
        }
      }
      const finalDiagram: RawDiagram = {
        nodes: nodes,
        edges: getEdges(),
      };
      synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
    },
    [synchronizeLayoutData]
  );

  const onEdgeLabelResizeStop = useCallback(
    (edgeId: string, size: { width: number; height: number }, labelPosition: 'begin' | 'center' | 'end') => {
      const edges: Edge<MultiLabelEdgeData>[] = getEdges();
      const edge: Edge<MultiLabelEdgeData> | undefined = edges.find((e) => e.id === edgeId);
      switch (labelPosition) {
        case 'begin':
          if (edge && edge.data?.beginLabel) {
            edge.data.beginLabel.width = size.width;
            edge.data.beginLabel.height = size.height;
            edge.data.beginLabel.resizedByUser = true;
          }
          break;
        case 'center':
          if (edge && edge.data?.label) {
            edge.data.label.width = size.width;
            edge.data.label.height = size.height;
            edge.data.label.resizedByUser = true;
          }
          break;
        case 'end':
          if (edge && edge.data?.endLabel) {
            edge.data.endLabel.width = size.width;
            edge.data.endLabel.height = size.height;
            edge.data.endLabel.resizedByUser = true;
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

  return {
    onLabelResizeStop,
    onEdgeLabelResizeStop,
  };
};
