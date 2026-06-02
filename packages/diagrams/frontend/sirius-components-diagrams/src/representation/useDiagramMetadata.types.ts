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
export interface UseDiagramMetadataValue {
  loading: boolean;
  data: GQLGetRepresentationDiagramMetadataQueryData | null;
}

export interface GQLGetRepresentationDiagramMetadataQueryVariables {
  editingContextId: string;
  diagramId: string;
}

export interface GQLGetRepresentationDiagramMetadataQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  representation: GQLRepresentation | null;
}

export interface GQLRepresentation {
  label: string;
}
