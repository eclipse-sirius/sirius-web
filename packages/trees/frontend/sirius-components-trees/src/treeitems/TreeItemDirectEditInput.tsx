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
import { GQLSuccessPayload, useMultiToast, useReporting } from '@eclipse-sirius/sirius-components-core';
import TextField from '@mui/material/TextField';
import { useEffect, useRef, useState } from 'react';
import { useTranslation } from 'react-i18next';
import {
  GQLInitialDirectEditElementLabelData,
  GQLInitialDirectEditElementLabelVariables,
  GQLRenameTreeItemMutationData,
  GQLRenameTreeItemMutationVariables,
  GQLRenameTreeItemPayload,
  TreeItemDirectEditInputProps,
  TreeItemDirectEditInputState,
} from './TreeItemDirectEditInput.types';

const renameTreeItemMutation = gql`
  mutation renameTreeItem($input: RenameTreeItemInput!) {
    renameTreeItem(input: $input) {
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

  const { addErrorMessage } = useMultiToast();

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

  const [renameTreeItem, result] = useMutation<GQLRenameTreeItemMutationData, GQLRenameTreeItemMutationVariables>(
    renameTreeItemMutation
  );
  useReporting(result, (payload) => payload.renameTreeItem);

  useEffect(() => {
    if (result.data && isSuccessPayload(result.data.renameTreeItem)) {
      onClose();
    }
  }, [result.data]);

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
