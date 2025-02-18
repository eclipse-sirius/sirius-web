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
import { MRT_ColumnOrderState } from 'material-react-table';
import { useMemo } from 'react';
import { GQLTable } from '../table/TableContent.types';
import {
  GQLReorderColumnsData,
  GQLReorderColumnsInput,
  GQLReorderColumnsVariables,
  UseTableColumnOrderingValue,
} from './useTableColumnOrdering.types';

const reorderColumnsMutation = gql`
  mutation reorderTableColumns($input: ReorderTableColumnsInput!) {
    reorderTableColumns(input: $input) {
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

const getColumnsOrder = (table: GQLTable, enableColumnResizing: boolean) => {
  const ids = [...table.columns].map((col) => col.id);
  if (enableColumnResizing) {
    return ['mrt-row-actions', ...ids, 'mrt-row-header', 'mrt-row-spacer'];
  }
  return ['mrt-row-actions', ...ids, 'mrt-row-header'];
};

export const useTableColumnOrdering = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  enableColumnResizing: boolean
): UseTableColumnOrderingValue => {
  const [mutationReorderColumns, mutationReorderColumnsResult] = useMutation<
    GQLReorderColumnsData,
    GQLReorderColumnsVariables
  >(reorderColumnsMutation);
  useReporting(mutationReorderColumnsResult, (data: GQLReorderColumnsData) => data.reorderTableColumns);

  const reorderColumns = (reorderedColumnIds: string[]) => {
    const input: GQLReorderColumnsInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId: table.id,
      reorderedColumnIds,
    };

    mutationReorderColumns({ variables: { input } });
  };

  const setColumnOrder = (
    columnOrder: MRT_ColumnOrderState | ((prevState: MRT_ColumnOrderState) => MRT_ColumnOrderState)
  ) => {
    let newColumnOrder: MRT_ColumnOrderState;
    if (typeof columnOrder === 'function') {
      newColumnOrder = columnOrder(getColumnsOrder(table, enableColumnResizing));
    } else {
      newColumnOrder = columnOrder;
    }

    reorderColumns(newColumnOrder);
  };

  const columnOrder = useMemo(() => getColumnsOrder(table, enableColumnResizing), [table, enableColumnResizing]);

  return {
    columnOrder,
    setColumnOrder,
  };
};
