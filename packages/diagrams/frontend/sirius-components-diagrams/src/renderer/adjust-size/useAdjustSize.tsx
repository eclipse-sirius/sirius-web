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
import { Node, useReactFlow } from 'reactflow';
import { useContext } from 'react';
import { RawDiagram } from '../layout/layout.types';
import { UseAdjustSizeValue } from './useAdjustSize.types';
import { useLayout } from '../layout/useLayout';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { NodeData, EdgeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { DiagramContext } from '../../contexts/DiagramContext';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';

export const useAdjustSize = (): UseAdjustSizeValue => {
  const { layout } = useLayout();
  const { refreshEventPayloadId } = useContext<DiagramContextValue>(DiagramContext);
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<NodeData, EdgeData>();

  const adjustSize = (nodeId: string): void => {
    const nodes: Node<NodeData, string>[] = [...getNodes()] as Node<NodeData, DiagramNodeType>[];
    const targetedNode: Node<NodeData, string> | undefined = nodes.find((node) => node.id === nodeId);
    const childNodes: Node<NodeData, string>[] | [] = nodes
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
