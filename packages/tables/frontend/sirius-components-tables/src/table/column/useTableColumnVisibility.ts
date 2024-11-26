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
import { useEffect, useState } from 'react';
import { MRT_VisibilityState } from 'material-react-table';
import { GQLTable } from '../TableContent.types';
import { useTableMutations } from '../../graphql/mutation/useTableMutation';
import { UseTableColumnVisibilityValue } from './useTableColumnVisibility.types';

export const useTableColumnVisibility = (
  editingContextId: string,
  representationId: string,
  table: GQLTable
): UseTableColumnVisibilityValue => {
  const [columnVisibility, setColumnVisibility] = useState<MRT_VisibilityState>(
    table.columns.reduce((acc, obj) => {
      acc[obj.id] = !obj.hidden;
      return acc;
    }, {})
  );
  const { changeColumnVisibility } = useTableMutations(editingContextId, representationId, table.id);

  useEffect(() => {
    changeColumnVisibility(
      Object.entries(columnVisibility).map(([columnId, visible]) => ({
        columnId,
        visible,
      }))
    );
  }, [columnVisibility]);

  useEffect(() => {
    setColumnVisibility(
      table.columns.reduce((acc, obj) => {
        acc[obj.id] = !obj.hidden;
        return acc;
      }, {})
    );
  }, [table.columns.map((column) => column.hidden).join()]);

  return {
    columnVisibility,
    setColumnVisibility,
  };
};
