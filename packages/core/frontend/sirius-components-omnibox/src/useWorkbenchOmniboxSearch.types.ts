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

import { LazyQueryExecFunction } from '@apollo/client';
export interface UseWorkbenchOmniboxSearchValue {
  getWorkbenchOmniboxSearchResults: LazyQueryExecFunction<
    GQLGetWorkbenchOmniboxSearchResultsQueryData,
    GQLGetWorkbenchOmniboxSearchResultsQueryVariables
  >;
  loading: boolean;
  data: GQLGetWorkbenchOmniboxSearchResultsQueryData | null;
}

export interface GQLGetWorkbenchOmniboxSearchResultsQueryVariables {
  editingContextId: string;
  selectedObjectIds: string[];
  query: string;
}

export interface GQLGetWorkbenchOmniboxSearchResultsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  workbenchOmniboxSearch: GQLViewerOmniboxCommandsConnection;
}

export interface GQLViewerOmniboxCommandsConnection {
  edges: GQLViewerOmniboxCommandsEdge[];
  pageInfo: GQLPageInfo;
}

export interface GQLViewerOmniboxCommandsEdge {
  node: GQLOmniboxCommand;
}

export interface GQLOmniboxCommand {
  id: string;
  label: string;
  iconURLs: string[];
  description: string;
}

export interface GQLPageInfo {
  count: number;
}
