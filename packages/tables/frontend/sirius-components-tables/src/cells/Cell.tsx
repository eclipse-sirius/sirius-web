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

import Typography from '@mui/material/Typography';
import { memo } from 'react';
import {
  GQLCell,
  GQLCheckboxCell,
  GQLIconLabelCell,
  GQLMultiSelectCell,
  GQLSelectCell,
  GQLTextareaCell,
  GQLTextfieldCell,
} from '../table/TableContent.types';
import { CellProps } from './Cell.types';
import { CheckboxCell } from './CheckboxCell';
import { IconLabelCell } from './IconLabelCell';
import { MultiSelectCell } from './MultiSelectCell';
import { SelectCell } from './SelectCell';
import { TextareaCell } from './TextareaCell';
import { TextfieldCell } from './TextfieldCell';

const isCheckboxCell = (cell: GQLCell): cell is GQLCheckboxCell => cell.__typename === 'CheckboxCell';
const isSelectCell = (cell: GQLCell): cell is GQLSelectCell => cell.__typename === 'SelectCell';
const isMultiSelectCell = (cell: GQLCell): cell is GQLMultiSelectCell => cell.__typename === 'MultiSelectCell';
const isTextfieldCell = (cell: GQLCell): cell is GQLTextfieldCell => cell.__typename === 'TextfieldCell';
const isTextareaCell = (cell: GQLCell): cell is GQLTextareaCell => cell.__typename === 'TextareaCell';
const isIconLabelCell = (cell: GQLCell): cell is GQLIconLabelCell => cell.__typename === 'IconLabelCell';

export const Cell = memo(({ editingContextId, representationId, tableId, cell, disabled }: CellProps) => {
  if (cell) {
    if (isCheckboxCell(cell)) {
      return (
        <CheckboxCell
          editingContextId={editingContextId}
          representationId={representationId}
          tableId={tableId}
          cell={cell}
          disabled={disabled}
        />
      );
    } else if (isTextfieldCell(cell)) {
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
    }
  }
  return <Typography></Typography>;
});
