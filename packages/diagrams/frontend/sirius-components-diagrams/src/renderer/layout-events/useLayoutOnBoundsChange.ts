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

import { useCallback } from 'react';
import { Node, NodeChange, NodeDimensionChange, NodePositionChange } from 'reactflow';
import { useStore } from '../../representation/useStore';
import { NodeData } from '../DiagramRenderer.types';
import { useDropNode } from '../dropNode/useDropNode';
import { RawDiagram } from '../layout/layout.types';
import { useLayout } from '../layout/useLayout';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseLayoutOnBoundsChangeValue } from './useLayoutOnBoundsChange.types';

export const useLayoutOnBoundsChange = (refreshEventPayloadId: string): UseLayoutOnBoundsChangeValue => {
  const { layout } = useLayout();
  const { hasDroppedNodeParentChanged } = useDropNode();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { getEdges } = useStore();

  const isMoveFinished = (change: NodeChange): change is NodePositionChange => {
    return (
      change.type === 'position' &&
      typeof change.dragging === 'boolean' &&
      !change.dragging &&
      !hasDroppedNodeParentChanged()
    );
  };
  const isResizeFinished = (change: NodeChange): change is NodeDimensionChange =>
    change.type === 'dimensions' && typeof change.resizing === 'boolean' && !change.resizing;

  const isBoundsChangeFinished = (changes: NodeChange[]): NodeChange | undefined => {
    return changes.find((change) => isMoveFinished(change) || isResizeFinished(change));
  };

  const updateNodeResizeByUserState = (
    changes: NodeChange[],
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
    (changes: NodeChange[], nodes: Node<NodeData, DiagramNodeType>[]): void => {
      const change = isBoundsChangeFinished(changes);
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
    [refreshEventPayloadId, synchronizeLayoutData]
  );

  return { layoutOnBoundsChange };
};
