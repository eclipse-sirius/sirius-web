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
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem, { MenuItemProps } from '@material-ui/core/MenuItem';
import HelpIcon from '@material-ui/icons/Help';
import { useState } from 'react';
import { NavigationBarMenuProps, NavigationBarMenuState } from './NavigationBarMenu.types';
import {
  navigationBarMenuEntryExtensionPoint,
  navigationBarMenuHelpURLExtensionPoint,
  navigationBarMenuIconExtensionPoint,
} from './NavigationBarMenuExtensionPoints';

export const NavigationBarMenu = ({}: NavigationBarMenuProps) => {
  const [state, setState] = useState<NavigationBarMenuState>({
    menuAnchor: null,
  });

  const { Component: MenuIcon } = useComponent(navigationBarMenuIconExtensionPoint);
  const { data: url } = useData(navigationBarMenuHelpURLExtensionPoint);
  const menuItemProps: ComponentExtension<MenuItemProps>[] = useComponents(navigationBarMenuEntryExtensionPoint);

  const handleClick = (event: React.MouseEvent<HTMLElement>) =>
    setState((prevState) => ({ ...prevState, menuAnchor: event.currentTarget }));
  const handleClose = () => setState((prevState) => ({ ...prevState, menuAnchor: null }));

  return (
    <>
      <MenuIcon onClick={handleClick} />

      <Menu
        data-test-id="navigationbar-menu"
        open={Boolean(state.menuAnchor)}
        anchorEl={state.menuAnchor}
        onClose={handleClose}>
        <MenuItem component="a" href={url} target="_blank" rel="noopener noreferrer">
          <ListItemIcon>
            <HelpIcon />
          </ListItemIcon>
          <ListItemText primary="Help" />
        </MenuItem>
        {menuItemProps.map((props, index) => (
          <MenuItem {...props} key={index} />
        ))}
      </Menu>
    </>
  );
};
