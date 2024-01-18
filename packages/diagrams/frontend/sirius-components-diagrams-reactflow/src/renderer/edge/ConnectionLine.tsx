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

import { Theme, useTheme } from '@material-ui/core/styles';
import React, { memo } from 'react';
import { ConnectionLineComponentProps, getSmoothStepPath } from 'reactflow';

const connectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

export const ConnectionLine = memo(
  ({ fromX, fromY, toX, toY, fromPosition, toPosition }: ConnectionLineComponentProps) => {
    const theme = useTheme();

    const [edgePath] = getSmoothStepPath({
      sourceX: fromX,
      sourceY: fromY,
      sourcePosition: fromPosition,
      targetX: toX,
      targetY: toY,
      targetPosition: toPosition,
    });

    return (
      <g>
        <path fill="none" className="animated" d={edgePath} style={connectionLineStyle(theme)} />
      </g>
    );
  }
);
