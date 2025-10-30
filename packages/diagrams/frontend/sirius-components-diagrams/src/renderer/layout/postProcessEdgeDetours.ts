import { Edge, Node, Position, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';
import { collectPolylineRectCollisions, expandRect } from './collision';
import type { PolylineRectCollision } from './collision';
import { buildDetourAroundRectangle, DetourContext, DetourOptions } from './detour';
import { computeAbsoluteNodeRects, Rect } from './geometry';

type EdgePolyline = XYPosition[];

type EdgeWithPositions = Edge<EdgeData> &
  Partial<{
    sourcePosition: Position;
    targetPosition: Position;
  }>;

const DETOUR_BASE_GAP = 8;
const DETOUR_STACK_SPACING = 6;
const MAX_PASSES = 3;

const getHandlePoint = (rect: Rect | undefined, position?: Position): XYPosition | undefined => {
  if (!rect) {
    return undefined;
  }
  const centerX = rect.x + rect.width / 2;
  const centerY = rect.y + rect.height / 2;
  switch (position) {
    case Position.Left:
      return { x: rect.x, y: centerY };
    case Position.Right:
      return { x: rect.x + rect.width, y: centerY };
    case Position.Top:
      return { x: centerX, y: rect.y };
    case Position.Bottom:
      return { x: centerX, y: rect.y + rect.height };
    default:
      return { x: centerX, y: centerY };
  }
};

const buildInitialPolyline = (
  edge: Edge<EdgeData>,
  rects: Map<string, Rect>
): EdgePolyline | null => {
  const { sourcePosition, targetPosition } = edge as EdgeWithPositions;
  const sourcePoint = getHandlePoint(rects.get(edge.source), sourcePosition);
  const targetPoint = getHandlePoint(rects.get(edge.target), targetPosition);
  if (!sourcePoint || !targetPoint) {
    return null;
  }

  const bendingPoints = edge.data?.bendingPoints ?? [];

  return [sourcePoint, ...bendingPoints.map((point) => ({ x: point.x, y: point.y })), targetPoint];
};

type CollisionCandidate = {
  edge: Edge<EdgeData>;
  polyline: EdgePolyline;
  entryPoint: XYPosition;
  exitPoint: XYPosition;
  entryIndex: number;
  exitIndex: number;
  entryOrder: number;
  obstacle: Rect;
};

type CollisionSide = 'top' | 'right' | 'bottom' | 'left' | null;

const computeEntryOrder = (entryPoint: XYPosition, obstacle: Rect): number => {
  if (entryPoint.y <= obstacle.y + 0.5 || entryPoint.y === obstacle.y) {
    return entryPoint.x;
  }
  if (entryPoint.y >= obstacle.y + obstacle.height - 0.5) {
    return entryPoint.x;
  }
  if (entryPoint.x <= obstacle.x + 0.5 || entryPoint.x === obstacle.x) {
    return entryPoint.y;
  }
  return entryPoint.y;
};

const determineCollisionSide = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  tolerance = 0.6
): CollisionSide => {
  if (Math.abs(point.y - bounds.top) <= tolerance) {
    return 'top';
  }
  if (Math.abs(point.y - bounds.bottom) <= tolerance) {
    return 'bottom';
  }
  if (Math.abs(point.x - bounds.left) <= tolerance) {
    return 'left';
  }
  if (Math.abs(point.x - bounds.right) <= tolerance) {
    return 'right';
  }
  return null;
};

const preferredTargetEntrySide = (position?: Position): CollisionSide => {
  switch (position) {
    case Position.Top:
      return 'top';
    case Position.Bottom:
      return 'bottom';
    case Position.Left:
      return 'left';
    case Position.Right:
      return 'right';
    default:
      return null;
  }
};

const preferredSourceExitSide = (position?: Position): CollisionSide => {
  switch (position) {
    case Position.Top:
      return 'top';
    case Position.Bottom:
      return 'bottom';
    case Position.Left:
      return 'left';
    case Position.Right:
      return 'right';
    default:
      return null;
  }
};

const projectPointToSide = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number },
  side: CollisionSide
): XYPosition => {
  const clampedX = Math.min(Math.max(point.x, bounds.left), bounds.right);
  const clampedY = Math.min(Math.max(point.y, bounds.top), bounds.bottom);
  switch (side) {
    case 'top':
      return { x: clampedX, y: bounds.top };
    case 'bottom':
      return { x: clampedX, y: bounds.bottom };
    case 'left':
      return { x: bounds.left, y: clampedY };
    case 'right':
      return { x: bounds.right, y: clampedY };
    default:
      return { x: clampedX, y: clampedY };
  }
};

