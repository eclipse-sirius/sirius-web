import { Node, XYPosition } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';

/**
 * Geometry helpers shared by the edge layout pipeline. The utilities below
 * translate React Flow nodes into absolute rectangles so collision detection
 * can operate directly in canvas coordinates.
 */
export type Rect = {
  x: number;
  y: number;
  width: number;
  height: number;
};

const LABEL_NODE_TYPES = new Set<DiagramNodeType>(['iconLabelNode']);

const parseCssDimension = (value: unknown): number | null => {
  /**
   * Convert a CSS dimension coming from React Flow into a plain number. Keeps the
   * logic tolerant to strings, numeric values, and undefined/NaN inputs.
   */
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
  /**
   * Resolve the rendered width/height for a node by checking the measured size,
   * explicit style, or default graph metadata. Missing values fallback to zero.
   */
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

const computeAbsolutePosition = (
  node: Node<NodeData, DiagramNodeType>,
  nodeById: Map<string, Node<NodeData, DiagramNodeType>>,
  cache: Map<string, XYPosition>
): XYPosition => {
  /**
   * Walk up the node hierarchy to compute the absolute canvas position of the node.
   * Results are memoized so repeated lookups within the same pass stay cheap.
   */
  const cached = cache.get(node.id);
  if (cached) {
    return cached;
  }

  const nodeWithInternals = node as Node<NodeData> & {
    positionAbsolute?: XYPosition;
    internals?: { positionAbsolute?: XYPosition };
  };

  const directAbsolute =
    nodeWithInternals.internals?.positionAbsolute ?? nodeWithInternals.positionAbsolute;
  if (directAbsolute && Number.isFinite(directAbsolute.x) && Number.isFinite(directAbsolute.y)) {
    // ReactFlow/XYFlow already tracks absolute coordinates when layouts are computed.
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

type RectComputationOptions = {
  filterHidden?: boolean;
  excludedNodeIds?: Set<string>;
};

export const computeAbsoluteNodeRects = (
  nodes: Node<NodeData, DiagramNodeType>[],
  options: RectComputationOptions = {}
): Map<string, Rect> => {
  /**
   * Produce absolute rectangles for the provided React Flow nodes. Hidden nodes,
   * excluded IDs, and label-only nodes are skipped so downstream layout code can
   * focus on real obstacles.
   */
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
