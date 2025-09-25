/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import { useMemo } from 'react';
import { useConnectorContext } from './useConnectorContext';
import { UseConnectorEdgeStyleValue } from './useConnectorEdgeStyle.types';

export const useConnectorEdgeStyle = (descriptionId: string, isHovered: boolean): UseConnectorEdgeStyleValue => {
  const theme = useTheme();
  const { toolCandidates, connection } = useConnectorContext();

  const style: React.CSSProperties = {};
  if (!!connection) {
    const isConnectionCompatibleNode = Boolean(
      toolCandidates.find((tool) => tool.candidateDescriptionIds.includes(descriptionId))
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
    [toolCandidates.map((candidate) => candidate.id).join('-'), !!connection, isHovered]
  );

  return { style: memoizedStyle };
};
