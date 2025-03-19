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

import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { SelectCellProps } from './SelectCell.types';
import { useEditSelectCell } from './useEditSelectCell';

export const SelectCell = ({ editingContextId, representationId, tableId, cell, disabled }: SelectCellProps) => {
  const { editSelectCell, loading } = useEditSelectCell(editingContextId, representationId, tableId, cell.id);

  const handleChange = (event: SelectChangeEvent<string>) => {
    editSelectCell(event.target.value);
  };

  return (
    <Select value={cell.value} onChange={handleChange} disabled={disabled || loading} size="small" fullWidth>
      {cell.options.map((option) => {
        return (
          <MenuItem key={option.id} value={option.id}>
            <Typography>{option.label}</Typography>
          </MenuItem>
        );
      })}
    </Select>
  );
};
