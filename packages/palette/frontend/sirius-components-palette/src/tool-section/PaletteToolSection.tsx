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
import { PaletteToolOverriddenContributionProps } from '../extensions/PaletteToolOverrideContribution.types';
import { paletteToolOverrideExtensionPoint } from '../extensions/PaletteToolOverrideExtensionPoints';
import { isPaletteDivider, isTool, isToolSection } from '../Palette';
import { GQLPalette, GQLPaletteEntry, GQLTool, GQLToolSection } from '../Palette.types';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { usePalette } from '../usePalette';
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
  representationKind,
  representationElementIds,
  palette,
  lastToolInvoked,
  onToolClick,
  onBackToMainList,
  onClose,
  extensionSections,
}: PaletteToolSectionProps) => {
  const [state, setState] = useState<PaletteToolSectionStateValue>(defaultStateValue);
  const { classes } = useStyle();
  const { setLastToolInvoked } = usePalette();

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
  const paletteToolOverriddenData: DataExtension<PaletteToolOverriddenContributionProps[]> = useData(
    paletteToolOverrideExtensionPoint
  );

  let currentEntries: GQLPaletteEntry[] =
    state.currentSectionId === undefined
      ? palette.paletteEntries
      : palette.paletteEntries
          .filter(isToolSection)
          .flatMap((entry) => findToolsInToolSection(entry, state.currentSectionId));

  const renderSingleClickToolItem = (tool: GQLTool) => {
    const overriddenTool = paletteToolOverriddenData.data.find((contributedTool) =>
      contributedTool.canHandle(representationKind, tool)
    );
    if (!overriddenTool) {
      return (
        <ToolListItem
          onToolClick={onToolClick}
          tool={tool}
          disabled={false}
          key={'toolItem_' + tool.id}
          data-testid={`paletteEntry-${tool.label}`}
        />
      );
    } else {
      const OverriddenComponent = overriddenTool.component;
      return (
        <OverriddenComponent
          representationElementIds={representationElementIds}
          onInvoked={() => setLastToolInvoked(palette.id, tool)}
          tool={tool}></OverriddenComponent>
      );
    }
  };

  // Backend tools/tools section/dividers
  const listItemsRendered: JSX.Element[] = currentEntries.flatMap((paletteEntry: GQLPaletteEntry) => {
    if (isTool(paletteEntry)) {
      return renderSingleClickToolItem(paletteEntry);
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
    .filter((contributedTool) => contributedTool.canHandle(representationKind))
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
      {isTool(lastToolInvoked) ? (
        renderSingleClickToolItem(lastToolInvoked)
      ) : (
        <ToolListItem onToolClick={onToolClick} tool={lastToolInvoked} disabled={!lastToolAvailable} />
      )}
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
