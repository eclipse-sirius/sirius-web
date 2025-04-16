/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Edge, EdgeProps, InternalNode, Node, XYPosition } from '@xyflow/react';
import { NodeData } from '../../DiagramRenderer.types';

export type MultiLabelEditableEdgeProps<T extends Edge<Record<string, unknown>, string | undefined>> = {
  bendingPoints: XYPosition[];
  customEdge: boolean;
  sourceNode: InternalNode<Node<NodeData>>;
  targetNode: InternalNode<Node<NodeData>>;
} & EdgeProps<T>;

export type XYPositionSetter = (source: XYPosition | ((prevState: XYPosition) => XYPosition)) => void;
