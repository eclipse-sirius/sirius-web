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
import React, { useMemo, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { isPaletteDivider, isSingleClickOnDiagramElementTool, isToolSection } from '../Palette';
import { GQLPaletteEntry } from '../Palette.types';
import { ToolListItem } from '../tool-list-item/ToolListItem';
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
  openedToolSectionId: null,
};

/**
 *
 * @technical-debt The extensions contributed with the prop children should be removed in favor of a dedicated extension point
 *
 */
export const PaletteToolList = ({
  palette,
  diagramElementIds,
  lastToolInvoked,
  onToolClick,
  onBackToMainList,
  onClose,
  children,
}: PaletteToolListProps) => {
  const [state, setState] = useState<PaletteToolListStateValue>(defaultStateValue);

  const { classes } = useStyle();

  const handleToolSectionClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, extensionSectionId: string) => {
    event.stopPropagation();
    setState((prevState) => ({
      ...prevState,
      openedToolSectionId: extensionSectionId,
    }));
  };

  const handleBackToMainList = () => {
    setState((prevState) => ({ ...prevState, openedToolSectionId: null, openedToolSectionLabel: null }));
    onBackToMainList();
  };

  const listItemsRendered = palette.paletteEntries.flatMap((paletteEntry: GQLPaletteEntry) => {
    if (isSingleClickOnDiagramElementTool(paletteEntry)) {
      return (
        <ToolListItem
          onToolClick={onToolClick}
          tool={paletteEntry}
          disabled={false}
          key={'toolItem_' + paletteEntry.id}
          data-testid={`paletteEntry-${paletteEntry.label}`}
        />
      );
    } else if (isToolSection(paletteEntry) && paletteEntry.tools.length > 0) {
      return (
        <Tooltip key={'tooltip_' + paletteEntry.id} title={paletteEntry.label} placement="right">
          <ListItemButton
            className={classes.listItemButton}
            onClick={(event) => handleToolSectionClick(event, paletteEntry.id)}
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

  children.forEach((extensionSection) => {
    const extensionSectionId = extensionSection.props.id;
    const extensionSectionTitle = extensionSection.props.title;
    listItemsRendered.push(
      <Tooltip key={`tooltip_${extensionSectionId}`} title={extensionSectionTitle} placement="right">
        <ListItemButton
          className={classes.listItemButton}
          onClick={(event) => handleToolSectionClick(event, extensionSectionId)}
          data-testid={`toolSection-${extensionSectionTitle}`}>
          <ListItemText primary={extensionSectionTitle} className={classes.listItemText} />
          <NavigateNextIcon />
        </ListItemButton>
      </Tooltip>
    );
  });

  const renderedLastTool =
    lastToolInvoked && !state.openedToolSectionId ? (
      <>
        <ToolListItem onToolClick={onToolClick} tool={lastToolInvoked} disabled={false} />
        <Divider />
      </>
    ) : null;

  const containerRef = React.useRef<HTMLElement>(null);

  const openedTooLSectionComponent = useMemo(() => {
    let renderedToolSection: JSX.Element | null = null;
    const openedToolSectionFromPalette = palette.paletteEntries
      .filter(isToolSection)
      .find((entry) => state.openedToolSectionId === entry.id);

    if (state.openedToolSectionId && openedToolSectionFromPalette) {
      renderedToolSection = (
        <PaletteToolSectionList
          onToolClick={onToolClick}
          onBackToMainList={handleBackToMainList}
          toolSection={openedToolSectionFromPalette}
        />
      );
    } else if (state.openedToolSectionId && !openedToolSectionFromPalette) {
      children
        .filter((extensionSection) => state.openedToolSectionId === extensionSection.props.id)
        .map((extensionSection) => {
          const SectionComponent = extensionSection.props.component;
          renderedToolSection = (
            <SectionComponent
              onBackToMainList={handleBackToMainList}
              diagramElementIds={diagramElementIds}
              onClose={onClose}
            />
          );
        });
    }

    return renderedToolSection;
  }, [state.openedToolSectionId]);

  return (
    <Box className={classes.container}>
      {renderedLastTool}
      <Box className={classes.toolListContainer} ref={containerRef}>
        <Slide
          key={'extension_' + state.openedToolSectionId}
          direction={'left'}
          in={!!state.openedToolSectionId}
          container={containerRef.current}
          unmountOnExit
          mountOnEnter>
          <div className={classes.toolList}>{openedTooLSectionComponent}</div>
        </Slide>

        <Slide
          direction={'right'}
          in={state.openedToolSectionId === null}
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
