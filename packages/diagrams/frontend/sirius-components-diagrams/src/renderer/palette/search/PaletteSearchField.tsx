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

import ClearIcon from '@mui/icons-material/Clear';
import SearchIcon from '@mui/icons-material/Search';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import TextField from '@mui/material/TextField';
import { useState } from 'react';
import { PaletteSearchFieldProps, PaletteSearchFieldState } from './PaletteSearchField.types';
export const PaletteSearchField = ({ onValueChanged, onEscape }: PaletteSearchFieldProps) => {
  const [state, setState] = useState<PaletteSearchFieldState>({ value: '' });
  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setState((prevState) => ({ ...prevState, value }));
    onValueChanged(value);
  };

  const onTextClear = (): void => {
    setState((prevState) => ({ ...prevState, value: '' }));
    onValueChanged('');
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLDivElement>): void => {
    if ('Escape' === event.key && onEscape) {
      onEscape();
    }
  };

  return (
    <TextField
      autoFocus={true}
      value={state.value}
      size="small"
      onKeyDown={handleKeyDown}
      onClick={(event) => event.stopPropagation()}
      placeholder="Search Tool"
      InputProps={{
        disableUnderline: true,
        startAdornment: (
          <InputAdornment position="start">
            <SearchIcon fontSize="small" />
          </InputAdornment>
        ),
        endAdornment: (
          <InputAdornment position="end">
            <IconButton aria-label="clear" size="small" onClick={onTextClear}>
              <ClearIcon fontSize="small" />
            </IconButton>
          </InputAdornment>
        ),
      }}
      variant="standard"
      onChange={onChange}
    />
  );
};
