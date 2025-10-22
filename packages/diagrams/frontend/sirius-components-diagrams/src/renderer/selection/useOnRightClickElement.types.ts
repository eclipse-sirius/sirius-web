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

import { Edge, EdgeMouseHandler, Node, NodeMouseHandler, XYPosition } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

export interface UseOnRightClickElementValue {
  onPaneContextMenu: (event: React.MouseEvent | MouseEvent) => void;
  onEdgeContextMenu: EdgeMouseHandler<Edge<EdgeData>>;
  onNodeContextMenu: NodeMouseHandler<Node<NodeData>>;
  onSelectionContextMenu: (event: React.MouseEvent, nodes: Node<NodeData>[]) => void;
  groupPalettePosition: XYPosition | null;
  isGroupPaletteOpened: boolean;
  groupPaletteRefElementId: string | null;
  hideGroupPalette: () => void;
}
