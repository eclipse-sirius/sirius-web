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
import { GQLSuccessPayload, useReporting } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import {
  DeleteImageModalProps,
  GQLDeleteImageMutationData,
  GQLDeleteImageMutationVariables,
  GQLDeleteImagePayload,
} from './DeleteImageModal.types';

const deleteImageMutation = gql`
  mutation deleteImage($input: DeleteImageInput!) {
    deleteImage(input: $input) {
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

const isSuccessPayload = (payload: GQLDeleteImagePayload): payload is GQLSuccessPayload =>
  payload.__typename === 'SuccessPayload';

export const DeleteImageModal = ({ imageId, onImageDeleted, onClose }: DeleteImageModalProps) => {
  const { t } = useTranslation('sirius-web-application', { keyPrefix: 'deleteImageModal' });

  const [deleteImage, result] = useMutation<GQLDeleteImageMutationData>(deleteImageMutation);
  useReporting(result, (payload) => payload.deleteImage);

  useEffect(() => {
    if (result.data && isSuccessPayload(result.data.deleteImage)) {
      onImageDeleted();
    }
  }, [result]);

  const onDeleteImage = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    event.preventDefault();
    const variables: GQLDeleteImageMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        imageId,
      },
    };
    deleteImage({ variables });
  };

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">{t('title')}</DialogTitle>
        <DialogContent>
          <DialogContentText>{t('content')}</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" onClick={onDeleteImage} color="primary" data-testid="delete-image">
            {t('submit')}
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
