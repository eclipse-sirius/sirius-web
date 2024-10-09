/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { NodeData } from '../../DiagramRenderer.types';
import { DiagramPaletteToolContributionComponentProps } from '../DiagramPaletteToolContribution.types';
import { GQLPalette, GQLTool } from '../Palette.types';

export interface PaletteQuickAccessToolBarProps {
  x: number;
  y: number;
  diagramElementId: string;
  hideableDiagramElement?: boolean;
  paletteToolComponents: React.ComponentType<DiagramPaletteToolContributionComponentProps>[];
  onToolClick: (tool: GQLTool) => void;
  palette: GQLPalette | null;
  node: Node<NodeData> | undefined;
}
