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

import {
  GQLgetProjectTemplatesQueryData,
  GQLgetProjectTemplatesQueryVariables,
  ProjectTemplateContext,
  UseProjectTemplatesValue,
} from './useProjectTemplates.types';

export const getProjectTemplatesQuery = gql`
  query getProjectTemplates($page: Int!, $limit: Int!, $context: ProjectTemplateContext!) {
    viewer {
      projectTemplates(page: $page, limit: $limit, context: $context) {
        edges {
          node {
            id
            label
            imageURL
          }
        }
        pageInfo {
          hasPreviousPage
          hasNextPage
          count
        }
      }
    }
  }
`;

export const useProjectTemplates = (
  page: number,
  limit: number,
  context: ProjectTemplateContext,
  skip?: boolean
): UseProjectTemplatesValue => {
  const variables: GQLgetProjectTemplatesQueryVariables = {
    page,
    limit,
    context,
  };
  const { data, loading, error } = useQuery<GQLgetProjectTemplatesQueryData, GQLgetProjectTemplatesQueryVariables>(
    getProjectTemplatesQuery,
    { variables, skip }
  );

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
