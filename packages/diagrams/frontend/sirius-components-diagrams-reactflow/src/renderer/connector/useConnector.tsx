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
  useStore,
  useUpdateNodeInternals,
} from 'reactflow';
import { NodeContext } from '../node/NodeContext';
import { NodeContextValue } from '../node/NodeContext.types';
import { useDiagramElementPalette } from '../palette/useDiagramElementPalette';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { NodeStyleProvider, UseConnectorValue } from './useConnector.types';

export const useConnector = (): UseConnectorValue => {
  const {
    position,
    setPosition,
    resetConnection,
    candidates,
    isNewConnection,
    setIsNewConnection,
    setFrozen,
    isFrozen,
  } = useContext<ConnectorContextValue>(ConnectorContext);

  const theme = useTheme();
  const { hideDiagramElementPalette } = useDiagramElementPalette();
  const updateNodeInternals = useUpdateNodeInternals();
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);

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

  const onConnect: OnConnect = (_connection: Connection) => {};

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

  const onConnectorContextualMenuClose = () => {
    resetConnection();
    cancelConnection();
    setFrozen(false);
  };

  const onConnectionStartElementClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    if (event.button === 0) {
      setIsNewConnection(true);
    }
  };

  const onConnectEnd: OnConnectEnd = (_event: MouseEvent | TouchEvent) => {
    if (!isFrozen) {
      resetConnection();
    }
  };

  const cancelConnection = useStore((state) => state.cancelConnection);

  const cancelConnectionLine = () => {
    cancelConnection();
    resetConnection();
  };

  return {
    onConnect,
    onConnectStart,
    onConnectEnd,
    onConnectorContextualMenuClose,
    onConnectionStartElementClick,
    setPosition,
    cancelConnectionLine,
    setFrozen,
    newConnectionStyleProvider,
    isNewConnection,
    position,
    isFrozen,
  };
};
