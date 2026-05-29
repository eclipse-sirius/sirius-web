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

import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { ToolSectionHeaderProps } from './ToolSectionHeader.types';

const useStyle = makeStyles()((theme) => ({
  toolListItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  sectionTitleListItemText: {
    '& .MuiListItemText-primary': {
      fontWeight: theme.typography.fontWeightBold,
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
}));

export const ToolSectionHeader = ({ title, navigateBack }: ToolSectionHeaderProps) => {
  const { classes } = useStyle();
  return (
    <Tooltip title={title} key={'tooltip_' + title} placement="right">
      <ListItemButton
        className={classes.toolListItemButton}
        onClick={navigateBack}
        data-testid={`back-${title}`}
        autoFocus={true}>
        <NavigateBeforeIcon />
        <ListItemText className={classes.sectionTitleListItemText} primary={title} />
      </ListItemButton>
    </Tooltip>
  );
};
