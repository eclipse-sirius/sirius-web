/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import Divider from '@material-ui/core/Divider';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import makeStyles from '@material-ui/core/styles/makeStyles';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import React from 'react';
import { TreeItemContextMenuProps } from './TreeItemContextMenu.types';

const useTreeItemContextMenuStyles = makeStyles((theme) => ({
  item: {
    paddingTop: theme.spacing(0),
    paddingBottom: theme.spacing(0),
  },
}));

export const TreeItemContextMenu = ({
  menuAnchor,
  item,
  editingContextId,
  readOnly,
  enterEditingMode,
  openModal,
  deleteItem,
  closeContextMenu,
  treeItemHandler,
}: TreeItemContextMenuProps) => {
  const classes = useTreeItemContextMenuStyles();
  const entries: Array<React.ReactElement> = [];
  // Creation operations (type-specific)
  treeItemHandler
    .getMenuEntries(item, editingContextId, readOnly, openModal, closeContextMenu, classes)
    .forEach((entry) => entries.push(entry));
  if (entries.length > 0) {
    entries.push(<Divider key="custom-entries-end" />);
  }
  // Generic edition operations
  if (item.editable) {
    entries.push(
      <MenuItem
        key="rename"
        onClick={enterEditingMode}
        data-testid="rename-tree-item"
        disabled={readOnly}
        dense
        className={classes.item}>
        <ListItemIcon>
          <EditIcon fontSize="small" />
        </ListItemIcon>
        <ListItemText primary="Rename" />
      </MenuItem>
    );
  }
  if (item.deletable) {
    entries.push(
      <MenuItem
        key="delete-tree-item"
        onClick={deleteItem}
        data-testid="delete"
        disabled={readOnly}
        dense
        className={classes.item}>
        <ListItemIcon>
          <DeleteIcon fontSize="small" />
        </ListItemIcon>
        <ListItemText primary="Delete" />
      </MenuItem>
    );
  }

  return (
    <Menu
      id="treeitem-contextmenu"
      anchorEl={menuAnchor}
      keepMounted
      open
      onClose={closeContextMenu}
      data-testid="treeitem-contextmenu"
      disableRestoreFocus={true}
      getContentAnchorEl={null}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}>
      {entries}
    </Menu>
  );
};
