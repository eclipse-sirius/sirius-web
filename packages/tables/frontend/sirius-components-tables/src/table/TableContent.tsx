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
import { useSelection } from '@eclipse-sirius/sirius-components-core';
import FormatLineSpacingIcon from '@mui/icons-material/FormatLineSpacing';
import Box from '@mui/material/Box';
import IconButton from '@mui/material/IconButton';
import { Theme, useTheme } from '@mui/material/styles';
import {
  MaterialReactTable,
  MRT_ShowHideColumnsButton,
  MRT_TableOptions,
  MRT_ToggleFiltersButton,
  MRT_ToggleFullScreenButton,
  useMaterialReactTable,
} from 'material-react-table';
import { memo, useEffect, useState } from 'react';
import { SettingsButton } from '../actions/SettingsButton';
import { useTableColumnFiltering } from '../columns/useTableColumnFiltering';
import { useTableColumnOrdering } from '../columns/useTableColumnOrdering';
import { useTableColumnSizing } from '../columns/useTableColumnSizing';
import { useTableColumnSorting } from '../columns/useTableColumnSorting';
import { useTableColumnVisibility } from '../columns/useTableColumnVisibility';
import { RowFiltersMenu } from '../rows/filters/RowFiltersMenu';
import { RowAction } from '../rows/RowAction';
import { useResetRowsMutation } from '../rows/useResetRows';
import { CursorBasedPagination } from './CursorBasedPagination';
import { GQLLine, TableContentProps } from './TableContent.types';
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
    onExpandedElementChange,
    onRowFiltersChange,
    onSortingChange,
    enableColumnVisibility,
    enableColumnResizing,
    enableColumnFilters,
    enableRowSizing,
    enableGlobalFilter,
    enablePagination,
    enableColumnOrdering,
    enableSelectionSynchronization,
    pageSize,
    expandedRowIds,
    rowFilters,
    activeRowFilterIds,
    enableSorting,
  }: TableContentProps) => {
    const { selection } = useSelection();
    const theme: Theme = useTheme();

    const handleRowHeightChange = (rowId: string, height: number) => {
      setLinesState((prev) => prev.map((line) => (line.id === rowId ? { ...line, height } : line)));
    };

    const { columns } = useTableColumns(
      editingContextId,
      representationId,
      table,
      readOnly,
      enableColumnVisibility,
      enableColumnResizing,
      enableColumnFilters,
      enableColumnOrdering,
      enableRowSizing,
      enableSelectionSynchronization,
      handleRowHeightChange,
      onExpandedElementChange,
      expandedRowIds
    );
    const { columnSizing, setColumnSizing } = useTableColumnSizing(
      editingContextId,
      representationId,
      table,
      enableColumnResizing
    );
    const { columnOrder, setColumnOrder } = useTableColumnOrdering(
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
    const { sorting, setSorting } = useTableColumnSorting(
      editingContextId,
      representationId,
      table,
      onSortingChange,
      enableColumnFilters
    );
    const [linesState, setLinesState] = useState<GQLLine[]>(table.lines);

    const { resetRowsHeight } = useResetRowsMutation(editingContextId, representationId, table.id, enableRowSizing);

    const handlePreviousPage = () => {
      const cursorRow = table.lines[0];
      onPaginationChange(cursorRow?.targetObjectId ?? null, 'PREV', null);
    };

    const handleNextPage = () => {
      const cursorRow = table.lines[table.lines.length - 1];
      onPaginationChange(cursorRow?.targetObjectId ?? null, 'NEXT', null);
    };

    const handlePageSize = (pageSize: number) => {
      onPaginationChange(null, 'NEXT', pageSize);
    };

    const { globalFilter, setGlobalFilter } = useGlobalFilter(
      editingContextId,
      representationId,
      table,
      onGlobalFilterChange,
      enableGlobalFilter
    );

    useEffect(() => {
      setLinesState([...table.lines]);
    }, [table]);

    const tableOptions: MRT_TableOptions<GQLLine> = {
      columns,
      data: linesState,
      enableEditing: !readOnly,
      onColumnFiltersChange: setColumnFilters,
      enableStickyHeader: true,
      enablePagination: false,
      manualPagination: enablePagination,
      rowCount: table.paginationData.totalRowCount,
      enableRowActions: true,
      enableColumnFilters,
      enableHiding: enableColumnVisibility,
      enableSorting,
      enableColumnResizing,
      enableGlobalFilter,
      manualFiltering: true,
      onGlobalFilterChange: setGlobalFilter,
      enableColumnPinning: true,
      manualSorting: true,
      enableMultiSort: true,
      isMultiSortEvent: () => true,
      onSortingChange: setSorting,
      initialState: {
        showGlobalFilter: enableGlobalFilter,
        columnPinning: { left: ['mrt-row-header'], right: ['mrt-row-actions'] },
      },
      onColumnSizingChange: setColumnSizing,
      onColumnVisibilityChange: setColumnVisibility,
      enableColumnOrdering,
      enableColumnDragging: enableColumnOrdering,
      onColumnOrderChange: setColumnOrder,
      state: { columnSizing, columnVisibility, globalFilter, columnFilters, columnOrder, sorting },
      muiTableBodyRowProps: ({ row }) => {
        return {
          selected: selection.entries.map((entry) => entry.id).includes(row.original.targetObjectId),
          sx: {
            backgroundColor: 'transparent', // required to remove the default mui backgroundColor that is defined as !important
            cursor: 'pointer',
            height: row.original.height,
          },
        };
      },
      muiTableBodyCellProps: ({ cell, row }) => {
        const rowSelected = selection.entries.map((entry) => entry.id).includes(row.original.targetObjectId);
        const cellTargetObjectId = row.original.cells.find(
          (originalCell) => originalCell.columnId === cell.column.id
        )?.targetObjectId;
        const cellSelected =
          cellTargetObjectId && selection.entries.map((entry) => entry.id).includes(cellTargetObjectId);
        return {
          sx: {
            border: !rowSelected && cellSelected ? `2px dashed ${theme.palette.action.selected}` : undefined,
            padding: '0px',
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
          pageSize={pageSize}
          onPageSizeChange={handlePageSize}
          pageSizeOptions={table.pageSizeOptions}
        />
      ),
      renderRowActions: ({ row }) => (
        <RowAction
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={table.id}
          row={row}
          readOnly={readOnly}
        />
      ),
      renderToolbarInternalActions: ({ table }) => (
        <Box>
          <IconButton
            aria-label="Reset row heights"
            title="Reset row heights"
            data-testid="reset-row-heights"
            onClick={() => resetRowsHeight()}>
            <FormatLineSpacingIcon />
          </IconButton>
          <RowFiltersMenu
            data-testid="row-filters-menu-button"
            readOnly={readOnly}
            rowFilters={rowFilters ?? []}
            activeRowFilterIds={activeRowFilterIds}
            onRowFiltersChange={onRowFiltersChange}
          />
          <MRT_ToggleFiltersButton table={table} />
          <MRT_ShowHideColumnsButton table={table} />
          <MRT_ToggleFullScreenButton table={table} />
        </Box>
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
