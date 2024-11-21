/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import { MaterialReactTable, useMaterialReactTable } from 'material-react-table';
import { memo } from 'react';
import { makeStyles } from 'tss-react/mui';
import { ExportAllDataButton } from '../actions/ExportAllDataButton';
import { TableProps } from './TableContent.types';
import { useTableColumns } from './useTableColumns';

const useStyles = makeStyles()((theme) => ({
  rowMain: {
    '&:hover': {
      backgroundColor: theme.palette.action.hover,
    },
  },
  cell: {
    '&:hover': {
      outline: `1px solid ${theme.palette.action.selected}`,
    },
  },
}));

export const TableContent = memo(({ editingContextId, representationId, table, readOnly }: TableProps) => {
  const { classes } = useStyles();
  const { selection, setSelection } = useSelection();

  const { columns } = useTableColumns(editingContextId, representationId, table, readOnly);

  const muiTable = useMaterialReactTable({
    columns,
    data: table.lines,
    editDisplayMode: 'cell',
    enableEditing: !readOnly,
    enableStickyHeader: true,
    muiTableBodyRowProps: ({ row }) => {
      return {
        onClick: () => {
          const newSelection: Selection = { entries: [{ id: row.original.targetObjectId, kind: 'Object' }] };
          setSelection(newSelection);
        },
        selected: selection.entries.map((entry) => entry.id).includes(row.original.targetObjectId),
        className: classes.rowMain,
        sx: {
          backgroundColor: 'transparent', // required to remove the default mui backgroundColor that is defined as !important
          cursor: 'pointer',
        },
      };
    },
    renderTopToolbarCustomActions: () => (
      <Box>
        <ExportAllDataButton table={table} />
      </Box>
    ),
  });

  return <MaterialReactTable table={muiTable} />;
});
