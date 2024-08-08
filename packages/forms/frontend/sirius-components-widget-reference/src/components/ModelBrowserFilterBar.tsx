/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
import { Theme } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { ModelBrowserFilterBarProps } from './ModelBrowserFilterBar.types';

const useFilterBarStyles = makeStyles()((theme: Theme) => ({
  filterbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
  },
  textfield: {
    height: theme.spacing(3),
    fontSize: theme.typography.fontSize,
  },
  placeHolderIcon: {
    color: theme.palette.text.disabled,
  },
}));
export const ModelBrowserFilterBar = ({ onTextChange, onTextClear, text }: ModelBrowserFilterBarProps) => {
  const { classes } = useFilterBarStyles();

  return (
    <div className={classes.filterbar}>
      <TextField
        variant="standard"
        id="filterbar-textfield"
        data-testid="filterbar-textfield"
        name="filterbar-textfield"
        placeholder="Type to filter"
        spellCheck={false}
        size="small"
        margin="none"
        autoFocus={true}
        multiline={false}
        fullWidth
        value={text}
        onChange={onTextChange}
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon fontSize="small" className={classes.placeHolderIcon} />
            </InputAdornment>
          ),
          endAdornment: (
            <InputAdornment position="end">
              <IconButton data-testid="filterbar-clear-button" aria-label="clear" size="small" onClick={onTextClear}>
                <ClearIcon fontSize="small" />
              </IconButton>
            </InputAdornment>
          ),
          className: classes.textfield,
        }}
      />
    </div>
  );
};
