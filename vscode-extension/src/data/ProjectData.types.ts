/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

export interface GQLGetRepresentationMetadataResponse {
  data: GQLGetRepresentationMetadataData;
}

export interface GQLGetRepresentationMetadataData {
  viewer: GQLGetRepresentationMetadataViewer;
}

export interface GQLGetRepresentationMetadataViewer {
  editingContext: GQLGetRepresentationMetadataEditingContext;
}

export interface GQLGetRepresentationMetadataEditingContext {
  representations: GQLGetRepresentationMetadataEditingContextConnexion;
}

export interface GQLGetRepresentationMetadataEditingContextConnexion {
  edges: GQLGetRepresentationMetadataEditingContextEdge[];
}

export interface GQLGetRepresentationMetadataEditingContextEdge {
  node: GQLGetRepresentationMetadataRepresentationMetadata;
}

export interface GQLGetRepresentationMetadataRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
}
