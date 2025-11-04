import { readFileSync } from 'node:fs';
import path from 'node:path';
import React, { PropsWithChildren } from 'react';
import { render, RenderOptions } from '@testing-library/react';
import {
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from 'vitest';
import {
  Position,
  type Edge,
  type EdgeProps,
  type Node,
  type XYPosition,
} from '@xyflow/react';
import { NodeTypeContext } from '../../../contexts/NodeContext';
import type { NodeTypeContextValue } from '../../../contexts/NodeContext.types';
import type { DiagramFixture } from '../../../../../../../dev/frontend/routing-harness/src/types';
import { SmoothStepEdgeWrapper } from '../../edge/SmoothStepEdgeWrapper';
import type { MultiLabelEdgeData } from '../../edge/MultiLabelEdge.types';
import type { NodeData } from '../../DiagramRenderer.types';
import type { DiagramNodeType } from '../../node/NodeTypes.types';
import { StoreContext } from '../../../representation/StoreContext';
import type { StoreContextValue } from '../../../representation/StoreContext.types';

type HarnessNode = Node<NodeData, DiagramNodeType>;
type HarnessEdge = Edge<MultiLabelEdgeData>;

type SegmentAxis = 'horizontal' | 'vertical';

type PolylineSegment = {
  edgeId: string;
  axis: SegmentAxis;
  coordinate: number;
  start: number;
  end: number;
};

type SegmentOverlap = PolylineSegment & {
  otherEdgeId: string;
  overlapStart: number;
  overlapEnd: number;
};

const mockInternalNodes = new Map<string, InternalNodeStub>();
const capturedPolylines = new Map<string, XYPosition[]>();

type InternalNodeStub = HarnessNode & {
  internals: {
    positionAbsolute: XYPosition;
  };
};

const harnessFixturesDir = path.join(
  __dirname,
  '../../../../../../../dev/frontend/routing-harness/src/fixtures',
);

const loadFixture = (filename: string): DiagramFixture => {
  const content = readFileSync(path.join(harnessFixturesDir, filename), 'utf-8');
  return JSON.parse(content) as DiagramFixture;
};

const toPositionEnum = (position: string | undefined): Position => {
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

const buildEdgeData = (
  edgeId: string,
  overrides: Partial<MultiLabelEdgeData> = {}
): MultiLabelEdgeData => ({
  targetObjectId: edgeId,
  targetObjectKind: 'HarnessEdge',
  targetObjectLabel: edgeId,
  descriptionId: 'harness-edge',
  label: null,
  faded: false,
  centerLabelEditable: false,
  bendingPoints: null,
  edgeAppearanceData: {
    customizedStyleProperties: [],
    gqlStyle: {} as never,
  },
  isHovered: false,
  ...overrides,
});

const toHarnessNodes = (fixture: DiagramFixture): HarnessNode[] =>
  fixture.nodes.map((node) => {
    const { id, label, position, size } = node;
    const data: NodeData = {
      targetObjectId: id,
      targetObjectKind: 'HarnessNode',
      targetObjectLabel: label,
      descriptionId: 'harness-node',
      insideLabel: null,
      outsideLabels: {},
      faded: false,
      pinned: false,
      nodeDescription: {} as never,
      defaultWidth: size.width,
      defaultHeight: size.height,
      isBorderNode: false,
      borderNodePosition: null,
      labelEditable: false,
      style: {},
      connectionHandles: [],
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
    };

    return {
      id,
      type: 'harnessNode',
      position: { ...position },
      width: size.width,
      height: size.height,
      data,
    } as HarnessNode;
  });

const buildHarnessEdge = (
  fixture: DiagramFixture,
  edgeId: string,
  overrides: Partial<MultiLabelEdgeData> = {}
): HarnessEdge | null => {
  const edgeFixture = fixture.edges.find((candidate) => candidate.id === edgeId);
  if (!edgeFixture) {
    return null;
  }

  return {
    id: edgeFixture.id,
    source: edgeFixture.source,
    target: edgeFixture.target,
    type: edgeFixture.type,
    markerEnd: undefined,
    markerStart: undefined,
    selected: false,
    sourceHandle: edgeFixture.sourceHandleId,
    targetHandle: edgeFixture.targetHandleId,
    sourcePosition: toPositionEnum(edgeFixture.sourcePosition),
    targetPosition: toPositionEnum(edgeFixture.targetPosition),
    data: buildEdgeData(edgeFixture.id, overrides),
  } as HarnessEdge;
};

type EdgeOverridesLookup = Partial<Record<string, Partial<MultiLabelEdgeData>>>;

const toHarnessEdges = (fixture: DiagramFixture, overrides: EdgeOverridesLookup = {}): HarnessEdge[] =>
  fixture.edges
    .map((edge) => buildHarnessEdge(fixture, edge.id, overrides[edge.id] ?? {}))
    .filter((edge): edge is HarnessEdge => !!edge);

const buildInternalNode = (node: HarnessNode): InternalNodeStub => ({
  ...node,
  internals: {
    positionAbsolute: {
      x: node.position?.x ?? 0,
      y: node.position?.y ?? 0,
    },
  },
});

const segmentsFromPolyline = (edgeId: string, polyline: XYPosition[]): PolylineSegment[] => {
  const segments: PolylineSegment[] = [];

  for (let index = 1; index < polyline.length; index++) {
    const prev = polyline[index - 1];
    const current = polyline[index];
    if (!prev || !current) {
      continue;
    }

    if (prev.x === current.x && prev.y === current.y) {
      continue;
    }

    if (prev.x === current.x) {
      const top = Math.min(prev.y, current.y);
      const bottom = Math.max(prev.y, current.y);
      segments.push({
        edgeId,
        axis: 'vertical',
        coordinate: prev.x,
        start: top,
        end: bottom,
      });
      continue;
    }

    if (prev.y === current.y) {
      const left = Math.min(prev.x, current.x);
      const right = Math.max(prev.x, current.x);
      segments.push({
        edgeId,
        axis: 'horizontal',
        coordinate: prev.y,
        start: left,
        end: right,
      });
    }
  }

  return segments;
};

const findOverlappingSegments = (
  segments: PolylineSegment[],
  tolerance = 0.5,
  minOverlapLength = 1,
): SegmentOverlap[] => {
  const overlaps: SegmentOverlap[] = [];
  for (let i = 0; i < segments.length; i++) {
    const first = segments[i];
    for (let j = i + 1; j < segments.length; j++) {
      const second = segments[j];
      if (first.edgeId === second.edgeId) {
        continue;
      }
      if (first.axis !== second.axis) {
        continue;
      }
      if (Math.abs(first.coordinate - second.coordinate) > tolerance) {
        continue;
      }

      const start = Math.max(first.start, second.start);
      const end = Math.min(first.end, second.end);
      if (end - start < minOverlapLength) {
        continue;
      }

      overlaps.push({
        ...first,
        otherEdgeId: second.edgeId,
        overlapStart: start,
        overlapEnd: end,
      });
    }
  }
  return overlaps;
};

const renderWithHarnessProviders = (
  ui: React.ReactElement,
  storeValue: StoreContextValue,
  nodeContextValue: NodeTypeContextValue,
  options?: RenderOptions,
) =>
  render(ui, {
    wrapper: ({ children }: PropsWithChildren) => (
      <StoreContext.Provider value={storeValue}>
        <NodeTypeContext.Provider value={nodeContextValue}>{children}</NodeTypeContext.Provider>
      </StoreContext.Provider>
    ),
    ...options,
  });

type RenderHarnessResult = {
  nodes: HarnessNode[];
  edges: HarnessEdge[];
  polylines: Map<string, XYPosition[]>;
};

const renderHarnessFixture = (
  fixture: DiagramFixture,
  edgeOverrides: EdgeOverridesLookup = {}
): RenderHarnessResult => {
  const nodes = toHarnessNodes(fixture);
  const edges = toHarnessEdges(fixture, edgeOverrides);

  nodes.forEach((node) => {
    mockInternalNodes.set(node.id, buildInternalNode(node));
  });

  const storeValue: StoreContextValue = {
    getEdges: () => edges,
    getEdge: (id: string) => edges.find((edge) => edge.id === id),
    getNodes: () => nodes,
    getNode: (id: string) => nodes.find((node) => node.id === id),
    onEdgesChange: () => {},
    onNodesChange: () => {},
    setEdges: () => {},
    setNodes: () => {},
  };

  const nodeContextValue: NodeTypeContextValue = {
    nodeLayoutHandlers: [],
    nodeConverters: [],
    nodeTypeContributions: [],
  };

  edges.forEach((edge) => {
    const props: EdgeProps<Edge<MultiLabelEdgeData>> = {
      ...edge,
      id: edge.id,
      source: edge.source,
      target: edge.target,
      data: edge.data,
      sourceHandleId: edge.sourceHandle ?? null,
      targetHandleId: edge.targetHandle ?? null,
      sourcePosition: edge.sourcePosition,
      targetPosition: edge.targetPosition,
    };

    const { unmount } = renderWithHarnessProviders(<SmoothStepEdgeWrapper {...props} />, storeValue, nodeContextValue);
    unmount();
  });

  return {
    nodes,
    edges,
    polylines: new Map(capturedPolylines),
  };
};

vi.mock('@xyflow/react', async () => {
  const actual = await vi.importActual<typeof import('@xyflow/react')>('@xyflow/react');
  return {
    ...actual,
    useInternalNode: (id: string) => mockInternalNodes.get(id) ?? null,
  };
});

vi.mock('../../edge/EdgeLayout', async () => {
  const actual = await vi.importActual<typeof import('../../edge/EdgeLayout')>('../../edge/EdgeLayout');
  const xyflow = await vi.importActual<typeof import('@xyflow/react')>('@xyflow/react');
  const { Position: PositionEnum } = xyflow;

  const computeHandleCoordinates = (node: InternalNodeStub | undefined, position: Position): XYPosition => {
    if (!node) {
      return { x: 0, y: 0 };
    }
    const absoluteX = node.internals.positionAbsolute.x ?? 0;
    const absoluteY = node.internals.positionAbsolute.y ?? 0;
    const width = node.width ?? 0;
    const height = node.height ?? 0;

    switch (position) {
      case PositionEnum.Left:
        return { x: absoluteX, y: absoluteY + height / 2 };
      case PositionEnum.Right:
        return { x: absoluteX + width, y: absoluteY + height / 2 };
      case PositionEnum.Top:
        return { x: absoluteX + width / 2, y: absoluteY };
      case PositionEnum.Bottom:
      default:
        return { x: absoluteX + width / 2, y: absoluteY + height };
    }
  };

  return {
    ...actual,
    getHandleCoordinatesByPosition: (
      node: InternalNodeStub | undefined,
      handlePosition: Position,
    ): XYPosition => computeHandleCoordinates(node, handlePosition),
  };
});

vi.mock('../../edge/rectilinear-edge/MultiLabelRectilinearEditableEdge', () => ({
  MultiLabelRectilinearEditableEdge: (props: {
    id: string;
    sourceX: number;
    sourceY: number;
    targetX: number;
    targetY: number;
    bendingPoints: XYPosition[];
  }) => {
    const { id, sourceX, sourceY, targetX, targetY, bendingPoints } = props;
    capturedPolylines.set(id, [
      { x: sourceX, y: sourceY },
      ...bendingPoints.map((point) => ({ x: point.x, y: point.y })),
      { x: targetX, y: targetY },
    ]);
    return null;
  },
}));

describe('SmoothStepEdgeWrapper parallel spacing post-processing', () => {
  beforeEach(() => {
    capturedPolylines.clear();
    mockInternalNodes.clear();
  });

  it('separates overlapping segments for the grid-crossing fixture by default', () => {
    const fixture = loadFixture('grid-crossing.json');
    const { polylines } = renderHarnessFixture(fixture);
    // eslint-disable-next-line no-console
    console.log(
      JSON.stringify(
        Array.from(polylines.entries()).map(([edgeId, polyline]) => ({
          edgeId,
          points: polyline.map((point) => ({ x: point.x, y: point.y })),
        })),
        null,
        2
      )
    );

    const allSegments = Array.from(polylines.entries()).flatMap(([edgeId, polyline]) =>
      segmentsFromPolyline(edgeId, polyline),
    );
    expect(allSegments.length).toBeGreaterThan(0);

    const overlaps = findOverlappingSegments(allSegments);
    // eslint-disable-next-line no-console
    console.log(overlaps);
    expect(overlaps.length).toBe(0);

    const diagonalDown = polylines.get('edge-diagonal-down');
    const diagonalUp = polylines.get('edge-diagonal-up');
    expect(diagonalDown).toBeDefined();
    expect(diagonalUp).toBeDefined();
    const downColumn = diagonalDown?.[1]?.x ?? 0;
    const upColumn = diagonalUp?.[1]?.x ?? 0;
    expect(Math.abs(downColumn - upColumn)).toBeGreaterThanOrEqual(6);
  });

  it('can be disabled to preserve the legacy overlapping behaviour', () => {
    const fixture = loadFixture('grid-crossing.json');
    const overrides: EdgeOverridesLookup = {
      'edge-horizontal-top': { rectilinearParallelSpacingEnabled: false },
      'edge-horizontal-bottom': { rectilinearParallelSpacingEnabled: false },
      'edge-diagonal-down': { rectilinearParallelSpacingEnabled: false },
      'edge-diagonal-up': { rectilinearParallelSpacingEnabled: false },
    };
    const { polylines } = renderHarnessFixture(fixture, overrides);

    const allSegments = Array.from(polylines.entries()).flatMap(([edgeId, polyline]) =>
      segmentsFromPolyline(edgeId, polyline),
    );
    expect(allSegments.length).toBeGreaterThan(0);

    const overlaps = findOverlappingSegments(allSegments);
    const diagonalOverlapDetected = overlaps.some(
      ({ edgeId, otherEdgeId }) =>
        (edgeId === 'edge-diagonal-down' && otherEdgeId === 'edge-diagonal-up') ||
        (edgeId === 'edge-diagonal-up' && otherEdgeId === 'edge-diagonal-down'),
    );

    expect(overlaps.length).toBeGreaterThan(0);
    expect(diagonalOverlapDetected).toBe(true);
  });
});

// Dedicated suite that exercises the almost-straight straightening heuristic.
describe('SmoothStepEdgeWrapper endpoint straightening post-processing', () => {
  beforeEach(() => {
    capturedPolylines.clear();
    mockInternalNodes.clear();
  });

  // When the feature is on, the vertical edge should collapse onto a single X coordinate.
  it('straightens nearly vertical edges by default', () => {
    const fixture = loadFixture('almost-straight-vertical.json');
    const { polylines } = renderHarnessFixture(fixture);
    const polyline = polylines.get('edge-vertical-near');
    expect(polyline).toBeDefined();
    const xValues = (polyline ?? []).map((point) => point.x);
    const minX = Math.min(...xValues);
    const maxX = Math.max(...xValues);
    expect(maxX - minX).toBeLessThan(0.5);
    expect((polyline ?? []).length).toBe(2);
  });

  // Opting out keeps the tiny zig-zag so we still hit the regression the old behaviour exposed.
  it('can be disabled to preserve the tiny zig-zag', () => {
    const fixture = loadFixture('almost-straight-vertical.json');
    const overrides: EdgeOverridesLookup = {
      'edge-vertical-near': { rectilinearStraightenEnabled: false },
    };
    const { polylines } = renderHarnessFixture(fixture, overrides);
    const polyline = polylines.get('edge-vertical-near');
    expect(polyline).toBeDefined();
    const xValues = (polyline ?? []).map((point) => point.x);
    const minX = Math.min(...xValues);
    const maxX = Math.max(...xValues);
    expect(maxX - minX).toBeGreaterThan(0.5);
    expect(maxX - minX).toBeLessThanOrEqual(20);
  });

  // Straightening must leave the fan offsets untouched when multiple edges share the same handle.
  it('skips straightening when a fan already spreads the edges', () => {
    const fixture = loadFixture('fan-out-right.json');
    const { polylines } = renderHarnessFixture(fixture);
    const top = polylines.get('edge-top');
    const bottom = polylines.get('edge-bottom');
    expect(top).toBeDefined();
    expect(bottom).toBeDefined();
    const topFirstBend = top?.[1];
    const bottomFirstBend = bottom?.[1];
    expect(topFirstBend).toBeDefined();
    expect(bottomFirstBend).toBeDefined();
    const deltaY = Math.abs((topFirstBend?.y ?? 0) - (bottomFirstBend?.y ?? 0));
    expect(deltaY).toBeGreaterThan(0.5);
  });
});
