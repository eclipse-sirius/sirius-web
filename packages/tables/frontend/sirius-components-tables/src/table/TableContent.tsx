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
import { MaterialReactTable, MRT_TableOptions, useMaterialReactTable } from 'material-react-table';
import { memo } from 'react';
import { SettingsButton } from '../actions/SettingsButton';
import { useTableColumnSizing } from '../columns/useTableColumnSizing';
import { useTableColumnVisibility } from '../columns/useTableColumnVisibility';
import { RowHeader } from '../rows/RowHeader';
import { GQLLine, TableProps } from './TableContent.types';
import { useTableColumns } from './useTableColumns';

export const TableContent = memo(({ editingContextId, representationId, table, readOnly }: TableProps) => {
  const { selection, setSelection } = useSelection();

  const { columns } = useTableColumns(editingContextId, representationId, table, readOnly);
  const { columnSizing, setColumnSizing } = useTableColumnSizing(editingContextId, representationId, table);
  const { columnVisibility, setColumnVisibility } = useTableColumnVisibility(editingContextId, representationId, table);

  const tableOptions: MRT_TableOptions<GQLLine> = {
    columns,
    data: table.lines,
    editDisplayMode: 'cell',
    enableEditing: !readOnly,
    enableStickyHeader: true,
    enableRowActions: true,
    enableColumnResizing: true,
    columnResizeMode: 'onEnd',
    enableSorting: false,
    onColumnSizingChange: setColumnSizing,
    onColumnVisibilityChange: setColumnVisibility,
    state: { columnSizing, columnVisibility },
    muiTableBodyRowProps: ({ row }) => {
      return {
        onClick: () => {
          const newSelection: Selection = { entries: [{ id: row.original.targetObjectId, kind: 'Object' }] };
          setSelection(newSelection);
        },
        selected: selection.entries.map((entry) => entry.id).includes(row.original.targetObjectId),
        sx: {
          backgroundColor: 'transparent', // required to remove the default mui backgroundColor that is defined as !important
          cursor: 'pointer',
        },
      };
    },
    renderTopToolbarCustomActions: () => (
      <Box>
        <SettingsButton editingContextId={editingContextId} representationId={representationId} table={table} />
      </Box>
    ),
    displayColumnDefOptions: {
      'mrt-row-actions': {
        header: '',
        size: 120,
      },
    },
    renderRowActions: ({ row }) => <RowHeader row={row.original} />,
  };

  if (table.stripeRow) {
    tableOptions.muiTableBodyProps = {
      sx: {
        '& tr:nth-of-type(odd) > td': {
          backgroundColor: '#f5f5f5',
        },
      },
    };
  }

  const muiTable = useMaterialReactTable(tableOptions);
  return <MaterialReactTable table={muiTable} />;
});
