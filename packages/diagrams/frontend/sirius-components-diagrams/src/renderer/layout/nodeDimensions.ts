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
import { NodeData } from '../DiagramRenderer.types';
import { getDefaultOrMinHeight, getDefaultOrMinWidth } from './layoutNode';

export const computeNodeWidth = (
  node: Node<NodeData>,
  minComputedWidth: number,
  previousWidth: number,
  forcedWidth: number | null | undefined
): number => {
  if (node.data.resizedByUser && !forcedWidth) {
    return minComputedWidth > previousWidth ? minComputedWidth : previousWidth;
  }
  return forcedWidth ?? getDefaultOrMinWidth(minComputedWidth, node);
};

export const computeNodeHeight = (
  node: Node<NodeData>,
  minComputedHeight: number,
  previousHeight: number,
  forcedHeight: number | null | undefined,
  removedChildrenHeight: number = 0
): number => {
  if (node.data.resizedByUser && !forcedHeight) {
    // Compare before removing children to preserve the list's height adjustment.
    return minComputedHeight > previousHeight ? minComputedHeight : previousHeight - removedChildrenHeight;
  }
  return forcedHeight ?? getDefaultOrMinHeight(minComputedHeight, node);
};
