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

export const useArrangeAll = (reactFlowWrapper: React.MutableRefObject<HTMLDivElement | null>): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { resolveNodeOverlap } = useOverlap();
  const { fitView } = useFitView();
  const { elkLayout } = useElkLayout(reactFlowWrapper);

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
            const overlapFreeLaidOutNodes: Node<NodeData, string>[] = resolveNodeOverlap(
              laidOutDiagram.nodes.filter((n) => !n.data.isBorderNode),
              'horizontal'
            ) as Node<NodeData, DiagramNodeType>[];
            laidOutNodesWithElk.map((node) => {
              const existingNode = overlapFreeLaidOutNodes.find((laidOutNode) => laidOutNode.id === node.id);
              if (existingNode) {
                return {
                  ...node,
                  position: existingNode.position,
                  width: existingNode.width,
                  height: existingNode.height,
                  style: {
                    ...node.style,
                    width: `${existingNode.width}px`,
                    height: `${existingNode.height}px`,
                  },
                };
              }
              return node;
            });
            setNodes(laidOutNodesWithElk);
            setEdges(laidOutDiagram.edges);
            const finalDiagram: RawDiagram = {
              nodes: laidOutNodesWithElk,
              edges: laidOutDiagram.edges,
            };
            fitView({ duration: 200, nodes: laidOutNodesWithElk });
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
