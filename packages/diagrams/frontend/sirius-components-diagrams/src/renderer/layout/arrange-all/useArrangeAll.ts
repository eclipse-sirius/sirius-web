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
  const { fitView } = useFitView();
  const { elkLayout } = useElkLayout();

  const arrangeAll = async (layoutOptions: LayoutOptions, nodesIds?: string[]): Promise<void> => {
    let nodesToLayout: Node<NodeData>[] = getNodes();
    let edgesToLayout: Edge<EdgeData>[] = getEdges();
    let minX: number,
      minY: number = 0;
    if (nodesIds && nodesIds.length > 0) {
      const selectedNodes = nodesToLayout.filter((node) => nodesIds.includes(node.id));
      edgesToLayout = edgesToLayout.filter((edge) => nodesIds.includes(edge.source) && nodesIds.includes(edge.target));
      nodesToLayout = selectedNodes.map((node) => {
        const clonedNode = { ...node };
        if (clonedNode.parentId && !nodesIds.includes(clonedNode.parentId)) {
          clonedNode.parentId = undefined;
        }
        return clonedNode;
      });
      [minX, minY] = computeSelectionOrigin(nodesToLayout, getNodes());
    }
    await elkLayout(nodesToLayout, edgesToLayout, layoutOptions).then(async (laidOutDiagramWithElk: RawDiagram) => {
      let allNodes = laidOutDiagramWithElk.nodes;
      let allEdges = laidOutDiagramWithElk.edges;
      if (nodesIds && nodesIds.length > 0) {
        let [newMinX, newMinY] = computeSelectionOrigin(laidOutDiagramWithElk.nodes, getNodes());
        laidOutDiagramWithElk.nodes.forEach((node) => {
          if (!node.parentId) {
            node.position.x += minX - newMinX;
            node.position.y += minY - newMinY;
          }
        });
        allNodes = getNodes().map((node) => {
          const laidOutNode = laidOutDiagramWithElk.nodes.find((n) => n.id === node.id);
          if (laidOutNode) {
            return {
              ...node,
              position: laidOutNode.position,
            };
          }
          return node;
        });
        allEdges = getEdges().map((edge) => {
          const laidOutEdge = laidOutDiagramWithElk.edges.find((e) => e.id === edge.id);
          return laidOutEdge ? laidOutEdge : edge;
        });
      }
      const diagramToLayout: RawDiagram = {
        nodes: allNodes,
        edges: allEdges,
      };
      const layoutPromise = new Promise<void>((resolve) => {
        layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
          setNodes(laidOutDiagram.nodes);
          setEdges(laidOutDiagram.edges);
          const finalDiagram: RawDiagram = {
            nodes: laidOutDiagram.nodes,
            edges: laidOutDiagram.edges,
          };
          fitView({ duration: 200, nodes: laidOutDiagram.nodes });
          synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram, 'ACTIVATE');
          resolve();
        });
      });
      await layoutPromise;
    });
  };

  return {
    arrangeAll,
  };
};
