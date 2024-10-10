/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Node, NodeChange, NodeDimensionChange, NodePositionChange } from '@xyflow/react';
import { useCallback } from 'react';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useLayout } from '../layout/useLayout';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseLayoutOnBoundsChangeValue } from './useLayoutOnBoundsChange.types';

export const useLayoutOnBoundsChange = (refreshEventPayloadId: string): UseLayoutOnBoundsChangeValue => {
  const { layout } = useLayout();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { getEdges, getNodes } = useStore();

  const isMoveFinished = (
    change: NodeChange<Node<NodeData>>,
    nodes: Node<NodeData>[]
  ): change is NodePositionChange => {
    return (
      change.type === 'position' &&
      typeof change.dragging === 'boolean' &&
      !change.dragging &&
      hasDropBeenTriggered(nodes)
    );
  };
  const isResizeFinished = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange =>
    change.type === 'dimensions' && typeof change.resizing === 'boolean' && !change.resizing;

  const isBoundsChangeFinished = (
    changes: NodeChange<Node<NodeData>>[],
    nodes: Node<NodeData>[]
  ): NodeChange<Node<NodeData>> | undefined => {
    return changes.find((change) => isMoveFinished(change, nodes) || isResizeFinished(change));
  };

  const hasDropBeenTriggered = (nodes: Node<NodeData>[]): boolean => {
    const targetNode = nodes.find((node) => node.data.isDropNodeTarget);
    const draggedNode = nodes.find((node) => node.dragging);
    const isDropOnNode: boolean = !!targetNode;

    const isDropOnSameParent: boolean =
      isDropOnNode &&
      !!draggedNode?.parentId &&
      draggedNode.parentId === targetNode?.id &&
      draggedNode?.type !== 'iconLabelNode';

    const isDropFromDiagramToDiagram: boolean = !isDropOnNode && !draggedNode?.parentId;

    return isDropOnSameParent || isDropFromDiagramToDiagram || !!draggedNode?.data.isBorderNode;
  };

  const updateNodeResizeByUserState = (
    changes: NodeChange<Node<NodeData>>[],
    nodes: Node<NodeData, DiagramNodeType>[]
  ): Node<NodeData, DiagramNodeType>[] => {
    return nodes.map((node) => {
      if (changes.filter(isResizeFinished).find((dimensionChange) => dimensionChange.id === node.id)) {
        return {
          ...node,
          data: {
            ...node.data,
            resizedByUser: true,
          },
        };
      }
      return node;
    });
  };

  const layoutOnBoundsChange = useCallback(
    (changes: NodeChange<Node<NodeData>>[], nodes: Node<NodeData, DiagramNodeType>[]): void => {
      const change = isBoundsChangeFinished(changes, getNodes());
      if (change) {
        const updatedNodes = updateNodeResizeByUserState(changes, nodes);

        const diagramToLayout: RawDiagram = {
          nodes: updatedNodes,
          edges: getEdges(),
        };

        layout(diagramToLayout, diagramToLayout, null, (laidOutDiagram) => {
          updatedNodes.map((node) => {
            const existingNode = laidOutDiagram.nodes.find((laidoutNode) => laidoutNode.id === node.id);
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

          const finalDiagram: RawDiagram = {
            nodes: updatedNodes,
            edges: laidOutDiagram.edges,
          };

          synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
        });
      }
    },
    [refreshEventPayloadId, synchronizeLayoutData, getNodes]
  );

  return { layoutOnBoundsChange };
};
