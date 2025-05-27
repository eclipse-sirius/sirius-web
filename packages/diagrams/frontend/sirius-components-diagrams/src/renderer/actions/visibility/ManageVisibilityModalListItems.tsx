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

import Box from '@mui/material/Box';
import Checkbox from '@mui/material/Checkbox';
import List from '@mui/material/List';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { getNodeLabel } from '../../node/NodeUtils';
import { ManageVisibilityListItemsProps } from './ManageVisibilityModalListItems.types';

const listMaxHeight = 220;

export const ManageVisibilityModalListItems = ({ nodes, onListItemClick }: ManageVisibilityListItemsProps) => {
  return (
    <Box sx={{ maxHeight: listMaxHeight, overflow: 'auto' }}>
      <List dense disablePadding>
        {nodes.map((node) => (
          <ListItemButton
            onClick={() => onListItemClick(node)}
            key={node.id}
            dense
            data-testid={`manage_visibility_list_item_button_${getNodeLabel(node)}`}>
            <ListItemIcon sx={{ minWidth: 0 }}>
              <Checkbox
                disableRipple
                checked={!node.hidden}
                size="small"
                sx={(theme) => ({ padding: theme.spacing(0.5) })}
              />
            </ListItemIcon>
            <ListItemText id={`checkbox-list-label-${node}`} primary={getNodeLabel(node)} />
          </ListItemButton>
        ))}
      </List>
    </Box>
  );
};
