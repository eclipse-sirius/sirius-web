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
import { Node, NodeChange, Position, XYPosition, getConnectedEdges, useReactFlow } from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParametersWhileMoving, getNodeCenter, getUpdatedConnectionHandles } from '../edge/EdgeLayout';
import { ConnectionHandle } from './ConnectionHandles.types';
import {
  GetUpdatedConnectionHandlesIndexByPosition,
  PopulateHandleIdToOtherHandNode,
  UseHandleChangeValue,
} from './useHandleChange.types';

const getHandlesIdsFromNode = (node: Node<NodeData>): string[] => {
  return node.data.connectionHandles.map((handle) => handle.id).filter((handleId): handleId is string => !!handleId);
};

const getUpdatedConnectionHandlesIndexByPosition: GetUpdatedConnectionHandlesIndexByPosition = (
  node,
  nodeConnectionHandle,
  position,
  handleIdToOtherEndNode,
  nodes
) => {
  if (nodeConnectionHandle.position === position && nodeConnectionHandle.id) {
    //Calculate all nodes center needed
    const nodeIdToNodeCenter: Map<string, XYPosition> = new Map<string, XYPosition>();
    handleIdToOtherEndNode.forEach((node) => {
      if (!handleIdToOtherEndNode.get(node.id)) {
        nodeIdToNodeCenter.set(node.id, getNodeCenter(node, nodes));
      }
    });

    let indexCounter = 0;

    const currentOtherEndNode = handleIdToOtherEndNode.get(nodeConnectionHandle.id);
    const currentOtherEndCenter = nodeIdToNodeCenter.get(currentOtherEndNode?.id ?? '');
    //Compare with other nodes center
    node.data.connectionHandles
      .filter((handle) => handle.position === position && handle.id !== nodeConnectionHandle.id)
      .filter((handle) => !handle.hidden)
      .forEach((handle) => {
        if (currentOtherEndCenter && handle.id && handle.position === position) {
          const otherEndNode = handleIdToOtherEndNode.get(handle.id);
          const otherEndCenter = nodeIdToNodeCenter.get(otherEndNode?.id ?? '');

          if (position === Position.Bottom || position === Position.Top) {
            if (otherEndCenter && otherEndCenter.x < currentOtherEndCenter.x) {
              indexCounter = indexCounter + 1;
            } else {
              indexCounter = indexCounter - 1;
            }
          } else {
            if (otherEndCenter && otherEndCenter.y < currentOtherEndCenter.y) {
              indexCounter = indexCounter + 1;
            } else {
              indexCounter = indexCounter - 1;
            }
          }
        }
      });

    nodeConnectionHandle.index = indexCounter;
  }
  return nodeConnectionHandle;
};

const populateHandleIdToOtherHandNode: PopulateHandleIdToOtherHandNode = (
  edges,
  nodes,
  handlesId,
  handesIdToOtherEndNode
) => {
  edges.forEach((edge) => {
    if (edge.sourceNode && edge.targetNode && edge.sourceHandle && edge.targetHandle) {
      if (handlesId.find((id) => id === edge.sourceHandle)) {
        const node = nodes.find((n) => n.id === edge.targetNode?.id);
        if (node) {
          handesIdToOtherEndNode.set(edge.sourceHandle, node);
        }
      }
      if (handlesId.find((id) => id === edge.targetHandle)) {
        const node = nodes.find((n) => n.id === edge.sourceNode?.id);
        if (node) {
          handesIdToOtherEndNode.set(edge.targetHandle, node);
        }
      }
    }
  });
};

export const useHandleChange = (): UseHandleChangeValue => {
  const { getEdges, getNodes, setNodes } = useReactFlow<NodeData, EdgeData>();

  const onHandleIndexChange = (changes: NodeChange[]): NodeChange[] => {
    return changes.map((change) => {
      //After the node stopped moving
      if (change.type === 'position' && typeof change.dragging === 'boolean' && !change.dragging) {
        const movedNode = getNodes().find((node) => change.id === node.id);
        if (movedNode) {
          const handleIdToOtherEndNode: Map<string, Node<NodeData>> = new Map<string, Node<NodeData>>();

          //First lvl
          const handlesId = getHandlesIdsFromNode(movedNode);
          populateHandleIdToOtherHandNode(getEdges(), getNodes(), handlesId, handleIdToOtherEndNode);
          //Second lvl
          const nodesToIterate: Map<string, Node<NodeData>> = new Map<string, Node<NodeData>>();
          nodesToIterate.set(movedNode.id, movedNode);
          handleIdToOtherEndNode.forEach((value) => nodesToIterate.set(value.id, value));
          nodesToIterate.forEach((secondLevelNode) => {
            const handlesId = getHandlesIdsFromNode(secondLevelNode);
            populateHandleIdToOtherHandNode(getEdges(), getNodes(), handlesId, handleIdToOtherEndNode);
          });

          const nodeIdToConnectionHandle: Map<string, ConnectionHandle[]> = new Map<string, ConnectionHandle[]>();
          //For each nodes concerned by the change
          nodesToIterate.forEach((node) => {
            //For each handle by side
            const connectionHandles = node.data.connectionHandles
              .filter((nodeConnectionHandle) => !nodeConnectionHandle.hidden)
              .map((nodeConnectionHandle: ConnectionHandle) => {
                Object.values(Position).map((position) => {
                  //Update index of handles on the same side
                  nodeConnectionHandle = getUpdatedConnectionHandlesIndexByPosition(
                    node,
                    nodeConnectionHandle,
                    position,
                    handleIdToOtherEndNode,
                    getNodes()
                  );
                });
                return nodeConnectionHandle;
              });
            nodeIdToConnectionHandle.set(node.id, connectionHandles);
          });
          setNodes((nodes: Node<NodeData>[]) =>
            nodes.map((node) => {
              const newConnectionHandles = nodeIdToConnectionHandle.get(node.id);
              if (newConnectionHandles) {
                node.data = { ...node.data, connectionHandles: newConnectionHandles };
              }
              return node;
            })
          );
        }
      }
      return change;
    });
  };

  const onHandlePositionChange = (changes: NodeChange[]): NodeChange[] => {
    return changes.map((change) => {
      //During node move
      if (
        change.type === 'position' &&
        change.positionAbsolute &&
        typeof change.dragging === 'boolean' &&
        change.dragging
      ) {
        const movingNode = getNodes().find((node) => change.id === node.id);

        if (movingNode) {
          const connectedEdges = getConnectedEdges([movingNode], getEdges());
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

  const onHandleChange = (changes: NodeChange[]): NodeChange[] => {
    return onHandleIndexChange(onHandlePositionChange(changes));
  };

  return { onHandleChange };
};
