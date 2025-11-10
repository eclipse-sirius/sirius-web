import type { Edge, Node, XYPosition } from '@xyflow/react';
import { Position } from '@xyflow/react';
import type { EdgeData, NodeData } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/DiagramRenderer.types';
import type { ConnectionHandle } from '../../../../../diagrams/frontend/sirius-components-diagrams/src/renderer/handles/ConnectionHandles.types';
import type { DiagramFixture, FixtureEdge, FixtureHandlePosition, FixtureNode } from '../types';

const toPositionEnum = (position?: FixtureHandlePosition): Position => {
  switch (position) {
    case 'left':
      return Position.Left;
    case 'right':
      return Position.Right;
    case 'top':
      return Position.Top;
    case 'bottom':
      return Position.Bottom;
    default:
      return Position.Right;
  }
};

const buildFallbackHandles = (nodeId: string): ConnectionHandle[] => {
  const baseHandles: Array<{ id: string; position: Position }> = [
    { id: 'top', position: Position.Top },
    { id: 'right', position: Position.Right },
    { id: 'bottom', position: Position.Bottom },
    { id: 'left', position: Position.Left },
  ];

  return baseHandles.flatMap(({ id, position }, index) => [
    {
      id: `source-${id}`,
      nodeId,
      type: 'source' as const,
      position,
      isHidden: false,
      isConnectable: true,
      isValidTargetPos: true,
      x: 0,
      y: 0,
      edgeId: '',
      index,
      isVirtualHandle: false,
      XYPosition: null,
    },
    {
      id: `target-${id}`,
      nodeId,
      type: 'target' as const,
      position,
      isHidden: false,
      isConnectable: true,
      isValidTargetPos: true,
      x: 0,
      y: 0,
      edgeId: '',
      index,
      isVirtualHandle: false,
      XYPosition: null,
    },
  ]);
};

const buildConnectionHandles = (node: FixtureNode): ConnectionHandle[] => {
  if (!node.handles || node.handles.length === 0) {
    return buildFallbackHandles(node.id);
  }

  return node.handles.map((handle, index) => {
    const position = toPositionEnum(handle.position);
    return {
      id: handle.id,
      nodeId: node.id,
      type: handle.type,
      position,
      XYPosition:
        typeof handle.x === 'number' || typeof handle.y === 'number'
          ? {
              x: handle.x ?? 0,
              y: handle.y ?? 0,
            }
          : null,
      isHidden: handle.isHidden ?? false,
      isVirtualHandle: handle.isVirtualHandle ?? false,
      isConnectable: true,
      isValidTargetPos: true,
      x: handle.x ?? 0,
      y: handle.y ?? 0,
      edgeId: '',
      index,
    } as ConnectionHandle;
  });
};

const buildNodeData = (node: FixtureNode): NodeData => {
  return {
    targetObjectId: node.id,
    targetObjectKind: 'HarnessNode',
    targetObjectLabel: node.label,
    descriptionId: 'harness-node',
    insideLabel: null,
    outsideLabels: {},
    faded: false,
    pinned: false,
    nodeDescription: {} as never,
    defaultWidth: node.size.width,
    defaultHeight: node.size.height,
    isBorderNode: false,
    borderNodePosition: null,
    labelEditable: false,
    style: {},
    connectionHandles: buildConnectionHandles(node),
    isNew: false,
    resizedByUser: false,
    isListChild: false,
    isDraggedNode: false,
    isDropNodeTarget: false,
    isDropNodeCandidate: false,
    isHovered: false,
    connectionLinePositionOnNode: 'none',
    nodeAppearanceData: {
      customizedStyleProperties: [],
      gqlStyle: {} as never,
    },
    harnessLabel: node.label,
  } as NodeData & { harnessLabel: string };
};

const buildEdgeData = (edge: FixtureEdge): EdgeData => {
  return {
    targetObjectId: edge.id,
    targetObjectKind: 'HarnessEdge',
    targetObjectLabel: edge.id,
    descriptionId: 'harness-edge',
    label: null,
    faded: false,
    centerLabelEditable: false,
    bendingPoints: edge.bendingPoints ?? null,
    isHovered: false,
    edgeAppearanceData: {
      customizedStyleProperties: [],
      gqlStyle: {} as never,
    },
  } as EdgeData;
};

const resolveHandleId = (prefix: 'source' | 'target', edge: FixtureEdge, fallback: FixtureHandlePosition) => {
  if (prefix === 'source' && edge.sourceHandleId) {
    return edge.sourceHandleId;
  }
  if (prefix === 'target' && edge.targetHandleId) {
    return edge.targetHandleId;
  }
  return `${prefix}-${fallback}`;
};

const buildMarkerId = (type: string, edgeId: string) => `${type}--${edgeId}--markerEnd`;

const buildAbsolutePositionLookup = (nodes: FixtureNode[]): Map<string, XYPosition> => {
  return nodes.reduce((acc, node) => {
    acc.set(node.id, { x: node.position.x, y: node.position.y });
    return acc;
  }, new Map<string, XYPosition>());
};

const resolveRelativePosition = (
  node: FixtureNode,
  absoluteLookup: Map<string, XYPosition>
): XYPosition => {
  if (!node.parentId) {
    return { x: node.position.x, y: node.position.y };
  }
  const parentAbsolute = absoluteLookup.get(node.parentId);
  if (!parentAbsolute) {
    return { x: node.position.x, y: node.position.y };
  }
  return {
    x: node.position.x - parentAbsolute.x,
    y: node.position.y - parentAbsolute.y,
  };
};

export const convertFixtureToDiagram = (
  fixture: DiagramFixture
): { nodes: Node<NodeData>[]; edges: Edge<EdgeData>[] } => {
  const absoluteLookup = buildAbsolutePositionLookup(fixture.nodes);

  const nodes: Node<NodeData>[] = fixture.nodes.map((node) => {
    const relativePosition = resolveRelativePosition(node, absoluteLookup);
    const parentId = node.parentId;
    const harnessNode = {
      id: node.id,
      type: 'harnessNode',
      position: relativePosition,
      data: buildNodeData(node),
      width: node.size.width,
      height: node.size.height,
      draggable: false,
      selectable: false,
      positionAbsolute: { x: node.position.x, y: node.position.y },
      parentNode: parentId,
    } as Node<NodeData> & { parentId?: string };

    if (parentId) {
      harnessNode.parentId = parentId;
      harnessNode.extent = 'parent';
    }

    return harnessNode;
  });

  const edges: Edge<EdgeData>[] = fixture.edges.map((edge) => {
    const sourcePosition = toPositionEnum(edge.sourcePosition);
    const targetPosition = toPositionEnum(edge.targetPosition);
    const sourcePoint = edge.sourcePoint;
    const targetPoint = edge.targetPoint;
    return {
      id: edge.id,
      source: edge.source,
      target: edge.target,
      type: edge.type,
      sourcePosition,
      targetPosition,
      sourceHandle: resolveHandleId('source', edge, edge.sourcePosition ?? 'right'),
      targetHandle: resolveHandleId('target', edge, edge.targetPosition ?? 'left'),
      data: buildEdgeData(edge),
      sourceX: sourcePoint?.x,
      sourceY: sourcePoint?.y,
      targetX: targetPoint?.x,
      targetY: targetPoint?.y,
      style: {
        stroke: '#0f0f0f',
        strokeWidth: 2,
      },
      selectable: false,
      markerEnd: buildMarkerId('InputFillClosedArrow', edge.id),
    };
  });

  return { nodes, edges };
};
