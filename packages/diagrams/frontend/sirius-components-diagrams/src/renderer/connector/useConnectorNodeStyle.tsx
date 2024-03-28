/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { NodeContext } from '../node/NodeContext';
import { NodeContextValue } from '../node/NodeContext.types';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { UseConnectorNodeStyleValue } from './useConnectorStyle.types';

export const useConnectorNodeStyle = (nodeId: string, descriptionId: string): UseConnectorNodeStyleValue => {
  const theme = useTheme();

  const { candidates, isNewConnection } = useContext<ConnectorContextValue>(ConnectorContext);
  const { hoveredNode } = useContext<NodeContextValue>(NodeContext);

  const style: React.CSSProperties = {};
  if (isNewConnection) {
    const isConnectionCompatibleNode = Boolean(
      candidates.find((nodeDescription) => nodeDescription.id === descriptionId)
    );
    const isSelectedNode = hoveredNode?.id === nodeId;
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
  return { style };
};
