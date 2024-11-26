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
import { useMutation } from '@apollo/client';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { changeColumnVisibilityMutation, resizeColumnMutation, resizeRowMutation } from './tableMutation';
import {
  GQLChangeColumnVisibilityData,
  GQLChangeColumnVisibilityInput,
  GQLChangeColumnVisibilityVariables,
  GQLColumnVisibility,
  GQLResizeColumnData,
  GQLResizeColumnInput,
  GQLResizeColumnVariables,
  GQLResizeRowData,
  GQLResizeRowInput,
  GQLResizeRowVariables,
  UseTableMutationValue,
} from './useTableMutation.types';

export const useTableMutations = (
  editingContextId: string,
  representationId: string,
  tableId: string
): UseTableMutationValue => {
  const [mutationResizeColumn, mutationResizeColumnResult] = useMutation<GQLResizeColumnData, GQLResizeColumnVariables>(
    resizeColumnMutation
  );
  useReporting(mutationResizeColumnResult, (data: GQLResizeColumnData) => data.resizeTableColumn);

  const resizeColumn = (columnId: string, width: number) => {
    const input: GQLResizeColumnInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId,
      columnId,
      width,
    };

    mutationResizeColumn({ variables: { input } });
  };

  const [mutationChangeColumnVisibility, mutationChangeColumnVisibilityResult] = useMutation<
    GQLChangeColumnVisibilityData,
    GQLChangeColumnVisibilityVariables
  >(changeColumnVisibilityMutation);
  useReporting(
    mutationChangeColumnVisibilityResult,
    (data: GQLChangeColumnVisibilityData) => data.changeTableColumnVisibility
  );

  const changeColumnVisibility = (columnsVisibility: GQLColumnVisibility[]) => {
    const input: GQLChangeColumnVisibilityInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId,
      columnsVisibility,
    };

    mutationChangeColumnVisibility({ variables: { input } });
  };

  const [mutationResizeRow, mutationResizeRowResult] = useMutation<GQLResizeRowData, GQLResizeRowVariables>(
    resizeRowMutation
  );
  useReporting(mutationResizeRowResult, (data: GQLResizeRowData) => data.resizeTableRow);

  const resizeRow = (rowId: string, height: number) => {
    const input: GQLResizeRowInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId,
      tableId,
      rowId,
      height,
    };

    mutationResizeRow({ variables: { input } });
  };

  return {
    resizeColumn,
    changeColumnVisibility,
    resizeRow,
  };
};
