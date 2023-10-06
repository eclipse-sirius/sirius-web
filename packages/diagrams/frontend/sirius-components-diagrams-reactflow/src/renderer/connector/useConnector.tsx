/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import { useTheme } from '@material-ui/core/styles';
import { useContext } from 'react';
import {
  Connection,
  OnConnect,
  OnConnectEnd,
  OnConnectStart,
  OnConnectStartParams,
  useReactFlow,
  useUpdateNodeInternals,
} from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { NodeContext } from '../node/NodeContext';
import { NodeContextValue } from '../node/NodeContext.types';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { NodeStyleProvider, UseConnectorValue } from './useConnector.types';

export const useConnector = (): UseConnectorValue => {
  const { connection, setConnection, resetConnection, candidates, isNewConnection, setIsNewConnection } =
    useContext<ConnectorContextValue>(ConnectorContext);

  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);
  const theme = useTheme();
  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const reactFlowInstance = useReactFlow<NodeData, EdgeData>();
  const updateNodeInternals = useUpdateNodeInternals();

  const newConnectionStyleProvider: NodeStyleProvider = {
    getNodeStyle: (nodeId: string, descriptionId: string): React.CSSProperties => {
      const node = reactFlowInstance.getNode(nodeId);

      const isCompatibleConnectionTarget: boolean = candidates
        .map((nodeDescription) => nodeDescription.id)
        .includes(descriptionId);
      const nodeStyle: React.CSSProperties = {};

      if (isNewConnection && !isCompatibleConnectionTarget && !node?.selected) {
        nodeStyle.opacity = 0.4;
      }

      const isCompatibleHoveredConnectionTarget: boolean = candidates
        .map((nodeDescription) => nodeDescription.id)
        .includes(hoveredNode?.data.descriptionId ?? '');

      if (nodeId === hoveredNode?.id && isCompatibleHoveredConnectionTarget && isNewConnection) {
        nodeStyle.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
      }

      return nodeStyle;
    },
  };

  const onConnect: OnConnect = (connection: Connection) => {
    setConnection(connection);
  };

  const onConnectStart: OnConnectStart = (
    _event: React.MouseEvent | React.TouchEvent,
    params: OnConnectStartParams
  ) => {
    hideDiagramElementPalette();
    //Refresh handles
    updateNodeInternals(params.nodeId ?? '');
    resetConnection();
  };

  const onConnectorContextualMenuClose = () => resetConnection();

  const onConnectionStartElementClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (event.button === 0) {
      setIsNewConnection(true);
    }
  };

  const onConnectEnd: OnConnectEnd = (_event: MouseEvent | TouchEvent) => {
    setIsNewConnection(false);
  };

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose,
    onConnectionStartElementClick,
    newConnectionStyleProvider,
    connection,
  };
};
