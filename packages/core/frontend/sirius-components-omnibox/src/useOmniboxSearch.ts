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
  GQLGetOmniboxSearchResultsQueryData,
  GQLGetOmniboxSearchResultsQueryVariables,
  UseOmniboxSearchValue,
} from './useOmniboxSearch.types';

const getOmniboxSearchResultsQuery = gql`
  query getOmniboxSearchResults($contextEntries: [OmniboxContextEntry!]!, $query: String!) {
    viewer {
      omniboxSearch(contextEntries: $contextEntries, query: $query) {
        id
        kind
        label
        iconURLs
      }
    }
  }
`;

export const useOmniboxSearch = (): UseOmniboxSearchValue => {
  const [getOmniboxSearchResults, { loading, data, error }] = useLazyQuery<
    GQLGetOmniboxSearchResultsQueryData,
    GQLGetOmniboxSearchResultsQueryVariables
  >(getOmniboxSearchResultsQuery);

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    getOmniboxSearchResults,
    loading,
    data: data ?? null,
  };
};
