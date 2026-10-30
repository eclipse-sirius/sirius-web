/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { NodeData } from '../DiagramRenderer.types';

const isMoveFinished = (change: NodeChange<Node<NodeData>>, nodes: Node<NodeData>[]): change is NodePositionChange => {
  return (
    change.type === 'position' &&
    typeof change.dragging === 'boolean' &&
    !change.dragging &&
    hasDropBeenTriggered(nodes)
  );
};
const isResizeFinished = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange =>
  change.type === 'dimensions' && typeof change.resizing === 'boolean' && !change.resizing;

export const isBoundsChangeFinished = (
  changes: NodeChange<Node<NodeData>>[],
  nodes: Node<NodeData>[]
): NodeChange<Node<NodeData>> | undefined => {
  return changes.find((change) => isMoveFinished(change, nodes) || isResizeFinished(change));
};

const hasDropBeenTriggered = (nodes: Node<NodeData>[]): boolean => {
  const targetNode = nodes.find((node) => node.data.isDropNodeTarget);
  const draggedNode = nodes.find((node) => node.dragging);
  const isDropOnNode: boolean = !!targetNode;
  const isMovableIconLabelNode: boolean = draggedNode?.type === 'iconLabelNode' && targetNode?.type === 'freeFormNode';

  const isDropOnSameParent: boolean =
    isDropOnNode &&
    !!draggedNode?.parentId &&
    draggedNode.parentId === targetNode?.id &&
    (draggedNode.type !== 'iconLabelNode' || isMovableIconLabelNode);

  const isDropFromDiagramToDiagram: boolean = !isDropOnNode && !draggedNode?.parentId;

  return isDropOnSameParent || isDropFromDiagramToDiagram || !!draggedNode?.data.isBorderNode;
};
