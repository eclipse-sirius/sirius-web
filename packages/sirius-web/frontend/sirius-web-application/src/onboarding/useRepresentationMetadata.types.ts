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

export interface UseRepresentationMetadataValue {
  data: GQLGetRepresentationMetadataData | null;
  loading: boolean;
}

export interface GQLGetRepresentationMetadataVariables {
  editingContextId: string;
  after: string | null;
  before: string | null;
  first: number | null;
  last: number | null;
}

export interface GQLGetRepresentationMetadataData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext | null;
}

export interface GQLEditingContext {
  representations: GQLEditingContextRepresentationsConnection;
}

export interface GQLEditingContextRepresentationsConnection {
  edges: GQLEditingContextRepresentationsEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLEditingContextRepresentationsEdge {
  node: GQLRepresentationMetadata;
  cursor: string;
}

export interface GQLRepresentationMetadata {
  id: string;
  label: string;
  kind: string;
  iconURLs: string[];
}

export interface GQLPageInfo {
  count: number;
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  startCursor: string;
  endCursor: string;
}
