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
import { SelectionTarget, useSelectionTargets } from '@eclipse-sirius/sirius-components-core';
import { PaletteExtensionSectionComponentProps } from '@eclipse-sirius/sirius-components-palette';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import Typography from '@mui/material/Typography';
import React, { useContext } from 'react';
import { makeStyles } from 'tss-react/mui';
import { DiagramContext } from '../../contexts/DiagramContext';
import { DiagramContextValue } from '../../contexts/DiagramContext.types';
import { useStore } from '../../representation/useStore';

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
  listItemIcon: {
    minWidth: 0,
    marginRight: theme.spacing(2),
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

export const ShowInSection = ({ onBackToMainList, onClose }: PaletteExtensionSectionComponentProps) => {
  const { classes } = useStyle();
  const { getEdges, getNodes } = useStore();
  const { diagramId } = useContext<DiagramContextValue>(DiagramContext);
  const { selectionTargets } = useSelectionTargets();

  const handleBackToMainListClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>): void => {
    event.stopPropagation();
    onBackToMainList();
  };

  const pushDiagramSelectionTo = (target: SelectionTarget) => {
    const diagramSelection: string[] = [];
    [...getNodes(), ...getEdges()]
      .filter((element) => element.selected)
      .forEach((element) => {
        if (element.data?.targetObjectId) {
          diagramSelection.push(element.data?.targetObjectId);
        }
      });
    target.applySelection({ entries: diagramSelection.map((id) => ({ id })) });
  };

  const showInItems: JSX.Element[] = selectionTargets
    .filter((target) => target.id !== diagramId)
    .map((target) => (
      <Tooltip title={'Show in ' + target.label} placement="right" key={`push-diagram-selection-to-${target.id}`}>
        <ListItemButton
          className={classes.toolListItemButton}
          data-testid={`push-diagram-selection-to-${target.id}`}
          onClick={() => {
            pushDiagramSelectionTo(target);
            onClose();
          }}>
          <ListItemIcon className={classes.listItemIcon}>{target.icon}</ListItemIcon>
          <ListItemText primary={target.label} className={classes.listItemText} />
        </ListItemButton>
      </Tooltip>
    ));

  return (
    <List className={classes.toolList} component="nav">
      <Tooltip title="Show in" key="tooltip_show_in" placement="right">
        <ListItemButton
          className={classes.toolListItemButton}
          onClick={handleBackToMainListClick}
          data-testid={`back-show_in`}
          autoFocus={true}>
          <NavigateBeforeIcon />
          <ListItemText className={classes.sectionTitleListItemText} primary="Show in" />
        </ListItemButton>
      </Tooltip>
      {showInItems.length > 0 ? (
        showInItems
      ) : (
        <ListItem>
          <Typography>No available targets</Typography>
        </ListItem>
      )}
    </List>
  );
};
