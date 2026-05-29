/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { ToolSectionEntryProps } from './ToolSectionEntry.types';

const useStyle = makeStyles()(() => ({
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
}));

export const ToolSectionEntry = ({ id, label, onNavigate }: ToolSectionEntryProps) => {
  const { classes } = useStyle();

  return (
    <Tooltip key={'tooltip_' + id} title={label} placement="right">
      <ListItemButton
        className={classes.listItemButton}
        onClick={(event) => onNavigate(event, id)}
        data-testid={`toolSection-${label}`}>
        <ListItemText primary={label} className={classes.listItemText} />
        <NavigateNextIcon />
      </ListItemButton>
    </Tooltip>
  );
};
