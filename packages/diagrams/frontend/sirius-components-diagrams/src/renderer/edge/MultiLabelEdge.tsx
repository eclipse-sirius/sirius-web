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
import { Theme, useTheme } from '@mui/material/styles';
import { BaseEdge, Edge, EdgeLabelRenderer, Node, Position, XYPosition } from '@xyflow/react';
import { memo, useContext, useMemo } from 'react';
import { DraggableData } from 'react-draggable';
import { Label } from '../Label';
import { NodeData } from '../DiagramRenderer.types';
import { RawDiagram } from '../layout/layout.types';
import { useSynchronizeLayoutData } from '../layout/useSynchronizeLayoutData';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useStore } from '../../representation/useStore';
import { BendPoint, TemporaryBendPoint } from './BendPoint';
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

const getMiddlePoint = (p1: XYPosition, p2: XYPosition): XYPosition => {
  return { x: (p1.x + p2.x) / 2, y: (p1.y + p2.y) / 2 };
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
    bendingPoints,
  }: MultiLabelEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const { beginLabel, endLabel, label, faded } = data || {};
    const theme = useTheme();
    const { getEdges, setEdges, getNodes } = useStore();
    const { refreshEventPayloadId } = useContext<DiagramContextValue>(DiagramContext);
    const { synchronizeLayoutData } = useSynchronizeLayoutData();

    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);
    const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
    const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);

    const onDragStop = (eventData: DraggableData, index: number) => {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === id);
      if (bendingPoints) {
        bendingPoints[index] = {
          x: eventData.x,
          y: eventData.y,
        };
      }
      if (edge?.data) {
        edge.data.bendingPoints = bendingPoints ?? null;
      }
      setEdges(edges);
      const finalDiagram: RawDiagram = {
        nodes: [...getNodes()] as Node<NodeData, DiagramNodeType>[],
        edges: edges,
      };
      synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
    };

    const onTemporaryDragStop = (eventData: DraggableData, index: number) => {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === id);
      if (edge?.data?.bendingPoints) {
        edge.data.bendingPoints.splice(index, 0, {
          x: eventData.x,
          y: eventData.y,
        });
        setEdges(edges);
        const finalDiagram: RawDiagram = {
          nodes: [...getNodes()] as Node<NodeData, DiagramNodeType>[],
          edges: edges,
        };
        synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
      }
    };

    const onDoubleClick = (index: number) => {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === id);
      if (edge?.data?.bendingPoints) {
        edge.data.bendingPoints.splice(index, 1);
        setEdges(edges);
        const finalDiagram: RawDiagram = {
          nodes: [...getNodes()] as Node<NodeData, DiagramNodeType>[],
          edges: edges,
        };
        synchronizeLayoutData(refreshEventPayloadId, finalDiagram);
      }
    };

    const middlePoints: XYPosition[] = [];
    if (bendingPoints) {
      if (bendingPoints.length > 0) {
        for (let i = 0; i < bendingPoints.length; i++) {
          const p1 = i === 0 ? { x: sourceX, y: sourceY } : bendingPoints[i - 1];
          const p2 = bendingPoints[i];
          if (p1 && p2) {
            middlePoints.push(getMiddlePoint(p1, p2));
          }
        }
        middlePoints.push(getMiddlePoint(bendingPoints[bendingPoints.length - 1]!, { x: targetX, y: targetY }));
      } else {
        middlePoints.push(getMiddlePoint({ x: sourceX, y: sourceY }, { x: targetX, y: targetY }));
      }
    }

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
            targetObjectId={data?.targetObjectId ?? ''}
            labelId={label ? label.id : null}
          />
        ) : null}
        {selected &&
          bendingPoints &&
          bendingPoints.map((point, index) => (
            <BendPoint
              key={index}
              x={point.x}
              y={point.y}
              index={index}
              onDragStop={onDragStop}
              onDoubleClick={onDoubleClick}
            />
          ))}
        {selected &&
          middlePoints &&
          middlePoints.map((point, index) => (
            <TemporaryBendPoint key={index} x={point.x} y={point.y} index={index} onDragStop={onTemporaryDragStop} />
          ))}
        <EdgeLabelRenderer>
          {beginLabel && (
            <div style={labelContainerStyle(`${sourceLabelTranslation} translate(${sourceX}px,${sourceY}px)`)}>
              <Label diagramElementId={id} label={beginLabel} faded={!!faded} />
            </div>
          )}
          {label && (
            <div style={labelContainerStyle(`translate(${edgeCenterX}px,${edgeCenterY}px)`)}>
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
