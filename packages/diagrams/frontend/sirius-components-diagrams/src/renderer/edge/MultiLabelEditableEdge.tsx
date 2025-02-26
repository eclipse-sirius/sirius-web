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
import {
  MultiLabelEditableEdgeProps,
  MultiLabelEditableEdgeState,
  MiddlePoint,
  BendPointData,
} from './MultiLabelEditableEdge.types';
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
): BendPointData[] => {
  const newPoints = [...existingBendPoint.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index }))];
  newPoints.forEach((point) => {
    if (point.pathOrder > bendPointIndex) {
      point.pathOrder += 4;
    }
  });
  const axis: 'x' | 'y' = determineSegmentAxis(prevMiddlePoint, newPoints[bendPointIndex]!);
  newPoints.push({
    ...prevMiddlePoint,
    pathOrder: bendPointIndex,
  });
  newPoints.push({
    x: axis === 'x' ? prevMiddlePoint.x : newX,
    y: axis === 'x' ? newY : prevMiddlePoint.y,
    pathOrder: bendPointIndex + 1,
  });
  newPoints[bendPointIndex] = {
    x: newX,
    y: newY,
    pathOrder: bendPointIndex + 2,
  };
  newPoints.push({
    x: axis === 'x' ? newX : nextMiddlePoint.x,
    y: axis === 'x' ? nextMiddlePoint.y : newY,
    pathOrder: bendPointIndex + 3,
  });
  newPoints.push({ ...nextMiddlePoint, pathOrder: bendPointIndex + 4 });

  return newPoints;
};

const cleanBendPoint = (bendPoints: XYPosition[]): XYPosition[] => {
  const cleanedPoints: XYPosition[] = [];

  const margin = 2.5;
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

const determineSegmentAxis = (p1: XYPosition, p2: XYPosition): 'x' | 'y' => {
  const deltaX = Math.abs(p2.x - p1.x);
  const deltaY = Math.abs(p2.y - p1.y);

  if (deltaX > deltaY) {
    return 'x';
  }
  return 'y';
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
      localBendingPoints: bendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })),
      middleBendingPoints: [],
    };
    const [state, setState] = useState<MultiLabelEditableEdgeState>(initialState);

    useEffect(() => {
      setState((prevState) => ({
        ...prevState,
        localBendingPoints: bendingPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })),
      }));
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
        edge.data.bendingPoints = cleanBendPoint(newPoints.sort((a, b) => a.pathOrder - b.pathOrder));
      }
      setState((prevState) => ({
        ...prevState,
        localBendingPoints: newPoints,
      }));
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

    const onTemporaryPointDrag = (eventData: DraggableData, temporaryPointIndex: number, direction: 'x' | 'y') => {
      const newPoints = [...state.localBendingPoints];
      if (direction === 'x' && newPoints[temporaryPointIndex - 1] && newPoints[temporaryPointIndex]) {
        newPoints[temporaryPointIndex - 1]!.x = eventData.x;
        newPoints[temporaryPointIndex]!.x = eventData.x;
      } else if (direction === 'y' && newPoints[temporaryPointIndex - 1] && newPoints[temporaryPointIndex]) {
        newPoints[temporaryPointIndex - 1]!.y = eventData.y;
        newPoints[temporaryPointIndex]!.y = eventData.y;
      }
      setState((prevState) => ({
        ...prevState,
        localBendingPoints: newPoints.map((bendingPoint, index) => ({ ...bendingPoint, pathOrder: index })),
      }));
    };

    const computeMiddlePoints = () => {
      const middlePoints: MiddlePoint[] = [];
      const margin = 2.5;
      const reorderBendPoint = [...state.localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
      if (reorderBendPoint.length > 0) {
        for (let i = 0; i < reorderBendPoint.length; i++) {
          const p1 = i === 0 ? { x: sourceX, y: sourceY } : reorderBendPoint[i - 1];
          const p2 = reorderBendPoint[i];
          if (p1 && p2) {
            const direction = Math.abs(p1.x - p2.x) <= margin ? 'x' : 'y';
            middlePoints.push({
              ...getMiddlePoint(p1, p2),
              direction: direction,
              segmentLength: direction !== 'x' ? Math.abs(p1.x - p2.x) : Math.abs(p1.y - p2.y),
            });
          }
        }
        const direction = reorderBendPoint[reorderBendPoint.length - 1]!.x === targetX ? 'x' : 'y';
        middlePoints.push({
          ...getMiddlePoint(reorderBendPoint[reorderBendPoint.length - 1]!, {
            x: targetX,
            y: targetY,
          }),
          direction: direction,
          segmentLength:
            direction !== 'x'
              ? Math.abs(reorderBendPoint[reorderBendPoint.length - 1]!.x - targetX)
              : Math.abs(reorderBendPoint[reorderBendPoint.length - 1]!.y - targetY),
        });
      } else {
        const direction = sourceX === targetX ? 'x' : 'y';
        middlePoints.push({
          ...getMiddlePoint({ x: sourceX, y: sourceY }, { x: targetX, y: targetY }),
          direction: direction,
          segmentLength: direction !== 'x' ? Math.abs(sourceX - targetX) : Math.abs(sourceY - targetY),
        });
      }
      return middlePoints;
    };

    useEffect(() => {
      setState((prevState) => ({ ...prevState, middleBendingPoints: computeMiddlePoints() }));
    }, [state.localBendingPoints, sourceX, sourceY, targetX, targetY]);

    useEffect(() => {
      const newBendPoint = [...state.localBendingPoints];
      if (newBendPoint[0]) {
        if (determineSegmentAxis({ x: sourceX, y: sourceY }, newBendPoint[0]) === 'x') {
          newBendPoint[0].y = sourceY;
        } else {
          newBendPoint[0].x = sourceX;
        }
      }
      setState((prevState) => ({ ...prevState, localBendingPoints: newBendPoint }));
    }, [sourceX, sourceY]);

    useEffect(() => {
      const newBendPoint = [...state.localBendingPoints];
      const lastPoint = newBendPoint[newBendPoint.length - 1];
      if (lastPoint) {
        if (determineSegmentAxis({ x: targetX, y: targetY }, lastPoint) === 'x') {
          lastPoint.y = targetY;
        } else {
          lastPoint.x = targetX;
        }
      }
      setState((prevState) => ({ ...prevState, localBendingPoints: newBendPoint }));
    }, [targetX, targetY]);

    const edgeCenter: XYPosition = useMemo(() => {
      let pointsSource = state.localBendingPoints.map((bendingPoint) => ({ x: bendingPoint.x, y: bendingPoint.y }));
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
      const reorderBendPoint = [...state.localBendingPoints].sort((a, b) => a.pathOrder - b.pathOrder);
      for (let i = 0; i < reorderBendPoint.length; i++) {
        edgePath += ` L ${reorderBendPoint[i]?.x} ${reorderBendPoint[i]?.y}`;
      }
      edgePath += ` L ${targetX} ${targetY}`;
      return edgePath;
    }, [state.localBendingPoints.map((point) => point.x + point.y).join(), sourceX, sourceY, targetX, targetY]);

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
              segmentLength={point.segmentLength}
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
