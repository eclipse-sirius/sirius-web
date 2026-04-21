/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
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
import { isPaletteDivider, isSingleClickOnDiagramElementTool, isTool, isToolSection } from '../Palette';
import { GQLPalette, GQLPaletteEntry, GQLTool } from '../Palette.types';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { PaletteToolListProps, PaletteToolListStateValue } from './PaletteToolList.types';
import { PaletteToolSectionList } from './PaletteToolSectionList';
import { PaletteEntriesContributionProps } from '../extension/PaletteEntriesContribution.types';
import { paletteEntriesExtensionPoint } from '../extension/PaletteEntriesExtensionPoints';

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
  toolSectionId: null,
};

const paletteContainsTool = (palette: GQLPalette, toolId: string) => {
  const containsTool = (tools: GQLTool[]) => tools.some((tool) => tool.id === toolId);
  return (
    containsTool(palette.quickAccessTools) ||
    containsTool(palette.paletteEntries.filter(isTool)) ||
    palette.paletteEntries.filter(isToolSection).find((section) => containsTool(section.tools))
  );
};

/**
 *
 * @technical-debt The extension contributed with the prop children section should support a list of diagramElement
 * They should also be contributed using a dedicated extension point see https://github.com/eclipse-sirius/sirius-web/pull/5413
 */
export const PaletteToolList = ({
  palette,
  onToolClick,
  onBackToMainList,
  representationElementIds,
  onClose,
  lastToolInvoked,
}: PaletteToolListProps) => {
  const [state, setState] = useState<PaletteToolListStateValue>(defaultStateValue);

  const { classes } = useStyle();

  const extensionEntriesData: DataExtension<PaletteEntriesContributionProps[]> = useData(paletteEntriesExtensionPoint);

  const handleToolSectionClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, toolSectionId: string) => {
    event.stopPropagation();
    setState((prevState) => ({ ...prevState, toolSectionId }));
  };

  const handleBackToMainList = () => {
    setState((prevState) => ({ ...prevState, toolSectionId: null }));
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
  extensionEntriesData.data
    .filter((extensionEntry) => extensionEntry.canHandle(representationElementIds))
    .forEach((extensionEntry) => {
      const EntryComponent = extensionEntry.component;
      listItemsRendered.push(<EntryComponent onToolClick={onToolClick} onToolSectionClick={handleToolSectionClick} />);
    });

  const lastToolAvailable = lastToolInvoked && paletteContainsTool(palette, lastToolInvoked.id);
  const lastUsedTool: JSX.Element | null = lastToolInvoked ? (
    <>
      <ToolListItem onToolClick={onToolClick} tool={lastToolInvoked} disabled={!lastToolAvailable} />
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
            in={state.toolSectionId === entry.id}
            container={containerRef.current}
            unmountOnExit
            mountOnEnter>
            <div className={classes.toolList}>
              <PaletteToolSectionList
                toolSection={entry}
                onToolClick={onToolClick}
                onBackToMainList={handleBackToMainList}
              />
            </div>
          </Slide>
        ))}

        {extensionEntriesData.data
          .filter((entry: PaletteEntriesContributionProps) => !!entry.sectionComponent)
          .map((toolSection: PaletteEntriesContributionProps) => {
            const SectionComponent = toolSection.sectionComponent;
            return SectionComponent ? (
              <Slide
                key={'extension_' + toolSection.id}
                direction={'left'}
                in={state.toolSectionId === toolSection.id}
                container={containerRef.current}
                unmountOnExit
                mountOnEnter>
                <div className={classes.toolList}>
                  <SectionComponent
                    onBackToMainList={handleBackToMainList}
                    representationElementIds={representationElementIds}
                    onClose={onClose}
                  />
                </div>
              </Slide>
            ) : null;
          })}

        <Slide
          direction={'right'}
          in={state.toolSectionId === null}
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
