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
import { GQLGetProjectQueryData, GQLGetProjectQueryVariables, UseProjectValue } from './useProject.types';

const getProjectQuery = gql`
  query getProject($projectId: ID!, $name: ID) {
    viewer {
      project(projectId: $projectId) {
        ...UseProjectFragment
      }
    }
  }
`;

export const useProject = (projectId: string, name: string | null): UseProjectValue => {
  const { data, loading, error } = useQuery<GQLGetProjectQueryData, GQLGetProjectQueryVariables>(getProjectQuery, {
    variables: { projectId, name },
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    data,
    loading,
  };
};
