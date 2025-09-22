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

import { PaletteExtensionSectionProps } from './PaletteExtensionSection.types';

export interface ContextualPaletteStyleProps {
  toolCount: number;
}

export interface PaletteState {
  searchToolValue: string;
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
  paletteXYPosition: XYPosition;
  representationElementId: string;
  palette: GQLPalette;
  lastToolInvoked: GQLTool | null;
  onToolClick: (
    toolExecutedPosition: XYPosition,
    representationElementId: string,
    onDirectEditClick: () => void,
    tool: GQLTool
  ) => void;
  onDirectEditClick: () => void;
  onClose: () => void;
  extensions: React.ReactElement<PaletteExtensionSectionProps>[];
  //The props below are here to ensure compatibility with react draggable
  style?: React.CSSProperties;
  className?: string | undefined;
  onMouseDown?: React.MouseEventHandler<HTMLDivElement> | undefined;
  onMouseUp?: React.MouseEventHandler<HTMLDivElement> | undefined;
}

export interface XYPosition {
  x: number;
  y: number;
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
