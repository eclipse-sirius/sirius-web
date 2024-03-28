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
import { Edge, Node, NodeChange, NodePositionChange, getConnectedEdges, useReactFlow } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParametersWhileMoving, getUpdatedConnectionHandles } from '../edge/EdgeLayout';
import { ConnectionHandle } from './ConnectionHandles.types';
import { UseHandleChangeValue } from './useHandleChange.types';

const isNodePositionChange = (change: NodeChange): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean' && change.dragging;
export const useHandleChange = (): UseHandleChangeValue => {
  const { getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const applyHandleChange = (changes: NodeChange<Node<NodeData>>[], nodes: Node<NodeData>[]): Node<NodeData>[] => {
    const nodeId2ConnectionHandles = new Map<string, ConnectionHandle[]>();
    changes.filter(isNodePositionChange).forEach((nodeDraggingChange) => {
      const movingNode = nodes.find((node) => nodeDraggingChange.id === node.id);
      if (movingNode) {
        const connectedEdges = getConnectedEdges([movingNode], getEdges());
        connectedEdges.forEach((edge) => {
          const { sourceHandle, targetHandle } = edge;
          const sourceNode = nodes.find((node) => node.id === edge.source);
          const targetNode = nodes.find((node) => node.id === edge.target);

          if (sourceNode && targetNode && sourceHandle && targetHandle) {
            const { sourcePosition, targetPosition } = getEdgeParametersWhileMoving(
              nodeDraggingChange,
              sourceNode,
              targetNode,
              nodes
            );
            const nodeSourceConnectionHandle: ConnectionHandle | undefined = sourceNode.data.connectionHandles.find(
              (connectionHandle: ConnectionHandle) => connectionHandle.id === sourceHandle
            );
            const nodeTargetConnectionHandle: ConnectionHandle | undefined = targetNode.data.connectionHandles.find(
              (connectionHandle: ConnectionHandle) => connectionHandle.id === targetHandle
            );

            if (
              nodeSourceConnectionHandle?.position !== sourcePosition &&
              nodeTargetConnectionHandle?.position !== targetPosition
            ) {
              const { sourceConnectionHandles, targetConnectionHandles } = getUpdatedConnectionHandles(
                sourceNode,
                targetNode,
                sourcePosition,
                targetPosition,
                sourceHandle,
                targetHandle
              );

              nodeId2ConnectionHandles.set(sourceNode.id, sourceConnectionHandles);
              nodeId2ConnectionHandles.set(targetNode.id, targetConnectionHandles);
            }
          }
        });
      }
    });

    return nodes.map((node) => {
      const connectionHandles = nodeId2ConnectionHandles.get(node.id);
      if (connectionHandles) {
        node.data = { ...node.data, connectionHandles: connectionHandles };
      }
      return node;
    });
  };

  return { applyHandleChange };
};
