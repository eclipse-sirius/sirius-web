/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import 'reflect-metadata';
import { AnchorComputerRegistry, RoutedPoint, SModelRoot, SNode, SParentElement } from 'sprotty';
import { Bounds, Point } from 'sprotty-protocol';
import { expect, test } from 'vitest';
import { Edge, Ratio } from '../../Diagram.types';
import { SiriusRectangleAnchor } from '../siriusPolylineAnchor';
import { SiriusPolylineEdgeRouter } from '../siriusPolylineEdgeRouter';

const DEFAULT_ANCHOR_RATIO = { x: 0.5, y: 0.5 };

const initSiriusPolylineEdgeRouter = () => {
  const anchorComputerRegistry = new AnchorComputerRegistry([new SiriusRectangleAnchor()]);

  const siriusPolylineEdgeRouter = new SiriusPolylineEdgeRouter();
  siriusPolylineEdgeRouter.anchorRegistry = anchorComputerRegistry;

  return siriusPolylineEdgeRouter;
};

const createEdge = (
  sourceId: string,
  targetId: string,
  parent: SParentElement,
  routingPoints: Point[] = [],
  sourceAnchorRatio: Ratio = DEFAULT_ANCHOR_RATIO,
  targetAnchorRatio: Ratio = DEFAULT_ANCHOR_RATIO
): Edge => {
  const edge = new Edge();
  edge.routerKind = 'rectangular';
  edge.sourceId = sourceId;
  edge.targetId = targetId;
  edge.sourceAnchorRelativePosition = sourceAnchorRatio;
  edge.targetAnchorRelativePosition = targetAnchorRatio;
  edge.routingPoints = [...routingPoints];
  parent.add(edge);

  return edge;
};

const createSimpleNode = (
  nodeId: string,
  parent: SParentElement,
  bounds: Bounds = {
    x: 5,
    y: 5,
    width: 10,
    height: 10,
  }
): SNode => {
  const node: SNode = new SNode();
  node.id = nodeId;
  node.bounds = { ...bounds };

  parent.add(node);
  return node;
};

test('can route self-loop edges', () => {
  const siriusPolylineEdgeRouter = initSiriusPolylineEdgeRouter();

  const modelRoot: SModelRoot = new SModelRoot();

  const nodeId: string = 'node 1';
  const node1 = createSimpleNode(nodeId, modelRoot, { x: 5, y: 5, width: 15, height: 10 });
  const edge = createEdge(nodeId, nodeId, modelRoot);

  expect(edge.source).toBe(node1);
  expect(edge.target).toBe(node1);

  const routedPoint: RoutedPoint[] = siriusPolylineEdgeRouter.route(edge);
  expect(routedPoint).toHaveLength(4);
  expect(routedPoint[0]).toEqual<RoutedPoint>({ kind: 'source', x: 10, y: 5 });
  expect(routedPoint[1]).toEqual<RoutedPoint>({ kind: 'linear', x: 10, y: -5, pointIndex: 0 });
  expect(routedPoint[2]).toEqual<RoutedPoint>({ kind: 'linear', x: 15, y: -5, pointIndex: 1 });
  expect(routedPoint[3]).toEqual<RoutedPoint>({ kind: 'target', x: 15, y: 5 });
});

test('can route edges with existing routing points', () => {
  const siriusPolylineEdgeRouter = initSiriusPolylineEdgeRouter();

  const modelRoot: SModelRoot = new SModelRoot();

  const nodeId1: string = 'node 1';
  const nodeId2: string = 'node 2';
  const node1 = createSimpleNode(nodeId1, modelRoot, {
    x: 5,
    y: 5,
    width: 10,
    height: 10,
  });
  const node2 = createSimpleNode(nodeId2, modelRoot, {
    x: 25,
    y: 15,
    width: 10,
    height: 10,
  });

  const edge = createEdge(
    nodeId1,
    nodeId2,
    modelRoot,
    [
      { x: 20, y: 7.5 },
      { x: 20, y: 17.5 },
    ],
    { x: 0.75, y: 0.25 },
    { x: 0.25, y: 0.25 }
  );

  expect(edge.source).toBe(node1);
  expect(edge.target).toBe(node2);

  const routedPoint: RoutedPoint[] = siriusPolylineEdgeRouter.route(edge);
  expect(routedPoint).toHaveLength(4);
  expect(routedPoint[0]).toEqual<RoutedPoint>({ kind: 'source', x: 15, y: 7.5 });
  expect(routedPoint[1]).toEqual<RoutedPoint>({ kind: 'linear', x: 20, y: 7.5, pointIndex: 0 });
  expect(routedPoint[2]).toEqual<RoutedPoint>({ kind: 'linear', x: 20, y: 17.5, pointIndex: 1 });
  expect(routedPoint[3]).toEqual<RoutedPoint>({ kind: 'target', x: 25, y: 17.5 });
});

test('can route straight edge', () => {
  const siriusPolylineEdgeRouter = initSiriusPolylineEdgeRouter();

  const modelRoot: SModelRoot = new SModelRoot();

  const nodeId1: string = 'node 1';
  const nodeId2: string = 'node 2';
  const node1 = createSimpleNode(nodeId1, modelRoot, {
    x: 5,
    y: 5,
    width: 10,
    height: 10,
  });
  const node2 = createSimpleNode(nodeId2, modelRoot, {
    x: 25,
    y: 15,
    width: 10,
    height: 10,
  });

  const edge = createEdge(nodeId1, nodeId2, modelRoot, [], { x: 0.75, y: 0.25 }, { x: 0.25, y: 0.25 });
  expect(edge.source).toBe(node1);
  expect(edge.target).toBe(node2);

  const routedPoint: RoutedPoint[] = siriusPolylineEdgeRouter.route(edge);
  expect(routedPoint).toHaveLength(2);
  expect(routedPoint[0].kind).toBe('source');
  expect(routedPoint[0].x).toBe(15);
  expect(routedPoint[0].y).toBeCloseTo(9.167, 3);

  expect(routedPoint[1].kind).toBe('target');
  expect(routedPoint[1].x).toBe(25);
  expect(routedPoint[1].y).toBeCloseTo(15.833, 3);
});
