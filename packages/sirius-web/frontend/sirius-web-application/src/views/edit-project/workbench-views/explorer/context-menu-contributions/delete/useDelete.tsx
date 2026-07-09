/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import {
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemVariables,
  GQLErrorPayload,
  GQLDeleteTreeItemPayload,
  UseDeleteValue,
} from './useDelete.types';

const deleteTreeItemMutation = gql`
  mutation deleteTreeItem($input: DeleteTreeItemInput!) {
    deleteTreeItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteTreeItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const useDelete = (): UseDeleteValue => {
  const [deleteTreeItem, mutationResult] = useMutation<GQLDeleteTreeItemData, GQLDeleteTreeItemVariables>(
    deleteTreeItemMutation
  );
  const { showDeletionConfirmation } = useDeletionConfirmationDialog();
  const { addErrorMessage, addMessages } = useMultiToast();

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

  useEffect(() => {
    if (mutationResult.error) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (mutationResult.data && isErrorPayload(mutationResult.data.deleteTreeItem)) {
      addMessages(mutationResult.data.deleteTreeItem.messages);
    }
  }, [mutationResult.data, mutationResult.error, addErrorMessage, addMessages]);

  return {
    handleDelete,
  };
};
