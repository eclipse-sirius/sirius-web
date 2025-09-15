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
import { Node } from '@xyflow/react';

export interface UseRevealNodesType {
  /**
   * Make sure all the specified nodes are visible in the viewport.
   * Only adjusts the viewport if required to make them all fully visible.
   * @param nodesToReveal the nodes to make visible
   */
  revealNodes: (nodesToReveal: Node[]) => void;
}
