/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { ConnectionLineComponentProps, getSmoothStepPath } from '@xyflow/react';
import React, { memo } from 'react';
import { getNearestPointInPerimeter } from './EdgeLayout';

const connectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

export const ConnectionLine = memo(
  ({ fromX, fromY, toX, toY, fromPosition, toPosition, fromNode, toNode }: ConnectionLineComponentProps) => {
    const theme = useTheme();

    // Snap the ConnectionLine to the border of the targetted node
    if (toNode && toNode.width && toNode.height) {
      const pointToSnap = getNearestPointInPerimeter(
        toNode.internals.positionAbsolute.x,
        toNode.internals.positionAbsolute.y,
        toNode.width,
        toNode.height,
        toX,
        toY
      );

      toX = pointToSnap.XYpostion.x;
      toY = pointToSnap.XYpostion.y;
      toPosition = pointToSnap.position;
    }

    if (fromNode && fromNode.width && fromNode.height) {
      const pointToSnap = getNearestPointInPerimeter(
        fromNode.internals.positionAbsolute.x,
        fromNode.internals.positionAbsolute.y,
        fromNode.width,
        fromNode.height,
        fromX,
        fromY
      );

      fromX = pointToSnap.XYpostion.x;
      fromY = pointToSnap.XYpostion.y;
      fromPosition = pointToSnap.position;
    }

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
