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

import { MaterialReactTable, MRT_TableOptions, useMaterialReactTable } from 'material-react-table';
import { CursorBasedPagination } from '../../../table/CursorBasedPagination';
import { ProjectActionButton } from './ProjectActionButton';
import { ProjectsTableProps } from './ProjectsTable.types';
import { GQLProject } from './useProjects.types';
import { useProjectsTableColumns } from './useProjectsTableColumns';

export const ProjectsTable = ({
  loading,
  projects,
  rowCount,
  hasPreviousPage,
  hasNextPage,
  onPreviousPage,
  onNextPage,
  pageSize,
  onChange,
  onPageSizeChange,
  globalFilter,
  onGlobalFilterChange,
}: ProjectsTableProps) => {
  const { columns } = useProjectsTableColumns();
  const tableOptions: MRT_TableOptions<GQLProject> = {
    columns,
    data: projects,
    rowCount: rowCount,
    enablePagination: true,
    manualPagination: true,
    enableColumnActions: false,
    enableColumnFilters: false,
    enableFullScreenToggle: false,
    enableDensityToggle: false,
    enableHiding: false,

    state: { globalFilter, isLoading: loading },

    enableGlobalFilter: true,
    onGlobalFilterChange: onGlobalFilterChange,

    enableRowActions: true,
    positionActionsColumn: 'last',
    displayColumnDefOptions: {
      'mrt-row-actions': {
        header: 'Actions',
        size: 10,
        grow: false,
      },
    },
    renderRowActions: ({ row }) => <ProjectActionButton project={row.original} onChange={onChange} />,

    enableBottomToolbar: true,
    renderBottomToolbar: () => (
      <CursorBasedPagination
        hasPreviousPage={hasPreviousPage}
        hasNextPage={hasNextPage}
        onPreviousPage={onPreviousPage}
        onNextPage={onNextPage}
        pageSize={pageSize}
        onPageSizeChange={onPageSizeChange}
      />
    ),
  };
  const table = useMaterialReactTable<GQLProject>(tableOptions);

  return <MaterialReactTable table={table} />;
};
