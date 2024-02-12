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
  GQLGetAllTreeFiltersData,
  GQLGetAllTreeFiltersVariables,
  GQLTreeFilter,
  UseTreeFilterValue,
} from './useTreeFilters.types';

const getAllTreeFiltersQuery = gql`
  query getAllTreeFilters($editingContextId: ID!, $treeId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $treeId) {
          description {
            ... on TreeDescription {
              filters {
                id
                label
                defaultState
              }
            }
          }
        }
      }
    }
  }
`;

export const useTreeFilters = (editingContextId: string, treeId: string): UseTreeFilterValue => {
  const { loading, data, error } = useQuery<GQLGetAllTreeFiltersData, GQLGetAllTreeFiltersVariables>(
    getAllTreeFiltersQuery,
    {
      variables: {
        editingContextId,
        treeId,
      },
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const treeFilters: GQLTreeFilter[] = data?.viewer.editingContext.representation.description.filters || [];

  return {
    treeFilters,
    loading,
  };
};
