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
import {
  RepresentationComponentProps,
  RepresentationLoadingIndicator,
  WorkbenchMainRepresentationHandle,
} from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { ForwardedRef, forwardRef, useEffect, useImperativeHandle, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { getColumnFilters } from '../columns/useTableColumnFiltering';
import { useTableRowFilters } from '../rows/filters/useTableRowFilters';
import { TableContent } from '../table/TableContent';
import { ColumnFilter, ColumnSort } from '../table/TableContent.types';
import { tableIdProvider } from './tableIdProvider';
import { TableRepresentationPagination, TableRepresentationState } from './TableRepresentation.types';
import { useTableConfiguration } from './useTableConfiguration';
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

export const TableRepresentation = forwardRef<WorkbenchMainRepresentationHandle, RepresentationComponentProps>(
  (
    { editingContextId, representationId, readOnly }: RepresentationComponentProps,
    ref: ForwardedRef<WorkbenchMainRepresentationHandle>
  ) => {
    const { classes } = useTableRepresentationStyles();
    const [state, setState] = useState<TableRepresentationState>({
      ...defaultPagination,
      globalFilter: null,
      columnFilters: null,
      expanded: [],
      activeRowFilterIds: [],
      columnSort: null,
      expandAll: false,
    });

    const { globalFilter, columnFilters, columnSort, defaultPageSize } = useTableConfiguration(
      editingContextId,
      representationId
    );

    useImperativeHandle(
      ref,
      () => {
        return {
          id: representationId,
          applySelection: null,
        };
      },
      []
    );

    useEffect(() => {
      if (globalFilter !== null && columnFilters !== null && columnSort !== null && defaultPageSize !== null) {
        setState((prevState) => ({
          ...prevState,
          size: defaultPageSize,
          globalFilter: globalFilter,
          columnFilters: getColumnFilters(columnFilters),
          columnSort: columnSort,
        }));
      }
    }, [
      globalFilter,
      columnFilters?.map((filter) => filter.id + filter.value).join(),
      columnSort?.map((sort) => sort.id + sort.desc).join(),
      defaultPageSize,
    ]);

    const representationFullId: string | null = tableIdProvider(
      representationId,
      state.cursor,
      state.direction,
      state.size,
      state.globalFilter,
      state.columnFilters,
      state.expanded,
      state.activeRowFilterIds,
      state.columnSort,
      state.expandAll
    );
    const { complete, table } = useTableSubscription(editingContextId, representationFullId);

    const { rowFilters, activeRowFilterIds } = useTableRowFilters(editingContextId, representationId, table === null);

    useEffect(() => {
      setState((prevState) => ({ ...prevState, activeRowFilterIds }));
    }, [activeRowFilterIds.join('')]);

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
      if (state.expandAll) {
        const expandAllId = table?.lines.filter((line) => line.hasChildren).map((line) => line.targetObjectId) ?? [];
        setState((prev) => ({ ...prev, expandAll: false, expanded: expandAllId.filter((id) => id !== rowId) }));
      } else {
        if (state.expanded.includes(rowId)) {
          setState((prev) => ({ ...prev, expanded: prev.expanded.filter((id) => id !== rowId) }));
        } else {
          setState((prev) => ({ ...prev, expanded: [...prev.expanded, rowId] }));
        }
      }
    };

    const onExpandAllChange = () => {
      if (state.expandAll) {
        setState((prev) => ({ ...prev, expandAll: !prev.expandAll, expanded: [] }));
      } else {
        setState((prev) => ({ ...prev, expandAll: !prev.expandAll }));
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
    } else if (!table || !representationFullId) {
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
            expandAll={state.expandAll}
            onExpandAllChange={onExpandAllChange}
          />
        </div>
      );
    }
  }
);
