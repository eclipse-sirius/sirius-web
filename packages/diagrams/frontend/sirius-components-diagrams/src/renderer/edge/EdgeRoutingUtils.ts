import { Node, XYPosition } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';

export type Rectangle = { left: number; right: number; top: number; bottom: number };

export type PathCollision = {
  node: Node<NodeData>;
  rect: Rectangle;
  segmentStart: XYPosition;
  segmentEnd: XYPosition;
  segmentIndex: number;
};

export type PathOverlapResult = { overlaps: boolean; collision?: PathCollision };

export const getParentId = (node: Node<NodeData> | undefined): string | undefined => {
  if (!node) {
    return undefined;
  }

  const parentNode = (node as Partial<Node<NodeData>> & { parentNode?: string }).parentNode;
  if (typeof parentNode === 'string') {
    return parentNode;
  }

  const parentId = (node as Partial<{ parentId?: string }>).parentId;
  if (typeof parentId === 'string') {
    return parentId;
  }

  return undefined;
};

export const collectAncestorIds = (nodeId: string, nodeMap: Map<string, Node<NodeData>>): Set<string> => {
  const ancestors = new Set<string>();
  let currentId: string | undefined = nodeId;
  while (currentId) {
    const node = nodeMap.get(currentId);
    const parentId = getParentId(node);
    if (!parentId || ancestors.has(parentId)) {
      break;
    }
    ancestors.add(parentId);
    currentId = parentId;
  }
  return ancestors;
};

export const getNodeAbsolutePosition = (
  node: Node<NodeData>,
  nodeMap: Map<string, Node<NodeData>>,
  cache: Map<string, XYPosition>
): XYPosition => {
  const cachedPosition = cache.get(node.id);
  if (cachedPosition) {
    return cachedPosition;
  }

  const positionAbsolute = (node as Partial<{ positionAbsolute?: XYPosition }>).positionAbsolute;
  if (positionAbsolute) {
    cache.set(node.id, positionAbsolute);
    return positionAbsolute;
  }

  const parentId = getParentId(node);
  if (!parentId) {
    const absolutePosition = { x: node.position.x, y: node.position.y };
    cache.set(node.id, absolutePosition);
    return absolutePosition;
  }

  const parentNode = nodeMap.get(parentId);
  if (!parentNode) {
    const absolutePosition = { x: node.position.x, y: node.position.y };
    cache.set(node.id, absolutePosition);
    return absolutePosition;
  }

  const parentAbsolute = getNodeAbsolutePosition(parentNode, nodeMap, cache);
  const absolutePosition = {
    x: parentAbsolute.x + node.position.x,
    y: parentAbsolute.y + node.position.y,
  };
  cache.set(node.id, absolutePosition);
  return absolutePosition;
};

export const getNodeRectangle = (
  node: Node<NodeData>,
  nodeMap: Map<string, Node<NodeData>>,
  cache: Map<string, XYPosition>
): Rectangle | null => {
  const width = node.width ?? 0;
  const height = node.height ?? 0;

  if (width <= 0 || height <= 0) {
    return null;
  }

  const absolutePosition = getNodeAbsolutePosition(node, nodeMap, cache);

  return {
    left: absolutePosition.x,
    right: absolutePosition.x + width,
    top: absolutePosition.y,
    bottom: absolutePosition.y + height,
  };
};

const isPointInsideRect = (point: XYPosition, rect: Rectangle): boolean =>
  point.x > rect.left && point.x < rect.right && point.y > rect.top && point.y < rect.bottom;

export const doesSegmentOverlapRect = (start: XYPosition, end: XYPosition, rect: Rectangle): boolean => {
  if (start.x === end.x && start.y === end.y) {
    return isPointInsideRect(start, rect);
  }

  if (start.y === end.y) {
    const segmentY = start.y;
    if (segmentY <= rect.top || segmentY >= rect.bottom) {
      return false;
    }
    const segMinX = Math.min(start.x, end.x);
    const segMaxX = Math.max(start.x, end.x);
    return segMaxX > rect.left && segMinX < rect.right;
  }

  if (start.x === end.x) {
    const segmentX = start.x;
    if (segmentX <= rect.left || segmentX >= rect.right) {
      return false;
    }
    const segMinY = Math.min(start.y, end.y);
    const segMaxY = Math.max(start.y, end.y);
    return segMaxY > rect.top && segMinY < rect.bottom;
  }

  const segLeft = Math.min(start.x, end.x);
  const segRight = Math.max(start.x, end.x);
  const segTop = Math.min(start.y, end.y);
  const segBottom = Math.max(start.y, end.y);
  return segRight > rect.left && segLeft < rect.right && segBottom > rect.top && segTop < rect.bottom;
};

export const doesPathOverlapNodes = (
  pathPoints: XYPosition[],
  nodes: Node<NodeData>[],
  nodeMap: Map<string, Node<NodeData>>,
  ignoredNodeIds: Set<string>
): PathOverlapResult => {
  const absolutePositionCache = new Map<string, XYPosition>();
  const collidableNodes = nodes
    .filter((node) => {
      if (ignoredNodeIds.has(node.id)) {
        return false;
      }
      const rect = getNodeRectangle(node, nodeMap, absolutePositionCache);
      return rect !== null;
    })
    .map((node) => ({ node, rect: getNodeRectangle(node, nodeMap, absolutePositionCache)! }));

  for (let index = 0; index < pathPoints.length - 1; index++) {
    const segmentStart = pathPoints[index];
    const segmentEnd = pathPoints[index + 1];

    if (!segmentStart || !segmentEnd) {
      continue;
    }

    if (segmentStart.x === segmentEnd.x && segmentStart.y === segmentEnd.y) {
      continue;
    }

    for (const { node, rect } of collidableNodes) {
      if (doesSegmentOverlapRect(segmentStart, segmentEnd, rect)) {
        return {
          overlaps: true,
          collision: { node, rect, segmentStart, segmentEnd, segmentIndex: index },
        };
      }
    }
  }
  return { overlaps: false };
};
