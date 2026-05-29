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
import { DataExtension, useData } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import Divider from '@mui/material/Divider';
import List from '@mui/material/List';
import Slide from '@mui/material/Slide';
import React, { useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PaletteToolContributionProps } from '../extensions/PaletteToolContribution.types';
import { paletteToolExtensionPoint } from '../extensions/PaletteToolExtensionPoints';
import { isPaletteDivider, isSingleClickOnDiagramElementTool, isTool, isToolSection } from '../Palette';
import { GQLPalette, GQLPaletteEntry, GQLTool, GQLToolSection } from '../Palette.types';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { PaletteToolSectionProps, PaletteToolSectionStateValue } from './PaletteToolSection.types';
import { ToolSectionEntry } from './ToolSectionEntry';
import { ToolSectionHeader } from './ToolSectionHeader';

const useStyle = makeStyles()(() => ({
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
}));

const defaultStateValue: PaletteToolSectionStateValue = {
  currentSectionId: undefined,
  history: [],
  direction: 'left',
};

const paletteContainsTool = (palette: GQLPalette, toolId: string) => {
  const containsTool = (tools: GQLTool[]) => tools.some((tool) => tool.id === toolId);
  return (
    containsTool(palette.quickAccessTools) ||
    containsTool(palette.paletteEntries.filter(isTool)) ||
    palette.paletteEntries.filter(isToolSection).find((section) => containsTool(section.tools))
  );
};

const findToolsInToolSection = (section: GQLToolSection, currentSectionId: string | undefined) => {
  if (section.id === currentSectionId) {
    return section.tools;
  } else {
    return [];
  }
};

/**
 *
 * @technical-debt The extension contributed with the prop extensionSections section should be contributed using a dedicated extension point
 *
 */
export const PaletteToolSection = ({
  palette,
  onToolClick,
  onBackToMainList,
  representationElementIds,
  onClose,
  lastToolInvoked,
  extensionSections,
}: PaletteToolSectionProps) => {
  const [state, setState] = useState<PaletteToolSectionStateValue>(defaultStateValue);
  const { classes } = useStyle();

  const navigateTo = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, sectionId: string | undefined) => {
    event.stopPropagation();
    setState((prev) => ({
      currentSectionId: sectionId,
      history: [...prev.history, prev.currentSectionId],
      direction: 'left',
    }));
  };

  const navigateBack = () => {
    onBackToMainList();
    setState((prev) => {
      const history = [...prev.history];
      const currentSectionId = history.pop() ?? undefined;
      return { currentSectionId, history, direction: 'right' };
    });
  };

  const paletteToolData: DataExtension<PaletteToolContributionProps[]> = useData(paletteToolExtensionPoint);

  let currentEntries: GQLPaletteEntry[] =
    state.currentSectionId === undefined
      ? palette.paletteEntries
      : palette.paletteEntries
          .filter(isToolSection)
          .flatMap((entry) => findToolsInToolSection(entry, state.currentSectionId));

  // Backend tools/tools section/dividers
  const listItemsRendered: JSX.Element[] = currentEntries.flatMap((paletteEntry: GQLPaletteEntry) => {
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
        <ToolSectionEntry id={paletteEntry.id} label={paletteEntry.label} onNavigate={navigateTo}></ToolSectionEntry>
      );
    } else if (isPaletteDivider(paletteEntry)) {
      return <Divider key={'divider_' + paletteEntry.id} />;
    }
    return [];
  });

  // Tools contributions
  paletteToolData.data
    .filter((contributedTool) => contributedTool.sectionId === state.currentSectionId)
    .forEach((contributedTool) => {
      const ContributedComponent = contributedTool.component;
      listItemsRendered.push(
        <ContributedComponent representationElementIds={representationElementIds}></ContributedComponent>
      );
    });

  //Extension section
  if (state.currentSectionId === undefined) {
    extensionSections.forEach((extensionSection) => {
      listItemsRendered.push(
        <ToolSectionEntry
          id={extensionSection.props.id}
          label={extensionSection.props.title}
          onNavigate={navigateTo}></ToolSectionEntry>
      );
    });
  } else {
    extensionSections
      .filter((extensionSection) => extensionSection.props.id === state.currentSectionId)
      .forEach((extensionSection) => {
        const SectionComponent = extensionSection.props.component;
        listItemsRendered.push(
          <div className={classes.toolList}>
            <SectionComponent
              onBackToMainList={navigateBack}
              diagramElementIds={representationElementIds}
              onClose={onClose}
            />
          </div>
        );
      });
  }

  const lastToolAvailable = lastToolInvoked && paletteContainsTool(palette, lastToolInvoked.id);
  const lastUsedTool: JSX.Element | null = lastToolInvoked ? (
    <>
      <ToolListItem onToolClick={onToolClick} tool={lastToolInvoked} disabled={!lastToolAvailable} />
      <Divider />
    </>
  ) : null;

  const containerRef = React.useRef<HTMLElement>(null);

  const currentSection: GQLToolSection | undefined = palette.paletteEntries
    .filter(isToolSection)
    .find((entry) => entry.id === state.currentSectionId);

  return (
    <Box className={classes.container}>
      {lastUsedTool}
      <Box className={classes.toolListContainer} ref={containerRef}>
        <Slide
          key={state.currentSectionId ?? 'main'}
          direction={state.direction === 'left' ? 'left' : 'right'}
          in
          container={containerRef.current}
          mountOnEnter
          unmountOnExit>
          <Box>
            <List className={classes.toolList} component="nav">
              {!!currentSection ? (
                <ToolSectionHeader title={currentSection.label} navigateBack={navigateBack}></ToolSectionHeader>
              ) : null}
              {listItemsRendered}
            </List>
          </Box>
        </Slide>
      </Box>
    </Box>
  );
};
