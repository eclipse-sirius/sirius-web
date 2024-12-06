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

import { Edge, Node } from '@xyflow/react';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { DiagramPaletteToolContributionComponentProps } from '../extensions/DiagramPaletteToolContribution.types';
import { GQLTool } from '../Palette.types';

export interface UseSingleElementQuickAccessToolProps {
  x: number;
  y: number;
  diagramElementId: string;
  hideableDiagramElement?: boolean;
  paletteToolComponents: React.ComponentType<DiagramPaletteToolContributionComponentProps>[];
  onToolClick: (tool: GQLTool) => void;
  quickAccessTools: GQLTool[];
  diagramElement: Node<NodeData> | Edge<EdgeData> | null;
}

export interface UseSingleElementQuickAccessToolValue {
  quickAccessToolComponents: JSX.Element[];
}
