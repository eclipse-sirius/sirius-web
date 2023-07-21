/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import IconButton from '@material-ui/core/IconButton';
import InputAdornment from '@material-ui/core/InputAdornment';
import { makeStyles, Theme } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import ClearIcon from '@material-ui/icons/Clear';
import SearchIcon from '@material-ui/icons/Search';
import { ModelBrowserFilterBarProps } from './ModelBrowserFilterBar.types';

const useFilterBarStyles = makeStyles((theme: Theme) => ({
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
  const classes = useFilterBarStyles();

  return (
    <div className={classes.filterbar}>
      <TextField
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
