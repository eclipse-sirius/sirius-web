/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA List.
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
import { RepresentationComponentProps, RepresentationLoadingIndicator } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { getColumnFilters } from '../columns/useTableColumnFiltering';
import { useTableRowFilters } from '../rows/filters/useTableRowFilters';
import { TableContent } from '../table/TableContent';
import { ColumnFilter, ColumnSort } from '../table/TableContent.types';
import { tableIdProvider } from './tableIdProvider';
import { TableRepresentationPagination, TableRepresentationState } from './TableRepresentation.types';
import { useTableSubscription } from './useTableSubscription';

const useTableRepresentationStyles = makeStyles()((theme) => ({
  complete: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: theme.spacing(8),
  },
  representation: {
    overflowX: 'auto',
  },
}));

const defaultPagination: TableRepresentationPagination = {
  cursor: null,
  direction: 'NEXT',
  size: 0,
};

export const TableRepresentation = ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
  const { classes } = useTableRepresentationStyles();
  const [state, setState] = useState<TableRepresentationState>({
    ...defaultPagination,
    globalFilter: null,
    columnFilters: null,
    expanded: [],
    activeRowFilterIds: [],
    columnSort: null,
  });

  const representationFullId = tableIdProvider(
    representationId,
    state.cursor,
    state.direction,
    state.size,
    state.globalFilter,
    state.columnFilters,
    state.expanded,
    state.activeRowFilterIds,
    state.columnSort
  );

  const { complete, table } = useTableSubscription(editingContextId, representationFullId);

  const { rowFilters, activeRowFilterIds } = useTableRowFilters(editingContextId, representationFullId);

  useEffect(() => {
    setState((prevState) => ({ ...prevState, activeRowFilterIds }));
  }, [activeRowFilterIds.join('')]);

  useEffect(() => {
    if (table) {
      setState((prevState) => ({
        ...prevState,
        size: prevState.size === 0 ? table.defaultPageSize : prevState.size,
        globalFilter: table.globalFilter,
        columnFilters: getColumnFilters(table),
        columnSort: table.columnSort,
      }));
    }
  }, [
    table?.defaultPageSize,
    table?.globalFilter,
    table?.columnFilters.map((filter) => filter.id + filter.value).join(),
    table?.columnSort.map((sort) => sort.id + sort.desc).join(),
  ]);

  const onPaginationChange = (cursor: string | null, direction: 'PREV' | 'NEXT', size: number | null) => {
    if (size) {
      setState((prevState) => ({ ...prevState, cursor, direction, size }));
    } else {
      setState((prevState) => ({ ...prevState, cursor, direction }));
    }
  };

  const onGlobalFilterChange = (globalFilter: string) => {
    setState((prevState) => ({
      ...prevState,
      cursor: defaultPagination.cursor,
      direction: defaultPagination.direction,
      globalFilter,
    }));
  };

  const onColumnFiltersChange = (columnFilters: ColumnFilter[]) => {
    setState((prevState) => ({
      ...prevState,
      cursor: defaultPagination.cursor,
      direction: defaultPagination.direction,
      columnFilters,
    }));
  };

  const onRowFiltersChange = (activeRowFilterIds: string[]) => {
    setState((prevState) => ({
      ...prevState,
      cursor: defaultPagination.cursor,
      direction: defaultPagination.direction,
      activeRowFilterIds,
    }));
  };

  const onSortingChange = (columnSort: ColumnSort[]) => {
    setState((prevState) => ({
      ...prevState,
      cursor: defaultPagination.cursor,
      direction: defaultPagination.direction,
      columnSort,
    }));
  };

  const onExpandedElementChange = (rowId: string) => {
    if (state.expanded.includes(rowId)) {
      setState((prev) => ({ ...prev, expanded: prev.expanded.filter((id) => id !== rowId) }));
    } else {
      setState((prev) => ({ ...prev, expanded: [...prev.expanded, rowId] }));
    }
  };

  if (complete) {
    return (
      <div className={classes.complete}>
        <Typography variant="h6" align="center">
          The table does not exist anymore
        </Typography>
      </div>
    );
  } else if (!table) {
    return <RepresentationLoadingIndicator />;
  } else {
    return (
      <div data-testid={'table-representation'} className={classes.representation}>
        <TableContent
          editingContextId={editingContextId}
          representationId={representationFullId}
          table={table}
          readOnly={readOnly}
          onPaginationChange={onPaginationChange}
          onGlobalFilterChange={onGlobalFilterChange}
          onColumnFiltersChange={onColumnFiltersChange}
          onExpandedElementChange={onExpandedElementChange}
          onRowFiltersChange={onRowFiltersChange}
          onSortingChange={onSortingChange}
          enableColumnVisibility
          enableColumnResizing
          enableColumnFilters
          enableRowSizing
          enableGlobalFilter
          enablePagination
          enableColumnOrdering
          enableSelectionSynchronization
          expandedRowIds={state.expanded}
          pageSize={state.size}
          rowFilters={rowFilters}
          activeRowFilterIds={state.activeRowFilterIds}
          enableSorting
        />
      </div>
    );
  }
};
