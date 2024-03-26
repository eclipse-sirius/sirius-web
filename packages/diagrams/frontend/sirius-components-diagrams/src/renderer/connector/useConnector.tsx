/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Theme, useTheme } from '@material-ui/core/styles';
import {
  Connection,
  Edge,
  Node,
  OnConnect,
  OnConnectEnd,
  OnConnectStart,
  OnConnectStartParams,
  useReactFlow,
  useStore,
  useUpdateNodeInternals,
} from '@xyflow/react';
import { useCallback, useContext } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getEdgeParameters } from '../edge/EdgeLayout';
import { NodeContext } from '../node/NodeContext';
import { NodeContextValue } from '../node/NodeContext.types';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { NodeStyleProvider, UseConnectorValue } from './useConnector.types';

const tempConnectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

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
  const updateNodeInternals = useUpdateNodeInternals();
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);
  const connectionNodeId = useStore((state) => state.connectionStartHandle?.nodeId);
  const isConnectionInProgress = (!!connectionNodeId && isNewConnection) || !!connection;
  const isReconnectionInProgress = !!connectionNodeId && !isNewConnection;

  const newConnectionStyleProvider: NodeStyleProvider = {
    getNodeStyle: (id: string, descriptionId: string): React.CSSProperties => {
      const style: React.CSSProperties = {};
      if (isNewConnection) {
        const isConnectionCompatibleNode = Boolean(
          candidates.find((nodeDescription) => nodeDescription.id === descriptionId)
        );
        const isSelectedNode = hoveredNode?.id === id;
        if (isConnectionCompatibleNode) {
          if (isSelectedNode) {
            // Highlight the selected target
            style.boxShadow = `0px 0px 2px 2px ${theme.palette.selected}`;
          }
          // Make sure all compatible nodes, even normally faded ones, are fully visible
          style.opacity = '1';
        } else {
          // Force fade all incompatible nodes
          style.opacity = '0.4';
        }
      }
      return style;
    },
    getHandleStyle: (id: string): React.CSSProperties => {
      const handleStyle: React.CSSProperties = {};
      if (isNewConnection && candidates.map((node) => node.id).includes(id)) {
        handleStyle.boxShadow = `0px 0px 2px 2px ${theme.palette.selected}`;
        handleStyle.opacity = 1;
      } else if (isNewConnection) {
        handleStyle.opacity = 0.4;
      }
      return handleStyle;
    },
  };

  const onConnect: OnConnect = useCallback((connection: Connection) => {
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

  const onConnectEnd: OnConnectEnd = useCallback((event: MouseEvent | TouchEvent) => {
    if ('clientX' in event && 'clientY' in event) {
      setPosition({ x: event.clientX || 0, y: event.clientY });
    } else if ('touches' in event) {
      const touchEvent = event as TouchEvent;
      setPosition({ x: touchEvent.touches[0]?.clientX || 0, y: touchEvent.touches[0]?.clientY || 0 });
    }
    setIsNewConnection(false);
  }, []);

  const reactFlowInstance = useReactFlow<Node<NodeData>, Edge<EdgeData>>();

  const addTempConnectionLine = () => {
    const sourceNode = reactFlowInstance.getNode(connection?.source ?? '');
    const targetNode = reactFlowInstance.getNode(connection?.target ?? '');
    if (sourceNode && targetNode && !!connection) {
      const { targetPosition, sourcePosition } = getEdgeParameters(
        sourceNode,
        targetNode,
        reactFlowInstance.getNodes()
      );

      const edge: Edge<EdgeData> = {
        id: 'temp',
        source: connection.source ?? '',
        target: connection.target ?? '',
        sourceHandle: `creationhandle--${connection.source}--${sourcePosition}`,
        targetHandle: `handle--${connection.target}--temp--${targetPosition}`,
        type: 'smoothstep',
        animated: true,
        updatable: false,
        style: tempConnectionLineStyle(theme),
        zIndex: 2002,
      };
      reactFlowInstance.setEdges((oldEdges) => [...oldEdges, edge]);
    }
  };

  const removeTempConnectionLine = () => {
    reactFlowInstance.setEdges(() => reactFlowInstance.getEdges().filter((item) => !item.id.includes('temp')));
  };

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose,
    onConnectionStartElementClick,
    addTempConnectionLine,
    removeTempConnectionLine,
    newConnectionStyleProvider,
    connection,
    position,
    isConnectionInProgress,
    isReconnectionInProgress,
    candidates,
  };
};
