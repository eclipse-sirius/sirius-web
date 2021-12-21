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
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import { TreeItemContextMenuComponentProps } from 'index';
import React, { useContext } from 'react';
import { TreeItemContextMenuProps } from './TreeItemContextMenu.types';

export const TreeItemContextMenuContext = React.createContext([]);

export const TreeItemContextMenu = ({
  menuAnchor,
  editingContextId,
  treeId,
  item,
  readOnly,
  depth,
  onExpand,
  selection,
  setSelection,
  enterEditingMode,
  deleteItem,
  onClose,
}: TreeItemContextMenuProps) => {
  const treeItemContextMenuContributions = useContext(TreeItemContextMenuContext);

  const expandItem = () => {
    if (!item.expanded && item.hasChildren) {
      onExpand(item.id, depth);
    }
  };

  return (
    <Menu
      id="treeitem-contextmenu"
      anchorEl={menuAnchor}
      keepMounted
      open
      onClose={onClose}
      data-testid="treeitem-contextmenu"
      disableRestoreFocus={true}
      getContentAnchorEl={null}
      anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
      }}
    >
      {treeItemContextMenuContributions
        .filter((contribution) => contribution.props.canHandle(item))
        .map((contribution, index) => {
          const props: TreeItemContextMenuComponentProps = {
            editingContextId,
            item,
            readOnly,
            onClose,
            selection,
            setSelection,
            expandItem,
            key: index.toString(),
            treeId: treeId,
          };
          const element = React.createElement(contribution.props.component, props);
          return element;
        })}
      {item.editable ? (
        <MenuItem
          key="rename"
          onClick={enterEditingMode}
          data-testid="rename-tree-item"
          disabled={readOnly}
          aria-disabled
        >
          <ListItemIcon>
            <EditIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Rename" />
        </MenuItem>
      ) : null}
      {item.deletable ? (
        <MenuItem key="delete-tree-item" onClick={deleteItem} data-testid="delete" disabled={readOnly} aria-disabled>
          <ListItemIcon>
            <DeleteIcon fontSize="small" />
          </ListItemIcon>
          <ListItemText primary="Delete" />
        </MenuItem>
      ) : null}
    </Menu>
  );
};
