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

import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { memo } from 'react';
import {
  GQLCell,
  GQLIconLabelCell,
  GQLMultiSelectCell,
  GQLSelectCell,
  GQLTextareaCell,
  GQLTextfieldCell,
} from '../table/TableContent.types';
import { CellProps } from './Cell.types';
import { IconLabelCell } from './IconLabelCell';
import { MultiSelectCell } from './MultiSelectCell';
import { SelectCell } from './SelectCell';
import { TableCellContribution } from './TableCellContribution.types';
import { tableCellExtensionPoint } from './TableCellExtensionPoints';
import { TextareaCell } from './TextareaCell';
import { TextfieldCell } from './TextfieldCell';

const isSelectCell = (cell: GQLCell): cell is GQLSelectCell => cell.__typename === 'SelectCell';
const isMultiSelectCell = (cell: GQLCell): cell is GQLMultiSelectCell => cell.__typename === 'MultiSelectCell';
const isTextfieldCell = (cell: GQLCell): cell is GQLTextfieldCell => cell.__typename === 'TextfieldCell';
const isTextareaCell = (cell: GQLCell): cell is GQLTextareaCell => cell.__typename === 'TextareaCell';
const isIconLabelCell = (cell: GQLCell): cell is GQLIconLabelCell => cell.__typename === 'IconLabelCell';

export const Cell = memo(({ editingContextId, representationId, tableId, cell, disabled }: CellProps) => {
  const customCells: DataExtension<TableCellContribution[]> = useData(tableCellExtensionPoint);
  if (cell) {
    if (isTextfieldCell(cell)) {
      return (
        <TextfieldCell
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          cell={cell}
          disabled={disabled}
        />
      );
    } else if (isTextareaCell(cell)) {
      return (
        <TextareaCell
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          cell={cell}
          disabled={disabled}
        />
      );
    } else if (isSelectCell(cell)) {
      return (
        <SelectCell
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          cell={cell}
          disabled={disabled}
        />
      );
    } else if (isMultiSelectCell(cell)) {
      return (
        <MultiSelectCell
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          cell={cell}
          disabled={disabled}
        />
      );
    } else if (isIconLabelCell(cell)) {
      return (
        <IconLabelCell
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          cell={cell}
        />
      );
    } else {
      const customCell = customCells.data.find((data) => data.canHandle(cell));
      if (customCell) {
        const CustomCellComponent = customCell.component;
        return (
          <CustomCellComponent
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
            disabled={disabled}
          />
        );
      }
    }
  }
  return <Typography></Typography>;
});
