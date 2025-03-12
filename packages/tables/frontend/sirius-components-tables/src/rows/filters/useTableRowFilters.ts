/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
  GQLGetAllTableRowFiltersData,
  GQLGetAllTableRowFiltersVariables,
  GQLTableRowFilter,
  UseTableRowFilterValue,
} from './useTableRowFilters.types';

const getAllTableRowFiltersQuery = gql`
  query getAllTableRowFilters($editingContextId: ID!, $tableId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $tableId) {
          description {
            ... on TableDescription {
              rowFilters {
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

export const useTableRowFilters = (editingContextId: string, tableId: string): UseTableRowFilterValue => {
  const { loading, data, error } = useQuery<GQLGetAllTableRowFiltersData, GQLGetAllTableRowFiltersVariables>(
    getAllTableRowFiltersQuery,
    {
      variables: {
        editingContextId,
        tableId,
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

  const rowFilters: GQLTableRowFilter[] = data?.viewer.editingContext.representation.description.rowFilters || [];

  return {
    rowFilters,
    loading,
  };
};
