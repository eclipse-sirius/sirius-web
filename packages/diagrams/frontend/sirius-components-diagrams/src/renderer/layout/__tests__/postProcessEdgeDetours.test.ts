import { readFileSync } from 'node:fs';
import path from 'node:path';
import { Position, type Edge, type Node, type XYPosition } from '@xyflow/react';
import { describe, expect, it } from 'vitest';
import { buildDetouredPolyline } from '../postProcessEdgeDetours';
import { simplifyRectilinearBends } from '../../edge/SmoothStepEdgeWrapper';
import type { EdgeData, NodeData } from '../../DiagramRenderer.types';
import type { DiagramNodeType } from '../../node/NodeTypes.types';
import type { DiagramFixture } from '../../../../../../../dev/frontend/routing-harness/src/types';

type HarnessNode = Node<NodeData, DiagramNodeType>;
type HarnessEdge = Edge<EdgeData>;

type Axis = 'horizontal' | 'vertical';

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

const buildEdgeData = (edgeId: string): EdgeData => ({
  targetObjectId: edgeId,
  targetObjectKind: 'HarnessEdge',
  targetObjectLabel: edgeId,
  descriptionId: 'harness-edge',
  label: null,
  faded: false,
  centerLabelEditable: false,
  bendingPoints: null,
  isHovered: false,
  edgeAppearanceData: {
    customizedStyleProperties: [],
    gqlStyle: {} as never,
  },
});

const handlePoint = (node: HarnessNode, position: Position): XYPosition => {
  const left = node.position.x ?? 0;
  const top = node.position.y ?? 0;
  const width = node.width ?? 0;
  const height = node.height ?? 0;

  switch (position) {
    case Position.Left:
      return { x: left, y: top + height / 2 };
    case Position.Right:
      return { x: left + width, y: top + height / 2 };
    case Position.Top:
      return { x: left + width / 2, y: top };
    case Position.Bottom:
    default:
      return { x: left + width / 2, y: top + height };
  }
};

const buildHarnessEdge = (fixture: DiagramFixture, edgeId: string): HarnessEdge | null => {
  const edgeFixture = fixture.edges.find((candidate) => candidate.id === edgeId);
  if (!edgeFixture) {
    return null;
  }
  return {
    id: edgeFixture.id,
    source: edgeFixture.source,
    target: edgeFixture.target,
    type: edgeFixture.type,
    sourcePosition: toPositionEnum(edgeFixture.sourcePosition),
    targetPosition: toPositionEnum(edgeFixture.targetPosition),
    data: buildEdgeData(edgeFixture.id),
    sourceHandle: edgeFixture.sourceHandleId,
    targetHandle: edgeFixture.targetHandleId,
  };
};

const buildFinalPolyline = (fixture: DiagramFixture, edgeId: string): XYPosition[] => {
  const nodes = toHarnessNodes(fixture);
  const edge = buildHarnessEdge(fixture, edgeId);
  if (!edge) {
    throw new Error(`Edge "${edgeId}" not found in fixture ${fixture.id}`);
  }

  const sourceNode = nodes.find((node) => node.id === edge.source);
  const targetNode = nodes.find((node) => node.id === edge.target);
  if (!sourceNode || !targetNode) {
    throw new Error(`Missing source or target node for edge ${edge.id}`);
  }

  const sourcePoint = handlePoint(sourceNode, edge.sourcePosition ?? Position.Right);
  const targetPoint = handlePoint(targetNode, edge.targetPosition ?? Position.Left);
  const initialAxis = determineInitialAxis(edge.sourcePosition ?? Position.Right);
  const baselineBendingPoints = ensureRectilinearPath([], sourcePoint, targetPoint, initialAxis);
  const simplifiedBaseline = simplifyRectilinearBends(baselineBendingPoints, sourcePoint, targetPoint);
  const baselinePolyline: XYPosition[] = [
    sourcePoint,
    ...simplifiedBaseline.map((point) => ({ x: point.x, y: point.y })),
    targetPoint,
  ];

  const detoured = buildDetouredPolyline(edge, baselinePolyline, [edge], nodes);
  return [
    detoured[0]!,
    ...simplifyRectilinearBends(detoured.slice(1, -1), detoured[0]!, detoured[detoured.length - 1]!),
    detoured[detoured.length - 1]!,
  ];
};

