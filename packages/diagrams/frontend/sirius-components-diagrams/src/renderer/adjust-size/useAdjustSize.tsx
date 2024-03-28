/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useContext } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useLayout } from '../layout/useLayout';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { UseAdjustSizeValue } from './useAdjustSize.types';

export const useAdjustSize = (): UseAdjustSizeValue => {
  const { layout } = useLayout();
  const { refreshEventPayloadId } = useContext<DiagramContextValue>(DiagramContext);
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const adjustSize = (nodeId: string): void => {
    const nodes: Node<NodeData>[] = [...getNodes()];
    const targetedNode: Node<NodeData> | undefined = nodes.find((node) => node.id === nodeId);
    const childNodes: Node<NodeData>[] | [] = nodes
      .filter((node) => node.parentNode === nodeId)
      .map((node) => {
        node.data.resizedByUser = true;
        return node;
      });
    if (targetedNode) {
      targetedNode.data.resizedByUser = false;
      const diagramToLayout: RawDiagram = {
        nodes: [targetedNode, ...childNodes],
        edges: getEdges(),
      };

      layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
        nodes.map((node) => {
          if (node.id === targetedNode.id) {
            return laidOutDiagram.nodes.find((laidOutNode) => laidOutNode.id === targetedNode.id) ?? node;
          }
          return node;
        });
        setNodes(nodes);
        setEdges(laidOutDiagram.edges);
        const finalDiagram: RawDiagram = {
          nodes: nodes,
          edges: laidOutDiagram.edges,
        };
        synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
        hideDiagramElementPalette();
      });
    }
  };

  return { adjustSize };
};
