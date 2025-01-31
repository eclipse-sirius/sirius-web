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
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { TableContent } from '../table/TableContent';
import { ColumnFilter } from '../table/TableContent.types';
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
  size: 10,
};

export const TableRepresentation = ({ editingContextId, representationId, readOnly }: RepresentationComponentProps) => {
  const { classes } = useTableRepresentationStyles();
  const [state, setState] = useState<TableRepresentationState>({
    ...defaultPagination,
    globalFilter: null,
    columnFilters: null,
    expanded: [],
  });

  const tableId = tableIdProvider(
    representationId,
    state.cursor,
    state.direction,
    state.size,
    state.globalFilter,
    state.columnFilters,
    state.expanded
  );
  const { complete, table } = useTableSubscription(editingContextId, tableId);

  const onPaginationChange = (cursor: string | null, direction: 'PREV' | 'NEXT', size: number) => {
    setState((prevState) => ({ ...prevState, cursor, direction, size }));
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
          representationId={tableId}
          table={table}
          readOnly={readOnly}
          onPaginationChange={onPaginationChange}
          onGlobalFilterChange={onGlobalFilterChange}
          onColumnFiltersChange={onColumnFiltersChange}
          onExpandedElementChange={onExpandedElementChange}
          enableColumnVisibility
          enableColumnResizing
          enableColumnFilters
          enableRowSizing
          enableGlobalFilter
          enablePagination
          enableColumnOrdering
          expandedRowIds={state.expanded}
        />
      </div>
    );
  }
};
