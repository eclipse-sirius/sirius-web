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

import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import { makeStyles } from 'tss-react/mui';

const useStyles = makeStyles()(() => ({
  icon: {
    position: 'absolute',
    top: '2px',
    right: '2px',
    padding: '0',
    ' & .MuiSvgIcon-fontSizeSmall': {
      fontSize: '12px',
    },
  },
}));

export const DeckDeleteButton = (props) => {
  const { classes } = useStyles();
  return (
    <IconButton className={classes.icon} tabIndex={-1} aria-label="deleteCard" {...props}>
      <DeleteIcon fontSize={'small'} />
    </IconButton>
  );
};
