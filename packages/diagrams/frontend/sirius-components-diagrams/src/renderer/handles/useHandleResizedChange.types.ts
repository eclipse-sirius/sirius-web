/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { Node, NodeChange } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';
import { DiagramNodeType } from '../node/NodeTypes.types';

export interface UseHandleResizedChangeValue {
  applyResizeHandleChange: (
    changes: NodeChange<Node<NodeData>>[],
    nodes: Node<NodeData, DiagramNodeType>[]
  ) => Node<NodeData, DiagramNodeType>[];
}

export type UseHandleResizedChangeState = {
  initialWidth: number | null;
  initialHeight: number | null;
  finalWidth: number | null;
  finalHeight: number | null;
};
