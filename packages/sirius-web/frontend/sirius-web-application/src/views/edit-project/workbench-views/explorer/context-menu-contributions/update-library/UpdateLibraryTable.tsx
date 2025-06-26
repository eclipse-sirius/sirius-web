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
import Box from '@mui/material/Box';
import Chip from '@mui/material/Chip';
import Typography from '@mui/material/Typography';
import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_PaginationState,
  MRT_RowSelectionState,
  useMaterialReactTable,
} from 'material-react-table';
import { useEffect, useMemo, useState } from 'react';
import { UpdateLibraryTableProps } from './UpdateLibraryTable.types';
import { useLibraryVersions } from './useLibraryVersions';
import { GQLLibraryVersion } from './useLibraryVersions.types';

export const UpdateLibraryTable = ({ namespace, name, version, onLibrarySelected }: UpdateLibraryTableProps) => {
  const [pagination, setPagination] = useState<MRT_PaginationState>({
    pageIndex: 0,
    pageSize: 20,
  });

  const { data } = useLibraryVersions(namespace, name, version, pagination.pageIndex, pagination.pageSize);
  const rows: GQLLibraryVersion[] = data?.viewer.library.versions.edges.map((edge) => edge.node) || [];
  const count: number = data?.viewer.library.versions.pageInfo.count ?? 0;

  const [rowSelection, setRowSelection] = useState<MRT_RowSelectionState>({});

  useEffect(() => {
    const selectedLibraryId = Object.keys(rowSelection)[0];
    onLibrarySelected(selectedLibraryId);
  }, [rowSelection]);

  const columns = useMemo<MRT_ColumnDef<GQLLibraryVersion>[]>(
    () => [
      {
        accessorFn: (row) => row.name,
        header: 'Name',
        size: 200,
        Cell: ({ renderedCellValue, row }) => {
          const currentVersion = row.original.version === version;
          return (
            <Box
              sx={(theme) => ({ display: 'flex', flexDirection: 'row', alignItems: 'center', gap: theme.spacing(2) })}>
              <Typography noWrap>{renderedCellValue}</Typography>
              {currentVersion ? (
                <Chip label="Current Version" variant="outlined" color="primary" size="small"></Chip>
              ) : null}
            </Box>
          );
        },
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

  const table = useMaterialReactTable<GQLLibraryVersion>({
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
    enableMultiRowSelection: false,
    enableRowSelection: (row) => row.original.version !== version,
    getRowId: (originalRow) => originalRow.id,
    onRowSelectionChange: setRowSelection,
    muiTableBodyCellProps: ({ column, row }) => {
      if (column.columnDef.header === 'Select') {
        return {
          'data-testid': 'select-' + row.original.name + '@' + row.original.version,
        } as any;
      }
    },

    // Configure pagination
    enablePagination: true,
    manualPagination: true,
    onPaginationChange: setPagination,
    rowCount: count,

    state: { pagination, rowSelection },
  });

  return <MaterialReactTable table={table} />;
};
