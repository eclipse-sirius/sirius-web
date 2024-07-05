/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import { gql } from '@apollo/client';
import { ServerContext, ServerContextValue, Toast } from '@eclipse-sirius/sirius-components-core';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormGroup from '@material-ui/core/FormGroup';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import { useMachine } from '@xstate/react';
import React, { useContext, useEffect, useState } from 'react';
import { FileUpload } from '../../../..//core/file-upload/FileUpload';
import { sendFile } from '../../../../core/sendFile';
import {
  GQLErrorPayload,
  GQLUploadImageMutationVariables,
  GQLUploadImagePayload,
  UploadImageModalProps,
} from './UploadImageModal.types';
import {
  HandleResponseEvent,
  HideToastEvent,
  RequestImageUploadingEvent,
  SchemaValue,
  SelectImageEvent,
  ShowToastEvent,
  UploadImageModalContext,
  UploadImageModalEvent,
  uploadImageModalMachine,
} from './UploadImageModalMachine';

const uploadImageMutationFile = gql`
  mutation uploadImage($input: UploadImageInput!) {
    uploadImage(input: $input) {
      __typename
      ... on UploadImageSuccessPayload {
        id
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

const useUploadImageModalStyle = makeStyles((theme) => ({
  form: {
    display: 'flex',
    flexDirection: 'column',
    paddingTop: theme.spacing(1),
    paddingLeft: theme.spacing(2),
    paddingRight: theme.spacing(2),
    '& > *': {
      marginBottom: theme.spacing(2),
    },
  },
}));

const isErrorPayload = (payload: GQLUploadImagePayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const UploadImageModal = ({ projectId, onImageUploaded, onClose }: UploadImageModalProps) => {
  const classes = useUploadImageModalStyle();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const [{ value, context }, dispatch] = useMachine<UploadImageModalContext, UploadImageModalEvent>(
    uploadImageModalMachine
  );

  const { toast, uploadImageModal } = value as SchemaValue;
  const { file, message } = context;

  const [label, setLabel] = useState<string>('');

  // Execute the upload of a image and redirect to the newly created image
  const uploadImage = async (event: React.FormEvent<HTMLFormElement>) => {
    const requestImageUploadingEvent: RequestImageUploadingEvent = { type: 'REQUEST_IMAGE_UPLOADING' };
    dispatch(requestImageUploadingEvent);

    event.preventDefault();
    const variables: GQLUploadImageMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        label,
        file: null, // the file will be send as a part of the multipart POST query.
      },
    };
    try {
      const { data, error } = await sendFile(httpOrigin, uploadImageMutationFile, variables, file);
      if (error) {
        const showToastEvent: ShowToastEvent = {
          type: 'SHOW_TOAST',
          message: 'An unexpected error has occurred, the file uploaded may be too large',
        };
        dispatch(showToastEvent);
      }
      if (data) {
        const event: HandleResponseEvent = { type: 'HANDLE_RESPONSE', data };
        dispatch(event);

        const { uploadImage } = data;
        if (isErrorPayload(uploadImage)) {
          const { message } = uploadImage;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    } catch (exception) {
      const showToastEvent: ShowToastEvent = {
        type: 'SHOW_TOAST',
        message: 'An unexpected error has occurred, the file uploaded may be too large',
      };
      dispatch(showToastEvent);
    }
  };

  const onFileSelected = (file) => {
    const selectImageEvent: SelectImageEvent = { type: 'SELECT_IMAGE', file };
    dispatch(selectImageEvent);
  };

  useEffect(() => {
    if (uploadImageModal === 'success') {
      onImageUploaded();
    }
  }, [uploadImageModal, onImageUploaded]);

  const canSubmit = uploadImageModal === 'imageSelected';
  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
        <DialogTitle id="dialog-title">Upload new image</DialogTitle>
        <DialogContent>
          <form id="upload-form-id" onSubmit={uploadImage} encType="multipart/form-data" className={classes.form}>
            <TextField
              label="Label"
              name="label"
              value={label}
              placeholder="Label for the image"
              data-testid="label"
              inputProps={{ 'data-testid': 'label-input' }}
              autoFocus={true}
              onChange={(event) => setLabel(event.target.value)}
              disabled={uploadImageModal === 'uploadingImage'}
            />
            <FormGroup>
              <FileUpload onFileSelected={onFileSelected} data-testid="file" />
            </FormGroup>
          </form>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={!canSubmit}
            color="primary"
            type="submit"
            form="upload-form-id"
            data-testid="upload-image">
            Upload
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
