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
import { memo, useContext, useMemo } from 'react';
import { BaseEdge, EdgeLabelRenderer, EdgeProps, Node, Position, getSmoothStepPath, useStoreApi } from 'reactflow';
import { NodeTypeContext } from '../../contexts/NodeContext';
import { NodeTypeContextValue } from '../../contexts/NodeContext.types';
import { NodeData } from '../DiagramRenderer.types';
import { Label } from '../Label';
import { DiagramNodeType } from '../node/NodeTypes.types';
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
    multiLabelEdgeStyle.stroke = `${theme.palette.selected}`;
  }

  return multiLabelEdgeStyle;
};

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

const labelContainerStyle = (transform: string): React.CSSProperties => {
  return {
    transform,
    position: 'absolute',
    padding: 5,
    zIndex: 1001,
  };
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
    const { beginLabel, endLabel, label, faded } = data || {};
    const theme = useTheme();
    const { nodeLayoutHandlers } = useContext<NodeTypeContextValue>(NodeTypeContext);

    const { nodeInternals } = useStoreApi().getState();

    const sourceNode = nodeInternals.get(source);
    const targetNode = nodeInternals.get(target);

    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);
    const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
    const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);

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

    return (
      <>
        <BaseEdge
          id={id}
          path={edgePath}
          style={edgeStyle}
          markerEnd={selected ? `${markerEnd?.slice(0, markerEnd.length - 1)}--selected)` : markerEnd}
          markerStart={selected ? `${markerStart?.slice(0, markerStart.length - 1)}--selected)` : markerStart}
        />
        {selected ? <DiagramElementPalette diagramElementId={id} labelId={label ? label.id : null} /> : null}
        <EdgeLabelRenderer>
          {beginLabel && (
            <div style={labelContainerStyle(`${sourceLabelTranslation} translate(${sourceX}px,${sourceY}px)`)}>
              <Label diagramElementId={id} label={beginLabel} faded={!!faded} />
            </div>
          )}
          {label && (
            <div style={labelContainerStyle(`translate(${labelX}px,${labelY}px)`)}>
              <Label diagramElementId={id} label={label} faded={!!faded} />
            </div>
          )}
          {endLabel && (
            <div style={labelContainerStyle(`${targetLabelTranslation} translate(${targetX}px,${targetY}px)`)}>
              <Label diagramElementId={id} label={endLabel} faded={!!faded} />
            </div>
          )}
        </EdgeLabelRenderer>
      </>
    );
  }
);
