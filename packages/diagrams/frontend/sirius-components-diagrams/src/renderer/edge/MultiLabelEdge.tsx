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

import { getCSSColor } from '@eclipse-sirius/sirius-components-core';
import { Theme, useTheme } from '@mui/material/styles';
import { BaseEdge, Edge } from '@xyflow/react';
import { memo, useEffect, useMemo } from 'react';
import { useStore } from '../../representation/useStore';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { DraggableEdgeLabels } from './DraggableEdgeLabels';
import { MultiLabelEdgeData, MultiLabelEdgeProps } from './MultiLabelEdge.types';

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
  }: MultiLabelEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const { label, faded } = data || {};
    const theme = useTheme();
    const { setEdges } = useStore();
    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);

    useEffect(() => {
      setEdges((prevEdges) =>
        prevEdges.map((prevEdge) => {
          if (prevEdge.id === id && prevEdge.data) {
            return {
              ...prevEdge,
              data: {
                ...prevEdge.data,
                edgePath: svgPathString,
              },
            };
          }
          return prevEdge;
        })
      );
    }, [svgPathString]);

    return (
      <>
        <BaseEdge
          id={id}
          path={svgPathString}
          style={edgeStyle}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 2)}--selected')` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 2)}--selected')` : markerStart}
        />
        {selected ? (
          <DiagramElementPalette
            diagramElementId={id}
            elementDescriptionId={data?.descriptionId ?? ''}
            targetObjectId={data?.targetObjectId ?? ''}
            labelId={label ? label.id : null}
          />
        ) : null}
        {data ? (
          <DraggableEdgeLabels
            id={id}
            data={data}
            selected={!!selected}
            sourcePosition={sourcePosition}
            targetPosition={targetPosition}
            sourceX={sourceX}
            sourceY={sourceY}
            targetX={targetX}
            targetY={targetY}
            edgeCenter={edgeCenterX && edgeCenterY ? { x: edgeCenterX, y: edgeCenterY } : undefined}
          />
        ) : null}
      </>
    );
  }
);
