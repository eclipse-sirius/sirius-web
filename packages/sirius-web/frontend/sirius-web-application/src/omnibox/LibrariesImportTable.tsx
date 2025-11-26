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
import { useTableTranslation } from '@eclipse-sirius/sirius-components-tables';
import Typography from '@mui/material/Typography';
import {
  MaterialReactTable,
  MRT_ColumnDef,
  MRT_PaginationState,
  MRT_RowSelectionState,
  useMaterialReactTable,
} from 'material-react-table';
import { useEffect, useMemo, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useLibraries } from '../views/library-browser/useLibraries';
import { GQLLibrary } from '../views/library-browser/useLibraries.types';
import { LibrariesImportTableProps, LibrariesImportTableState } from './LibrariesImportTable.types';

export const LibrariesImportTable = ({ onSelectedLibrariesChange }: LibrariesImportTableProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'librariesImportTable' });
  const localization = useTableTranslation();
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
    onSelectedLibrariesChange(Object.keys(rowSelection));
  }, [rowSelection]);

  const columns = useMemo<MRT_ColumnDef<GQLLibrary>[]>(
    () => [
      {
        accessorFn: (row) => row.name,
        header: t('name'),
        size: 200,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.version,
        header: t('version'),
        size: 50,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => new Date(row.createdOn).toISOString().split('T')[0],
        header: t('createdOn'),
        size: 50,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.namespace,
        header: t('namespace'),
        size: 150,
        Cell: ({ renderedCellValue }) => (
          <Typography noWrap sx={{ opacity: '0.6' }}>
            {renderedCellValue}
          </Typography>
        ),
      },
      {
        accessorFn: (row) => row.description,
        header: t('description'),
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

    localization: localization,

    muiTableBodyProps: () =>
      ({
        'data-testid': 'libraries-import-table',
      } as any),

    muiTableBodyRowProps: ({ row }) =>
      ({
        'data-testid': `library-${row.original.namespace}:${row.original.name}:${row.original.version}`,
      } as any),

    state: { pagination, rowSelection },
  });

  return <MaterialReactTable table={table} />;
};
