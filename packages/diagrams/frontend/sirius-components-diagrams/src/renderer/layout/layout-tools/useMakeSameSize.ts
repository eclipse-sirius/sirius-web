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
import { Edge, Node, useReactFlow } from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { RawDiagram } from '../layout.types';
import { useLayout } from '../useLayout';
import { useSynchronizeLayoutData } from '../useSynchronizeLayoutData';
import { UseMakeSameSizeValue } from './useMakeSameSize.types';

export const useMakeSameSize = (): UseMakeSameSizeValue => {
  const { getNodes, getNode, getEdges, setNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const makeNodesSameSize = useCallback((selectedNodeIds: string[], refElementId: string) => {
    const refNode = getNode(refElementId);

    if (refNode) {
      const updatedNodes = getNodes().map((node) => {
        if (!selectedNodeIds.includes(node.id) || node.data.nodeDescription?.userResizable === 'NONE') {
          return node;
        }

        return {
          ...node,
          width: refNode.width,
          height: refNode.height,
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      });
      const diagramToLayout: RawDiagram = {
        nodes: [...updatedNodes] as Node<NodeData, DiagramNodeType>[],
        edges: getEdges(),
      };

      layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
        const finalDiagram: RawDiagram = {
          nodes: laidOutDiagram.nodes as Node<NodeData, DiagramNodeType>[],
          edges: laidOutDiagram.edges,
        };
        setNodes(laidOutDiagram.nodes);
        synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
      });
    }
  }, []);

  return {
    makeNodesSameSize,
  };
};
