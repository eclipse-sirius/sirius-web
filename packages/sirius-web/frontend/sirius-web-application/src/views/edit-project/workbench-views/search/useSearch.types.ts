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

export interface SearchQuery {
  text: string;
  matchCase: boolean;
  matchWholeWord: boolean;
  useRegularExpression: boolean;
  searchInAttributes: boolean;
}

export interface UseSearchValue {
  launchSearch: (editingContextId: string, query: SearchQuery) => void;
  loading: boolean;
  data: GQLGetSearchResultsQueryData | null;
}

export interface GQLGetSearchResultsQueryVariables {
  editingContextId: string;
  query: GQLSearchQuery;
}

export interface GQLSearchQuery {
  text: string;
  matchCase: boolean;
  matchWholeWord: boolean;
  useRegularExpression: boolean;
  searchInAttributes: boolean;
}

export interface GQLGetSearchResultsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  editingContext: GQLEditingContext;
}

export interface GQLEditingContext {
  search: GQLSearchPayload;
}

export interface GQLSearchPayload {
  __typename: string;
}

export interface GQLSearchSuccessPayload extends GQLSearchPayload {
  result: GQLSearchResult;
}

export interface GQLSearchResult {
  matches: GQLObject[];
}

export interface GQLObject {
  id: string;
  kind: string;
  label: string;
  iconURLs: string[];
}
