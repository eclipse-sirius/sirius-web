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

import { Edge, FinalConnectionState, InternalNode, Node, OnConnectEnd, useStoreApi, XYPosition } from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { HandleNodeData } from '../node/HandleNode.types';
import { ConnectorPaletteContext } from './context/ConnectorPaletteContext';
import { ConnectorPaletteContextValue } from './context/ConnectorPaletteContext.types';
import { UseConnectorValue } from './useConnector.types';

const computePalettePosition = (event: MouseEvent | TouchEvent): XYPosition => {
  if ('clientX' in event && 'clientY' in event) {
    return {
      x: event.clientX,
      y: event.clientY,
    };
  } else if ('touches' in event) {
    const touchEvent = event as TouchEvent;
    return {
      x: touchEvent.touches[0]?.clientX || 0,
      y: touchEvent.touches[0]?.clientY || 0,
    };
  } else {
    return { x: 0, y: 0 };
  }
};

const isHandleNode = (node: InternalNode<Node<NodeData>>): node is InternalNode<Node<HandleNodeData>> =>
  node.type === 'handleNode';

export const useConnector = (): UseConnectorValue => {
  const { showConnectorPalette, candidateDescriptionIds } =
    useContext<ConnectorPaletteContextValue>(ConnectorPaletteContext);
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();

  const openPalette = useCallback(
    (event: MouseEvent | TouchEvent, sourceDiagramElementId: string, targetDiagramElementId: string) => {
      const palettePosition = computePalettePosition(event);
      if (!event.altKey && !event.ctrlKey) {
        event.preventDefault();
        showConnectorPalette(palettePosition.x, palettePosition.y, sourceDiagramElementId, targetDiagramElementId);
      }
    },
    []
  );

  const onConnectEnd: OnConnectEnd = useCallback(
    (event: MouseEvent | TouchEvent, connectionState: FinalConnectionState) => {
      const nodeSource = nodeLookup.get(connectionState?.fromNode?.id || '');
      if (nodeSource && isHandleNode(nodeSource) && connectionState.fromHandle?.id?.startsWith('creationhandle')) {
        const sourceDiagramElementId = nodeSource.data.nodeId || nodeSource.data.edgeId;
        if (sourceDiagramElementId) {
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
              hoveredEdge && hoveredEdge.data && candidateDescriptionIds.includes(hoveredEdge.data.descriptionId);

            if (connectionState.fromNode && shouldConnectToAnEdge) {
              openPalette(event, sourceDiagramElementId, hoveredEdge.id);
            }
          }
        }
      }
    },
    [candidateDescriptionIds.join('-')]
  );

  return {
    onConnectEnd,
  };
};
