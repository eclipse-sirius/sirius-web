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

import { gql, useLazyQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetWorkbenchOmniboxSearchResultsQueryData,
  GQLGetWorkbenchOmniboxSearchResultsQueryVariables,
  UseWorkbenchOmniboxSearchValue,
} from './useWorkbenchOmniboxSearch.types';

const getWorkbenchOmniboxSearchResultsQuery = gql`
  query getWorkbenchOmniboxSearchResults($editingContextId: ID!, $selectedObjectIds: [ID!]!, $query: String!) {
    viewer {
      workbenchOmniboxSearch(
        editingContextId: $editingContextId
        selectedObjectIds: $selectedObjectIds
        query: $query
      ) {
        edges {
          node {
            id
            label
            iconURLs
            description
            __typename
          }
        }
        pageInfo {
          count
        }
      }
    }
  }
`;

export const useWorkbenchOmniboxSearch = (): UseWorkbenchOmniboxSearchValue => {
  const [getWorkbenchOmniboxSearchResults, { loading, data, error }] = useLazyQuery<
    GQLGetWorkbenchOmniboxSearchResultsQueryData,
    GQLGetWorkbenchOmniboxSearchResultsQueryVariables
  >(getWorkbenchOmniboxSearchResultsQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    getWorkbenchOmniboxSearchResults,
    loading,
    data: data ?? null,
  };
};
