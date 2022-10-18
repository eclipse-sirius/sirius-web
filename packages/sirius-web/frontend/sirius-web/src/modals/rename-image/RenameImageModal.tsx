/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import TextField from '@material-ui/core/TextField';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import {
  GQLErrorPayload,
  GQLRenameImageMutationData,
  GQLRenameImageMutationVariables,
  GQLRenameImagePayload,
  RenameImageModalProps,
} from './RenameImageModal.types';
import {
  ChangeNameEvent,
  HandleResponseEvent,
  HideToastEvent,
  RenameImageModalContext,
  RenameImageModalEvent,
  renameImageModalMachine,
  RequestImageRenamingEvent,
  SchemaValue,
  ShowToastEvent,
} from './RenameImageModalMachine';

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

const isErrorPayload = (payload: GQLRenameImagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const RenameImageModal = ({ imageId, initialImageName, onImageRenamed, onClose }: RenameImageModalProps) => {
  const [{ value, context }, dispatch] = useMachine<RenameImageModalContext, RenameImageModalEvent>(
    renameImageModalMachine,
    {
      context: {
        name: initialImageName,
        initialName: initialImageName,
      },
    }
  );
  const { toast, renameImageModal } = value as SchemaValue;
  const { name, nameIsInvalid, nameMessage, message } = context;

  const onNewName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const name = event.target.value;
    const changeNameEvent: ChangeNameEvent = { type: 'CHANGE_NAME', name };
    dispatch(changeNameEvent);
  };

  const [renameImage, { loading, data, error }] = useMutation<GQLRenameImageMutationData>(renameImageMutation);
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

        const { renameImage } = data;
        if (isErrorPayload(renameImage)) {
          const { message } = renameImage;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, dispatch]);

  const onRenameImage = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    const requestImageRenamingEvent: RequestImageRenamingEvent = { type: 'REQUEST_IMAGE_RENAMING' };
    dispatch(requestImageRenamingEvent);

    event.preventDefault();
    const variables: GQLRenameImageMutationVariables = {
      input: {
        id: uuid(),
        imageId,
        newLabel: name,
      },
    };
    renameImage({ variables });
  };

  useEffect(() => {
    if (renameImageModal === 'success') {
      onImageRenamed();
    }
  }, [renameImageModal, onImageRenamed]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" maxWidth="xs" fullWidth>
        <DialogTitle id="dialog-title">Rename the image</DialogTitle>
        <DialogContent>
          <TextField
            value={name}
            onChange={onNewName}
            error={nameIsInvalid}
            helperText={nameMessage}
            label="Name"
            placeholder="Enter the new image name"
            data-testid="name"
            autoFocus
            fullWidth
          />
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={renameImageModal !== 'valid'}
            onClick={onRenameImage}
            color="primary"
            data-testid="rename-image">
            Rename
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'right',
        }}
        open={toast === 'visible'}
        autoHideDuration={3000}
        onClose={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
        message={message}
        action={
          <IconButton
            size="small"
            aria-label="close"
            color="inherit"
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}>
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
