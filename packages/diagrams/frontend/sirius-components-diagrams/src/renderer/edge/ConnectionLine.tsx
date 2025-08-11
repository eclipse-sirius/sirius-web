/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Theme, useTheme } from '@mui/material/styles';
import {
  ConnectionLineComponentProps,
  Edge,
  getSmoothStepPath,
  HandleType,
  InternalNode,
  Node,
  useReactFlow,
  useStoreApi,
  useUpdateNodeInternals,
} from '@xyflow/react';
import React, { memo, useContext, useEffect, useState } from 'react';
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { ConnectorContext } from '../connector/ConnectorContext';
import { ConnectorContextValue } from '../connector/ConnectorContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { ConnectionHandle } from '../handles/ConnectionHandles.types';
import { ConnectionLineState } from './ConnectionLine.types';
import {
  getEdgeParameters,
  getNearestPointInPath,
  getNearestPointInPerimeter,
  getUpdatedHandleForNode,
  isCursorNearCenterOfTheNode,
} from './EdgeLayout';

const connectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

export const ConnectionLine = memo(
  ({
    fromX,
    fromY,
    toX,
    toY,
    fromPosition,
    toPosition,
    fromNode,
    toNode,
    fromHandle,
  }: ConnectionLineComponentProps<Node<NodeData>>) => {
    const theme = useTheme();
    const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
    const { candidates } = useContext<ConnectorContextValue>(ConnectorContext);
    const { diagramDescription } = useDiagramDescription();
    const { setNodes, getEdges, getEdge } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
    const updateNodeInternals = useUpdateNodeInternals();
    const [state, setState] = useState<ConnectionLineState>({
      previousToNodeId: toNode?.id ?? null,
    });

    const edgeId = fromNode.data.connectionHandles.find((handle) => handle.id === fromHandle?.id)?.edgeId;
    const targetHandle = getEdges().find((e) => e.id === edgeId)?.targetHandle;
    const sourceHandle = getEdges().find((e) => e.id === edgeId)?.sourceHandle;
    const handleToUpdate = targetHandle === fromHandle.id ? sourceHandle : targetHandle;

    let updatedToY = toY;
    let updatedToX = toX;

    useEffect(() => {
      // When reconnection to a node
      if (state.previousToNodeId != toNode?.id) {
        setState({
          previousToNodeId: toNode?.id ?? null,
        });
      }
    }, [toNode?.id]);

    // When reconnecting an edge
    if (edgeId) {
      const edge = getEdge(edgeId);
      if (edge) {
        const edgeBase = getEdge(edge.target);
        // Snap the ConnectionLine to the border of the targeted edge
        if (edgeBase && edgeBase.data && edgeBase.data.edgePath && edgeBase.data.isHovered) {
          const { position, handlePosition } = getNearestPointInPath(
            updatedToX,
            updatedToY,
            edgeBase.data.edgePath,
            fromNode
          );
          updatedToX = position.x;
          updatedToY = position.y;
          toPosition = handlePosition;
        }
      }
    }

    if (handleToUpdate) {
      if (edgeId && toNode && toNode.width && toNode.height) {
        // Snap the ConnectionLine to the border of the targeted node
        const pointToSnap = getNearestPointInPerimeter(
          toNode.internals.positionAbsolute.x,
          toNode.internals.positionAbsolute.y,
          toNode.width,
          toNode.height,
          updatedToX,
          updatedToY
        );

        updatedToX = pointToSnap.XYPosition.x;
        updatedToY = pointToSnap.XYPosition.y;
        toPosition = pointToSnap.position;
      }
    }

    // When creating an edge
    let candidate: InternalNode<Node<NodeData>> | null | undefined = toNode;
    if (
      fromHandle &&
      fromHandle.id &&
      fromHandle.id.startsWith('creationhandle') &&
      toNode &&
      toNode.width &&
      toNode.height
    ) {
      let isNodeCandidate = false;

      while (!isNodeCandidate && !!candidate) {
        isNodeCandidate = candidates.map((candidate) => candidate.id).includes(candidate.data.descriptionId);
        if (isNodeCandidate && candidate && candidate.width && candidate.height) {
          const pointToSnap = getNearestPointInPerimeter(
            candidate.internals.positionAbsolute.x,
            candidate.internals.positionAbsolute.y,
            candidate.width,
            candidate.height,
            updatedToX,
            updatedToY
          );
          updatedToX = pointToSnap.XYPosition.x;
          updatedToY = pointToSnap.XYPosition.y;
          toPosition = pointToSnap.position;
        } else {
          candidate = store.getState().nodeLookup.get(candidate.parentId || '');
        }
      }
    }

    // Update handle position early to avoid flicking effect after onReconnectEnd
    useEffect(() => {
      // When reconnection to a node
      if (toNode && edgeId && handleToUpdate) {
        const isNearCenter = isCursorNearCenterOfTheNode(toNode, { x: toX, y: toY });
        if (!isNearCenter) {
          const updatedHandles = getUpdatedHandleForNode(
            toNode,
            edgeId,
            handleToUpdate,
            { x: updatedToX, y: updatedToY },
            toPosition
          );
          setNodes((previousNodes) =>
            previousNodes.map((previousNode) => {
              if (previousNode.id === toNode.id) {
                return {
                  ...previousNode,
                  data: {
                    ...previousNode.data,
                    connectionHandles: updatedHandles,
                  },
                };
              }
              return previousNode;
            })
          );
          updateNodeInternals(toNode.id);
        } else {
          setHandleToAutoLayout();
        }
      } else {
        setHandleToAutoLayout();
      }

      // When reconnection to an edge
      if (edgeId) {
        const edge = getEdge(edgeId);
        if (edge) {
          const edgeBase = getEdge(edge.target);
          if (edgeBase && edgeBase.data && edgeBase.data.edgePath && edgeBase.data.isHovered) {
            const { position, positionRatio, handlePosition } = getNearestPointInPath(
              updatedToX,
              updatedToY,
              edgeBase.data.edgePath,
              fromNode
            );
            setNodes((prevNodes) =>
              prevNodes.map((prevNode) => {
                if (prevNode.id === edgeBase.id) {
                  const handles: ConnectionHandle[] = prevNode.data.connectionHandles.map((connectionHandle) => {
                    return {
                      ...connectionHandle,
                      position: handlePosition,
                    };
                  });

                  return {
                    ...prevNode,
                    position: position,
                    data: {
                      ...prevNode.data,
                      isLayouted: true,
                      positionRatio: positionRatio,
                      connectionHandles: handles,
                    },
                  };
                }
                return prevNode;
              })
            );
          }
        }
      }

      setNodes((previousNodes) =>
        previousNodes.map((previousNode) => {
          if (
            state.previousToNodeId &&
            state.previousToNodeId !== toNode?.id &&
            state.previousToNodeId === previousNode.id
          ) {
            return {
              ...previousNode,
              data: {
                ...previousNode.data,
                connectionLinePositionOnNode: 'none',
              },
            };
          }

          return previousNode;
        })
      );

      if (candidate) {
        const isNearCenter = isCursorNearCenterOfTheNode(candidate, { x: toX, y: toY });
        setNodes((previousNodes) =>
          previousNodes.map((previousNode) => {
            if (previousNode.id === candidate.id) {
              if (isNearCenter && previousNode.data.isConnectionLineOnCenter !== 'center') {
                return {
                  ...previousNode,
                  data: {
                    ...previousNode.data,
                    connectionLinePositionOnNode: 'center',
                  },
                };
              } else if (!isNearCenter && previousNode.data.isConnectionLineOnCenter !== 'border') {
                return {
                  ...previousNode,
                  data: {
                    ...previousNode.data,
                    connectionLinePositionOnNode: 'border',
                  },
                };
              }
            }
            return previousNode;
          })
        );
      }
    }, [updatedToX, updatedToY]);

    const setHandleToAutoLayout = () => {
      const edge = getEdge(edgeId ?? '');
      if (edge) {
        const handleType: HandleType = edge.target === fromNode.id ? 'source' : 'target';
        const nodeIdToUpdate = handleType === 'source' ? edge.source : edge.target;
        const nodeToUpdate = store.getState().nodeLookup.get(nodeIdToUpdate);

        if (nodeToUpdate) {
          const { targetPosition } = getEdgeParameters(
            fromNode,
            nodeToUpdate,
            store.getState().nodeLookup,
            diagramDescription.arrangeLayoutDirection,
            edge.data?.bendingPoints ?? []
          );

          setNodes((previousNodes) =>
            previousNodes.map((previousNode) => {
              if (previousNode.id === nodeToUpdate.id) {
                return {
                  ...previousNode,
                  data: {
                    ...previousNode.data,
                    connectionHandles: previousNode.data.connectionHandles.map((handle) => {
                      if (handle.id === handleToUpdate && handle.XYPosition) {
                        return {
                          ...handle,
                          XYPosition: null,
                          position: targetPosition,
                        };
                      }
                      return handle;
                    }),
                  },
                };
              }
              return previousNode;
            })
          );
          updateNodeInternals(nodeToUpdate.id);
        }
      }
    };

    const [edgePath] = getSmoothStepPath({
      sourceX: fromX,
      sourceY: fromY,
      sourcePosition: fromPosition,
      targetX: updatedToX,
      targetY: updatedToY,
      targetPosition: toPosition,
    });

    return (
      <g>
        <path fill="none" className="animated" d={edgePath} style={connectionLineStyle(theme)} />
      </g>
    );
  }
);
