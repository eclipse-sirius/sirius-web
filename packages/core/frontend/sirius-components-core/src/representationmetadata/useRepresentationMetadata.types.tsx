/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export interface UseRepresentationMetadataValue {
  data: GQLRepresentationMetadataQueryData | null;
}

export interface GQLRepresentationMetadataQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}
export interface GQLEditingContext {
  representations: GQLRepresentationMetadataConnection;
}

export interface GQLRepresentationMetadataConnection {
  edges: GQLRepresentationMetadataEdge[];
}

export interface GQLRepresentationMetadataEdge {
  node: GQLRepresentationMetadata;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}

export interface GQLRepresentationMetadataQueryVariables {
  editingContextId: string;
  representationIds: string[];
}
