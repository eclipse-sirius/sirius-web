/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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

import { GQLKeyBinding } from '@eclipse-sirius/sirius-components-core';
import { PaletteExtensionSectionProps } from './PaletteExtensionSection.types';

export interface ContextualPaletteStyleProps {
  toolCount: number;
}

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

export interface XYPosition {
  x: number;
  y: number;
}
export interface PaletteStyleProps {
  paletteWidth: string;
  paletteHeight: string;
}

export interface GQLPalette {
  /**
   * @deprecated The `id` field is deprecated and should not be used. It will be removed in a future release.
   * See https://github.com/eclipse-sirius/sirius-web/issues/6470 for more details.
   */
  id: string;
  quickAccessTools: GQLTool[];
  paletteEntries: GQLPaletteEntry[];
}

export interface GQLTool extends GQLPaletteEntry {
  label: string;
  iconURL: string[];
  dialogDescriptionId: string;
  withImpactAnalysis: boolean;
  keyBindings: GQLKeyBinding[];
  __typename: string;
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
