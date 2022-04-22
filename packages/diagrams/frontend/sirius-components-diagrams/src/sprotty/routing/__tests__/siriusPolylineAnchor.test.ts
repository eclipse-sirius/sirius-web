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
import { SModelRoot, SNode, SParentElement } from 'sprotty';
import { Point } from 'sprotty-protocol';
import { expect, test } from 'vitest';
import { Edge, Ratio } from '../../Diagram.types';
import { SiriusRectangleAnchor } from '../siriusPolylineAnchor';

const DEFAULT_ANCHOR_RATIO = { x: 0.5, y: 0.5 };

const createSimpleNode = (parent?: SParentElement): SNode => {
  const node: SNode = new SNode();
  node.id = 'SimpleNode';

  node.bounds = {
    x: 1,
    y: 1,
    width: 2,
    height: 2,
  };
  if (!!parent) {
    parent.add(node);
  }

  return node;
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

test('gets the anchor from a line and the bounds of a node', () => {
  const node = createSimpleNode();
  const targetEndRefPoint: Point = { x: 5, y: 1.5 };
  const sourceEndRefPoint: Point = { x: 2.5, y: 1.5 };
  const siriusRectangleAnchor = new SiriusRectangleAnchor();
  const anchor = siriusRectangleAnchor.getSiriusAnchor(node, sourceEndRefPoint, targetEndRefPoint);

  expect(anchor).not.toBeNull();
  expect(anchor).not.toBeUndefined();

  expect(anchor.x).toBe(3);
  expect(anchor.y).toBe(1.5);
});

test('gets the anchor on the north face of the node', () => {
  const node = createSimpleNode();
  const targetEndRefPoint: Point = { x: 2.5, y: 1.5 };

  const siriusRectangleAnchor = new SiriusRectangleAnchor();
  const anchor = siriusRectangleAnchor.getSelfLoopAnchor(node, targetEndRefPoint);

  expect(anchor).not.toBeNull();
  expect(anchor).not.toBeUndefined();

  expect(anchor.x).toBe(2.5);
  expect(anchor.y).toBe(1);
});

test('updates the anchor of an edge end regarding the position in the new reconnected element', () => {
  const modelRoot: SModelRoot = new SModelRoot();
  const reconnectedNode = createSimpleNode(modelRoot);
  const edge = createEdge('', reconnectedNode.id, modelRoot);

  const reconnectPosition: Point = { x: 1.5, y: 1.5 };
  const siriusRectangleAnchor = new SiriusRectangleAnchor();
  siriusRectangleAnchor.updateAnchor(reconnectedNode, edge, reconnectPosition);

  expect(edge.targetAnchorRelativePosition).toEqual<Ratio>({ x: 0.25, y: 0.25 });
});
