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
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { download, generateCsv, mkConfig } from 'export-to-csv';
import {
  GQLCell,
  GQLIconLabelCell,
  GQLMultiSelectCell,
  GQLSelectCell,
  GQLTable,
  GQLTextareaCell,
  GQLTextfieldCell,
} from '../table/TableContent.types';
import { CsvData, ExportDataButtonProps } from './ExportDataButton.types';

const isSelectCell = (cell: GQLCell): cell is GQLSelectCell => cell.__typename === 'SelectCell';
const isIconLabelCell = (cell: GQLCell): cell is GQLIconLabelCell => cell.__typename === 'IconLabelCell';
const isMultiSelectCell = (cell: GQLCell): cell is GQLMultiSelectCell => cell.__typename === 'MultiSelectCell';
const isTextfieldCell = (cell: GQLCell): cell is GQLTextfieldCell => cell.__typename === 'TextfieldCell';
const isTextareaCell = (cell: GQLCell): cell is GQLTextareaCell => cell.__typename === 'TextareaCell';

const getCellLabel = (cell: GQLCell | undefined): string => {
  let value = '';
  if (cell) {
    if (isTextfieldCell(cell) || isTextareaCell(cell)) {
      value = cell.stringValue;
    } else if (isIconLabelCell(cell)) {
      value = cell.label;
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

const csvConfig = mkConfig({
  fieldSeparator: ';',
  decimalSeparator: '.',
  useKeysAsHeaders: true,
});

const handleExportData = (table: GQLTable) => {
  const visibleColumns = table.columns.filter((column) => !column.hidden);
  const visibleColumnIds = new Set(visibleColumns.map((col) => col.id));
  const columnOrder = {};
  visibleColumns.forEach((column, index) => (columnOrder[column.id] = index));

  const columnIdToLabel = {};
  visibleColumns.forEach((column) => (columnIdToLabel[column.id] = column.headerLabel));

  const csvData: CsvData[] = table.lines.map((line) => {
    const csvDatum: CsvData = { ['_row_header_']: `${line.headerIndexLabel} ${line.headerLabel}` };
    const sortedVisibleCells = [...line.cells]
      .filter((cell) => visibleColumnIds.has(cell.columnId))
      .sort((a, b) => columnOrder[a.columnId] - columnOrder[b.columnId]);
    sortedVisibleCells.forEach((cell) => (csvDatum[columnIdToLabel[cell.columnId]] = getCellLabel(cell)));
    return csvDatum;
  });

  const csv = generateCsv(csvConfig)(csvData);
  download(csvConfig)(csv);
};

export const ExportDataButton = ({ table }: ExportDataButtonProps) => {
  return (
    <MenuItem onClick={() => handleExportData(table)}>
      <ListItemIcon>
        <FileDownloadIcon fontSize="small" />
      </ListItemIcon>
      <ListItemText>Export page data as CSV</ListItemText>
    </MenuItem>
  );
};
