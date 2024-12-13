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
import { memo, useEffect, useState } from 'react';
import { SettingsButton } from '../actions/SettingsButton';
import { GQLLine, ClientSideTableProps } from './TableContent.types';
import { useTableColumns } from './useTableColumns';

export const ClientSideTableContent = memo(
  ({ editingContextId, representationId, table, readOnly }: ClientSideTableProps) => {
    const { selection, setSelection } = useSelection();
    const [linesState, setLinesState] = useState<GQLLine[]>(table.lines);

    useEffect(() => {
      setLinesState([...table.lines]);
    }, [table]);

    const { columns } = useTableColumns(editingContextId, representationId, table, readOnly);

    const tableOptions: MRT_TableOptions<GQLLine> = {
      columns,
      data: linesState,
      editDisplayMode: 'cell',
      enableEditing: !readOnly,
      enableStickyHeader: true,
      rowCount: linesState.length,
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
            height: row.original.height,
          },
        };
      },
      renderTopToolbarCustomActions: () => (
        <Box>
          <SettingsButton editingContextId={editingContextId} representationId={representationId} table={table} />
        </Box>
      ),
    };

    const muiTable = useMaterialReactTable(tableOptions);
    return (
      <div>
        <MaterialReactTable table={muiTable} />
      </div>
    );
  }
);
