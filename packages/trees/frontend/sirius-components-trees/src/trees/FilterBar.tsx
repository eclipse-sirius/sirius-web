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
import CloseIcon from '@mui/icons-material/Close';
import FilterList from '@mui/icons-material/FilterList';
import IconButton from '@mui/material/IconButton';
import InputAdornment from '@mui/material/InputAdornment';
import TextField from '@mui/material/TextField';
import { Theme } from '@mui/material/styles';
import { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { FilterBarProps, FilterBarState } from './FilterBar.types';

const useFilterBarStyles = makeStyles()((theme: Theme) => ({
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
export const FilterBar = ({ onTextChange, onFilterButtonClick, onClose }: FilterBarProps) => {
  const { classes } = useFilterBarStyles();

  const initialState: FilterBarState = {
    filterEnabled: false,
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
        variant="outlined"
        size="small"
        margin="none"
        autoFocus={true}
        multiline={false}
        fullWidth
        onChange={onTextChange}
        InputProps={{
          endAdornment: (
            <InputAdornment position="end">
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
            </InputAdornment>
          ),
          className: classes.textfield,
        }}
      />
      <IconButton
        data-testid="filterbar-close-button"
        size="small"
        aria-label="close"
        color="inherit"
        onClick={onClose}>
        <CloseIcon fontSize="small" />
      </IconButton>
    </div>
  );
};
