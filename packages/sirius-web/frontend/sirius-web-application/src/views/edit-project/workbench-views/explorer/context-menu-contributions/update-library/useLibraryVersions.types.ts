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

export interface UseLibraryVersionsValue {
  data: GQLGetLibraryVersionsQueryData | null;
  loading: boolean;
}

export interface GQLGetLibraryVersionsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  library: GQLLibrary | null;
}

export interface GQLLibrary {
  namespace: string;
  name: string;
  version: string;
  versions: GQLLibraryVersionsConnection;
}

export interface GQLLibraryVersionsConnection {
  edges: GQLLibraryVersionsEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLLibraryVersionsEdge {
  node: GQLLibraryVersion;
}

export interface GQLLibraryVersion {
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

export interface GQLGetLibraryVersionsQueryVariables {
  name: string;
  namespace: string;
  version: string;
  page: number;
  limit: number;
}
