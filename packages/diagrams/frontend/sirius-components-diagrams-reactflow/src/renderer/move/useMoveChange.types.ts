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
import { NodeChange, Node } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';

export interface UseMoveChangeValue {
  transformUndraggableListNodeChanges: (changes: NodeChange[]) => NodeChange[];
  applyMoveChange: (changes: NodeChange[], nodes: Node<NodeData>[]) => Node<NodeData>[];
}
