/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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

import { useTheme } from '@mui/material/styles';
import { useContext, useMemo } from 'react';
import { ConnectorContext } from './ConnectorContext';
import { ConnectorContextValue } from './ConnectorContext.types';
import { useConnector } from './useConnector';
import { UseConnectorEdgeStyleValue } from './useConnectorEdgeStyle.types';

export const useConnectorEdgeStyle = (descriptionId: string, isHovered: boolean): UseConnectorEdgeStyleValue => {
  const theme = useTheme();
  const { isConnectionInProgress } = useConnector();
  const { candidates } = useContext<ConnectorContextValue>(ConnectorContext);

  const style: React.CSSProperties = {};
  if (isConnectionInProgress) {
    const isConnectionCompatibleNode = Boolean(
      candidates.find((nodeDescription) => nodeDescription.id === descriptionId)
    );

    if (isConnectionCompatibleNode) {
      if (isHovered) {
        // Highlight the selected target
        style.stroke = `${theme.palette.selected}`;
      }
      // Make sure all compatible nodes, even normally faded ones, are fully visible
      style.opacity = '1';
    } else {
      // Force fade all incompatible nodes
      style.opacity = '0.4';
    }
  }

  const memoizedStyle = useMemo(
    () => style,
    [candidates.map((candidate) => candidate.id).join('-'), isConnectionInProgress, isHovered]
  );

  return { style: memoizedStyle };
};
