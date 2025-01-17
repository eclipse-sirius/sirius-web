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

import TextField from '@mui/material/TextField';
import React, { useEffect, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { TextareaCellProps, TextareaCellState } from './TextareaCell.types';
import { useEditTextareaCell } from './useEditTextareaCell';

const useTextareaCellStyles = makeStyles()(() => ({
  wrapper: {
    overflow: 'auto',
    width: '100%',
    height: '100%',
  },
}));

export const TextareaCell = ({ editingContextId, representationId, tableId, cell, disabled }: TextareaCellProps) => {
  const { classes } = useTextareaCellStyles();
  const [state, setState] = useState<TextareaCellState>({
    value: cell.stringValue,
  });

  useEffect(() => {
    setState((prevState) => ({ ...prevState, value: cell.stringValue }));
  }, [cell.stringValue]);

  const { editTextareaCell, loading } = useEditTextareaCell(editingContextId, representationId, tableId, cell.id);

  const handleChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const { value } = event.target;
    setState((prevState) => ({ ...prevState, value }));
  };

  const handleBlur: React.FocusEventHandler<HTMLInputElement | HTMLTextAreaElement> = () => {
    editTextareaCell(state.value);
  };

  return (
    <div className={classes.wrapper}>
      <TextField
        multiline
        value={state.value}
        onChange={handleChange}
        onBlur={handleBlur}
        disabled={disabled || loading}
        size="small"
        fullWidth
        variant="standard"
        InputProps={{
          disableUnderline: true,
          sx: {
            marginBottom: 0,
            padding: '0px',
          },
        }}
        inputProps={{
          sx: {
            padding: '0px',
          },
        }}
      />
    </div>
  );
};
