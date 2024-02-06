/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { useComponent } from '@eclipse-sirius/sirius-components-core';
import AppBar from '@material-ui/core/AppBar';
import IconButton from '@material-ui/core/IconButton';
import Link from '@material-ui/core/Link';
import Toolbar from '@material-ui/core/Toolbar';
import Tooltip from '@material-ui/core/Tooltip';
import { emphasize, makeStyles } from '@material-ui/core/styles';
import { Link as RouterLink } from 'react-router-dom';
import { NavigationBarProps } from './NavigationBar.types';
import { navigationBarIconExtensionPoint, navigationBarMenuExtensionPoint } from './NavigationBarExtensionPoints';

const useNavigationBarStyles = makeStyles((theme) => ({
  navbar: {
    display: 'flex',
    flexDirection: 'column',
  },
  appBarHeader: {
    height: '4px',
    backgroundColor: theme.palette.navigationBar.border,
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    backgroundColor: theme.palette.navigationBar.background,
  },
  left: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  link: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  right: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
  },
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

export const NavigationBar = ({ children }: NavigationBarProps) => {
  const classes = useNavigationBarStyles();

  const { Component: Icon } = useComponent(navigationBarIconExtensionPoint);
  const { Component: Menu } = useComponent(navigationBarMenuExtensionPoint);

  return (
    <div className={classes.navbar}>
      <div className={classes.appBarHeader}></div>
      <AppBar position="static">
        <Toolbar className={classes.toolbar} variant="dense">
          <div className={classes.left}>
            <Tooltip title="Back to the homepage">
              <Link component={RouterLink} to="/" className={classes.link} color="inherit">
                <IconButton className={classes.onDarkBackground} color="inherit">
                  <Icon />
                </IconButton>
              </Link>
            </Tooltip>
          </div>
          {children}
          <div className={classes.right}>
            <Menu />
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
};
