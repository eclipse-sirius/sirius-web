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
import { useCallback, useContext, useEffect } from 'react';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getNearestPointInPerimeter, getNodesUpdatedWithHandles } from '../edge/EdgeLayout';
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
  const { addErrorMessage, addMessages } = useMultiToast();
  const { diagramId, editingContextId, readOnly } = useContext<DiagramContextValue>(DiagramContext);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { screenToFlowPosition, getNodes, getEdges, setEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const { synchronizeLayoutData } = useSynchronizeLayoutData();
  const updateNodeInternals = useUpdateNodeInternals();

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
    (edgeId: string, newEdgeEndId: string, reconnectEdgeKind: GQLReconnectKind): void => {
      const input: GQLReconnectEdgeInput = {
        id: crypto.randomUUID(),
        editingContextId: editingContextId,
        representationId: diagramId,
        edgeId,
        newEdgeEndId,
        reconnectEdgeKind,
      };
      if (!readOnly) {
        updateEdgeEnd({ variables: { input } });
      }
    },
    [editingContextId, diagramId, readOnly, updateEdgeEnd]
  );

  useEffect(() => {
    if (reconnectEdgeError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (reconnectEdgeData) {
      const { reconnectEdge } = reconnectEdgeData;
      if (isErrorPayload(reconnectEdge) || isSuccessPayload(reconnectEdge)) {
        addMessages(reconnectEdge.messages);
      }
    }
  }, [reconnectEdgeData, reconnectEdgeError]);

  const reconnectEdge = useCallback(
    (oldEdge: Edge, newConnection: Connection) => {
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
          handleReconnectEdge(edgeId, newEdgeTargetId, reconnectEdgeKind);
        }
      }
    },
    [handleReconnectEdge]
  );

  const onReconnectEdgeEnd = useCallback(
    (event: MouseEvent | TouchEvent, edge: Edge, handleType: HandleType) => {
      const targetEdgeHovered = getEdges().find((edge) => edge.data?.isHovered);
      const targetInternalNode = store.getState().connection.toNode;

      const handle = handleType === 'source' ? edge.targetHandle : edge.sourceHandle;

      // Reconnect an edge on the same node to update handle position
      if (
        'clientX' in event &&
        'clientY' in event &&
        targetInternalNode &&
        targetInternalNode.width &&
        targetInternalNode.height &&
        handle
      ) {
        let XYPosition: XYPosition = screenToFlowPosition({
          x: event.clientX,
          y: event.clientY,
        });

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
          handle,
          pointToSnap.XYPosition,
          pointToSnap.position
        );

        const finalDiagram: RawDiagram = {
          nodes: nodes,
          edges: getEdges(),
        };
        updateNodeInternals(targetInternalNode.id);
        synchronizeLayoutData(crypto.randomUUID(), 'layout', finalDiagram);
      }

      // Reconnect an edge on another edge
      if (targetEdgeHovered) {
        let reconnectEdgeKind = handleType === 'source' ? GQLReconnectKind.TARGET : GQLReconnectKind.SOURCE;
        if (
          targetEdgeHovered.id !== edge.id &&
          ((reconnectEdgeKind === GQLReconnectKind.TARGET && targetEdgeHovered.id !== edge.target) ||
            (reconnectEdgeKind === GQLReconnectKind.SOURCE && targetEdgeHovered.id !== edge.source))
        ) {
          handleReconnectEdge(edge.id, targetEdgeHovered.id, reconnectEdgeKind);
        }
      }
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
    },
    [getNodes, getEdges]
  );

  return { onReconnectEdgeStart, reconnectEdge, onReconnectEdgeEnd };
};
