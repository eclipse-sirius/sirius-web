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
import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { GQLTool } from '../Palette.types';
import { PaletteToolSectionListProps } from './PaletteToolSectionList.types';

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

export const PaletteToolSectionList = ({
  toolSection,
  onToolClick,
  onBackToMainList,
  tooltipDelay,
  tooltipPlacement,
}: PaletteToolSectionListProps) => {
  const { classes } = useStyle();

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  const handleOnToolClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tool: GQLTool): void => {
    event.stopPropagation();
    onToolClick(tool);
  };

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip
        enterDelay={tooltipDelay}
        placement={tooltipPlacement}
        title={toolSection.label}
        key={'tooltip_' + toolSection.id}>
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-${toolSection.label}`}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary={toolSection.label} />
        </ListItemButton>
      </Tooltip>
      {toolSection?.tools.map((tool) => (
        <Tooltip enterDelay={tooltipDelay} placement={tooltipPlacement} title={tool.label} key={'tooltip_' + tool.id}>
          <ListItemButton
            className={classes.toolListItemButton}
            onClick={(event) => handleOnToolClick(event, tool)}
            data-testid={`tool-${tool.label}`}>
            <ListItemIcon className={classes.toolListItemIcon}>
              <IconOverlay iconURL={tool.iconURL} alt={tool.label} customIconHeight={16} customIconWidth={16} />
            </ListItemIcon>
            <ListItemText className={classes.listItemText} primary={tool.label} />
          </ListItemButton>
        </Tooltip>
      ))}
    </List>
  );
};
