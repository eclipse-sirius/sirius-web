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
import { gql, useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { MRT_SortingState } from 'material-react-table';
import { useEffect, useMemo } from 'react';
import { GQLTable, ColumnSort } from '../table/TableContent.types';
import {
  GQLChangeColumnSortData,
  GQLChangeColumnSortInput,
  GQLChangeColumnSortVariables,
  UseTableColumnSortingValue,
} from './useTableColumnSorting.types';

const changeColumnSortMutation = gql`
  mutation changeColumnSort($input: ChangeColumnSortInput!) {
    changeColumnSort(input: $input) {
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

export const useTableColumnSorting = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  onSortingChange: (columSort: ColumnSort[]) => void,
  enableSorting: boolean
): UseTableColumnSortingValue => {
  const [mutationChangeColumnSort, mutationChangeColumnSortResult] = useMutation<
    GQLChangeColumnSortData,
    GQLChangeColumnSortVariables
  >(changeColumnSortMutation);
  useReporting(mutationChangeColumnSortResult, (data: GQLChangeColumnSortData) => data.changeColumnSort);

  const changeSorting = (columnSort: ColumnSort[]) => {
    const input: GQLChangeColumnSortInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId: table.id,
      columnSort: columnSort.map((sort) => ({
        id: sort.id,
        desc: sort.desc,
      })),
    };
    mutationChangeColumnSort({ variables: { input } });
  };

  const setSorting = (columnSorts: MRT_SortingState | ((prevState: MRT_SortingState) => MRT_SortingState)) => {
    let newColumnSort: MRT_SortingState;
    if (typeof columnSorts === 'function') {
      newColumnSort = columnSorts(table.columnSort);
    } else {
      newColumnSort = columnSorts;
    }
    onSortingChange(newColumnSort);
    changeSorting(newColumnSort);
  };

  const sorting = useMemo(() => table.columnSort, [table]);

  useEffect(() => {
    onSortingChange(sorting);
  }, [sorting.map((sort) => sort.id + sort.desc).join()]);

  if (!enableSorting) {
    return {
      sorting: [],
      setSorting: undefined,
    };
  }

  return {
    sorting,
    setSorting,
  };
};
