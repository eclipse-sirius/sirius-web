/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import {
  Toast,
  useDeletionConfirmationDialog,
  useComponents,
  ComponentExtension,
} from '@eclipse-sirius/sirius-components-core';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import UnfoldMore from '@material-ui/icons/UnfoldMore';
import { useEffect, useState } from 'react';
import {
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemPayload,
  GQLDeleteTreeItemVariables,
  GQLErrorPayload,
  TreeItemContextMenuProps,
  TreeItemContextMenuState,
} from './TreeItemContextMenu.types';
import { treeItemContextMenuEntryExtensionPoint } from './TreeItemContextMenuEntryExtensionPoints';
import { TreeItemContextMenuComponentProps } from './TreeItemContextMenuEntry.types';

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

export const TreeItemContextMenu = ({
  menuAnchor,
  editingContextId,
  treeId,
  item,
  readOnly,
  depth,
  onExpand,
  onExpandAll,
  enterEditingMode,
  onClose,
}: TreeItemContextMenuProps) => {
  const [state, setState] = useState<TreeItemContextMenuState>({ message: null });

  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const treeItemMenuContextComponents: ComponentExtension<TreeItemContextMenuComponentProps>[] = useComponents(
    treeItemContextMenuEntryExtensionPoint
  );

  const expandItem = () => {
    if (!item.expanded && item.hasChildren) {
      onExpand(item.id, depth);
    }
  };

  const [deleteTreeItem, { loading: deleteTreeItemLoading, data: deleteTreeItemData, error: deleteTreeItemError }] =
    useMutation<GQLDeleteTreeItemData, GQLDeleteTreeItemVariables>(deleteTreeItemMutation);

  const deleteItem = () => {
    const input: GQLDeleteTreeItemInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
    };
    showDeletionConfirmation(() => {
      deleteTreeItem({ variables: { input } });
      onClose();
    });
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
        }}>
        {treeItemMenuContextComponents.map(({ Component: TreeItemMenuContextComponent }, index) => (
          <TreeItemMenuContextComponent
            editingContextId={editingContextId}
            item={item}
            readOnly={readOnly}
            onClose={onClose}
            expandItem={expandItem}
            key={index.toString()}
            treeId={treeId}
          />
        ))}
        {item.hasChildren ? (
          <MenuItem
            key="expand-all"
            data-testid="expand-all"
            onClick={() => {
              onExpandAll(item);
              onClose();
            }}
            disabled={readOnly}
            aria-disabled>
            <ListItemIcon>
              <UnfoldMore fontSize="small" />
            </ListItemIcon>
            <ListItemText primary="Expand all" />
          </MenuItem>
        ) : null}
        {item.editable ? (
          <MenuItem
            key="rename"
            onClick={enterEditingMode}
            data-testid="rename-tree-item"
            disabled={readOnly}
            aria-disabled>
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
      <Toast message={state.message} open={!!state.message} onClose={() => setState({ message: null })} />
    </>
  );
};
