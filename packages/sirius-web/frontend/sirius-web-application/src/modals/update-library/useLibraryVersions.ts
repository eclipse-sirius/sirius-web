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
import { gql, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetLibraryVersionsQueryData,
  GQLGetLibraryVersionsQueryVariables,
  UseLibraryVersionsValue,
} from './useLibraryVersions.types';

const getLibraryVersionsQuery = gql`
  query getLibraryVersions($namespace: String!, $name: String!, $version: String!, $page: Int!, $limit: Int!) {
    viewer {
      library(namespace: $namespace, name: $name, version: $version) {
        namespace
        name
        version
        versions(page: $page, limit: $limit) {
          edges {
            node {
              id
              namespace
              name
              version
              description
              createdOn
            }
          }
          pageInfo {
            hasNextPage
            hasPreviousPage
            startCursor
            endCursor
            count
          }
        }
      }
    }
  }
`;

export const useLibraryVersions = (
  namespace: string,
  name: string,
  version: string,
  page: number,
  limit: number
): UseLibraryVersionsValue => {
  const variables: GQLGetLibraryVersionsQueryVariables = {
    namespace,
    name,
    version,
    page,
    limit,
  };

  const { data, error, loading } = useQuery<GQLGetLibraryVersionsQueryData, GQLGetLibraryVersionsQueryVariables>(
    getLibraryVersionsQuery,
    {
      variables,
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return { data: data ?? null, loading };
};
