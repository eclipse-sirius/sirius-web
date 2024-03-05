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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@material-ui/core/styles';
import { memo } from 'react';
import { BaseEdge, EdgeLabelRenderer, Position } from 'reactflow';
import { MultiLabelEdgeProps } from './MultiLabelEdge.types';
import { Label } from '../Label';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';

const multiLabelEdgeStyle = (
  theme: Theme,
  style: React.CSSProperties | undefined,
  selected: boolean | undefined,
  faded: boolean | undefined
): React.CSSProperties => {
  const multiLabelEdgeStyle: React.CSSProperties = {
    opacity: faded ? '0.4' : '',
    ...style,
    stroke: style?.stroke ? getCSSColor(String(style.stroke), theme) : undefined,
  };

  if (selected) {
    multiLabelEdgeStyle.stroke = `${theme.palette.selected}`;
  }

  return multiLabelEdgeStyle;
};
export const MultiLabelEdge = memo(
  ({
    id,
    data,
    style,
    markerEnd,
    markerStart,
    selected,
    sourcePosition,
    targetPosition,
    sourceX,
    sourceY,
    targetX,
    targetY,
    edgeCenterX,
    edgeCenterY,
    svgPathString,
  }: MultiLabelEdgeProps) => {
    const { beginLabel, endLabel, label, faded } = data || {};
    const theme: Theme = useTheme();

    const getTranslateFromHandlePositon = (position: Position) => {
      switch (position) {
        case Position.Right:
          return 'translate(2%, -100%)';
        case Position.Left:
          return 'translate(-102%, -100%)';
        case Position.Top:
          return 'translate(2%, -100%)';
        case Position.Bottom:
          return 'translate(2%, 0%)';
      }
    };

    return (
      <>
        <BaseEdge
          id={id}
          path={svgPathString}
          style={multiLabelEdgeStyle(theme, style, selected, faded)}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 1)}--selected)` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 1)}--selected)` : markerStart}
        />
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={label ? label.id : null} /> : null}
        <EdgeLabelRenderer>
          {beginLabel && (
            <Label
              diagramElementId={id}
              transform={`${getTranslateFromHandlePositon(sourcePosition)} translate(${sourceX}px,${sourceY}px)`}
              label={beginLabel}
              faded={faded || false}
            />
          )}
          {label && (
            <Label
              diagramElementId={id}
              transform={`translate(${edgeCenterX}px,${edgeCenterY}px)`}
              label={label}
              faded={false}
            />
          )}
          {endLabel && (
            <Label
              diagramElementId={id}
              transform={`${getTranslateFromHandlePositon(targetPosition)} translate(${targetX}px,${targetY}px)`}
              label={endLabel}
              faded={faded || false}
            />
          )}
        </EdgeLabelRenderer>
      </>
    );
  }
);
