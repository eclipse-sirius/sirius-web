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

import {
  Connection,
  Edge,
  FinalConnectionState,
  InternalNode,
  Node,
  OnConnect,
  OnConnectEnd,
  OnConnectStart,
  OnConnectStartParams,
  useReactFlow,
  useStoreApi,
  useUpdateNodeInternals,
} from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { EdgeAnchorNodeCreationHandlesData } from '../node/EdgeAnchorNodeCreationHandles.types';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { UseConnectorValue } from './useConnector.types';

const isEdgeAnchorNodeCreationHandles = (node: Node<NodeData>): node is Node<EdgeAnchorNodeCreationHandlesData> =>
  node.type === 'edgeAnchorNodeCreationHandles';

export const useConnector = (): UseConnectorValue => {
  const {
    connection,
    setConnection,
    position,
    setPosition,
    resetConnection,
    candidates,
    isNewConnection,
    setIsNewConnection,
  } = useContext<ConnectorContextValue>(ConnectorContext);
  const { getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();
  const updateNodeInternals = useUpdateNodeInternals();

  const isConnectionInProgress = useCallback(() => {
    const connectionNodeId = store.getState().connection.fromNode?.id;
    return (!!connectionNodeId && isNewConnection) || !!connection;
  }, [isNewConnection, connection]);

  const isReconnectionInProgress = useCallback(() => {
    const connectionNodeId = store.getState().connection.fromNode?.id;
    return !!connectionNodeId && !isNewConnection;
  }, [isNewConnection]);

  //  Set the new connection if we're connecting to a node
  const onConnect: OnConnect = useCallback(
    (connection: Connection) => {
      const nodeSource = nodeLookup.get(connection.source);
      //  Set the edge as source when we're connecting from an EdgeAnchorNode
      if (nodeSource && isEdgeAnchorNodeCreationHandles(nodeSource)) {
        connection.source = nodeSource.data.edgeId;
      }

      // Use one of the parent as target if it's candidate
      let isNodeCandidate = false;
      let candidate: InternalNode<Node<NodeData>> | undefined = store.getState().nodeLookup.get(connection.target);

      while (!isNodeCandidate && !!candidate) {
        isNodeCandidate = candidates.map((candidate) => candidate.id).includes(candidate.data.descriptionId);

        if (isNodeCandidate && candidate) {
          connection.target = candidate.id;
        } else {
          candidate = store.getState().nodeLookup.get(candidate.parentId || '');
        }
      }

      setConnection(connection);
    },
    [candidates.join('-')]
  );

  const onConnectStart: OnConnectStart = useCallback(
    (_event: MouseEvent | TouchEvent, params: OnConnectStartParams) => {
      resetConnection();
      if (params.nodeId) {
        updateNodeInternals(params.nodeId);
      }
    },
    []
  );

  const onConnectionStartElementClick = useCallback((event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (event.button === 0) {
      setIsNewConnection(true);
    }
  }, []);

  const onConnectEnd: OnConnectEnd = useCallback(
    (event: MouseEvent | TouchEvent, connectionState: FinalConnectionState) => {
      if ('clientX' in event && 'clientY' in event) {
        setPosition({ x: event.clientX || 0, y: event.clientY });
      } else if ('touches' in event) {
        const touchEvent = event as TouchEvent;
        setPosition({ x: touchEvent.touches[0]?.clientX || 0, y: touchEvent.touches[0]?.clientY || 0 });
      }

      //  Set the new connection if we're connecting to an edge
      const hoveredEdge = getEdges().find((edge) => edge.data && edge.data.isHovered);
      const shouldConnectToAnEdge = hoveredEdge && connectionState.fromHandle?.id?.startsWith('creationhandle');
      if (connectionState.fromNode && shouldConnectToAnEdge) {
        setConnection({
          source: connectionState.fromNode.id,
          target: hoveredEdge.id,
          sourceHandle: null,
          targetHandle: null,
        });
      }

      setIsNewConnection(false);
    },
    []
  );

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose: resetConnection,
    onConnectionStartElementClick,
    connection,
    position,
    isConnectionInProgress,
    isReconnectionInProgress,
    candidates,
  };
};
