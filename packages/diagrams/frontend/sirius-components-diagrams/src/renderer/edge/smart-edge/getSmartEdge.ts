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
import { InternalNode, Node, Position, XYPosition } from '@xyflow/react';
import { DiagonalMovement, Grid, JumpPointFinder } from 'pathfinding';
import { NodeData } from '../../DiagramRenderer.types';

type PointInfo = {
  x: number;
  y: number;
  position: Position;
};

type NodeBoundingBox = {
  id: string;
  width: number;
  height: number;
  topLeft: XYPosition;
  bottomLeft: XYPosition;
  topRight: XYPosition;
  bottomRight: XYPosition;
};

type GraphBoundingBox = {
  width: number;
  height: number;
  topLeft: XYPosition;
  bottomLeft: XYPosition;
  topRight: XYPosition;
  bottomRight: XYPosition;
  xMax: number;
  yMax: number;
  xMin: number;
  yMin: number;
};

type SVGDrawFunction = (source: XYPosition, target: XYPosition, path: number[][]) => string;

type PathFindingFunction = (
  grid: Grid,
  start: XYPosition,
  end: XYPosition
) => {
  fullPath: number[][];
  smoothedPath: number[][];
} | null;

const roundDown = (x: number, multiple = 10) => Math.floor(x / multiple) * multiple;

const roundUp = (x: number, multiple = 10) => Math.ceil(x / multiple) * multiple;

const round = (x: number, multiple = 10) => Math.round(x / multiple) * multiple;

const svgDrawStraightLinePath: SVGDrawFunction = (source, target, path) => {
  let svgPathString = `M ${source.x}, ${source.y} `;

  path.forEach((point) => {
    const [x, y] = point;
    svgPathString += `L ${x}, ${y} `;
  });

  svgPathString += `L ${target.x}, ${target.y} `;

  return svgPathString;
};

const pathfindingJumpPointNoDiagonal: PathFindingFunction = (grid, start, end) => {
  const finder = new JumpPointFinder({
    diagonalMovement: DiagonalMovement.Never,
  });
  const fullPath = finder.findPath(start.x, start.y, end.x, end.y, grid);
  const smoothedPath = fullPath;
  if (fullPath.length === 0 || smoothedPath.length === 0) return null;
  return { fullPath, smoothedPath };
};

const graphToGridPoint = (
  graphPoint: XYPosition,
  smallestX: number,
  smallestY: number,
  gridRatio: number
): XYPosition => {
  let x = Math.floor(graphPoint.x / gridRatio);
  let y = Math.floor(graphPoint.y / gridRatio);

  let referenceX = Math.floor(smallestX / gridRatio);
  let referenceY = Math.floor(smallestY / gridRatio);

  x -= referenceX - 1;
  y -= referenceY - 1;

  return { x, y };
};

const getNextPointFromPosition = (point: XYPosition, position: Position): XYPosition => {
  switch (position) {
    case 'top':
      return { x: point.x, y: point.y - 1 };
    case 'bottom':
      return { x: point.x, y: point.y + 1 };
    case 'left':
      return { x: point.x - 1, y: point.y };
    case 'right':
      return { x: point.x + 1, y: point.y };
    default:
      return { x: 0, y: 0 };
  }
};

const guaranteeWalkablePath = (grid: Grid, point: XYPosition, position: Position) => {
  let node = grid.getNodeAt(point.x, point.y);
  while (!node.walkable) {
    grid.setWalkableAt(node.x, node.y, true);
    const next = getNextPointFromPosition(node, position);
    node = grid.getNodeAt(next.x, next.y);
  }
};

const createGrid = (
  graph: GraphBoundingBox,
  nodes: NodeBoundingBox[],
  source: PointInfo,
  target: PointInfo,
  gridRatio = 10
) => {
  const { xMin, yMin, width, height } = graph;

  const mapColumns = roundUp(width, gridRatio) / gridRatio + 1;
  const mapRows = roundUp(height, gridRatio) / gridRatio + 1;
  const grid = new Grid(mapColumns, mapRows);

  nodes.forEach((node) => {
    const nodeStart = graphToGridPoint(node.topLeft, xMin, yMin, gridRatio);
    const nodeEnd = graphToGridPoint(node.bottomRight, xMin, yMin, gridRatio);

    for (let x = nodeStart.x; x < nodeEnd.x; x++) {
      for (let y = nodeStart.y; y < nodeEnd.y; y++) {
        grid.setWalkableAt(x, y, false);
      }
    }
  });

  const startGrid = graphToGridPoint(
    {
      x: round(source.x, gridRatio),
      y: round(source.y, gridRatio),
    },
    xMin,
    yMin,
    gridRatio
  );

  const endGrid = graphToGridPoint(
    {
      x: round(target.x, gridRatio),
      y: round(target.y, gridRatio),
    },
    xMin,
    yMin,
    gridRatio
  );

  const startingNode = grid.getNodeAt(startGrid.x, startGrid.y);
  guaranteeWalkablePath(grid, startingNode, source.position);
  const endingNode = grid.getNodeAt(endGrid.x, endGrid.y);
  guaranteeWalkablePath(grid, endingNode, target.position);

  const start = getNextPointFromPosition(startingNode, source.position);
  const end = getNextPointFromPosition(endingNode, target.position);

  return { grid, start, end };
};

