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
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import { useEffect, useMemo, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { PaletteToolContributionProps } from '../extensions/PaletteToolContribution.types';
import { paletteToolExtensionPoint } from '../extensions/PaletteToolExtensionPoints';
import { PaletteToolOverriddenContributionProps } from '../extensions/PaletteToolOverrideContribution.types';
import { paletteToolOverrideExtensionPoint } from '../extensions/PaletteToolOverrideExtensionPoints';
import { isTool, isToolSection } from '../Palette';
import { GQLPaletteEntry, GQLTool } from '../Palette.types';
import { useTool } from '../tool-section/useTool';
import { usePalette } from '../usePalette';
import { fuzzyMatch } from './fuzzyMatch';
import { PaletteSearchResultProps } from './PaletteSearchResult.types';

const convertToList = (entry: GQLPaletteEntry): GQLTool[] => {
  if (isTool(entry)) {
    return [entry];
  } else if (isToolSection(entry)) {
    return entry.tools.filter(isTool);
  } else {
    return [];
  }
};

const flatToolsFromPaletteEntries = (paletteEntries: GQLPaletteEntry[]): GQLTool[] => {
  return paletteEntries.filter((entry) => isToolSection(entry) || isTool(entry)).flatMap(convertToList);
};

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

export const PaletteSearchResult = ({
  palette,
  onToolClick,
  searchedValue,
  representationElementIds,
  representationKind,
}: PaletteSearchResultProps) => {
  const [selectedIndex, setSelectedIndex] = useState<number>(0);
  const { getRenderedTool } = useTool();
  const { setLastToolInvokedId } = usePalette();
  const { classes } = useStyle();
  const paletteToolOverriddenData: DataExtension<PaletteToolOverriddenContributionProps[]> = useData(
    paletteToolOverrideExtensionPoint
  );
  const toolList: GQLTool[] = useMemo(() => flatToolsFromPaletteEntries(palette.paletteEntries), [palette]);
  const filteredToolList: GQLTool[] = toolList.filter((tool) => {
    const overriddenTool = paletteToolOverriddenData.data.find((contributedTool) =>
      contributedTool.canHandle(representationKind, tool)
    );
    // If the tool is overridden then the contribution handles if the tool appears in the search result or not
    if (!!overriddenTool) {
      return true;
    } else {
      return fuzzyMatch(tool.label, searchedValue).matches;
    }
  });

  // Tools contributions
  const paletteToolData: DataExtension<PaletteToolContributionProps[]> = useData(paletteToolExtensionPoint);
  const filteredContributions: JSX.Element[] = paletteToolData.data
    .filter((contributedTool) => contributedTool.canHandle(representationKind))
    .map((contributedTool, index) => {
      const ContributedComponent = contributedTool.component;
      return (
        <ContributedComponent
          representationElementIds={representationElementIds}
          key={`contribution_${index}`}
          onInvoked={() => setLastToolInvokedId(palette.id, contributedTool.id)}
          asLastToolUsed={false}
          searchedValue={searchedValue}></ContributedComponent>
      );
    });

  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.key === 'ArrowUp') {
        event.preventDefault();
        setSelectedIndex((prev) => (prev === 0 ? filteredToolList.length - 1 : prev - 1));
      } else if (event.key === 'ArrowDown') {
        event.preventDefault();
        setSelectedIndex((prev) => (prev === filteredToolList.length - 1 ? 0 : prev + 1));
      } else if (event.key === 'Enter') {
        event.preventDefault();
        // Execute the tool
        const selectedTool = filteredToolList[selectedIndex];
        if (selectedTool) {
          onToolClick(selectedTool);
        }
      }
    };

    document.addEventListener('keydown', handleKeyDown);
    return () => {
      document.removeEventListener('keydown', handleKeyDown);
    };
  }, [selectedIndex, filteredToolList]);

  const convertToListItem = (tool: GQLTool, index: number): JSX.Element | null => {
    const selected = index === selectedIndex;
    return getRenderedTool(
      palette,
      tool.id,
      representationElementIds,
      representationKind,
      false,
      selected,
      searchedValue,
      onToolClick
    );
  };

  const matchingTools: JSX.Element[] = [...filteredToolList.map(convertToListItem), ...filteredContributions].filter(
    (tool) => tool !== null
  ) as JSX.Element[];

  return (
    <Box className={classes.container}>
      <Box className={classes.toolListContainer}>
        {matchingTools.length > 0 ? (
          <List className={classes.toolList} component="nav">
            {matchingTools}
          </List>
        ) : (
          <Typography variant="body2" align="center">
            No result
          </Typography>
        )}
      </Box>
    </Box>
  );
};
