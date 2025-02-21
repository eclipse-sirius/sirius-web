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

export interface UseOmniboxSearchValue {
  getOmniboxSearchResults: LazyQueryExecFunction<
    GQLGetOmniboxSearchResultsQueryData,
    GQLGetOmniboxSearchResultsQueryVariables
  >;
  loading: boolean;
  data: GQLGetOmniboxSearchResultsQueryData | null;
}

export interface GQLGetOmniboxSearchResultsQueryVariables {
  editingContextId: string;
  selectedObjectIds: string[];
  query: string;
}

export interface GQLGetOmniboxSearchResultsQueryData {
  viewer: GQLViewer;
}

export interface GQLViewer {
  omniboxSearch: GQLObject[];
}

export interface GQLObject {
  id: string;
  kind: string;
  label: string;
  iconURLs: string[];
}
