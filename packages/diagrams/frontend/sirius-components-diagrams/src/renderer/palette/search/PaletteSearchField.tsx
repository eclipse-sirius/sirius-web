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

import ClearIcon from '@mui/icons-material/Clear';
import SearchIcon from '@mui/icons-material/Search';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import TextField from '@mui/material/TextField';
import { useRef, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PaletteSearchFieldProps, PaletteSearchFieldState } from './PaletteSearchField.types';

const usePaletteSearchFieldStyle = makeStyles()((theme) => ({
  paletteSearchField: {
    '& .MuiInputBase-root': {
      backgroundColor: `${theme.palette.secondary.main}08`,
      borderWidth: '0px',
    },
    marginLeft: theme.spacing(1),
    marginRight: theme.spacing(1),
    marginBottom: theme.spacing(0.5),
    marginTop: theme.spacing(0.5),
  },
}));

export const PaletteSearchField = ({ onValueChanged }: PaletteSearchFieldProps) => {
  const [state, setState] = useState<PaletteSearchFieldState>({ value: '' });
  const ref = useRef<HTMLDivElement | null>(null);

  const { classes } = usePaletteSearchFieldStyle();

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { value } = event.target;
    setState((prevState) => ({ ...prevState, value }));
    onValueChanged(value);
  };

  const onTextClear = (): void => {
    setState((prevState) => ({ ...prevState, value: '' }));
    onValueChanged('');
    if (ref.current) {
      ref.current.focus();
    }
  };

  return (
    <TextField
      autoFocus
      value={state.value}
      size="small"
      onClick={(event) => event.stopPropagation()}
      placeholder="Search Tool"
      className={classes.paletteSearchField}
      ref={ref}
      InputProps={{
        disableUnderline: true,
        startAdornment: (
          <InputAdornment position="start">
            <SearchIcon fontSize="small" />
          </InputAdornment>
        ),
        endAdornment: state.value ? (
          <InputAdornment position="end">
            <IconButton aria-label="clear" size="small" onClick={onTextClear}>
              <ClearIcon fontSize="small" />
            </IconButton>
          </InputAdornment>
        ) : null,
      }}
      variant="standard"
      onChange={onChange}
    />
  );
};
