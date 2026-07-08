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
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useLayout } from '../layout/useLayout';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { ListNodeData } from '../node/ListNode.types';
import { UseAdjustSizeValue } from './useAdjustSize.types';

const isListData = (node: Node): node is Node<ListNodeData> => node.type === 'listNode';

export const useAdjustSize = (): UseAdjustSizeValue => {
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const adjustSize = (nodeIds: string[]): void => {
    const listNodeIds = getNodes()
      .filter((node) => nodeIds.includes(node.id) && isListData(node))
      .map((node) => node.id);
    const updatedNodes = getNodes().map((node) => {
      const isDirectlyReset = nodeIds.includes(node.id);
      const isChildOfResetList = node.parentId && listNodeIds.includes(node.parentId);

      if (isDirectlyReset || isChildOfResetList) {
        return {
          ...node,
          data: {
            ...node.data,
            resizedByUser: false,
          },
        };
      }
      return node;
    });

    if (nodeIds.length > 0) {
      const diagramToLayout: RawDiagram = {
        nodes: updatedNodes,
        edges: getEdges(),
      };

      layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
        const updatedNodesAfterLayout = updatedNodes.map((node) => {
          if (nodeIds.find((nodeId) => nodeId === node.id)) {
            return laidOutDiagram.nodes.find((laidOutNode) => laidOutNode.id === node.id) ?? node;
          }
          return node;
        });

        setNodes(updatedNodesAfterLayout);
        setEdges(laidOutDiagram.edges);
        const finalDiagram: RawDiagram = {
          nodes: updatedNodesAfterLayout,
          edges: laidOutDiagram.edges,
        };
        synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram, 'UNCHANGED');
      });
    }
  };

  return { adjustSize };
};
