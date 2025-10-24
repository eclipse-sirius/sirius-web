/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST.
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
import { gql, useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { MRT_ColumnFiltersState } from 'material-react-table';
import { useEffect, useMemo } from 'react';
import { ColumnFilter, GQLTable } from '../table/TableContent.types';
import {
  GQLChangeColumnFilterData,
  GQLChangeColumnFilterInput,
  GQLChangeColumnFilterVariables,
  UseTableColumnFilteringValue,
} from './useTableColumnFiltering.types';

const changeColumnFilterMutation = gql`
  mutation changeColumnFilter($input: ChangeColumnFilterInput!) {
    changeColumnFilter(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const getColumnFilters = (columnFilters: ColumnFilter[]) => {
  return columnFilters.map((filter) => ({
    id: filter.id,
    value: JSON.parse(filter.value as string),
  }));
};

export const useTableColumnFiltering = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  onColumnFiltersChange: (columFilters: ColumnFilter[]) => void,
  enableColumnFilters: boolean
): UseTableColumnFilteringValue => {
  const [mutationChangeColumnFilter, mutationChangeColumnFilterResult] = useMutation<
    GQLChangeColumnFilterData,
    GQLChangeColumnFilterVariables
  >(changeColumnFilterMutation);
  useReporting(mutationChangeColumnFilterResult, (data: GQLChangeColumnFilterData) => data.changeColumnFilter);

  const changeColumnFilter = (columnFilters: ColumnFilter[]) => {
    const input: GQLChangeColumnFilterInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId: table.id,
      columnFilters: columnFilters.map((columnFilter) => ({
        id: columnFilter.id,
        value: JSON.stringify(columnFilter.value),
      })),
    };
    mutationChangeColumnFilter({ variables: { input } });
  };

  const setColumnFilters = (
    columnFilters: MRT_ColumnFiltersState | ((prevState: MRT_ColumnFiltersState) => MRT_ColumnFiltersState)
  ) => {
    let newColumnFilter: MRT_ColumnFiltersState;
    if (typeof columnFilters === 'function') {
      newColumnFilter = columnFilters(getColumnFilters(table.columnFilters));
    } else {
      newColumnFilter = columnFilters;
    }
    onColumnFiltersChange(newColumnFilter);
    changeColumnFilter(newColumnFilter);
  };

  const columnFilters = useMemo(() => getColumnFilters(table.columnFilters), [table]);

  useEffect(() => {
    onColumnFiltersChange(columnFilters);
  }, [columnFilters.map((columnFilter) => columnFilter.id + columnFilter.value).join()]);

  if (!enableColumnFilters) {
    return {
      columnFilters: [],
      setColumnFilters: undefined,
    };
  }

  return {
    columnFilters,
    setColumnFilters,
  };
};
