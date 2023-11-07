/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import { Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import { useMachine } from '@xstate/react';
import React, { useEffect } from 'react';
import {
  DeleteImageModalProps,
  GQLDeleteImageMutationData,
  GQLDeleteImageMutationVariables,
  GQLDeleteImagePayload,
  GQLErrorPayload,
} from './DeleteImageModal.types';
import {
  DeleteImageModalContext,
  DeleteImageModalEvent,
  deleteImageModalMachine,
  HandleResponseEvent,
  HideToastEvent,
  RequestImageDeletionEvent,
  SchemaValue,
  ShowToastEvent,
} from './DeleteImageModalMachine';

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
  const [{ value, context }, dispatch] = useMachine<DeleteImageModalContext, DeleteImageModalEvent>(
    deleteImageModalMachine
  );
  const { toast, deleteImageModal } = value as SchemaValue;
  const { message } = context;

  const [deleteImage, { loading, data, error }] = useMutation<GQLDeleteImageMutationData>(deleteImageMutation);
  useEffect(() => {
    if (!loading) {
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: error.message,
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const event: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data };
        dispatch(event);

        const { deleteImage } = data;
        if (isErrorPayload(deleteImage)) {
          const { message } = deleteImage;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, dispatch]);

  const onDeleteImage = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    const requestImageDeletionEvent: RequestImageDeletionEvent = { type: 'REQUEST_IMAGE_DELETION' };
    dispatch(requestImageDeletionEvent);

    event.preventDefault();
    const variables: GQLDeleteImageMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        imageId,
      },
    };
    deleteImage({ variables });
  };

  useEffect(() => {
    if (deleteImageModal === 'success') {
      onImageDeleted();
    }
  }, [deleteImageModal, onImageDeleted]);

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
          <Button
            variant="contained"
            disabled={deleteImageModal !== 'idle'}
            onClick={onDeleteImage}
            color="primary"
            data-testid="delete-image">
            Delete
          </Button>
        </DialogActions>
      </Dialog>
      <Toast
        message={message}
        open={toast === 'visible'}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
      />
    </>
  );
};
