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
import { gql } from '@apollo/client';
import { ServerContext, ServerContextValue, useMultiToast } from '@eclipse-sirius/sirius-components-core';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import FormGroup from '@mui/material/FormGroup';
import TextField from '@mui/material/TextField';
import React, { useContext, useState } from 'react';
import { makeStyles } from 'tss-react/mui';
import { FileUpload } from '../../../..//core/file-upload/FileUpload';
import { sendFile } from '../../../../core/sendFile';
import {
  GQLErrorPayload,
  GQLUploadImageMutationVariables,
  GQLUploadImagePayload,
  UploadImageModalProps,
  UploadImageModalState,
} from './UploadImageModal.types';

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

const useUploadImageModalStyle = makeStyles()((theme) => ({
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

const isNameInvalid = (name: string) => name && name.trim().length < 3;

export const UploadImageModal = ({ projectId, onImageUploaded, onClose }: UploadImageModalProps) => {
  const { classes } = useUploadImageModalStyle();
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);
  const { addErrorMessage } = useMultiToast();

  const [state, setState] = useState<UploadImageModalState>({
    label: '',
    file: null,
  });

  const onNewName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const name = event.target.value;
    setState((prevState) => ({
      ...prevState,
      label: name,
    }));
  };

  // Execute the upload of a image and redirect to the newly created image
  const uploadImage = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const variables: GQLUploadImageMutationVariables = {
      input: {
        id: crypto.randomUUID(),
        projectId,
        label: state.label,
        file: null, // the file will be send as a part of the multipart POST query.
      },
    };

    try {
      const { data, error } = await sendFile(httpOrigin, uploadImageMutationFile, variables, state.file);
      if (error) {
        addErrorMessage(error.message);
      }
      if (data) {
        const { uploadImage } = data;
        if (isErrorPayload(uploadImage)) {
          const { message } = uploadImage;
          addErrorMessage(message);
          onClose();
        } else {
          onImageUploaded();
        }
      }
    } catch (exception) {
      addErrorMessage('An unexpected error has occurred, the file uploaded may be too large');
    }
  };

  const onFileSelected = (file: File) => {
    setState((prevState) => ({
      ...prevState,
      file: file,
    }));
  };

  const isError = isNameInvalid(state.label);
  const canSubmit = !isError && state.file;
  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
        <DialogTitle id="dialog-title">Upload new image</DialogTitle>
        <DialogContent>
          <form id="upload-form-id" onSubmit={uploadImage} encType="multipart/form-data" className={classes.form}>
            <TextField
              variant="standard"
              label="Label"
              name="label"
              value={state.label}
              error={isError}
              helperText="The name must contain at least 3 characters"
              placeholder="Label for the image"
              data-testid="label"
              inputProps={{ 'data-testid': 'label-input' }}
              autoFocus={true}
              onChange={onNewName}
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
    </>
  );
};
