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

import { emphasize } from '@mui/material/styles';
import Typography from '@mui/material/Typography';
import { makeStyles } from 'tss-react/mui';
import { NavigationBar } from '../../navigationBar/NavigationBar';
import { DisplayLibraryNavbarProps } from './DisplayLibraryNavbar.types';

const useDisplayLibraryViewNavbarStyles = makeStyles()((theme) => ({
  center: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  title: {
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
  },
  titleLabel: {
    marginRight: theme.spacing(2),
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
    maxWidth: '100ch',
  },
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

export const DisplayLibraryNavbar = ({ library }: DisplayLibraryNavbarProps) => {
  const { classes } = useDisplayLibraryViewNavbarStyles();
  return (
    <NavigationBar>
      <div className={classes.center}>
        <div className={classes.title}>
          <Typography
            variant="h6"
            noWrap
            className={classes.titleLabel}
            data-testid={`navbar-${library.namespace}-${library.name}-${library.version}`}>
            {library.namespace} - {library.name} @{library.version}
          </Typography>
        </div>
      </div>
    </NavigationBar>
  );
};