const closestCollisionSide = (
  point: XYPosition,
  bounds: { left: number; right: number; top: number; bottom: number }
): CollisionSide => {
  const distances: Array<{ side: CollisionSide; value: number }> = [
    { side: 'top', value: Math.abs(point.y - bounds.top) },
    { side: 'bottom', value: Math.abs(point.y - bounds.bottom) },
    { side: 'left', value: Math.abs(point.x - bounds.left) },
    { side: 'right', value: Math.abs(point.x - bounds.right) },
  ];
  distances.sort((a, b) => a.value - b.value);
  return distances[0]?.side ?? null;
};

const gatherCollisions = (
  edges: Edge<EdgeData>[],
  rects: Map<string, Rect>,
  polylines: Map<string, EdgePolyline>,
  excludedObstacleIds?: Set<string>
): Map<string, CollisionCandidate[]> => {
  const collisions = new Map<string, CollisionCandidate[]>();

  rects.forEach((rect, nodeId) => {
    if (excludedObstacleIds?.has(nodeId)) {
      return;
    }

    edges.forEach((edge) => {
      const isSourceNode = edge.source === nodeId;
      const isTargetNode = edge.target === nodeId;
      if (edge.hidden || (isSourceNode && isTargetNode)) {
        return;
      }

      const edgeWithPositions = edge as EdgeWithPositions;

      const polyline = polylines.get(edge.id);
      if (!polyline || polyline.length < 2) {
        return;
      }

      const paddedRect = expandRect(rect, DETOUR_BASE_GAP);
      const paddedBounds = {
        left: paddedRect.x,
        right: paddedRect.x + paddedRect.width,
        top: paddedRect.y,
        bottom: paddedRect.y + paddedRect.height,
      };
      const collisionSpans = collectPolylineRectCollisions(polyline, paddedRect);
      if (collisionSpans.length === 0) {
        return;
      }

      const preferredTargetSide = isTargetNode
        ? preferredTargetEntrySide(edgeWithPositions.targetPosition)
        : null;
      const preferredSourceSide = isSourceNode
        ? preferredSourceExitSide(edgeWithPositions.sourcePosition)
        : null;

      const adjustedSpans: PolylineRectCollision[] = [];

      collisionSpans.forEach((span) => {
        let entrySide = determineCollisionSide(span.entryPoint, paddedBounds);
        let exitSide = determineCollisionSide(span.exitPoint, paddedBounds);

        if (!entrySide) {
          entrySide =
            (isSourceNode && preferredSourceSide) ||
            (isTargetNode && preferredTargetSide) ||
            closestCollisionSide(span.entryPoint, paddedBounds);
        }

        if (!exitSide) {
          exitSide =
            (isTargetNode && preferredTargetSide) ||
            (isSourceNode && preferredSourceSide) ||
            closestCollisionSide(span.exitPoint, paddedBounds);
        }

        if (isTargetNode && preferredTargetSide && entrySide === preferredTargetSide) {
          return;
        }
        if (isSourceNode && preferredSourceSide && exitSide === preferredSourceSide) {
          return;
        }

        if (isTargetNode && preferredTargetSide) {
          exitSide = preferredTargetSide;
        }

        const entryPoint = projectPointToSide(span.entryPoint, paddedBounds, entrySide);
        const exitPoint = projectPointToSide(span.exitPoint, paddedBounds, exitSide);

        adjustedSpans.push({
          entryPoint,
          exitPoint,
          entryIndex: span.entryIndex,
          exitIndex: span.exitIndex,
        });
      });

      if (adjustedSpans.length === 0) {
        return;
      }

      adjustedSpans.forEach((span) => {
        const key = `${nodeId}::${Math.round(span.entryPoint.x)}::${Math.round(span.entryPoint.y)}`;
        const candidates = collisions.get(key) ?? [];
        candidates.push({
          edge,
          polyline,
          entryPoint: span.entryPoint,
          exitPoint: span.exitPoint,
          entryIndex: span.entryIndex,
          exitIndex: span.exitIndex,
          entryOrder: computeEntryOrder(span.entryPoint, rect),
          obstacle: rect,
        });
        collisions.set(key, candidates);
      });
    });
  });

  return collisions;
};

