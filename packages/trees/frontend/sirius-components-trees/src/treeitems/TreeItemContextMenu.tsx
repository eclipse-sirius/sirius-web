/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
import { gql, useMutation } from '@apollo/client';
import IconButton from '@material-ui/core/IconButton';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import React, { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import {
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemPayload,
  GQLDeleteTreeItemVariables,
  GQLErrorPayload,
  TreeItemContextMenuProps,
  TreeItemContextMenuState,
} from './TreeItemContextMenu.types';
import { TreeItemContextMenuComponentProps } from './TreeItemContextMenuContribution.types';

const deleteTreeItemMutation = gql`
  mutation deleteTreeItem($input: DeleteTreeItemInput!) {
    deleteTreeItem(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteTreeItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const TreeItemContextMenuContext = React.createContext([]);

export const TreeItemContextMenu = ({
  menuAnchor,
  editingContextId,
  treeId,
  item,
  readOnly,
  treeItemMenuContributionComponents,
  depth,
  onExpand,
  selection,
  setSelection,
  enterEditingMode,
  onClose,
}: TreeItemContextMenuProps) => {
  const [state, setState] = useState<TreeItemContextMenuState>({ message: null });
  const closeToast = () => setState({ message: null });

  const expandItem = () => {
    if (!item.expanded && item.hasChildren) {
      onExpand(item.id, depth);
    }
  };

  const [deleteTreeItem, { loading: deleteTreeItemLoading, data: deleteTreeItemData, error: deleteTreeItemError }] =
    useMutation<GQLDeleteTreeItemData, GQLDeleteTreeItemVariables>(deleteTreeItemMutation);

  const deleteItem = () => {
    const input: GQLDeleteTreeItemInput = {
      id: uuid(),
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
    };
    deleteTreeItem({ variables: { input } });
    onClose();
  };

  useEffect(() => {
    if (!deleteTreeItemLoading) {
      if (deleteTreeItemError) {
        setState({
          message: 'An error has occurred while executing this action, please contact the server administrator',
        });
      }
      if (deleteTreeItemData) {
        const { deleteTreeItem } = deleteTreeItemData;
        if (isErrorPayload(deleteTreeItem)) {
          const { message } = deleteTreeItem;
          setState({ message });
        }
      }
    }
  }, [deleteTreeItemLoading, deleteTreeItemError, deleteTreeItemData]);

  return (
    <>
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
        {treeItemMenuContributionComponents.map((component, index) => {
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
          const element = React.createElement(component, props);
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
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={state.message != null}
        autoHideDuration={3000}
        onClose={closeToast}
        message={state.message}
        action={
          <IconButton size="small" aria-label="close" color="inherit" onClick={closeToast}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
