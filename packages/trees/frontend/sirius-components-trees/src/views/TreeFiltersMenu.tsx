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

import FilterListIcon from '@mui/icons-material/FilterList';
import Checkbox from '@mui/material/Checkbox';
import ClickAwayListener from '@mui/material/ClickAwayListener';
import Fade from '@mui/material/Fade';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormGroup from '@mui/material/FormGroup';
import IconButton from '@mui/material/IconButton';
import Paper from '@mui/material/Paper';
import Popper from '@mui/material/Popper';
import { makeStyles } from 'tss-react/mui';
import { useEffect, useRef, useState } from 'react';
import { TreeFilter } from './ExplorerView.types';
import { TreeFilterMenuProps } from './TreeFiltersMenu.types';

const useTreeFiltersMenuStyles = makeStyles()((_) => ({
  root: {
    display: 'flex',
  },
  treeFilter: {
    marginLeft: '0px',
  },
}));

export const TreeFiltersMenu = ({ filters, onTreeFilterMenuItemClick }: TreeFilterMenuProps) => {
  const { classes } = useTreeFiltersMenuStyles();
  const [open, setOpen] = useState<boolean>(false);
  const anchorRef = useRef<HTMLButtonElement | null>(null);

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event: MouseEvent | TouchEvent) => {
    if (anchorRef.current && anchorRef.current.contains(event.target as HTMLElement)) {
      return;
    }
    setOpen(false);
  };

  const prevOpen = useRef<boolean>(open);
  useEffect(() => {
    if (prevOpen.current === true && open === false && anchorRef.current) {
      anchorRef.current.focus();
    }
    prevOpen.current = open;
  }, [open]);

  const updateTreeFilters = (filter: TreeFilter, newState: boolean): void => {
    const updatedFilters = filters.map((f) => (f.id === filter.id ? { ...f, state: newState } : f));
    onTreeFilterMenuItemClick(updatedFilters);
  };

  return (
    <div>
      <IconButton
        data-testid={`tree-filter-menu-icon`}
        color="inherit"
        size="small"
        ref={anchorRef}
        aria-haspopup="true"
        title="Filters"
        onClick={handleToggle}>
        <FilterListIcon color={open ? 'disabled' : 'inherit'} />
      </IconButton>
      <Popper
        data-testid={`tree-filter-menu`}
        open={open}
        anchorEl={anchorRef.current}
        role={undefined}
        placement={'bottom-start'}>
        {({ TransitionProps }) => (
          <Fade {...TransitionProps} timeout={350}>
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <FormGroup>
                  {filters.map((filter) => (
                    <FormControlLabel
                      key={filter.id}
                      className={classes.treeFilter}
                      control={
                        <Checkbox
                          data-testid={`tree-filter-menu-checkbox-${filter.label}`}
                          checked={filter.state}
                          onChange={(event) => updateTreeFilters(filter, event.target.checked)}
                          name={filter.label}
                        />
                      }
                      label={filter.label}
                    />
                  ))}
                </FormGroup>
              </ClickAwayListener>
            </Paper>
          </Fade>
        )}
      </Popper>
    </div>
  );
};
