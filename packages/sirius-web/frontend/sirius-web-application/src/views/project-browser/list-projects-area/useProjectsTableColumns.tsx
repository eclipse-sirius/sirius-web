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
import { useData } from '@eclipse-sirius/sirius-components-core';
import Link from '@mui/material/Link';
import { MRT_ColumnDef } from 'material-react-table';
import { useMemo } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { GQLProject } from './useProjects.types';
import { UseProjectsTableColumnsValue } from './useProjectsTableColumns.types';
import { projectsTableColumnCustomizersExtensionPoint } from './useProjectsTableColumnsExtensionPoints';

export const useProjectsTableColumns = (): UseProjectsTableColumnsValue => {
  let columnDefinitions: MRT_ColumnDef<GQLProject>[] = [
    {
      header: 'Name',
      size: 200,
      grow: true,
      Cell: ({ row }) => (
        <Link
          component={RouterLink}
          to={`/projects/${row.original.id}/edit`}
          sx={{
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            color: 'inherit',
          }}>
          {row.original.name}
        </Link>
      ),
    },
  ];

  const { data: customizers } = useData(projectsTableColumnCustomizersExtensionPoint);
  customizers.forEach((customizer) => {
    columnDefinitions = customizer.customize(columnDefinitions);
  });

  const columns = useMemo(() => columnDefinitions, []);

  return {
    columns,
  };
};
