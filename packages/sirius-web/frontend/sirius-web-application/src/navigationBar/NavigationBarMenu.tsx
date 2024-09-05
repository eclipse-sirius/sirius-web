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

import { ComponentExtension, useComponent, useComponents, useData } from '@eclipse-sirius/sirius-components-core';
import HelpIcon from '@mui/icons-material/Help';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useState } from 'react';
import { NavigationBarMenuItemProps, NavigationBarMenuProps, NavigationBarMenuState } from './NavigationBarMenu.types';
import {
  navigationBarMenuContainerExtensionPoint,
  navigationBarMenuEntryExtensionPoint,
  navigationBarMenuHelpURLExtensionPoint,
  navigationBarMenuIconExtensionPoint,
} from './NavigationBarMenuExtensionPoints';

export const NavigationBarMenu = ({}: NavigationBarMenuProps) => {
  const [state, setState] = useState<NavigationBarMenuState>({
    menuAnchor: null,
  });

  const { Component: NavigationBarMenuContainer } = useComponent(navigationBarMenuContainerExtensionPoint);
  const { Component: MenuIcon } = useComponent(navigationBarMenuIconExtensionPoint);
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
        data-test-id="navigationbar-menu"
        open={Boolean(state.menuAnchor)}
        anchorEl={state.menuAnchor}
        onClose={handleClose}>
        {menuItemComponentExtensions.map(({ Component: NavigationBarMenuItem }, index) => (
          <NavigationBarMenuItem key={index} />
        ))}
        <MenuItem component="a" href={url} target="_blank" rel="noopener noreferrer">
          <ListItemIcon>
            <HelpIcon />
          </ListItemIcon>
          <ListItemText primary="Help" />
        </MenuItem>
      </Menu>
    </NavigationBarMenuContainer>
  );
};
