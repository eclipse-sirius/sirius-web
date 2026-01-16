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

import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import KeyboardCommandKeyIcon from '@mui/icons-material/KeyboardCommandKey';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { isSingleClickOnDiagramElementTool } from '../Palette';
import { GQLTool } from '../Palette.types';
import { ToolListItemProps } from './ToolListItem.types';

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
  keyBinding: {
    flex: '0 0 auto',
    marginLeft: 'auto',
    minWidth: 'fit-content',
    overflow: 'hidden',
    display: 'flex',
    alignItems: 'center',
    color: theme.palette.text.disabled,
  },
}));

const renderKeyBinding = (tool: GQLTool) => {
  const { classes } = useStyle();
  if (isSingleClickOnDiagramElementTool(tool) && tool.keyBindings.length > 0) {
    const keyBinding = tool.keyBindings.at(0)!;
    return (
      <div className={classes.keyBinding} data-testid={`key-binding-${tool.label}`}>
        {keyBinding.isCtrl ? 'Ctrl+' : ''}
        {keyBinding.isMeta ? <KeyboardCommandKeyIcon /> : null}
        {keyBinding.isAlt ? 'Alt+' : ''}
        {keyBinding.key}
      </div>
    );
  }
  return null;
};

export const ToolListItem = ({ tool, disabled, onToolClick }: ToolListItemProps) => {
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
        data-testid={`tool-${tool.label}`}>
        <ListItemIcon className={classes.listItemIcon}>
          <IconOverlay iconURLs={tool.iconURL} alt={tool.label} customIconHeight={16} customIconWidth={16} />
        </ListItemIcon>
        <ListItemText primary={tool.label} className={classes.listItemText} />
        {renderKeyBinding(tool)}
      </ListItemButton>
    </Tooltip>
  );
};
