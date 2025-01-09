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
import { useEffect, useState } from 'react';
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

const getColumnsOrder = (table: GQLTable) => {
  const ids = [...table.columns].map((col) => col.id);
  return ['mrt-row-actions', ...ids, 'mrt-row-spacer'];
};

export const useTableColumnOrdering = (
  editingContextId: string,
  representationId: string,
  table: GQLTable
): UseTableColumnOrderingValue => {
  const [columnOrder, setColumnOrder] = useState<MRT_ColumnOrderState>(getColumnsOrder(table));
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

  useEffect(() => {
    if (columnOrder.length > 0) {
      reorderColumns(columnOrder.filter((id: string) => !id.startsWith('mrt-row')));
    }
  }, [columnOrder]);

  useEffect(() => {
    //Once the table is up to date, we don't want to keep the column order in the state but use the data coming from
    // the backend.
    setColumnOrder(getColumnsOrder(table));
  }, [table.columns.map((column) => column.id).join()]);

  return {
    columnOrder,
    setColumnOrder,
  };
};
