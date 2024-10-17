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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import GetAppIcon from '@mui/icons-material/GetApp';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useContext } from 'react';
import { ProjectContextMenuEntryProps } from './ProjectActionButton.types';

export const ProjectDownloadMenuItemExtension = ({ project }: ProjectContextMenuEntryProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  return (
    <MenuItem
      data-testid="project-download-action"
      component="a"
      href={`${httpOrigin}/api/projects/${project.id}`}
      type="application/octet-stream">
      <ListItemIcon>
        <GetAppIcon />
      </ListItemIcon>
      <ListItemText primary="Download" />
    </MenuItem>
  );
};
