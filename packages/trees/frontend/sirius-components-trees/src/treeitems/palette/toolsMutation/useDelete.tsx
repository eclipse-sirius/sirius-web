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
import { useEffect } from 'react';
import { GQLTreeItem } from '../../../views/TreeView.types';
import {
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemPayload,
  GQLDeleteTreeItemVariables,
  GQLErrorPayload,
  UseDeleteValue,
} from './useDelete.types';

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

export const useDelete = (): UseDeleteValue => {
  const [deleteTreeItem, { data, error }] = useMutation<GQLDeleteTreeItemData, GQLDeleteTreeItemVariables>(
    deleteTreeItemMutation
  );
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();

  const handleDelete = (editingContextId: string, treeId: string, item: GQLTreeItem) => {
    if (item.deletable) {
      const input: GQLDeleteTreeItemInput = {
        id: crypto.randomUUID(),
        editingContextId,
        representationId: treeId,
        treeItemId: item.id,
      };
      showDeletionConfirmation(() => {
        deleteTreeItem({ variables: { input } });
      });
    }
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

  return {
    handleDelete,
  };
};
