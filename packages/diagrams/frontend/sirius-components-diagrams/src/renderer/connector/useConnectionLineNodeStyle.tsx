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

import { Theme, useTheme } from '@mui/material/styles';
import { useMemo } from 'react';
import { ConnectionLinePositionOnNode } from '../DiagramRenderer.types';
import { UseConnectionLineNodeStyleValues } from './useConnectionLineNodeStyle.types';

const getStyle = (theme: Theme, connectionLinePositionOnNode: ConnectionLinePositionOnNode): React.CSSProperties => {
  switch (connectionLinePositionOnNode) {
    case 'border':
      return { borderColor: 'orange' };
    case 'center':
      return { borderColor: theme.palette.selected };
    default:
      return {};
  }
};

export const useConnectionLineNodeStyle = (
  connectionLinePositionOnNode: ConnectionLinePositionOnNode
): UseConnectionLineNodeStyleValues => {
  const theme: Theme = useTheme();
  const memoizedStyle: React.CSSProperties = useMemo(
    () => getStyle(theme, connectionLinePositionOnNode),
    [connectionLinePositionOnNode]
  );

  return { style: memoizedStyle };
};
