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

import { IconOverlay } from '@eclipse-sirius/sirius-components-core';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Tooltip from '@mui/material/Tooltip';
import { makeStyles } from 'tss-react/mui';
import { Tool } from '../DraggablePalette.types';
import { usePaletteEntryTooltip } from '../usePaletteEntryTooltip';
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
}));
export const ToolListItem = ({ tool, onToolClick }: ToolListItemProps) => {
  const { classes } = useStyle();
  const { tooltipEnterDelay, tooltipPlacement } = usePaletteEntryTooltip();
  const handleToolClick = (event: React.MouseEvent<HTMLDivElement, MouseEvent>, tool: Tool) => {
    event.stopPropagation();
    onToolClick(tool);
  };
  return (
    <Tooltip enterDelay={tooltipEnterDelay} placement={tooltipPlacement} title={tool.label}>
      <ListItemButton className={classes.listItemButton} onClick={(event) => handleToolClick(event, tool)}>
        <ListItemIcon className={classes.listItemIcon}>
          {tool.iconElement ?? null}
          <IconOverlay iconURL={tool.iconURL} alt={tool.label} customIconHeight={16} customIconWidth={16} />
        </ListItemIcon>
        <ListItemText primary={tool.label} className={classes.listItemText} />
      </ListItemButton>
    </Tooltip>
  );
};