const applyDetours = (
  collisions: Map<string, CollisionCandidate[]>,
  polylines: Map<string, EdgePolyline>
): boolean => {
  let mutated = false;
  const mutatedEdgesThisPass = new Set<string>();

  collisions.forEach((candidates) => {
    if (candidates.length === 0) {
      return;
    }

    const sorted = candidates
      .slice()
      .sort((a, b) => a.entryOrder - b.entryOrder || a.edge.id.localeCompare(b.edge.id));

    let order = 0;
    for (const candidate of sorted) {
      if (mutatedEdgesThisPass.has(candidate.edge.id)) {
        continue;
      }

      const context: DetourContext = {
        entryPoint: candidate.entryPoint,
        exitPoint: candidate.exitPoint,
        entryIndex: candidate.entryIndex,
        exitIndex: candidate.exitIndex,
      };

      const polyline = polylines.get(candidate.edge.id);
      if (!polyline) {
        order++;
        continue;
      }

      const options: DetourOptions = {
        baseGap: DETOUR_BASE_GAP,
        extraGap: order * DETOUR_STACK_SPACING,
      };
      order++;

      const detoured = buildDetourAroundRectangle(polyline, candidate.obstacle, options, context);
      if (detoured) {
        polylines.set(candidate.edge.id, detoured);
        mutated = true;
        mutatedEdgesThisPass.add(candidate.edge.id);
      }
    }
  });

  return mutated;
};

const collectAncestorIds = (
  nodeId: string | null | undefined,
  lookup: Map<string, Node<NodeData, DiagramNodeType>>
): Set<string> => {
  const ancestors = new Set<string>();
  let currentId: string | null | undefined = nodeId;
  let guard = lookup.size + 1;

  while (currentId && guard > 0) {
    const node = lookup.get(currentId);
    if (!node || !node.parentId) {
      break;
    }
    const parentId = node.parentId;
    if (ancestors.has(parentId)) {
      break;
    }
    ancestors.add(parentId);
    currentId = parentId;
    guard--;
  }

  return ancestors;
};

const computeExcludedObstacleIds = (
  currentEdge: Edge<EdgeData>,
  lookup: Map<string, Node<NodeData, DiagramNodeType>>
): Set<string> => {
  const excluded = new Set<string>();
  const sourceAncestors = collectAncestorIds(currentEdge.source, lookup);
  const targetAncestors = collectAncestorIds(currentEdge.target, lookup);

  sourceAncestors.forEach((candidate) => excluded.add(candidate));
  targetAncestors.forEach((candidate) => excluded.add(candidate));

  return excluded;
};

export const buildDetouredPolyline = (
  currentEdge: Edge<EdgeData>,
  currentPolyline: EdgePolyline,
  edges: Edge<EdgeData>[],
  nodes: Node<NodeData, DiagramNodeType>[]
): EdgePolyline => {
  if (currentPolyline.length < 2 || edges.length === 0 || nodes.length === 0) {
    return currentPolyline;
  }

  const nodeLookup = new Map(nodes.map((node) => [node.id, node]));
  const excludedObstacleIds = computeExcludedObstacleIds(currentEdge, nodeLookup);
  const rects = computeAbsoluteNodeRects(nodes, { excludedNodeIds: excludedObstacleIds });
  if (rects.size === 0) {
    return currentPolyline;
  }

  const polylines = new Map<string, EdgePolyline>();
  edges.forEach((edge) => {
    const baseline =
      edge.id === currentEdge.id ? currentPolyline : buildInitialPolyline(edge, rects);
    if (baseline) {
      polylines.set(edge.id, baseline);
    }
  });

  if (!polylines.has(currentEdge.id)) {
    polylines.set(currentEdge.id, currentPolyline);
  }

  const edgesWithPolylines = edges.filter((edge) => polylines.has(edge.id));

  for (let pass = 0; pass < MAX_PASSES; pass++) {
    const collisions = gatherCollisions(edgesWithPolylines, rects, polylines, excludedObstacleIds);
    const changed = applyDetours(collisions, polylines);
    if (!changed) {
      break;
    }
  }

  return polylines.get(currentEdge.id) ?? currentPolyline;
};