const determineInitialAxis = (position: Position): Axis =>
  position === Position.Left || position === Position.Right ? 'horizontal' : 'vertical';

const ensureRectilinearPath = (
  bendingPoints: XYPosition[],
  source: XYPosition,
  target: XYPosition,
  initialAxis: Axis,
): XYPosition[] => {
  const rectified: XYPosition[] = [];
  const checkpoints = [...bendingPoints, target];
  let prev = source;
  let axis: Axis = initialAxis;

  for (let i = 0; i < checkpoints.length; i++) {
    const current = checkpoints[i];
    if (!current) {
      continue;
    }
    let dx = current.x - prev.x;
    let dy = current.y - prev.y;
    let guard = 0;

    while (!(axis === 'horizontal' ? dy === 0 : dx === 0) && (dx !== 0 || dy !== 0) && guard < 4) {
      const intermediate = axis === 'horizontal' ? { x: current.x, y: prev.y } : { x: prev.x, y: current.y };
      if (intermediate.x === prev.x && intermediate.y === prev.y) {
        break;
      }
      rectified.push(intermediate);
      prev = intermediate;
      axis = axis === 'horizontal' ? 'vertical' : 'horizontal';
      dx = current.x - prev.x;
      dy = current.y - prev.y;
      guard++;
    }

    if (i < checkpoints.length - 1 && (current.x !== prev.x || current.y !== prev.y)) {
      rectified.push(current);
    }
    prev = current;
    axis = axis === 'horizontal' ? 'vertical' : 'horizontal';
  }

  return rectified;
};

const harnessFixturesDir = path.join(__dirname, '../../../../../../../dev/frontend/routing-harness/src/fixtures');

const loadFixture = (filename: string): DiagramFixture => {
  const content = readFileSync(path.join(harnessFixturesDir, filename), 'utf-8');
  return JSON.parse(content) as DiagramFixture;
};

const isRectilinear = (polyline: XYPosition[]): boolean =>
  polyline.every((point, index) => {
    if (index === 0) {
      return true;
    }
    const prev = polyline[index - 1]!;
    return point.x === prev.x || point.y === prev.y;
  });

