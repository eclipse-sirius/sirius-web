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
import { BaseEdge, Edge, EdgeLabelRenderer, Position, XYPosition } from '@xyflow/react';
import { memo, useEffect, useMemo, useState } from 'react';
import { DraggableData } from 'react-draggable';
import { useStore } from '../../representation/useStore';
import { Label } from '../Label';
import { DiagramElementPalette } from '../palette/DiagramElementPalette';
import { BendPoint, TemporaryBendPoint } from './BendPoint';
import { MultiLabelEdgeData } from './MultiLabelEdge.types';
import { MultiLabelEditableEdgeProps, MultiLabelEditableEdgeState, MiddlePoint } from './MultiLabelEditableEdge.types';
import { useEditableEdgePath } from './useEditableEdgePath';

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

function isMultipleOfTwo(num: number): boolean {
  return num % 2 === 0;
}

const generateNewBendPointToKeepRectilinearSegment = (
  existingBendPoint: XYPosition[],
  bendPointIndex: number,
  newX: number,
  newY: number,
  prevMiddlePoint: XYPosition,
  nextMiddlePoint: XYPosition
) => {
  const newPoints = [...existingBendPoint];
  newPoints[bendPointIndex] = prevMiddlePoint;
  newPoints.splice(bendPointIndex + 1, 0, {
    x: prevMiddlePoint.x,
    y: newY,
  });
  newPoints.splice(bendPointIndex + 2, 0, {
    x: newX,
    y: newY,
  });
  newPoints.splice(bendPointIndex + 3, 0, {
    x: newX,
    y: nextMiddlePoint.y,
  });
  newPoints.splice(bendPointIndex + 4, 0, nextMiddlePoint);
  return newPoints;
};

const cleanBendPoint = (bendPoints: XYPosition[]): XYPosition[] => {
  const cleanedPoints: XYPosition[] = [];

  const margin = 1.8;
  for (let i = 0; i < bendPoints.length; i++) {
    const { x: x1, y: y1 } = bendPoints[i]!;
    let isSimilar = false;

    if (i < bendPoints.length - 1) {
      const { x: x2, y: y2 } = bendPoints[i + 1]!;

      if (Math.abs(x1 - x2) <= margin && Math.abs(y1 - y2) <= margin) {
        isSimilar = true;
        i++;
      }
    }

    if (!isSimilar) {
      cleanedPoints.push({ x: x1, y: y1 });
    }
  }
  return cleanedPoints;
};

