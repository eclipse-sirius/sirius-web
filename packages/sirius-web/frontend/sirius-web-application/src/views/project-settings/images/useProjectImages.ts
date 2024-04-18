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
import {
  GQLGetProjectImagesQueryData,
  GQLGetProjectImagesQueryVariables,
  UseProjectImagesValue,
} from './useProjectImages.types';

const getProjectImagesQuery = gql`
  query projectImages($projectId: ID!) {
    viewer {
      project(projectId: $projectId) {
        images {
          id
          label
          url
        }
      }
    }
  }
`;

export const useProjectImages = (projectId: string): UseProjectImagesValue => {
  const variables: GQLGetProjectImagesQueryVariables = {
    projectId,
  };

  const { data, loading, error, refetch } = useQuery<GQLGetProjectImagesQueryData, GQLGetProjectImagesQueryVariables>(
    getProjectImagesQuery,
    { variables }
  );

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  const refreshImages = () => refetch(variables);

  return { data: data ?? null, loading, refreshImages };
};
