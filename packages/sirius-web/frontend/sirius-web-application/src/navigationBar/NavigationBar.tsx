/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import AppBar from '@material-ui/core/AppBar';
import IconButton from '@material-ui/core/IconButton';
import Link from '@material-ui/core/Link';
import Toolbar from '@material-ui/core/Toolbar';
import Tooltip from '@material-ui/core/Tooltip';
import { emphasize, makeStyles } from '@material-ui/core/styles';
import { useContext } from 'react';
import { Link as RouterLink } from 'react-router-dom';
import { ViewsContext } from '../views/ViewsContext';
import { ViewsContextValue } from '../views/ViewsContext.types';
import { NavigationBarProps } from './NavigationBar.types';

const useNavigationBarStyles = makeStyles((theme) => ({
  navbar: {
    display: 'flex',
    flexDirection: 'column',
  },
  appBarHeader: {
    height: '4px',
    backgroundColor: theme.palette.primary.main,
  },
  toolbar: {
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
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

  const { applicationIcon, applicationBarMenu } = useContext<ViewsContextValue>(ViewsContext);

  return (
    <div className={classes.navbar}>
      <div className={classes.appBarHeader}></div>
      <AppBar position="static">
        <Toolbar className={classes.toolbar} variant="dense">
          <div className={classes.left}>
            <Tooltip title="Back to the homepage">
              <Link component={RouterLink} to="/" className={classes.link} color="inherit">
                <IconButton className={classes.onDarkBackground} color="inherit">
                  {applicationIcon}
                </IconButton>
              </Link>
            </Tooltip>
          </div>
          {children}
          <div className={classes.right}>{applicationBarMenu}</div>
        </Toolbar>
      </AppBar>
    </div>
  );
};
