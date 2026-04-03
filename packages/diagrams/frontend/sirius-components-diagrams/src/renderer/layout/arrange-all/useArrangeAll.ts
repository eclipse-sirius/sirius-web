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
import { Edge, Node, useReactFlow, useUpdateNodeInternals } from '@xyflow/react';
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
  const updateNodeInternals = useUpdateNodeInternals();
  const { fitView } = useFitView();
  const { elkLayout } = useElkLayout();

  const arrangeAll = async (layoutOptions: LayoutOptions): Promise<void> => {
    await elkLayout(getNodes(), getEdges(), layoutOptions).then(
      async (laidOutNodesWithElk: Node<NodeData, string>[]) => {
        //Removed bending point
        const edges = getEdges().map((edge: Edge<EdgeData, string>) => {
          if (edge.data?.bendingPoints) {
            edge.data.bendingPoints = null;
          }
          return edge;
        });
        //Removed custom handles for edges
        const laidOutNodesWithElkWithoutCustomHandles = laidOutNodesWithElk.map((node) => {
          const handles = node.data.connectionHandles.map((handle) => {
            return {
              ...handle,
              XYPosition: null,
            };
          });

          return {
            ...node,
            data: {
              ...node.data,
              connectionHandles: handles,
            },
          };
        });

        const diagramToLayout: RawDiagram = {
          nodes: laidOutNodesWithElkWithoutCustomHandles,
          edges,
        };
        const layoutPromise = new Promise<void>((resolve) => {
          layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
            setNodes(laidOutDiagram.nodes);
            setEdges(laidOutDiagram.edges);
            fitView({ duration: 200, nodes: laidOutDiagram.nodes });
            updateNodeInternals(laidOutDiagram.nodes.map((node) => node.id));
            synchronizeLayoutData(crypto.randomUUID(), 'layout', laidOutDiagram);
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
