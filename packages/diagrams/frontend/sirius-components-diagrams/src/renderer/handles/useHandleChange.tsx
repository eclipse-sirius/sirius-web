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
import { BorderNodePosition, EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  getEdgeParameters,
  getEdgeParametersWhileMoving,
  getHandlePositionFromNodeAndPath,
  getUpdatedConnectionHandle,
  getUpdatedConnectionHandles,
} from '../edge/EdgeLayout';
import {
  convertPositionToBorderNodePosition,
  computeBorderNodeXYPositionFromBorderNodePosition,
  getBorderNodeParentIfExist,
} from '../layout/layoutBorderNodes';
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
      const borderNodeId2NewPosition = new Map<string, BorderNodePosition>();
      const borderNodeId2ConnectionHandles = new Map<string, ConnectionHandle[]>();
      changes.filter(isNodePositionChange).forEach((nodeDraggingChange) => {
        const movingNode = nodes.find((node) => nodeDraggingChange.id === node.id && !node.data.pinned);
        if (movingNode) {
          const borderNodeChild = nodes.find((node) => node.data.isBorderNode && node.parentId === movingNode.id);
          if (borderNodeChild) {
            const connectedEdges = getConnectedEdges([borderNodeChild], getEdges());
            connectedEdges.forEach((edge) => {
              const { sourceHandle, targetHandle } = edge;
              const sourceNode = nodeLookup.get(edge.source);
              const targetNode = nodeLookup.get(edge.target);
              if (sourceNode && targetNode && sourceHandle && targetHandle && edge.data && edge.data.bendingPoints) {
                const sourceReferenceNode = getBorderNodeParentIfExist(sourceNode, nodeLookup);
                const targetReferenceNode = getBorderNodeParentIfExist(targetNode, nodeLookup);
                let { sourcePosition, targetPosition } = getEdgeParameters(
                  sourceReferenceNode,
                  targetReferenceNode,
                  storeApi.getState().nodeLookup,
                  diagramDescription.arrangeLayoutDirection,
                  edge.data.bendingPoints
                );
                if (sourceNode?.data.isBorderNode && !sourceNode.data.movedByUser) {
                  borderNodeId2ConnectionHandles.set(
                    sourceNode.id,
                    getUpdatedConnectionHandle(sourceNode, sourcePosition, sourceHandle, 'source')
                  );
                  borderNodeId2NewPosition.set(sourceNode.id, convertPositionToBorderNodePosition(sourcePosition));
                }
                if (targetNode?.data.isBorderNode && !targetNode.data.movedByUser) {
                  borderNodeId2ConnectionHandles.set(
                    targetNode.id,
                    getUpdatedConnectionHandle(targetNode, targetPosition, targetHandle, 'target')
                  );
                  borderNodeId2NewPosition.set(targetNode.id, convertPositionToBorderNodePosition(targetPosition));
                }
              }
            });
          }
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

      return nodes.reduce((updatedNodes: Node<NodeData>[], node: Node<NodeData>, _index, nodeArray) => {
        const connectionHandles = nodeId2ConnectionHandles.get(node.id);
        if (connectionHandles) {
          return [
            ...updatedNodes,
            {
              ...node,
              data: {
                ...node.data,
                connectionHandles: connectionHandles,
              },
            },
          ];
        }
        const newBorderNodePosition = borderNodeId2NewPosition.get(node.id);
        if (newBorderNodePosition !== undefined) {
          const newXYPosition = computeBorderNodeXYPositionFromBorderNodePosition(
            node,
            nodeArray,
            updatedNodes,
            newBorderNodePosition,
            nodeLookup
          );
          if (newXYPosition) {
            return [
              ...updatedNodes,
              {
                ...node,
                position: newXYPosition,
                data: {
                  ...node.data,
                  connectionHandles: borderNodeId2ConnectionHandles.get(node.id) || node.data.connectionHandles,
                  borderNodePosition: newBorderNodePosition,
                },
              },
            ];
          }
        }
        return [...updatedNodes, node];
      }, []);
    },
    [getEdges]
  );

  return { applyHandleChange };
};
