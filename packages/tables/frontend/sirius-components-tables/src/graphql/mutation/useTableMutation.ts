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
import {
  UseTableMutationValue,
  GQLResizeColumnInput,
  GQLResizeColumnData,
  GQLResizeColumnVariables,
} from './useTableMutation.types';
import { useReporting } from '@eclipse-sirius/sirius-components-core';
import { resizeColumnMutation } from './tableMutation';

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

  return {
    resizeColumn,
  };
};
