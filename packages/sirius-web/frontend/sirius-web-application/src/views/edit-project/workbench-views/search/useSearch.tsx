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
  GQLGetSearchResultsQueryData,
  GQLGetSearchResultsQueryVariables,
  SearchQuery,
  UseSearchValue,
} from './useSearch.types';

const searchQuery = gql`
  query search($editingContextId: ID!, $query: SearchQuery!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        search(query: $query) {
          ... on SearchSuccessPayload {
            result {
              matches {
                object {
                  id
                  label
                  iconURLs
                }
              }
            }
          }
        }
      }
    }
  }
`;

export const useSearch = (): UseSearchValue => {
  const [fetchSearchResults, { loading, data, error }] = useLazyQuery<
    GQLGetSearchResultsQueryData,
    GQLGetSearchResultsQueryVariables
  >(searchQuery);

  const launchSearch = (editingContextId: string, query: SearchQuery) => {
    fetchSearchResults({ variables: { editingContextId, query } });
  };

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    launchSearch,
    loading,
    data: data ?? null,
  };
};
