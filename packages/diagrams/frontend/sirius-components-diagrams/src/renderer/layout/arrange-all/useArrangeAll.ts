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
import { useArrangeOnGroups } from './useArrangeOnGroups';
import { GQLLayoutGroup } from './useLayoutGroups.types';

export const useArrangeAll = (): UseArrangeAllValue => {
  const { getNodes, getEdges, setNodes, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { fitView } = useFitView();
  const { elkLayout } = useElkLayout();

  const arrangeAll = async (layoutOptions: LayoutOptions, groups?: GQLLayoutGroup[]): Promise<void> => {
    let finalNodes = getNodes();
    let finalEdges = getEdges();
    if (groups && groups.length > 0) {
      const diagramGroup = await useArrangeOnGroups(finalNodes, finalEdges, groups, layoutOptions, elkLayout);
      finalNodes = diagramGroup.nodes;
      finalEdges = diagramGroup.edges;
    } else {
      const laidOutDiagramWithElk = await elkLayout(finalNodes, getEdges(), layoutOptions);
      finalNodes = laidOutDiagramWithElk.nodes;
      finalEdges = laidOutDiagramWithElk.edges;
    }
    const diagramToLayout: RawDiagram = {
      nodes: finalNodes,
      edges: finalEdges,
    };
    await new Promise<void>((resolve) => {
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
  };

  return {
    arrangeAll,
  };
};
