/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import React from 'react';
import { makeStyles } from 'tss-react/mui';
import { NodeData } from '../../DiagramRenderer.types';
import { APPEARANCE_SECTION_ID } from '../tool-list/PaletteToolList.types';
import { PaletteAppearanceSectionProps } from './PaletteAppearanceSection.types';
import { useNodeTypeAppearanceSections } from './useNodeTypeAppearanceSections';

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

export const PaletteAppearanceSection = ({
  elementId,
  elementType,
  elementData,
  onBackToMainList,
}: PaletteAppearanceSectionProps) => {
  const { classes } = useStyle();
  const { nodeTypeAppearanceSectionMap } = useNodeTypeAppearanceSections();
  const NodeAppearanceSectionComponent =
    !!elementType && !!elementData && !!elementData.nodeAppearanceData
      ? nodeTypeAppearanceSectionMap[(elementData as NodeData).nodeAppearanceData.gqlStyle.__typename]
      : undefined;

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title={'Appearance'} key={'tooltip_' + APPEARANCE_SECTION_ID} placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-Appearance`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary={'Appearance'} />
        </ListItemButton>
      </Tooltip>
      {NodeAppearanceSectionComponent ? (
        <NodeAppearanceSectionComponent
          nodeId={elementId}
          nodeType={elementType as string}
          nodeData={elementData as NodeData}
        />
      ) : (
        <ListItem>
          <Typography>No appearance editor available for this style of element</Typography>
        </ListItem>
      )}
    </List>
  );
};
