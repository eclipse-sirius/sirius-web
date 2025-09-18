/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { GQLPalette } from '@eclipse-sirius/sirius-components-palette';

export interface UsePaletteContentValue {
  palette: GQLPalette | null;
  loading: boolean;
}

export interface GQLDiagramDescription extends GQLRepresentationDescription {
  palette: GQLPalette;
}

export interface GQLRepresentationDescription {
  __typename: string;
}

export interface GQLGetToolSectionsVariables {
  editingContextId: string;
  diagramId: string;
  diagramElementId: string;
}

export interface GQLGetToolSectionsData {
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
