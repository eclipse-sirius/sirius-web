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
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { Tool } from './../draggable-palette/DraggablePalette.types';
import { PaletteToolSectionListProps } from './PaletteToolSectionList.types';
import { ToolListItem } from './ToolListItem';

const useStyle = makeStyles()((theme) => ({
  toolListItemIcon: {
    minWidth: 0,
    marginRight: 16,
  },
  toolListItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  toolList: {
    width: '100%',
    padding: 0,
  },
  listItemText: {
    '& .MuiListItemText-primary': {
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
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

export const PaletteToolSectionList = ({ toolSection, onToolClick, onBackToMainList }: PaletteToolSectionListProps) => {
  const { classes } = useStyle();

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title={toolSection.label} key={'tooltip_' + toolSection.id}>
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-${toolSection.label}`}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary={toolSection.label} />
        </ListItemButton>
      </Tooltip>
      {toolSection.tools.map((tool) => (
        <ToolListItem onToolClick={onToolClick} tool={tool as Tool} key={tool.id} />
      ))}
    </List>
  );
};
