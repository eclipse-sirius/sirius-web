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
import { LayoutOptions } from 'elkjs/lib/elk-api';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { useFitView } from '../../fit-to-screen/useFitView';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { useOverlap } from '../../overlap/useOverlap';
import { useElkLayout } from '../elk/useElkLayout';
import { RawDiagram } from '../layout.types';
import { useLayout } from '../useLayout';
import { useSynchronizeLayoutData } from '../useSynchronizeLayoutData';
import { UseArrangeAllValue } from './useArrangeAll.types';

const getParentPosition = (node: Node<NodeData>, allNodes: Node<NodeData>[]): [number, number] => {
  if (node.parentId) {
    const parent = allNodes.find((n) => n.id === node.parentId);
    if (parent) {
      return getParentPosition(parent, allNodes);
    }
  }
  return [node.position.x, node.position.y];
};

const computeSelectionOrigin = (nodes: Node<NodeData>[], allNodes: Node<NodeData>[]): [number, number] => {
  let minX = Infinity;
  let minY = Infinity;
  let minXNode = 0;
  let minYNode = 0;

  nodes.forEach((node) => {
    [minXNode, minYNode] = getParentPosition(node, allNodes);
    if (minXNode < minX) {
      minX = minXNode;
    }
    if (minYNode < minY) {
      minY = minYNode;
    }
  });

  return [minX, minY];
};

export const useArrangeAll = (): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { resolveNodeOverlap } = useOverlap();
  const { fitView } = useFitView();
  const { elkLayout } = useElkLayout();

  const arrangeAll = async (layoutOptions: LayoutOptions, selectedNodesIds?: string[]): Promise<void> => {
    let nodesToLayout: Node<NodeData>[] = getNodes();
    let minX: number,
      minY: number = 0;
    if (selectedNodesIds && selectedNodesIds.length > 0) {
      nodesToLayout = getNodes().filter((node) => selectedNodesIds.includes(node.id));
      [minX, minY] = computeSelectionOrigin(nodesToLayout, getNodes());
    }
    await elkLayout(nodesToLayout, getEdges(), layoutOptions).then(
      async (laidOutNodesWithElk: Node<NodeData, string>[]) => {
        if (selectedNodesIds && selectedNodesIds.length > 0) {
          let [newMinX, newMinY] = computeSelectionOrigin(laidOutNodesWithElk, getNodes());
          laidOutNodesWithElk.forEach((node) => {
            if (!node.parentId) {
              node.position.x += minX - newMinX;
              node.position.y += minY - newMinY;
            }
          });
        }
        const laidOutMovedNodeIds = laidOutNodesWithElk
          .filter((node) => !node.data.isBorderNode && !node.data.pinned)
          .map((node) => node.id);
        const edges = getEdges();
        edges
          .filter((edge) => laidOutMovedNodeIds.includes(edge.source) || laidOutMovedNodeIds.includes(edge.target))
          .forEach((edge: Edge<EdgeData, string>) => {
            if (edge.data?.bendingPoints) {
              edge.data.bendingPoints = null;
            }
          });
        const allNodes: Node<NodeData, string>[] = getNodes().map((node) => {
          const modifiedNode = laidOutNodesWithElk.find((laidOutNode) => laidOutNode.id === node.id);
          return modifiedNode ? modifiedNode : node;
        });
        const diagramToLayout: RawDiagram = {
          nodes: allNodes,
          edges: edges,
        };
        const layoutPromise = new Promise<void>((resolve) => {
          layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
            resolveNodeOverlap(
              laidOutDiagram.nodes.filter((n) => !n.data.isBorderNode),
              'horizontal'
            ) as Node<NodeData, DiagramNodeType>[];
            setNodes(laidOutDiagram.nodes);
            setEdges(laidOutDiagram.edges);
            const finalDiagram: RawDiagram = {
              nodes: laidOutDiagram.nodes,
              edges: laidOutDiagram.edges,
            };
            fitView({ duration: 200, nodes: laidOutDiagram.nodes });
            synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
            resolve();
          });
        });
        await layoutPromise;
      }
    );
  };

  return {
    arrangeAll,
  };
};
