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
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Slide from '@mui/material/Slide';
import Tooltip from '@mui/material/Tooltip';
import React, { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { isPaletteDivider, isSingleClickOnDiagramElementTool, isToolSection } from '../Palette';
import { GQLPaletteEntry, GQLToolSection } from '../Palette.types';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { useDiagramPalette } from '../useDiagramPalette';
import { usePaletteEntryTooltip } from '../usePaletteEntryTooltip';
import { PaletteToolListProps, PaletteToolListStateValue } from './PaletteToolList.types';
import { PaletteToolSectionList } from './PaletteToolSectionList';

const useStyle = makeStyles()((theme) => ({
  container: {
    display: 'grid',
    gridTemplateRows: `repeat(2,min-content) 1fr`,
    overflowY: 'auto',
    overflowX: 'hidden',
  },
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

  const handleToolSectionClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, toolSection: GQLToolSection) => {
    event.stopPropagation();
    setState((prevState) => ({ ...prevState, toolSection }));
  };

  const onBackToMainList = (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => {
    event.stopPropagation();
    setState((prevState) => ({ ...prevState, toolSection: null }));
  };

  const { classes } = useStyle();
  const { tooltipEnterDelay, tooltipPlacement } = usePaletteEntryTooltip();
  const convertPaletteEntry = (paletteEntry: GQLPaletteEntry): JSX.Element | null => {
    let jsxElement: JSX.Element | null = null;
    if (isSingleClickOnDiagramElementTool(paletteEntry)) {
      jsxElement = <ToolListItem onToolClick={onToolClick} tool={paletteEntry} key={'toolItem_' + paletteEntry.id} />;
    } else if (isToolSection(paletteEntry)) {
      jsxElement = (
        <Tooltip
          key={'tooltip_' + paletteEntry.id}
          enterDelay={tooltipEnterDelay}
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

  const { getLastToolInvoked } = useDiagramPalette();
  const lastToolInvoked = getLastToolInvoked(palette.id);
  const lastUsedTool: JSX.Element | null = lastToolInvoked ? (
    <>
      <ToolListItem onToolClick={onToolClick} tool={lastToolInvoked} />
      <Divider />
    </>
  ) : null;

  const containerRef = React.useRef<HTMLElement>(null);
  return (
    <Box className={classes.container}>
      {lastUsedTool}
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
                onToolClick={onToolClick}
                onBackToMainList={onBackToMainList}
                tooltipDelay={tooltipEnterDelay}
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
    </Box>
  );
};
