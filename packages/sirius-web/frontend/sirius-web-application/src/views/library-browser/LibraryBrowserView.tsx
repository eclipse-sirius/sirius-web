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
import { useComponent } from '@eclipse-sirius/sirius-components-core';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import { MaterialReactTable, MRT_ColumnDef, MRT_PaginationState, useMaterialReactTable } from 'material-react-table';
import { useEffect, useMemo, useState } from 'react';
import { Link, Navigate } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import { footerExtensionPoint } from '../../footer/FooterExtensionPoints';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { useCurrentViewer } from '../../viewer/useCurrentViewer';
import { LibrariesTableProps, LibrariesTableState } from './LibraryBrowserView.types';
import { useLibraries } from './useLibraries';
import { GQLLibrary } from './useLibraries.types';

const useLibraryBrowserViewStyle = makeStyles()((theme) => ({
  librariesView: {
    display: 'grid',
    gridTemplateColumns: '1fr',
    gridTemplateRows: 'min-content 1fr min-content',
    minHeight: '100vh',
  },
  main: {
    display: 'flex',
    flexDirection: 'column',
    gap: theme.spacing(5),
    paddingTop: theme.spacing(3),
    paddingBottom: theme.spacing(3),
  },
  header: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
}));

export const LibraryBrowserView = () => {
  const { classes } = useLibraryBrowserViewStyle();
  const { Component: Footer } = useComponent(footerExtensionPoint);
  const {
    viewer: {
      capabilities: {
        libraries: { canView },
      },
    },
  } = useCurrentViewer();

  if (!canView) {
    return <Navigate to="/errors/404" />;
  }

  return (
    <div className={classes.librariesView}>
      <NavigationBar />
      <Container maxWidth="xl">
        <Grid container justifyContent="center">
          <Grid size={{ xs: 8 }}>
            <main className={classes.main}>
              <div className={classes.header}>
                <Typography variant="h4">Libraries</Typography>
              </div>
              <LibrariesTable />
            </main>
          </Grid>
        </Grid>
      </Container>
      <Footer />
    </div>
  );
};

const LibrariesTable = ({}: LibrariesTableProps) => {
  const [state, setState] = useState<LibrariesTableState>({
    data: null,
  });
  const [pagination, setPagination] = useState<MRT_PaginationState>({
    pageIndex: 0,
    pageSize: 20,
  });

  const rows: GQLLibrary[] = state.data?.viewer.libraries.edges.map((edge) => edge.node) ?? [];
  const count: number = state.data?.viewer.libraries.pageInfo.count ?? 0;

  const { data } = useLibraries(pagination.pageIndex, pagination.pageSize);
  useEffect(() => {
    if (data) {
      setState((prevState) => ({ ...prevState, data }));
    }
  }, [data]);

  const columns = useMemo<MRT_ColumnDef<GQLLibrary>[]>(
    () => [
      {
        accessorFn: (row) => row.name,
        header: 'Name',
        size: 200,
        Cell: ({ renderedCellValue, row }) => (
          <Typography noWrap>
            <Link
              to={`/libraries/${encodeURIComponent(row.original.namespace)}/${encodeURIComponent(
                row.original.name
              )}/${encodeURIComponent(row.original.version)}`}>
              {renderedCellValue}
            </Link>
          </Typography>
        ),
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
    columns,
    data: rows,

    // Disable some unnecessary features (overkill here)
    enableColumnActions: false,
    enableColumnFilters: false,
    enableFullScreenToggle: false,
    enableDensityToggle: false,
    enableHiding: false,
    enableSorting: false,

    // Configure pagination
    enablePagination: true,
    manualPagination: true,
    onPaginationChange: setPagination,
    state: { pagination },
    rowCount: count,
    autoResetPageIndex: false,
  });

  return <MaterialReactTable table={table} />;
};
