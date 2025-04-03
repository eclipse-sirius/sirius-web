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

import { Theme, useTheme } from '@mui/material/styles';
import {
  Connection,
  Edge,
  FinalConnectionState,
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
import { useDiagramDescription } from '../../contexts/useDiagramDescription';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParameters } from '../edge/EdgeLayout';
import { EdgeAnchorNodeCreationHandlesData } from '../node/EdgeAnchorNodeCreationHandles.types';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { UseConnectorValue } from './useConnector.types';

const tempConnectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

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
  const theme = useTheme();

  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const { diagramDescription } = useDiagramDescription();

  const { setEdges, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
  const store = useStoreApi<Node<NodeData>, Edge<EdgeData>>();
  const { nodeLookup } = store.getState();
  const updateNodeInternals = useUpdateNodeInternals();

  const isConnectionInProgress = () => {
    const connectionNodeId = store.getState().connection.fromNode?.id;
    return (!!connectionNodeId && isNewConnection) || !!connection;
  };

  const isReconnectionInProgress = () => {
    const connectionNodeId = store.getState().connection.fromNode?.id;
    return !!connectionNodeId && !isNewConnection;
  };

  const onConnect: OnConnect = useCallback((connection: Connection) => {
    const nodeSource = nodeLookup.get(connection.source);
    if (nodeSource && isEdgeAnchorNodeCreationHandles(nodeSource)) {
      connection.source = nodeSource.data.edgeId;
    }
    setConnection(connection);
  }, []);

  const onConnectStart: OnConnectStart = useCallback(
    (_event: MouseEvent | TouchEvent, params: OnConnectStartParams) => {
      hideDiagramElementPalette();
      resetConnection();
      if (params.nodeId) {
        updateNodeInternals(params.nodeId);
      }
    },
    [hideDiagramElementPalette]
  );

  const onConnectorContextualMenuClose = () => resetConnection();

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

      const hoveredEdge = getEdges().find((edge) => edge.data && edge.data.isHovered);
      if (hoveredEdge && connectionState.fromNode && isNewConnection) {
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

  const addTempConnectionLine = () => {
    const sourceNode = nodeLookup.get(connection?.source ?? '');
    const targetNode = nodeLookup.get(connection?.target ?? '');
    if (sourceNode && targetNode && !!connection) {
      const { targetPosition, sourcePosition } = getEdgeParameters(
        sourceNode,
        targetNode,
        store.getState().nodeLookup,
        diagramDescription.arrangeLayoutDirection
      );

      const edge: Edge<EdgeData> = {
        id: 'temp',
        source: connection.source ?? '',
        target: connection.target ?? '',
        sourceHandle: `creationhandle--${connection.source}--${sourcePosition}`,
        targetHandle: `handle--${connection.target}--temp--${targetPosition}`,
        type: 'smoothstep',
        animated: true,
        reconnectable: false,
        style: tempConnectionLineStyle(theme),
        zIndex: 2002,
      };
      setEdges((oldEdges) => [...oldEdges, edge]);
    }
  };

  const removeTempConnectionLine = () => {
    setEdges((oldEdges) => oldEdges.filter((item) => !item.id.includes('temp')));
  };

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose,
    onConnectionStartElementClick,
    addTempConnectionLine,
    removeTempConnectionLine,
    connection,
    position,
    isConnectionInProgress,
    isReconnectionInProgress,
    candidates,
  };
};
