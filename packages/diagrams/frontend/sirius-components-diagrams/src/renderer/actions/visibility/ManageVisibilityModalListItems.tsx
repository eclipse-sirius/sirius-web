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
import { Node } from '@xyflow/react';
import { NodeData } from '../../DiagramRenderer.types';
import { ManageVisibilityListItemsProps } from './ManageVisibilityModalListItems.types';

const listMaxHeight = 220;

const getLabel = (node: Node<NodeData>): string => {
  if (node.data.insideLabel) {
    return node.data.insideLabel.text;
  } else {
    return node.data.targetObjectLabel;
  }
};

export const ManageVisibilityModalListItems = ({ items, onChecked }: ManageVisibilityListItemsProps) => {
  return (
    <Box sx={{ maxHeight: listMaxHeight, overflow: 'auto' }}>
      <List dense disablePadding>
        {items.map((value) => (
          <ListItemButton
            onClick={() => onChecked(value)}
            key={value.id}
            dense
            data-testid={`manage_visibility_list_item_button_${getLabel(value)}`}>
            <ListItemIcon>
              <Checkbox edge="start" tabIndex={-1} disableRipple checked={value.hidden} size={'small'} />
            </ListItemIcon>
            <ListItemText id={`checkbox-list-label-${value}`} primary={`${getLabel(value)}`} />
          </ListItemButton>
        ))}
      </List>
    </Box>
  );
};
