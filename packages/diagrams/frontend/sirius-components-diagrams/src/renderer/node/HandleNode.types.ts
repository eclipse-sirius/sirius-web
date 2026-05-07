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
import { InternalNode, Node, Position } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';

export interface HandleNodeData extends NodeData {
  nodeId: string | null;
  edgeId: string | null;
  isSelected: boolean;
  position: Position;
}

export interface HandleNodeState {
  isHovered: boolean;
  isMouseDown: boolean;
}

export const isHandleNode = (node: Node<NodeData>): node is Node<HandleNodeData> => node.type === 'handleNode';

export const isInternalHandleNode = (node: InternalNode<Node>): node is InternalNode<Node<HandleNodeData>> =>
  node.type === 'handleNode';
