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
  useStoreApi,
} from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { EdgeAnchorNodeCreationHandlesData } from '../node/EdgeAnchorNodeCreationHandles.types';
import { UseConnectorValue } from './useConnector.types';
import { useConnectorContext } from './useConnectorContext';

const isEdgeAnchorNodeCreationHandles = (node: Node<NodeData>): node is Node<EdgeAnchorNodeCreationHandlesData> =>
  node.type === 'edgeAnchorNodeCreationHandles';

export const useConnector = (): UseConnectorValue => {
  const { setConnection, setPosition, toolCandidates } = useConnectorContext();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();

  const onConnectStart: OnConnectStart = useCallback(
    (_event: MouseEvent | TouchEvent, params: OnConnectStartParams) => {
      if (params.nodeId && params.handleId?.startsWith('creationhandle')) {
        setConnection({
          source: params.nodeId,
          target: '',
          sourceHandle: null,
          targetHandle: null,
        });
      }
    },
    []
  );

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
          isNodeCandidate = toolCandidates
            .flatMap((tool) => tool.candidateDescriptionIds)
            .includes(candidate.data.descriptionId);

          if (isNodeCandidate && candidate) {
            connection.target = candidate.id;
          } else {
            candidate = store.getState().nodeLookup.get(candidate.parentId || '');
          }
        }

        if (isNodeCandidate) {
          setConnection(connection);
        } else {
          setConnection(null);
        }
      }
    },
    [toolCandidates.map((tool) => tool.id).join('-')]
  );

  const onConnectEnd: OnConnectEnd = useCallback(
    (event: MouseEvent | TouchEvent, connectionState: FinalConnectionState) => {
      if (connectionState && connectionState.fromHandle?.id?.startsWith('creationhandle')) {
        if ('clientX' in event && 'clientY' in event) {
          setPosition({ x: event.clientX || 0, y: event.clientY });
        } else if ('touches' in event) {
          const touchEvent = event as TouchEvent;
          setPosition({ x: touchEvent.touches[0]?.clientX || 0, y: touchEvent.touches[0]?.clientY || 0 });
        }

        //  Set the new connection if we're connecting to an edge
        const hoveredEdge = store.getState().edges.find((edge) => edge.data && edge.data.isHovered);
        const shouldConnectToAnEdge =
          hoveredEdge &&
          hoveredEdge.data &&
          connectionState.fromHandle?.id?.startsWith('creationhandle') &&
          toolCandidates.flatMap((tool) => tool.candidateDescriptionIds).includes(hoveredEdge.data.descriptionId);

        if (connectionState.fromNode && shouldConnectToAnEdge) {
          setConnection({
            source: connectionState.fromNode.id,
            target: hoveredEdge.id,
            sourceHandle: null,
            targetHandle: null,
          });
        } else if (
          !shouldConnectToAnEdge &&
          ((connectionState.toNode && !connectionState.isValid) || !connectionState.toNode)
        ) {
          setConnection(null);
        }
      }
    },
    [toolCandidates.map((tool) => tool.id).join('-')]
  );

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
  };
};
