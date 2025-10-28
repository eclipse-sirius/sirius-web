/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { NodeLookup } from '@xyflow/system';
import { GQLDiagramDescription } from '../../representation/DiagramRepresentation.types';
import { NodeData } from '../DiagramRenderer.types';
import { getEdgeParameters, getNodeCenter, getUpdatedConnectionHandles } from '../edge/EdgeLayout';
import { ConnectionHandle } from '../handles/ConnectionHandles.types';
import {
  GetUpdatedConnectionHandlesIndexByPosition,
  PopulateHandleIdToOtherHandNode,
} from '../handles/useHandleChange.types';
import { RawDiagram } from './layout.types';

const getHandlesIdsFromNode = (node: Node<NodeData>): string[] => {
  return node.data.connectionHandles.map((handle) => handle.id).filter((handleId): handleId is string => !!handleId);
};

const isOverlap = (point: XYPosition, targetNode: Node<NodeData>): boolean => {
  const { position, width, height } = targetNode;

  return (
    point.x >= position.x &&
    point.x <= position.x + (width ?? 0) &&
    point.y >= position.y &&
    point.y <= position.y + (height ?? 0)
  );
};

const getUpdatedConnectionHandlesIndexByPosition: GetUpdatedConnectionHandlesIndexByPosition = (
  node,
  nodeConnectionHandle,
  position,
  handleIdToOtherEndNode,
  nodeIdToNodeCenter
) => {
  if (nodeConnectionHandle.position === position && nodeConnectionHandle.id) {
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

const populateHandleIdToOtherEndNode: PopulateHandleIdToOtherHandNode = (
  edges,
  nodes,
  handlesId,
  handesIdToOtherEndNode
) => {
  edges.forEach((edge) => {
    if (edge.source && edge.target && edge.sourceHandle && edge.targetHandle) {
      if (handlesId.find((id) => id === edge.sourceHandle)) {
        const node = nodes.find((n) => n.id === edge.target);
        if (node) {
          handesIdToOtherEndNode.set(edge.sourceHandle, node);
        }
      }
      if (handlesId.find((id) => id === edge.targetHandle)) {
        const node = nodes.find((n) => n.id === edge.source);
        if (node) {
          handesIdToOtherEndNode.set(edge.targetHandle, node);
        }
      }
    }
  });
};

const layoutHandleIndex = (diagram: RawDiagram, nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>) => {
  const handleIdToOtherEndNode: Map<string, Node<NodeData>> = new Map<string, Node<NodeData>>();
  const nodeIdToNodeCenter: Map<string, XYPosition> = new Map<string, XYPosition>();
  diagram.nodes.forEach((node) => {
    const handlesId = getHandlesIdsFromNode(node);
    populateHandleIdToOtherEndNode(diagram.edges, diagram.nodes, handlesId, handleIdToOtherEndNode);
    const internalNode = nodeLookup.get(node.id);
    if (internalNode) {
      nodeIdToNodeCenter.set(node.id, getNodeCenter(internalNode, nodeLookup));
    }
  });

  const nodeIdToConnectionHandle: Map<string, ConnectionHandle[]> = new Map<string, ConnectionHandle[]>();
  handleIdToOtherEndNode.forEach((node) => {
    //For each handle by side
    const connectionHandles = node.data.connectionHandles.map((nodeConnectionHandle: ConnectionHandle) => {
      Object.values(Position).map((position) => {
        //Update index of handles on the same side
        nodeConnectionHandle = getUpdatedConnectionHandlesIndexByPosition(
          node,
          nodeConnectionHandle,
          position,
          handleIdToOtherEndNode,
          nodeIdToNodeCenter
        );
      });
      return nodeConnectionHandle;
    });
    nodeIdToConnectionHandle.set(node.id, connectionHandles);
  });

  diagram.nodes = diagram.nodes.map((node) => {
    const newConnectionHandles = nodeIdToConnectionHandle.get(node.id);
    if (newConnectionHandles) {
      node.data = { ...node.data, connectionHandles: newConnectionHandles };
    }
    return node;
  });
};

const layoutHandlePosition = (
  diagram: RawDiagram,
  diagramDescription: GQLDiagramDescription,
  nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>
) => {
  diagram.edges.forEach((edge) => {
    const { source: sourceEdgeNode, target: targetEdgeNode, sourceHandle, targetHandle } = edge;
    const sourceNode = nodeLookup.get(sourceEdgeNode);
    const targetNode = nodeLookup.get(targetEdgeNode);
    if (sourceNode && targetNode && sourceHandle && targetHandle) {
      const { sourcePosition, targetPosition } = getEdgeParameters(
        sourceNode,
        targetNode,
        nodeLookup,
        diagramDescription.arrangeLayoutDirection,
        edge.data?.bendingPoints ?? []
      );

      const nodeSourceConnectionHandle: ConnectionHandle | undefined = sourceNode.data.connectionHandles.find(
        (connectionHandle: ConnectionHandle) => connectionHandle.id === sourceHandle
      );
      const nodeTargetConnectionHandle: ConnectionHandle | undefined = targetNode.data.connectionHandles.find(
        (connectionHandle: ConnectionHandle) => connectionHandle.id === targetHandle
      );

      if (
        nodeSourceConnectionHandle?.position !== sourcePosition ||
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

        diagram.nodes = diagram.nodes.map((node) => {
          if (edge.source && edge.target) {
            if (edge.source === node.id) {
              node.data = { ...node.data, connectionHandles: sourceConnectionHandles };
            }
            if (edge.target === node.id) {
              node.data = { ...node.data, connectionHandles: targetConnectionHandles };
            }
          }
          return node;
        });
      }
    }
  });
};

const resetEdgePathWhenOverlapping = (diagram: RawDiagram, nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>) => {
  diagram.edges.map((edge) => {
    const { source: sourceEdgeNode, target: targetEdgeNode } = edge;
    const sourceNode = nodeLookup.get(sourceEdgeNode);
    const targetNode = nodeLookup.get(targetEdgeNode);
    if (targetNode && sourceNode && (edge.data?.bendingPoints?.length ?? 0) > 0) {
      if (
        edge.data?.bendingPoints?.some((point) => isOverlap(point, targetNode)) ||
        edge.data?.bendingPoints?.some((point) => isOverlap(point, sourceNode))
      ) {
        const sourceConnectionHandles = sourceNode.data.connectionHandles.map((handle) => {
          if (edge.id === handle.edgeId) {
            return {
              ...handle,
              XYPosition: null,
            };
          } else {
            return handle;
          }
        });
        const targetConnectionHandles = targetNode.data.connectionHandles.map((handle) => {
          if (edge.id === handle.edgeId) {
            return {
              ...handle,
              XYPosition: null,
            };
          } else {
            return handle;
          }
        });

        diagram.nodes = diagram.nodes.map((node) => {
          if (edge.source && edge.target) {
            if (edge.source === node.id) {
              node.data = { ...node.data, connectionHandles: sourceConnectionHandles };
            }
            if (edge.target === node.id) {
              node.data = { ...node.data, connectionHandles: targetConnectionHandles };
            }
          }
          return node;
        });

        edge.data.bendingPoints = [];
      }
    }
    return edge;
  });
};

export const layoutHandles = (
  diagram: RawDiagram,
  diagramDescription: GQLDiagramDescription,
  nodeLookup: NodeLookup<InternalNode<Node<NodeData>>>
) => {
  resetEdgePathWhenOverlapping(diagram, nodeLookup);
  layoutHandlePosition(diagram, diagramDescription, nodeLookup);
  layoutHandleIndex(diagram, nodeLookup);
};
