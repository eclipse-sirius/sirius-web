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
import { useMutation } from '@apollo/client';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import IconButton from '@material-ui/core/IconButton';
import Snackbar from '@material-ui/core/Snackbar';
import CloseIcon from '@material-ui/icons/Close';
import { useMachine } from '@xstate/react';
import gql from 'graphql-tag';
import React, { useEffect } from 'react';
import { v4 as uuid } from 'uuid';
import {
  DeleteDocumentModalProps,
  GQLDeleteDocumentMutationData,
  GQLDeleteDocumentPayload,
  GQLErrorPayload,
} from './DeleteDocumentModal.types';
import {
  DeleteDocumentModalContext,
  DeleteDocumentModalEvent,
  deleteDocumentModalMachine,
  HandleResponseEvent,
  HideToastEvent,
  RequestDocumentDeletingEvent,
  SchemaValue,
  ShowToastEvent,
} from './DeleteDocumentModalMachine';

const deleteDocumentMutation = gql`
  mutation deleteDocument($input: DeleteDocumentInput!) {
    deleteDocument(input: $input) {
      __typename
      ... on ErrorPayload {
        message
      }
    }
  }
`;

const isErrorPayload = (payload: GQLDeleteDocumentPayload): payload is GQLErrorPayload =>
  payload.__typename === 'ErrorPayload';

export const DeleteDocumentModal = ({
  documentName,
  documentId,
  onDocumentDeleted,
  onClose,
}: DeleteDocumentModalProps) => {
  const [{ value, context }, dispatch] = useMachine<DeleteDocumentModalContext, DeleteDocumentModalEvent>(
    deleteDocumentModalMachine
  );
  const { toast, deleteDocumentModal } = value as SchemaValue;
  const { message } = context;

  const [performDocumentDeletion, { loading, data, error }] =
    useMutation<GQLDeleteDocumentMutationData>(deleteDocumentMutation);
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

        const { deleteDocument } = data;
        if (isErrorPayload(deleteDocument)) {
          const { message } = deleteDocument;
          const showToastEvent: ShowToastEvent = { type: 'SHOW_TOAST', message };
          dispatch(showToastEvent);
        }
      }
    }
  }, [loading, data, error, dispatch]);

  const onDeleteDocument = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    const requestDocumentDeletingEvent: RequestDocumentDeletingEvent = { type: 'REQUEST_DOCUMENT_DELETING' };
    dispatch(requestDocumentDeletingEvent);

    event.preventDefault();
    const variables = {
      input: {
        id: uuid(),
        documentId,
      },
    };
    performDocumentDeletion({ variables });
  };

  useEffect(() => {
    if (deleteDocumentModal === 'success') {
      onDocumentDeleted();
    }
  }, [deleteDocumentModal, onDocumentDeleted]);

  return (
    <>
      <Dialog open={true} onClose={onClose} aria-labelledby="dialog-title" fullWidth>
        <DialogTitle id="dialog-title">{`Permanently delete "${documentName}"`}</DialogTitle>
        <DialogContent>
          <DialogContentText>
            This action will permanently delete the document and all its associated content. These items will no longer
            be accessible to you or anyone else. This action is irreversible.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            onClick={onDeleteDocument}
            color="primary"
            data-testid="delete-document"
            disabled={deleteDocumentModal !== 'pristine'}>
            Delete
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
      />
    </>
  );
};
