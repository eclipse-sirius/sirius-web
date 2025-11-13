/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import {
  GQLErrorPayload,
  GQLRenameImageMutationData,
  GQLRenameImageMutationVariables,
  GQLRenameImagePayload,
  RenameImageModalProps,
  RenameImageModalState,
} from './RenameImageModal.types';

const renameImageMutation = gql`
  mutation renameImage($input: RenameImageInput!) {
    renameImage(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isNameInvalid = (name: string) => name.trim().length < 3;

const isErrorPayload = (payload: GQLRenameImagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const RenameImageModal = ({ imageId, initialImageName, onImageRenamed, onClose }: RenameImageModalProps) => {
  const { addErrorMessage } = useMultiToast();
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'renameImageModal' });
  const [state, setState] = useState<RenameImageModalState>({
    name: initialImageName,
  });

  const onNewName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const name = event.target.value;
    setState((prevState) => ({
      ...prevState,
      name: name,
    }));
  };

  const [renameImage, { loading, data, error }] = useMutation<GQLRenameImageMutationData>(renameImageMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      if (data) {
        const { renameImage } = data;
        if (isErrorPayload(renameImage)) {
          const { message } = renameImage;
          addErrorMessage(message);
          onClose();
        } else {
          onImageRenamed();
        }
      }
    }
  }, [loading, data, error]);

  const onRenameImage = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    event.preventDefault();
    const variables: GQLRenameImageMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        imageId,
        newLabel: state.name,
      },
    };
    renameImage({ variables });
  };

  const isError = isNameInvalid(state.name);
  const isInvalid = isError || state.name === initialImageName;
  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
        <DialogContent>
          <TextField
            variant="standard"
            value={state.name}
            error={isError}
            helperText={t('name.helperText')}
            onChange={onNewName}
            label={t('name.label')}
            placeholder={t('name.placeholder')}
            data-testid="name"
            autoFocus
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            onClick={onRenameImage}
            color="primary"
            data-testid="rename-image"
            disabled={isInvalid}>
            {t('submit')}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
