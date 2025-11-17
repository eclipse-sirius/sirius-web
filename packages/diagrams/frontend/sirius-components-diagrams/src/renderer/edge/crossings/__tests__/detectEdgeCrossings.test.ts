/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { Edge, Node } from '@xyflow/react';
import { describe, it, assert } from 'vitest';
import { detectEdgeCrossings } from '../detectEdgeCrossings';
import { MultiLabelEdgeData } from '../../MultiLabelEdge.types';
import { NodeData } from '../../../DiagramRenderer.types';

type TestEdge = Edge<MultiLabelEdgeData>;
type TestNode = Node<NodeData>;

/**
 * Builds a fully populated edge data object so every test can focus on the path geometry.
 */
const buildEdgeData = (overrides: Partial<MultiLabelEdgeData> = {}): MultiLabelEdgeData => {
  const baseData: MultiLabelEdgeData = {
    targetObjectId: overrides.targetObjectId ?? 'edge',
    targetObjectKind: overrides.targetObjectKind ?? 'Edge',
    targetObjectLabel: overrides.targetObjectLabel ?? 'Edge',
    descriptionId: overrides.descriptionId ?? 'edge-description',
    label: overrides.label ?? null,
    beginLabel: overrides.beginLabel,
    endLabel: overrides.endLabel,
    faded: overrides.faded ?? false,
    centerLabelEditable: overrides.centerLabelEditable ?? false,
    bendingPoints: overrides.bendingPoints ?? null,
    edgePath: overrides.edgePath ?? '',
    isHovered: overrides.isHovered ?? false,
    rectilinearFanInEnabled: overrides.rectilinearFanInEnabled ?? true,
    rectilinearFanOutEnabled: overrides.rectilinearFanOutEnabled ?? true,
    rectilinearMinOutwardLength: overrides.rectilinearMinOutwardLength ?? 4,
    rectilinearObstacleDetoursEnabled: overrides.rectilinearObstacleDetoursEnabled ?? true,
    rectilinearParallelSpacingEnabled: overrides.rectilinearParallelSpacingEnabled ?? true,
    rectilinearSimplifyEnabled: overrides.rectilinearSimplifyEnabled ?? true,
    rectilinearStraightenEnabled: overrides.rectilinearStraightenEnabled ?? true,
    rectilinearTurnPreference: overrides.rectilinearTurnPreference ?? 'target',
    edgeAppearanceData:
      overrides.edgeAppearanceData ??
      ({
        customizedStyleProperties: [],
        gqlStyle: {} as never,
      } as MultiLabelEdgeData['edgeAppearanceData']),
    crossingGaps: overrides.crossingGaps,
  };
  return baseData;
};

/**
 * Builds a helper edge with just enough properties for the detector.
 */
const buildEdge = (
  id: string,
  pathDefinition: string,
  overrides: Partial<TestEdge> = {},
  dataOverrides: Partial<MultiLabelEdgeData> = {}
): TestEdge => {
  const { data: _ignoredData, ...restOverrides } = overrides;
  return {
    id,
    type: 'test-edge',
    source: restOverrides.source ?? `${id}-source`,
    target: restOverrides.target ?? `${id}-target`,
    data: buildEdgeData({ edgePath: pathDefinition, ...dataOverrides }),
    ...restOverrides,
  };
};

/**
 * Builds a helper node sized/positioned in diagram coordinates so the proximity filter can be exercised.
 */
const buildNode = (id: string, x: number, y: number, width = 40, height = 40): TestNode => {
  const baseData: NodeData = {
    targetObjectId: id,
    targetObjectKind: 'Node',
    targetObjectLabel: id,
    descriptionId: 'node-description',
    insideLabel: null,
    outsideLabels: {},
    faded: false,
    pinned: false,
    nodeDescription: {} as never,
    defaultWidth: width,
    defaultHeight: height,
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
    type: 'default',
    width,
    height,
    position: { x, y },
    data: baseData,
    dragging: false,
    selected: false,
  };
};

