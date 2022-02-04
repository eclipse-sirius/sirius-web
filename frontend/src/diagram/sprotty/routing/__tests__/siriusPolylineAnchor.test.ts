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
import { SNode } from 'sprotty';
import { Point } from 'sprotty-protocol';
import { SiriusRectangleAnchor } from '../siriusPolylineAnchor';

const createSimpleNode = (): SNode => {
  const node: SNode = new SNode();

  node.bounds = {
    x: 1,
    y: 1,
    width: 2,
    height: 2,
  };

  return node;
};

describe('Sirius polyline anchor', () => {
  it('gets the anchor from a line and the bounds of a node', () => {
    const node = createSimpleNode();
    const targetEndRefPoint: Point = { x: 5, y: 1.5 };
    const sourceEndRefPoint: Point = { x: 2.5, y: 1.5 };
    const siriusRectangleAnchor = new SiriusRectangleAnchor();
    const anchor = siriusRectangleAnchor.getAnchorBetweenTwoPoints(node, sourceEndRefPoint, targetEndRefPoint);

    expect(anchor).not.toBeNull();
    expect(anchor).not.toBeUndefined();

    expect(anchor.x).toBe(3);
    expect(anchor.y).toBe(1.5);
  });

  it('gets the on the north face of the node', () => {
    const node = createSimpleNode();
    const targetEndRefPoint: Point = { x: 2.5, y: 1.5 };

    const siriusRectangleAnchor = new SiriusRectangleAnchor();
    const anchor = siriusRectangleAnchor.getSelfLoopAnchor(node, targetEndRefPoint);

    expect(anchor).not.toBeNull();
    expect(anchor).not.toBeUndefined();

    expect(anchor.x).toBe(2.5);
    expect(anchor.y).toBe(1);
  });
});
