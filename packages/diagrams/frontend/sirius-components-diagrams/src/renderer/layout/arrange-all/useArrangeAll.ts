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

export const useArrangeAll = (): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
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
