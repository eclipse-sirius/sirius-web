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
import { NodeData } from '../../DiagramRenderer.types';

export interface UseProcessLayoutToolValue {
  processLayoutTool: ProcessLayoutTool;
}

export type ProcessLayoutTool = (
  selectedNodeIds: string[],
  layoutFn: (selectedNodes: Node<NodeData>[], refNode: Node<NodeData>) => Node<NodeData>[],
  sortFn: ShortFn,
  refElementId: string | null
) => void;

export type ShortFn = ((node1: Node, node2: Node) => number) | null;
