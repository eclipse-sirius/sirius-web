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
import { gql, useMutation } from '@apollo/client';
import { useDeletionConfirmationDialog, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import DeleteIcon from '@mui/icons-material/Delete';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import {
  DeleteMenuItemProps,
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemPayload,
  GQLDeleteTreeItemVariables,
  GQLErrorPayload,
} from './DeleteMenuItem.types';

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

export const DeleteMenuItem = ({ editingContextId, treeId, item, readOnly, onClick }: DeleteMenuItemProps) => {
  const [deleteTreeItem, { data, error }] = useMutation<GQLDeleteTreeItemData, GQLDeleteTreeItemVariables>(
    deleteTreeItemMutation
  );
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'deleteMenuItem' });

  const handleDelete = () => {
    const input: GQLDeleteTreeItemInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: treeId,
      treeItemId: item.id,
    };
    showDeletionConfirmation(() => {
      deleteTreeItem({ variables: { input } });
      onClick();
    });
  };

  const { addErrorMessage } = useMultiToast();
  useEffect(() => {
    if (error) {
      addErrorMessage('An error has occurred while executing this action, please contact the server administrator');
    }
    if (data) {
      const { deleteTreeItem } = data;
      if (isErrorPayload(deleteTreeItem)) {
        addErrorMessage(deleteTreeItem.message);
      }
    }
  }, [error, data]);

  if (!item.deletable) {
    return null;
  }
  return (
    <MenuItem key="delete-tree-item" onClick={handleDelete} data-testid="delete" disabled={readOnly} aria-disabled>
      <ListItemIcon>
        <DeleteIcon fontSize="small" />
      </ListItemIcon>
      <ListItemText primary={t('delete')} />
    </MenuItem>
  );
};
