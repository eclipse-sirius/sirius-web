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
import { Edge, Node, NodeChange, NodePositionChange, Position, getConnectedEdges, useStoreApi } from '@xyflow/react';
import { useCallback } from 'react';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { useStore } from '../../representation/useStore';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  getEdgeParametersWhileMoving,
  getHandlePositionFromNodeAndPath,
  getUpdatedConnectionHandles,
} from '../edge/EdgeLayout';
import { EdgeAnchorNodeData, isEdgeAnchorNode } from '../node/EdgeAnchorNode.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { ConnectionHandle } from './ConnectionHandles.types';
import { UseHandleChangeValue } from './useHandleChange.types';

const isNodePositionChange = (change: NodeChange<Node<NodeData>>): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean' && change.dragging;

const getEdgeAnchorNodePosition = (
  position: Position,
  edgeAnchorNode: Node<EdgeAnchorNodeData>,
  baseEdge: Edge<EdgeData> | undefined,
  movingNode: Node<NodeData>
): Position => {
  if (baseEdge && baseEdge.data && baseEdge.data.edgePath) {
    var svgPath = document.createElementNS('http://www.w3.org/2000/svg', 'path');
    svgPath.setAttribute('d', baseEdge.data.edgePath);
    const pathLength = svgPath.getTotalLength();
    let points = svgPath.getPointAtLength(pathLength * 0.5);
    if (edgeAnchorNode.data.isLayouted && edgeAnchorNode.data.positionRatio) {
      points = svgPath.getPointAtLength(pathLength * edgeAnchorNode.data.positionRatio);
    }
    return getHandlePositionFromNodeAndPath(baseEdge.data.edgePath, points, movingNode);
  } else {
    return position;
  }
};

export const useHandleChange = (): UseHandleChangeValue => {
  const { getEdges, getEdge } = useStore();
  const { diagramDescription } = useDiagramDescription();
  const storeApi = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = storeApi.getState();
  const applyHandleChange = useCallback(
    (
      changes: NodeChange<Node<NodeData>>[],
      nodes: Node<NodeData, DiagramNodeType>[]
    ): Node<NodeData, DiagramNodeType>[] => {
      const nodeId2ConnectionHandles = new Map<string, ConnectionHandle[]>();
      changes.filter(isNodePositionChange).forEach((nodeDraggingChange) => {
        const movingNode = nodes.find((node) => nodeDraggingChange.id === node.id && !node.data.pinned);
        if (movingNode) {
          const connectedEdges = getConnectedEdges([movingNode], getEdges());
          connectedEdges.forEach((edge) => {
            const { sourceHandle, targetHandle } = edge;
            const sourceNode = nodeLookup.get(edge.source);
            const targetNode = nodeLookup.get(edge.target);

            if (
              sourceNode &&
              targetNode &&
              sourceHandle &&
              targetHandle &&
              edge.data &&
              edge.data.bendingPoints &&
              edge.data.bendingPoints.length === 0
            ) {
              let { sourcePosition, targetPosition } = getEdgeParametersWhileMoving(
                nodeDraggingChange,
                sourceNode,
                targetNode,
                storeApi.getState().nodeLookup,
                diagramDescription.arrangeLayoutDirection
              );

              if (isEdgeAnchorNode(sourceNode)) {
                const baseEdge = getEdge(sourceNode.id);
                sourcePosition = getEdgeAnchorNodePosition(sourcePosition, sourceNode, baseEdge, movingNode);
              } else if (isEdgeAnchorNode(targetNode)) {
                const baseEdge = getEdge(targetNode.id);
                targetPosition = getEdgeAnchorNodePosition(targetPosition, targetNode, baseEdge, movingNode);
              }

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
          return {
            ...node,
            data: {
              ...node.data,
              connectionHandles: connectionHandles,
            },
          };
        }
        return node;
      });
    },
    [getEdges]
  );

  return { applyHandleChange };
};
