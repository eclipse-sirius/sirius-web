/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

const defaultStateValue: PaletteToolListStateValue = {
  toolSection: null,
};

export const PaletteToolList = ({ palette, onToolClick }: PaletteToolListProps) => {
  const [state, setState] = useState<PaletteToolListStateValue>(defaultStateValue);

  const { getLastToolInvoked } = useDiagramPalette();
  const lastToolInvoked = getLastToolInvoked(palette.id);

  const { classes } = useStyle();

  const handleToolSectionClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, toolSection: GQLToolSection) => {
    event.stopPropagation();
    setState((prevState) => ({ ...prevState, toolSection }));
  };

  const onBackToMainList = () => {
    setState((prevState) => ({ ...prevState, toolSection: null }));
    document.getElementById('palette')?.focus();
  };

  const listItemsRendered = palette.paletteEntries.flatMap((paletteEntry: GQLPaletteEntry) => {
    if (isSingleClickOnDiagramElementTool(paletteEntry)) {
      return <ToolListItem onToolClick={onToolClick} tool={paletteEntry} key={'toolItem_' + paletteEntry.id} />;
    } else if (isToolSection(paletteEntry)) {
      return (
        <Tooltip key={'tooltip_' + paletteEntry.id} title={paletteEntry.label} placement="right">
          <ListItemButton
            className={classes.listItemButton}
            onClick={(event) => handleToolSectionClick(event, paletteEntry)}
            data-testid={`toolSection-${paletteEntry.label}`}>
            <ListItemText primary={paletteEntry.label} className={classes.listItemText} />
            <NavigateNextIcon />
          </ListItemButton>
        </Tooltip>
      );
    } else if (isPaletteDivider(paletteEntry)) {
      return <Divider key={'divider_' + paletteEntry.id} />;
    }
    return [];
  });

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
                toolSection={entry}
                onToolClick={onToolClick}
                onBackToMainList={onBackToMainList}
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
            {listItemsRendered}
          </List>
        </Slide>
      </Box>
    </Box>
  );
};
