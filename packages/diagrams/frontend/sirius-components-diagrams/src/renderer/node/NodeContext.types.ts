/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo and others.
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

import { Node } from 'reactflow';
import { NodeData } from '../DiagramRenderer.types';
export interface NodeContextValue {
  hoveredNode: Node<NodeData> | null;
  setHoveredNode: (node: Node<NodeData> | null) => void;
}

export interface NodeContextProviderProps {
  children: React.ReactNode;
}

export interface NodeContextProviderState {
  hoveredNode: Node<NodeData> | null;
}
