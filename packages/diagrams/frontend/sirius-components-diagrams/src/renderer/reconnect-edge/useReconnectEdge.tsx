/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import {
  Connection,
  Edge,
  HandleType,
  Node,
  useReactFlow,
  useStoreApi,
  useUpdateNodeInternals,
  XYPosition,
} from '@xyflow/react';
import { useCallback, useContext, useEffect, useRef } from 'react';
import { useTranslation } from 'react-i18next';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import {
  getNearestPointInPerimeter,
  getNodesUpdatedWithHandles,
  isCursorNearCenterOfTheNode,
} from '../edge/EdgeLayout';
import { determineSegmentAxis, getNewPointToGoAroundNode } from '../edge/rectilinear-edge/RectilinearEdgeCalculation';
import { useHandlesLayout } from '../handles/useHandlesLayout';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import {
  GQLErrorPayload,
  GQLReconnectEdgeData,
  GQLReconnectEdgeInput,
  GQLReconnectEdgePayload,
  GQLReconnectEdgeVariables,
  GQLReconnectKind,
  GQLSuccessPayload,
  UseReconnectEdge,
  ReconnectEdgeState,
} from './useReconnectEdge.types';

export const reconnectEdgeMutation = gql`
  mutation reconnectEdge($input: ReconnectEdgeInput!) {
    reconnectEdge(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLReconnectEdgePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLReconnectEdgePayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useReconnectEdge = (): UseReconnectEdge => {
  const { t } = useTranslation('sirius-components-diagrams', { keyPrefix: 'useReconnectEdge' });
  const { addErrorMessage, addMessages } = useMultiToast();
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { screenToFlowPosition, getNodes, getEdges, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const { removeNodeHandleLayoutData } = useHandlesLayout();
  const updateNodeInternals = useUpdateNodeInternals();
  const stateRef = useRef<ReconnectEdgeState>({
    edgeId: null,
    newEdgeEndId: null,
    reconnectEdgeKind: null,
  });

  const [updateEdgeEnd, { data: reconnectEdgeData, error: reconnectEdgeError }] = useMutation<
    GQLReconnectEdgeData,
    GQLReconnectEdgeVariables
  >(reconnectEdgeMutation);

  const onReconnectEdgeStart = useCallback(
    (_event: React.MouseEvent<Element, MouseEvent>, edge: Edge<EdgeData>, _handleType: HandleType) => {
      setEdges((prevEdges) =>
        prevEdges.map((prevEdge) => {
          if (prevEdge.id === edge.id) {
            return {
              ...prevEdge,
              reconnectable: false,
            };
          }
          return prevEdge;
        })
      );
    },
    []
  );

  const handleReconnectEdge = useCallback(
    (
      edgeId: string,
      newEdgeEndId: string,
      reconnectEdgeKind: GQLReconnectKind,
      targetHandlePosition: XYPosition
    ): void => {
      const input: GQLReconnectEdgeInput = {
        id: crypto.randomUUID(),
        editingContextId: editingContextId,
        representationId: diagramId,
        edgeId,
        newEdgeEndId,
        reconnectEdgeKind,
        targetPositionX: targetHandlePosition.x,
        targetPositionY: targetHandlePosition.y,
      };
      if (!readOnly) {
        updateEdgeEnd({ variables: { input } });
      }
    },
    [editingContextId, diagramId, readOnly, updateEdgeEnd]
  );

  useEffect(() => {
    if (reconnectEdgeError) {
      addErrorMessage(t('errors.unexpected'));
    }
    if (reconnectEdgeData) {
      const { reconnectEdge } = reconnectEdgeData;
      if (isErrorPayload(reconnectEdge) || isSuccessPayload(reconnectEdge)) {
        addMessages(reconnectEdge.messages);
      }
    }
  }, [reconnectEdgeData, reconnectEdgeError]);

  const reconnectEdge = useCallback(
    (oldEdge: Edge<EdgeData>, newConnection: Connection) => {
      // Reconnect an edge on another node
      if (oldEdge.target !== newConnection.target || oldEdge.source !== newConnection.source) {
        const edgeId = oldEdge.id;
        let reconnectEdgeKind = GQLReconnectKind.TARGET;
        let newEdgeTargetId = newConnection.target;

        if (oldEdge.target === newConnection.target) {
          reconnectEdgeKind = GQLReconnectKind.SOURCE;
          newEdgeTargetId = newConnection.source;
        }
        if (newEdgeTargetId) {
          stateRef.current = {
            edgeId,
            newEdgeEndId: newEdgeTargetId,
            reconnectEdgeKind,
          };
        }
      }
    },
    [handleReconnectEdge]
  );

  const onReconnectEdgeEnd = useCallback(
    (event: MouseEvent | TouchEvent, edge: Edge<EdgeData>, handleType: HandleType) => {
      const { edgeId, newEdgeEndId, reconnectEdgeKind } = stateRef.current;
      if (edgeId && newEdgeEndId && reconnectEdgeKind) {
        let targetHandlePosition: XYPosition = { x: 0, y: 0 };
        const target = store.getState().nodeLookup.get(newEdgeEndId);
        if (target && 'clientX' in event && 'clientY' in event) {
          targetHandlePosition = screenToFlowPosition({ x: event.clientX, y: event.clientY });
          const isNearCenter = isCursorNearCenterOfTheNode(target, targetHandlePosition);
          if (isNearCenter) {
            targetHandlePosition = { x: 0, y: 0 };
          }
        }
        handleReconnectEdge(edgeId, newEdgeEndId, reconnectEdgeKind, targetHandlePosition);
      } else {
        reconnectEdgeOnSameNode(event, edge, handleType);
        reconnectEdgeOnSameEdge(event, edge, handleType);
        reconnectEdgeOnAnotherEdge(event, edge, handleType);
      }
      stateRef.current = {
        edgeId: null,
        newEdgeEndId: null,
        reconnectEdgeKind: null,
      };
    },
    [getNodes, getEdges]
  );

  const reconnectEdgeOnSameEdge = (event: MouseEvent | TouchEvent, edge: Edge<EdgeData>, handleType: HandleType) => {
    const targetEdgeHovered = getEdges().find((edge) => edge.data?.isHovered);
    // Reconnect an edge on the same edge to update handle position
    if (targetEdgeHovered && targetEdgeHovered.data && targetEdgeHovered.data.edgePath) {
      let reconnectEdgeKind = handleType === 'source' ? GQLReconnectKind.TARGET : GQLReconnectKind.SOURCE;
      if (
        'clientX' in event &&
        'clientY' in event &&
        ((reconnectEdgeKind === GQLReconnectKind.TARGET && targetEdgeHovered.id === edge.target) ||
          (reconnectEdgeKind === GQLReconnectKind.SOURCE && targetEdgeHovered.id === edge.source))
      ) {
        const finalDiagram: RawDiagram = {
          nodes: getNodes(),
          edges: getEdges(),
        };
        updateNodeInternals(targetEdgeHovered.id);
        synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
      }
    }
  };

  const reconnectEdgeOnAnotherEdge = (
    _event: MouseEvent | TouchEvent,
    edge: Edge<EdgeData>,
    handleType: HandleType
  ) => {
    const targetEdgeHovered = getEdges().find((edge) => edge.data?.isHovered);
    // Reconnect an edge on another edge
    if (targetEdgeHovered) {
      let reconnectEdgeKind = handleType === 'source' ? GQLReconnectKind.TARGET : GQLReconnectKind.SOURCE;
      if (
        targetEdgeHovered.id !== edge.id &&
        ((reconnectEdgeKind === GQLReconnectKind.TARGET && targetEdgeHovered.id !== edge.target) ||
          (reconnectEdgeKind === GQLReconnectKind.SOURCE && targetEdgeHovered.id !== edge.source))
      ) {
        handleReconnectEdge(edge.id, targetEdgeHovered.id, reconnectEdgeKind, { x: 0, y: 0 });
      }
    }

    setEdges((prevEdges) =>
      prevEdges.map((prevEdge) => {
        if (prevEdge.id === edge.id) {
          return {
            ...prevEdge,
            reconnectable: true,
          };
        }
        return prevEdge;
      })
    );
  };

  const reconnectEdgeOnSameNode = (event: MouseEvent | TouchEvent, edge: Edge<EdgeData>, handleType: HandleType) => {
    const targetInternalNode = store.getState().connection.toNode;
    const handleId = handleType === 'source' ? edge.targetHandle : edge.sourceHandle;

    // Reconnect an edge on the same node to update handle position
    if (
      'clientX' in event &&
      'clientY' in event &&
      targetInternalNode &&
      targetInternalNode.width &&
      targetInternalNode.height &&
      handleId
    ) {
      let XYPosition: XYPosition = screenToFlowPosition({
        x: event.clientX,
        y: event.clientY,
      });

      const isNearCenter = isCursorNearCenterOfTheNode(targetInternalNode, { x: XYPosition.x, y: XYPosition.y });

      if (!isNearCenter) {
        const pointToSnap = getNearestPointInPerimeter(
          targetInternalNode.internals.positionAbsolute.x,
          targetInternalNode.internals.positionAbsolute.y,
          targetInternalNode.width,
          targetInternalNode.height,
          XYPosition.x,
          XYPosition.y
        );

        const nodes = getNodesUpdatedWithHandles(
          getNodes(),
          targetInternalNode,
          edge.id,
          handleId,
          pointToSnap.XYPosition,
          pointToSnap.position
        );

        //Create a new bending point if the edge was manualy layouted
        let newEdges = store.getState().edges;
        if (edge.data?.bendingPoints) {
          if (handleType === 'source') {
            const newPoints = [...edge.data.bendingPoints];
            const lastPoint = newPoints[newPoints.length - 1];
            const penultimatePoint = newPoints[newPoints.length - 2];
            if (lastPoint && penultimatePoint) {
              const segmentAxis = determineSegmentAxis(penultimatePoint, lastPoint);
              const newPoint = getNewPointToGoAroundNode(
                segmentAxis,
                pointToSnap.position,
                pointToSnap.XYPosition.x,
                pointToSnap.XYPosition.y
              );

              if (newPoint) {
                newPoints.push(newPoint);
                if (segmentAxis === 'x') {
                  lastPoint.x = newPoint.x;
                } else if (segmentAxis === 'y') {
                  lastPoint.y = newPoint.y;
                }
                newEdges = newEdges.map((previousEdge) => {
                  if (previousEdge.data && previousEdge.id === edge.id) {
                    return {
                      ...previousEdge,
                      data: {
                        ...previousEdge.data,
                        bendingPoints: newPoints,
                      },
                    };
                  }
                  return previousEdge;
                });
              }
            }
          } else {
            const newPoints = [...edge.data.bendingPoints];
            const firstPoint = newPoints[0];
            const secondPoint = newPoints[1];
            if (firstPoint && secondPoint) {
              const segmentAxis = determineSegmentAxis(firstPoint, secondPoint);
              const newPoint = getNewPointToGoAroundNode(
                segmentAxis,
                pointToSnap.position,
                pointToSnap.XYPosition.x,
                pointToSnap.XYPosition.y
              );

              if (newPoint) {
                newPoints.unshift(newPoint);
                if (segmentAxis === 'x') {
                  firstPoint.x = newPoint.x;
                } else if (segmentAxis === 'y') {
                  firstPoint.y = newPoint.y;
                }
                newEdges = newEdges.map((previousEdge) => {
                  if (previousEdge.data && previousEdge.id === edge.id) {
                    return {
                      ...previousEdge,
                      data: {
                        ...previousEdge.data,
                        bendingPoints: newPoints,
                      },
                    };
                  }
                  return previousEdge;
                });
              }
            }
          }
        }

        const finalDiagram: RawDiagram = {
          nodes: nodes,
          edges: newEdges,
        };
        updateNodeInternals(targetInternalNode.id);
        synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
      } else {
        const currentHandle = targetInternalNode.data.connectionHandles.find(
          (connectionHandle) => connectionHandle.id === handleId
        );

        if (currentHandle && currentHandle.XYPosition && (currentHandle.XYPosition.x || currentHandle.XYPosition.y)) {
          removeNodeHandleLayoutData(edge.id);
        }
      }
    }
  };

  return { onReconnectEdgeStart, reconnectEdge, onReconnectEdgeEnd };
};
