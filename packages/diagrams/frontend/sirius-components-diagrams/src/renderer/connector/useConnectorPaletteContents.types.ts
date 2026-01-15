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

import { GQLPalette } from './../palette/Palette.types';

export interface UseConnectorPaletteContentValue {
  connectorPalette: GQLPalette | null;
  loading: boolean;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  connectorPalette: GQLPalette;
}

export interface GQLRepresentationDescription {
  __typename: string;
}

export interface GQLGetConnectorPaletteVariables {
  editingContextId: string;
  diagramId: string;
  sourceDiagramElementId: string | null;
  targetDiagramElementId: string | null;
}

export interface GQLGetConnectorPaletteData {
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
