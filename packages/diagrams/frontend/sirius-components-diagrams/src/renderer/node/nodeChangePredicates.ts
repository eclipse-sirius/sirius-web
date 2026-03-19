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
import { Node, NodeChange, NodeDimensionChange, NodePositionChange } from '@xyflow/react';
import { NodeData } from '../DiagramRenderer.types';

export const isResizing = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange =>
  change.type === 'dimensions' && (change.resizing ?? false);
export const isResize = (change: NodeChange<Node<NodeData>>): change is NodeDimensionChange =>
  change.type === 'dimensions';
export const isMoving = (change: NodeChange<Node<NodeData>>): change is NodePositionChange =>
  change.type === 'position' && typeof change.dragging === 'boolean' && change.dragging;
export const isMove = (change: NodeChange<Node<NodeData>>): change is NodePositionChange =>
  change.type === 'position' && !change.dragging;
