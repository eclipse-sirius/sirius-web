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

import UnfoldMore from '@mui/icons-material/UnfoldMore';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { ExpandAllMenuItemProps } from './ExpandAllMenuItem.types';

export const ExpandAllMenuItem = ({ item, onExpandAll, onClick }: ExpandAllMenuItemProps) => {
  if (!item.hasChildren) {
    return null;
  }
  return (
    <MenuItem
      key="expand-all"
      data-testid="expand-all"
      onClick={() => {
        onExpandAll(item);
        onClick();
      }}
      aria-disabled>
      <ListItemIcon>
        <UnfoldMore fontSize="small" />
      </ListItemIcon>
      <ListItemText primary="Expand all" />
    </MenuItem>
  );
};
