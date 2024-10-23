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

import Checkbox from '@mui/material/Checkbox';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Typography from '@mui/material/Typography';
import { MultiSelectCellProps } from './MultiSelectCell.types';
import { useEditMultiSelectCell } from './useEditMultiSelectCell';

export const MultiSelectCell = ({
  editingContextId,
  representationId,
  tableId,
  cell,
  disabled,
}: MultiSelectCellProps) => {
  const { editMultiSelectCell, loading } = useEditMultiSelectCell(editingContextId, representationId, tableId, cell.id);

  const handleChange = (event: SelectChangeEvent<string[]>) => {
    const { value } = event.target;

    let values: string[] = [];
    if (typeof value === 'string') {
      values = value.split(',').map((entry) => entry.trim());
    } else {
      values = value;
    }

    editMultiSelectCell(values);
  };

  return (
    <Select
      value={cell.values}
      renderValue={(value: string[]) =>
        cell.options
          .filter((option) => value.includes(option.id))
          .map((option) => option.label)
          .join(', ')
      }
      onChange={handleChange}
      disabled={disabled || loading}
      multiple
      size="small"
      fullWidth>
      {cell.options.map((option) => {
        return (
          <MenuItem key={option.id} value={option.id}>
            <Checkbox checked={cell.values.includes(option.id)} />
            <Typography>{option.label}</Typography>
          </MenuItem>
        );
      })}
    </Select>
  );
};
