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
import Box from '@mui/material/Box';
import { MaterialReactTable, MRT_DensityState, MRT_TableOptions, useMaterialReactTable } from 'material-react-table';
import { memo, useEffect, useState } from 'react';
import { SettingsButton } from '../actions/SettingsButton';
import { useTableColumnFiltering } from '../columns/useTableColumnFiltering';
import { useTableColumnSizing } from '../columns/useTableColumnSizing';
import { useTableColumnVisibility } from '../columns/useTableColumnVisibility';
import { ResizeRowHandler } from '../rows/ResizeRowHandler';
import { RowHeader } from '../rows/RowHeader';
import { useResetRowsMutation } from '../rows/useResetRows';
import { CursorBasedPagination } from './CursorBasedPagination';
import { GQLLine, TablePaginationState, TableProps } from './TableContent.types';
import { useGlobalFilter } from './useGlobalFilter';
import { useTableColumns } from './useTableColumns';

export const TableContent = memo(
  ({
    editingContextId,
    representationId,
    table,
    readOnly,
    onPaginationChange,
    onGlobalFilterChange,
    onColumnFiltersChange,
    enableColumnVisibility,
    enableColumnResizing,
    enableColumnFilters,
    enableRowSizing,
    enableGlobalFilter,
    enablePagination,
  }: TableProps) => {
    const { selection, setSelection } = useSelection();

    const { columns } = useTableColumns(
      editingContextId,
      representationId,
      table,
      readOnly,
      enableColumnVisibility,
      enableColumnResizing,
      enableColumnFilters
    );
    const { columnSizing, setColumnSizing } = useTableColumnSizing(
      editingContextId,
      representationId,
      table,
      enableColumnResizing
    );
    const { columnVisibility, setColumnVisibility } = useTableColumnVisibility(
      editingContextId,
      representationId,
      table,
      enableColumnVisibility
    );
    const { columnFilters, setColumnFilters } = useTableColumnFiltering(
      editingContextId,
      representationId,
      table,
      onColumnFiltersChange,
      enableColumnFilters
    );
    const [density, setDensity] = useState<MRT_DensityState>('comfortable');
    const [linesState, setLinesState] = useState<GQLLine[]>(table.lines);

    const { resetRowsHeight } = useResetRowsMutation(editingContextId, representationId, table.id, enableRowSizing);

    const [pagination, setPagination] = useState<TablePaginationState>({
      size: 10,
      cursor: null,
      direction: 'NEXT',
    });

    const handlePreviousPage = () => {
      setPagination((prevState) => {
        const cursorRow = table.lines[0];
        return {
          ...prevState,
          cursor: cursorRow?.targetObjectId ?? null,
          direction: 'PREV',
        };
      });
    };

    const handleNextPage = () => {
      setPagination((prevState) => {
        const cursorRow = table.lines[table.lines.length - 1];
        return {
          ...prevState,
          cursor: cursorRow?.targetObjectId ?? null,
          direction: 'NEXT',
        };
      });
    };

    const handlePageSize = (pageSize: number) => {
      setPagination((prevState) => ({
        ...prevState,
        size: pageSize,
        cursor: null,
        direction: 'NEXT',
      }));
    };

    const { globalFilter, setGlobalFilter } = useGlobalFilter(
      editingContextId,
      representationId,
      table,
      onGlobalFilterChange,
      enableGlobalFilter
    );

    useEffect(() => {
      onPaginationChange(pagination.cursor, pagination.direction, pagination.size);
    }, [pagination.cursor, pagination.size, pagination.direction]);

    const handleRowHeightChange = (rowId, height) => {
      setLinesState((prev) => prev.map((line) => (line.id === rowId ? { ...line, height } : line)));
    };

    useEffect(() => {
      setLinesState([...table.lines]);
    }, [table]);

    useEffect(() => {
      if (density != 'comfortable') {
        resetRowsHeight();
      }
    }, [density]);

    const tableOptions: MRT_TableOptions<GQLLine> = {
      columns,
      data: linesState,
      editDisplayMode: 'cell',
      enableEditing: !readOnly,
      onColumnFiltersChange: setColumnFilters,
      enableStickyHeader: true,
      enablePagination: false,
      manualPagination: enablePagination,
      rowCount: table.paginationData.totalRowCount,
      enableRowActions: true,
      enableColumnFilters,
      enableHiding: enableColumnVisibility,
      enableSorting: false,
      enableColumnResizing,
      enableGlobalFilter,
      manualFiltering: true,
      onGlobalFilterChange: setGlobalFilter,
      initialState: { showGlobalFilter: enableGlobalFilter },
      onColumnSizingChange: setColumnSizing,
      onColumnVisibilityChange: setColumnVisibility,
      onDensityChange: setDensity,
      state: { columnSizing, columnVisibility, globalFilter, density, columnFilters },
      muiTableBodyRowProps: ({ row }) => {
        return {
          onClick: () => {
            const newSelection: Selection = { entries: [{ id: row.original.targetObjectId, kind: 'Object' }] };
            setSelection(newSelection);
          },
          selected: selection.entries.map((entry) => entry.id).includes(row.original.targetObjectId),
          sx: {
            backgroundColor: 'transparent', // required to remove the default mui backgroundColor that is defined as !important
            cursor: 'pointer',
            height: row.original.height,
          },
        };
      },
      renderTopToolbarCustomActions: () => (
        <Box>
          <SettingsButton editingContextId={editingContextId} representationId={representationId} table={table} />
        </Box>
      ),
      enableBottomToolbar: enablePagination,
      renderBottomToolbarCustomActions: () => (
        <CursorBasedPagination
          hasPrev={table.paginationData.hasPreviousPage}
          hasNext={table.paginationData.hasNextPage}
          onPrev={handlePreviousPage}
          onNext={handleNextPage}
          pageSize={pagination.size}
          onPageSizeChange={handlePageSize}
        />
      ),
      displayColumnDefOptions: {
        'mrt-row-actions': {
          header: '',
          size: 120,
        },
      },
      renderRowActions: ({ row }) => (
        <>
          <RowHeader row={row.original} />
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
        </>
      ),
    };

    if (table.stripeRow) {
      tableOptions.muiTableBodyProps = {
        sx: {
          '& tr:nth-of-type(odd):not(.Mui-selected) > td': {
            backgroundColor: '#f5f5f5',
          },
        },
      };
    }

    if (enableColumnResizing && table.columns.filter((column) => column.isResizable).length > 0) {
      tableOptions.enableColumnResizing = enableColumnResizing;
      tableOptions.columnResizeMode = 'onEnd';
    }

    const muiTable = useMaterialReactTable(tableOptions);
    return (
      <div>
        <MaterialReactTable table={muiTable} />
      </div>
    );
  }
);
