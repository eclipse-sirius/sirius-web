/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { Node, NodeChange, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { useDropNode } from '../dropNode/useDropNode';
import { RawDiagram } from '../layout/layout.types';
import { useLayout } from '../layout/useLayout';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { UseLayoutOnBoundsChangeValue } from './useLayoutOnBoundsChange.types';

export const useLayoutOnBoundsChange = (refreshEventPayloadId: string): UseLayoutOnBoundsChangeValue => {
  const { getEdges, setNodes, setEdges } = useReactFlow<NodeData, EdgeData>();
  const { layout } = useLayout();
  const { hasDroppedNodeParentChanged } = useDropNode();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();

  const isMoveFinished = (change: NodeChange): boolean => {
    return (
      change.type === 'position' &&
      typeof change.dragging === 'boolean' &&
      !change.dragging &&
      !hasDroppedNodeParentChanged()
    );
  };
  const isResizeFinished = (change: NodeChange): boolean =>
    change.type === 'dimensions' && typeof change.resizing === 'boolean' && !change.resizing;

  const isBoundsChangeFinished = (changes: NodeChange[]): NodeChange | undefined => {
    return changes.find((change) => isMoveFinished(change) || isResizeFinished(change));
  };

  const layoutOnBoundsChange = (changes: NodeChange[], nodes: Node<NodeData, DiagramNodeType>[]): void => {
    const change = isBoundsChangeFinished(changes);
    if (change) {
      const diagramToLayout: RawDiagram = {
        nodes,
        edges: getEdges(),
      };

      layout(diagramToLayout, diagramToLayout, (laidOutDiagram) => {
        nodes.map((node) => {
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
        setNodes(nodes);
        setEdges(laidOutDiagram.edges);
        const finalDiagram: RawDiagram = {
          nodes: nodes,
          edges: laidOutDiagram.edges,
        };

        synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
      });
    }
  };

  return { layoutOnBoundsChange };
};
