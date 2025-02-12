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
import { GQLGetLibrariesQueryData, GQLGetLibrariesQueryVariables, UseLibrariesValue } from './useLibraries.types';

const getLibrariesQuery = gql`
  query getLibraries($page: Int!, $limit: Int!) {
    viewer {
      ...ViewerLibraries
    }
  }
`;

export const useLibraries = (page: number, limit: number): UseLibrariesValue => {
  const variables: GQLGetLibrariesQueryVariables = {
    page,
    limit,
  };
  const { data, loading, error, refetch } = useQuery<GQLGetLibrariesQueryData, GQLGetLibrariesQueryVariables>(
    getLibrariesQuery,
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

  const refreshLibraries = (page: number, limit: number) => refetch({ page, limit });

  return {
    data: data ?? null,
    loading,
    refreshLibraries,
  };
};
