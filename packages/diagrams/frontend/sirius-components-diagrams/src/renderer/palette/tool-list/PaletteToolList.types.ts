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

import { Edge, InternalNode, Node } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { GQLPalette, GQLTool, GQLToolSection } from '../Palette.types';

export interface PaletteToolListProps {
  onToolClick: (tool: GQLTool) => void;
  palette: GQLPalette;
  onBackToMainList: () => void;
  diagramElement: Edge<EdgeData> | InternalNode<Node<NodeData>>;
}

export const APPEARANCE_SECTION_ID = 'appearance';

export interface AppearanceSection {
  id: string;
  label: string;
}

export const AppearanceSectionValue: AppearanceSection = {
  id: APPEARANCE_SECTION_ID,
  label: 'Appearance',
};

export interface PaletteToolListStateValue {
  toolSection: GQLToolSection | AppearanceSection | null;
}
