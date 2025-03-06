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

export interface UseLibrariesValue {
  data: GQLGetLibrariesQueryData | null;
  loading: boolean;
}

export interface GQLGetLibrariesQueryVariables {
  page: number;
  limit: number;
}

export interface GQLGetLibrariesQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  libraries: GQLViewerLibraryConnection;
}

export interface GQLViewerLibraryConnection {
  edges: GQLViewerLibraryEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLViewerLibraryEdge {
  node: GQLLibrary;
}

export interface GQLLibrary {
  id: string;
  namespace: string;
  name: string;
  version: string;
  description: string;
  createdOn: string;
}

export interface GQLPageInfo {
  hasPreviousPage: boolean;
  hasNextPage: boolean;
  count: number;
}
