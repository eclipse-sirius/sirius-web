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
import {
  EdgeChange,
  EdgeSelectionChange,
  Node,
  NodeChange,
  NodeDimensionChange,
  NodePositionChange,
  NodeReplaceChange,
  NodeSelectionChange,
} from '@xyflow/react';

export const isPositionChange = (change: NodeChange): change is NodePositionChange => {
  return change.type === 'position';
};

export const isDimensionChange = (change: NodeChange): change is NodeDimensionChange => {
  return change.type === 'dimensions';
};

export const isNodeSelectionChange = (change: NodeChange): change is NodeSelectionChange => {
  return change.type === 'select';
};

export const isEdgeSelectionChange = (change: EdgeChange): change is EdgeSelectionChange => {
  return change.type === 'select';
};

export const isReplaceChange = <NodeType extends Node>(
  change: NodeChange<NodeType>
): change is NodeReplaceChange<NodeType> => {
  return change.type === 'replace';
};

export const isResizing = (change: NodeChange): change is NodeDimensionChange => {
  return isDimensionChange(change) && (change.resizing ?? false);
};

export const isResizeFinished = (change: NodeChange): change is NodeDimensionChange => {
  return isDimensionChange(change) && typeof change.resizing === 'boolean' && !change.resizing;
};

export const isMoving = (change: NodeChange): change is NodePositionChange => {
  return isPositionChange(change) && typeof change.dragging === 'boolean' && change.dragging;
};

export const isMove = (change: NodeChange): change is NodePositionChange => {
  return isPositionChange(change) && !change.dragging;
};

export const isDragChange = (change: NodeChange): change is NodePositionChange => {
  return isPositionChange(change) && typeof change.dragging === 'boolean';
};

export const isMoveFinished = (change: NodeChange): change is NodePositionChange => {
  return isDragChange(change) && !change.dragging;
};

/** Detects geometry changes carried by a replacement rather than a dedicated bounds change. */
export const isReplaceAndBoundsChange = <NodeType extends Node>(
  change: NodeChange<NodeType>,
  previousNode: NodeType | undefined
): change is NodeReplaceChange<NodeType> => {
  if (!isReplaceChange(change) || !previousNode) {
    return false;
  }
  const node = change.item;
  return (
    node.position.x !== previousNode.position.x ||
    node.position.y !== previousNode.position.y ||
    node.parentId !== previousNode.parentId ||
    (node.width ?? node.measured?.width ?? 0) !== (previousNode.width ?? previousNode.measured?.width ?? 0) ||
    (node.height ?? node.measured?.height ?? 0) !== (previousNode.height ?? previousNode.measured?.height ?? 0)
  );
};
