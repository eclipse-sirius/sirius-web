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
import { useEffect, useState } from 'react';
import { RowFilter } from './RowFiltersMenu.types';
import {
  GQLGetAllTableRowFiltersData,
  GQLGetAllTableRowFiltersVariables,
  GQLTableRowFilter,
  UseTableRowFilterState,
  UseTableRowFilterValue,
} from './useTableRowFilters.types';

const getAllTableRowFiltersQuery = gql`
  query getAllTableRowFilters($editingContextId: ID!, $representationId: ID!, $tableId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TableDescription {
              rowFilters(tableId: $tableId) {
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

export const useTableRowFilters = (
  editingContextId: string,
  representationId: string | null,
  skip: boolean
): UseTableRowFilterValue => {
  const [state, setState] = useState<UseTableRowFilterState>({ rowFilters: [], activeRowFilterIds: [] });
  const { loading, data, error } = useQuery<GQLGetAllTableRowFiltersData, GQLGetAllTableRowFiltersVariables>(
    getAllTableRowFiltersQuery,
    {
      variables: {
        editingContextId,
        representationId: representationId ?? '',
        tableId: representationId ?? '',
      },
      skip,
    }
  );

  const { addErrorMessage } = useMultiToast();

  useEffect(() => {
    if (error) {
      const { message } = error;
      addErrorMessage(message);
    }
  }, [error]);

  const gqlRowFilters: GQLTableRowFilter[] = data?.viewer.editingContext.representation.description.rowFilters || [];

  useEffect(() => {
    if (!loading) {
      const allFilters: RowFilter[] = gqlRowFilters.map((gqlRowFilter) => ({
        id: gqlRowFilter.id,
        label: gqlRowFilter.label,
      }));
      const activeIds = gqlRowFilters
        .filter((gqlRowFilter) => gqlRowFilter.defaultState)
        .map((gqlRowFilter) => gqlRowFilter.id);
      setState((prevState) => ({ ...prevState, rowFilters: allFilters, activeRowFilterIds: activeIds }));
    }
  }, [loading, gqlRowFilters.map((filter) => filter.id + filter.label).join()]);

  return {
    rowFilters: state.rowFilters,
    activeRowFilterIds: state.activeRowFilterIds,
  };
};
