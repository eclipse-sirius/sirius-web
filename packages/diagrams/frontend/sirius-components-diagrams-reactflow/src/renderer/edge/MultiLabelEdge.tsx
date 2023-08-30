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
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo } from 'react';
import { BaseEdge, EdgeLabelRenderer, EdgeProps, getSmoothStepPath } from 'reactflow';
import { Label } from '../Label';
import { EdgePalette } from '../palette/EdgePalette';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';

const multiLabelEdgeStyle = (
  theme: Theme,
  style: React.CSSProperties | undefined,
  selected: boolean | undefined,
  faded: boolean | undefined
): React.CSSProperties => {
  const multiLabelEdgeStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    ...style,
  };

  if (selected) {
    multiLabelEdgeStyle.stroke = `${theme.palette.primary.main}`;
  }

  return multiLabelEdgeStyle;
};

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
    selected,
  }: EdgeProps<MultiLabelEdgeData>) => {
    const theme = useTheme();
    const [edgePath, labelX, labelY] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });

    const { beginLabel, endLabel, label, faded } = data || {};

    return (
      <>
        <BaseEdge
          id={id}
          path={edgePath}
          style={multiLabelEdgeStyle(theme, style, selected, faded)}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 1)}--selected)` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 1)}--selected)` : markerStart}
        />
        {selected ? <EdgePalette edgeId={id} labelId={label ? label.id : null} /> : null}
        <EdgeLabelRenderer>
          {beginLabel && (
            <Label
              transform={`translate(2%, 0%) translate(${sourceX}px,${sourceY}px)`}
              label={beginLabel}
              faded={faded || false}
            />
          )}
          {label && <Label transform={`translate(${labelX}px,${labelY}px)`} label={label} faded={false} />}
          {endLabel && (
            <Label
              transform={`translate(2%, -100%) translate(${targetX}px,${targetY}px)`}
              label={endLabel}
              faded={faded || false}
            />
          )}
        </EdgeLabelRenderer>
      </>
    );
  }
);
