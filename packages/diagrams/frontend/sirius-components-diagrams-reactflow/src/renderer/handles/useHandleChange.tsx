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
import { Node, NodeChange, NodePositionChange, getConnectedEdges, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParametersWhileMoving, getUpdatedConnectionHandles } from '../edge/EdgeLayout';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ConnectionHandle } from './ConnectionHandles.types';
import { UseHandleChangeValue } from './useHandleChange.types';

const isNodePositionChange = (change: NodeChange): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean' && change.dragging;
export const useHandleChange = (): UseHandleChangeValue => {
  const { getEdges } = useReactFlow<NodeData, EdgeData>();

  const applyHandleChange = (
    changes: NodeChange[],
    nodes: Node<NodeData, DiagramNodeType>[]
  ): Node<NodeData, DiagramNodeType>[] => {
    return nodes.map((node) => {
      const nodeDraggingChange: NodePositionChange | undefined = changes
        .filter(isNodePositionChange)
        .find((change) => change.id === node.id);

      if (nodeDraggingChange) {
        const connectedEdges = getConnectedEdges([node], getEdges());
        connectedEdges.forEach((edge) => {
          const { sourceHandle, targetHandle } = edge;
          const sourceNode = nodes.find((node) => node.id === edge.sourceNode?.id);
          const targetNode = nodes.find((node) => node.id === edge.targetNode?.id);

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

              if (node.id === sourceNode.id) {
                node.data = { ...node.data, connectionHandles: sourceConnectionHandles };
              }
              if (node.id === targetNode.id) {
                node.data = { ...node.data, connectionHandles: targetConnectionHandles };
              }
            }
          }
        });
      }
      return node;
    });
  };

  return { applyHandleChange };
};
