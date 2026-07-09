/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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
import { GQLErrorPayload, GQLSuccessPayload, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import TextField from '@mui/material/TextField';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import {
  GQLInitialDirectEditElementLabelData,
  GQLInitialDirectEditElementLabelVariables,
  GQLRenameTreeItemMutationData,
  GQLRenameTreeItemPayload,
  GQLRenameTreeItemMutationVariables,
  TreeItemDirectEditInputProps,
  TreeItemDirectEditInputState,
} from './TreeItemDirectEditInput.types';

const renameTreeItemMutation = gql`
  mutation renameTreeItem($input: RenameTreeItemInput!) {
    renameTreeItem(input: $input) {
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

const isErrorPayload = (payload: GQLRenameTreeItemPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
const isSuccessPayload = (payload: GQLRenameTreeItemPayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const TreeItemDirectEditInput = ({
  editingContextId,
  treeId,
  treeItemId,
  editingKey,
  onClose,
}: TreeItemDirectEditInputProps) => {
  const initialLabel = editingKey === null || editingKey === '' ? '' : editingKey;
  const { t } = useTranslation('sirius-components-trees', { keyPrefix: 'treeItemDirectEditInput' });
  const [state, setState] = useState<TreeItemDirectEditInputState>({
    newLabel: initialLabel,
  });

  const { addErrorMessage, addMessages } = useMultiToast();

  const textInput = useRef<HTMLInputElement | null>(null);
  const editionFinished = useRef<boolean>(false);

  const { data: initialLabelTreeItemItemData, error: initialLabelTreeItemItemError } = useQuery<
    GQLInitialDirectEditElementLabelData,
    GQLInitialDirectEditElementLabelVariables
  >(initialDirectEditElementLabeQuery, {
    variables: {
      editingContextId: editingContextId,
      representationId: treeId,
      treeItemId: treeItemId,
    },
  });

  useEffect(() => {
    if (initialLabelTreeItemItemError) {
      addErrorMessage(t('errors.unexpected'));
    }
    const initialLabel =
      initialLabelTreeItemItemData?.viewer.editingContext.representation.description.initialDirectEditTreeItemLabel;
    if (initialLabel) {
      if (!editingKey) {
        setState((prevState) => {
          return { ...prevState, newLabel: initialLabel };
        });
        const timeOutId = setTimeout(() => {
          if (textInput.current) {
            textInput.current.select();
          }
        }, 0);

        return () => clearTimeout(timeOutId);
      }
    }
    return () => {};
  }, [initialLabelTreeItemItemError, initialLabelTreeItemItemData]);

  const [renameTreeItem, renameTreeItemResult] = useMutation<
    GQLRenameTreeItemMutationData,
    GQLRenameTreeItemMutationVariables
  >(renameTreeItemMutation);

  useEffect(() => {
    if (renameTreeItemResult.error) {
      addErrorMessage(t('errors.unexpected'));
    }
    if (renameTreeItemResult.data && isErrorPayload(renameTreeItemResult.data.renameTreeItem)) {
      addMessages(renameTreeItemResult.data.renameTreeItem.messages);
    }
    if (renameTreeItemResult.data && isSuccessPayload(renameTreeItemResult.data.renameTreeItem)) {
      onClose();
    }
  }, [renameTreeItemResult.data, renameTreeItemResult.error, addErrorMessage, addMessages, onClose, t]);

  const doRename = () => {
    renameTreeItem({
      variables: {
        input: {
          id: crypto.randomUUID(),
          editingContextId,
          representationId: treeId,
          treeItemId,
          newLabel: state.newLabel,
        },
      },
    });
  };

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement | HTMLInputElement>) => {
    const newLabel = event.target.value;
    setState((prevState) => {
      return { ...prevState, newLabel: newLabel };
    });
  };

  const onFinishEditing = (event: React.KeyboardEvent<HTMLDivElement>) => {
    const { key } = event;
    if (key === 'Enter' && !event.shiftKey) {
      editionFinished.current = true;
      event.preventDefault();
      doRename();
    } else if (key === 'Escape') {
      editionFinished.current = true;
      onClose();
    }
  };

  const onFocusIn = (event: React.FocusEvent<HTMLTextAreaElement | HTMLInputElement>) => event.target.select();

  const onBlur = () => {
    if (!editionFinished.current) {
      doRename();
    }
  };

  return (
    <>
      <TextField
        variant="standard"
        name="name"
        size="small"
        inputRef={textInput}
        placeholder={t('enterNewValue')}
        value={state.newLabel}
        onChange={handleChange}
        onFocus={onFocusIn}
        onKeyDown={onFinishEditing}
        onBlur={onBlur}
        autoFocus
        spellCheck={false}
        data-testid="name-edit"
      />
    </>
  );
};
