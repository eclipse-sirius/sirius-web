/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import { ComponentExtension, useComponent, useComponents, useData } from '@eclipse-sirius/sirius-components-core';
import FileCopyIcon from '@mui/icons-material/FileCopy';
import FolderIcon from '@mui/icons-material/Folder';
import HelpIcon from '@mui/icons-material/Help';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link as RouterLink } from 'react-router-dom';
import { useCurrentViewer } from '../viewer/useCurrentViewer';
import { NavigationBarMenuItemProps, NavigationBarMenuProps, NavigationBarMenuState } from './NavigationBarMenu.types';
import {
  navigationBarMenuContainerExtensionPoint,
  navigationBarMenuEntryExtensionPoint,
  navigationBarMenuHeaderExtensionPoint,
  navigationBarMenuHelpURLExtensionPoint,
  navigationBarMenuIconExtensionPoint,
} from './NavigationBarMenuExtensionPoints';

export const NavigationBarMenu = ({}: NavigationBarMenuProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'navigationBarMenu' });
  const [state, setState] = useState<NavigationBarMenuState>({
    menuAnchor: null,
  });

  const {
    viewer: {
      capabilities: {
        libraries: { canList: canListLibrary },
        projects: { canList: canListProject },
      },
    },
  } = useCurrentViewer();

  const { Component: NavigationBarMenuContainer } = useComponent(navigationBarMenuContainerExtensionPoint);
  const { Component: MenuIcon } = useComponent(navigationBarMenuIconExtensionPoint);
  const { Component: NavigationBarMenuHeader } = useComponent(navigationBarMenuHeaderExtensionPoint);
  const { data: url } = useData(navigationBarMenuHelpURLExtensionPoint);
  const menuItemComponentExtensions: ComponentExtension<NavigationBarMenuItemProps>[] = useComponents(
    navigationBarMenuEntryExtensionPoint
  );

  const handleClick = (event: React.MouseEvent<HTMLElement>) =>
    setState((prevState) => ({ ...prevState, menuAnchor: event.currentTarget }));
  const handleClose = () => setState((prevState) => ({ ...prevState, menuAnchor: null }));

  return (
    <NavigationBarMenuContainer>
      <MenuIcon onClick={handleClick} />

      <Menu
        data-testid="navigationbar-menu"
        open={Boolean(state.menuAnchor)}
        anchorEl={state.menuAnchor}
        onClose={handleClose}>
        <NavigationBarMenuHeader />
        {canListLibrary ? (
          <MenuItem component={RouterLink} to="/libraries" data-testid="libraries-link">
            <ListItemIcon>
              <FileCopyIcon />
            </ListItemIcon>
            <ListItemText primary={t('libraries')} />
          </MenuItem>
        ) : null}
        {canListProject ? (
          <MenuItem component={RouterLink} to="/projects" data-testid="projects-link">
            <ListItemIcon>
              <FolderIcon />
            </ListItemIcon>
            <ListItemText primary={t('projects')} />
          </MenuItem>
        ) : null}
        {menuItemComponentExtensions.map(({ Component: NavigationBarMenuItem }, index) => (
          <NavigationBarMenuItem key={index} />
        ))}
        <MenuItem component="a" href={url} target="_blank" rel="noopener noreferrer">
          <ListItemIcon>
            <HelpIcon />
          </ListItemIcon>
          <ListItemText primary={t('help')} />
        </MenuItem>
      </Menu>
    </NavigationBarMenuContainer>
  );
};
