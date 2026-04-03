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
  const { connection, setConnection, position, setPosition, resetConnection, candidates, isConnectionInProgress } =
    useContext<ConnectorContextValue>(ConnectorContext);
  const { getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();
  const updateNodeInternals = useUpdateNodeInternals();

  //  Set the new connection if we're connecting to a node
  const onConnect: OnConnect = useCallback(
    (connection: Connection) => {
      if (connection.sourceHandle?.startsWith('creationhandle')) {
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
      }
    },
    [candidates.join('-')]
  );

  const onConnectStart: OnConnectStart = useCallback(
    (_event: MouseEvent | TouchEvent, params: OnConnectStartParams) => {
      if (params.handleId?.startsWith('creationhandle')) {
        resetConnection();
        if (params.nodeId) {
          updateNodeInternals(params.nodeId);
        }
      }
    },
    []
  );

  const onConnectEnd: OnConnectEnd = useCallback(
    (event: MouseEvent | TouchEvent, connectionState: FinalConnectionState) => {
      if (connectionState.fromHandle?.id?.startsWith('creationhandle')) {
        if ('clientX' in event && 'clientY' in event) {
          setPosition({ x: event.clientX || 0, y: event.clientY });
        } else if ('touches' in event) {
          const touchEvent = event as TouchEvent;
          setPosition({ x: touchEvent.touches[0]?.clientX || 0, y: touchEvent.touches[0]?.clientY || 0 });
        }

        //  Set the new connection if we're connecting to an edge
        const hoveredEdge = getEdges().find((edge) => edge.data && edge.data.isHovered);
        if (connectionState.fromNode && !!hoveredEdge) {
          setConnection({
            source: connectionState.fromNode.id,
            target: hoveredEdge.id,
            sourceHandle: null,
            targetHandle: null,
          });
        }
      }
    },
    []
  );

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose: resetConnection,
    connection,
    position,
    candidates,
    isConnectionInProgress,
  };
};