const getBoundingBoxes = (nodes: InternalNode<Node<NodeData>>[], nodePadding = 5, roundTo = 10) => {
  let xMax = Number.MIN_SAFE_INTEGER;
  let yMax = Number.MIN_SAFE_INTEGER;
  let xMin = Number.MAX_SAFE_INTEGER;
  let yMin = Number.MAX_SAFE_INTEGER;

  const nodeBoxes = nodes.map((node) => {
    const width = node.width ?? 1;
    const height = node.height ?? 1;

    const { x = 0, y = 0 } = node.internals.positionAbsolute || {};

    const topLeft = { x: x - nodePadding, y: y - nodePadding };
    const bottomLeft = { x: x - nodePadding, y: y + height + nodePadding };
    const topRight = { x: x + width + nodePadding, y: y - nodePadding };
    const bottomRight = { x: x + width + nodePadding, y: y + height + nodePadding };

    if (roundTo > 0) {
      topLeft.x = roundDown(topLeft.x, roundTo);
      topLeft.y = roundDown(topLeft.y, roundTo);
      bottomLeft.x = roundDown(bottomLeft.x, roundTo);
      bottomLeft.y = roundUp(bottomLeft.y, roundTo);
      topRight.x = roundUp(topRight.x, roundTo);
      topRight.y = roundDown(topRight.y, roundTo);
      bottomRight.x = roundUp(bottomRight.x, roundTo);
      bottomRight.y = roundUp(bottomRight.y, roundTo);
    }

    xMin = Math.min(xMin, topLeft.x);
    yMin = Math.min(yMin, topLeft.y);
    xMax = Math.max(xMax, bottomRight.x);
    yMax = Math.max(yMax, bottomRight.y);

    return {
      id: node.id,
      width,
      height,
      topLeft,
      bottomLeft,
      topRight,
      bottomRight,
    };
  });

  const graphPadding = nodePadding * 2;

  xMax = roundUp(xMax + graphPadding, roundTo);
  yMax = roundUp(yMax + graphPadding, roundTo);
  xMin = roundDown(xMin - graphPadding, roundTo);
  yMin = roundDown(yMin - graphPadding, roundTo);

  const topLeft = { x: xMin, y: yMin };
  const bottomLeft = { x: xMin, y: yMax };
  const topRight = { x: xMax, y: yMin };
  const bottomRight = { x: xMax, y: yMax };

  const width = Math.abs(topLeft.x - topRight.x);
  const height = Math.abs(topLeft.y - bottomLeft.y);

  const graphBox = {
    topLeft,
    bottomLeft,
    topRight,
    bottomRight,
    width,
    height,
    xMax,
    yMax,
    xMin,
    yMin,
  };

  return { nodeBoxes, graphBox };
};

export const gridToGraphPoint = (
  gridPoint: XYPosition,
  smallestX: number,
  smallestY: number,
  gridRatio: number
): XYPosition => {
  let x = (gridPoint.x - 1) * gridRatio;
  let y = (gridPoint.y - 1) * gridRatio;

  let referenceX = roundUp(smallestX, gridRatio);
  let referenceY = roundUp(smallestY, gridRatio);

  x += referenceX;
  y += referenceY;
  return { x, y };
};

export const getSmartEdge = ({ nodes, sourceX, sourceY, targetX, targetY, sourcePosition, targetPosition }) => {
  const { graphBox, nodeBoxes } = getBoundingBoxes(nodes);
  const gridRatio: number = 10;

  const source: PointInfo = {
    x: sourceX,
    y: sourceY,
    position: sourcePosition,
  };

  const target: PointInfo = {
    x: targetX,
    y: targetY,
    position: targetPosition,
  };

  const { grid, start, end } = createGrid(graphBox, nodeBoxes, source, target);

  const generatePathResult = pathfindingJumpPointNoDiagonal(grid, start, end);

  if (generatePathResult === null) {
    return null;
  }

  const { fullPath, smoothedPath } = generatePathResult;

  const graphPath = smoothedPath.map((gridPoint) => {
    const [x, y] = gridPoint;
    const graphPoint = gridToGraphPoint({ x: x ?? 0, y: y ?? 0 }, graphBox.xMin, graphBox.yMin, gridRatio);
    return [graphPoint.x, graphPoint.y];
  });

  const svgPathString = svgDrawStraightLinePath(source, target, graphPath);

  const index = Math.floor(fullPath.length / 2);
  const middlePoint = fullPath[index];
  const [middleX, middleY] = middlePoint ?? [0, 0];
  const { x: edgeCenterX, y: edgeCenterY } = gridToGraphPoint(
    { x: middleX ?? 0, y: middleY ?? 0 },
    graphBox.xMin,
    graphBox.yMin,
    gridRatio
  );

  return { svgPathString, edgeCenterX, edgeCenterY };
};
