/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { GQLGetProjectsQueryData, GQLGetProjectsQueryVariables, UseProjectsValue } from './useProjects.types';

const getProjectsQuery = gql`
  query getProjects($after: String, $before: String, $first: Int, $last: Int) {
    viewer {
      ...ViewerProjects
    }
  }
`;

export const useProjects = (after: string | null, before: string | null, pageSize: number): UseProjectsValue => {
  const variables: GQLGetProjectsQueryVariables = {
    after,
    before,
    first: after ? pageSize : before ? null : pageSize,
    last: before ? pageSize : null,
  };
  const { data, loading, error, refetch } = useQuery<GQLGetProjectsQueryData, GQLGetProjectsQueryVariables>(
    getProjectsQuery,
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

  const refreshProjects = () => refetch(variables);

  return { data, loading, refreshProjects };
};
