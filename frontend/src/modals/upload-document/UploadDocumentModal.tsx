/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import FormGroup from '@material-ui/core/FormGroup';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import { sendFile } from 'common/sendFile';
import { FileUpload } from 'core/file-upload/FileUpload';
import { Form } from 'core/form/Form';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import { GQLErrorPayload, GQLUploadDocumentPayload, UploadDocumentModalProps } from './UploadDocumentModal.types';
import {
  HandleResponseEvent,
  HideToastEvent,
  RequestDocumentUploadingEvent,
  SchemaValue,
  SelectDocumentEvent,
  ShowToastEvent,
  UploadDocumentModalContext,
  UploadDocumentModalEvent,
  uploadDocumentModalMachine,
} from './UploadDocumentModalMachine';

const uploadDocumentMutationFile = gql`
  mutation uploadDocument($input: UploadDocumentInput!) {
    uploadDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`.loc.source.body;

const isErrorPayload = (payload: GQLUploadDocumentPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';
export const UploadDocumentModal = ({ editingContextId, onDocumentUploaded, onClose }: UploadDocumentModalProps) => {
  const [{ value, context }, dispatch] = useMachine<UploadDocumentModalContext, UploadDocumentModalEvent>(
    uploadDocumentModalMachine
  );

  const { toast, uploadDocumentModal } = value as SchemaValue;
  const { file, message } = context;

  // Execute the upload of a document and redirect to the newly created document
  const uploadDocument = async (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    const requestDocumentUploadingEvent: RequestDocumentUploadingEvent = { type: 'REQUEST_DOCUMENT_UPLOADING' };
    dispatch(requestDocumentUploadingEvent);

    event.preventDefault();
    const variables = {
      input: {
        id: uuid(),
        editingContextId,
        file: null, // the file will be send as a part of the multipart POST query.
      },
    };
    try {
      const { data, error } = await sendFile(uploadDocumentMutationFile, variables, file);
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

        const { uploadDocument } = data;
        if (isErrorPayload(uploadDocument)) {
          const { message } = uploadDocument;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    } catch (exception) {
      // Handle other errors like max file size error send by the backend...
      const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message: exception.toString() };
      dispatch(showToastEvent);
    }
  };

  const onFileSelected = (file) => {
    const selectDocumentEvent: SelectDocumentEvent = { type: 'SELECT_DOCUMENT', file };
    dispatch(selectDocumentEvent);
  };

  useEffect(() => {
    if (uploadDocumentModal === 'success') {
      onDocumentUploaded();
    }
  }, [uploadDocumentModal, onDocumentUploaded]);

  const canSubmit = uploadDocumentModal === 'documentSelected';
  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
        <DialogTitle id="dialog-title">Upload new model</DialogTitle>
        <DialogContent>
          <Form id="upload-form-id" onSubmit={uploadDocument} encType="multipart/form-data">
            <FormGroup>
              <FileUpload onFileSelected={onFileSelected} data-testid="file" />
            </FormGroup>
          </Form>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={!canSubmit}
            color="primary"
            type="submit"
            form="upload-form-id"
            data-testid="upload-document"
          >
            Upload
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
            onClick={() => dispatch({ type: 'HIDE_TOAST' } as HideToastEvent)}
          >
            <CloseIcon fontSize="small" />
          </IconButton>
        }
        data-testid="error"
      />
    </>
  );
};
