/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { memo, useCallback, useEffect } from 'react';
import {
  BaseEdge,
  Edge,
  EdgeLabelRenderer,
  EdgeProps,
  Node,
  ReactFlowState,
  getSmoothStepPath,
  useReactFlow,
  useStore,
} from 'reactflow';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
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
    stroke: style?.stroke ? getCSSColor(String(style.stroke), theme) : undefined,
  };

  if (selected) {
    multiLabelEdgeStyle.stroke = `${theme.palette.primary.main}`;
  }

  return multiLabelEdgeStyle;
};

export const MultiLabelEdge = memo(
  ({
    id,
    source,
    target,
    data,
    style,
    markerEnd,
    markerStart,
    selected,
    sourcePosition,
    targetPosition,
    sourceHandleId,
    targetHandleId,
  }: EdgeProps<MultiLabelEdgeData>) => {
    const theme = useTheme();
    const reactFlowInstance = useReactFlow<NodeData, EdgeData>();

    const sourceNode = useStore<Node<NodeData> | undefined>(
      useCallback((store: ReactFlowState) => store.nodeInternals.get(source), [source])
    );
    const targetNode = useStore<Node<NodeData> | undefined>(
      useCallback((store: ReactFlowState) => store.nodeInternals.get(target), [target])
    );

    if (!sourceNode || !targetNode) {
      return null;
    }

    const { x: sourceX, y: sourceY } = getHandleCoordinatesByPosition(sourceNode, sourcePosition, sourceHandleId ?? '');
    const { x: targetX, y: targetY } = getHandleCoordinatesByPosition(targetNode, targetPosition, targetHandleId ?? '');

    const [edgePath, labelX, labelY] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });

    const { beginLabel, endLabel, label, faded } = data || {};

    useEffect(() => {
      reactFlowInstance.setEdges((edges: Edge<EdgeData>[]) =>
        edges.map((edge) => {
          if (edge.id === id) {
            if (selected) {
              edge.updatable = true;
            } else {
              edge.updatable = false;
            }
          }
          return edge;
        })
      );
    }, [selected]);

    return (
      <>
        <BaseEdge
          id={id}
          path={edgePath}
          style={multiLabelEdgeStyle(theme, style, selected, faded)}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 1)}--selected)` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 1)}--selected)` : markerStart}
        />
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={label ? label.id : null} /> : null}
        <EdgeLabelRenderer>
          {beginLabel && (
            <Label
              diagramElementId={id}
              transform={`translate(2%, 0%) translate(${sourceX}px,${sourceY}px)`}
              label={beginLabel}
              faded={faded || false}
            />
          )}
          {label && (
            <Label diagramElementId={id} transform={`translate(${labelX}px,${labelY}px)`} label={label} faded={false} />
          )}
          {endLabel && (
            <Label
              diagramElementId={id}
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
