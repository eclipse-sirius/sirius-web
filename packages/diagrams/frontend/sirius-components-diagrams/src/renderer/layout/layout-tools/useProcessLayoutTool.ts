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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { Edge, Node, useReactFlow, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';
import { RawDiagram } from '../layout.types';
import { useLayout } from '../useLayout';
import { useSynchronizeLayoutData } from '../useSynchronizeLayoutData';
import { ProcessLayoutTool, UseProcessLayoutToolValue } from './useProcessLayoutTool.types';

export const getComparePositionFn = (direction: 'horizontal' | 'vertical') => {
  return (node1: Node, node2: Node) => {
    const positionNode1: XYPosition = node1.position;
    const positionNode2: XYPosition = node2.position;
    if (positionNode1 && positionNode2) {
      return direction === 'horizontal' ? positionNode1.x - positionNode2.x : positionNode1.y - positionNode2.y;
    }
    return 0;
  };
};

export const useProcessLayoutTool = (): UseProcessLayoutToolValue => {
  const { getNodes, getEdges, setNodes } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { addMessages } = useMultiToast();

  const processLayoutTool: ProcessLayoutTool = (
    selectedNodeIds: string[],
    layoutFn: (selectedNodes: Node<NodeData>[], refNode: Node<NodeData>) => Node<NodeData>[],
    sortFn: ((node1: Node, node2: Node) => number) | null = null,
    refElementId: string | null = null
  ): void => {
    const selectedNodes: Node<NodeData>[] = getNodes().filter((node) => selectedNodeIds.includes(node.id));
    const firstParent = selectedNodes[0]?.parentId;
    const sameParent: boolean = selectedNodes.reduce(
      (isSameParent, node) => isSameParent && node.parentId === firstParent,
      true
    );
    if (selectedNodes.length < 2) {
      return;
    }
    if (!sameParent) {
      addMessages([{ body: 'This tool can only be applied on elements on the same level', level: 'WARNING' }]);
      return;
    }
    if (sortFn) {
      selectedNodes.sort(sortFn);
    }

    let refNode: Node<NodeData> | undefined = selectedNodes[0];
    if (refElementId) {
      refNode = selectedNodes.find((node) => node.id === refElementId);
    }
    if (refNode) {
      const updatedNodes: Node<NodeData>[] = layoutFn(selectedNodes, refNode);
      const diagramToLayout: RawDiagram = {
        nodes: [...updatedNodes] as Node<NodeData, DiagramNodeType>[],
        edges: getEdges(),
      };
      layout(diagramToLayout, diagramToLayout, null, 'UNDEFINED', (laidOutDiagram) => {
        setNodes(laidOutDiagram.nodes);
        const finalDiagram: RawDiagram = {
          nodes: laidOutDiagram.nodes as Node<NodeData, DiagramNodeType>[],
          edges: laidOutDiagram.edges,
        };
        synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
      });
    }
  };

  return {
    processLayoutTool,
  };
};
