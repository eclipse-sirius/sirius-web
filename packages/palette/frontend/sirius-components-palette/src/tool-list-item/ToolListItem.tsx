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

import { IconOverlay, KeyBinding } from '@eclipse-sirius/sirius-components-core';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { isSingleClickOnDiagramElementTool } from '../Palette';
import { GQLTool } from '../Palette.types';
import { ToolListItemProps } from './ToolListItem.types';
import { ToolListItemText } from './ToolListItemText';

const useStyle = makeStyles()((theme) => ({
  listItemButton: {
    paddingTop: 0,
    paddingBottom: 0,
  },
  listItemIcon: {
    minWidth: 0,
    marginRight: theme.spacing(2),
  },
}));

export const ToolListItem = ({ tool, disabled, onToolClick, selected, searchedValue }: ToolListItemProps) => {
  const { classes } = useStyle();

  const handleToolClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tool: GQLTool) => {
    event.stopPropagation();
    onToolClick(tool);
  };

  return (
    <Tooltip title={tool.label} placement="right">
      <ListItemButton
        className={classes.listItemButton}
        disabled={disabled}
        onClick={(event) => handleToolClick(event, tool)}
        data-testid={`tool-${tool.label}`}
        selected={selected}>
        <ListItemIcon className={classes.listItemIcon}>
          <IconOverlay iconURLs={tool.iconURL} alt={tool.label} customIconHeight={16} customIconWidth={16} />
        </ListItemIcon>
        <ToolListItemText label={tool.label} searchedValue={searchedValue} />
        {isSingleClickOnDiagramElementTool(tool) && tool.keyBindings[0] ? (
          <KeyBinding keyBinding={tool.keyBindings[0]} data-testid={`key-binding-${tool.label}`} />
        ) : null}
      </ListItemButton>
    </Tooltip>
  );
};
