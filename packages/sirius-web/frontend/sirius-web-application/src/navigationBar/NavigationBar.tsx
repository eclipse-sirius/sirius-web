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
import { useComponent, useComponents } from '@eclipse-sirius/sirius-components-core';
import AppBar from '@mui/material/AppBar';
import IconButton from '@mui/material/IconButton';
import Link from '@mui/material/Link';
import Toolbar from '@mui/material/Toolbar';
import Tooltip from '@mui/material/Tooltip';
import { emphasize } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { Link as RouterLink } from 'react-router-dom';
import { NavigationBarProps } from './NavigationBar.types';
import {
  navigationBarIconExtensionPoint,
  navigationBarLeftContributionExtensionPoint,
  navigationBarRightContributionExtensionPoint,
} from './NavigationBarExtensionPoints';
import { NavigationBarMenu } from './NavigationBarMenu';

const useNavigationBarStyles = makeStyles()((theme) => ({
  navbar: {
    display: 'flex',
    flexDirection: 'column',
  },
  appBarHeader: {
    height: '4px',
    backgroundColor: theme.palette.navigationBar.border,
  },
  toolbar: {
    display: 'grid',
    gridTemplateColumns: '1fr min-content 1fr',
    backgroundColor: theme.palette.navigationBar.background,
  },
  left: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    gap: theme.spacing(1),
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
    gap: theme.spacing(1),
  },
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

export const NavigationBar = ({ children }: NavigationBarProps) => {
  const { classes } = useNavigationBarStyles();

  const { Component: Icon } = useComponent(navigationBarIconExtensionPoint);

  const leftContributions = useComponents(navigationBarLeftContributionExtensionPoint);
  const rightContributions = useComponents(navigationBarRightContributionExtensionPoint);

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
            {leftContributions.map(({ Component: LeftContribution }, index) => (
              <LeftContribution key={index} />
            ))}
          </div>
          <div>{children}</div>
          <div className={classes.right}>
            {rightContributions.map(({ Component: RightContribution }, index) => (
              <RightContribution key={index} />
            ))}
            <NavigationBarMenu />
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
};