export const MultiLabelEditableEdge = memo(
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
    bendingPoints,
  }: MultiLabelEditableEdgeProps<Edge<MultiLabelEdgeData>>) => {
    const { beginLabel, endLabel, label, faded } = data || {};
    const theme = useTheme();
    const { getEdges, setEdges } = useStore();
    const { synchronizeEdgeLayoutData } = useEditableEdgePath();

    const initialState: MultiLabelEditableEdgeState = {
      localBendingPoints: bendingPoints,
      middleBendingPoints: [],
      temporaryPointDragIndex: null,
      temporaryPointDragPosition: null,
    };
    const [state, setState] = useState<MultiLabelEditableEdgeState>(initialState);

    useEffect(() => {
      setState((prevState) => ({ ...prevState, localBendingPoints: bendingPoints }));
    }, [bendingPoints.map((point) => point.x + point.y).join()]);

    const edgeStyle = useMemo(() => multiLabelEdgeStyle(theme, style, selected, faded), [style, selected, faded]);
    const sourceLabelTranslation = useMemo(() => getTranslateFromHandlePositon(sourcePosition), [sourcePosition]);
    const targetLabelTranslation = useMemo(() => getTranslateFromHandlePositon(targetPosition), [targetPosition]);

    const onBendingPointDragStop = (eventData: DraggableData, index: number) => {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === id);
      const prevMiddle = getMiddlePoint(
        bendingPoints[index - 1] ?? {
          x: sourceX,
          y: sourceY,
        },
        bendingPoints[index]!
      );
      const nextMiddle = getMiddlePoint(
        bendingPoints[index]!,
        bendingPoints[index + 1] ?? {
          x: targetX,
          y: targetY,
        }
      );

      const newPoints = generateNewBendPointToKeepRectilinearSegment(
        bendingPoints,
        index,
        eventData.x,
        eventData.y,
        prevMiddle,
        nextMiddle
      );
      if (edge?.data) {
        edge.data.bendingPoints = cleanBendPoint(newPoints);
      }
      setState((prevState) => ({ ...prevState, localBendingPoints: newPoints }));
      setEdges(edges);
      synchronizeEdgeLayoutData(edges);
    };

    const onBendingPointDrag = (eventData: DraggableData, index: number) => {
      const prevMiddle = getMiddlePoint(
        bendingPoints[index - 1] ?? {
          x: sourceX,
          y: sourceY,
        },
        bendingPoints[index]!
      );
      const nextMiddle = getMiddlePoint(
        bendingPoints[index]!,
        bendingPoints[index + 1] ?? {
          x: targetX,
          y: targetY,
        }
      );

      const newPoints = generateNewBendPointToKeepRectilinearSegment(
        bendingPoints,
        index,
        eventData.x,
        eventData.y,
        prevMiddle,
        nextMiddle
      );

      setState((prevState) => ({ ...prevState, localBendingPoints: newPoints }));
    };

    const onTemporaryPointDragStop = (_eventData: DraggableData, _index: number) => {
      const edges = getEdges();
      const edge = edges.find((edge) => edge.id === id);
      if (edge?.data) {
        edge.data.bendingPoints = cleanBendPoint(state.localBendingPoints);
        setEdges(edges);
        synchronizeEdgeLayoutData(edges);
      }
    };

    const onTemporaryPointDrag = (eventData: DraggableData, index: number, direction: 'x' | 'y') => {
      const newPoints = [...bendingPoints];

      if (direction === 'x' && newPoints[index - 1] && newPoints[index]) {
        newPoints[index - 1]!.x = eventData.x;
        newPoints[index]!.x = eventData.x;
      } else if (direction === 'y' && newPoints[index - 1] && newPoints[index]) {
        newPoints[index - 1]!.y = eventData.y;
        newPoints[index]!.y = eventData.y;
      }

      setState((prevState) => ({ ...prevState, localBendingPoints: newPoints }));
    };

    const computeMiddlePoints = () => {
      const middlePoints: MiddlePoint[] = [];
      const margin = 1.8;
      if (state.localBendingPoints.length > 0) {
        for (let i = 0; i < state.localBendingPoints.length; i++) {
          const p1 = i === 0 ? { x: sourceX, y: sourceY } : state.localBendingPoints[i - 1];
          const p2 = state.localBendingPoints[i];
          if (p1 && p2) {
            middlePoints.push({
              ...getMiddlePoint(p1, p2),
              direction: Math.abs(p1.x - p2.x) <= margin ? 'x' : 'y',
            });
          }
        }
        middlePoints.push({
          ...getMiddlePoint(state.localBendingPoints[state.localBendingPoints.length - 1]!, {
            x: targetX,
            y: targetY,
          }),
          direction: state.localBendingPoints[state.localBendingPoints.length - 1]!.x === targetX ? 'x' : 'y',
        });
      } else {
        middlePoints.push({
          ...getMiddlePoint({ x: sourceX, y: sourceY }, { x: targetX, y: targetY }),
          direction: sourceX === targetX ? 'x' : 'y',
        });
      }
      return middlePoints;
    };

    useEffect(() => {
      setState((prevState) => ({ ...prevState, middleBendingPoints: computeMiddlePoints() }));
    }, [state.localBendingPoints, sourceX, sourceY, targetX, targetY]);

    const edgeCenter: XYPosition = useMemo(() => {
      let pointsSource = state.localBendingPoints;
      if (isMultipleOfTwo(state.localBendingPoints.length)) {
        pointsSource = state.middleBendingPoints;
      }
      if (pointsSource.length === 0) {
        pointsSource = computeMiddlePoints();
      }
      return pointsSource[Math.floor(pointsSource.length / 2)] ?? { x: 0, y: 0 };
    }, [
      state.middleBendingPoints.map((point) => `${point.x}:${point.y}`).join(),
      state.localBendingPoints.map((point) => `${point.x}:${point.y}`).join(),
    ]);

    const edgePath: string = useMemo(() => {
      let edgePath = `M ${sourceX} ${sourceY}`;
      for (let i = 0; i < state.localBendingPoints.length; i++) {
        edgePath += ` L ${state.localBendingPoints[i]?.x} ${state.localBendingPoints[i]?.y}`;
      }
      edgePath += ` L ${targetX} ${targetY}`;
      return edgePath;
    }, [
      state.localBendingPoints.map((point) => point.x + point.y).join(),
      state.temporaryPointDragPosition,
      state.temporaryPointDragIndex,
      sourceX,
      sourceY,
      targetX,
      targetY,
    ]);

    return (
      <>
        <BaseEdge
          id={id}
          path={edgePath}
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
          state.localBendingPoints &&
          state.localBendingPoints.map((point, index) => (
            <BendPoint
              key={index}
              x={point.x}
              y={point.y}
              index={index}
              onDrag={onBendingPointDrag}
              onDragStop={onBendingPointDragStop}
            />
          ))}
        {selected &&
          state.middleBendingPoints &&
          state.middleBendingPoints.map((point, index) => (
            <TemporaryBendPoint
              key={index}
              x={point.x}
              y={point.y}
              direction={point.direction}
              index={index}
              onDrag={onTemporaryPointDrag}
              onDragStop={onTemporaryPointDragStop}
            />
          ))}
        <EdgeLabelRenderer>
          {beginLabel && (
            <div style={labelContainerStyle(`${sourceLabelTranslation} translate(${sourceX}px,${sourceY}px)`)}>
              <Label diagramElementId={id} label={beginLabel} faded={!!faded} />
            </div>
          )}
          {label && (
            <div style={labelContainerStyle(`translate(${edgeCenter.x}px,${edgeCenter.y}px)`)}>
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
