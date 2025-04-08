/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import Typography from '@mui/material/Typography';
import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_PaginationState,
  MRT_RowSelectionState,
  useMaterialReactTable,
} from 'material-react-table';
import { useEffect, useMemo, useState } from 'react';
import { useLibraries } from '../views/library-browser/useLibraries';
import { GQLLibrary } from '../views/library-browser/useLibraries.types';
import { LibrariesImportTableProps, LibrariesImportTableState } from './LibrariesImportTable.types';

export const LibrariesImportTable = ({ onSelectionChange }: LibrariesImportTableProps) => {
  const [state, setState] = useState<LibrariesImportTableState>({
    data: null,
  });
  const [pagination, setPagination] = useState<MRT_PaginationState>({
    pageIndex: 0,
    pageSize: 20,
  });

  const { data } = useLibraries(pagination.pageIndex, pagination.pageSize);
  const rows: GQLLibrary[] = state.data?.viewer.libraries.edges.map((edge) => edge.node) || [];
  const count: number = state.data?.viewer.libraries.pageInfo.count ?? 0;

  const [rowSelection, setRowSelection] = useState<MRT_RowSelectionState>({});

  useEffect(() => {
    if (data) {
      setState((prevState) => ({ ...prevState, data }));
    }
  }, [data]);

  useEffect(() => {
    onSelectionChange(Object.keys(rowSelection));
  }, [rowSelection]);

  const columns = useMemo<MRT_ColumnDef<GQLLibrary>[]>(
    () => [
      {
        accessorFn: (row) => row.name,
        header: 'Name',
        size: 200,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.version,
        header: 'Version',
        size: 50,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => new Date(row.createdOn).toISOString().split('T')[0],
        header: 'Created On',
        size: 50,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.namespace,
        header: 'Namespace',
        size: 150,
        Cell: ({ renderedCellValue }) => (
          <Typography noWrap sx={{ opacity: '0.6' }}>
            {renderedCellValue}
          </Typography>
        ),
      },
      {
        accessorFn: (row) => row.description,
        header: 'Description',
        size: 200,
        grow: true,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
    ],
    []
  );

  const table = useMaterialReactTable<GQLLibrary>({
    // Data
    columns,
    data: rows,

    // Disable some unnecessary features (overkill here)
    enableColumnActions: false,
    enableColumnFilters: false,
    enableFullScreenToggle: false,
    enableDensityToggle: false,
    enableHiding: false,
    enableSorting: false,

    // Configure selection
    enableRowSelection: true,
    getRowId: (originalRow) => originalRow.id,
    onRowSelectionChange: setRowSelection,

    // Configure pagination
    enablePagination: true,
    manualPagination: true,
    onPaginationChange: setPagination,
    rowCount: count,

    state: { pagination, rowSelection },
  });

  return <MaterialReactTable table={table} />;
};
