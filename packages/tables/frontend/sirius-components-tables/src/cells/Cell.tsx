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

import { Selection, useSelection } from '@eclipse-sirius/sirius-components-core';
import Typography from '@mui/material/Typography';
import { memo } from 'react';
import { makeStyles } from 'tss-react/mui';
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

const useStyles = makeStyles()((theme) => ({
  wrapper: {
    padding: theme.spacing(2),
    height: '100%',
    width: '100%',
    cursor: 'pointer',
  },
}));

export const Cell = memo(
  ({ editingContextId, representationId, tableId, cell, disabled, enableSelectionSynchronization }: CellProps) => {
    const { classes } = useStyles();

    const { setSelection } = useSelection();
    const onClick = (cell: GQLCell) => {
      if (enableSelectionSynchronization) {
        const newSelection: Selection = { entries: [{ id: cell.targetObjectId }] };
        setSelection(newSelection);
      }
    };

    let component = <Typography />;

    if (cell) {
      if (isCheckboxCell(cell)) {
        component = (
          <CheckboxCell
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
            disabled={disabled}
          />
        );
      } else if (isTextfieldCell(cell)) {
        component = (
          <TextfieldCell
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
            disabled={disabled}
          />
        );
      } else if (isTextareaCell(cell)) {
        component = (
          <TextareaCell
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
            disabled={disabled}
          />
        );
      } else if (isSelectCell(cell)) {
        component = (
          <SelectCell
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
            disabled={disabled}
          />
        );
      } else if (isMultiSelectCell(cell)) {
        component = (
          <MultiSelectCell
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
            disabled={disabled}
          />
        );
      } else if (isIconLabelCell(cell)) {
        component = (
          <IconLabelCell
            editingContextId={editingContextId}
            representationId={representationId}
            tableId={tableId}
            cell={cell}
          />
        );
      }
      return (
        <div className={classes.wrapper} onClick={() => onClick(cell)}>
          {component}
        </div>
      );
    }
    return component;
  }
);
