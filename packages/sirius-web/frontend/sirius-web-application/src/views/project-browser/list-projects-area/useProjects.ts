/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

export const useProjects = (after: string, before: string, first: number, last: number): UseProjectsValue => {
  const variables: GQLGetProjectsQueryVariables = {
    after,
    before,
    first,
    last,
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

export const useProjectsAfter = (after: string | null, first: number): UseProjectsValue => {
  const variables: GQLGetProjectsQueryVariables = {
    after,
    before: null,
    first,
    last: null,
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

export const useProjectsBefore = (before: string | null, last: number): UseProjectsValue => {
  const variables: GQLGetProjectsQueryVariables = {
    after: null,
    before,
    first: null,
    last,
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
