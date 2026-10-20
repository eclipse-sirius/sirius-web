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
import { GQLKeyBinding } from '@eclipse-sirius/sirius-components-core';
import { GQLPalette, GQLTool } from '@eclipse-sirius/sirius-components-palette';

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
