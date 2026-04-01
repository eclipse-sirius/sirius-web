/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Edge, Node, useReactFlow, XYPosition } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { RawDiagram } from '../layout.types';
import { useLayout } from '../useLayout';
import { useSynchronizeLayoutData } from '../useSynchronizeLayoutData';
import { UseDistributeNodesOnGapValue } from './useDistributeNodesOnGap.types';

function getComparePositionFn(direction: 'horizontal' | 'vertical') {
  return (node1: Node, node2: Node) => {
    const positionNode1: XYPosition = node1.position;
    const positionNode2: XYPosition = node2.position;
    if (positionNode1 && positionNode2) {
      return direction === 'horizontal' ? positionNode1.x - positionNode2.x : positionNode1.y - positionNode2.y;
    }
    return 0;
  };
}

export const useDistributeNodesOnGap = (): UseDistributeNodesOnGapValue => {
  const distributeNodesOnGap = (direction: 'horizontal' | 'vertical') => {
    const { getNodes, getEdges, setNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
    const { synchronizeLayoutData } = useSynchronizeLayoutData();

    const { layout } = useLayout();

    return useCallback((selectedNodeIds: string[]) => {
      const selectedNodes: Node<NodeData>[] = getNodes().filter((node) => selectedNodeIds.includes(node.id));
      const firstParent = selectedNodes[0]?.parentId;
      const sameParent: boolean = selectedNodes.reduce(
        (isSameParent, node) => isSameParent && node.parentId === firstParent,
        true
      );
      if (selectedNodes.length < 3 || !sameParent) {
        return;
      }

      selectedNodes.sort(getComparePositionFn(direction));

      const firstNode = selectedNodes[0];
      const lastNode = selectedNodes[selectedNodes.length - 1];

      if (firstNode && lastNode) {
        const totalSize: number = selectedNodes
          .filter((node) => node.id !== firstNode.id && node.id !== lastNode.id)
          .reduce((total, node) => total + (direction === 'horizontal' ? node.width ?? 0 : node.height ?? 0), 0);
        const numberOfGap: number = selectedNodes.length - 1;
        const gap: number =
          ((direction === 'horizontal'
            ? lastNode.position.x - firstNode.position.x - (firstNode.width ?? 0)
            : lastNode.position.y - firstNode.position.y - (firstNode.height ?? 0)) -
            totalSize) /
          numberOfGap;
        const updatedNodes = getNodes().map((node) => {
          if (!selectedNodeIds.includes(node.id) || node.data.pinned) {
            return node;
          }

          const index: number = selectedNodes.findIndex((selectedNode) => selectedNode.id === node.id);
          const currentSelectedNode = selectedNodes[index];
          const previousNode = selectedNodes[index - 1];

          let newPosition: number = direction === 'horizontal' ? node.position.x : node.position.y;

          if (index > 0 && index < selectedNodes.length - 1 && previousNode && currentSelectedNode) {
            newPosition =
              direction === 'horizontal'
                ? previousNode.position.x + (previousNode.width ?? 0) + gap
                : previousNode.position.y + (previousNode.height ?? 0) + gap;
            currentSelectedNode.position[direction === 'horizontal' ? 'x' : 'y'] = newPosition;
          }

          return {
            ...node,
            position: {
              ...node.position,
              [direction === 'horizontal' ? 'x' : 'y']: newPosition,
            },
          };
        });
        const diagramToLayout: RawDiagram = {
          nodes: [...updatedNodes] as Node<NodeData, DiagramNodeType>[],
          edges: getEdges(),
        };
        layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
          setNodes(laidOutDiagram.nodes);
          const finalDiagram: RawDiagram = {
            nodes: laidOutDiagram.nodes,
            edges: laidOutDiagram.edges,
          };
          synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
        });
      }
    }, []);
  };

  const distributeGapHorizontally = distributeNodesOnGap('horizontal');
  const distributeGapVertically = distributeNodesOnGap('vertical');

  return {
    distributeGapHorizontally,
    distributeGapVertically,
  };
};
