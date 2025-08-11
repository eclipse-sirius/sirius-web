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
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import React, { useEffect } from 'react';
import {
  DeleteImageModalProps,
  GQLDeleteImageMutationData,
  GQLDeleteImageMutationVariables,
  GQLDeleteImagePayload,
  GQLErrorPayload,
} from './DeleteImageModal.types';

const deleteImageMutation = gql`
  mutation deleteImage($input: DeleteImageInput!) {
    deleteImage(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteImagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const DeleteImageModal = ({ imageId, onImageDeleted, onClose }: DeleteImageModalProps) => {
  const { addErrorMessage } = useMultiToast();

  const [deleteImage, { loading, data, error }] = useMutation<GQLDeleteImageMutationData>(deleteImageMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        addErrorMessage(error.message);
      }
      if (data) {
        const { deleteImage } = data;
        if (isErrorPayload(deleteImage)) {
          const { message } = deleteImage;
          addErrorMessage(message);
          onClose();
        } else {
          onImageDeleted();
        }
      }
    }
  }, [loading, data, error]);

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
        <DialogTitle id="dialog-title">Delete the image</DialogTitle>
        <DialogContent>
          <DialogContentText>
            This action will delete the image and might break projects which use it. It cannot be reversed.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button variant="contained" onClick={onDeleteImage} color="primary" data-testid="delete-image">
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};
