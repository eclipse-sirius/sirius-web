/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { CoordinateExtent, Node } from 'reactflow';
import { BorderNodePositon, NodeData } from '../DiagramRenderer.types';

export const isEastBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePositon.EAST;
};
export const isWestBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePositon.WEST;
};
export const isNorthBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePositon.NORTH;
};
export const isSouthBorderNode = (borderNode: Node<NodeData>): boolean => {
  return borderNode.data.isBorderNode && borderNode.data.borderNodePosition === BorderNodePositon.SOUTH;
};

export const getBorderNodeExtent = (
  nodeParent: Node<NodeData>,
  borderNorde: Node<NodeData>
): CoordinateExtent | 'parent' => {
  let coordinateExtent: CoordinateExtent | 'parent' = 'parent';
  if (nodeParent.width && nodeParent.height && borderNorde.height && borderNorde.width) {
    coordinateExtent = [
      [0 - borderNorde.width, 0 - borderNorde.height],
      [nodeParent.width, nodeParent.height],
    ];
  }
  return coordinateExtent;
};
