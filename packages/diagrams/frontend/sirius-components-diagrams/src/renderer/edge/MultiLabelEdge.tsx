/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { memo, useCallback, useContext } from 'react';
import {
  BaseEdge,
  EdgeLabelRenderer,
  EdgeProps,
  Node,
  Position,
  ReactFlowState,
  getSmoothStepPath,
  useStore,
} from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { getHandleCoordinatesByPosition } from './EdgeLayout';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { DiagramNodeType } from '../node/NodeTypes.types';

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
    const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);

    const sourceNode = useStore<Node<NodeData> | undefined>(
      useCallback((store: ReactFlowState) => store.nodeInternals.get(source), [source])
    );
    const targetNode = useStore<Node<NodeData> | undefined>(
      useCallback((store: ReactFlowState) => store.nodeInternals.get(target), [target])
    );

    if (!sourceNode || !targetNode) {
      return null;
    }

    const sourceLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
      nodeLayoutHandler.canHandle(sourceNode as Node<NodeData, DiagramNodeType>)
    );
    const targetLayoutHandler = nodeLayoutHandlers.find((nodeLayoutHandler) =>
      nodeLayoutHandler.canHandle(targetNode as Node<NodeData, DiagramNodeType>)
    );

    let { x: sourceX, y: sourceY } = getHandleCoordinatesByPosition(
      sourceNode,
      sourcePosition,
      sourceHandleId ?? '',
      sourceLayoutHandler?.calculateCustomNodeEdgeHandlePosition
    );
    let { x: targetX, y: targetY } = getHandleCoordinatesByPosition(
      targetNode,
      targetPosition,
      targetHandleId ?? '',
      targetLayoutHandler?.calculateCustomNodeEdgeHandlePosition
    );

    // trick to have the source of the edge positioned at the very border of a node
    // if the edge has a marker, then only the marker need to touch the node
    const handleSourceRadius = markerStart == undefined || markerStart.includes('None') ? 2 : 3;
    switch (sourcePosition) {
      case Position.Right:
        sourceX = sourceX + handleSourceRadius;
        break;
      case Position.Left:
        sourceX = sourceX - handleSourceRadius;
        break;
      case Position.Top:
        sourceY = sourceY - handleSourceRadius;
        break;
      case Position.Bottom:
        sourceY = sourceY + handleSourceRadius;
        break;
    }
    // trick to have the target of the edge positioned at the very border of a node
    // if the edge has a marker, then only the marker need to touch the node
    const handleTargetRadius = markerEnd == undefined || markerEnd.includes('None') ? 2 : 3;
    switch (targetPosition) {
      case Position.Right:
        targetX = targetX + handleTargetRadius;
        break;
      case Position.Left:
        targetX = targetX - handleTargetRadius;
        break;
      case Position.Top:
        targetY = targetY - handleTargetRadius;
        break;
      case Position.Bottom:
        targetY = targetY + handleTargetRadius;
        break;
    }

    const [edgePath, labelX, labelY] = getSmoothStepPath({
      sourceX,
      sourceY,
      sourcePosition,
      targetX,
      targetY,
      targetPosition,
    });

    const { beginLabel, endLabel, label, faded } = data || {};

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
              transform={`${getTranslateFromHandlePositon(sourcePosition)} translate(${sourceX}px,${sourceY}px)`}
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
