import { Position, type Edge, type Node, type XYPosition } from '@xyflow/react';
import { describe, expect, it } from 'vitest';
import stackedWithDetour from '../../../../../../../dev/frontend/routing-harness/src/fixtures/stacked-with-detour.json';
import { buildDetouredPolyline } from '../postProcessEdgeDetours';
import { simplifyRectilinearBends } from '../../edge/SmoothStepEdgeWrapper';
import type { EdgeData, NodeData } from '../../DiagramRenderer.types';
import type { DiagramNodeType } from '../../node/NodeTypes.types';
import type { DiagramFixture } from '../../../../../../../dev/frontend/routing-harness/src/types';

type HarnessNode = Node<NodeData, DiagramNodeType>;
type HarnessEdge = Edge<EdgeData>;

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

const bottomCenter = (node: HarnessNode): XYPosition => ({
  x: (node.position.x ?? 0) + (node.width ?? 0) / 2,
  y: (node.position.y ?? 0) + (node.height ?? 0),
});

const topCenter = (node: HarnessNode): XYPosition => ({
  x: (node.position.x ?? 0) + (node.width ?? 0) / 2,
  y: node.position.y ?? 0,
});

describe('buildDetouredPolyline', () => {
  it('detours stacked-with-detour vertical edge around the obstacle', () => {
    const fixture = stackedWithDetour as DiagramFixture;
    const nodes = toHarnessNodes(fixture);
    const edgeFixture = fixture.edges.find((candidate) => candidate.id === 'edge-vertical');
    expect(edgeFixture).toBeTruthy();
    if (!edgeFixture) {
      return;
    }

    const edge: HarnessEdge = {
      id: edgeFixture.id,
      source: edgeFixture.source,
      target: edgeFixture.target,
      type: edgeFixture.type,
      sourcePosition: Position.Bottom,
      targetPosition: Position.Top,
      data: buildEdgeData(edgeFixture.id),
    };

    const sourceNode = nodes.find((node) => node.id === edge.source);
    const targetNode = nodes.find((node) => node.id === edge.target);
    expect(sourceNode).toBeTruthy();
    expect(targetNode).toBeTruthy();
    if (!sourceNode || !targetNode) {
      return;
    }

    const baseline = [bottomCenter(sourceNode), topCenter(targetNode)];
    const detoured = buildDetouredPolyline(edge, baseline, [edge], nodes);
    const finalPolyline = [
      detoured[0]!,
      ...simplifyRectilinearBends(detoured.slice(1, -1), detoured[0]!, detoured[detoured.length - 1]!),
      detoured[detoured.length - 1]!,
    ];

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
});
