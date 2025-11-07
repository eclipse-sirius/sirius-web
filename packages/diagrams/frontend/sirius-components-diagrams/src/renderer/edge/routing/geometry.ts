/**
 * Geometry helpers used by the routing pipeline. The functions below take raw XYFlow nodes and
 * convert them into absolute rectangles or overlap reports so higher-level code can focus on
 * polylines instead of graph internals.
 *
 * Nothing in this module mutates the incoming nodes – every coordinate that leaves the file is a
 * fresh object, which makes the helpers safe to memoize or reuse across renders.
 */
import { Node, XYPosition } from '@xyflow/react';
import { NodeData } from '../../DiagramRenderer.types';
import { DiagramNodeType } from '../../node/NodeTypes.types';

export type Rect = {
  x: number;
  y: number;
  width: number;
  height: number;
};

export type Rectangle = { left: number; right: number; top: number; bottom: number };

export type PathCollision = {
  node: Node<NodeData>;
  rect: Rectangle;
  segmentStart: XYPosition;
  segmentEnd: XYPosition;
  segmentIndex: number;
};

export type PathOverlapResult = { overlaps: boolean; collision?: PathCollision };

const LABEL_NODE_TYPES = new Set<DiagramNodeType>(['iconLabelNode', 'edgeAnchorNodeCreationHandles']);

const parseCssDimension = (value: unknown): number | null => {
  if (typeof value === 'number' && Number.isFinite(value)) {
    return value;
  }
  if (typeof value === 'string') {
    const parsed = parseFloat(value);
    if (!Number.isNaN(parsed)) {
      return parsed;
    }
  }
  return null;
};

const getNodeSize = (node: Node<NodeData>): { width: number; height: number } => {
  const width =
    parseCssDimension(node.width) ??
    parseCssDimension(node.measured?.width) ??
    parseCssDimension((node.style as Record<string, unknown>)?.width) ??
    parseCssDimension(node.data?.defaultWidth) ??
    0;
  const height =
    parseCssDimension(node.height) ??
    parseCssDimension(node.measured?.height) ??
    parseCssDimension((node.style as Record<string, unknown>)?.height) ??
    parseCssDimension(node.data?.defaultHeight) ??
    0;

  return {
    width: Math.max(0, width ?? 0),
    height: Math.max(0, height ?? 0),
  };
};

const addPositions = (a: XYPosition, b: XYPosition): XYPosition => ({
  x: a.x + b.x,
  y: a.y + b.y,
});

const getParentId = (node: Node<NodeData> | undefined): string | undefined => {
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

/**
 * Collect every ancestor id for the provided node. Used to keep routing from treating a node's own
 * containers as obstacles when building detours.
 */
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

/**
 * Test whether an axis-aligned polyline overlaps any node rectangle in the provided set and return
 * the first collision (when present).
 */
export const doesPathOverlapNodes = (
  pathPoints: XYPosition[],
  nodes: Node<NodeData>[],
  nodeMap: Map<string, Node<NodeData>>,
  ignoredNodeIds: Set<string>
): PathOverlapResult => {
  const absolutePositionCache = new Map<string, XYPosition>();
  const collidableNodes = nodes.reduce<Array<{ node: Node<NodeData>; rect: Rectangle }>>((accumulator, node) => {
    if (ignoredNodeIds.has(node.id)) {
      return accumulator;
    }
    const rect = getNodeRectangle(node, nodeMap, absolutePositionCache);
    if (rect) {
      accumulator.push({ node, rect });
    }
    return accumulator;
  }, []);

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

type RectComputationOptions = {
  filterHidden?: boolean;
  excludedNodeIds?: Set<string>;
};

/**
 * Convert XYFlow nodes into axis-aligned rectangles expressed in absolute coordinates. Hidden
 * nodes, label-only nodes, and explicitly excluded ids are skipped.
 */
export const computeAbsoluteNodeRects = (
  nodes: Node<NodeData, DiagramNodeType>[],
  options: RectComputationOptions = {}
): Map<string, Rect> => {
  const filterHidden = options.filterHidden ?? true;
  const excludedNodeIds = options.excludedNodeIds ?? new Set<string>();

  const nodeById = new Map<string, Node<NodeData, DiagramNodeType>>();
  nodes.forEach((node) => nodeById.set(node.id, node));

  const cache = new Map<string, XYPosition>();
  const rects = new Map<string, Rect>();

  nodes.forEach((node) => {
    const nodeType = node.type ?? '';
    if ((filterHidden && node.hidden) || LABEL_NODE_TYPES.has(nodeType) || excludedNodeIds.has(node.id)) {
      return;
    }

    const absolutePosition = computeAbsolutePosition(node, nodeById, cache);
    const { width, height } = getNodeSize(node);
    if (width <= 0 || height <= 0) {
      return;
    }
    rects.set(node.id, { x: absolutePosition.x, y: absolutePosition.y, width, height });
  });

  return rects;
};

const computeAbsolutePosition = (
  node: Node<NodeData, DiagramNodeType>,
  nodeById: Map<string, Node<NodeData, DiagramNodeType>>,
  cache: Map<string, XYPosition>
): XYPosition => {
  const cached = cache.get(node.id);
  if (cached) {
    return cached;
  }

  const nodeWithInternals = node as Node<NodeData> & {
    positionAbsolute?: XYPosition;
    internals?: { positionAbsolute?: XYPosition };
  };

  const directAbsolute = nodeWithInternals.internals?.positionAbsolute ?? nodeWithInternals.positionAbsolute;
  if (directAbsolute && Number.isFinite(directAbsolute.x) && Number.isFinite(directAbsolute.y)) {
    const absolute = { x: directAbsolute.x, y: directAbsolute.y };
    cache.set(node.id, absolute);
    return absolute;
  }

  const localPosition = node.position ?? { x: 0, y: 0 };
  const base: XYPosition = {
    x: localPosition?.x ?? 0,
    y: localPosition?.y ?? 0,
  };

  if (!node.parentId) {
    cache.set(node.id, base);
    return base;
  }

  const parentId = node.parentId;
  if (!parentId) {
    cache.set(node.id, base);
    return base;
  }

  const parent = nodeById.get(parentId);
  if (!parent) {
    cache.set(node.id, base);
    return base;
  }

  const parentAbs = computeAbsolutePosition(parent, nodeById, cache);
  const absolute = addPositions(base, parentAbs);
  cache.set(node.id, absolute);
  return absolute;
};
