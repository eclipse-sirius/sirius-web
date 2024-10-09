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
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Slide from '@mui/material/Slide';
import Tooltip from '@mui/material/Tooltip';
import React, { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { isPaletteDivider, isSingleClickOnDiagramElementTool, isToolSection } from '../Palette';
import { GQLPaletteEntry, GQLTool, GQLToolSection } from '../Palette.types';
import { PaletteToolListProps, PaletteToolListStateValue } from './PaletteToolList.types';
import { PaletteToolSectionList } from './PaletteToolSectionList';

const useStyle = makeStyles()((theme) => ({
  toolListContainer: {
    display: 'grid',
    overflowY: 'auto',
    overflowX: 'hidden',
    gridTemplateColumns: '100%',
  },
  toolList: {
    gridRowStart: 1,
    gridColumnStart: 1,
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
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  listItemIcon: {
    minWidth: 0,
    marginRight: theme.spacing(2),
  },
}));

export const PaletteToolList = ({ palette, onToolClick }: PaletteToolListProps) => {
  const defaultValue: PaletteToolListStateValue = {
    toolSection: null,
  };

  const [state, setState] = useState<PaletteToolListStateValue>(defaultValue);

  const tooltipDelay = 750;
  const tooltipPlacement = 'left';

  const handleToolSectionClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, toolSection: GQLToolSection) => {
    event.stopPropagation();
    setState((prevState) => ({ ...prevState, toolSection }));
  };

  const handleToolClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tool: GQLTool) => {
    event.stopPropagation();
    onToolClick(tool);
  };

  const onBackToMainList = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    event.stopPropagation();
    setState((prevState) => ({ ...prevState, toolSection: null }));
  };

  const { classes } = useStyle();
  const convertPaletteEntry = (paletteEntry: GQLPaletteEntry): JSX.Element | null => {
    let jsxElement: JSX.Element | null = null;
    if (isSingleClickOnDiagramElementTool(paletteEntry)) {
      jsxElement = (
        <Tooltip
          enterDelay={tooltipDelay}
          placement={tooltipPlacement}
          title={paletteEntry.label}
          key={'tooltip_' + paletteEntry.id}>
          <ListItemButton className={classes.listItemButton} onClick={(event) => handleToolClick(event, paletteEntry)}>
            <ListItemIcon className={classes.listItemIcon}>
              <IconOverlay
                iconURL={paletteEntry.iconURL}
                alt={paletteEntry.label}
                customIconHeight={16}
                customIconWidth={16}
              />
            </ListItemIcon>
            <ListItemText primary={paletteEntry.label} className={classes.listItemText} />
          </ListItemButton>
        </Tooltip>
      );
    } else if (isToolSection(paletteEntry)) {
      jsxElement = (
        <Tooltip
          key={'tooltip_' + paletteEntry.id}
          enterDelay={tooltipDelay}
          placement={tooltipPlacement}
          title={paletteEntry.label}>
          <ListItemButton
            className={classes.listItemButton}
            onClick={(event) => handleToolSectionClick(event, paletteEntry)}>
            <ListItemText primary={paletteEntry.label} className={classes.listItemText} />
            <NavigateNextIcon />
          </ListItemButton>
        </Tooltip>
      );
    } else if (isPaletteDivider(paletteEntry)) {
      jsxElement = <Divider key={'divider_' + paletteEntry.id} />;
    }
    return jsxElement;
  };

  const containerRef = React.useRef<HTMLElement>(null);
  return (
    <Box className={classes.toolListContainer} ref={containerRef}>
      {palette.paletteEntries.filter(isToolSection).map((entry) => (
        <Slide
          key={'slide_' + entry.id}
          direction={'left'}
          in={state.toolSection?.id === entry.id}
          container={containerRef.current}
          unmountOnExit
          mountOnEnter>
          <div className={classes.toolList}>
            <PaletteToolSectionList
              toolSection={entry as GQLToolSection}
              onToolClick={handleToolClick}
              onBackToMainList={onBackToMainList}
              tooltipDelay={tooltipDelay}
              tooltipPlacement={tooltipPlacement}
            />
          </div>
        </Slide>
      ))}
      <Slide
        direction={'right'}
        in={state.toolSection === null}
        container={containerRef.current}
        appear={false}
        unmountOnExit
        mountOnEnter>
        <List className={classes.toolList} component="nav">
          {palette?.paletteEntries.map(convertPaletteEntry)}
        </List>
      </Slide>
    </Box>
  );
};
