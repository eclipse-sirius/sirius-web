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
import IconButton from '@material-ui/core/IconButton';
import { emphasize, makeStyles } from '@material-ui/core/styles';
import HelpIcon from '@material-ui/icons/Help';
import { NavigationBarMenuIconProps } from './NavigationBarMenu.types';

const useHelpStyle = makeStyles((theme) => ({
  onDarkBackground: {
    '&:hover': {
      backgroundColor: emphasize(theme.palette.secondary.main, 0.08),
    },
  },
}));

export const Help = ({ onClick }: NavigationBarMenuIconProps) => {
  const classes = useHelpStyle();
  return (
    <IconButton className={classes.onDarkBackground} color="inherit" onClick={onClick}>
      <HelpIcon />
    </IconButton>
  );
};
