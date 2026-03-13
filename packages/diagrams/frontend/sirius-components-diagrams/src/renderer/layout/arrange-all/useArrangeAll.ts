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

const computeSelectionOrigin = (nodes: Node<NodeData, string>[]): [number, number] => {
  let minX = Infinity;
  let minY = Infinity;

  nodes.forEach((node) => {
    if (node && node.width && node.height) {
      if (node.position.x < minX) minX = node.position.x;
      if (node.position.y < minY) minY = node.position.y;
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

  const arrangeAll = async (layoutOptions: LayoutOptions): Promise<void> => {
    await elkLayout(getNodes(), getEdges(), layoutOptions).then(
      async (laidOutNodesWithElk: Node<NodeData, string>[]) => {
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
        const diagramToLayout: RawDiagram = {
          nodes: laidOutNodesWithElk,
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

export const useArrangeSelection = (): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { resolveNodeOverlap } = useOverlap();
  const { fitView } = useFitView();
  const { elkLayout } = useElkLayout();

  const arrangeAll = async (layoutOptions: LayoutOptions): Promise<void> => {
    const selectedNodes: Node<NodeData>[] = getNodes().filter((n) => n.selected);
    const [minX, minY] = computeSelectionOrigin(selectedNodes);
    await elkLayout(selectedNodes, getEdges(), layoutOptions).then(
      async (laidOutNodesWithElk: Node<NodeData, string>[]) => {
        const [newMinX, newMinY] = computeSelectionOrigin(laidOutNodesWithElk);
        laidOutNodesWithElk.forEach((node) => {
          node.position.x += minX - newMinX;
          node.position.y += minY - newMinY;
        });
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
