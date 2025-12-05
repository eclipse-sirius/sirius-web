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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
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
import { PaletteToolContributionProps } from '../extensions/PaletteToolContribution.types';
import { paletteToolExtensionPoint } from '../extensions/PaletteToolExtensionPoints';
import { PaletteToolSectionContributionProps } from '../extensions/PaletteToolSectionContribution.types';
import { paletteToolSectionExtensionPoint } from '../extensions/PaletteToolSectionExtensionPoints';
import { isPaletteDivider, isSingleClickOnDiagramElementTool, isTool, isToolSection } from '../Palette';
import { GQLPalette, GQLPaletteEntry, GQLTool } from '../Palette.types';
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
  openedToolSectionLabel: null,
};

const getToolFromPalette = (palette: GQLPalette, toolId: string): GQLTool | null => {
  const getTool = (tools: GQLTool[]) => tools.find((tool) => tool.id === toolId);
  const tool =
    getTool(palette.paletteEntries.filter(isTool)) ||
    palette.paletteEntries
      .filter(isToolSection)
      .flatMap((toolSection) => toolSection.tools)
      .find((tool) => tool.id === toolId) ||
    null;
  return tool;
};

/**
 *
 * @technical-debt The extension contributed with the prop children section should support a list of diagramElement
 * They should also be contributed using a dedicated extension point see https://github.com/eclipse-sirius/sirius-web/pull/5413
 */
export const PaletteToolList = ({
  palette,
  diagramElementIds,
  lastToolInvokedId,
  onToolClick,
  onBackToMainList,
  onClose,
  children,
}: PaletteToolListProps) => {
  const [state, setState] = useState<PaletteToolListStateValue>(defaultStateValue);

  const { classes } = useStyle();

  const handleToolSectionClick = (
    event: React.MouseEvent<HTMLDivElement, MouseEvent>,
    extensionSectionId: string,
    openedToolSectionLabel: string
  ) => {
    event.stopPropagation();
    setState((prevState) => ({
      ...prevState,
      openedToolSectionId: extensionSectionId,
      openedToolSectionLabel: openedToolSectionLabel,
    }));
  };

  const handleBackToMainList = () => {
    setState((prevState) => ({ ...prevState, openedToolSectionId: null, openedToolSectionLabel: null }));
    onBackToMainList();
  };

  const getContributionId = (contributionProp: PaletteToolContributionProps | PaletteToolSectionContributionProps) =>
    contributionProp.id;

  const paletteToolData: DataExtension<PaletteToolContributionProps[]> = useData(paletteToolExtensionPoint);
  const paletteToolSectionData: DataExtension<PaletteToolSectionContributionProps[]> = useData(
    paletteToolSectionExtensionPoint
  );
  const listItemsRendered = palette.paletteEntries
    .filter((paletteEntry) => !paletteToolData.data.map(getContributionId).includes(paletteEntry.id))
    .filter((paletteEntry) => !paletteToolSectionData.data.map(getContributionId).includes(paletteEntry.id))
    .flatMap((paletteEntry: GQLPaletteEntry) => {
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
              onClick={(event) => handleToolSectionClick(event, paletteEntry.id, paletteEntry.label)}
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

  //This should be removed and a contribution with the toolSection or tool extension point should be used instead
  children.forEach((extensionSection) => {
    const extensionSectionId = extensionSection.props.id;
    const extensionSectionTitle = extensionSection.props.title;
    listItemsRendered.push(
      <Tooltip key={`tooltip_${extensionSectionId}`} title={extensionSectionTitle} placement="right">
        <ListItemButton
          className={classes.listItemButton}
          onClick={(event) => handleToolSectionClick(event, extensionSectionId, extensionSectionTitle)}
          data-testid={`toolSection-${extensionSectionTitle}`}>
          <ListItemText primary={extensionSectionTitle} className={classes.listItemText} />
          <NavigateNextIcon />
        </ListItemButton>
      </Tooltip>
    );
  });

  const renderContributedTool = (contributedToolProps: PaletteToolContributionProps) => {
    var Component = contributedToolProps.component;
    return <Component></Component>;
  };

  const lastToolAvailable = lastToolInvokedId ? getToolFromPalette(palette, lastToolInvokedId) : null;
  const LastContributedToolComponentAvailable = paletteToolData.data.find(
    (contributedTool) => contributedTool.id === lastToolInvokedId
  );

  let renderedLastTool = LastContributedToolComponentAvailable
    ? renderContributedTool(LastContributedToolComponentAvailable)
    : null;

  if (!renderedLastTool && lastToolAvailable) {
    renderedLastTool = (
      <>
        <ToolListItem onToolClick={onToolClick} tool={lastToolAvailable} disabled={false} />
        <Divider />
      </>
    );
  }

  const containerRef = React.useRef<HTMLElement>(null);

  paletteToolData.data
    .filter((data) => data.canHandle())
    .filter((data) => !data.toolSectionId)
    .map((data) => data.component)
    .forEach((PaletteToolComponent, index) =>
      listItemsRendered.push(<PaletteToolComponent key={'paletteToolComponents_' + index.toString()} />)
    );

  paletteToolSectionData.data
    .filter((data) => data.canHandle())
    .forEach((PaletteToolComponent) =>
      listItemsRendered.push(
        <Tooltip key={'tooltip_' + PaletteToolComponent.id} title={PaletteToolComponent.label} placement="right">
          <ListItemButton
            className={classes.listItemButton}
            onClick={(event) => handleToolSectionClick(event, PaletteToolComponent.id, PaletteToolComponent.label)}
            data-testid={`toolSection-${PaletteToolComponent.label}`}>
            <ListItemText primary={PaletteToolComponent.label} className={classes.listItemText} />
            <NavigateNextIcon />
          </ListItemButton>
        </Tooltip>
      )
    );

  const openedTooLSectionComponent = useMemo(() => {
    let renderedToolSection: JSX.Element | null = null;
    const isOpenedPaletteFromPaletteContent = palette.paletteEntries
      .filter(isToolSection)
      .find((entry) => state.openedToolSectionId === entry.id);
    const isOpenedPaletteFromPaletteExtension = paletteToolData.data.find(
      (entry) => state.openedToolSectionId === entry.toolSectionId
    );

    if (
      state.openedToolSectionId &&
      state.openedToolSectionLabel &&
      (isOpenedPaletteFromPaletteContent || isOpenedPaletteFromPaletteExtension)
    ) {
      renderedToolSection = (
        <PaletteToolSectionList
          palette={palette}
          toolSectionId={state.openedToolSectionId}
          toolSectionLabel={state.openedToolSectionLabel}
          onToolClick={onToolClick}
          onBackToMainList={handleBackToMainList}
        />
      );
    } else if (
      state.openedToolSectionId &&
      state.openedToolSectionLabel &&
      !isOpenedPaletteFromPaletteContent &&
      !isOpenedPaletteFromPaletteExtension
    ) {
      //This should be removed and a contribution with the toolSection or tool extension point should be used instead
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
