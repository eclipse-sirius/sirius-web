/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import ExpandMoreOutlinedIcon from '@mui/icons-material/ExpandMoreOutlined';
import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { ManageVisibilityMenuCheckBoxProps } from './ManageVisibilityModalMenuCheckBox.types';

export const ManageVisibilityModalMenuCheckBox = ({
  isOneElementChecked,
  onCheckingAllElement,
}: ManageVisibilityMenuCheckBoxProps) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <Box
      sx={{
        display: 'flex',
        justifyContent: 'left',
        alignItems: 'center',
      }}>
      <Checkbox
        disableRipple
        checked={isOneElementChecked}
        onChange={(_event, checked) => onCheckingAllElement(checked)}
        data-testid="manage_visibility_checkbox"
      />

      <IconButton aria-label="filter-button" color="primary" onClick={handleClick} data-testid="manage_visibility_menu">
        <ExpandMoreOutlinedIcon />
      </IconButton>

      <Menu id="filter-menu" anchorEl={anchorEl} open={anchorEl !== null} onClose={handleClose}>
        <MenuItem onClick={() => onCheckingAllElement(true)} data-testid="manage_visibility_menu_hide_all_action">
          All
        </MenuItem>
        <MenuItem onClick={() => onCheckingAllElement(false)} data-testid="manage_visibility_menu_reveal_all_action">
          None
        </MenuItem>
      </Menu>
    </Box>
  );
};
