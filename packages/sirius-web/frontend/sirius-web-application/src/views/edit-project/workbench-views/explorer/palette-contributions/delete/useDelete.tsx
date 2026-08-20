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
import { useDeletionConfirmationDialog, useReporting } from '@eclipse-sirius/sirius-components-core';
import { GQLTreeItem } from '@eclipse-sirius/sirius-components-trees';
import {
  GQLDeleteTreeItemData,
  GQLDeleteTreeItemInput,
  GQLDeleteTreeItemVariables,
  UseDeleteValue,
} from './useDelete.types';

const deleteTreeItemMutation = gql`
  mutation deleteTreeItem($input: DeleteTreeItemInput!) {
    deleteTreeItem(input: $input) {
      __typename
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

export const useDelete = (): UseDeleteValue => {
  const [deleteTreeItem, result] = useMutation<GQLDeleteTreeItemData, GQLDeleteTreeItemVariables>(
    deleteTreeItemMutation
  );
  useReporting(result, (payload) => payload.deleteTreeItem);

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

  return {
    handleDelete,
  };
};
