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
import { FilterList } from '@material-ui/icons';
import ClearIcon from '@material-ui/icons/Clear';
import CloseIcon from '@material-ui/icons/Close';
import SearchIcon from '@material-ui/icons/Search';
import { useState } from 'react';
import { FilterBarProps, FilterBarState } from './FilterBar.types';

const useFilterBarStyles = makeStyles((theme: Theme) => ({
  filterbar: {
    display: 'flex',
    flexDirection: 'row',
    overflow: 'hidden',
    paddingLeft: theme.spacing(1),
    paddingRight: theme.spacing(1),
    paddingBottom: theme.spacing(1),
  },
  textfield: {
    height: theme.spacing(3),
    fontSize: theme.typography.fontSize,
  },
}));
export const FilterBar = ({
  onTextChange,
  onTextClear,
  onFilterButtonClick,
  onClose,
  text,
  options,
}: FilterBarProps) => {
  const classes = useFilterBarStyles();

  const initialState: FilterBarState = {
    filterEnabled: options?.filterEnabled || false,
  };
  const [state, setState] = useState<FilterBarState>(initialState);

  return (
    <div className={classes.filterbar}>
      <TextField
        id="filterbar-textfield"
        data-testid="filterbar-textfield"
        name="filterbar-textfield"
        placeholder="Type to filter"
        spellCheck={false}
        variant={options?.textFieldVariant || 'outlined'}
        size="small"
        margin="none"
        autoFocus={true}
        multiline={false}
        fullWidth
        value={text}
        onChange={onTextChange}
        InputProps={{
          startAdornment: options?.searchIcon && (
            <InputAdornment position="start">
              <SearchIcon fontSize="small" />
            </InputAdornment>
          ),
          endAdornment: (
            <InputAdornment position="end">
              {options?.clearTextButton && (
                <IconButton data-testid="filterbar-clear-button" aria-label="clear" size="small" onClick={onTextClear}>
                  <ClearIcon fontSize="small" />
                </IconButton>
              )}
              {(options?.filterButton !== undefined ? options.filterButton : true) && (
                <IconButton
                  data-testid="filterbar-filter-button"
                  aria-label="filter"
                  size="small"
                  onClick={() => {
                    onFilterButtonClick(!state.filterEnabled);
                    setState((prevState) => {
                      return { filterEnabled: !prevState.filterEnabled };
                    });
                  }}>
                  <FilterList fontSize="small" color={state.filterEnabled ? 'primary' : 'secondary'} />
                </IconButton>
              )}
            </InputAdornment>
          ),
          className: classes.textfield,
        }}
      />
      {(options?.closeButton !== undefined ? options.closeButton : true) && (
        <IconButton
          data-testid="filterbar-close-button"
          size="small"
          aria-label="close"
          color="inherit"
          onClick={onClose}>
          <CloseIcon fontSize="small" />
        </IconButton>
      )}
    </div>
  );
};
