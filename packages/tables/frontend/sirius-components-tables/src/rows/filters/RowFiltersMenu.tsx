/*******************************************************************************
 * Copyright (c) 2025 CEA List.
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

import TableRowsIcon from '@mui/icons-material/TableRows';
import FormControlLabel from '@mui/material/FormControlLabel';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import Switch from '@mui/material/Switch';
import Tooltip from '@mui/material/Tooltip';
import { MouseEvent, useState } from 'react';
import { RowFiltersButtonProps, RowFiltersMenuState } from './RowFiltersMenu.types';

export const RowFiltersMenu = ({
  readOnly,
  rowFilters,
  activeRowFilterIds,
  onRowFiltersChange,
}: RowFiltersButtonProps) => {
  const initialState: RowFiltersMenuState = {
    anchorEl: null,
  };

  const [state, setState] = useState<RowFiltersMenuState>(initialState);

  const handleOpenRowFiltersMenu = (event: MouseEvent<HTMLElement>) => {
    setState((prevState) => ({ ...prevState, anchorEl: event.currentTarget }));
  };

  const handleToggleFilter = (filterId: string) => {
    let updatedActiveIds: string[] = [];
    if (activeRowFilterIds.includes(filterId)) {
      updatedActiveIds = activeRowFilterIds.filter((id) => id !== filterId);
    } else {
      updatedActiveIds = [...activeRowFilterIds, filterId];
    }
    onRowFiltersChange(updatedActiveIds);
  };

  return (
    <>
      {rowFilters.length > 0 ? (
        <Tooltip disableInteractive title="Row filters">
          <IconButton aria-label="Row filters" data-testid="row-filters-button" onClick={handleOpenRowFiltersMenu}>
            <TableRowsIcon />
          </IconButton>
        </Tooltip>
      ) : null}
      {state.anchorEl != null ? (
        <Menu
          anchorEl={state.anchorEl}
          disableScrollLock
          onClick={(event) => event.stopPropagation()}
          onClose={() => setState((prevState) => ({ ...prevState, anchorEl: null }))}
          open
          data-testid={'row-filters-menu'}>
          {rowFilters.map((filter) => (
            <MenuItem key={filter.id}>
              <FormControlLabel
                data-testid={`row-filter-${filter.id}`}
                checked={activeRowFilterIds.includes(filter.id)}
                control={
                  <Tooltip title={`Toggle '${filter.label}'`}>
                    <Switch readOnly={readOnly} />
                  </Tooltip>
                }
                label={filter.label}
                onChange={() => handleToggleFilter(filter.id)}
              />
            </MenuItem>
          ))}
        </Menu>
      ) : null}
    </>
  );
};
