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
import { Point } from 'sprotty-protocol/';
import { expect, test } from 'vitest';
import { convertDiagram } from '../convertDiagram';
import { BorderNode } from '../Diagram.types';
import { SiriusDragAndDropMouseListener } from '../siriusDragAndDropMouseListener';
import { siriusWebDiagram } from './siriusWebDiagram';

const httpOrigin = 'http://localhost';

test('snaps the border node', () => {
  const sprottyDiagram = convertDiagram(siriusWebDiagram, httpOrigin, false);

  const sprottyNode: SNode = <SNode>sprottyDiagram.children[1];

  const borderNode: BorderNode = <BorderNode>sprottyNode.children.filter((value) => {
    return value instanceof BorderNode;
  })[0];

  // border node position when its center is positionned at the parent upper left corner(origin))
  const borderNodeOrigin: Point = { x: -borderNode.size.width / 2, y: -borderNode.size.height / 2 };
  const parentWidth: number = sprottyNode.size.width;
  const parentHeight: number = sprottyNode.size.height;

  const siriusDragAndDropMouseListener: SiriusDragAndDropMouseListener = new SiriusDragAndDropMouseListener();
  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x - 10, y: borderNodeOrigin.y - 5 }, borderNode, false)
  ).toStrictEqual({ x: -52, y: -20 });
  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x - 10, y: borderNodeOrigin.y - 15 }, borderNode, false)
  ).toStrictEqual({ x: -30, y: -32 });
  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x + 50, y: borderNodeOrigin.y - 25 }, borderNode, false)
  ).toStrictEqual({ x: 20, y: -32 });
  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x + 50, y: borderNodeOrigin.y + 5 }, borderNode, false)
  ).toStrictEqual({ x: 20, y: -32 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth + 5, y: borderNodeOrigin.y - 10 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 250, y: -32 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth + 15, y: borderNodeOrigin.y - 10 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 272, y: -20 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth + 15, y: borderNodeOrigin.y + 50 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 272, y: 30 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth - 5, y: borderNodeOrigin.y + 50 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 272, y: 30 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth + 15, y: borderNodeOrigin.y + parentHeight + 10 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 272, y: 160 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth + 10, y: borderNodeOrigin.y + parentHeight + 15 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 250, y: 172 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth / 2, y: borderNodeOrigin.y + parentHeight - 5 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 110, y: 172 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + parentWidth / 2, y: borderNodeOrigin.y + parentHeight + 5 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: 110, y: 172 });

  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x - 5, y: borderNodeOrigin.y + 10 }, borderNode, false)
  ).toStrictEqual({ x: -52, y: -10 });
  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x - 10, y: borderNodeOrigin.y + 5 }, borderNode, false)
  ).toStrictEqual({ x: -52, y: -15 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x + 10, y: borderNodeOrigin.y + parentHeight / 2 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: -52, y: 70 });
  expect(
    siriusDragAndDropMouseListener.snap(
      { x: borderNodeOrigin.x - 10, y: borderNodeOrigin.y + parentHeight / 2 },
      borderNode,
      false
    )
  ).toStrictEqual({ x: -52, y: 70 });
  expect(
    siriusDragAndDropMouseListener.snap({ x: borderNodeOrigin.x - 10, y: borderNodeOrigin.y - 5 }, borderNode, false)
  ).toStrictEqual({ x: -52, y: -20 });
});
