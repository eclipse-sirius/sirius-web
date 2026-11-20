/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Node } from '@xyflow/react';
import { expect, it } from 'vitest';
import { NodeData } from '../../DiagramRenderer.types';
import { LayoutEngine } from '../LayoutEngine';

const measuredWidths = { top: 0.5, right: 1.5, bottom: 2, left: 0 };

it.each([
  {
    type: 'freeFormNode',
    name: 'uses measured borders for a non-header label',
    nodeBorderWidths: measuredWidths,
    width: 101.5,
    height: 22.5,
  },
  {
    type: 'listNode',
    name: 'uses measured borders for a non-header label',
    nodeBorderWidths: measuredWidths,
    width: 101.5,
    height: 22.5,
  },
  {
    type: 'freeFormNode',
    name: 'falls back to CSS border width when measurements are missing',
    nodeBorderWidths: null,
    width: 102,
    height: 22,
  },
] as const)('$type $name', ({ type, nodeBorderWidths, width, height }) => {
  const node: Node<NodeData, typeof type> = {
    id: 'node',
    type,
    position: { x: 0, y: 0 },
    data: {
      insideLabel: {
        isHeader: false,
        headerPosition: undefined,
        overflowStrategy: 'NONE',
        width: 100,
        height: 20,
      },
      style: { borderWidth: 1 },
      nodeBorderWidths,
      defaultWidth: 0,
      defaultHeight: 0,
    } as NodeData,
  };

  const nodes = [node];
  new LayoutEngine(() => null).layoutNodes(null, nodes, nodes, []);

  expect(node).toMatchObject({ width, height, data: { minComputedWidth: width, minComputedHeight: height } });
});

it('places a list child inside asymmetric borders and leaves room for the bottom header', () => {
  const list: Node<NodeData, 'listNode'> = {
    id: 'list',
    type: 'listNode',
    position: { x: 0, y: 0 },
    data: {
      insideLabel: {
        isHeader: true,
        headerPosition: 'BOTTOM',
        displayHeaderSeparator: true,
        overflowStrategy: 'NONE',
        width: 40,
        height: 20,
      },
      style: { borderWidth: 1 },
      nodeBorderWidths: measuredWidths,
      defaultWidth: 80,
      defaultHeight: 0,
      growableNodeIds: [],
      topGap: 3,
      bottomGap: 4,
    } as NodeData,
  };
  const child: Node<NodeData, 'freeFormNode'> = {
    id: 'child',
    type: 'freeFormNode',
    parentId: list.id,
    position: { x: 0, y: 0 },
    data: {
      ...list.data,
      insideLabel: null,
      outsideLabels: {},
      style: {},
      nodeBorderWidths: null,
      defaultWidth: 30,
      defaultHeight: 60,
    },
  };

  new LayoutEngine(() => null).layoutNodes(null, [list, child], [list], []);

  expect(child).toMatchObject({ position: { x: 0, y: 3.5 }, width: 78.5, height: 60 });
  expect(list).toMatchObject({ width: 80, height: 89.5, data: { minComputedHeight: 89.5 } });
});
