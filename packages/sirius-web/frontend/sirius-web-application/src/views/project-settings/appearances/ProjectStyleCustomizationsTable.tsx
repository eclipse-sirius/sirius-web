/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { Checkbox } from '@mui/material';
import Typography from '@mui/material/Typography';
import { MaterialReactTable, MRT_ColumnDef, useMaterialReactTable } from 'material-react-table';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { CursorBasedPagination } from '../../../table/CursorBasedPagination';
import {
  ProjectStyleCustomization,
  ProjectStyleCustomizationsTableProps,
} from './ProjectStyleCustomizationsTable.types';
import { GQLStyleCustomization } from './useProjectStyleCustomizations.types';

export const ProjectStyleCustomizationsTable = ({
  loading,
  styleCustomizations,
  rowCount,
  hasPreviousPage,
  hasNextPage,
  onPreviousPage,
  onNextPage,
  pageSize,
  onPageSizeChange,
}: ProjectStyleCustomizationsTableProps) => {
  const { t } = useTranslation('sirius-web-projects-stylecustomizations-application', {
    keyPrefix: 'projectStyleCustomizationTable',
  });
  const localization = useTableTranslation();

  const columns = useMemo<MRT_ColumnDef<ProjectStyleCustomization>[]>(
    () => [
      {
        accessorKey: 'enabled',
        header: '',
        size: 50,
        Cell: ({ cell }) => <Checkbox disabled checked={cell.getValue<boolean>()} />,
      },
      {
        accessorFn: (row) => row.name,
        header: t('name'),
        size: 200,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
      {
        accessorFn: (row) => row.description,
        header: t('description'),
        size: 200,
        Cell: ({ renderedCellValue }) => <Typography noWrap>{renderedCellValue}</Typography>,
      },
    ],
    [t]
  );

  const table = useMaterialReactTable<GQLStyleCustomization>({
    // Data
    columns,
    data: styleCustomizations,

    // Disable some unnecessary features (overkill here)
    enableColumnActions: false,
    enableColumnFilters: false,
    enableFullScreenToggle: false,
    enableDensityToggle: false,
    enableHiding: false,
    enableSorting: false,
    enableRowSelection: false,
    enableGlobalFilter: false,

    // Configure pagination
    enablePagination: true,
    manualPagination: true,
    rowCount: rowCount,
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

    localization: localization,

    state: { isLoading: loading },
  });

  return <MaterialReactTable table={table} />;
};
