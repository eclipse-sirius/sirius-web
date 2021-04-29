/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
import { makeStyles } from '@material-ui/core/styles';
import Toolbar from '@material-ui/core/Toolbar';
import Tooltip from '@material-ui/core/Tooltip';
import { Help, SiriusIcon } from 'icons';
import React from 'react';
import { Link as RouterLink } from 'react-router-dom';

const useNavigationbarStyles = makeStyles((theme) => ({
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
}));

export const NavigationBar = () => {
  const classes = useNavigationbarStyles();
  debugger;
  return (
    <AppBar position="static">
      <Toolbar className={classes.toolbar}>
        <div className={classes.left}>
          <Tooltip title="Back to the homepage">
            <RouterLink to="/" className={classes.link}>
              <SiriusIcon fontSize="large" style={{ color: '#FFFFFF' }} />
            </RouterLink>
          </Tooltip>
        </div>
        <div className={classes.right}>
          <a href="https://www.eclipse.org/sirius" rel="noopener noreferrer" target="_blank">
            <Help titleAccess="Help" style={{ fill: 'var(--white)', marginTop: 16, width: 32, height: 32 }} />
          </a>
        </div>
      </Toolbar>
    </AppBar>
  );
};
