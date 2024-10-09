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

export interface ContextualPaletteStyleProps {
  toolCount: number;
}

export interface PaletteProps {
  x: number;
  y: number;
  diagramElementId: string;
  targetObjectId: string;
  onDirectEditClick: () => void;
  hideableDiagramElement?: boolean;
  onEscape?: () => void;
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
