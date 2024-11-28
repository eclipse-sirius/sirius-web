/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import { useTheme } from '@mui/material/styles';
import { MRT_ColumnDef } from 'material-react-table';
import { useMemo } from 'react';
import { Cell } from '../cells/Cell';
import { GQLCell, GQLLine, GQLTable } from './TableContent.types';
import { UseTableColumnsValue } from './useTableColumns.types';

export const useTableColumns = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  readOnly
): UseTableColumnsValue => {
  const theme = useTheme();

  const columns = useMemo<MRT_ColumnDef<GQLLine, string>[]>(() => {
    const columnDefs: MRT_ColumnDef<GQLLine, string>[] = table.columns.map((column) => {
      return {
        id: column.id,
        header: column.label,
        Header: ({}) => {
          return (
            <Box display="flex" alignItems="center">
              <IconOverlay
                iconURL={column.iconURLs}
                alt={column.label}
                customIconStyle={{ marginRight: theme.spacing(1) }}
              />
              {column.label}
            </Box>
          );
        },
        size: 150,
        Cell: ({ row }) => {
          const cell: GQLCell | null = row.original.cells.find((cell) => column.id === cell.columnId) ?? null;
          return (
            <Cell
              editingContextId={editingContextId}
              representationId={representationId}
              tableId={table.id}
              cell={cell}
              disabled={readOnly}
            />
          );
        },
      };
    });

    return columnDefs;
  }, [table]);

  return {
    columns,
  };
};
