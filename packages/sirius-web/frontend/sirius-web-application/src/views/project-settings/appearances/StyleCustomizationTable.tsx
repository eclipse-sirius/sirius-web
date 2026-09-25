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
import Typography from '@mui/material/Typography';
import { MaterialReactTable, MRT_ColumnDef, useMaterialReactTable } from 'material-react-table';
import { useMemo } from 'react';
import { useTranslation } from 'react-i18next';
import { GQLStyleCustomization, StyleCustomizationTableProps } from './StyleCustomizationTable.types';

export const StyleCustomizationTable = ({}: StyleCustomizationTableProps) => {
  const { t } = useTranslation('sirius-web-projects-stylecustomizations-application', {
    keyPrefix: 'styleCustomizationTable',
  });
  const localization = useTableTranslation();

  const rows: GQLStyleCustomization[] = [
    {
      name: 'Basic node style customization in diagram',
      description: 'Changes the background color of flow elements when temperature above 50°',
    },
    {
      name: 'Basic edge style customization in diagram',
      description: 'Changes the background color of flow data',
    },
    {
      name: 'Basic button style customization in form',
      description: 'Changes the font of buttons in a form',
    },
  ];

  const columns = useMemo<MRT_ColumnDef<GQLStyleCustomization>[]>(
    () => [
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
    []
  );

  const table = useMaterialReactTable<GQLStyleCustomization>({
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
    enableGlobalFilter: false,

    localization: localization,
  });

  return <MaterialReactTable table={table} />;
};