describe('buildDetouredPolyline', () => {
  it('detours stacked-with-detour vertical edge around the obstacle', () => {
    const fixture = loadFixture('stacked-with-detour.json');
    const finalPolyline = buildFinalPolyline(fixture, 'edge-vertical');
    const nodes = toHarnessNodes(fixture);

    expect(finalPolyline.length).toBeGreaterThanOrEqual(2);
    const segmentCount = finalPolyline.length - 1;
    expect(segmentCount).toBe(5);

    // Ensure the detour exits the obstacle bounds horizontally.
    const obstacle = nodes.find((node) => node.id === 'obstacle');
    expect(obstacle).toBeTruthy();
    if (!obstacle) {
      return;
    }
    const obstacleLeft = obstacle.position.x ?? 0;
    const obstacleRight = obstacleLeft + (obstacle.width ?? 0);
    const traversesAroundObstacle =
      finalPolyline.some((point) => point.x < obstacleLeft) ||
      finalPolyline.some((point) => point.x > obstacleRight);
    expect(traversesAroundObstacle).toBe(true);
  });

  it('detours directional-vertical north-to-north edge to avoid the upper node', () => {
    const fixture = loadFixture('directional-vertical-b-above-a-north-b-north.json');
    const finalPolyline = buildFinalPolyline(fixture, 'edge-a-b');

    expect(finalPolyline.length).toBeGreaterThanOrEqual(2);
    const segmentCount = finalPolyline.length - 1;
    expect(segmentCount).toBe(5);

    // Expect the detour to leave the vertical column (i.e. go around either side of the upper node).
    const nodeB = fixture.nodes.find((node) => node.id === 'B');
    expect(nodeB).toBeTruthy();
    if (!nodeB) {
      return;
    }
    const left = nodeB.position.x;
    const right = left + nodeB.size.width;
    const escapesVerticalColumn =
      finalPolyline.some((point) => point.x < left - 0.5) || finalPolyline.some((point) => point.x > right + 0.5);
    expect(escapesVerticalColumn).toBe(true);
  });

  it('detours directional-vertical south-to-north edge around both nodes', () => {
    const fixture = loadFixture('directional-vertical-b-above-a-south-b-north.json');
    const finalPolyline = buildFinalPolyline(fixture, 'edge-a-b');

    expect(finalPolyline.length).toBeGreaterThanOrEqual(2);
    const segmentCount = finalPolyline.length - 1;
    expect(segmentCount).toBe(9);

    const nodeA = fixture.nodes.find((node) => node.id === 'A');
    const nodeB = fixture.nodes.find((node) => node.id === 'B');
    expect(nodeA).toBeTruthy();
    expect(nodeB).toBeTruthy();
    if (!nodeA || !nodeB) {
      return;
    }

    const nodeLeft = Math.min(nodeA.position.x, nodeB.position.x);
    const nodeRight = Math.max(nodeA.position.x + nodeA.size.width, nodeB.position.x + nodeB.size.width);
    const escapesColumn = finalPolyline.some((point) => point.x < nodeLeft - 0.5) || finalPolyline.some((point) => point.x > nodeRight + 0.5);
    expect(escapesColumn).toBe(true);
  });

  const lastChanceDetourFixtures = [
    { id: 'stacked-with-detour', edgeId: 'edge-vertical', filename: 'stacked-with-detour.json', expectedSegments: 5 },
    { id: 'directional-vertical-a-east-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-a-east-b-east.json', expectedSegments: 7 },
    { id: 'directional-vertical-a-east-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-a-east-b-north.json', expectedSegments: 4 },
    { id: 'directional-vertical-a-east-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-a-east-b-south.json', expectedSegments: 8 },
    { id: 'directional-vertical-a-east-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-a-east-b-west.json', expectedSegments: 7 },
    { id: 'directional-vertical-a-north-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-a-north-b-east.json', expectedSegments: 8 },
    { id: 'directional-vertical-a-north-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-a-north-b-north.json', expectedSegments: 5 },
    { id: 'directional-vertical-a-north-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-a-north-b-south.json', expectedSegments: 9 },
    { id: 'directional-vertical-a-north-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-a-north-b-west.json', expectedSegments: 8 },
    { id: 'directional-vertical-a-south-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-a-south-b-east.json', expectedSegments: 4 },
    { id: 'directional-vertical-a-south-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-a-south-b-north.json', expectedSegments: 1 },
    { id: 'directional-vertical-a-south-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-a-south-b-south.json', expectedSegments: 5 },
    { id: 'directional-vertical-a-south-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-a-south-b-west.json', expectedSegments: 4 },
    { id: 'directional-vertical-a-west-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-a-west-b-east.json', expectedSegments: 7 },
    { id: 'directional-vertical-a-west-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-a-west-b-north.json', expectedSegments: 4 },
    { id: 'directional-vertical-a-west-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-a-west-b-south.json', expectedSegments: 8 },
    { id: 'directional-vertical-a-west-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-a-west-b-west.json', expectedSegments: 7 },
    { id: 'directional-vertical-b-above-a-east-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-east-b-east.json', expectedSegments: 7 },
    { id: 'directional-vertical-b-above-a-east-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-east-b-north.json', expectedSegments: 8 },
    { id: 'directional-vertical-b-above-a-east-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-east-b-south.json', expectedSegments: 4 },
    { id: 'directional-vertical-b-above-a-east-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-east-b-west.json', expectedSegments: 7 },
    { id: 'directional-vertical-b-above-a-north-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-north-b-east.json', expectedSegments: 4 },
    { id: 'directional-vertical-b-above-a-north-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-north-b-north.json', expectedSegments: 5 },
    { id: 'directional-vertical-b-above-a-north-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-north-b-south.json', expectedSegments: 1 },
    { id: 'directional-vertical-b-above-a-north-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-north-b-west.json', expectedSegments: 4 },
    { id: 'directional-vertical-b-above-a-south-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-south-b-east.json', expectedSegments: 8 },
    { id: 'directional-vertical-b-above-a-south-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-south-b-north.json', expectedSegments: 9 },
    { id: 'directional-vertical-b-above-a-south-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-south-b-south.json', expectedSegments: 5 },
    { id: 'directional-vertical-b-above-a-south-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-south-b-west.json', expectedSegments: 8 },
    { id: 'directional-vertical-b-above-a-west-b-east', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-west-b-east.json', expectedSegments: 7 },
    { id: 'directional-vertical-b-above-a-west-b-north', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-west-b-north.json', expectedSegments: 8 },
    { id: 'directional-vertical-b-above-a-west-b-south', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-west-b-south.json', expectedSegments: 4 },
    { id: 'directional-vertical-b-above-a-west-b-west', edgeId: 'edge-a-b', filename: 'directional-vertical-b-above-a-west-b-west.json', expectedSegments: 7 },
    { id: 'directional-horizontal-b-left-a-east-b-east', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-east-b-east.json', expectedSegments: 5 },
    { id: 'directional-horizontal-b-left-a-east-b-north', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-east-b-north.json', expectedSegments: 8 },
    { id: 'directional-horizontal-b-left-a-east-b-south', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-east-b-south.json', expectedSegments: 8 },
    { id: 'directional-horizontal-b-left-a-east-b-west', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-east-b-west.json', expectedSegments: 9 },
    { id: 'directional-horizontal-b-left-a-north-b-east', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-north-b-east.json', expectedSegments: 4 },
    { id: 'directional-horizontal-b-left-a-north-b-north', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-north-b-north.json', expectedSegments: 7 },
    { id: 'directional-horizontal-b-left-a-north-b-south', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-north-b-south.json', expectedSegments: 7 },
    { id: 'directional-horizontal-b-left-a-north-b-west', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-north-b-west.json', expectedSegments: 8 },
    { id: 'directional-horizontal-b-left-a-south-b-east', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-south-b-east.json', expectedSegments: 4 },
    { id: 'directional-horizontal-b-left-a-south-b-north', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-south-b-north.json', expectedSegments: 7 },
    { id: 'directional-horizontal-b-left-a-south-b-south', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-south-b-south.json', expectedSegments: 7 },
    { id: 'directional-horizontal-b-left-a-south-b-west', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-south-b-west.json', expectedSegments: 8 },
    { id: 'directional-horizontal-b-left-a-west-b-east', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-west-b-east.json', expectedSegments: 1 },
    { id: 'directional-horizontal-b-left-a-west-b-north', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-west-b-north.json', expectedSegments: 4 },
    { id: 'directional-horizontal-b-left-a-west-b-south', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-west-b-south.json', expectedSegments: 4 },
    { id: 'directional-horizontal-b-left-a-west-b-west', edgeId: 'edge-a-b', filename: 'directional-horizontal-b-left-a-west-b-west.json', expectedSegments: 5 },
  ] as const;

  it.each(lastChanceDetourFixtures)('produces rectilinear detours for %s', ({ filename, edgeId, id, expectedSegments }) => {
    const fixture = loadFixture(filename);
    const finalPolyline = buildFinalPolyline(fixture, edgeId);

    const segmentCount = finalPolyline.length - 1;
    const rectilinear = isRectilinear(finalPolyline);
    expect(finalPolyline.length).toBeGreaterThanOrEqual(2);
    expect(segmentCount).toBe(expectedSegments);
    expect(rectilinear).toBe(true);
  });
});
