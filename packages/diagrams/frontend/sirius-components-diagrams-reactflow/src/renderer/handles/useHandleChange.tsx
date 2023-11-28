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
import { Node, NodeChange, getConnectedEdges, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParametersWhileMoving, getUpdatedConnectionHandles } from '../edge/EdgeLayout';
import { ConnectionHandle } from './ConnectionHandles.types';
import { UseHandleChangeValue } from './useHandleChange.types';
export const useHandleChange = (): UseHandleChangeValue => {
  const { getEdges, getNodes, setNodes } = useReactFlow<NodeData, EdgeData>();

  const onHandleChange = (changes: NodeChange[]): NodeChange[] => {
    return changes.map((change) => {
      if (change.type === 'position' && change.positionAbsolute) {
        const movedNode = getNodes().find((node) => change.id === node.id);

        if (movedNode) {
          const connectedEdges = getConnectedEdges([movedNode], getEdges());
          connectedEdges.forEach((edge) => {
            const { sourceHandle, targetHandle } = edge;
            const sourceNode = getNodes().find((node) => node.id === edge.sourceNode?.id);
            const targetNode = getNodes().find((node) => node.id === edge.targetNode?.id);

            if (sourceNode && targetNode && sourceHandle && targetHandle) {
              const { sourcePosition, targetPosition } = getEdgeParametersWhileMoving(
                change,
                sourceNode,
                targetNode,
                getNodes()
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

                setNodes((nodes: Node<NodeData>[]) =>
                  nodes.map((node) => {
                    if (sourceNode.id === node.id) {
                      node.data = { ...node.data, connectionHandles: sourceConnectionHandles };
                    }
                    if (targetNode.id === node.id) {
                      node.data = { ...node.data, connectionHandles: targetConnectionHandles };
                    }
                    return node;
                  })
                );
              }
            }
          });
        }
      }
      return change;
    });
  };

  return { onHandleChange };
};
