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
import { Cell } from '../cells/Cell';
import { ColumnHeader } from '../columns/ColumnHeader';
import { ResizeRowHandler } from '../rows/ResizeRowHandler';
import { RowHeader } from '../rows/RowHeader';
import { GQLCell, GQLLine, GQLTable } from './TableContent.types';
import { UseTableColumnsValue } from './useTableColumns.types';

export const useTableColumns = (
  editingContextId: string,
  representationId: string,
  table: GQLTable,
  readOnly: boolean,
  enableColumnVisibility: boolean,
  enableColumnSizing: boolean,
  enableColumnFilters: boolean,
  enableColumnOrdering: boolean,
  enableRowSizing: boolean,
  handleRowHeightChange: (rowId: string, height: number) => void,
  onExpandedElement: (rowId: string) => void,
  expandedRowIds: string[]
): UseTableColumnsValue => {
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

    const rowHeaderColumn: MRT_ColumnDef<GQLLine, string> = {
      id: 'mrt-row-header',
      header: '',
      columnDefType: 'display',
      Cell: ({ row }) => (
        <div
          onClick={() => {
            const newSelection: Selection = {
              entries: [
                {
                  id: row.original.targetObjectId,
                },
              ],
            };
            setSelection(newSelection);
          }}>
          <RowHeader
            row={row.original}
            enableSubRows={table.enableSubRows}
            isExpanded={expandedRowIds.includes(row.original.targetObjectId)}
            onClick={onExpandedElement}
          />
          {enableRowSizing ? (
            <ResizeRowHandler
              editingContextId={editingContextId}
              representationId={representationId}
              table={table}
              readOnly={readOnly}
              row={row.original}
              onRowHeightChanged={handleRowHeightChange}
            />
          ) : null}
        </div>
      ),
    };
    return [rowHeaderColumn, ...columnDefs];
  }, [table, expandedRowIds]);

  return {
    columns,
  };
};
