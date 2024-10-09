/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import { useCallback, useEffect } from 'react';
import {
  GQLDropTreeItemData,
  GQLDropTreeItemPayload,
  GQLErrorPayload,
  GQLSuccessPayload,
  GQLDropTreeItemInput,
  GQLDropTreeItemVariables,
} from './useDropTreeItem.types';

const dropTreeItemMutation = gql`
  mutation dropTreeItem($input: DropTreeItemInput!) {
    dropTreeItem(input: $input) {
      __typename
      ... on ErrorPayload {
        messages {
          body
          level
        }
      }
      ... on SuccessPayload {
        messages {
          body
          level
        }
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDropTreeItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLDropTreeItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const useDropTreeItem = (editingContextId: string, treeId: string) => {
  const { addErrorMessage, addMessages } = useMultiToast();
  const [dropMutation, { data: dropTreeItemData, error: dropTreeItemError }] = useMutation<
    GQLDropTreeItemData,
    GQLDropTreeItemVariables
  >(dropTreeItemMutation);

  useEffect(() => {
    if (dropTreeItemError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (dropTreeItemData) {
      const { dropTreeItem } = dropTreeItemData;
      if (isSuccessPayload(dropTreeItem)) {
        addMessages(dropTreeItem.messages);
      }
      if (isErrorPayload(dropTreeItem)) {
        addMessages(dropTreeItem.messages);
      }
    }
  }, [dropTreeItemData, dropTreeItemError]);

  return useCallback((droppedElementIds: string[], targetElementId: string, index: number): void => {
    const input: GQLDropTreeItemInput = {
      id: crypto.randomUUID(),
      editingContextId,
      representationId: treeId,
      droppedElementIds,
      targetElementId,
      index,
    };
    dropMutation({ variables: { input } });
  }, []);
};
