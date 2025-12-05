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
import HelpIcon from '@mui/icons-material/Help';
import IconButton from '@mui/material/IconButton';
import { emphasize } from '@mui/material/styles';
import { makeStyles } from 'tss-react/mui';
import { NavigationBarMenuIconProps } from './NavigationBarMenu.types';

const useHelpStyle = makeStyles()((theme) => ({
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
  iconButton: {
    padding: '4px',
  },
  icon: {
    fontSize: '1.25rem',
  },
}));

export const Help = ({ onClick }: NavigationBarMenuIconProps) => {
  const { classes } = useHelpStyle();
  return (
    <IconButton className={`${classes.iconButton} ${classes.onDarkBackground}`} color="inherit" onClick={onClick}>
      <HelpIcon className={classes.icon} />
    </IconButton>
  );
};
