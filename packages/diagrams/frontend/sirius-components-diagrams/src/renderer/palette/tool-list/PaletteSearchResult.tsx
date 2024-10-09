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

import { IconOverlay, splitText } from '@eclipse-sirius/sirius-components-core';
import Box from '@mui/material/Box';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { Theme } from '@mui/material/styles';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import { useEffect, useMemo, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { isSingleClickOnDiagramElementTool, isToolSection } from '../Palette';
import { GQLPaletteEntry, GQLSingleClickOnDiagramElementTool, GQLTool } from '../Palette.types';
import { usePaletteEntryTooltip } from '../usePaletteEntryTooltip';
import { HighlightedLabelProps, PaletteSearchResultProps } from './PaletteSearchResult.types';

const convertToToolList = (entry: GQLPaletteEntry): GQLSingleClickOnDiagramElementTool[] => {
  if (isSingleClickOnDiagramElementTool(entry)) {
    return [entry];
  } else if (isToolSection(entry)) {
    return entry.tools.filter(isSingleClickOnDiagramElementTool);
  } else {
    return [];
  }
};

const flatToolsFromPaletteEntries = (paletteEntries: GQLPaletteEntry[]): GQLSingleClickOnDiagramElementTool[] => {
  return paletteEntries
    .filter((entry) => isToolSection(entry) || isSingleClickOnDiagramElementTool(entry))
    .flatMap(convertToToolList);
};

const useLabelStyles = makeStyles()((theme: Theme) => ({
  highlight: {
    backgroundColor: theme.palette.navigation.leftBackground,
  },
  itemText: {
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  },
}));

const HighlightedLabel = ({ label, textToHighlight }: HighlightedLabelProps) => {
  const { classes } = useLabelStyles();
  let itemLabel: JSX.Element;
  const splitLabelWithTextToHighlight: string[] = splitText(label, textToHighlight);
  if (
    textToHighlight === null ||
    textToHighlight === '' ||
    (splitLabelWithTextToHighlight.length === 1 &&
      splitLabelWithTextToHighlight[0]?.toLocaleLowerCase() !== label.toLocaleLowerCase())
  ) {
    itemLabel = <>{label}</>;
  } else {
    const languages: string[] = Array.from(navigator.languages);
    itemLabel = (
      <>
        {splitLabelWithTextToHighlight.map((value, index) => {
          const shouldHighlight = value.localeCompare(textToHighlight, languages, { sensitivity: 'base' }) === 0;
          return (
            <span
              key={value + index}
              data-testid={`${label}-${value}-${index}`}
              className={shouldHighlight ? classes.highlight : ''}>
              {value}
            </span>
          );
        })}
      </>
    );
  }
  return <Typography className={classes.itemText}>{itemLabel}</Typography>;
};
const filterFromSearchValue = (tool: GQLTool, searchToolValue: string): boolean => {
  return tool.label.toLocaleLowerCase().includes(searchToolValue.toLocaleLowerCase());
};
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
export const PaletteSearchResult = ({ palette, onToolClick, searchToolValue }: PaletteSearchResultProps) => {
  const [selectedIndex, setSelectedIndex] = useState<number>(0);

  const { tooltipEnterDelay, tooltipPlacement } = usePaletteEntryTooltip();

  const { classes } = useStyle();

  const toolList: GQLSingleClickOnDiagramElementTool[] = useMemo(
    () => flatToolsFromPaletteEntries(palette.paletteEntries),
    [palette]
  );

  const filteredToolList: GQLSingleClickOnDiagramElementTool[] = toolList.filter((tool) =>
    filterFromSearchValue(tool, searchToolValue)
  );

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

  const convertToListItem = (tool: GQLSingleClickOnDiagramElementTool, index: number): JSX.Element | null => {
    return (
      <Tooltip enterDelay={tooltipEnterDelay} placement={tooltipPlacement} title={tool.label} key={tool.id}>
        <ListItemButton
          className={classes.listItemButton}
          onClick={() => onToolClick(tool)}
          selected={index === selectedIndex}>
          <ListItemIcon className={classes.listItemIcon}>
            <IconOverlay iconURL={tool.iconURL} alt={tool.label} customIconHeight={16} customIconWidth={16} />
          </ListItemIcon>
          <ListItemText primary={<HighlightedLabel label={tool.label} textToHighlight={searchToolValue} />} />
        </ListItemButton>
      </Tooltip>
    );
  };

  return (
    <Box className={classes.toolListContainer}>
      {filteredToolList.length > 0 ? (
        <List className={classes.toolList} component="nav">
          {filteredToolList.map(convertToListItem)}
        </List>
      ) : (
        <Typography variant="body2" align="center">
          No result
        </Typography>
      )}
    </Box>
  );
};
