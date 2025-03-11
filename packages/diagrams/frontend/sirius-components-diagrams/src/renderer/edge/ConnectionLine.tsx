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
import {
  ConnectionLineComponentProps,
  Edge,
  getSmoothStepPath,
  Node,
  useReactFlow,
  useUpdateNodeInternals,
} from '@xyflow/react';
import React, { memo, useEffect } from 'react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { getNearestPointInPerimeter, getNodesUpdatedWithHandles } from './EdgeLayout';

const connectionLineStyle = (theme: Theme): React.CSSProperties => {
  return {
    stroke: theme.palette.selected,
    strokeWidth: theme.spacing(0.2),
  };
};

export const ConnectionLine = memo(
  ({
    fromX,
    fromY,
    toX,
    toY,
    fromPosition,
    toPosition,
    fromNode,
    toNode,
    fromHandle,
  }: ConnectionLineComponentProps<Node<NodeData>>) => {
    const theme = useTheme();
    const { getNodes, setNodes, getEdges } = useReactFlow<Node<NodeData>, Edge<EdgeData>>();
    const updateNodeInternals = useUpdateNodeInternals();

    const edgeId = fromNode.data.connectionHandles.find((handle) => handle.id === fromHandle?.id)?.edgeId;
    const targetHandle = getEdges().find((e) => e.id === edgeId)?.targetHandle;
    const sourceHandle = getEdges().find((e) => e.id === edgeId)?.sourceHandle;
    const handleToUpdate = targetHandle === fromHandle.id ? sourceHandle : targetHandle;

    if (handleToUpdate) {
      if (edgeId && toNode && toNode.width && toNode.height) {
        // Snap the ConnectionLine to the border of the targetted node
        const pointToSnap = getNearestPointInPerimeter(
          toNode.internals.positionAbsolute.x,
          toNode.internals.positionAbsolute.y,
          toNode.width,
          toNode.height,
          toX,
          toY
        );

        toX = pointToSnap.XYPosition.x;
        toY = pointToSnap.XYPosition.y;
        toPosition = pointToSnap.position;
      }
    }

    useEffect(() => {
      if (toNode && edgeId && handleToUpdate) {
        // Update handle position early to avoid flicking effect after onReconnectEnd
        const nodes = getNodesUpdatedWithHandles(
          getNodes(),
          toNode,
          edgeId,
          handleToUpdate,
          { x: toX, y: toY },
          toPosition
        );
        setNodes(nodes);
        updateNodeInternals(toNode.id);
      }
    }, [toX, toY]);

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
