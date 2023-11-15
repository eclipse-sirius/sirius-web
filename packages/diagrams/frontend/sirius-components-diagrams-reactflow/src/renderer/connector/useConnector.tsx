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
  useUpdateNodeInternals,
} from 'reactflow';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { NodeStyleProvider, UseConnectorValue } from './useConnector.types';

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

  const newConnectionStyleProvider: NodeStyleProvider = {
    getNodeStyle: (id: string): React.CSSProperties => {
      const nodeStyle: React.CSSProperties = {};
      if (isNewConnection && candidates.map((node) => node.id).includes(id)) {
        nodeStyle.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
        nodeStyle.opacity = 1;
      } else if (isNewConnection) {
        nodeStyle.opacity = 0.4;
      }
      return nodeStyle;
    },
    getHandleStyle: (id: string): React.CSSProperties => {
      const handleStyle: React.CSSProperties = {};
      if (isNewConnection && candidates.map((node) => node.id).includes(id)) {
        handleStyle.boxShadow = `0px 0px 2px 2px ${theme.palette.primary.main}`;
        handleStyle.opacity = 1;
      } else if (isNewConnection) {
        handleStyle.opacity = 0.4;
      }
      return handleStyle;
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
    resetConnection();
    if (params.nodeId) {
      updateNodeInternals(params.nodeId);
    }
  };

  const onConnectorContextualMenuClose = () => resetConnection();

  const onConnectionStartElementClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (event.button === 0) {
      setIsNewConnection(true);
    }
  };

  const onConnectEnd: OnConnectEnd = (event: MouseEvent | TouchEvent) => {
    if ('clientX' in event && 'clientY' in event) {
      setPosition({ x: event.clientX || 0, y: event.clientY });
    } else if ('touches' in event) {
      const touchEvent = event as TouchEvent;
      setPosition({ x: touchEvent.touches[0]?.clientX || 0, y: touchEvent.touches[0]?.clientY || 0 });
    }
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
    position,
  };
};
