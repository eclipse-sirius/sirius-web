/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import { Dimensions, Node, XYPosition } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
import { defaultHeight, defaultWidth } from './layoutParams';

export const computePreviousPosition = (
  previousNode: Node<NodeData, string> | undefined,
  node: Node<NodeData, string>
): XYPosition | null => {
  let previousPosition: XYPosition | null = null;
  if (node.data.isNew) {
    /*
      We don't have any layout data for the node, the node brand new.
      The node may have some position since the position is required by ReactFlow but the converter was forced to put {x: 0, y: 0}.
      We won't consider this position as relevant since the node is new.
      */

    previousPosition = null;
  } else if (previousNode) {
    /*
      We have some layout data returned from the server and the node already exists in memory.
      We are in the case of a refreshed diagram which we had already in our memory.
      We will thus use the data from the previousNode from our memory.
      */

    previousPosition = previousNode.position;
  } else {
    /*
      We have a node with some layout data from the server but we do not have a previous node in memory.
      This node already existed and it has been laid out in the past but we have never seen it.
      We must be receiving the diagram with those layout data for the first time.
      We will thus consider the position of the node which has just been converted with its server layout data.
      */

    previousPosition = node.position;
  }
  return previousPosition;
};

export const computePreviousSize = (
  previousNode: Node<NodeData, string> | undefined,
  node: Node<NodeData, string>
): Dimensions => {
  let previousDimensions: Dimensions;
  const nodeDefaultHeight: number = node.data.defaultHeight ?? defaultHeight;
  const nodeDefaultWidth: number = node.data.defaultWidth ?? defaultWidth;

  if (node.data.isNew) {
    previousDimensions = {
      height: nodeDefaultHeight,
      width: nodeDefaultWidth,
    };
  } else if (previousNode) {
    previousDimensions = {
      height: previousNode.height ?? nodeDefaultHeight,
      width: previousNode.width ?? nodeDefaultWidth,
    };
  } else {
    previousDimensions = {
      height: node.height ?? nodeDefaultHeight,
      width: node.width ?? nodeDefaultWidth,
    };
  }

  return previousDimensions;
};
