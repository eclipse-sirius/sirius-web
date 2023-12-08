/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import { gql, useMutation, useQuery } from '@apollo/client';
import { useMultiToast } from '@eclipse-sirius/sirius-components-core';
import TextField from '@material-ui/core/TextField';
import { useEffect, useRef, useState } from 'react';
import {
  GQLErrorPayload,
  GQLInitialDirectEditElementLabelData,
  GQLInitialDirectEditElementLabelInput,
  GQLRenameTreeItemPayload,
  GQLSuccessPayload,
  TreeItemDirectEditInputProps,
  TreeItemDirectEditInputState,
} from './TreeItemDirectEditInput.types';

const renameTreeItemMutation = gql`
  mutation renameTreeItem($input: RenameTreeItemInput!) {
    renameTreeItem(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const initialDirectEditElementLabeQuery = gql`
  query initialDirectEditElementLabel($editingContextId: ID!, $representationId: ID!, $treeItemId: ID!) {
    viewer {
      editingContext(editingContextId: $editingContextId) {
        representation(representationId: $representationId) {
          description {
            ... on TreeDescription {
              initialDirectEditTreeItemLabel(treeItemId: $treeItemId)
            }
          }
        }
      }
    }
  }
`;

export const TreeItemDirectEditInput = ({
  editingContextId,
  treeId,
  treeItemId,
  editingkey,
  onClose,
}: TreeItemDirectEditInputProps) => {
  const [state, setState] = useState<TreeItemDirectEditInputState>({
    newLabel: editingkey,
  });

  const { addErrorMessage } = useMultiToast();

  const isErrorPayload = (payload: GQLRenameTreeItemPayload): payload is GQLErrorPayload =>
    payload.__typename === 'ErrorPayload';
  const isSuccessPayload = (payload: GQLRenameTreeItemPayload): payload is GQLSuccessPayload =>
    payload.__typename === 'SuccessPayload';

  const textInput = useRef(null);

  const { data: initialLabelTreeItemItemData, error: initialLabelTreeItemItemError } = useQuery<
    GQLInitialDirectEditElementLabelData,
    GQLInitialDirectEditElementLabelInput
  >(initialDirectEditElementLabeQuery, {
    variables: {
      editingContextId: editingContextId,
      representationId: treeId,
      treeItemId: treeItemId,
    },
  });

  useEffect(() => {
    let cleanup = undefined;
    if (initialLabelTreeItemItemError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (initialLabelTreeItemItemData?.viewer.editingContext.representation.description.initialDirectEditTreeItemLabel) {
      const initialLabel =
        initialLabelTreeItemItemData?.viewer.editingContext.representation.description.initialDirectEditTreeItemLabel;
      if (!editingkey) {
        setState((prevState) => {
          return { ...prevState, newLabel: initialLabel };
        });
        const timeOutId = setTimeout(() => {
          textInput.current.select();
        }, 0);
        cleanup = () => clearTimeout(timeOutId);
      }
    }
    return cleanup;
  }, [initialLabelTreeItemItemError, initialLabelTreeItemItemData]);

  const [renameTreeItem, { data: renameTreeItemData, error: renameTreeItemError }] =
    useMutation(renameTreeItemMutation);

  useEffect(() => {
    if (renameTreeItemError) {
      addErrorMessage('An unexpected error has occurred, please refresh the page');
    }
    if (renameTreeItemData) {
      const { renameTreeItem } = renameTreeItemData;
      if (isErrorPayload(renameTreeItem)) {
        addErrorMessage(renameTreeItem.message);
      } else if (isSuccessPayload(renameTreeItem)) {
        if (renameTreeItem.__typename === 'SuccessPayload') {
          onClose();
        }
      }
    }
  }, [renameTreeItemData, renameTreeItemError]);

  const doRename = () => {
    renameTreeItem({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId: editingContextId,
          representationId: treeId,
          treeItemId: treeItemId,
          newLabel: state.newLabel,
        },
      },
    });
  };

  const handleChange = (event) => {
    const newLabel = event.target.value;
    setState((prevState) => {
      return { ...prevState, newLabel: newLabel };
    });
  };

  const onFinishEditing = (event) => {
    const { key } = event;
    if (key === 'Enter') {
      doRename();
    } else if (key === 'Escape') {
      onClose();
    }
  };

  const onFocusIn = (event) => event.target.select();

  useEffect(() => {
    document.addEventListener('mousedown', doRename);
    return () => document.removeEventListener('mousedown', doRename);
  });

  return (
    <>
      <TextField
        name="name"
        size="small"
        inputRef={textInput}
        placeholder={'Enter the new name'}
        value={state.newLabel}
        onChange={handleChange}
        onFocus={onFocusIn}
        onKeyDown={onFinishEditing}
        autoFocus
        data-testid="name-edit"
      />
    </>
  );
};
