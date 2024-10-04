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

export interface DraggablePaletteProps {
  x: number;
  y: number;
  onEscape?: () => void;
  paletteEntries: PaletteEntry[];
  onToolClick: (tool: Tool) => void;
  lastToolInvoked: Tool | null;
  quickAccessToolComponents: JSX.Element[];
}

export interface PaletteStyleProps {
  paletteWidth: string;
  paletteHeight: string;
}

export interface Tool extends PaletteEntry {
  label: string;
  iconURL: string[];
  iconElement?: JSX.Element;
  __typename: 'SingleClickOnDiagramElementTool' | 'SingleClickOnTwoDiagramElementsTool';
}

export interface PaletteEntry {
  id: string;
  __typename: string;
}
export interface PaletteDivider extends PaletteEntry {
  __typename: 'PaletteDivider';
}

export interface ToolSection extends PaletteEntry {
  label: string;
  iconURL: string[];
  tools: Tool[];
  __typename: 'ToolSection';
}
