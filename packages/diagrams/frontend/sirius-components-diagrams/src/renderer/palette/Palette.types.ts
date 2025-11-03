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

import { PaletteExtensionSectionProps } from '@eclipse-sirius/sirius-components-palette';
import { XYPosition } from '@xyflow/react';

export interface ContextualPaletteStyleProps {
  toolCount: number;
}

export interface GQLTool {
  id: string;
  label: string;
  iconURL: string[];
  __typename: string;
}

export interface GQLToolVariable {
  name: string;
  value: string;
  type: GQLToolVariableType;
}

export type GQLToolVariableType = 'STRING' | 'OBJECT_ID' | 'OBJECT_ID_ARRAY';

export interface PaletteProps {
  x: number;
  y: number;
  palette: GQLPalette;
  diagramElementIds: string[];
  onToolClick: (tool: GQLTool) => void;
  onClose: () => void;
  paletteToolListExtensions: React.ReactElement<PaletteExtensionSectionProps>[];
}

export interface PaletteState {
  searchToolValue: string;
  controlledPosition: XYPosition;
}

export interface PaletteStyleProps {
  paletteWidth: string;
  paletteHeight: string;
}

export interface GQLTool extends GQLPaletteEntry {
  label: string;
  iconURL: string[];
  __typename: string;
}

export interface GQLSingleClickOnDiagramElementTool extends GQLTool {
  appliesToDiagramRoot: boolean;
  dialogDescriptionId: string;
  withImpactAnalysis: boolean;
}

export interface GQLPalette {
  id: string;
  quickAccessTools: GQLTool[];
  paletteEntries: GQLPaletteEntry[];
}

export interface GQLPaletteEntry {
  id: string;
  __typename: string;
}

export interface GQLPaletteDivider extends GQLPaletteEntry {}

export interface GQLToolSection extends GQLPaletteEntry {
  label: string;
  iconURL: string[];
  tools: GQLTool[];
}
