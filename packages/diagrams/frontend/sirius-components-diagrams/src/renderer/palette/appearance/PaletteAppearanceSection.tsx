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
import { Edge, Node, useStoreApi } from '@xyflow/react';
import React from 'react';
import { makeStyles } from 'tss-react/mui';
import { EdgeData, NodeData } from '../../DiagramRenderer.types';
import { PaletteExtensionSectionComponentProps } from '../PaletteExtensionSection.types';
import { RectangularNodeAppearanceSection } from './RectangularNodeAppearanceSection';

const useStyle = makeStyles()((theme) => ({
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
  diagramElementId,
  onBackToMainList,
}: PaletteExtensionSectionComponentProps) => {
  const { classes } = useStyle();
  const { nodeLookup } = useStoreApi<Node<NodeData>, Edge<EdgeData>>().getState();
  const diagramElement = nodeLookup.get(diagramElementId);

  const nodeAppearanceData = diagramElement?.data.nodeAppearanceData;

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title="Appearance" key="tooltip_appearance" placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-Appearance`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary="Appearance" />
        </ListItemButton>
      </Tooltip>
      {diagramElement && nodeAppearanceData?.gqlStyle.__typename === 'RectangularNodeStyle' ? (
        <RectangularNodeAppearanceSection nodeId={diagramElement.id} nodeData={diagramElement.data as NodeData} />
      ) : (
        <ListItem>
          <Typography>No appearance editor available for this style of element</Typography>
        </ListItem>
      )}
    </List>
  );
};
