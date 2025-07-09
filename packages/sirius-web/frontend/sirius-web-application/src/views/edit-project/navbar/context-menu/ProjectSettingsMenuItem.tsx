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

import SettingsIcon from '@mui/icons-material/Settings';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { Link as RouterLink } from 'react-router-dom';
import { ProjectSettingsMenuItemProps } from './ProjectSettingsMenuItem.types';

export const ProjectSettingsMenuItem = ({ project, onClick }: ProjectSettingsMenuItemProps) => {
  return (
    <MenuItem
      component={RouterLink}
      to={`/projects/${project.id}/settings`}
      onClick={onClick}
      data-testid="project-settings-link">
      <ListItemIcon>
        <SettingsIcon />
      </ListItemIcon>
      <ListItemText primary="Settings" />
    </MenuItem>
  );
};
