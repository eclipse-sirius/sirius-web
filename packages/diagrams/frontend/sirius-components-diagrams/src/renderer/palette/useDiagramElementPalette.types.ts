/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { Edge, Node } from '@xyflow/react';
import { EdgeData, NodeData } from '../DiagramRenderer.types';

export interface UseDiagramElementPaletteValue {
  x: number | null;
  y: number | null;
  isOpened: boolean;
  hideDiagramElementPalette: () => void;
  showDiagramElementPalette: (x: number, y: number) => void;
  onDiagramElementClick: (
    event: React.MouseEvent<Element, MouseEvent>,
    elementClicked: Node<NodeData> | Edge<EdgeData>
  ) => void;
}
