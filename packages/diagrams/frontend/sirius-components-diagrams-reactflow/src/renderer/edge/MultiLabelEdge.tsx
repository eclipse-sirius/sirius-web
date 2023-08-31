/*******************************************************************************
 * Copyright (c) 2023 Obeo and others.
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
import { memo } from 'react';
import { BaseEdge, EdgeLabelRenderer, EdgeProps, getSmoothStepPath } from 'reactflow';
import { Label } from '../Label';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';

export const MultiLabelEdge = memo(
  ({
    id,
    sourceX,
    sourceY,
    targetX,
    targetY,
    sourcePosition,
    targetPosition,
    data,
    style,
    markerEnd,
    markerStart,
  }: EdgeProps<MultiLabelEdgeData>) => {
    const [edgePath, labelX, labelY] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });

    const { beginLabel, endLabel, centerLabel } = data || {};

    return (
      <>
        <BaseEdge id={id} path={edgePath} style={style} markerEnd={markerEnd} markerStart={markerStart} />
        <EdgeLabelRenderer>
          {beginLabel && (
            <Label
              transform={`translate(2%, 0%) translate(${sourceX}px,${sourceY}px)`}
              label={beginLabel}
              faded={false}
            />
          )}
          {centerLabel && <Label transform={`translate(${labelX}px,${labelY}px)`} label={centerLabel} faded={false} />}
          {endLabel && (
            <Label
              transform={`translate(2%, -100%) translate(${targetX}px,${targetY}px)`}
              label={endLabel}
              faded={false}
            />
          )}
        </EdgeLabelRenderer>
      </>
    );
  }
);
