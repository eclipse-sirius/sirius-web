/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { Edge, Node, ReactFlowState } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ConnectionHandle } from '../handles/ConnectionHandles.types';
import { DiagramNodeType } from '../node/NodeTypes.types';

export const updateHandleViewModifier = (
  nodes: Node<NodeData, DiagramNodeType>[],
  state: ReactFlowState<Node<NodeData>, Edge<EdgeData>>
) => {
  nodes.forEach((node) => {
    const updatedConnectionHandles: ConnectionHandle[] = node.data.connectionHandles.map((connectionHandle) => {
      const isEdgeSelected = state.edgeLookup.get(connectionHandle.edgeId)?.selected;
      if (isEdgeSelected) {
        return {
          ...connectionHandle,
          isHidden: false,
        };
      }
      return connectionHandle;
    });
    node.data.connectionHandles = updatedConnectionHandles;
  });
};
