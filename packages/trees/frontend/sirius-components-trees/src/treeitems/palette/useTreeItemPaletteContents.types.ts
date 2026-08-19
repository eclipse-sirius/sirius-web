/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { GQLTool } from '@eclipse-sirius/sirius-components-palette';

export interface UsePaletteContentValue {
  palette: GQLPalette | null;
  loading: boolean;
}

export interface GQLTreeDescription extends GQLRepresentationDescription {
  palette: GQLPalette;
}

export interface GQLRepresentationDescription {
  __typename: string;
}

export interface GQLGetPaletteVariables {
  editingContextId: string;
  representationId: string;
  treeItemId: string;
}

export interface GQLGetPaletteData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  representation: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  description: GQLRepresentationDescription;
}

export interface GQLSingleClickTreeItemTool extends GQLTool {
  keyBindings: GQLKeyBinding[];
  withImpactAnalysis: boolean;
}

export interface GQLFetchTreeItemTool extends GQLTool {
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
