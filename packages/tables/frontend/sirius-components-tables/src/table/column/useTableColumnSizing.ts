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
import { UseTableColumnSizingValue } from './useTableColumnSizing.types';
import { useEffect, useState } from 'react';
import { MRT_ColumnSizingState } from 'material-react-table';
import { GQLTable } from '../TableContent.types';
import { useTableMutations } from '../../graphql/mutation/useTableMutation';

export const useTableColumnSizing = (
  editingContextId: string,
  representationId: string,
  table: GQLTable
): UseTableColumnSizingValue => {
  const [columnSizing, setColumnSizing] = useState<MRT_ColumnSizingState>({});
  const { resizeColumn } = useTableMutations(editingContextId, representationId, table.id);

  useEffect(() => {
    for (const [columnName, columnSize] of Object.entries(columnSizing)) {
      resizeColumn(columnName, columnSize);
    }
  }, [columnSizing]);

  useEffect(() => {
    //Once the table is up to date, we don't want to keep the column size in the state but use the data coming from
    // the backend.
    setColumnSizing({});
  }, [table.columns.map((column) => column.width).join()]);

  return {
    columnSizing,
    setColumnSizing,
  };
};
