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
import { Edge, Node, NodeChange, Position, XYPosition } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { ConnectionHandle } from './ConnectionHandles.types';

export interface UseHandleChangeValue {
  applyHandleChange: (changes: NodeChange<Node<NodeData>>[], nodes: Node<NodeData>[]) => Node<NodeData>[];
}

export type PopulateHandleIdToOtherHandNode = (
  edges: Edge[],
  nodes: Node<NodeData>[],
  handlesId: string[],
  handesIdToOtherEndNode: Map<string, Node<NodeData>>
) => void;

export type GetUpdatedConnectionHandlesIndexByPosition = (
  node: Node<NodeData>,
  nodeConnectionHandle: ConnectionHandle,
  position: Position,
  handleIdToOtherEndNode: Map<string, Node<NodeData>>,
  nodeIdToNodeCenter: Map<string, XYPosition>
) => ConnectionHandle;
