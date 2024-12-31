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
import TextField from '@mui/material/TextField';
import { useEffect, useState } from 'react';
import { TextfieldCellProps, TextfieldCellState } from './TextfieldCell.types';
import { useEditTextfieldCell } from './useEditTextfieldCell';

export const TextfieldCell = ({ editingContextId, representationId, tableId, cell, disabled }: TextfieldCellProps) => {
  const [state, setState] = useState<TextfieldCellState>({
    value: cell.stringValue,
  });

  useEffect(() => {
    setState((prevState) => ({ ...prevState, value: cell.stringValue }));
  }, [cell.stringValue]);

  const { editTextfieldCell, loading } = useEditTextfieldCell(editingContextId, representationId, tableId, cell.id);

  const handleChange: React.ChangeEventHandler<HTMLInputElement | HTMLTextAreaElement> = (event) => {
    const { value } = event.target;
    setState((prevState) => ({ ...prevState, value }));
  };

  const handleBlur: React.FocusEventHandler<HTMLInputElement | HTMLTextAreaElement> = () => {
    editTextfieldCell(state.value);
  };

  const handleEnterKeyDown: React.KeyboardEventHandler<HTMLDivElement> = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      editTextfieldCell(state.value);
    }
  };

  const { setSelection } = useSelection();
  const onClick = () => {
    const newSelection: Selection = { entries: [{ id: cell.targetObjectId, kind: cell.targetObjectKind }] };
    setSelection(newSelection);
  };

  return (
    <TextField
      value={state.value}
      onChange={handleChange}
      onBlur={handleBlur}
      onKeyDown={handleEnterKeyDown}
      onClick={onClick}
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
  );
};
