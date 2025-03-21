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
import { useData, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import {
  GQLGetProjectsQueryData,
  GQLGetProjectsQueryVariables,
  GQLProjectFilter,
  UseProjectsValue,
} from './useProjects.types';
import { projectFilterCustomizersExtensionPoint } from './useProjectsExtensionPoints';

const getProjectsQuery = gql`
  query getProjects($after: String, $before: String, $first: Int, $last: Int, $filter: ProjectFilter) {
    viewer {
      ...ViewerProjects
    }
  }
`;

export const useProjects = (
  after: string | null,
  before: string | null,
  pageSize: number,
  projectFilter: GQLProjectFilter | null
): UseProjectsValue => {
  let filter: GQLProjectFilter = projectFilter ? { ...projectFilter } : {};

  const { data: projectFilterCustomizers } = useData(projectFilterCustomizersExtensionPoint);

  projectFilterCustomizers.forEach((customizer) => {
    filter = customizer.customize(filter);
  });

  const variables: GQLGetProjectsQueryVariables = {
    after,
    before,
    first: after ? pageSize : before ? null : pageSize,
    last: before ? pageSize : null,
    filter,
  };
  const {
    data: projectData,
    loading,
    error,
    refetch,
  } = useQuery<GQLGetProjectsQueryData, GQLGetProjectsQueryVariables>(getProjectsQuery, {
    variables,
  });
  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const refreshProjects = () => refetch(variables);

  return { data: projectData, loading, refreshProjects };
};
