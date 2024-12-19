/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
import { MRT_ColumnSizingState } from 'material-react-table';
import { useEffect, useState } from 'react';
import { GQLTable } from '../table/TableContent.types';
import {
  GQLResizeColumnData,
  GQLResizeColumnInput,
  GQLResizeColumnVariables,
  UseTableColumnSizingValue,
} from './useTableColumnSizing.types';

const resizeColumnMutation = gql`
  mutation resizeTableColumn($input: ResizeTableColumnInput!) {
    resizeTableColumn(input: $input) {
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

export const useTableColumnSizing = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  enableColumnSizing: boolean
): UseTableColumnSizingValue => {
  const [columnSizing, setColumnSizing] = useState<MRT_ColumnSizingState>({});

  useEffect(() => {
    if (enableColumnSizing) {
      for (const [columnName, columnSize] of Object.entries(columnSizing)) {
        resizeColumn(columnName, columnSize);
      }
    }
  }, [columnSizing]);

  useEffect(() => {
    //Once the table is up to date, we don't want to keep the column size in the state but use the data coming from
    // the backend.
    setColumnSizing({});
  }, [table.columns.map((column) => column.width).join()]);

  const [mutationResizeColumn, mutationResizeColumnResult] = useMutation<GQLResizeColumnData, GQLResizeColumnVariables>(
    resizeColumnMutation
  );
  useReporting(mutationResizeColumnResult, (data: GQLResizeColumnData) => data.resizeTableColumn);

  const resizeColumn = (columnId: string, width: number) => {
    const input: GQLResizeColumnInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId: table.id,
      columnId,
      width,
    };

    mutationResizeColumn({ variables: { input } });
  };

  if (!enableColumnSizing) {
    return {
      columnSizing: {},
      setColumnSizing: undefined,
    };
  }

  return {
    columnSizing,
    setColumnSizing,
  };
};
