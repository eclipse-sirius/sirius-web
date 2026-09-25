/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
  GQLGetProjectStyleCustomizationsQueryData,
  GQLGetProjectStyleCustomizationsQueryVariables,
  UseProjectStyleCustomizationsValue,
} from './useProjectStyleCustomizations.types';

const getProjectStyleCustomizations = gql`
  query getProjectStyleCustomizations($projectId: ID!, $after: String, $before: String, $first: Int, $last: Int) {
    viewer {
      project(projectId: $projectId) {
        styleCustomizations(after: $after, before: $before, first: $first, last: $last) {
          ...ProjectStyleCustomizationsConnectionFragment
        }
      }
    }
  }

  fragment ProjectStyleCustomizationsConnectionFragment on ProjectStyleCustomizationsConnection {
    edges {
      node {
        id
        name
        description
        enabled
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
`;

export const useProjectStyleCustomizations = (
  projectId: string,
  after: string | null,
  before: string | null,
  pageSize: number
): UseProjectStyleCustomizationsValue => {
  const variables = {
    projectId,
    after,
    before,
    first: after ? pageSize : before ? null : pageSize,
    last: before ? pageSize : null,
  };

  const { data, loading, error } = useQuery<
    GQLGetProjectStyleCustomizationsQueryData,
    GQLGetProjectStyleCustomizationsQueryVariables
  >(getProjectStyleCustomizations, {
    variables,
  });

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage(error.message);
    }
  }, [error]);

  return {
    data: data ?? null,
    loading,
  };
};
