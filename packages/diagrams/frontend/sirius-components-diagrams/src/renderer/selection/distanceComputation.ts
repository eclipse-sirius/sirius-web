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

import type { Box, InternalNode, Node, XYPosition } from '@xyflow/react';
import { nodeToBox } from '@xyflow/system';
import { NodeData } from '../DiagramRenderer.types';

/**
 * Measures the squared distance between a point and the border of an axis-aligned box (AABB).
 *
 * The result is squared to avoid a square root: it preserves ordering, so it can be
 * used to compare distances or find a minimum, but it is not a distance in pixels.
 *
 * The distance is measured to the *border*:
 * it is zero only on the border itself, and strictly positive both inside and
 * outside.
 *
 * @param point the point, in the same coordinate system as the box
 * @param box an axis-aligned box, i.e. one whose sides are parallel to the axes
 * @returns the squared distance from the point to the nearest side of the box
 */
export function squaredDistancePointToBoxBorder(point: XYPosition, box: Box): number {
  const dx = Math.max(box.x - point.x, point.x - box.x2);
  const dy = Math.max(box.y - point.y, point.y - box.y2);

  if (dx > 0 || dy > 0) {
    // Outside on at least one axis
    const overflowX = Math.max(dx, 0);
    const overflowY = Math.max(dy, 0);
    return overflowX * overflowX + overflowY * overflowY;
  }

  // Inside: dx and dy are both negative
  const insideDistance = Math.max(dx, dy);
  return insideDistance * insideDistance;
}

/**
 * Returns the node whose border is the closest to the given point.
 *
 * Nodes are compared by the distance to their border rather than by the distance to
 * the area they cover.
 * As a consequence, a point lying in the empty area of a container selects a child
 * of that container as soon as it is closer to the child than to the borders of the
 * container.
 *
 * Note that nodes are given as internal nodes because the comparison is done in
 * absolute coordinates, whereas the position of a node is relative to its parent.
 *
 * @param point a point in absolute (flow) coordinates
 * @param nodes the nodes to compare, in any order
 * @returns the closest node, or null if no node was given
 */
export function findClosest(point: XYPosition, nodes: InternalNode<Node<NodeData>>[]): Node<NodeData> | null {
  let best: Node<NodeData> | null = null;
  let bestDistanceSquared = Number.POSITIVE_INFINITY;

  for (const node of nodes) {
    const box: Box = nodeToBox(node);
    const distanceSquared = squaredDistancePointToBoxBorder(point, box);
    if (distanceSquared < bestDistanceSquared) {
      bestDistanceSquared = distanceSquared;
      best = node;
    }
  }

  return best;
}
