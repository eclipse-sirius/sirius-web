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
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import { TextFieldProps } from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import { MaterialReactTable, MRT_Cell, MRT_ColumnDef, MRT_Row, useMaterialReactTable } from 'material-react-table';
import { useEffect, useMemo, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { handleExportData } from './exportHelper';
import {
  GQLCell,
  GQLCheckboxCell,
  GQLLine,
  GQLMultiSelectCell,
  GQLSelectCell,
  GQLTextfieldCell,
  TableProps,
} from './TableContent.types';
import { useEditableCheckboxCell } from './useEditableCheckboxCell';
import { useEditableMultiSelectCell } from './useEditableMultiSelectCell';
import { useEditableSelectCell } from './useEditableSelectCell';
import { useEditableTextfieldCell } from './useEditableTextfieldCell';

const isCheckboxCell = (cell: GQLCell): cell is GQLCheckboxCell => cell.__typename === 'CheckboxCell';
const isSelectCell = (cell: GQLCell): cell is GQLSelectCell => cell.__typename === 'SelectCell';
const isMultiSelectCell = (cell: GQLCell): cell is GQLMultiSelectCell => cell.__typename === 'MultiSelectCell';
const isTextfieldCell = (cell: GQLCell): cell is GQLTextfieldCell => cell.__typename === 'TextfieldCell';

const useStyles = makeStyles()((theme) => ({
  rowMain: {
    '&:hover': {
      backgroundColor: theme.palette.action.hover,
    },
  },
  rowSelected: {
    backgroundColor: theme.palette.action.selected,
    '&:hover': {
      backgroundColor: theme.palette.action.selected,
    },
  },
  cell: {
    '&:hover': {
      outline: `1px solid ${theme.palette.action.selected}`,
    },
  },
}));

export const TableContent = ({ editingContextId, representationId, table, readOnly }: TableProps) => {
  const { classes } = useStyles();
  const { selection, setSelection } = useSelection();

  const [editedLines, setEditedLines] = useState<GQLLine[]>(table.lines);

  const getMuiTextfieldProps = useEditableTextfieldCell(editingContextId, representationId, table.id);
  const getMuiSelectProps = useEditableSelectCell(editingContextId, representationId, table.id);
  const getMuiMultiSelectProps = useEditableMultiSelectCell(editingContextId, representationId, table.id);
  const getMuiCheckboxProps = useEditableCheckboxCell(editingContextId, representationId, table.id);

  useEffect(() => {
    setEditedLines(table.lines);
  }, [table]);

  const getCellLabel = (cell: GQLCell | undefined): string => {
    let value = '';
    if (cell) {
      if (isTextfieldCell(cell)) {
        value = cell.stringValue;
      } else if (isMultiSelectCell(cell)) {
        value = cell.options
          .filter((option) => cell.values.includes(option.id))
          .map((option) => option.label)
          .join(' ,');
      } else if (isSelectCell(cell)) {
        value = cell.options.find((opt) => opt.id == cell.value)?.label ?? '';
      }
    }
    return value;
  };

  const columns = useMemo<MRT_ColumnDef<GQLLine, string>[]>(() => {
    let columnDefs: MRT_ColumnDef<GQLLine, string>[] = table.columns.map((col) => {
      return {
        id: col.id,
        accessorFn: (line: GQLLine): string => {
          return getCellLabel(line.cells.find((cell) => col.id === cell.columnId));
        },
        header: col.label,
        size: 150,
        Cell: ({ row }) => {
          const gqlCell = row.original.cells.find((cell) => col.id === cell.columnId);
          if (gqlCell && isCheckboxCell(gqlCell)) {
            return <Checkbox id={gqlCell.id} checked={gqlCell.booleanValue} />;
          }
          return <Typography>{getCellLabel(gqlCell)}</Typography>;
        },
        muiEditTextFieldProps: (props: { cell: MRT_Cell<GQLLine, string>; row: MRT_Row<GQLLine> }): TextFieldProps => {
          const line: GQLLine = props.row.original;
          const gqlCell: GQLCell | undefined = line.cells.find((cell) => col.id === cell.columnId);

          let muiTextFieldProps: TextFieldProps = {};
          if (gqlCell) {
            if (isTextfieldCell(gqlCell)) {
              muiTextFieldProps = getMuiTextfieldProps(gqlCell, props.cell, props.row, setEditedLines);
            } else if (isMultiSelectCell(gqlCell)) {
              muiTextFieldProps = getMuiMultiSelectProps(gqlCell, props.cell, props.row, setEditedLines);
            } else if (isSelectCell(gqlCell)) {
              muiTextFieldProps = getMuiSelectProps(gqlCell, props.cell, props.row, setEditedLines);
            } else if (isCheckboxCell(gqlCell)) {
              muiTextFieldProps = getMuiCheckboxProps(gqlCell, props.cell, props.row, setEditedLines);
            }
          }

          return muiTextFieldProps;
        },
        muiTableBodyCellProps() {
          return {
            className: classes.cell,
          };
        },
      };
    });

    return columnDefs;
  }, [table, editedLines]);

  const muiTable = useMaterialReactTable({
    columns,
    data: editedLines,
    editDisplayMode: 'cell',
    enableEditing: !readOnly,
    enableStickyHeader: true,
    muiTableBodyRowProps: ({ row }) => {
      const selected = selection?.entries.map((entry) => entry.id).includes(row.original.targetObjectId);
      let classNames = classes.rowMain;
      if (selected) {
        classNames = classes.rowSelected;
      }
      return {
        onClick: () => {
          const newSelection: Selection = { entries: [{ id: row.original.targetObjectId, kind: 'Object' }] };
          setSelection(newSelection);
        },
        className: classNames,
        sx: {
          backgroundColor: 'transparent', // required to remove the default mui backgroundColor that is defined as !important
          cursor: 'pointer',
        },
      };
    },
    renderTopToolbarCustomActions: () => (
      <Box>
        <Button onClick={() => handleExportData(table, getCellLabel)} startIcon={<FileDownloadIcon />}>
          Export All Data
        </Button>
      </Box>
    ),
  });

  return <MaterialReactTable table={muiTable} />;
};
