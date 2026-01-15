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
  useStoreApi,
  XYPosition,
} from '@xyflow/react';
import { useCallback } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { EdgeAnchorNodeCreationHandlesData } from '../node/EdgeAnchorNodeCreationHandles.types';
import { UseConnectorValue } from './useConnector.types';
import { useConnectorPalette } from './useConnectorPalette';

const computePalettePosition = (event: MouseEvent | TouchEvent, bounds: DOMRect | undefined): XYPosition => {
  if ('clientX' in event && 'clientY' in event) {
    return {
      x: event.clientX - (bounds?.left ?? 0),
      y: event.clientY - (bounds?.top ?? 0),
    };
  } else if ('touches' in event) {
    const touchEvent = event as TouchEvent;
    return {
      x: touchEvent.touches[0]?.clientX || 0 - (bounds?.left ?? 0),
      y: touchEvent.touches[0]?.clientY || 0 - (bounds?.top ?? 0),
    };
  } else {
    return { x: 0, y: 0 };
  }
};

const isEdgeAnchorNodeCreationHandles = (node: Node<NodeData>): node is Node<EdgeAnchorNodeCreationHandlesData> =>
  node.type === 'edgeAnchorNodeCreationHandles';

export const useConnector = (): UseConnectorValue => {
  const { showConnectorPalette, candidateDescriptionIds } = useConnectorPalette();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();

  const openPalette = useCallback(
    (event: MouseEvent | TouchEvent, sourceDiagramElementId: string, targetDiagramElementId: string) => {
      const { domNode } = store.getState();
      const domElement = domNode?.getBoundingClientRect();

      const palettePosition = computePalettePosition(event, domElement);
      if (!event.altKey && !event.ctrlKey) {
        event.preventDefault();
        showConnectorPalette(palettePosition.x, palettePosition.y, sourceDiagramElementId, targetDiagramElementId);
      }
    },
    []
  );

  const onConnectStart: OnConnectStart = useCallback(
    (_event: MouseEvent | TouchEvent, _params: OnConnectStartParams) => {},
    []
  );

  const onConnect: OnConnect = useCallback((_connection: Connection) => {}, []);

  const onConnectEnd: OnConnectEnd = useCallback(
    (event: MouseEvent | TouchEvent, connectionState: FinalConnectionState) => {
      if (connectionState && connectionState.fromNode && connectionState.fromHandle?.id?.startsWith('creationhandle')) {
        let sourceDiagramElementId = connectionState.fromNode.id;
        //  Set the edge as source when we're connecting from an EdgeAnchorNode
        const nodeSource = nodeLookup.get(connectionState.fromNode.id);
        if (nodeSource && isEdgeAnchorNodeCreationHandles(nodeSource)) {
          sourceDiagramElementId = nodeSource.data.edgeId;
        }

        if (connectionState.toNode) {
          // Use one of the parent as target if it's candidate
          let targetDiagramElementId = connectionState.toNode.id;
          let isNodeCandidate = false;
          let candidate: InternalNode<Node<NodeData>> | undefined = store
            .getState()
            .nodeLookup.get(connectionState.toNode.id);

          while (!isNodeCandidate && !!candidate) {
            isNodeCandidate = candidateDescriptionIds.includes(candidate.data.descriptionId);

            if (isNodeCandidate && candidate) {
              targetDiagramElementId = candidate.id;
            } else {
              candidate = store.getState().nodeLookup.get(candidate.parentId || '');
            }
          }

          if (isNodeCandidate) {
            openPalette(event, sourceDiagramElementId, targetDiagramElementId);
          }
        } else {
          //  Set the edge as target if we're connecting to an edge
          const hoveredEdge = store.getState().edges.find((edge) => edge.data && edge.data.isHovered);
          const shouldConnectToAnEdge =
            hoveredEdge &&
            hoveredEdge.data &&
            connectionState.fromHandle?.id?.startsWith('creationhandle') &&
            candidateDescriptionIds.includes(hoveredEdge.data.descriptionId);

          if (connectionState.fromNode && shouldConnectToAnEdge) {
            openPalette(event, sourceDiagramElementId, hoveredEdge.id);
          }
        }
      }
    },
    [candidateDescriptionIds.join('-')]
  );

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
  };
};
