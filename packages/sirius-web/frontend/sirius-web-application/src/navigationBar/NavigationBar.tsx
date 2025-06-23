/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
import Toolbar from '@mui/material/Toolbar';
import { Theme } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { NavigationBarProps } from './NavigationBar.types';
import {
  navigationBarCenterContributionExtensionPoint,
  navigationBarIconExtensionPoint,
  navigationBarLeftContributionExtensionPoint,
  navigationBarRightContributionExtensionPoint,
} from './NavigationBarExtensionPoints';
import { NavigationBarMenu } from './NavigationBarMenu';

export const useNavigationBarStyles = makeStyles()((theme: Theme) => ({
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
    justifyContent: 'flex-start',
    gap: theme.spacing(1),
  },
  right: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'flex-end',
    gridColumnStart: 3,
    gap: theme.spacing(1),
  },
}));

export const NavigationBar = ({ children }: NavigationBarProps) => {
  const { classes } = useNavigationBarStyles();

  const { Component: NavigationBarIcon } = useComponent(navigationBarIconExtensionPoint);
  const leftContributions = useComponents(navigationBarLeftContributionExtensionPoint);
  const rightContributions = useComponents(navigationBarRightContributionExtensionPoint);
  const { Component: CenterContribution } = useComponent(navigationBarCenterContributionExtensionPoint);

  return (
    <div className={classes.navbar}>
      <div className={classes.appBarHeader}></div>
      <AppBar position="static" data-testid="navigation-bar">
        <Toolbar className={classes.toolbar} variant="dense">
          <div className={classes.left}>
            <NavigationBarIcon />
            {leftContributions.map(({ Component: LeftContribution }, index) => (
              <LeftContribution key={index} />
            ))}
          </div>
          <CenterContribution children={children} />
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
