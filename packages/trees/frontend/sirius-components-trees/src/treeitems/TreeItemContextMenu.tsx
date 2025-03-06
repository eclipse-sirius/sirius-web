/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
import { gql, useLazyQuery, useMutation } from '@apollo/client';
import {
  ComponentExtension,
  IconOverlay,
  useComponents,
  useData,
  useDeletionConfirmationDialog,
  useMultiToast,
} from '@eclipse-sirius/sirius-components-core';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import UnfoldMore from '@mui/icons-material/UnfoldMore';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { useEffect } from 'react';
import {
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemPayload,
  GQLDeleteTreeItemVariables,
  GQLErrorPayload,
  GQLFetchTreeItemContextEntryDataData,
  GQLGetFetchTreeItemContextMenuEntryDataQueryVariables,
  GQLInvokeSingleClickTreeItemContextMenuEntryData,
  GQLInvokeSingleClickTreeItemContextMenuEntryInput,
  GQLInvokeSingleClickTreeItemContextMenuEntryVariables,
  TreeItemContextMenuEntry,
  TreeItemContextMenuProps,
} from './TreeItemContextMenu.types';
import { TreeItemContextMenuComponentProps } from './TreeItemContextMenuEntry.types';
import {
  treeItemContextMenuEntryExtensionPoint,
  treeItemContextMenuEntryOverrideExtensionPoint,
} from './TreeItemContextMenuEntryExtensionPoints';
import { useContextMenuEntries } from './useContextMenuEntries';
import { TreeItemContextMenuOverrideContribution } from './TreeItemContextMenuEntryExtensionPoints.types';

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

const invokeSingleClickTreeItemContextMenuEntryMutation = gql`
  mutation invokeSingleClickTreeItemContextMenuEntry($input: InvokeSingleClickTreeItemContextMenuEntryInput!) {
    invokeSingleClickTreeItemContextMenuEntry(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const getFetchTreeItemContextMenuEntryDataQuery = gql`
  query getFetchTreeItemContextMenuEntryDataQuery(
    $editingContextId: ID!
    $representationId: ID!
    $treeItemId: ID!
    $menuEntryId: ID!
  ) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              fetchTreeItemContextMenuEntryData(treeItemId: $treeItemId, menuEntryId: $menuEntryId) {
                urlToFetch
                fetchKind
              }
            }
          }
        }
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
  const { addErrorMessage } = useMultiToast();

  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const treeItemMenuContextComponents: ComponentExtension<TreeItemContextMenuComponentProps>[] = useComponents(
    treeItemContextMenuEntryExtensionPoint
  );

  const { data: treeItemContextMenuOverrideContributions } = useData<TreeItemContextMenuOverrideContribution[]>(
    treeItemContextMenuEntryOverrideExtensionPoint
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
        addErrorMessage('An error has occurred while executing this action, please contact the server administrator');
      }
      if (deleteTreeItemData) {
        const { deleteTreeItem } = deleteTreeItemData;
        if (isErrorPayload(deleteTreeItem)) {
          addErrorMessage(deleteTreeItem.message);
        }
      }
    }
  }, [deleteTreeItemLoading, deleteTreeItemError, deleteTreeItemData]);

  const { contextMenuEntries } = useContextMenuEntries(editingContextId, treeId, item.id);

  const [getFetchData, { loading, data, error }] = useLazyQuery<
    GQLFetchTreeItemContextEntryDataData,
    GQLGetFetchTreeItemContextMenuEntryDataQueryVariables
  >(getFetchTreeItemContextMenuEntryDataQuery);
  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }

      if (data) {
        const { urlToFetch, fetchKind } =
          data.viewer.editingContext.representation.description.fetchTreeItemContextMenuEntryData;
        if (fetchKind === 'DOWNLOAD') {
          window.location.href = urlToFetch;
        } else if (fetchKind === 'OPEN') {
          window.open(urlToFetch, '_blank', 'noopener,noreferrer');
        }
        onClose();
      }
    }
  }, [loading, data, error]);

  const invokeFetch = (menuEntryId: string) => {
    const variables: GQLGetFetchTreeItemContextMenuEntryDataQueryVariables = {
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
      menuEntryId,
    };
    getFetchData({ variables });
  };

  const [
    invokeSingleClickTreeItemContextMenuEntry,
    { loading: invokeSingleClickLoading, data: invokeSingleClickData, error: invokeSingleClickError },
  ] = useMutation<
    GQLInvokeSingleClickTreeItemContextMenuEntryData,
    GQLInvokeSingleClickTreeItemContextMenuEntryVariables
  >(invokeSingleClickTreeItemContextMenuEntryMutation);

  const invokeSingleClick = (menuEntryId: string) => {
    const input: GQLInvokeSingleClickTreeItemContextMenuEntryInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
      menuEntryId,
    };
    invokeSingleClickTreeItemContextMenuEntry({ variables: { input } });
  };

  useEffect(() => {
    if (!invokeSingleClickLoading) {
      if (invokeSingleClickError) {
        addErrorMessage('An error has occurred while executing this action, please contact the server administrator');
      }
      if (invokeSingleClickData) {
        const { invokeSingleClickTreeItemContextMenuEntry } = invokeSingleClickData;
        if (isErrorPayload(invokeSingleClickTreeItemContextMenuEntry)) {
          addErrorMessage(invokeSingleClickTreeItemContextMenuEntry.message);
        }
      }
    }
  }, [invokeSingleClickLoading, invokeSingleClickError, invokeSingleClickData]);

  const invokeContextMenuEntry = (menuEntry: TreeItemContextMenuEntry) => {
    if (menuEntry.__typename === 'FetchTreeItemContextMenuEntry') {
      invokeFetch(menuEntry.id);
    } else if (menuEntry.__typename === 'SingleClickTreeItemContextMenuEntry') {
      invokeSingleClick(menuEntry.id);
      onClose();
    }
  };

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
        {contextMenuEntries.map((entry) => {
          const contributedTreeItemMenuContextComponents = treeItemContextMenuOverrideContributions
            .filter((contribution) => contribution.canHandle(entry))
            .map((contribution) => contribution.component);
          if (contributedTreeItemMenuContextComponents.length > 0) {
            return contributedTreeItemMenuContextComponents.map((TreeItemMenuContextComponent, index) => (
              <TreeItemMenuContextComponent
                editingContextId={editingContextId}
                item={item}
                readOnly={readOnly}
                onClose={onClose}
                expandItem={expandItem}
                key={index.toString()}
                treeId={treeId}
              />
            ));
          } else {
            return (
              <MenuItem
                key={entry.id}
                onClick={(_) => invokeContextMenuEntry(entry)}
                data-testid={`context-menu-entry-${entry.label}`}
                disabled={readOnly}
                aria-disabled>
                <ListItemIcon>
                  {entry.iconURL.length > 0 ? (
                    <IconOverlay iconURL={entry.iconURL} alt={entry.label} title={entry.label} />
                  ) : (
                    <div style={{ marginRight: '16px' }} />
                  )}
                </ListItemIcon>
                <ListItemText primary={entry.label} />
              </MenuItem>
            );
          }
        })}
      </Menu>
    </>
  );
};
