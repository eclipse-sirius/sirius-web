/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import { MRT_ColumnDef } from 'material-react-table';
import { useMemo } from 'react';
import { makeStyles } from 'tss-react/mui';
import { Cell } from '../cells/Cell';
import { ColumnHeader } from '../columns/ColumnHeader';
import { GQLCell, GQLLine, GQLTable } from './TableContent.types';
import { UseTableColumnsValue } from './useTableColumns.types';

const useStyles = makeStyles()(() => ({
  cell: {
    width: '100%',
  },
}));

export const useTableColumns = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  readOnly: boolean,
  enableColumnVisibility: boolean,
  enableColumnSizing: boolean,
  enableColumnFilters: boolean,
  enableColumnOrdering: boolean
): UseTableColumnsValue => {
  const { classes } = useStyles();
  const { setSelection } = useSelection();
  const columns = useMemo<MRT_ColumnDef<GQLLine, string>[]>(() => {
    const columnDefs: MRT_ColumnDef<GQLLine, string>[] = table.columns.map((column) => {
      return {
        id: column.id,
        accessorKey: column.id,
        header: column.headerLabel,
        Header: ({}) => {
          return <ColumnHeader column={column} />;
        },
        filterVariant: enableColumnFilters ? column.filterVariant : undefined,
        size: enableColumnSizing && column.width > 0 ? column.width : undefined,
        enableResizing: enableColumnSizing && column.isResizable,
        visibleInShowHideMenu: enableColumnVisibility,
        enableColumnDragging: enableColumnOrdering,
        enableColumnOrdering,
        Cell: ({ row }) => {
          const cell: GQLCell | null = row.original.cells.find((cell) => column.id === cell.columnId) ?? null;
          const onClick = () => {
            if (cell) {
              const newSelection: Selection = { entries: [{ id: cell.targetObjectId, kind: cell.targetObjectKind }] };
              setSelection(newSelection);
            }
          };
          return (
            <div className={classes.cell} onClick={onClick}>
              <Cell
                editingContextId={editingContextId}
                representationId={representationId}
                tableId={table.id}
                cell={cell}
                disabled={readOnly}
              />
            </div>
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