describe('detectEdgeCrossings', () => {
  it('identifies orthogonal crossings and fades the higher z-index edge', () => {
    // Arrange a vertical and a horizontal edge crossing at (0, 20) with distinct z-indexes.
    const verticalEdge = buildEdge('vertical', 'M 0 0 L 0 40', { zIndex: 1 });
    const horizontalEdge = buildEdge('horizontal', 'M -20 20 L 20 20', { zIndex: 2 });

    // Act by feeding both edges to the detector.
    const windows = detectEdgeCrossings([verticalEdge, horizontalEdge]);

    // Assert that only the horizontal edge (higher z-index) receives the fade window.
    assert.strictEqual(windows.size, 1, 'should only emit windows for the under-edge');
    const horizontalWindows = windows.get('horizontal');
    assert.isDefined(horizontalWindows, 'should store windows on the horizontal edge id');
    assert.lengthOf(horizontalWindows ?? [], 1, 'should emit exactly one window for a single crossing');
    const [window] = horizontalWindows!;
    assert.deepEqual(window.intersection, { x: 0, y: 20 }, 'should capture the precise intersection coordinates');
    assert.approximately(window.startRatio, 0.275, 0.001, 'should start fading roughly 9 units before the crossing');
    assert.approximately(window.endRatio, 0.725, 0.001, 'should end fading roughly 9 units after the crossing');
  });

  it('ignores intersections that lie near either path endpoint', () => {
    // Arrange two edges that meet only 1 px away from the vertical edge endpoint.
    const shortVertical = buildEdge('shortVertical', 'M 0 0 L 0 10');
    const horizontal = buildEdge('nearEndpoint', 'M -10 9 L 10 9');

    // Act.
    const windows = detectEdgeCrossings([shortVertical, horizontal]);

    // Assert.
    assert.strictEqual(
      windows.size,
      0,
      'should drop intersections that occur inside the endpoint padding window to avoid handle portals'
    );
  });

  it('ignores intersections occurring within the padded bounds of connected nodes', () => {
    // Arrange nodes so that both edges share a common source rectangle at (0, 0).
    const sharedNode = buildNode('shared-node', 0, 0, 40, 40);
    const targetA = buildNode('target-a', 0, 80, 40, 40);
    const targetB = buildNode('target-b', 80, 0, 40, 40);

    // Build two edges whose first segment exits the shared node and crosses immediately after leaving the node bounds.
    const edgeA = buildEdge(
      'edge-a',
      'M 10 10 L 10 80',
      { source: sharedNode.id, target: targetA.id },
      { targetObjectId: 'edge-a' }
    );
    const edgeB = buildEdge(
      'edge-b',
      'M 10 10 L 80 10',
      { source: sharedNode.id, target: targetB.id },
      { targetObjectId: 'edge-b' }
    );

    // Act.
    const windows = detectEdgeCrossings([edgeA, edgeB], [sharedNode, targetA, targetB]);

    // Assert.
    assert.strictEqual(
      windows.size,
      0,
      'should skip crossings that still occur within the padded bounds of either endpoint node'
    );
  });

  it('falls back to deterministic edge ids when z-indexes are missing', () => {
    // Arrange two edges that cross with identical z-order so the detector must rely on ids.
    const first = buildEdge('edge-a', 'M 0 0 L 0 40');
    const second = buildEdge('edge-b', 'M -20 20 L 20 20');

    // Act with a custom fade size to make the math easy to verify.
    const windows = detectEdgeCrossings([first, second], [], 10);

    // Assert that the lexicographically higher id ("edge-b") receives the fade.
    const underEdgeWindows = windows.get('edge-b');
    assert.isDefined(underEdgeWindows, 'should select the lexicographically higher id as the under-edge');
    assert.lengthOf(underEdgeWindows ?? [], 1, 'should emit exactly one window for the single intersection');
    const [window] = underEdgeWindows!;
    assert.approximately(window.startRatio, 0.375, 0.001, 'should honor the custom fade length when computing ratios');
    assert.approximately(window.endRatio, 0.625, 0.001, 'should span an equal distance after the crossing point');
  });
});
