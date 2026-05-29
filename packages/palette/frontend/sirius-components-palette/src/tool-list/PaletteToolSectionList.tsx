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
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { PaletteToolContributionProps } from '../extensions/PaletteToolContribution.types';
import { paletteToolExtensionPoint } from '../extensions/PaletteToolExtensionPoints';
import { isSingleClickOnDiagramElementTool } from '../Palette';
import { ToolListItem } from '../tool-list-item/ToolListItem';
import { PaletteToolSectionListProps } from './PaletteToolSectionList.types';

const useStyle = makeStyles()((theme) => ({
  toolListItemIcon: {
    minWidth: 0,
    marginRight: 16,
  },
  toolListItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  toolList: {
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
  sectionTitleListItemText: {
    '& .MuiListItemText-primary': {
      fontWeight: theme.typography.fontWeightBold,
      whiteSpace: 'nowrap',
      overflow: 'hidden',
      textOverflow: 'ellipsis',
    },
  },
}));

export const PaletteToolSectionList = ({
  toolSection,
  representationElementIds,
  onToolClick,
  onBackToMainList,
}: PaletteToolSectionListProps) => {
  const { classes } = useStyle();

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  const paletteToolData: DataExtension<PaletteToolContributionProps[]> = useData(paletteToolExtensionPoint);

  const listItemsRendered = toolSection.tools.filter(isSingleClickOnDiagramElementTool).map((tool) => {
    const overriddenTool = paletteToolData.data
      .filter((contributedTool) => contributedTool.sectionId === toolSection.id)
      .find((contributedTool) => contributedTool.id === tool.id);
    if (!overriddenTool) {
      return <ToolListItem onToolClick={onToolClick} tool={tool} disabled={false} key={tool.id} />;
    } else if (overriddenTool && overriddenTool.canHandle(representationElementIds)) {
      const OverriddenComponent = overriddenTool.component;
      return (
        <OverriddenComponent
          representationElementIds={representationElementIds}
          key={overriddenTool.id}></OverriddenComponent>
      );
    } else {
      return null;
    }
  });

  paletteToolData.data
    .filter((contributedTool) => contributedTool.sectionId === toolSection.id)
    .filter((contributedTool) => !toolSection.tools.find((tool) => tool.id === contributedTool.id))
    .forEach((contributedTool) => {
      const OverriddenComponent = contributedTool.component;
      listItemsRendered.push(
        <OverriddenComponent
          representationElementIds={representationElementIds}
          key={contributedTool.id}></OverriddenComponent>
      );
    });

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title={toolSection.label} key={'tooltip_' + toolSection.id} placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-${toolSection.label}`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary={toolSection.label} />
        </ListItemButton>
      </Tooltip>
      {listItemsRendered}
    </List>
  );
};
