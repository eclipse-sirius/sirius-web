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

import { PaletteExtensionSectionProps } from '@eclipse-sirius/sirius-components-palette';

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
  representationKind: string;
  representationElementIds: string[];
  palette: GQLPalette;
  paletteToolListExtensions: React.ReactElement<PaletteExtensionSectionProps>[];
  onToolClick: (tool: GQLTool) => void;
  onClose: () => void;
}

export interface PaletteState {
  searchToolValue: string;
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
  dialogDescriptionId: string;
  withImpactAnalysis: boolean;
  withDeletionConfirmationDialog: boolean;
  keyBindings: GQLKeyBinding[];
}

export interface GQLKeyBinding {
  isCtrl: boolean;
  isMeta: boolean;
  isAlt: boolean;
  key: string;
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
